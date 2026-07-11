package fr.oliviervanre.renforcementgainage

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {

    private val viewModel: WorkoutViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContent {
            val context = LocalContext.current
            val speechManager = SpeechManager(context)

            DisposableEffect(Unit) {
                viewModel.attachSpeechManager(speechManager)
                onDispose {
                    speechManager.shutdown()
                }
            }

            val state by viewModel.uiState.collectAsState()

            WorkoutScreen(
                state = state,
                onStart = viewModel::start,
                onPause = viewModel::pause,
                onResume = viewModel::resume,
                onStop = viewModel::stop
            )
        }
    }
}
