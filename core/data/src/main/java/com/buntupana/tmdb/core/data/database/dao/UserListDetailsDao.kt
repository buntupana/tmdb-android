package com.buntupana.tmdb.core.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.buntupana.tmdb.core.data.database.entity.UserListDetailsEntity
import com.buntupana.tmdb.core.data.database.entity.UserListDetailsSimpleEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class UserListDetailsDao {

    @Upsert
    abstract suspend fun upsert(userListDetailsEntity: UserListDetailsEntity)

    @Upsert(entity = UserListDetailsEntity::class)
    abstract suspend fun upsert(userListDetailsSimpleEntity: UserListDetailsSimpleEntity)

    @Upsert
    abstract suspend fun upsert(userList: List<UserListDetailsEntity>)

    @Query("SELECT * FROM user_list_details WHERE id = :id")
    abstract fun getById(id: Long): Flow<UserListDetailsEntity?>

    @Query(
        """
        SELECT * FROM user_list_details
        ORDER BY updatedAt DESC
    """
    )
    abstract fun getAll(): PagingSource<Int, UserListDetailsEntity>

    @Query(
        """
        UPDATE user_list_details
        SET itemCount = itemCount + 1
        WHERE id = :id;
        """
    )
    abstract suspend fun addItemCount(id: Long)

    @Query(
        """
        UPDATE user_list_details
        SET itemCount = itemCount - 1
        WHERE id = :id;
        """
    )
    abstract suspend fun restItemCount(id: Long)


    @Query("DELETE FROM user_list_details WHERE id = :id")
    abstract suspend fun deleteById(id: Long)

    @Query("DELETE FROM user_list_details")
    abstract suspend fun clearAll()
}