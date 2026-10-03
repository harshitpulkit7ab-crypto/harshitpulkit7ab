package com.example.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AirplanemodeActive
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BatteryAlert
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.BrightnessAuto
import androidx.compose.material.icons.filled.BrightnessMedium
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DoNotDisturb
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FlashlightOn
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.ScreenRotation
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled.VolumeMute
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.WifiTethering
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.QsNotificationItem
import com.example.model.QsTab
import com.example.model.RedmiY3Device
import com.example.viewmodel.AppScreen
import com.example.viewmodel.PixelRomViewModel
import kotlin.math.sin

/**
 * Official Google Android 16 (Baklava) Control Center & Notification Shade.
 *
 * Implements the authentic Android 16 layout:
 * - Dual Segmented View (Control Center & Notifications)
 * - Signature Mega Dual Feature Pills (Internet & Bluetooth) with live status & expandable drawers
 * - Snapdragon 632 60 FPS Locked SmoothEngine & 3GB Low-RAM Hybrid hardware pills
 * - Modular 1x1 Quick Settings Grid (Flashlight with multi-intensity, DND, Rotation, Night Light, Battery Saver, Quick Share, Dark Theme, RAM Flush)
 * - Dual Tactile Expressive Sliders (Brightness with Auto toggle & Dirac HD Volume with Sound mode switcher)
 * - Signature Android 16 Squiggly Waveform Media Player with Device Output Switcher
 * - Fully optimized for SDM632 (Adreno 506): Solid M3 tonal surfaces, 0% GPU blur shader penalty
 */
