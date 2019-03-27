package ir.roohi.farshid.reminderpro.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import ir.roohi.farshid.reminderpro.ResourceApplication
import ir.roohi.farshid.reminderpro.listener.OnCallbackResponse
import ir.roohi.farshid.reminderpro.repository.FeedbackRepository
import org.json.JSONObject

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2/22/19.
 */
class FeedbackViewModel(application: Application) : AndroidViewModel(application), OnCallbackResponse {

    private var repository: FeedbackRepository = FeedbackRepository(this)
    var mutableLiveData = MutableLiveData<Boolean>()

    fun send(content: String, name: String) {
        val json = JsonObject()
        json.addProperty("user_msg", content)
        json.addProperty("user_name", name)
        json.addProperty("user_device", ResourceApplication.applicationResource!!.getDeviceName())
        repository.request(json)
    }

    override fun onFailure(data: String) {
        mutableLiveData.postValue(false)
    }

    override fun onSuccess(response: String) {
        val success = JSONObject(response).getString("success")
        mutableLiveData.postValue(success != null)
    }
}