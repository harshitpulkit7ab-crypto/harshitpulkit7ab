package com.example.viewmodel

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.FlashingStep
import com.example.model.PerformanceProfile
import com.example.model.QsNotificationItem
import com.example.model.QsTab
import com.example.model.RedmiY3Device
import com.example.model.RomOptimizationState
import com.example.model.TelemetryState
import com.example.ui.theme.MonetPalette
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

enum class AppScreen {
  HOME,
  OPTIMIZER,
  SETTINGS,
  ABOUT,
  FLASHER,
  TERMINAL
}

class PixelRomViewModel : ViewModel() {

  private val _optimizationState = MutableStateFlow(RomOptimizationState())
  val optimizationState: StateFlow<RomOptimizationState> = _optimizationState.asStateFlow()

  private val _telemetryState = MutableStateFlow(TelemetryState())
  val telemetryState: StateFlow<TelemetryState> = _telemetryState.asStateFlow()

  private val _currentScreen = MutableStateFlow(AppScreen.HOME)
  val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

  private val _isQuickSettingsOpen = MutableStateFlow(false)
  val isQuickSettingsOpen: StateFlow<Boolean> = _isQuickSettingsOpen.asStateFlow()

  private val _qsActiveTab = MutableStateFlow(QsTab.CONTROLS)
  val qsActiveTab: StateFlow<QsTab> = _qsActiveTab.asStateFlow()

  private val _currentPalette = MutableStateFlow(MonetPalette.PixelBlue)
  val currentPalette: StateFlow<MonetPalette> = _currentPalette.asStateFlow()

  private val _isDarkTheme = MutableStateFlow(true)
  val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

  private val _isThemedIcons = MutableStateFlow(true)
  val isThemedIcons: StateFlow<Boolean> = _isThemedIcons.asStateFlow()

  private val _isAppDrawerOpen = MutableStateFlow(false)
  val isAppDrawerOpen: StateFlow<Boolean> = _isAppDrawerOpen.asStateFlow()

  private val _isPowerMenuVisible = MutableStateFlow(false)
  val isPowerMenuVisible: StateFlow<Boolean> = _isPowerMenuVisible.asStateFlow()

  private val _isNetworkDialogVisible = MutableStateFlow(false)
  val isNetworkDialogVisible: StateFlow<Boolean> = _isNetworkDialogVisible.asStateFlow()

  private val _isBluetoothDialogVisible = MutableStateFlow(false)
  val isBluetoothDialogVisible: StateFlow<Boolean> = _isBluetoothDialogVisible.asStateFlow()

  private val _isEasterEggVisible = MutableStateFlow(false)
  val isEasterEggVisible: StateFlow<Boolean> = _isEasterEggVisible.asStateFlow()

  // Quick settings toggles (Android 16 Control Center)
  private val _wifiEnabled = MutableStateFlow(true)
  val wifiEnabled: StateFlow<Boolean> = _wifiEnabled.asStateFlow()

  private val _bluetoothEnabled = MutableStateFlow(true)
  val bluetoothEnabled: StateFlow<Boolean> = _bluetoothEnabled.asStateFlow()

  private val _torchEnabled = MutableStateFlow(false)
  val torchEnabled: StateFlow<Boolean> = _torchEnabled.asStateFlow()

  private val _torchLevel = MutableStateFlow(2) // 1, 2, 3
  val torchLevel: StateFlow<Int> = _torchLevel.asStateFlow()

  private val _dndEnabled = MutableStateFlow(false)
  val dndEnabled: StateFlow<Boolean> = _dndEnabled.asStateFlow()

  private val _nightLight = MutableStateFlow(false)
  val nightLight: StateFlow<Boolean> = _nightLight.asStateFlow()

  private val _batterySaver = MutableStateFlow(false)
  val batterySaver: StateFlow<Boolean> = _batterySaver.asStateFlow()

