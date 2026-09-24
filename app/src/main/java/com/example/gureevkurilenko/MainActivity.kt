package com.example.gureevkurilenko

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gureevkurilenko.ui.theme.GureevKurilenkoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val elements = listOf("Element 0", "Element 1", "Element 2", "Element 3", "Element 4", "Element 5")
        setContent {
            Hello(elements)
        }
    }
}

@Composable
fun Hello(elements: List<String>) {
    val columnElementsStyle = TextStyle(
        fontSize = 28.sp,
        color = Color.Cyan,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
    val columnElementModifier = Modifier.fillMaxWidth().height(40.dp).padding(5.dp)
        .clip(CircleShape)
        .background(color = Color.White)
        .border(width = 2.dp, color = Color.Yellow, shape = CircleShape)
    LazyColumn (modifier = Modifier.height(600.dp)
        .padding(10.dp)
        .background(Color.LightGray)
        .fillMaxWidth()
        .padding(30.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {
        item { Text("Elements:", fontSize = 32.sp) }
        items(elements){ e -> val clicksState = remember { mutableStateOf(0) }
            val onClicksChange = { value: Int ->
                clicksState.value = value
            }
            Text(e + " clicked: ${clicksState.value}",
                style = columnElementsStyle,
                modifier = columnElementModifier.clickable {
                    onClicksChange(clicksState.value + 1)
                }
            )}
    }
}