package com.example.myfirstcrudapp.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myfirstcrudapp.Model.Candy

@Database(
    entities = [Candy::class],
    version = 1,
    exportSchema = false
)
abstract class CandyDatabase: RoomDatabase() {
    abstract fun candyDao(): CandyDao

    companion object {
        @Volatile
        private var Instance: CandyDatabase? = null

        fun getDatabase(context: Context): CandyDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, CandyDatabase::class.java, "candy_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}