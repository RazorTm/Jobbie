package com.example.jobbie.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jobbie.models.JobToSave

@Database(entities = [JobToSave::class], version = 1)
abstract class JobDatabase : RoomDatabase() {

    abstract fun getRemoteJobDao(): JobDao

    companion object {
        @Volatile
        private var instance: JobDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                JobDatabase::class.java,
                "remoteJob_db2"
            ).build()
    }
}