package ru.softstone.kotime.presentation.actions.edit

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import dagger.android.support.AndroidSupportInjection
import ru.softstone.kotime.R
import ru.softstone.kotime.architecture.presentation.BaseFragment

class EditActionFragment : BaseFragment<EditActionPresenter>(), EditActionView {
    companion object {
        fun newInstance() = EditActionFragment()
    }

    @InjectPresenter
    lateinit var presenter: EditActionPresenter

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_action, container, false)
    }

    @ProvidePresenter
    override fun providePresenter(): EditActionPresenter {
        return super.providePresenter()
    }

}