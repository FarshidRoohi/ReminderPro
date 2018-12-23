package ir.roohi.farshid.reminderpro.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ir.roohi.farshid.reminderpro.model.VoiceEntity
import ir.roohi.farshid.reminderpro.repository.VoiceRepository
import java.util.*

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 12/24/18.
 */
class VoiceViewModel : AndroidViewModel {

    private var repository: VoiceRepository? = null
    var mutableList: LiveData<List<VoiceEntity>>? = null

    constructor(application: Application) : super(application) {
        this.repository = VoiceRepository.get(application)
        this.mutableList = this.repository!!.liveData
    }

    public fun add(title: String, path: String) {
        repository!!.add(VoiceEntity(Date(), title, path))
    }
}