@Composable
fun QuickSettingsShade(
  viewModel: PixelRomViewModel,
  onDismiss: () -> Unit,
  modifier: Modifier = Modifier
) {
  // Handle back gesture to dismiss Control Center
  BackHandler(onBack = onDismiss)

  val qsTab by viewModel.qsActiveTab.collectAsState()
  val optimization by viewModel.optimizationState.collectAsState()
  val telemetry by viewModel.telemetryState.collectAsState()
  val wifi by viewModel.wifiEnabled.collectAsState()
  val bluetooth by viewModel.bluetoothEnabled.collectAsState()
  val torch by viewModel.torchEnabled.collectAsState()
  val torchLevel by viewModel.torchLevel.collectAsState()
  val dnd by viewModel.dndEnabled.collectAsState()
  val nightLight by viewModel.nightLight.collectAsState()
  val batterySaver by viewModel.batterySaver.collectAsState()
  val quickShare by viewModel.quickShare.collectAsState()
  val autoRotate by viewModel.autoRotate.collectAsState()
  val airplaneMode by viewModel.airplaneMode.collectAsState()
  val isDarkTheme by viewModel.isDarkTheme.collectAsState()
  val brightness by viewModel.brightness.collectAsState()
  val autoBrightness by viewModel.autoBrightness.collectAsState()
  val volume by viewModel.volume.collectAsState()
  val soundMode by viewModel.soundMode.collectAsState()
  val notifications by viewModel.notifications.collectAsState()

  val isPowerMenuVisible by viewModel.isPowerMenuVisible.collectAsState()
  val isNetworkDialogVisible by viewModel.isNetworkDialogVisible.collectAsState()
  val isBluetoothDialogVisible by viewModel.isBluetoothDialogVisible.collectAsState()

  var isMediaPlaying by remember { mutableStateOf(true) }
  val scrollState = rememberScrollState()

  // Full scrim with dismissal
  Box(
    modifier = modifier
      .fillMaxSize()
      .background(Color.Black.copy(alpha = 0.65f))
      .clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = onDismiss
      )
  ) {
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .align(Alignment.TopCenter)
        .clickable(
          interactionSource = remember { MutableInteractionSource() },
          indication = null,
          enabled = true
        ) {}
        .statusBarsPadding()
        .navigationBarsPadding()
        .padding(horizontal = 8.dp, vertical = 4.dp)
        .testTag("quick_settings_shade_card"),
      shape = RoundedCornerShape(32.dp),
      colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surface
      ),
      elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .verticalScroll(scrollState)
          .padding(horizontal = 16.dp, vertical = 12.dp)
      ) {
        // Drag Pill Handle
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onDismiss)
            .padding(bottom = 6.dp),
          contentAlignment = Alignment.Center
        ) {
          Box(
            modifier = Modifier
              .width(46.dp)
              .height(4.dp)
              .clip(CircleShape)
              .background(MaterialTheme.colorScheme.outlineVariant)
          )
        }

        // ==========================================
        // 1. ANDROID 16 STATUS & QUICK ACTION HEADER
        // ==========================================
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "09:41",
              style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                letterSpacing = (-1.0).sp,
                fontSize = 32.sp
              )
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = "Saturday, Oct 3 • Google Fi LTE+",
                style = MaterialTheme.typography.bodySmall.copy(
                  color = MaterialTheme.colorScheme.onSurfaceVariant,
                  fontWeight = FontWeight.Medium,
                  fontSize = 12.sp
                )
              )
            }
          }

          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            // User Google Account Pill
            Surface(
              shape = CircleShape,
              color = MaterialTheme.colorScheme.primaryContainer,
              modifier = Modifier
                .size(36.dp)
                .clickable { viewModel.showToast("Google Account: Harshit (harshitpulkit7ab@gmail.com)") }
            ) {
              Box(contentAlignment = Alignment.Center) {
                Text(
                  text = "H",
                  style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                  )
                )
              }
            }

            // Edit Tiles
            IconButton(
              onClick = { viewModel.showToast("Customize Android 16 Quick Tiles layout") },
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
              Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit Tiles",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(16.dp)
              )
            }

            // Settings
            IconButton(
              onClick = {
                onDismiss()
                viewModel.navigateTo(AppScreen.SETTINGS)
              },
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .testTag("qs_settings_button")
            ) {
              Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(17.dp)
              )
            }

            // Power Menu
            IconButton(
              onClick = { viewModel.togglePowerMenu() },
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .testTag("qs_power_button")
            ) {
              Icon(
                imageVector = Icons.Default.PowerSettingsNew,
                contentDescription = "Power Menu",
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(17.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ==========================================
        // 2. ANDROID 16 TAB SWITCHER (Controls / Notifications)
        // ==========================================
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            .padding(4.dp),
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          // Control Center Tab
          Surface(
            shape = RoundedCornerShape(16.dp),
            color = if (qsTab == QsTab.CONTROLS) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
            modifier = Modifier
              .weight(1f)
              .height(36.dp)
              .clickable { viewModel.setQsTab(QsTab.CONTROLS) }
              .testTag("qs_tab_controls")
          ) {
            Box(contentAlignment = Alignment.Center) {
              Text(
                text = "Control Center",
                style = MaterialTheme.typography.labelMedium.copy(
                  fontWeight = if (qsTab == QsTab.CONTROLS) FontWeight.Bold else FontWeight.Medium,
                  color = if (qsTab == QsTab.CONTROLS) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                )
              )
            }
          }

          // Notifications Tab
          Surface(
            shape = RoundedCornerShape(16.dp),
            color = if (qsTab == QsTab.NOTIFICATIONS) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
            modifier = Modifier
              .weight(1f)
              .height(36.dp)
              .clickable { viewModel.setQsTab(QsTab.NOTIFICATIONS) }
              .testTag("qs_tab_notifications")
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center
            ) {
              Text(
                text = "Notifications",
                style = MaterialTheme.typography.labelMedium.copy(
                  fontWeight = if (qsTab == QsTab.NOTIFICATIONS) FontWeight.Bold else FontWeight.Medium,
                  color = if (qsTab == QsTab.NOTIFICATIONS) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                )
              )
              if (notifications.isNotEmpty()) {
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                  modifier = Modifier
                    .size(18.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                  contentAlignment = Alignment.Center
                ) {
                  Text(
                    text = "${notifications.size}",
                    style = MaterialTheme.typography.labelSmall.copy(
                      fontSize = 10.sp,
                      fontWeight = FontWeight.Bold,
                      color = MaterialTheme.colorScheme.onPrimary
                    )
                  )
                }
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // TAB CONTENT
        if (qsTab == QsTab.CONTROLS) {
          // ==========================================
          // 3A. DUAL SIGNATURE MEGA PILLS (2x1)
          // ==========================================
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            // Internet Pill
            Android16MegaPill(
              title = "Internet",
              subtitle = if (wifi) "Google-Fi 5GHz" else "4G VoLTE (Jio)",
              meta = if (wifi) "866 Mbps" else "12.4 MB/s",
              icon = Icons.Default.Wifi,
              isActive = wifi,
              onToggle = { viewModel.toggleWifi() },
              onExpand = { viewModel.openNetworkDialog() },
              modifier = Modifier.weight(1f),
              testTag = "qs_pill_internet"
            )

            // Bluetooth Pill
            Android16MegaPill(
              title = "Bluetooth",
              subtitle = if (bluetooth) "Pixel Buds Pro 2" else "Off",
              meta = if (bluetooth) "88% • LDAC" else "",
              icon = Icons.Default.Bluetooth,
              isActive = bluetooth,
              onToggle = { viewModel.toggleBluetooth() },
              onExpand = { viewModel.openBluetoothDialog() },
              modifier = Modifier.weight(1f),
              testTag = "qs_pill_bluetooth"
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          // ==========================================
          // 3B. PERFORMANCE & SMOOTH ENGINE PILLS (2x1)
          // ==========================================
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            // SmoothEngine 60 FPS
            Android16MegaPill(
              title = "SmoothEngine",
              subtitle = if (optimization.ultraSmoothMode) "60 FPS Locked" else "Stock Jank",
              meta = "SDM632 HW Compose",
              icon = Icons.Default.Speed,
              isActive = optimization.ultraSmoothMode,
              activeContainer = Color(0xFF1E3A8A),
              onToggle = { viewModel.toggleUltraSmooth() },
              onExpand = {
                onDismiss()
                viewModel.navigateTo(AppScreen.OPTIMIZER)
              },
              modifier = Modifier.weight(1f),
              testTag = "qs_pill_smoothengine"
            )

            // 3GB Low-RAM Hybrid Mode
            Android16MegaPill(
              title = "3GB Low-RAM",
              subtitle = if (optimization.lowRamMode) "-450MB Heap" else "Full GMS Heavy",
              meta = "ZRAM 2GB zstd",
              icon = Icons.Default.Memory,
              isActive = optimization.lowRamMode,
              activeContainer = Color(0xFF065F46),
              onToggle = { viewModel.toggleLowRamMode() },
              onExpand = { viewModel.boostMemoryNow() },
              modifier = Modifier.weight(1f),
              testTag = "qs_pill_low_ram"
            )
          }

          Spacer(modifier = Modifier.height(12.dp))

          // ==========================================
          // 3C. MODULAR 1x1 QUICK ACTION TILES GRID (2x4)
          // ==========================================
          // Row 1
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Android16ModularTile(
              title = "Torch",
              subtitle = if (torch) "Lvl $torchLevel" else "Off",
              icon = Icons.Default.FlashlightOn,
              isActive = torch,
              onClick = { viewModel.cycleTorchLevel() },
              modifier = Modifier.weight(1f)
            )

            Android16ModularTile(
              title = "DND",
              subtitle = if (dnd) "Priority" else "Off",
              icon = Icons.Default.DoNotDisturb,
              isActive = dnd,
              onClick = { viewModel.toggleDnd() },
              modifier = Modifier.weight(1f)
            )

            Android16ModularTile(
              title = "Rotate",
              subtitle = if (autoRotate) "Auto" else "Locked",
              icon = Icons.Default.ScreenRotation,
              isActive = autoRotate,
              onClick = { viewModel.toggleAutoRotate() },
              modifier = Modifier.weight(1f)
            )

            Android16ModularTile(
              title = "Night Light",
              subtitle = if (nightLight) "Amber" else "Off",
              icon = Icons.Default.Bedtime,
              isActive = nightLight,
              onClick = { viewModel.toggleNightLight() },
              modifier = Modifier.weight(1f)
            )
          }

          Spacer(modifier = Modifier.height(8.dp))

          // Row 2
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Android16ModularTile(
              title = "Battery",
              subtitle = if (batterySaver) "Extreme" else "Normal",
              icon = Icons.Default.BatteryAlert,
              isActive = batterySaver,
              onClick = { viewModel.toggleBatterySaver() },
              modifier = Modifier.weight(1f)
            )

            Android16ModularTile(
              title = "Quick Share",
              subtitle = if (quickShare) "Visible" else "Hidden",
              icon = Icons.Default.Share,
              isActive = quickShare,
              onClick = { viewModel.toggleQuickShare() },
              modifier = Modifier.weight(1f)
            )

            Android16ModularTile(
              title = "Dark Theme",
              subtitle = if (isDarkTheme) "On" else "Off",
              icon = Icons.Default.DarkMode,
              isActive = isDarkTheme,
              onClick = { viewModel.toggleDarkTheme() },
              modifier = Modifier.weight(1f)
            )

            Android16ModularTile(
              title = "Clean RAM",
              subtitle = "Free 240M",
              icon = Icons.Default.Bolt,
              isActive = false,
              onClick = { viewModel.boostMemoryNow() },
              modifier = Modifier.weight(1f)
            )
          }

          Spacer(modifier = Modifier.height(14.dp))

          // ==========================================
          // 3D. DUAL TACTILE M3 EXPRESSIVE SLIDERS
          // ==========================================
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            // Brightness Slider with Auto Toggle
            Surface(
              shape = RoundedCornerShape(22.dp),
              color = MaterialTheme.colorScheme.surfaceVariant,
              modifier = Modifier
                .weight(1f)
                .height(52.dp)
            ) {
              Row(
                modifier = Modifier
                  .fillMaxSize()
                  .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = Icons.Default.BrightnessMedium,
                  contentDescription = "Brightness",
                  tint = MaterialTheme.colorScheme.primary,
                  modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Slider(
                  value = brightness,
                  onValueChange = { viewModel.setBrightness(it) },
                  valueRange = 0.1f..1.0f,
                  modifier = Modifier.weight(1f),
                  colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.primary,
                    activeTrackColor = MaterialTheme.colorScheme.primary,
                    inactiveTrackColor = MaterialTheme.colorScheme.outlineVariant
                  )
                )
                Spacer(modifier = Modifier.width(4.dp))
                // Auto Brightness Pill
                Surface(
                  shape = CircleShape,
                  color = if (autoBrightness) MaterialTheme.colorScheme.primary else Color.Transparent,
                  modifier = Modifier
                    .size(24.dp)
                    .clickable { viewModel.toggleAutoBrightness() }
                ) {
                  Box(contentAlignment = Alignment.Center) {
                    Text(
                      text = "A",
                      style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (autoBrightness) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 11.sp
                      )
                    )
                  }
                }
              }
            }

            // Volume Slider with Sound Mode Toggle
            Surface(
              shape = RoundedCornerShape(22.dp),
              color = MaterialTheme.colorScheme.surfaceVariant,
              modifier = Modifier
                .weight(1f)
                .height(52.dp)
            ) {
              Row(
                modifier = Modifier
                  .fillMaxSize()
                  .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = when (soundMode) {
                    "Vibrate" -> Icons.Default.Vibration
                    "Silent" -> Icons.Default.VolumeMute
                    else -> Icons.AutoMirrored.Filled.VolumeUp
                  },
                  contentDescription = "Volume",
                  tint = MaterialTheme.colorScheme.primary,
                  modifier = Modifier
                    .size(18.dp)
                    .clickable { viewModel.cycleSoundMode() }
                )
                Spacer(modifier = Modifier.width(4.dp))
                Slider(
                  value = volume,
                  onValueChange = { viewModel.setVolume(it) },
                  valueRange = 0.0f..1.0f,
                  modifier = Modifier.weight(1f),
                  colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.primary,
                    activeTrackColor = MaterialTheme.colorScheme.primary,
                    inactiveTrackColor = MaterialTheme.colorScheme.outlineVariant
                  )
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = "${(volume * 100).toInt()}%",
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp
                  )
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // ==========================================
          // 3E. ANDROID 16 SQUIGGLY MEDIA PLAYER CARD
          // ==========================================
          Android16SquigglyMediaPlayer(
            isPlaying = isMediaPlaying,
            onPlayPauseToggle = { isMediaPlaying = !isMediaPlaying },
            onNext = { viewModel.showToast("Next track • Dirac HD Sound active") },
            onPrevious = { viewModel.showToast("Previous track") },
            onDeviceOutputClick = { viewModel.showToast("Audio Output: Redmi Y3 3.5mm Jack & Speaker") }
          )

          Spacer(modifier = Modifier.height(12.dp))

          // ==========================================
          // 3F. SYSTEM SPECS FOOTER
          // ==========================================
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "Snapdragon 632 • Adreno 506 (60 FPS)",
              style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            )
            Text(
              text = "OS: 3.2GB / 32GB • 28GB Free",
              style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
              )
            )
          }

        } else {
          // ==========================================
          // NOTIFICATIONS TAB CONTENT
          // ==========================================
          Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            if (notifications.isEmpty()) {
              Box(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 40.dp),
                contentAlignment = Alignment.Center
              ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                  Icon(
                    imageVector = Icons.Default.NotificationsNone,
                    contentDescription = "No notifications",
                    tint = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(48.dp)
                  )
                  Spacer(modifier = Modifier.height(8.dp))
                  Text(
                    text = "No new notifications",
                    style = MaterialTheme.typography.titleMedium.copy(
                      color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                  )
                  Text(
                    text = "You're all caught up with Android 16!",
                    style = MaterialTheme.typography.bodySmall.copy(
                      color = MaterialTheme.colorScheme.outline
                    )
                  )
                }
              }
            } else {
              notifications.forEach { item ->
                Android16NotificationCard(
                  item = item,
                  onDismiss = { viewModel.dismissNotification(item.id) }
                )
              }

              Spacer(modifier = Modifier.height(10.dp))

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                TextButton(
                  onClick = { viewModel.showToast("Notification History opened") }
                ) {
                  Text("History", fontSize = 12.sp)
                }

                Surface(
                  shape = RoundedCornerShape(16.dp),
                  color = MaterialTheme.colorScheme.surfaceVariant,
                  modifier = Modifier.clickable { viewModel.clearAllNotifications() }
                ) {
                  Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Icon(
                      imageVector = Icons.Default.Delete,
                      contentDescription = "Clear all",
                      modifier = Modifier.size(14.dp),
                      tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                      text = "Clear all",
                      style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                    )
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  // ==========================================
  // POWER MENU DIALOG
  // ==========================================
  if (isPowerMenuVisible) {
    Dialog(onDismissRequest = { viewModel.closePowerMenu() }) {
      Card(
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier.padding(20.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(
            text = "Android 16 Power Menu",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
          )
          Spacer(modifier = Modifier.height(16.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
          ) {
            PowerMenuItem(
              title = "Restart",
              icon = Icons.Default.RestartAlt,
              color = MaterialTheme.colorScheme.primary,
              onClick = {
                viewModel.closePowerMenu()
                viewModel.showToast("Rebooting Redmi Y3...")
              }
            )

            PowerMenuItem(
              title = "Power Off",
              icon = Icons.Default.PowerSettingsNew,
              color = MaterialTheme.colorScheme.error,
              onClick = {
                viewModel.closePowerMenu()
                viewModel.showToast("Powering off SDM632...")
              }
            )

            PowerMenuItem(
              title = "Recovery",
              icon = Icons.Default.Tune,
              color = Color(0xFF10B981),
              onClick = {
                viewModel.closePowerMenu()
                onDismiss()
                viewModel.navigateTo(AppScreen.TERMINAL)
              }
            )
          }

          Spacer(modifier = Modifier.height(16.dp))

          TextButton(
            onClick = { viewModel.closePowerMenu() },
            modifier = Modifier.align(Alignment.End)
          ) {
            Text("Cancel")
          }
        }
      }
    }
  }

  // ==========================================
  // INTERNET / WI-FI QUICK DIALOG
  // ==========================================
  if (isNetworkDialogVisible) {
    AlertDialog(
      onDismissRequest = { viewModel.closeNetworkDialog() },
      title = { Text("Internet & Networks") },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Text(
            text = "Wi-Fi (Dual-Band 2.4/5GHz)",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
          )
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(12.dp))
              .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
              .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(Icons.Default.Wifi, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text("Google-Fi 5GHz", fontWeight = FontWeight.Bold, fontSize = 13.sp)
              Text("Connected • 866 Mbps", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Icon(Icons.Default.Check, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
          }

          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "SIM 1 & 2 (Dual VoLTE)",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
          )
          Text(
            text = "• SIM 1: Jio 4G LTE+ (Data Active • VoLTE HD)\n• SIM 2: Airtel 4G (VoLTE Standby)",
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp)
          )
        }
      },
      confirmButton = {
        TextButton(onClick = { viewModel.closeNetworkDialog() }) {
          Text("Done")
        }
      }
    )
  }

  // ==========================================
  // BLUETOOTH QUICK DIALOG
  // ==========================================
  if (isBluetoothDialogVisible) {
    AlertDialog(
      onDismissRequest = { viewModel.closeBluetoothDialog() },
      title = { Text("Connected Devices") },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(12.dp))
              .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
              .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(Icons.Default.Bluetooth, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text("Pixel Buds Pro 2", fontWeight = FontWeight.Bold, fontSize = 13.sp)
              Text("Active • 88% Battery • LDAC 990kbps", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Icon(Icons.Default.Check, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
          }

          Text(
            text = "Audio Codec: LDAC / Qualcomm aptX HD (Active on SDM632)",
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
          )
        }
      },
      confirmButton = {
        TextButton(onClick = { viewModel.closeBluetoothDialog() }) {
          Text("Done")
        }
      }
    )
  }
}

