package com.example.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Wallpaper
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.RedmiY3Device
import com.example.ui.theme.MonetPalette
import com.example.viewmodel.AppScreen
import com.example.viewmodel.PixelRomViewModel

@Composable
fun PixelSettingsScreen(
  viewModel: PixelRomViewModel,
  modifier: Modifier = Modifier
) {
  // BackHandler to navigate back to Home
  BackHandler(onBack = { viewModel.navigateTo(AppScreen.HOME) })

  val currentPalette by viewModel.currentPalette.collectAsState()
  val isDarkTheme by viewModel.isDarkTheme.collectAsState()
  val telemetry by viewModel.telemetryState.collectAsState()
  val optimization by viewModel.optimizationState.collectAsState()

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
        modifier = Modifier.testTag("settings_back_button")
      ) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowBack,
          contentDescription = "Back",
          tint = MaterialTheme.colorScheme.onSurface
        )
      }

      Text(
        text = "Settings",
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier.weight(1f)
      )
    }

    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      // Search Bar
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("settings_search_card"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search",
            tint = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Spacer(modifier = Modifier.width(12.dp))
          Text(
            text = "Search settings...",
            style = MaterialTheme.typography.bodyMedium.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          )
        }
      }

      // Google Account & Device Profile Card
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .clickable { viewModel.showToast("Google Account: Harshit Pulkit (harshitpulkit7ab@gmail.com)") }
          .testTag("settings_google_account_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
        )
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(48.dp)
              .clip(CircleShape)
              .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "H",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onPrimaryContainer
              )
            )
          }

          Spacer(modifier = Modifier.width(14.dp))

          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "Harshit Pulkit",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
            Text(
              text = "harshitpulkit7ab@gmail.com",
              style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
              )
            )
            Text(
              text = "Google Pixel (Redmi Y3 • SDM632) • Android 16",
              style = MaterialTheme.typography.labelSmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 10.sp
              )
            )
          }
        }
      }

      // Monet Dynamic Color Picker (Wallpaper & Style)
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
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Palette,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(22.dp)
              )
              Spacer(modifier = Modifier.width(10.dp))
              Text(
                text = "Material You Theme Engine",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
              )
            }
            Text(
              text = currentPalette.displayName,
              style = MaterialTheme.typography.labelSmall.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
              )
            )
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Swatches
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            MonetPalette.values().forEach { palette ->
              val isSelected = currentPalette == palette
              Box(
                modifier = Modifier
                  .size(42.dp)
                  .clip(CircleShape)
                  .background(palette.primary)
                  .clickable { viewModel.setPalette(palette) }
                  .padding(3.dp),
                contentAlignment = Alignment.Center
              ) {
                if (isSelected) {
                  Box(
                    modifier = Modifier
                      .size(16.dp)
                      .clip(CircleShape)
                      .background(Color.White)
                  )
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Dark Mode switch
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.DarkMode,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(20.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "Dark Theme",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
              )
            }
            Switch(
              checked = isDarkTheme,
              onCheckedChange = { viewModel.toggleDarkTheme() },
              colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                checkedTrackColor = MaterialTheme.colorScheme.primary
              )
            )
          }
        }
      }

      // Settings Group: System & Performance
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
          text = "Device & Performance",
          style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
          )
        )

        SettingActionRow(
          title = "SmoothEngine 16 Optimizer",
          subtitle = "Adreno 506 GPU Boost, 60 FPS lock, Low-RAM tuner",
          icon = Icons.Default.Speed,
          iconBgColor = Color(0xFF0F766E),
          onClick = { viewModel.navigateTo(AppScreen.OPTIMIZER) }
        )

        SettingActionRow(
          title = "About Redmi Y3",
          subtitle = "Android 16 (Baklava), Kernel 4.9.227, Easter Egg",
          icon = Icons.Default.Info,
          iconBgColor = Color(0xFF6D28D9),
          onClick = { viewModel.navigateTo(AppScreen.ABOUT) }
        )

        SettingActionRow(
          title = "ROM Flasher & Toolkit",
          subtitle = "OrangeFox/TWRP guide, fastboot scripts & build.prop",
          icon = Icons.Default.Smartphone,
          iconBgColor = Color(0xFF1D4ED8),
          onClick = { viewModel.navigateTo(AppScreen.FLASHER) }
        )
      }

      // Standard Android 16 Settings Rows
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
          text = "Standard Controls",
          style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
          )
        )

        SettingActionRow(
          title = "Network & Internet",
          subtitle = "Wi-Fi, Dual 4G VoLTE (SDM632 X9 LTE Modem)",
          icon = Icons.Default.Wifi,
          iconBgColor = Color(0xFF0369A1),
          onClick = { viewModel.showToast("Network: Dual 4G Dual VoLTE configured for onclite") }
        )

        SettingActionRow(
          title = "Battery & Thermal",
          subtitle = "${telemetry.batteryLevel}% • 4000mAh (${telemetry.batteryTempC}°C) • Est. 19 hrs",
          icon = Icons.Default.BatteryChargingFull,
          iconBgColor = Color(0xFF15803D),
          onClick = { viewModel.showToast("Battery: 4000 mAh Li-Po healthy • Adaptive Charging active") }
        )

        SettingActionRow(
          title = "Storage",
          subtitle = "Android 16 System: 5.2GB • Low-RAM trim active",
          icon = Icons.Default.Storage,
          iconBgColor = Color(0xFFB45309),
          onClick = { viewModel.showToast("Storage: System partition optimized for eMMC 5.1") }
        )

        SettingActionRow(
          title = "Sound & Vibration",
          subtitle = "Pixel 16 Haptics, Volume, Dirac Audio HD for Y3",
          icon = Icons.Default.VolumeUp,
          iconBgColor = Color(0xFF475569),
          onClick = { viewModel.showToast("Sound: Dirac Audio Sound Enhancer loaded") }
        )
      }

      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

@Composable
fun SettingActionRow(
  title: String,
  subtitle: String,
  icon: ImageVector,
  iconBgColor: Color,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .clickable(onClick = onClick)
      .testTag("setting_row_${title.lowercase().replace(" ", "_")}"),
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
          .size(40.dp)
          .clip(CircleShape)
          .background(iconBgColor),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = Color.White,
          modifier = Modifier.size(20.dp)
        )
      }

      Spacer(modifier = Modifier.width(14.dp))

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = title,
          style = MaterialTheme.typography.labelLarge.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
        )
        Text(
          text = subtitle,
          style = MaterialTheme.typography.bodySmall.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        )
      }

      Icon(
        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.size(20.dp)
      )
    }
  }
}
