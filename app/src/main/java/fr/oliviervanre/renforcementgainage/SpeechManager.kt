package fr.oliviervanre.renforcementgainage

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import java.util.Locale
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class SpeechManager(context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = TextToSpeech(context.applicationContext, this)
    private var ready = false
    private val speechRate = 0.95f
    private val pendingUtterances = ConcurrentHashMap<String, () -> Unit>()

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts?.language = Locale.FRANCE
            tts?.setSpeechRate(speechRate)
            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) = Unit

                override fun onDone(utteranceId: String?) {
                    completeUtterance(utteranceId)
                }

                @Deprecated("Deprecated in Java")
                override fun onError(utteranceId: String?) {
                    completeUtterance(utteranceId)
                }

                override fun onError(utteranceId: String?, errorCode: Int) {
                    completeUtterance(utteranceId)
                }
            })
            ready = true
        }
    }

    fun speak(text: String, flush: Boolean = true) {
        if (!ready) return

        val mode = if (flush) TextToSpeech.QUEUE_FLUSH else TextToSpeech.QUEUE_ADD
        tts?.speak(text, mode, null, text.hashCode().toString())
    }

    suspend fun speakAndWait(text: String, flush: Boolean = true) {
        if (!ready) return

        val utteranceId = UUID.randomUUID().toString()
        val mode = if (flush) TextToSpeech.QUEUE_FLUSH else TextToSpeech.QUEUE_ADD

        suspendCoroutine<Unit> { continuation ->
            pendingUtterances[utteranceId] = {
                continuation.resume(Unit)
            }

            val result = tts?.speak(text, mode, null, utteranceId)
            if (result == TextToSpeech.ERROR) {
                completeUtterance(utteranceId)
            }
        }
    }

    private fun completeUtterance(utteranceId: String?) {
        if (utteranceId == null) return
        pendingUtterances.remove(utteranceId)?.invoke()
    }

    fun stop() {
        pendingUtterances.clear()
        tts?.stop()
    }

    fun shutdown() {
        pendingUtterances.clear()
        tts?.stop()
        tts?.shutdown()
        tts = null
    }
}