/**
 * Android 16 Signature Mega Pill with interactive toggle + chevron expander.
 */
@Composable
fun Android16MegaPill(
  title: String,
  subtitle: String,
  meta: String,
  icon: ImageVector,
  isActive: Boolean,
  onToggle: () -> Unit,
  onExpand: () -> Unit,
  modifier: Modifier = Modifier,
  activeContainer: Color = MaterialTheme.colorScheme.primaryContainer,
  testTag: String = ""
) {
  Surface(
    shape = RoundedCornerShape(22.dp),
    color = if (isActive) activeContainer else MaterialTheme.colorScheme.surfaceVariant,
    modifier = modifier
      .height(68.dp)
      .clip(RoundedCornerShape(22.dp))
      .clickable(onClick = onToggle)
      .testTag(testTag)
  ) {
    Row(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 10.dp, vertical = 8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(38.dp)
          .clip(CircleShape)
          .background(
            if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
          ),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = title,
          tint = if (isActive) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
          modifier = Modifier.size(20.dp)
        )
      }

      Spacer(modifier = Modifier.width(8.dp))

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = title,
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 13.sp
          ),
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
        Text(
          text = subtitle,
          style = MaterialTheme.typography.bodySmall.copy(
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Medium
          ),
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
        if (meta.isNotEmpty()) {
          Text(
            text = meta,
            style = MaterialTheme.typography.labelSmall.copy(
              fontSize = 9.sp,
              color = MaterialTheme.colorScheme.primary,
              fontWeight = FontWeight.SemiBold
            ),
            maxLines = 1
          )
        }
      }

      // Expand chevron
      Box(
        modifier = Modifier
          .size(28.dp)
          .clip(CircleShape)
          .clickable(onClick = onExpand),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
          contentDescription = "Expand",
          tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
          modifier = Modifier.size(12.dp)
        )
      }
    }
  }
}

