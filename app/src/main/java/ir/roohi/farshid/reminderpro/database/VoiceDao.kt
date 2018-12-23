package ir.roohi.farshid.reminderpro.database

import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.room.*
import ir.roohi.farshid.reminderpro.model.VoiceEntity

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 12/23/18.
 */
@Dao
interface VoiceDao {

    @get:Query("SELECT * FROM tbl_voice ORDER BY date DESC")
    val all: MutableLiveData<MutableList<VoiceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: VoiceEntity)

    @Delete
    fun delete(item: VoiceEntity)

}