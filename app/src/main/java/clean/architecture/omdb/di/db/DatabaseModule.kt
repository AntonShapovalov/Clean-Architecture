package clean.architecture.omdb.di.db

import android.content.Context
import androidx.room.Room
import clean.architecture.data.db.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module that provides singleton instance of [MovieDatabase].
 * Can be replaced with TestDatabase for integration testing.
 * See: https://developer.android.com/training/dependency-injection/hilt-testing.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Provides singleton instance of [MovieDatabase].
     */
    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieDatabase {
        val builder = Room.databaseBuilder(
            context.applicationContext,
            MovieDatabase::class.java,
            "movie_database"
        ).fallbackToDestructiveMigration()
        return builder.build()
    }
}
