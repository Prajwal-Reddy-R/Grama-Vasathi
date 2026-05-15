//package com.yourname.gramavasathi
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.SnackbarHost
//import androidx.compose.material3.SnackbarHostState
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.navigation.compose.rememberNavController
//import com.yourname.gramavasathi.ui.GramaVasathiNavGraph
//import com.yourname.gramavasathi.ui.theme.GramaVasathiTheme
//import com.yourname.gramavasathi.util.SnackbarController
//import dagger.hilt.android.AndroidEntryPoint
//import kotlinx.coroutines.flow.collectLatest
//
//@AndroidEntryPoint
//class MainActivity : ComponentActivity() {
//    /*
//     * PRE-SUBMISSION CHECKLIST:
//     * [ ] Firebase project connected (google-services.json present)
//     * [ ] All screens navigate correctly (Home -> Detail -> Booking -> Confirmation)
//     * [ ] Firestore security rules deployed
//     * [ ] Demo data seeded (10 listings, 3 reviews, 2 bookings)
//     * [ ] Score calculator unit tests passing (ScoreCalculatorTest.kt)
//     * [ ] App tested on emulator API 23 and API 34
//     * [ ] APK generated from release build
//     * [ ] Demo video recorded (host flow + guest flow, 3-5 minutes)
//     */
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            GramaVasathiTheme {
//                val navController = rememberNavController()
//                val snackbarHostState = remember { SnackbarHostState() }
//
//                LaunchedEffect(Unit) {
//                    SnackbarController.events.collectLatest { event ->
//                        snackbarHostState.showSnackbar(
//                            message = event.message,
//                            actionLabel = event.actionLabel
//                        )
//                    }
//                }
//
//                Scaffold(
//                    modifier = Modifier.fillMaxSize(),
//                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
//                ) { innerPadding ->
//                    GramaVasathiNavGraph(
//                        navController = navController
//                    )
//                }
//            }
//        }
//    }
//}

package com.yourname.gramavasathi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.yourname.gramavasathi.ui.GramaVasathiNavGraph
import com.yourname.gramavasathi.ui.theme.GramaVasathiTheme
import com.yourname.gramavasathi.util.SnackbarController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Fix status bar overlap
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            GramaVasathiTheme {
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }

                LaunchedEffect(Unit) {
                    SnackbarController.events.collectLatest { event ->
                        snackbarHostState.showSnackbar(
                            message = event.message,
                            actionLabel = event.actionLabel
                        )
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = {
                        SnackbarHost(hostState = snackbarHostState)
                    }
                ) { _ ->
                    GramaVasathiNavGraph(
                        navController = navController
                    )
                }
            }
        }
    }
}