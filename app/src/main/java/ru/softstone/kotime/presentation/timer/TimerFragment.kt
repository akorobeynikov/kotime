package ru.softstone.kotime.presentation.timer

import android.animation.Animator
import android.animation.AnimatorInflater
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_timer.*
import ru.softstone.kotime.R
import ru.softstone.kotime.architecture.presentation.BaseFragment
import ru.softstone.kotime.presentation.getFormattedDuration

class TimerFragment : BaseFragment<TimerPresenter>(), TimerView {
    companion object {
        fun newInstance() = TimerFragment()
        private const val DEFAUL_ANIMATION_DURATION = 200L
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
        timer_button.setOnClickListener { presenter.onTimerClick() }
        timer_view.setOnClickListener { presenter.onAddRecordClick() }
    }

    override fun onResume() {
        super.onResume()
        pulseAnimator.resume()
    }

    override fun onPause() {
        super.onPause()
        pulseAnimator.pause()
    }

    private fun startPulseAnimation() {
        pulseAnimator.apply {
            setTarget(pulse_view)
            start()
        }
    }

    override fun showTime(seconds: Int) {
        timer_view.text = getFormattedDuration(seconds, true)
    }

    override fun setIsRunning(running: Boolean) {
        isTimerRunning = running
        if (running) {
            add_record_label.animate().apply {
                alpha(1f)
                duration = DEFAUL_ANIMATION_DURATION
            }.start()
            pulseAnimator.apply {
                setTarget(pulse_view)
                start()
            }
        } else {
            pulseAnimator.cancel()
            pulse_view.animate().apply {
                scaleX(1f)
                scaleY(1f)
                duration = DEFAUL_ANIMATION_DURATION
            }.start()
            add_record_label.animate().apply {
                alpha(0f)
                duration = DEFAUL_ANIMATION_DURATION
            }.start()
        }
        timer_button.isChecked = running
    }

    @ProvidePresenter
    override fun providePresenter(): TimerPresenter {
        return super.providePresenter()
    }

}