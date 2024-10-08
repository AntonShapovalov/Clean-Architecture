package clean.architecture.omdb.di.db

import clean.architecture.data.db.MovieDatabase
import clean.architecture.data.db.dao.SearchDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module that provides singleton instances of DAO classes.
 */
@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    /**
     * Provides the [SearchDao] instance.
     */
    @Provides
    @Singleton
    fun provideSearchDao(movieDatabase: MovieDatabase): SearchDao {
        return movieDatabase.searchDao()
    }
}
