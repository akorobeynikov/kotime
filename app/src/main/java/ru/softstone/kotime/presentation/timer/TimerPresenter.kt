package ru.softstone.kotime.presentation.timer

import com.arellomobile.mvp.InjectViewState
import ru.softstone.kotime.architecture.presentation.BasePresenter
import javax.inject.Inject

@InjectViewState
class TimerPresenter @Inject constructor() : BasePresenter<TimerView>()