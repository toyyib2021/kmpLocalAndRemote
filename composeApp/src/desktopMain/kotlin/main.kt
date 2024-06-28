import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.LocalImageLoader
import com.seiko.imageloader.component.setupDefaultComponents
import com.seiko.imageloader.intercept.bitmapMemoryCacheConfig
import com.seiko.imageloader.intercept.imageMemoryCacheConfig
import com.seiko.imageloader.intercept.painterMemoryCacheConfig
import di.startKoinJvm
import okio.Path.Companion.toOkioPath
import root.RootComponent
import root.RootContent
import androidx.compose.ui.Modifier
import java.io.File


fun main() = application {

    val koin = startKoinJvm().koin

    Window(
        onCloseRequest = ::exitApplication,
        title = "AndriodAndDesktopTutorial",
    ) {
        CompositionLocalProvider(
            LocalImageLoader provides remember { generateImageLoader() },
        ) {
            val rootComponent = koin.get<RootComponent>()
            RootContent(rootComponent, modifier = Modifier)
        }
    }
}


fun generateImageLoader(): ImageLoader {
    return ImageLoader {
        components {
            setupDefaultComponents()
        }
        interceptor {
            // cache 32MB bitmap
            bitmapMemoryCacheConfig {
                maxSize(32 * 1024 * 1024) // 32MB
            }
            // cache 50 image
            imageMemoryCacheConfig {
                maxSize(50)
            }
            // cache 50 painter
            painterMemoryCacheConfig {
                maxSize(50)
            }
            diskCacheConfig {
                directory(getCacheDir().toOkioPath().resolve("image_cache"))
                maxSizeBytes(512L * 1024 * 1024) // 512MB
            }
        }
    }
}

enum class OperatingSystem {
    Windows, Linux, MacOS, Unknown
}

private val currentOperatingSystem: OperatingSystem
    get() {
        val operSys = System.getProperty("os.name").lowercase()
        return if (operSys.contains("win")) {
            OperatingSystem.Windows
        } else if (operSys.contains("nix") || operSys.contains("nux") ||
            operSys.contains("aix")
        ) {
            OperatingSystem.Linux
        } else if (operSys.contains("mac")) {
            OperatingSystem.MacOS
        } else {
            OperatingSystem.Unknown
        }
    }


// about currentOperatingSystem, see app
private fun getCacheDir() = when (currentOperatingSystem) {
    OperatingSystem.Windows -> File(System.getenv("AppData"), "$ApplicationName/cache")
    OperatingSystem.Linux -> File(System.getProperty("user.home"), ".cache/$ApplicationName")
    OperatingSystem.MacOS -> File(System.getProperty("user.home"), "Library/Caches/$ApplicationName")
    else -> throw IllegalStateException("Unsupported operating system")
}

private const val ApplicationName = "AndriodAndDesktopTutorial"