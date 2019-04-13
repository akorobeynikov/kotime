package ru.softstone.kotime

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.softstone.kotime.data.DataModule
import ru.softstone.kotime.domain.DomainModule
import ru.softstone.kotime.presentation.PresentationModule
import ru.softstone.kotime.presentation.actions.edit.delegate.di.ActionDelegateComponent
import ru.softstone.kotime.presentation.actions.edit.delegate.di.ActionDelegateModule
import javax.inject.Singleton


@Component(
    modules = [
        DataModule::class,
        DomainModule::class,
        PresentationModule::class,
        AppModule::class
    ]
)
@Singleton
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun presentationModule(presentationModule: PresentationModule): Builder

        fun build(): AppComponent
    }

    fun actionDelegateComponent(module: ActionDelegateModule): ActionDelegateComponent

    fun inject(app: App)
}