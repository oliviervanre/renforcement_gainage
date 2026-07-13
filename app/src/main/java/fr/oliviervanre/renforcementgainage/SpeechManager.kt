package fr.oliviervanre.renforcementgainage

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale
import kotlin.math.roundToLong

class SpeechManager(context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = TextToSpeech(context.applicationContext, this)
    private var ready = false
    private val speechRate = 0.95f

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts?.language = Locale.FRANCE
            tts?.setSpeechRate(speechRate)
            ready = true
        }
    }

    fun speak(text: String, flush: Boolean = true) {
        if (!ready) return

        val mode = if (flush) TextToSpeech.QUEUE_FLUSH else TextToSpeech.QUEUE_ADD
        tts?.speak(text, mode, null, text.hashCode().toString())
    }

    fun estimatedSpeechDurationMillis(text: String): Long {
        val wordCount = text
            .trim()
            .split(Regex("\\s+"))
            .count { it.isNotBlank() }
            .coerceAtLeast(1)

        val wordsPerMinute = 145.0 * speechRate
        val spokenMillis = (wordCount / wordsPerMinute * 60_000.0).roundToLong()
        return (spokenMillis + 900L).coerceIn(2_200L, 7_500L)
    }

    fun stop() {
        tts?.stop()
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
    }
}
