package ro.mdc.petproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ro.mdc.petproject.ui.list.ListDirections
import ro.mdc.petproject.ui.list.ListRoute
import ro.mdc.petproject.ui.theme.PetProjectTheme
import ro.mdc.petproject.ui.utils.createNavGraph

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            PetProjectTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.background),
                ) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = ListDirections.root,
                        modifier = Modifier.padding(innerPadding),
                    ) {
                        createNavGraph(
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}