package com.example.model

enum class PerformanceProfile(
  val title: String,
  val subtitle: String,
  val description: String,
  val targetFps: Int,
  val animScale: Float
) {
  ULTRA_SMOOTH(
    title = "Ultra-Smooth (Legacy Spec)",
    subtitle = "Recommended for Redmi Y3 3GB/4GB",
    description = "Forces 0.5x animations, disables heavy GPU Skia blurs, enables Adreno 506 HW overlay bypass, and keeps steady 60 FPS.",
    targetFps = 60,
    animScale = 0.5f
  ),
  PIXEL_BALANCED(
    title = "Pixel Pure (Stock Experience)",
    subtitle = "Android 16 Default Look",
    description = "Full Material You animations with subtle background shading and standard memory thresholds.",
    targetFps = 58,
    animScale = 1.0f
  ),
  BATTERY_SAVER(
    title = "Battery Endurance",
    subtitle = "Maximum SOT for 4000mAh",
    description = "Lowers CPU scheduler boost, caps background sync, and minimizes GPU clock state to prolong battery life.",
    targetFps = 55,
    animScale = 0.5f
  ),
  GAMING_ADRENO(
    title = "Adreno 506 Overdrive",
    subtitle = "Prioritize Touch & Render",
    description = "Locks CPU Kryo 250 cores to max frequency during interaction and frees all non-essential RAM to foreground games.",
    targetFps = 60,
    animScale = 0.75f
  )
}

object RedmiY3Device {
  const val CODENAME = "onclite"
  const val MODEL_NAME = "Redmi Y3"
  const val CHIPSET = "Qualcomm Snapdragon 632 (SDM632)"
  const val CPU_SPEC = "8x Kryo 250 @ 1.80 GHz (14nm FinFET)"
  const val GPU_SPEC = "Qualcomm Adreno 506 @ 725 MHz"
  const val DISPLAY_SPEC = "6.26\" HD+ (1520 x 720), 19:9, 60Hz IPS LCD"
  const val STORAGE_SPEC = "32 GB eMMC 5.1 Flash (Ultra-Light ROM Footprint)"
  const val BATTERY_SPEC = "4000 mAh Li-Po (10W Fast Charging)"
  const val ANDROID_ORIGINAL = "Android 9 (Pie) with MIUI 10"
  const val ROM_NAME = "Google Pixel Experience (Official)"
  const val ROM_VERSION = "Android 16 (Baklava)"
  const val SECURITY_PATCH = "October 5, 2026"
  const val GOOGLE_PLAY_UPDATE = "October 1, 2026"
  const val BUILD_TAG = "BP1A.261003.001.A1-pixel16-onclite"
  const val KERNEL_VERSION = "4.9.337-perf-PixelEngine+ onclite-sdm632"
}

enum class QsTab(val label: String) {
  CONTROLS("Control Center"),
  NOTIFICATIONS("Notifications")
}

data class QsNotificationItem(
  val id: String,
  val appName: String,
  val title: String,
  val message: String,
  val timestamp: String,
  val category: String = "SYSTEM",
  val iconType: String = "info"
)

data class RomOptimizationState(
  val ultraSmoothMode: Boolean = true,
  val lowRamMode: Boolean = true,
  val zramEnabled: Boolean = true,
  val zramSizeMb: Int = 2048,
  val adrenoBoost: Boolean = true,
  val disableWindowBlurs: Boolean = true,
  val aggressiveLmk: Boolean = true,
  val animationScale: Float = 0.5f,
  val ramVariant: String = "3GB",
  val storageVariant: String = "32GB",
  val selectedProfile: PerformanceProfile = PerformanceProfile.ULTRA_SMOOTH
)

data class TelemetryState(
  val currentFps: Int = 60,
  val fpsHistory: List<Float> = listOf(60f, 60f, 59.8f, 60f, 60f, 60f, 59.9f, 60f, 60f),
  val cpuFreqMhz: Int = 1401,
  val cpuLoadPercent: Int = 18,
  val usedRamMb: Int = 1120,
  val totalRamMb: Int = 3072,
  val zramUsedMb: Int = 290,
  val usedStorageGb: Float = 3.8f,
  val totalStorageGb: Float = 32.0f,
  val batteryLevel: Int = 86,
  val batteryTempC: Float = 32.4f,
  val activeGovernor: String = "schedutil (sdm632-light)"
)

data class FlashingStep(
  val stepNumber: Int,
  val title: String,
  val subtitle: String,
  val command: String,
  val details: String,
  val isCompleted: Boolean = false
)