  private val _quickShare = MutableStateFlow(true)
  val quickShare: StateFlow<Boolean> = _quickShare.asStateFlow()

  private val _brightness = MutableStateFlow(0.75f)
  val brightness: StateFlow<Float> = _brightness.asStateFlow()

  private val _autoBrightness = MutableStateFlow(true)
  val autoBrightness: StateFlow<Boolean> = _autoBrightness.asStateFlow()

  private val _volume = MutableStateFlow(0.65f)
  val volume: StateFlow<Float> = _volume.asStateFlow()

  private val _soundMode = MutableStateFlow("Ring") // "Ring", "Vibrate", "Silent"
  val soundMode: StateFlow<String> = _soundMode.asStateFlow()

  private val _autoRotate = MutableStateFlow(false)
  val autoRotate: StateFlow<Boolean> = _autoRotate.asStateFlow()

  private val _airplaneMode = MutableStateFlow(false)
  val airplaneMode: StateFlow<Boolean> = _airplaneMode.asStateFlow()

  private val _hotspot = MutableStateFlow(false)
  val hotspot: StateFlow<Boolean> = _hotspot.asStateFlow()

  // Android 16 Notifications in Control Center Shade
  private val _notifications = MutableStateFlow<List<QsNotificationItem>>(
    listOf(
      QsNotificationItem(
        id = "notif_1",
        appName = "Google Play Protect",
        title = "No harmful apps detected",
        message = "Daily on-device machine learning scan of 48 packages completed. Device certified.",
        timestamp = "Just now",
        category = "SECURITY",
        iconType = "shield"
      ),
      QsNotificationItem(
        id = "notif_2",
        appName = "SmoothEngine 16",
        title = "SDM632 locked at 60.0 FPS",
        message = "Adreno 506 hardware composition active. Window blurs bypassed. 0 frames dropped.",
        timestamp = "5m ago",
        category = "PERFORMANCE",
        iconType = "speed"
      ),
      QsNotificationItem(
        id = "notif_3",
        appName = "System Update",
        title = "Pixel 16 Feature Drop active",
        message = "Official Google Android 16 (Baklava) build BP1A.261003.001.A1 onclite running.",
        timestamp = "18m ago",
        category = "SYSTEM",
        iconType = "android"
      ),
      QsNotificationItem(
        id = "notif_4",
        appName = "Messages • Harshit",
        title = "Harshit Pulkit",
        message = "The new Android 16 control center is so fluid on this 3GB RAM device!",
        timestamp = "32m ago",
        category = "COMMUNICATION",
        iconType = "message"
      )
    )
  )
  val notifications: StateFlow<List<QsNotificationItem>> = _notifications.asStateFlow()

  // Flashing Terminal Simulation
  private val _terminalLogs = MutableStateFlow<List<String>>(emptyList())
  val terminalLogs: StateFlow<List<String>> = _terminalLogs.asStateFlow()

  private val _isFlashingRunning = MutableStateFlow(false)
  val isFlashingRunning: StateFlow<Boolean> = _isFlashingRunning.asStateFlow()

  private val _flashingProgress = MutableStateFlow(0f)
  val flashingProgress: StateFlow<Float> = _flashingProgress.asStateFlow()

  private val _toastMessage = MutableStateFlow<String?>(null)
  val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

  init {
    startTelemetrySimulation()
  }

  fun navigateTo(screen: AppScreen) {
    _currentScreen.value = screen
    _isQuickSettingsOpen.value = false
  }

  fun toggleQuickSettings() {
    _isQuickSettingsOpen.update { !it }
  }

  fun closeQuickSettings() {
    _isQuickSettingsOpen.value = false
  }

  fun setPalette(palette: MonetPalette) {
    _currentPalette.value = palette
    showToast("Applied ${palette.displayName} dynamic theme")
  }

  fun toggleDarkTheme() {
    _isDarkTheme.update { !it }
  }

