package com.example.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.PerformanceProfile
import com.example.model.RedmiY3Device
import com.example.viewmodel.AppScreen
import com.example.viewmodel.PixelRomViewModel

@Composable
fun SmoothEngineScreen(
  viewModel: PixelRomViewModel,
  modifier: Modifier = Modifier
) {
  // BackHandler to navigate back to Home
  BackHandler(onBack = { viewModel.navigateTo(AppScreen.HOME) })

  val optimization by viewModel.optimizationState.collectAsState()
  val telemetry by viewModel.telemetryState.collectAsState()
  val scrollState = rememberScrollState()

  Column(
    modifier = modifier
      .fillMaxSize()
      .statusBarsPadding()
      .navigationBarsPadding()
      .background(MaterialTheme.colorScheme.background)
  ) {
    // Top Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 4.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(
        onClick = { viewModel.navigateTo(AppScreen.HOME) },
        modifier = Modifier.testTag("smooth_engine_back")
      ) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowBack,
          contentDescription = "Back",
          tint = MaterialTheme.colorScheme.onSurface
        )
      }

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = "SmoothEngine 16",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(
          text = "Redmi Y3 (onclite) • Snapdragon 632",
          style = MaterialTheme.typography.bodySmall.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        )
      }

      Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (optimization.ultraSmoothMode) Color(0xFF0F5132) else Color(0xFF78350F)
      ) {
        Text(
          text = if (optimization.ultraSmoothMode) "60 FPS LOCKED" else "STOCK JANK",
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.Bold,
            color = Color.White
          ),
          modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
      }
    }

    // Main Content
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
        .padding(horizontal = 16.dp, vertical = 8.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      // Hardware Banner: Redmi Y3 Specs
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "Target Hardware: Redmi Y3",
                style = MaterialTheme.typography.titleSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.primary
                )
              )
              Text(
                text = "Codename: ${RedmiY3Device.CODENAME} • SDM632 14nm",
                style = MaterialTheme.typography.bodySmall.copy(
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              )
            }

            Surface(
              shape = CircleShape,
              color = MaterialTheme.colorScheme.primaryContainer
            ) {
              Text(
                text = "Android 16",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Spec Pills
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            SpecChip(label = "GPU", value = "Adreno 506", modifier = Modifier.weight(1f))
            SpecChip(label = "Cores", value = "8x Kryo 250", modifier = Modifier.weight(1f))
            SpecChip(label = "Display", value = "720x1520 60Hz", modifier = Modifier.weight(1f))
          }
        }
      }

      // Live FPS and Frametime Monitor Card
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.surface
        ),
        border = CardDefaults.outlinedCardBorder()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "Live Framerate Telemetry",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
              )
              Text(
                text = "Frametime: 16.6ms (Locked 60Hz panel)",
                style = MaterialTheme.typography.bodySmall.copy(
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              )
            }

            Text(
              text = "${telemetry.currentFps} FPS",
              style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Black,
                color = if (telemetry.currentFps >= 59) Color(0xFF22C55E) else Color(0xFFF59E0B)
              )
            )
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Real-time Canvas Graph for FPS History
          val fpsHistory = telemetry.fpsHistory
          val primaryColor = MaterialTheme.colorScheme.primary
          val gridLineColor = MaterialTheme.colorScheme.outlineVariant

          Canvas(
            modifier = Modifier
              .fillMaxWidth()
              .height(70.dp)
              .clip(RoundedCornerShape(12.dp))
              .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
          ) {
            val width = size.width
            val height = size.height

            // Baseline at 60 fps
            drawLine(
              color = Color(0xFF22C55E).copy(alpha = 0.4f),
              start = Offset(0f, height * 0.2f),
              end = Offset(width, height * 0.2f),
              strokeWidth = 2f
            )

            // Grid line at 30 fps
            drawLine(
              color = gridLineColor.copy(alpha = 0.3f),
              start = Offset(0f, height * 0.8f),
              end = Offset(width, height * 0.8f),
              strokeWidth = 1f
            )

            if (fpsHistory.size > 1) {
              val path = Path()
              val stepX = width / (fpsHistory.size - 1)

              fpsHistory.forEachIndexed { index, fpsVal ->
                val normY = (65f - fpsVal) / 20f
                val clampedY = (normY * height).coerceIn(0f, height)
                val x = index * stepX

                if (index == 0) {
                  path.moveTo(x, clampedY)
                } else {
                  path.lineTo(x, clampedY)
                }
              }

              drawPath(
                path = path,
                color = primaryColor,
                style = Stroke(width = 4f, cap = StrokeCap.Round)
              )
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "CPU Governor: ${telemetry.activeGovernor}",
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp)
            )
            Text(
              text = "Jank Rate: ${if (optimization.ultraSmoothMode) "0.0%" else "6.4%"}",
              style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = if (optimization.ultraSmoothMode) Color(0xFF22C55E) else Color(0xFFEF4444)
              )
            )
          }
        }
      }

      // Performance Profiles Presets
      Column {
        Text(
          text = "Optimization Profiles",
          style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
        )
        Text(
          text = "Choose configuration tuned for Snapdragon 632",
          style = MaterialTheme.typography.bodySmall.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        )

        Spacer(modifier = Modifier.height(8.dp))

        PerformanceProfile.values().forEach { profile ->
          val isSelected = optimization.selectedProfile == profile
          ProfileSelectionCard(
            profile = profile,
            isSelected = isSelected,
            onClick = { viewModel.applyProfile(profile) }
          )
          Spacer(modifier = Modifier.height(8.dp))
        }
      }

      // Memory & Low-RAM Architecture (3GB / 4GB)
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "Memory Architecture & ZRAM",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )

            // Select 3GB or 4GB variant of Redmi Y3
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
              FilterChip(
                selected = optimization.ramVariant == "3GB",
                onClick = { viewModel.setRamVariant("3GB") },
                label = { Text("3GB RAM") },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = MaterialTheme.colorScheme.primary,
                  selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                )
              )
              FilterChip(
                selected = optimization.ramVariant == "4GB",
                onClick = { viewModel.setRamVariant("4GB") },
                label = { Text("4GB RAM") },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = MaterialTheme.colorScheme.primary,
                  selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                )
              )
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          // RAM Bar
          val usedFrac = (telemetry.usedRamMb.toFloat() / telemetry.totalRamMb.toFloat()).coerceIn(0f, 1f)
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .height(14.dp)
              .clip(CircleShape)
              .background(MaterialTheme.colorScheme.surface)
          ) {
            Box(
              modifier = Modifier
                .fillMaxWidth(usedFrac)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
            )
          }

          Spacer(modifier = Modifier.height(6.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "Used: ${telemetry.usedRamMb}MB",
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp)
            )
            Text(
              text = "Total: ${telemetry.totalRamMb}MB (${((1f - usedFrac) * telemetry.totalRamMb).toInt()}MB Free)",
              style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
              )
            )
          }
        }
      }

      // Granular Low-Spec Switches
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
          text = "Low-Spec Smoothness Switches",
          style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
        )

        TuningSwitchItem(
          title = "Adreno 506 HW Overlay Bypass",
          subtitle = "Forces GPU composition, skips CPU rendering (saves 22% CPU)",
          icon = Icons.Default.Speed,
          checked = optimization.adrenoBoost,
          onCheckedChange = { viewModel.toggleAdrenoBoost() }
        )

        TuningSwitchItem(
          title = "Low-RAM Hybrid Architecture",
          subtitle = "ro.config.low_ram=true (Cuts 450MB system bloat, light GMS)",
          icon = Icons.Default.Memory,
          checked = optimization.lowRamMode,
          onCheckedChange = { viewModel.toggleLowRamMode() }
        )

        TuningSwitchItem(
          title = "Disable Heavy Window Blurs",
          subtitle = "Bypasses Gaussian blurs on Adreno 506 for steady 60 FPS",
          icon = Icons.Default.VisibilityOff,
          checked = optimization.disableWindowBlurs,
          onCheckedChange = { viewModel.toggleDisableWindowBlurs() }
        )

        TuningSwitchItem(
          title = "ZRAM 2048MB (LZ4 Compressed)",
          subtitle = "High-speed kernel swap; doubles multitasking capability",
          icon = Icons.Default.SwapHoriz,
          checked = optimization.zramEnabled,
          onCheckedChange = { viewModel.toggleZram() }
        )

        TuningSwitchItem(
          title = "Aggressive LMKD Cache Purge",
          subtitle = "Low Memory Killer daemon prioritizes foreground UI frame rate",
          icon = Icons.Default.Bolt,
          checked = optimization.aggressiveLmk,
          onCheckedChange = { viewModel.toggleAggressiveLmk() }
        )
      }

      // Animation Scale Selector
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "Window & Transition Animation Scale",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
          )
          Text(
            text = "0.5x scale is recommended for Snapdragon 632 to eliminate perceived input lag.",
            style = MaterialTheme.typography.bodySmall.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          )

          Spacer(modifier = Modifier.height(10.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            listOf(0.5f, 0.75f, 1.0f).forEach { scale ->
              val isSelected = optimization.animationScale == scale
              Surface(
                shape = RoundedCornerShape(12.dp),
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                modifier = Modifier
                  .weight(1f)
                  .clickable { viewModel.setAnimationScale(scale) }
              ) {
                Text(
                  text = "${scale}x ${if (scale == 0.5f) "(Best)" else ""}",
                  style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                  ),
                  modifier = Modifier.padding(vertical = 10.dp),
                  textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))
    }
  }
}

