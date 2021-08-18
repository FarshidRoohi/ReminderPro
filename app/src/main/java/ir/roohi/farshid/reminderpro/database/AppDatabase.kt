package ir.roohi.farshid.reminderpro.database


import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import ir.roohi.farshid.reminderpro.model.LocationEntity

import ir.roohi.farshid.reminderpro.model.NoteEntity
import ir.roohi.farshid.reminderpro.model.VoiceEntity

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */

@Database(entities = [NoteEntity::class, VoiceEntity::class, LocationEntity::class], version = 2)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun voiceDao(): VoiceDao
    abstract fun locationDao(): LocationDao

    companion object {

        private const val DATABASE_NAME = "AppDatabase.db"
        @Volatile
        var instance: AppDatabase? = null

        private val LOCK = Any()

        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(LOCK) {
                    if (instance == null) {
                        instance =
                                Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DATABASE_NAME)
                                        .fallbackToDestructiveMigration()
                                        .build()
                    }
                }
            }
            return instance
        }
    }
}
