package ir.roohi.farshid.reminderpro.database

import androidx.lifecycle.LiveData
import androidx.room.*
import ir.roohi.farshid.reminderpro.model.VoiceEntity

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 12/23/18.
 */
@Dao
interface VoiceDao {

    @get:Query("SELECT * FROM voices ORDER BY date DESC")
    val all: LiveData<List<VoiceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: VoiceEntity)

    @Delete
    fun delete(item: VoiceEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(item: VoiceEntity)

}