@Composable
fun SpecChip(
  label: String,
  value: String,
  modifier: Modifier = Modifier
) {
  Surface(
    shape = RoundedCornerShape(12.dp),
    color = MaterialTheme.colorScheme.surface,
    modifier = modifier
  ) {
    Column(
      modifier = Modifier.padding(8.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = label,
        style = MaterialTheme.typography.labelSmall.copy(
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          fontSize = 10.sp
        )
      )
      Text(
        text = value,
        style = MaterialTheme.typography.labelSmall.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 11.sp
        )
      )
    }
  }
}

@Composable
fun ProfileSelectionCard(
  profile: PerformanceProfile,
  isSelected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    shape = RoundedCornerShape(16.dp),
    color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
    modifier = modifier
      .fillMaxWidth()
      .clickable(onClick = onClick)
      .testTag("profile_card_${profile.name.lowercase()}")
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(40.dp)
          .clip(CircleShape)
          .background(
            if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
          ),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = if (isSelected) Icons.Default.CheckCircle else Icons.Default.Tune,
          contentDescription = null,
          tint = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
          modifier = Modifier.size(22.dp)
        )
      }

      Spacer(modifier = Modifier.width(12.dp))

      Column(modifier = Modifier.weight(1f)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = profile.title,
            style = MaterialTheme.typography.labelLarge.copy(
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
          )
          Spacer(modifier = Modifier.width(6.dp))
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
          ) {
            Text(
              text = "${profile.targetFps} FPS",
              style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
              ),
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
          }
        }
        Text(
          text = profile.description,
          style = MaterialTheme.typography.bodySmall.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 11.sp
          )
        )
      }
    }
  }
}

@Composable
fun TuningSwitchItem(
  title: String,
  subtitle: String,
  icon: ImageVector,
  checked: Boolean,
  onCheckedChange: (Boolean) -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    border = CardDefaults.outlinedCardBorder()
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(36.dp)
          .clip(CircleShape)
          .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = MaterialTheme.colorScheme.primary,
          modifier = Modifier.size(20.dp)
        )
      }

      Spacer(modifier = Modifier.width(12.dp))

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = title,
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
        )
        Text(
          text = subtitle,
          style = MaterialTheme.typography.bodySmall.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 11.sp
          )
        )
      }

      Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(
          checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
          checkedTrackColor = MaterialTheme.colorScheme.primary
        )
      )
    }
  }
}