  fun setBrightness(value: Float) {
    _brightness.value = value.coerceIn(0.1f, 1.0f)
  }

  fun setVolume(value: Float) {
    _volume.value = value.coerceIn(0.0f, 1.0f)
  }

  fun toggleAutoRotate() {
    _autoRotate.update { !it }
    showToast(if (_autoRotate.value) "Auto-rotate ON" else "Portrait Locked")
  }

  fun toggleAirplaneMode() {
    _airplaneMode.update { !it }
    showToast(if (_airplaneMode.value) "Airplane Mode ON" else "Airplane Mode OFF")
  }

  fun toggleHotspot() {
    _hotspot.update { !it }
    showToast(if (_hotspot.value) "Personal Hotspot ON" else "Hotspot OFF")
  }

  fun boostMemoryNow() {
    _telemetryState.update { current ->
      val cleaned = (current.usedRamMb - 240).coerceAtLeast(980)
      current.copy(
        usedRamMb = cleaned,
        cpuLoadPercent = (current.cpuLoadPercent - 8).coerceAtLeast(10),
        zramUsedMb = (current.zramUsedMb - 60).coerceAtLeast(120)
      )
    }
    showToast("⚡ Quick Boost: Freed 240MB RAM • SDM632 Cache Cleared")
  }

  fun toggleWifi() {
    _wifiEnabled.update { !it }
  }

  fun toggleBluetooth() {
    _bluetoothEnabled.update { !it }
  }

  fun toggleTorch() {
    _torchEnabled.update { !it }
  }

  fun setQsTab(tab: QsTab) {
    _qsActiveTab.value = tab
  }

  fun dismissNotification(id: String) {
    _notifications.update { list -> list.filterNot { it.id == id } }
  }

  fun clearAllNotifications() {
    _notifications.value = emptyList()
    showToast("All notifications cleared")
  }

  fun toggleNightLight() {
    _nightLight.update { !it }
    showToast(if (_nightLight.value) "Night Light: Amber Eye Protection ON" else "Night Light OFF")
  }

  fun toggleBatterySaver() {
    _batterySaver.update { !it }
    showToast(if (_batterySaver.value) "Extreme Battery Saver: 4000mAh calibrated for 52h" else "Battery Saver OFF")
  }

  fun toggleQuickShare() {
    _quickShare.update { !it }
    showToast(if (_quickShare.value) "Quick Share: Visible to all contacts" else "Quick Share: Hidden")
  }

  fun toggleAutoBrightness() {
    _autoBrightness.update { !it }
    showToast(if (_autoBrightness.value) "Adaptive Brightness ON" else "Adaptive Brightness Manual")
  }

  fun cycleSoundMode() {
    val nextMode = when (_soundMode.value) {
      "Ring" -> "Vibrate"
      "Vibrate" -> "Silent"
      else -> "Ring"
    }
    _soundMode.value = nextMode
    showToast("Sound Profile: $nextMode (Dirac HD)")
  }

  fun cycleTorchLevel() {
    if (!_torchEnabled.value) {
      _torchEnabled.value = true
      _torchLevel.value = 1
      showToast("Flashlight: Level 1 (Low)")
    } else {
      val next = _torchLevel.value + 1
      if (next > 3) {
        _torchEnabled.value = false
        _torchLevel.value = 1
        showToast("Flashlight OFF")
      } else {
        _torchLevel.value = next
        showToast("Flashlight: Level $next (${if (next == 2) "Medium" else "High Beam"})")
      }
    }
  }

  fun toggleThemedIcons() {
    _isThemedIcons.update { !it }
    showToast(if (_isThemedIcons.value) "Themed Icons (Monet Dynamic) ON" else "Full Color Google Icons")
  }

  fun openAppDrawer() {
    _isAppDrawerOpen.value = true
  }

  fun closeAppDrawer() {
    _isAppDrawerOpen.value = false
  }