/**
 * Android 16 Modular 1x1 Quick Setting Tile.
 */
@Composable
fun Android16ModularTile(
  title: String,
  subtitle: String,
  icon: ImageVector,
  isActive: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    shape = RoundedCornerShape(20.dp),
    color = if (isActive) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
    modifier = modifier
      .height(64.dp)
      .clip(RoundedCornerShape(20.dp))
      .clickable(onClick = onClick)
      .testTag("qs_mod_tile_${title.lowercase().replace(" ", "_")}")
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 6.dp, vertical = 6.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Icon(
        imageVector = icon,
        contentDescription = title,
        tint = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.size(20.dp)
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = title,
        style = MaterialTheme.typography.labelSmall.copy(
          fontSize = 10.sp,
          fontWeight = if (isActive) FontWeight.Bold else FontWeight.Medium,
          color = if (isActive) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Center
      )
      Text(
        text = subtitle,
        style = MaterialTheme.typography.labelSmall.copy(
          fontSize = 8.sp,
          color = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )
    }
  }
}

/**
 * Android 16 Expressive Media Player with the signature animated squiggly wave progress bar.
 */
@Composable
fun Android16SquigglyMediaPlayer(
  isPlaying: Boolean,
  onPlayPauseToggle: () -> Unit,
  onNext: () -> Unit,
  onPrevious: () -> Unit,
  onDeviceOutputClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val infiniteTransition = rememberInfiniteTransition(label = "squiggly_wave")
  val wavePhase by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 6.283f, // 2*PI
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 2000, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "wave_phase"
  )

  Card(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(24.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
    )
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Album art thumbnail
        Box(
          modifier = Modifier
            .size(46.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(
              Brush.linearGradient(
                colors = listOf(Color(0xFF4285F4), Color(0xFF9C27B0))
              )
            ),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.MusicNote,
            contentDescription = "Music",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
          )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = "Baklava Horizon • Official Pixel 16",
            style = MaterialTheme.typography.labelMedium.copy(
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
          Text(
            text = "Google Sound Design • Dirac HD Audio",
            style = MaterialTheme.typography.bodySmall.copy(
              fontSize = 11.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            maxLines = 1
          )
        }

        // Device output switcher pill
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = MaterialTheme.colorScheme.surface,
          modifier = Modifier.clickable(onClick = onDeviceOutputClick)
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(Color(0xFF22C55E))
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "Dirac HD",
              style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
              )
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Iconic Android 16 Squiggly Wave Canvas
      val primaryColor = MaterialTheme.colorScheme.primary
      val trackColor = MaterialTheme.colorScheme.outlineVariant

      Canvas(
        modifier = Modifier
          .fillMaxWidth()
          .height(14.dp)
      ) {
        val width = size.width
        val midY = size.height / 2
        val playedWidth = width * 0.45f

        val path = Path()
        path.moveTo(0f, midY)

        val wavelength = 24f
        val amplitude = if (isPlaying) 3.5f else 0f

        var x = 0f
        while (x <= playedWidth) {
          val y = midY + sin((x / wavelength) * 2 * Math.PI.toFloat() + wavePhase) * amplitude
          path.lineTo(x, y)
          x += 2f
        }

        // Draw played wavy track
        drawPath(
          path = path,
          color = primaryColor,
          style = androidx.compose.ui.graphics.drawscope.Stroke(
            width = 3.5f,
            cap = androidx.compose.ui.graphics.StrokeCap.Round
          )
        )

        // Draw remaining straight track
        drawLine(
          color = trackColor,
          start = Offset(playedWidth, midY),
          end = Offset(width, midY),
          strokeWidth = 3.5f,
          cap = androidx.compose.ui.graphics.StrokeCap.Round
        )

        // Scrubber thumb
        drawCircle(
          color = primaryColor,
          radius = 5.5f,
          center = Offset(playedWidth, midY)
        )
      }

      Spacer(modifier = Modifier.height(4.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text("1:24", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant))
        Text("3:12", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant))
      }

      // Media controls
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(
          onClick = onPrevious,
          modifier = Modifier.size(34.dp)
        ) {
          Icon(Icons.Default.SkipPrevious, contentDescription = "Previous", tint = MaterialTheme.colorScheme.onSurface)
        }

        Spacer(modifier = Modifier.width(16.dp))

        IconButton(
          onClick = onPlayPauseToggle,
          modifier = Modifier
            .size(42.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
        ) {
          Icon(
            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
            contentDescription = "Play/Pause",
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.size(22.dp)
          )
        }

        Spacer(modifier = Modifier.width(16.dp))

        IconButton(
          onClick = onNext,
          modifier = Modifier.size(34.dp)
        ) {
          Icon(Icons.Default.SkipNext, contentDescription = "Next", tint = MaterialTheme.colorScheme.onSurface)
        }
      }
    }
  }
}

