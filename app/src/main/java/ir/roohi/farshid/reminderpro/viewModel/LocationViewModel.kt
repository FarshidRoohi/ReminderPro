package ir.roohi.farshid.reminderpro.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import ir.roohi.farshid.reminderpro.model.LocationEntity
import ir.roohi.farshid.reminderpro.repository.LocationRepository
import java.util.*

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/5/19.
 */
class LocationViewModel(application: Application) : AndroidViewModel(application) {

    public var liveDateLocations: LiveData<List<LocationEntity>>? = null
    public var repository: LocationRepository? = null

    init {
        repository = LocationRepository.instance(application)
        liveDateLocations = repository!!.getLocations()
    }

    public fun add(date: Date, title: String, text: String, status: Boolean,
                   latitude: Double, longitude: Double) {

        repository!!.add(LocationEntity(date, title, text, status, latitude, longitude))
    }
}