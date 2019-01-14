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

    public var liveDateLocations: LiveData<List<LocationEntity>>? = null
    public var repository: LocationRepository? = null

    init {
        repository = LocationRepository.instance(application)
        liveDateLocations = repository!!.getLocations()
    }

    public fun add(title: String, text: String?, status: Boolean, point: LatLng,distance:Int) {
        repository!!.add(LocationEntity(Date(), title, text, status, point.latitude, point.longitude,distance))
    }

    public fun update(item: LocationEntity) {
        repository!!.update(item.id!!, item.date, item.title, item.text, item.status, item.latitude, item.longitude,item.distance)
    }

    public fun remove(item:LocationEntity){
        repository!!.remove(item)
    }
}