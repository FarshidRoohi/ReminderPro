package ir.roohi.farshid.reminderpro.database


import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context

import ir.roohi.farshid.reminderpro.model.NoteEntity
import ir.roohi.farshid.reminderpro.model.VoiceEntity

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */

@Database(entities = [NoteEntity::class, VoiceEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun voiceDao(): VoiceDao

    companion object {

        private const val DATABASE_NAME = "AppDatabase.db"
        @Volatile
        var instance: AppDatabase? = null

        private val LOCK = Any()

        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(LOCK) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DATABASE_NAME).build()
                    }
                }
            }
            return instance
        }
    }
}
