package ir.roohi.farshid.reminderpro.repository

import com.google.gson.JsonElement
import ir.roohi.farshid.reminderpro.listener.OnCallbackResponse
import okhttp3.*
import java.io.IOException

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2/22/19.
 */
class FeedbackRepository(val callback: OnCallbackResponse) {

    fun request(json: JsonElement) {

        val okHttp = OkHttpClient.Builder()
        val requestBody = RequestBody.create(MediaType.get("application/json; charset=utf-8"), json.toString())
        val request = Request.Builder()
        request.header("User-agent", "android.user.reminderPro")
        request.post(requestBody)
        request.url("http://farshid-roohi.ir/api/feedback.php")

        okHttp.build().newCall(request.build()).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailure(e.message!!)
            }

            override fun onResponse(call: Call, response: Response) {
                callback.onSuccess(response.body()!!.string())
            }
        })


    }
}