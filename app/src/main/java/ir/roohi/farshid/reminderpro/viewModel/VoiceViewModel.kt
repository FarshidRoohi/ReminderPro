package ir.roohi.farshid.reminderpro.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import ir.roohi.farshid.reminderpro.model.VoiceEntity
import ir.roohi.farshid.reminderpro.repository.VoiceRepository
import java.util.*

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 12/24/18.
 */
class VoiceViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: VoiceRepository? = null
    var mutableList: LiveData<List<VoiceEntity>>? = null

    init {
        this.repository = VoiceRepository.get(application)
        this.mutableList = this.repository!!.liveData
    }

    public fun add(title: String, path: String, duration: Int) {
        repository!!.add(VoiceEntity(Date(), title, path, duration))
    }
    public fun remove(item:VoiceEntity){
        repository!!.delete(item)
    }
    public fun update(item:VoiceEntity){
        repository!!.update(item)
    }
}