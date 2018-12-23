package ir.roohi.farshid.reminderpro.repository

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import ir.roohi.farshid.reminderpro.database.AppDatabase
import ir.roohi.farshid.reminderpro.model.VoiceEntity
import java.util.*
import java.util.concurrent.Executors

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 12/23/18.
 */
class VoiceRepository(context: Context) {

    private var dataBase: AppDatabase? = null
    private var liveData: MutableLiveData<MutableList<VoiceEntity>>? = null

    private val executor = Executors.newSingleThreadExecutor()

    companion object {
        private var instance: VoiceRepository? = null
        fun get(context: Context): VoiceRepository {
            if (instance == null) {
                return VoiceRepository(context)
            }
            return instance!!
        }
    }

    init {
        this.dataBase = AppDatabase.getInstance(context)
        this.liveData = getList()
    }

    public fun getList(): MutableLiveData<MutableList<VoiceEntity>> {
        return this.dataBase!!.voiceDao().all
    }

    public fun add(title: String, path: String) {
        executor.execute {
            this.dataBase!!.voiceDao().insert(VoiceEntity(Date(), title, path))
        }

    }

    public fun delete(item: VoiceEntity) {
        executor.execute {
            this.dataBase!!.voiceDao().delete(item)
        }
    }
}