package com.buntupana.tmdb.core.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.buntupana.tmdb.core.data.database.dao.EpisodesDao
import com.buntupana.tmdb.core.data.database.dao.FavoriteDao
import com.buntupana.tmdb.core.data.database.dao.MediaDao
import com.buntupana.tmdb.core.data.database.dao.RemoteKeyDao
import com.buntupana.tmdb.core.data.database.dao.UserListDetailsDao
import com.buntupana.tmdb.core.data.database.dao.UserListItemDao
import com.buntupana.tmdb.core.data.database.dao.WatchlistDao
import com.buntupana.tmdb.core.data.database.entity.EpisodeEntity
import com.buntupana.tmdb.core.data.database.entity.FavoriteEntity
import com.buntupana.tmdb.core.data.database.entity.MediaEntity
import com.buntupana.tmdb.core.data.database.entity.RemoteKeyEntity
import com.buntupana.tmdb.core.data.database.entity.UserListDetailsEntity
import com.buntupana.tmdb.core.data.database.entity.UserListItemEntity
import com.buntupana.tmdb.core.data.database.entity.WatchlistEntity

@Database(
    entities = [
        RemoteKeyEntity::class,
        EpisodeEntity::class,
        MediaEntity::class,
        WatchlistEntity::class,
        FavoriteEntity::class,
        UserListDetailsEntity::class,
        UserListItemEntity::class
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

    abstract val remoteKeyDao: RemoteKeyDao

    abstract val episodesDao: EpisodesDao

    abstract val mediaDao: MediaDao

    abstract val watchlistDao: WatchlistDao

    abstract val favoriteDao: FavoriteDao

    abstract val userListDetailsDao: UserListDetailsDao

    abstract val userListItemDao: UserListItemDao
}