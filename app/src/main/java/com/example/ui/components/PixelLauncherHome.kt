package com.example.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shop
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.RedmiY3Device
import com.example.viewmodel.AppScreen
import com.example.viewmodel.PixelRomViewModel

data class PixelAppItem(
  val id: String,
  val name: String,
  val icon: ImageVector,
  val badge: String? = null,
  val standardBgColor: Color,
  val targetScreen: AppScreen,
  val actionMessage: String? = null
)

/**
 * Official Google Android 16 (Baklava) Pixel Launcher Home.
 *
 * Fully tuned for Redmi Y3 (SDM632 3GB RAM / 32GB Storage):
 * - Google At a Glance widget with Live Weather, Calendar event, and SmoothEngine 60 FPS chip
 * - Pull down indicator for the Android 16 Control Center
 * - Material You Themed Icons switch (Dynamic Monet vs Full Color)
 * - 5x3 Pixel Grid with official Google & ROM utility apps
 * - Bottom Google Search Bar dock with G logo, Gemini AI, Voice search, and Google Lens
 * - Full interactive Google Pixel App Drawer (Swipe up / tap arrow)
 */
@Composable
fun PixelLauncherHome(
  viewModel: PixelRomViewModel,
  modifier: Modifier = Modifier
) {
  val telemetry by viewModel.telemetryState.collectAsState()
  val optimization by viewModel.optimizationState.collectAsState()
  val isThemedIcons by viewModel.isThemedIcons.collectAsState()
  val isAppDrawerOpen by viewModel.isAppDrawerOpen.collectAsState()

  // Handle back button when App Drawer is open
  if (isAppDrawerOpen) {
    BackHandler(onBack = { viewModel.closeAppDrawer() })
  }

  val allApps = listOf(
    PixelAppItem(
      id = "smooth_engine",
      name = "SmoothEngine",
      icon = Icons.Default.Speed,
      badge = if (optimization.ultraSmoothMode) "60 FPS" else "30-55",
      standardBgColor = Color(0xFF1E3A8A),
      targetScreen = AppScreen.OPTIMIZER
    ),
    PixelAppItem(
      id = "google_phone",
      name = "Phone",
      icon = Icons.Default.Phone,
      standardBgColor = Color(0xFF166534),
      targetScreen = AppScreen.HOME,
      actionMessage = "Google Phone: VoLTE HD Calling ready on SDM632"
    ),
    PixelAppItem(
      id = "google_messages",
      name = "Messages",
      icon = Icons.Default.ChatBubble,
      badge = "RCS",
      standardBgColor = Color(0xFF0284C7),
      targetScreen = AppScreen.HOME,
      actionMessage = "Google Messages: RCS Chat connected"
    ),
    PixelAppItem(
      id = "google_chrome",
      name = "Chrome",
      icon = Icons.Default.Language,
      standardBgColor = Color(0xFFEA580C),
      targetScreen = AppScreen.HOME,
      actionMessage = "Google Chrome: Fast 60 FPS web rendering active"
    ),
    PixelAppItem(
      id = "pixel_camera",
      name = "Camera Go",
      icon = Icons.Default.CameraAlt,
      badge = "32MP",
      standardBgColor = Color(0xFF7C3AED),
      targetScreen = AppScreen.OPTIMIZER,
      actionMessage = "Pixel Camera Go: Samsung 32MP HDR & 12MP Sony tuned"
    ),
    PixelAppItem(
      id = "pixel_photos",
      name = "Photos",
      icon = Icons.Default.Photo,
      standardBgColor = Color(0xFF059669),
      targetScreen = AppScreen.HOME,
      actionMessage = "Google Photos: Unlimited storage backup profile"
    ),
    PixelAppItem(
      id = "pixel_settings",
      name = "Settings",
      icon = Icons.Default.Settings,
      standardBgColor = MaterialTheme.colorScheme.surfaceVariant,
      targetScreen = AppScreen.SETTINGS
    ),
    PixelAppItem(
      id = "rom_flasher",
      name = "ROM Flasher",
      icon = Icons.Default.Build,
      badge = "onclite",
      standardBgColor = Color(0xFF0E7490),
      targetScreen = AppScreen.FLASHER
    ),
    PixelAppItem(
      id = "flash_terminal",
      name = "Recovery",
      icon = Icons.Default.Code,
      badge = "Fastboot",
      standardBgColor = Color(0xFF065F46),
      targetScreen = AppScreen.TERMINAL
    ),
    PixelAppItem(
      id = "about_phone",
      name = "About Phone",
      icon = Icons.Default.Info,
      badge = "Android 16",
      standardBgColor = Color(0xFF4338CA),
      targetScreen = AppScreen.ABOUT
    ),
    PixelAppItem(
      id = "files_google",
      name = "Files",
      icon = Icons.Default.Folder,
      badge = "32GB",
      standardBgColor = Color(0xFF0284C7),
      targetScreen = AppScreen.SETTINGS,
      actionMessage = "Files by Google: 28.2GB free storage available"
    ),
    PixelAppItem(
      id = "play_store",
      name = "Play Store",
      icon = Icons.Default.Shop,
      standardBgColor = Color(0xFF15803D),
      targetScreen = AppScreen.HOME,
      actionMessage = "Google Play: System updates certified for Android 16"
    ),
    PixelAppItem(
      id = "clock_app",
      name = "Clock",
      icon = Icons.Default.AccessTime,
      standardBgColor = Color(0xFF334155),
      targetScreen = AppScreen.HOME,
      actionMessage = "Google Clock: Alarm & Bedtime timer ready"
    ),
    PixelAppItem(
      id = "calculator_app",
      name = "Calculator",
      icon = Icons.Default.Calculate,
      standardBgColor = Color(0xFF475569),
      targetScreen = AppScreen.HOME,
      actionMessage = "Google Calculator: Material 3 Expressive edition"
    ),
    PixelAppItem(
      id = "youtube_app",
      name = "YouTube",
      icon = Icons.Default.PlayArrow,
      standardBgColor = Color(0xFFDC2626),
      targetScreen = AppScreen.HOME,
      actionMessage = "YouTube: 720p60 hardware VP9 decoder on SDM632"
    )
  )

  Box(modifier = modifier.fillMaxSize()) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .navigationBarsPadding()
        .padding(horizontal = 16.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // ==========================================
      // 1. TOP PIXEL STATUS BAR & PULL-DOWN HANDLE
      // ==========================================
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 6.dp, bottom = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "Google Pixel",
            style = MaterialTheme.typography.labelLarge.copy(
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.primary
            )
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "• Redmi Y3 (${RedmiY3Device.CODENAME})",
            style = MaterialTheme.typography.labelSmall.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          )
        }

        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          // Quick Settings Pull-down button
          Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier
              .clickable { viewModel.toggleQuickSettings() }
              .testTag("quick_settings_button")
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.Tune,
                contentDescription = "Control Center",
                modifier = Modifier.size(14.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "Control Center",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onPrimaryContainer
                )
              )
            }
          }

          Icon(
            imageVector = Icons.Default.Wifi,
            contentDescription = "Wi-Fi",
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.onSurface
          )

          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.BatteryChargingFull,
              contentDescription = "Battery",
              modifier = Modifier.size(16.dp),
              tint = MaterialTheme.colorScheme.primary
            )
            Text(
              text = "${telemetry.batteryLevel}%",
              style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
            )
          }
        }
      }

      // Swipe down drag bar
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clickable { viewModel.toggleQuickSettings() }
          .padding(vertical = 4.dp),
        contentAlignment = Alignment.Center
      ) {
        Box(
          modifier = Modifier
            .width(48.dp)
            .height(4.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.outlineVariant)
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      // ==========================================
      // 2. OFFICIAL GOOGLE "AT A GLANCE" WIDGET
      // ==========================================
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .clickable { viewModel.navigateTo(AppScreen.OPTIMIZER) }
          .testTag("at_a_glance_card"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
        )
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
          ) {
            Column {
              Text(
                text = "Saturday, Oct 3",
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurface
                )
              )
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 2.dp)
              ) {
                Icon(
                  imageVector = Icons.Default.WbSunny,
                  contentDescription = "Weather",
                  tint = Color(0xFFF59E0B),
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "26°C Mostly Sunny • Bangalore",
                  style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Medium
                  )
                )
              }
            }

            // Live 60 FPS Locked Badge
            Surface(
              shape = RoundedCornerShape(12.dp),
              color = if (optimization.ultraSmoothMode) Color(0xFF0F5132) else Color(0xFF78350F)
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Box(
                  modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(if (optimization.ultraSmoothMode) Color(0xFF22C55E) else Color(0xFFFBBF24))
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "${telemetry.currentFps} FPS",
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                  )
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          // Smart Space Chips
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            // Calendar Chip
            Surface(
              shape = RoundedCornerShape(12.dp),
              color = MaterialTheme.colorScheme.surface,
              modifier = Modifier.weight(1f)
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(Icons.Default.AccessTime, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = "11:30 AM • Android 16 Rollout",
                  style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                  maxLines = 1,
                  overflow = TextOverflow.Ellipsis
                )
              }
            }

            // Buds Chip
            Surface(
              shape = RoundedCornerShape(12.dp),
              color = MaterialTheme.colorScheme.surface,
              modifier = Modifier.clickable { viewModel.showToast("Pixel Buds Pro 2: Connected • LDAC") }
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(Icons.Default.Headphones, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = "Buds: 88%",
                  style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, fontWeight = FontWeight.Bold)
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // ==========================================
      // 3. APPS GRID HEADER & THEMED ICONS SWITCH
      // ==========================================
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Pixel 16 Apps",
          style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
        )

        // Themed Icons toggle button
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = MaterialTheme.colorScheme.surfaceVariant,
          modifier = Modifier.clickable { viewModel.toggleThemedIcons() }
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.AutoAwesome,
              contentDescription = "Themed Icons",
              tint = if (isThemedIcons) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
              modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = if (isThemedIcons) "Themed Icons ON" else "Standard Icons",
              style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (isThemedIcons) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
              )
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      // ==========================================
      // 4. MAIN APPS GRID (5 Columns)
      // ==========================================
      LazyVerticalGrid(
        columns = GridCells.Fixed(5),
        modifier = Modifier.weight(1f),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        items(allApps.take(15)) { app ->
          PixelAppIcon(
            item = app,
            isThemed = isThemedIcons,
            onClick = {
              if (app.actionMessage != null) {
                viewModel.showToast(app.actionMessage)
              }
              if (app.targetScreen != AppScreen.HOME) {
                viewModel.navigateTo(app.targetScreen)
              }
            }
          )
        }
      }

      // ==========================================
      // 5. APP DRAWER EXPAND HANDLE
      // ==========================================
      Row(
        modifier = Modifier
          .clickable { viewModel.openAppDrawer() }
          .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = Icons.Default.KeyboardArrowUp,
          contentDescription = "Open App Drawer",
          tint = MaterialTheme.colorScheme.onSurfaceVariant,
          modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = "App Drawer",
          style = MaterialTheme.typography.labelSmall.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Medium
          )
        )
      }

      // ==========================================
      // 6. OFFICIAL GOOGLE SEARCH BAR DOCK
      // ==========================================
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp)
          .clickable { viewModel.openAppDrawer() }
          .testTag("google_search_dock"),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 10.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Google 4-Color 'G' Logo
          Box(
            modifier = Modifier
              .size(32.dp)
              .clip(CircleShape)
              .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "G",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Black,
                color = Color(0xFF4285F4)
              )
            )
          }

          Spacer(modifier = Modifier.width(12.dp))

          Text(
            text = "Search phone, apps, and web...",
            style = MaterialTheme.typography.bodyMedium.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              fontSize = 13.sp
            ),
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )

          Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            // Gemini AI Sparkle
            IconButton(
              onClick = { viewModel.showToast("Google Gemini AI: Built into Android 16") },
              modifier = Modifier.size(32.dp)
            ) {
              Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = "Gemini",
                tint = Color(0xFF38BDF8),
                modifier = Modifier.size(16.dp)
              )
            }

            // Voice Search
            IconButton(
              onClick = { viewModel.showToast("Voice Search: Listening...") },
              modifier = Modifier.size(32.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Mic,
                contentDescription = "Voice",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(16.dp)
              )
            }

            // Google Lens
            IconButton(
              onClick = { viewModel.showToast("Google Lens: SDM632 AI Camera Scanner") },
              modifier = Modifier.size(32.dp)
            ) {
              Icon(
                imageVector = Icons.Default.CameraAlt,
                contentDescription = "Lens",
                tint = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.size(16.dp)
              )
            }
          }
        }
      }

      // Gesture Navigation Pill
      Box(
        modifier = Modifier
          .padding(bottom = 6.dp)
          .width(72.dp)
          .height(5.dp)
          .clip(CircleShape)
          .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f))
      )
    }

    // ==========================================
    // 7. EXPANDABLE GOOGLE PIXEL APP DRAWER MODAL
    // ==========================================
    AnimatedVisibility(
      visible = isAppDrawerOpen,
      enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
      exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
    ) {
      PixelAppDrawerSheet(
        allApps = allApps,
        isThemedIcons = isThemedIcons,
        onClose = { viewModel.closeAppDrawer() },
        onAppClick = { app ->
          viewModel.closeAppDrawer()
          if (app.actionMessage != null) {
            viewModel.showToast(app.actionMessage)
          }
          if (app.targetScreen != AppScreen.HOME) {
            viewModel.navigateTo(app.targetScreen)
          }
        }
      )
    }
  }
}