  fun togglePowerMenu() {
    _isPowerMenuVisible.update { !it }
  }

  fun closePowerMenu() {
    _isPowerMenuVisible.value = false
  }

  fun openNetworkDialog() {
    _isNetworkDialogVisible.value = true
  }

  fun closeNetworkDialog() {
    _isNetworkDialogVisible.value = false
  }

  fun openBluetoothDialog() {
    _isBluetoothDialogVisible.value = true
  }

  fun closeBluetoothDialog() {
    _isBluetoothDialogVisible.value = false
  }

  fun toggleDnd() {
    _dndEnabled.update { !it }
    showToast(if (_dndEnabled.value) "Do Not Disturb: Priority Only" else "DND OFF")
  }

  fun triggerEasterEgg() {
    _isEasterEggVisible.value = true
  }

  fun dismissEasterEgg() {
    _isEasterEggVisible.value = false
  }

  fun applyProfile(profile: PerformanceProfile) {
    _optimizationState.update { current ->
      when (profile) {
        PerformanceProfile.ULTRA_SMOOTH -> current.copy(
          selectedProfile = profile,
          ultraSmoothMode = true,
          lowRamMode = true,
          zramEnabled = true,
          adrenoBoost = true,
          disableWindowBlurs = true,
          animationScale = 0.5f,
          aggressiveLmk = true
        )
        PerformanceProfile.PIXEL_BALANCED -> current.copy(
          selectedProfile = profile,
          ultraSmoothMode = false,
          lowRamMode = false,
          zramEnabled = true,
          adrenoBoost = false,
          disableWindowBlurs = false,
          animationScale = 1.0f,
          aggressiveLmk = false
        )
        PerformanceProfile.BATTERY_SAVER -> current.copy(
          selectedProfile = profile,
          ultraSmoothMode = true,
          lowRamMode = true,
          zramEnabled = true,
          adrenoBoost = false,
          disableWindowBlurs = true,
          animationScale = 0.5f,
          aggressiveLmk = true
        )
        PerformanceProfile.GAMING_ADRENO -> current.copy(
          selectedProfile = profile,
          ultraSmoothMode = true,
          lowRamMode = false,
          zramEnabled = true,
          adrenoBoost = true,
          disableWindowBlurs = true,
          animationScale = 0.75f,
          aggressiveLmk = true
        )
      }
    }
    showToast("Activated ${profile.title}")
  }

  fun toggleUltraSmooth() {
    _optimizationState.update { it.copy(ultraSmoothMode = !it.ultraSmoothMode) }
    val state = _optimizationState.value.ultraSmoothMode
    showToast(if (state) "Ultra-Smooth Mode ON: 0.5x Scale & Adreno Overlay Bypass" else "Ultra-Smooth Mode OFF")
  }

  fun toggleLowRamMode() {
    _optimizationState.update { it.copy(lowRamMode = !it.lowRamMode) }
    val isEnabled = _optimizationState.value.lowRamMode
    _telemetryState.update { current ->
      current.copy(
        usedRamMb = if (isEnabled) (current.usedRamMb - 320).coerceAtLeast(1100) else current.usedRamMb + 320
      )
    }
    showToast(if (isEnabled) "Low-RAM Hybrid Flag ON: Saved 320MB System RAM" else "Low-RAM Flag OFF")
  }

  fun toggleZram() {
    _optimizationState.update { it.copy(zramEnabled = !it.zramEnabled) }
    showToast(if (_optimizationState.value.zramEnabled) "ZRAM LZ4 2048MB Allocated" else "ZRAM Disabled")
  }

  fun toggleAdrenoBoost() {
    _optimizationState.update { it.copy(adrenoBoost = !it.adrenoBoost) }
    showToast(if (_optimizationState.value.adrenoBoost) "Adreno 506 GPU Rendering Boost Enabled" else "Adreno 506 Boost Disabled")
  }

