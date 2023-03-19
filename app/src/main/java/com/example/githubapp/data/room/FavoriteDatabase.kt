package com.example.githubapp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubapp.data.entity.FavoriteEntity


@Database(entities = [FavoriteEntity::class], version = 6, exportSchema = true)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var instance: FavoriteDatabase? = null

        @JvmStatic
        fun getDatabase(context : Context) : FavoriteDatabase {
            if (instance == null) {
                synchronized(FavoriteDatabase::class.java) {
                    if (instance == null) {
                        instance = androidx.room.Room.databaseBuilder(
                            context.applicationContext,
                            FavoriteDatabase::class.java,
                            "favorite_database"
                        ).fallbackToDestructiveMigration().build()
                    }
                }
            }
            return instance as FavoriteDatabase
        }


    }


}