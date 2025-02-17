package com.buntupana.tmdb.core.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.buntupana.tmdb.core.data.database.dao.EpisodesDao
import com.buntupana.tmdb.core.data.database.dao.MovieDetailsDao
import com.buntupana.tmdb.core.data.database.dao.TvShowDetailsDao
import com.buntupana.tmdb.core.data.database.entity.EpisodeEntity
import com.buntupana.tmdb.core.data.database.entity.MovieDetailsEntity
import com.buntupana.tmdb.core.data.database.entity.TvShowDetailsEntity

@Database(
    entities = [
        MovieDetailsEntity::class,
        TvShowDetailsEntity::class,
        EpisodeEntity::class
    ],
    version = 2,
    exportSchema = false,
)
abstract class TmdbDataBase : RoomDatabase() {

    companion object {
        fun newInstance(context: Context): TmdbDataBase {
            return Room.databaseBuilder(
                context = context,
                klass = TmdbDataBase::class.java,
                name = "tmdb-database"
            ).fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract val movieDetailsDao: MovieDetailsDao

    abstract val tvShowDetailsDao: TvShowDetailsDao

    abstract val episodesDao: EpisodesDao
}