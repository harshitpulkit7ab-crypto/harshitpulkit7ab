package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AboutDeviceScreen
import com.example.ui.components.Android16EasterEggDialog
import com.example.ui.components.PixelLauncherHome
import com.example.ui.components.PixelSettingsScreen
import com.example.ui.components.QuickSettingsShade
import com.example.ui.components.RecoveryTerminalScreen
import com.example.ui.components.RomFlasherHub
import com.example.ui.components.SmoothEngineScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.AppScreen
import com.example.viewmodel.PixelRomViewModel

class MainActivity : ComponentActivity() {
  private val viewModel: PixelRomViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      val isDarkTheme by viewModel.isDarkTheme.collectAsState()
      val palette by viewModel.currentPalette.collectAsState()

      MyApplicationTheme(
        darkTheme = isDarkTheme,
        palette = palette
      ) {
        PixelRomApp(viewModel = viewModel)
      }
    }
  }
}

@Composable
fun PixelRomApp(viewModel: PixelRomViewModel) {
  val currentScreen by viewModel.currentScreen.collectAsState()
  val isQuickSettingsOpen by viewModel.isQuickSettingsOpen.collectAsState()
  val isEasterEggVisible by viewModel.isEasterEggVisible.collectAsState()
  val toastMessage by viewModel.toastMessage.collectAsState()

  Box(modifier = Modifier.fillMaxSize()) {
    Crossfade(
      targetState = currentScreen,
      label = "screen_crossfade"
    ) { screen ->
      when (screen) {
        AppScreen.HOME -> PixelLauncherHome(viewModel = viewModel)
        AppScreen.OPTIMIZER -> SmoothEngineScreen(viewModel = viewModel)
        AppScreen.SETTINGS -> PixelSettingsScreen(viewModel = viewModel)
        AppScreen.ABOUT -> AboutDeviceScreen(viewModel = viewModel)
        AppScreen.FLASHER -> RomFlasherHub(viewModel = viewModel)
        AppScreen.TERMINAL -> RecoveryTerminalScreen(viewModel = viewModel)
      }
    }

    // Quick Settings Dropdown Overlay
    AnimatedVisibility(
      visible = isQuickSettingsOpen,
      enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
      exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut()
    ) {
      QuickSettingsShade(
        viewModel = viewModel,
        onDismiss = { viewModel.closeQuickSettings() }
      )
    }

    // Android 16 Easter Egg Dialog
    if (isEasterEggVisible) {
      Android16EasterEggDialog(
        onDismiss = { viewModel.dismissEasterEgg() }
      )
    }

    // Custom Material Toast/Snackbar Notification
    AnimatedVisibility(
      visible = toastMessage != null,
      enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
      exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
      modifier = Modifier
        .align(Alignment.BottomCenter)
        .navigationBarsPadding()
        .padding(bottom = 24.dp, start = 16.dp, end = 16.dp)
    ) {
      Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.inverseSurface,
        tonalElevation = 6.dp,
        modifier = Modifier.testTag("app_toast_message")
      ) {
        Text(
          text = toastMessage ?: "",
          style = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.inverseOnSurface,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp
          ),
          modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )
      }
    }
  }
}

// Retained for backwards-compatibility with screenshot and unit tests
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(text = "Hello $name!", modifier = modifier)
}
