package ru.softstone.kotime.presentation.timer

import android.animation.Animator
import android.animation.AnimatorInflater
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_timer.*
import ru.softstone.kotime.R
import ru.softstone.kotime.architecture.presentation.BaseNavigationFragment
import ru.softstone.kotime.presentation.getFormattedDuration

class TimerFragment : BaseNavigationFragment<TimerPresenter>(), TimerView {
    companion object {
        fun newInstance() = TimerFragment()
        private const val DEFAULT_ANIMATION_DURATION = 200L
    }

    private lateinit var pulseAnimator: Animator
    private var isTimerRunning = false

    @InjectPresenter
    lateinit var presenter: TimerPresenter

    override fun onAttach(context: Context) {
        pulseAnimator = AnimatorInflater.loadAnimator(context, R.animator.timer_animator)
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        timer_button.setOnClickListener { presenter.onTimerControlClick() }
        timer_view.setOnClickListener { presenter.onAddRecordClick() }
        contribute_button.setOnClickListener { presenter.onContributeClick() }
    }

    override fun onResume() {
        super.onResume()
        pulseAnimator.resume()
    }

    override fun onPause() {
        super.onPause()
        pulseAnimator.pause()
    }

    override fun showTime(seconds: Int) {
        timer_view.text = getFormattedDuration(seconds, true)
    }

    override fun showStopTimerDialog() {
        MaterialDialog(context!!).show {
            title(R.string.stop_timer_dialog_title)
            message(R.string.stop_timer_dialog_message)
            negativeButton(android.R.string.no)
            positiveButton(android.R.string.yes) {
                presenter.onStopTimer()
            }
        }
    }

    override fun setIsRunning(running: Boolean) {
        isTimerRunning = running
        timer_view.isClickable = running
        if (running) {
            timer_button.setImageResource(R.drawable.ic_stop)
            startAnimation()
        } else {
            timer_button.setImageResource(R.drawable.ic_start)
            stopAnimation()
        }
    }

    private fun startAnimation() {
        add_record_label.animate().apply {
            alpha(1f)
            duration = DEFAULT_ANIMATION_DURATION
        }.start()
        pulseAnimator.apply {
            setTarget(pulse_view)
            start()
        }
    }

    private fun stopAnimation() {
        pulseAnimator.cancel()
        pulse_view.animate().apply {
            scaleX(1f)
            scaleY(1f)
            duration = DEFAULT_ANIMATION_DURATION
        }.start()
        add_record_label.animate().apply {
            alpha(0f)
            duration = DEFAULT_ANIMATION_DURATION
        }.start()
    }

    @ProvidePresenter
    override fun providePresenter(): TimerPresenter {
        return super.providePresenter()
    }

}