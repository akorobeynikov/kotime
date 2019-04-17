package ru.softstone.kotime.data

import android.content.Context
import android.preference.PreferenceManager
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.softstone.kotime.architecture.data.AndroidLogger
import ru.softstone.kotime.architecture.data.SchedulerProvider
import ru.softstone.kotime.architecture.data.StateStorageImpl
import ru.softstone.kotime.architecture.domain.Logger
import ru.softstone.kotime.architecture.domain.StateStorage
import ru.softstone.kotime.data.action.ActionSourceImpl
import ru.softstone.kotime.data.billing.ReactivePlayBilling
import ru.softstone.kotime.data.category.CategorySourceImpl
import ru.softstone.kotime.data.storage.AppDatabase
import ru.softstone.kotime.data.storage.DatabasePopulater
import ru.softstone.kotime.data.storage.MIGRATION_1_2
import ru.softstone.kotime.data.string.StringProviderImpl
import ru.softstone.kotime.data.time.TimeSourceImpl
import ru.softstone.kotime.domain.action.ActionSource
import ru.softstone.kotime.domain.billing.BillingSource
import ru.softstone.kotime.domain.category.CategorySource
import ru.softstone.kotime.domain.common.StringProvider
import ru.softstone.kotime.domain.time.TimeSource
import javax.inject.Provider
import javax.inject.Singleton

@Module(includes = [DataModule.Declarations::class])
class DataModule {
    companion object {
        private const val DATABASE_NAME = "kotime_database"
    }

    @Module
    internal interface Declarations {
        @Binds
        @Singleton
        fun bindLogger(logger: AndroidLogger): Logger

        @Binds
        @Singleton
        fun bindTimeSource(timeSource: TimeSourceImpl): TimeSource

        @Binds
        @Singleton
        fun bindSchedulerProrovider(provider: AppSchedulerProvider): SchedulerProvider

        @Binds
        @Singleton
        fun bindActionSource(source: ActionSourceImpl): ActionSource

        @Binds
        @Singleton
        fun bindCategorySource(source: CategorySourceImpl): CategorySource

        @Binds
        @Singleton
        fun bindStateStorage(storage: StateStorageImpl): StateStorage

        @Binds
        @Singleton
        fun bindStringProvider(provider: StringProviderImpl): StringProvider

        @Binds
        @Singleton
        fun bindBillingSource(source: ReactivePlayBilling): BillingSource
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Context, databasePopulaterProvider: Provider<DatabasePopulater>) =
        Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .addMigrations(MIGRATION_1_2)
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    databasePopulaterProvider.get().populateCategories()
                }
            })
            .build()

    @Provides
    @Singleton
    fun provideActionDao(appDatabase: AppDatabase) = appDatabase.actionDao()

    @Provides
    @Singleton
    fun provideCategoryDao(appDatabase: AppDatabase) = appDatabase.categoryDao()

    @Provides
    @Singleton
    fun providePref(context: Context) = PreferenceManager.getDefaultSharedPreferences(context)

}