/**
 * Android 16 Pixel App Icon Cell with support for Material You Themed Monochromatic mode.
 */
@Composable
fun PixelAppIcon(
  item: PixelAppItem,
  isThemed: Boolean,
  onClick: () -> Unit
) {
  val iconBg = if (isThemed) {
    MaterialTheme.colorScheme.primaryContainer
  } else {
    item.standardBgColor
  }

  val iconTint = if (isThemed) {
    MaterialTheme.colorScheme.onPrimaryContainer
  } else {
    Color.White
  }

  Column(
    modifier = Modifier
      .clip(RoundedCornerShape(14.dp))
      .clickable(onClick = onClick)
      .padding(4.dp)
      .testTag("app_cell_${item.id}"),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Box(
      modifier = Modifier
        .size(48.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(iconBg),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = item.icon,
        contentDescription = item.name,
        tint = iconTint,
        modifier = Modifier.size(24.dp)
      )

      if (item.badge != null) {
        Box(
          modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(1.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(Color.Black.copy(alpha = 0.8f))
            .padding(horizontal = 3.dp, vertical = 1.dp)
        ) {
          Text(
            text = item.badge,
            style = MaterialTheme.typography.labelSmall.copy(
              fontSize = 7.sp,
              color = Color.White,
              fontWeight = FontWeight.Bold
            )
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(4.dp))

    Text(
      text = item.name,
      style = MaterialTheme.typography.labelSmall.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp
      ),
      maxLines = 1,
      overflow = TextOverflow.Ellipsis,
      textAlign = TextAlign.Center
    )
  }
}

/**
 * Full A-Z Searchable Google Pixel App Drawer.
 */
@Composable
fun PixelAppDrawerSheet(
  allApps: List<PixelAppItem>,
  isThemedIcons: Boolean,
  onClose: () -> Unit,
  onAppClick: (PixelAppItem) -> Unit
) {
  var searchQuery by remember { mutableStateOf("") }
  val filteredApps = remember(searchQuery, allApps) {
    if (searchQuery.isBlank()) {
      allApps
    } else {
      allApps.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }
  }

  Card(
    modifier = Modifier
      .fillMaxSize()
      .statusBarsPadding()
      .navigationBarsPadding(),
    shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
      // Top Drag Handle & Close button
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "All Apps (Android 16)",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        IconButton(onClick = onClose) {
          Icon(Icons.Default.Close, contentDescription = "Close Drawer")
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      // Search field
      OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = { Text("Search ${allApps.size} apps...", fontSize = 13.sp) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
          unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        singleLine = true
      )

      Spacer(modifier = Modifier.height(14.dp))

      // App list grid
      LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier.weight(1f),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        items(filteredApps) { app ->
          PixelAppIcon(
            item = app,
            isThemed = isThemedIcons,
            onClick = { onAppClick(app) }
          )
        }
      }
    }
  }
}
