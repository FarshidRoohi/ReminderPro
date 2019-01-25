package ir.roohi.farshid.reminderpro.database

import androidx.lifecycle.LiveData
import androidx.room.*
import ir.roohi.farshid.reminderpro.model.LocationEntity
import java.util.*

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/4/19.
 */
@Dao
interface LocationDao {

    @get:Query("SELECT * FROM locations ORDER BY date DESC")
    val all: LiveData<List<LocationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: LocationEntity)

    @Delete
    fun delete(item: LocationEntity)


    @Update
    fun update(item: LocationEntity)


}