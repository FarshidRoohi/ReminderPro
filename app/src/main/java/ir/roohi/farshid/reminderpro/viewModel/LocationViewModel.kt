package ir.roohi.farshid.reminderpro.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.mapbox.mapboxsdk.geometry.LatLng
import ir.roohi.farshid.reminderpro.model.LocationEntity
import ir.roohi.farshid.reminderpro.repository.LocationRepository
import java.util.*

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/5/19.
 */
class LocationViewModel(application: Application) : AndroidViewModel(application) {

    var liveDateLocations: LiveData<List<LocationEntity>>? = null
    var repository: LocationRepository? = null

    init {
        repository = LocationRepository.instance(application)
        liveDateLocations = repository!!.getLocations()
    }

    fun all(){
        liveDateLocations = repository!!.getLocations()
    }

    fun add(title: String, text: String?, status: Boolean, point: LatLng, distance: Int) {
        repository!!.add(LocationEntity(Date(), title, text, status, point.latitude, point.longitude, distance))
    }

    fun update(item: LocationEntity) {
        repository!!.update(item)
    }

    fun remove(item: LocationEntity) {
        repository!!.remove(item)
    }
}