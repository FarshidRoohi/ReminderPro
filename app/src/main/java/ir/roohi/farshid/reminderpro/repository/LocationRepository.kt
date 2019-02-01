package ir.roohi.farshid.reminderpro.repository

import android.content.Context
import androidx.lifecycle.LiveData
import ir.roohi.farshid.reminderpro.database.AppDatabase
import ir.roohi.farshid.reminderpro.model.LocationEntity
import java.util.concurrent.Executors

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/4/19.
 */
class LocationRepository constructor(context: Context) {

    private var database: AppDatabase? = null
    var locationList: LiveData<List<LocationEntity>>? = null

    private val executors = Executors.newSingleThreadExecutor()

    init {
        database = AppDatabase.getInstance(context)
        this.locationList = getLocations()
    }

    companion object {
        private var repository: LocationRepository? = null
        fun instance(context: Context): LocationRepository {
            if (repository == null) {
                repository = LocationRepository(context)
            }
            return repository!!
        }
    }

    public fun getLocations(): LiveData<List<LocationEntity>> {
        return database!!.locationDao().all
    }

    public fun add(item: LocationEntity) {
        executors.execute {
            database!!.locationDao().insert(item)
        }
    }

    public fun remove(item: LocationEntity) {
        executors.execute {
            database!!.locationDao().delete(item)
        }
    }

    public fun update(item: LocationEntity) {
        executors.execute {
            database!!.locationDao().update(item)
        }
    }
}