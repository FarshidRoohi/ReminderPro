package ir.roohi.farshid.reminderpro.listener.multiSelect

import ir.roohi.farshid.reminderpro.model.VoiceEntity

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/14/19.
 */
interface OnMultiSelectVoiceListener {
    fun onMultiSelectVoice(items: ArrayList<VoiceEntity>)
}