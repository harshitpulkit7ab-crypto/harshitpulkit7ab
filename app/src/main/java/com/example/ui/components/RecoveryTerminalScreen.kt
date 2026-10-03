package com.example.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.RedmiY3Device
import com.example.viewmodel.AppScreen
import com.example.viewmodel.PixelRomViewModel

@Composable
fun RecoveryTerminalScreen(
  viewModel: PixelRomViewModel,
  modifier: Modifier = Modifier
) {
  // BackHandler to navigate back to Home
  BackHandler(onBack = { viewModel.navigateTo(AppScreen.HOME) })

  val terminalLogs by viewModel.terminalLogs.collectAsState()
  val isFlashing by viewModel.isFlashingRunning.collectAsState()
  val progress by viewModel.flashingProgress.collectAsState()
  val listState = rememberLazyListState()

  LaunchedEffect(terminalLogs.size) {
    if (terminalLogs.isNotEmpty()) {
      listState.animateScrollToItem(terminalLogs.size - 1)
    }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .statusBarsPadding()
      .navigationBarsPadding()
      .background(Color(0xFF0F172A))
  ) {
    // Top Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 6.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(
        onClick = { viewModel.navigateTo(AppScreen.FLASHER) },
        modifier = Modifier.testTag("terminal_back_button")
      ) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowBack,
          contentDescription = "Back",
          tint = Color.White
        )
      }

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = "OrangeFox Recovery Terminal",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = Color.White
          )
        )
        Text(
          text = "Target: Redmi Y3 (${RedmiY3Device.CODENAME}) • Partition: /system",
          style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF94A3B8))
        )
      }

      Surface(
        shape = RoundedCornerShape(8.dp),
        color = if (isFlashing) Color(0xFFF59E0B) else if (progress >= 1f) Color(0xFF10B981) else Color(0xFF3B82F6)
      ) {
        Text(
          text = if (isFlashing) "FLASHING..." else if (progress >= 1f) "SUCCESS" else "READY",
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.Bold,
            color = Color.Black
          ),
          modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
      }
    }

    // Progress Bar
    if (isFlashing || progress > 0f) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 6.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(
            text = "Flashing PixelOS 16 Image...",
            style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF38BDF8))
          )
          Text(
            text = "${(progress * 100).toInt()}%",
            style = MaterialTheme.typography.labelSmall.copy(
              color = Color.White,
              fontWeight = FontWeight.Bold
            )
          )
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
          progress = { progress },
          modifier = Modifier
            .fillMaxWidth()
            .height(6.dp)
            .clip(CircleShape),
          color = Color(0xFF38BDF8),
          trackColor = Color(0xFF1E293B)
        )
      }
    }

    // Monospace Terminal Output Window
    Card(
      modifier = Modifier
        .weight(1f)
        .padding(horizontal = 16.dp, vertical = 8.dp),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(
        containerColor = Color(0xFF030712)
      ),
      border = CardDefaults.outlinedCardBorder()
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(14.dp)
      ) {
        // Terminal Title header dots
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Color(0xFFEF4444)))
            Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Color(0xFFF59E0B)))
            Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Color(0xFF10B981)))
          }
          Text(
            text = "bash - /sbin/recovery",
            style = MaterialTheme.typography.bodySmall.copy(
              color = Color(0xFF64748B),
              fontSize = 10.sp,
              fontFamily = FontFamily.Monospace
            )
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (terminalLogs.isEmpty()) {
          Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Icon(
                imageVector = Icons.Default.Terminal,
                contentDescription = null,
                tint = Color(0xFF475569),
                modifier = Modifier.size(48.dp)
              )
              Spacer(modifier = Modifier.height(8.dp))
              Text(
                text = "Tap 'Start Flashing' below to install PixelOS 16",
                style = MaterialTheme.typography.bodySmall.copy(
                  color = Color(0xFF94A3B8),
                  fontFamily = FontFamily.Monospace
                )
              )
            }
          }
        } else {
          LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize()
          ) {
            items(terminalLogs) { log ->
              val logColor = when {
                log.contains("[OK]") -> Color(0xFF34D399)
                log.contains("MATCH") -> Color(0xFF38BDF8)
                log.contains("successfully") -> Color(0xFFFCD34D)
                log.startsWith("   +") -> Color(0xFFA78BFA)
                else -> Color(0xFFE2E8F0)
              }

              Text(
                text = log,
                style = MaterialTheme.typography.bodySmall.copy(
                  fontFamily = FontFamily.Monospace,
                  fontSize = 11.sp,
                  lineHeight = 16.sp,
                  color = logColor
                ),
                modifier = Modifier.padding(vertical = 1.dp)
              )
            }
          }
        }
      }
    }

    // Action Buttons
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      Button(
        onClick = { viewModel.startSimulatedFlash() },
        enabled = !isFlashing,
        modifier = Modifier.weight(1f),
        colors = ButtonDefaults.buttonColors(
          containerColor = Color(0xFF38BDF8),
          contentColor = Color.Black
        ),
        shape = RoundedCornerShape(14.dp)
      ) {
        Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = if (isFlashing) "Flashing..." else "Start Flashing", fontWeight = FontWeight.Bold)
      }

      OutlinedButton(
        onClick = { viewModel.navigateTo(AppScreen.HOME) },
        modifier = Modifier.weight(1f),
        shape = RoundedCornerShape(14.dp)
      ) {
        Icon(imageVector = Icons.Default.RestartAlt, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = "Boot System", color = Color.White)
      }
    }
  }
}
