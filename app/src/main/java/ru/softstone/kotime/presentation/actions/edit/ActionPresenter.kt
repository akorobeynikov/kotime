package ru.softstone.kotime.presentation.actions.edit

import com.arellomobile.mvp.InjectViewState
import io.reactivex.functions.BiFunction
import ru.softstone.kotime.R
import ru.softstone.kotime.architecture.data.SchedulerProvider
import ru.softstone.kotime.architecture.domain.Logger
import ru.softstone.kotime.architecture.presentation.BasePresenter
import ru.softstone.kotime.domain.action.ActionInteractor
import ru.softstone.kotime.domain.category.CategoryInteractor
import ru.softstone.kotime.domain.category.model.Category
import ru.softstone.kotime.domain.common.StringProvider
import ru.softstone.kotime.domain.error.ErrorInteractor
import ru.softstone.kotime.domain.time.TimeInteractor
import ru.softstone.kotime.presentation.ERROR_SCREEN
import ru.softstone.kotime.presentation.actions.edit.delegate.ActionPresenterDelegate
import ru.softstone.kotime.presentation.actions.edit.delegate.di.ActionDelegateProvider
import ru.softstone.kotime.presentation.actions.edit.model.ActionScreenData
import ru.terrakok.cicerone.Router
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@InjectViewState
class ActionPresenter @Inject constructor(
    private val categoryInteractor: CategoryInteractor,
    private val schedulerProvider: SchedulerProvider,
    private val actionInteractor: ActionInteractor,
    private val timeInteractor: TimeInteractor,
    private val errorInteractor: ErrorInteractor,
    private val actionDelegateProvider: ActionDelegateProvider,
    private val stringProvider: StringProvider,
    private val router: Router,
    private val logger: Logger
) : BasePresenter<ActionView>() {

    companion object {
        private const val TOLERANCE = 60000 //ms
        private const val CHANGE_DURATION_STEP = 5 //minutes
    }

    private val calendar = Calendar.getInstance()
    private var delegate: ActionPresenterDelegate? = null
    private var categories: List<Category>? = null
    private var selectedCategoryIndex = 0
    private lateinit var startDate: Date
    private lateinit var endDate: Date
    private lateinit var screenData: ActionScreenData

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        addDisposable(
            actionInteractor.getActionState()
                .map { actionDelegateProvider.getInstance(it) }
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe({ delegate ->
                    this.delegate = delegate
                    initScreen(delegate)
                }, {
                    val wtf = "ru.softstone.kotime.presentation.actions.edit.ActionPresenter.onFirstViewAttach"
                    logger.error(wtf, it)
                    errorInteractor.setLastError(wtf, it)
                    router.navigateTo(ERROR_SCREEN)
                })
        )
    }

    private fun initScreen(delegate: ActionPresenterDelegate) {
        addDisposable(
            categoryInteractor.observeActiveCategories()
                .firstOrError()
                .zipWith(delegate.getActionScreenData(),
                    BiFunction { categories: List<Category>, screenData: ActionScreenData ->
                        categories to screenData
                    })
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe({ pair ->
                    val categories = pair.first
                    val screenData = pair.second
                    this.categories = categories
                    this.screenData = screenData
                    this.startDate = screenData.startTime
                    this.endDate = screenData.endTime
                    showDataOnScreen(screenData)
                }, {
                    val wtf = "ru.softstone.kotime.presentation.actions.edit.ActionPresenter.initScreen"
                    logger.error(wtf, it)
                    errorInteractor.setLastError(wtf, it)
                    router.navigateTo(ERROR_SCREEN)
                })
        )
    }

    private fun showDataOnScreen(screenData: ActionScreenData) {
        updateSelectedCategory(screenData.selectedCategoryId)
        updateDuration()
        viewState.setDescription(screenData.description ?: "")
        viewState.showStartTime(screenData.startTime)
        viewState.showEndTime(screenData.endTime)
        validateAndShowErrors()
    }

    private fun updateSelectedCategory(categoryId: Int?) {
        categories?.let { categories ->
            selectedCategoryIndex = categories.indexOfFirst { category -> category.id == categoryId }
            if (selectedCategoryIndex == -1) {
                selectedCategoryIndex = 0 //если категория отключена или selectedCategoryId = null
            }
            viewState.showSelectedCategory(categories[selectedCategoryIndex].name)
        }
    }

    private fun updateDuration() {
        if (delegate?.needDuration() == true) {
            val durationSeconds = getDurationSeconds().toInt()
            val correctionSeconds = getCorrectionSeconds().toInt()
            if (durationSeconds >= 0) {
                viewState.showDuration(durationSeconds, correctionSeconds)
            }
        }
    }

    private fun validateAndShowErrors(): Boolean {
        var startTimeError = ""
        var endTimeError = ""
        var hasError = false
        if (isFuture(startDate)) {
            hasError = true
            startTimeError += stringProvider.getString(R.string.future_time_error) + " "
        }
        if (isFuture(endDate)) {
            hasError = true
            endTimeError += stringProvider.getString(R.string.future_time_error) + " "
        }
        if (startDate > endDate) {
            hasError = true
            startTimeError += stringProvider.getString(R.string.start_before_end_error) + " "
        }
        viewState.enableNextButton(!hasError)
        viewState.showStartTimeError(startTimeError)
        viewState.showEndTimeError(endTimeError)
        return hasError
    }

    private fun isFuture(date: Date): Boolean {
        return date.time - TOLERANCE > timeInteractor.getCurrentTime()
    }

    private fun getDurationSeconds(): Long {
        val milliseconds = endDate.time - startDate.time
        return TimeUnit.MILLISECONDS.toSeconds(milliseconds)
    }

    private fun getCorrectionSeconds(): Long {
        val milliseconds = endDate.time - screenData.endTime.time
        return TimeUnit.MILLISECONDS.toSeconds(milliseconds)
    }

    fun onCategoryClick() {
        categories?.let { categories ->
            viewState.showCategories(categories.map { it.name })
        }
    }

    fun onCategorySelected(index: Int) {
        selectedCategoryIndex = index
        categories?.let { categories ->
            viewState.showSelectedCategory(categories[selectedCategoryIndex].name)
        }
    }

    fun startTimeChanged(date: Date) {
        startDate = date
        viewState.showStartTime(date)
        updateDuration()
        validateAndShowErrors()
    }


    fun endTimeChanged(date: Date) {
        endDate = date
        viewState.showEndTime(date)
        updateDuration()
        validateAndShowErrors()
    }

    fun checkOverlapAddAction(description: String) {
        val hasError = validateAndShowErrors()
        if (!hasError) {
            delegate?.let {
                addDisposable(
                    it.checkOverlap(startDate, endDate)
                        .subscribeOn(schedulerProvider.ioScheduler())
                        .observeOn(schedulerProvider.mainScheduler())
                        .subscribe({ hasOverlap ->
                            if (hasOverlap) {
                                viewState.showOverlapWarning()
                            } else {
                                addAction(description)
                            }
                        }, {
                            val wtf = "Can't check overlap"
                            logger.error(wtf, it)
                            errorInteractor.setLastError(wtf, it)
                            router.navigateTo(ERROR_SCREEN)
                        })
                )
            }
        }
    }

    fun addAction(description: String) {
        val hasError = validateAndShowErrors()
        val delegate = this.delegate
        val categories = this.categories
        if (!hasError && delegate != null && categories != null) {
            addDisposable(
                delegate.saveAction(categories[selectedCategoryIndex].id, description.trim(), startDate, endDate)
                    .subscribeOn(schedulerProvider.ioScheduler())
                    .observeOn(schedulerProvider.mainScheduler())
                    .subscribe({
                        delegate.navigateNext()
                    }, {
                        val wtf = "Can't save action"
                        logger.error(wtf, it)
                        errorInteractor.setLastError(wtf, it)
                        router.navigateTo(ERROR_SCREEN)
                    })
            )
        }
    }

    override fun onDestroy() {
        actionDelegateProvider.clearInstance()
        super.onDestroy()
    }

    fun onMinusDurationClick() {
        changeEndTime(-CHANGE_DURATION_STEP)
    }

    fun onPlusDurationClick() {
        changeEndTime(CHANGE_DURATION_STEP)
    }

    private fun changeEndTime(minutesAmount: Int) {
        calendar.time = endDate
        calendar.add(Calendar.MINUTE, minutesAmount)
        endTimeChanged(calendar.time)
    }

    fun onBackPressed() {
        router.exit()
    }
}