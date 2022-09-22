package com.shubham.composetest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.shubham.composetest.ui.theme.ComposeTestTheme
import kotlin.math.pow

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    //Greeting("Android")
                    MainUI()
                }
            }
        }
    }
}

fun calculateSip(
    context: Context,
    amount: Int,
    interestRate: Float,
    duration: Float
): Double {
    //M = P × ({[1 + i]n – 1} / i) × (1 + i).
    val durationInMonths = duration * 12
    val periodRateOfInterest: Float = (interestRate / (12 * 100))
    //Toast.makeText(context, "" + periodRateOfInterest, Toast.LENGTH_LONG).show()
    val mid = (((1 + periodRateOfInterest).toDouble()
        .pow((durationInMonths).toDouble())) - 1) / periodRateOfInterest
    return amount * mid * (1 + periodRateOfInterest)
}

@Composable
fun MainUI() {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background)) {
        var resultVisible by remember {
            mutableStateOf(false)
        }
        Text(text = "Sip Calculator")
        Text(text = "Amount")
        var amount by remember { mutableStateOf(0f) }
        Slider(
            amount, { amount = it }, valueRange = 0f..1000f
        )
        Text(
            text = "" + amount.toInt() * 100,
            modifier = Modifier.align(alignment = Alignment.End)
        )
        Text(text = "Rate Of Interest")
        var interest by remember { mutableStateOf(0f) }
        Slider(
            interest, { interest = it }, valueRange = 0f..30f
        )
        Text(
            text = "" + interest.toInt(),
            modifier = Modifier.align(alignment = Alignment.End)
        )
        Text(text = "Duration In Years")
        var duration by remember { mutableStateOf(0f) }
        Slider(
            duration, { duration = it }, valueRange = 0f..30f
        )
        Text(
            text = "" + duration.toInt(),
            modifier = Modifier.align(alignment = Alignment.End)
        )
        var maturityAmount by remember {
            mutableStateOf(0L)
        }
        val context = LocalContext.current
        var investedAmount by remember {
            mutableStateOf(0L)
        }
        var returnsAmount by remember {
            mutableStateOf(0L)
        }
        Button(
            onClick = {
                //Toast.makeText(context, "" +amount.toInt()*100*12*1, Toast.LENGTH_LONG).show()
                investedAmount = ((amount.toInt() * 100) * duration.toInt() * 12).toLong()
                maturityAmount = calculateSip(
                    context,
                    amount.toInt() * 100,
                    interest.toInt().toFloat(),
                    duration.toInt().toFloat()
                ).toLong()
                returnsAmount = maturityAmount - investedAmount
                resultVisible = true
            },
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
        ) {
            Text(text = "Calculate")
        }
        if (resultVisible) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = "Invested Amount : ")
                Text(text = "" + investedAmount)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = "Est. Returns : ")
                Text(text = "" + returnsAmount)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = "Total Amount : ")
                Text(text = "" + maturityAmount)
            }
        }
        Button(onClick = {
            context.startActivity(Intent(context, LumSumActivity::class.java))
        },
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)) {
            Text(text = "GO to Lumsum screen")
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeTestTheme {
        MainUI()
    }
}