/**
 * Android 16 Notification Shade Card.
 */
@Composable
fun Android16NotificationCard(
  item: QsNotificationItem,
  onDismiss: () -> Unit
) {
  Card(
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surfaceVariant
    ),
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = when (item.iconType) {
              "shield" -> Icons.Default.Shield
              "speed" -> Icons.Default.Speed
              "android" -> Icons.Default.AutoAwesome
              else -> Icons.Default.Tune
            },
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = item.appName,
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "• ${item.timestamp}",
            style = MaterialTheme.typography.labelSmall.copy(
              fontSize = 10.sp,
              color = MaterialTheme.colorScheme.outline
            )
          )
        }

        IconButton(
          onClick = onDismiss,
          modifier = Modifier.size(24.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Dismiss",
            tint = MaterialTheme.colorScheme.outline,
            modifier = Modifier.size(14.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(4.dp))

      Text(
        text = item.title,
        style = MaterialTheme.typography.titleSmall.copy(
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )
      )

      Text(
        text = item.message,
        style = MaterialTheme.typography.bodySmall.copy(
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          fontSize = 12.sp
        )
      )
    }
  }
}

@Composable
fun PowerMenuItem(
  title: String,
  icon: ImageVector,
  color: Color,
  onClick: () -> Unit
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
      .clickable(onClick = onClick)
      .padding(8.dp)
  ) {
    Box(
      modifier = Modifier
        .size(52.dp)
        .clip(CircleShape)
        .background(color.copy(alpha = 0.15f)),
      contentAlignment = Alignment.Center
    ) {
      Icon(imageVector = icon, contentDescription = title, tint = color, modifier = Modifier.size(26.dp))
    }
    Spacer(modifier = Modifier.height(6.dp))
    Text(text = title, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold))
  }
}
