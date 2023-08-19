package com.eevajonna.catgpt

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eevajonna.catgpt.ui.CatGPTViewModel
import com.eevajonna.catgpt.ui.CatGPTViewModelFactory
import com.eevajonna.catgpt.ui.screens.MainScreen
import com.eevajonna.catgpt.ui.theme.CatGPTTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CatGPTTheme {
                val owner = LocalViewModelStoreOwner.current

                owner?.let {
                    val viewModel: CatGPTViewModel = viewModel(
                        it,
                        "CatGPTViewModel",
                        CatGPTViewModelFactory(
                            LocalContext.current.applicationContext as Application,
                        ),
                    )

                    MainScreen(viewModel = viewModel)
                }
            }
        }
    }
}
