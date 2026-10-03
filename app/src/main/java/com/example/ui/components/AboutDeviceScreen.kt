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
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.viewmodel.AppScreen
import com.example.viewmodel.PixelRomViewModel

@Composable
fun AboutDeviceScreen(
  viewModel: PixelRomViewModel,
  modifier: Modifier = Modifier
) {
  // BackHandler to navigate back to Settings or Home
  BackHandler(onBack = { viewModel.navigateTo(AppScreen.SETTINGS) })

  val optimization by viewModel.optimizationState.collectAsState()
  val telemetry by viewModel.telemetryState.collectAsState()
  val scrollState = rememberScrollState()

  var easterEggTapCount by remember { mutableIntStateOf(0) }

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
        onClick = { viewModel.navigateTo(AppScreen.SETTINGS) },
        modifier = Modifier.testTag("about_back_button")
      ) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowBack,
          contentDescription = "Back",
          tint = MaterialTheme.colorScheme.onSurface
        )
      }

      Text(
        text = "About Redmi Y3",
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier.weight(1f)
      )
    }

    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
        .padding(horizontal = 16.dp, vertical = 8.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      // Hero Device Card
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
      ) {
        Column(
          modifier = Modifier.padding(20.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          // Redmi Y3 Waterdrop Notch Outline Simulation
          Box(
            modifier = Modifier
              .size(72.dp)
              .clip(RoundedCornerShape(20.dp))
              .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Smartphone,
              contentDescription = "Redmi Y3",
              tint = MaterialTheme.colorScheme.primary,
              modifier = Modifier.size(40.dp)
            )
          }

          Spacer(modifier = Modifier.height(12.dp))

          Text(
            text = RedmiY3Device.MODEL_NAME,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
          )

          Text(
            text = "Device Codename: ${RedmiY3Device.CODENAME}",
            style = MaterialTheme.typography.bodyMedium.copy(
              color = MaterialTheme.colorScheme.primary,
              fontWeight = FontWeight.SemiBold
            )
          )

          Spacer(modifier = Modifier.height(8.dp))

          Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surface
          ) {
            Text(
              text = "PixelOS 16 Official • Android 16 (Baklava)",
              style = MaterialTheme.typography.labelSmall.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
              ),
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
          }
        }
      }

      // Android 16 Interactive Card (Tap 3x for Easter Egg)
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .clickable {
            easterEggTapCount++
            if (easterEggTapCount >= 3) {
              easterEggTapCount = 0
              viewModel.triggerEasterEgg()
            } else {
              viewModel.showToast("Tap ${3 - easterEggTapCount} more times for Android 16 Easter Egg!")
            }
          }
          .testTag("android_version_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
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
              .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Android,
              contentDescription = "Android 16",
              tint = MaterialTheme.colorScheme.onPrimary,
              modifier = Modifier.size(28.dp)
            )
          }

          Spacer(modifier = Modifier.width(14.dp))

          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "Android Version",
              style = MaterialTheme.typography.labelMedium.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            )
            Text(
              text = "16 (Baklava)",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
              )
            )
            Text(
              text = "Tap multiple times to launch Easter Egg",
              style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.primary
              )
            )
          }
        }
      }

      // Detailed Specs Group
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
          text = "Software & Security Specifications",
          style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
          )
        )

        SpecInfoItem(
          title = "Android Security Update",
          value = RedmiY3Device.SECURITY_PATCH,
          icon = Icons.Default.Shield
        )

        SpecInfoItem(
          title = "Kernel Version",
          value = RedmiY3Device.KERNEL_VERSION,
          subtitle = "Tuned for Snapdragon 632 schedutil governor",
          icon = Icons.Default.VerifiedUser
        )

        SpecInfoItem(
          title = "Build Number",
          value = RedmiY3Device.BUILD_TAG,
          subtitle = "Userdebug build • SELinux: Enforcing",
          icon = Icons.Default.Info
        )

        SpecInfoItem(
          title = "SmoothEngine 16 Status",
          value = if (optimization.ultraSmoothMode) "Active (60 FPS Locked)" else "Inactive",
          subtitle = "Adreno 506 HW bypass + Low-RAM flag active",
          icon = Icons.Default.Speed
        )
      }

      // Hardware Specs Group
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
          text = "Redmi Y3 Hardware Architecture",
          style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
          )
        )

        SpecInfoItem(
          title = "SoC (System-on-Chip)",
          value = RedmiY3Device.CHIPSET,
          subtitle = RedmiY3Device.CPU_SPEC,
          icon = Icons.Default.Memory
        )

        SpecInfoItem(
          title = "GPU & Driver",
          value = RedmiY3Device.GPU_SPEC,
          subtitle = "Vulkan 1.1 / OpenGL ES 3.2 • SkiaGL Renderer",
          icon = Icons.Default.Speed
        )

        SpecInfoItem(
          title = "RAM & Compressed Swap",
          value = "${optimization.ramVariant} LPDDR3 + 2048MB ZRAM (LZ4)",
          subtitle = "Low Memory Killer tuned for 3GB multitasking on SDM632",
          icon = Icons.Default.Memory
        )

        SpecInfoItem(
          title = "Internal Storage (Ultra-Light)",
          value = "${telemetry.usedStorageGb} GB used of ${RedmiY3Device.STORAGE_SPEC}",
          subtitle = "Light ROM: System + Vendor takes only 3.8GB, leaving 28GB free",
          icon = Icons.Default.Folder
        )

        SpecInfoItem(
          title = "Display Panel",
          value = RedmiY3Device.DISPLAY_SPEC,
          subtitle = "Waterdrop notch IPS LCD • HD+ 720p resolution",
          icon = Icons.Default.Smartphone
        )

        SpecInfoItem(
          title = "Battery Capacity",
          value = RedmiY3Device.BATTERY_SPEC,
          subtitle = "4000mAh battery • ~18-20 hrs estimated endurance",
          icon = Icons.Default.VerifiedUser
        )
      }

      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

@Composable
fun SpecInfoItem(
  title: String,
  value: String,
  icon: ImageVector,
  subtitle: String? = null,
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
          .size(38.dp)
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
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        )
        Text(
          text = value,
          style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
        )
        if (subtitle != null) {
          Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall.copy(
              fontSize = 11.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          )
        }
      }
    }
  }
}
