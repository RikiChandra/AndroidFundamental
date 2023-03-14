package com.example.githubapp.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubapp.data.entity.FavoriteEntity


@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM FavoriteEntity WHERE id = :id")
    fun deleteFavorite(id: Int)

    @Query("SELECT * FROM FavoriteEntity ORDER BY login ASC")
    fun getAllFavorite(): LiveData<List<FavoriteEntity>>

    @Query("SELECT * FROM FavoriteEntity WHERE FavoriteEntity.id = :id")
    fun getFavoriteById(id: Int): LiveData<FavoriteEntity>


}