package ru.softstone.kotime.data

import dagger.Binds
import dagger.Module
import ru.softstone.kotime.architecture.data.AndroidLogger
import ru.softstone.kotime.architecture.domain.Logger

@Module(includes = [DataModule.Declarations::class])
class DataModule {
    @Module
    internal interface Declarations {
        @Binds
        fun bindLogger(logger: AndroidLogger): Logger
    }
}