package ir.roohi.farshid.reminderpro.database;



import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import ir.roohi.farshid.reminderpro.model.NoteEntity;
import ir.roohi.farshid.reminderpro.model.VoiceEntity;

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */

@Database(entities = {NoteEntity.class,VoiceEntity.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "AppDatabase.db";
    public static volatile AppDatabase instance;

    private static final Object LOCK = new Object();

    public abstract NoteDao noteDao();
    public abstract VoiceDao voiceDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {

                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext()
                            , AppDatabase.class, DATABASE_NAME).build();
                }

            }
        }
        return instance;
    }
}
