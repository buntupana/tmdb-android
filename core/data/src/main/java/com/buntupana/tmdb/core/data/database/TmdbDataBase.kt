package com.buntupana.tmdb.core.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.buntupana.tmdb.core.data.database.dao.AnyMediaDao
import com.buntupana.tmdb.core.data.database.dao.EpisodesDao
import com.buntupana.tmdb.core.data.database.dao.FavoriteDao
import com.buntupana.tmdb.core.data.database.dao.MovieDao
import com.buntupana.tmdb.core.data.database.dao.RemoteKeyDao
import com.buntupana.tmdb.core.data.database.dao.TvShowDao
import com.buntupana.tmdb.core.data.database.dao.WatchlistDao
import com.buntupana.tmdb.core.data.database.entity.AnyMediaEntity
import com.buntupana.tmdb.core.data.database.entity.EpisodeEntity
import com.buntupana.tmdb.core.data.database.entity.FavoriteEntity
import com.buntupana.tmdb.core.data.database.entity.MovieEntity
import com.buntupana.tmdb.core.data.database.entity.RemoteKeyEntity
import com.buntupana.tmdb.core.data.database.entity.TvShowEntity
import com.buntupana.tmdb.core.data.database.entity.WatchlistEntity

@Database(
    entities = [
        RemoteKeyEntity::class,
        MovieEntity::class,
        TvShowEntity::class,
        EpisodeEntity::class,
        AnyMediaEntity::class,
        WatchlistEntity::class,
        FavoriteEntity::class
    ],
    version = 1,
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

    abstract val remoteKeyDao: RemoteKeyDao

    abstract val movieDao: MovieDao

    abstract val tvShowDao: TvShowDao

    abstract val episodesDao: EpisodesDao

    abstract val anyMediaDao: AnyMediaDao

    abstract val watchlistDao: WatchlistDao

    abstract val favoriteDao: FavoriteDao
}