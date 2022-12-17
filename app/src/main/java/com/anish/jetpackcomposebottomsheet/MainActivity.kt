package com.anish.jetpackcomposebottomsheet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anish.jetpackcomposebottomsheet.ui.theme.JetpackComposeBottomSheetTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeBottomSheetTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    MainScreen() //use this for Modal Bottom Sheet
                    //HomeScreen() use this for Standard Bottom Sheet
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen() {
    val bottomSheetScaffoldState =
        rememberBottomSheetScaffoldState(bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed))
    val coroutineScope = rememberCoroutineScope()
    BottomSheetScaffold(scaffoldState = bottomSheetScaffoldState, sheetContent = {
        Box(
            Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Text(text = "Hello from sheet")
        }
    }, sheetPeekHeight = 0.dp) {
        Button(onClick = {
            coroutineScope.launch {
                if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                    bottomSheetScaffoldState.bottomSheetState.expand()
                } else {
                    bottomSheetScaffoldState.bottomSheetState.collapse()
                }
            }
        }) {
            Text(text = "Expand/Collapse Bottom Sheet")
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen() {
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )
    val coroutineScope = rememberCoroutineScope()

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState, sheetContent = {
            Column() {
                BottomSheet()
                BottomSheet()
                BottomSheet()
                BottomSheet()
            }
        }, modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome to bottom sheet playground!",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = {
                coroutineScope.launch {
                    if (sheetState.isVisible) sheetState.hide()
                    else sheetState.show()
                }
            }) {
                Text(text = "Click to show bottom sheet")
            }
        }
    }
}

@Composable
fun BottomSheet() {
    Column(
        modifier = Modifier.padding(32.dp)
    ) {
        Text(
            text = "Bottom sheet",
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Click outside the bottom sheet to hide it",
            style = MaterialTheme.typography.body1
        )
    }
}
