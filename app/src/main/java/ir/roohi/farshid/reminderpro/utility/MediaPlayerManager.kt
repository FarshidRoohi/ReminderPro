package ir.roohi.farshid.reminderpro.utility

import android.media.MediaPlayer

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 3/30/19.
 */
class MediaPlayerManager {

    private val mediaPlayer = MediaPlayer()


    fun play(path: String) {
        reset()
        mediaPlayer.setDataSource(path)
        mediaPlayer.start()
    }

    fun pause() {
        mediaPlayer.pause()
    }

    fun stop() {
        mediaPlayer.stop()
    }

    fun reset() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.reset()
    }


}