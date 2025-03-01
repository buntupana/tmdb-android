package com.buntupana.tmdb.core.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.buntupana.tmdb.core.data.database.entity.MovieEntity
import com.buntupana.tmdb.core.data.database.entity.MovieSimpleEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MovieDao {

    @Upsert
    abstract suspend fun upsert(movieEntity: MovieEntity)

    @Upsert(entity = MovieEntity::class)
    abstract suspend fun upsert(movieSimpleEntity: MovieSimpleEntity)

    @Upsert(entity = MovieEntity::class)
    abstract suspend fun upsert(movieSimpleEntityList: List<MovieSimpleEntity>)

    @Query("SELECT * FROM movie WHERE id = :id")
    abstract fun getMovieDetails(id: Long): Flow<MovieEntity>

    @Query("DELETE FROM movie WHERE id = :id")
    abstract suspend fun deleteMovieDetails(id: Long)

    @Query("""
        SELECT movie.* FROM movie 
        INNER JOIN watchlist ON movie.id = watchlist.mediaId 
        WHERE watchlist.mediaType = 'MOVIE'
        ORDER BY watchlist.addedAt ASC
    """)
    abstract fun getWatchlistMovies(): PagingSource<Int, MovieEntity>

    @Query("""
        SELECT movie.* FROM movie 
        INNER JOIN favorite ON movie.id = favorite.mediaId 
        WHERE favorite.mediaType = 'MOVIE'
        ORDER BY favorite.addedAt ASC
    """)
    abstract fun getFavoriteMovies(): PagingSource<Int, MovieEntity>

    @Delete
    abstract suspend fun deleteMovieDetails(movieEntity: MovieEntity)

    @Query("UPDATE movie SET isFavorite = :isFavorite WHERE id = :id")
    abstract suspend fun updateFavorite(id: Long, isFavorite: Boolean)

    @Query("UPDATE movie SET isWatchListed = :isWatchListed WHERE id = :id")
    abstract suspend fun updateWatchList(id: Long, isWatchListed: Boolean)

    @Query("UPDATE movie SET userRating = :rating WHERE id = :id")
    abstract suspend fun updateRating(id: Long, rating: Int?)

    @Transaction
    open suspend fun updateRatingAndWatchlist(id: Long, rating: Int?) {
        updateRating(id = id, rating = rating)
        if (rating != null && rating != 0) {
            updateWatchList(id = id, isWatchListed = false)
        }
    }

    @Query("DELETE FROM movie")
    abstract suspend fun clearAll()

}