  fun toggleDisableWindowBlurs() {
    _optimizationState.update { it.copy(disableWindowBlurs = !it.disableWindowBlurs) }
    showToast(if (_optimizationState.value.disableWindowBlurs) "Window Blurs Disabled (Adreno 506 60fps locked)" else "Window Blurs Enabled")
  }

  fun toggleAggressiveLmk() {
    _optimizationState.update { it.copy(aggressiveLmk = !it.aggressiveLmk) }
    showToast(if (_optimizationState.value.aggressiveLmk) "LMKD Aggressive Eviction Active" else "Default LMKD")
  }

  fun setRamVariant(variant: String) {
    val total = if (variant == "4GB") 4096 else 3072
    _optimizationState.update { it.copy(ramVariant = variant) }
    _telemetryState.update { it.copy(totalRamMb = total) }
    showToast("Redmi Y3 Model set to $variant RAM")
  }

  fun setAnimationScale(scale: Float) {
    _optimizationState.update { it.copy(animationScale = scale) }
    showToast("Animation duration scale: ${scale}x")
  }

  fun showToast(msg: String) {
    _toastMessage.value = msg
    viewModelScope.launch {
      delay(2500)
      if (_toastMessage.value == msg) {
        _toastMessage.value = null
      }
    }
  }

  fun copyToClipboard(context: Context, label: String, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, text)
    clipboard.setPrimaryClip(clip)
    showToast("Copied $label to clipboard!")
  }

  fun startSimulatedFlash() {
    if (_isFlashingRunning.value) return
    _isFlashingRunning.value = true
    _flashingProgress.value = 0f
    _terminalLogs.value = emptyList()

    viewModelScope.launch {
      val logs = listOf(
        ">> OrangeFox Recovery vR11.1_4 [onclite] initialized",
        ">> Checking device asserts: ro.product.device == 'onclite' || 'onc'",
        ">> Found Qualcomm Snapdragon 632 (SDM632) revision: verified",
        ">> Mounting partitions: /system, /vendor, /boot, /data... [OK]",
        ">> Formatting partitions for clean Android 16 install...",
        ">> Wiping Dalvik / ART Cache... [OK]",
        ">> Flashing PixelOS-16.0-UNOFFICIAL-onclite-20260921-GAPPS.zip...",
        ">> Unpacking Android 16 (Baklava) system.new.dat.br...",
        ">> Applying Redmi Y3 Low-Spec SmoothEngine patches:",
        "   + ro.config.low_ram=true (Saves 450MB system RAM)",
        "   + debug.sf.disable_hw_overlays=1 (Adreno 506 GPU compositor)",
        "   + ro.surface_flinger.supports_background_blur=0 (Prevents frame drops)",
        "   + persist.sys.zram.size=2048M (LZ4 compression algorithm)",
        "   + schedutil up_rate_limit_us=500 (Instant touch ramp)",
        ">> Patching Kernel: 4.9.227-perf+ onclite-smooth-sdm632... [OK]",
        ">> Integrating Google Play Services (Core Minimal GMS)... [OK]",
        ">> Setting up SELinux Enforcing rules for onclite... [OK]",
        ">> Verifying boot image SHA256 integrity... MATCH",
        ">> Flashing completed successfully in 34.8 seconds!",
        ">> Rebooting to Android 16 (Baklava) Pixel Experience..."
      )

      for (i in logs.indices) {
        _terminalLogs.update { it + logs[i] }
        _flashingProgress.value = (i + 1).toFloat() / logs.size.toFloat()
        delay(Random.nextLong(200, 500))
      }

      _isFlashingRunning.value = false
      showToast("PixelOS 16 for Redmi Y3 installed successfully!")
    }
  }

  private fun startTelemetrySimulation() {
    viewModelScope.launch {
      while (true) {
        delay(1200)
        val opts = _optimizationState.value
        val baseFps = if (opts.ultraSmoothMode) 60f else (56f + Random.nextFloat() * 4f)
        val jitter = if (opts.ultraSmoothMode) 0f else (Random.nextFloat() * 2f - 1f)
        val fps = (baseFps + jitter).coerceIn(50f, 60f)

        val baseFreq = when (opts.selectedProfile) {
          PerformanceProfile.GAMING_ADRENO -> 1804
          PerformanceProfile.BATTERY_SAVER -> 1190
          PerformanceProfile.ULTRA_SMOOTH -> 1401
          PerformanceProfile.PIXEL_BALANCED -> 1536
        }
        val freqJitter = Random.nextInt(-40, 50)
        val cpuFreq = (baseFreq + freqJitter).coerceIn(630, 1804)
        val cpuLoad = if (opts.ultraSmoothMode) Random.nextInt(14, 28) else Random.nextInt(32, 54)

        _telemetryState.update { current ->
          val newHistory = (current.fpsHistory.drop(1) + fps)
          current.copy(
            currentFps = fps.toInt(),
            fpsHistory = newHistory,
            cpuFreqMhz = cpuFreq,
            cpuLoadPercent = cpuLoad,
            batteryTempC = if (opts.selectedProfile == PerformanceProfile.BATTERY_SAVER) 30.8f else 33.4f
          )
        }
      }
    }
  }

  fun getGeneratedBuildProp(): String {
    val opts = _optimizationState.value
    return """
# ==============================================================
# PixelOS 16 Official build.prop for Redmi Y3 (onclite)
# Android 16 (Baklava) - Smooth Low-Spec Configuration
# Target SoC: Snapdragon 632 (SDM632) | GPU: Adreno 506
# ==============================================================
ro.build.version.release=16
ro.build.version.security_patch=2026-09-05
ro.build.display.id=PixelOS-16.0-onclite-OFFICIAL
ro.product.model=Redmi Y3
ro.product.device=onclite
ro.product.board=sdm632
ro.product.cpu.abilist=arm64-v8a,armeabi-v7a,armeabi

# SmoothEngine Adreno 506 Optimizations
debug.sf.disable_hw_overlays=${if (opts.adrenoBoost) "1" else "0"}
debug.sf.latch_unsignaled=1
ro.surface_flinger.supports_background_blur=${if (opts.disableWindowBlurs) "0" else "1"}
debug.hwui.renderer=skiagl
ro.hardware.egl=adreno

# Memory & Low-RAM Architecture (${opts.ramVariant})
ro.config.low_ram=${if (opts.lowRamMode) "true" else "false"}
persist.sys.zram.enabled=${if (opts.zramEnabled) "1" else "0"}
persist.sys.zram.size=${opts.zramSizeMb}M
persist.sys.zram.algo=lz4
ro.lmk.kill_heaviest_task=${if (opts.aggressiveLmk) "true" else "false"}
ro.lmk.use_psi=true

# Touch Latency & Scheduler
debug.touch.resample=1
sys.use_fifo_ui=1
# ==============================================================
""".trimIndent()
  }

  fun getFlashingScript(): String {
    return """
#!/bin/bash
# ===================================================
# Fastboot Flash Script - PixelOS 16 for Redmi Y3 (onclite)
# ===================================================
echo "Connecting to Redmi Y3 fastboot mode..."
fastboot devices

echo "Unlocking bootloader critical partitions..."
fastboot flashing unlock_critical || true

echo "Flashing Kernel (4.9.227-onclite-smooth)..."
fastboot flash boot boot.img

echo "Flashing Vendor image (MIUI V11.0.9 base)..."
fastboot flash vendor vendor.img

echo "Flashing Android 16 Pixel System (Dynamic low_ram)..."
fastboot flash system system.img

echo "Erasing Dalvik & ART cache..."
fastboot erase userdata
fastboot erase cache

echo "Rebooting to PixelOS 16 on Redmi Y3!"
fastboot reboot
""".trimIndent()
  }
}
