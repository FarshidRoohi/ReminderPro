package ir.roohi.farshid.reminderpro.viewModel

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.listener.OnCallbackResponse
import ir.roohi.farshid.reminderpro.repository.FeedbackRepository
import ir.roohi.farshid.reminderpro.utility.getDeviceName
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2/22/19.
 */
class FeedbackViewModel(application: Application) : AndroidViewModel(application) {

    var isLoading = ObservableBoolean()
//    var errorMessageName = ObservableInt()
//    var errorMessageContent = ObservableInt()

    fun send(name: String,message: String): LiveData<Boolean> {
        val mutableLiveData = MutableLiveData<Boolean>()

        val dateFormat = SimpleDateFormat("dd/mm/yyyy")
        val date = dateFormat.format(Calendar.getInstance().time)

        val json = JsonObject()
        json.addProperty("user_msg", message)
        json.addProperty("user_name", name)
        json.addProperty("date", date)
        json.addProperty("user_device", getDeviceName())

        this.isLoading.set(true)


        val repository = FeedbackRepository(object : OnCallbackResponse {
            override fun onFailure(data: String) {
                isLoading.set(false)
                mutableLiveData.postValue(false)
            }

            override fun onSuccess(response: String) {
                isLoading.set(false)
                val success = JSONObject(response).getString("success")

                mutableLiveData.postValue(!success.isNullOrEmpty())
            }
        })

        repository.request(json)

        return mutableLiveData
    }
}