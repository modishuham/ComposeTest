package com.shubham.composetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shubham.composetest.ui.theme.ComposeTestTheme
import java.text.DecimalFormat
import kotlin.math.pow

class LumSumActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LumSumLayout()
                }
            }
        }
    }
}

/*
* Lump-sum Calculators work on the principle of future value.
* The lump-sum calculator tells you the future value of your
* investment at a certain rate of interest. You must use the
* mathematical formula:
* FV = PV(1+r)^n
* FV = Future Value
* PV = Present Value
* r = Rate of interest
* n = Number of years
* For example, you have invested a lump sum amount of
* Rs 1,00,000 in a mutual fund scheme for 20 years.
* You have the expected rate of return of 10% on the investment.
* You may calculate the future value of the investment as:
* FV = 1,00,000(1+0.1)^20 FV = Rs 6,72,750.
* You have invested Rs 1,00,000 which has grown to Rs 6,72,750.
* The wealth gain is Rs 6,72,750 – Rs 1,00,000 = Rs 5,72,750.
* */
fun calculateLumpSum(
    amount: Long,
    duration: Int,
    interestRate: Float
): Long {
    return (amount * ((1 + (interestRate / 100)).pow(duration))).toLong()
}

@Composable
fun LumSumLayout() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        var investmentAmount by remember {
            mutableStateOf("")
        }
        var duration by remember { mutableStateOf(0f) }
        var interest by remember { mutableStateOf(9.0f) }
        var maturity by remember { mutableStateOf(0L) }
        Text(text = "Lumpsum Calculator")
        OutlinedTextField(
            value = investmentAmount,
            onValueChange = {
                if (it.length < 10) {
                    investmentAmount = it
                }
            },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            label = { Text("Investment Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Row {
            Text(text = "Investment Period (in Years)", Modifier.weight(4.0F))
            Text(text = "${duration.toInt()} Years", Modifier.weight(1.0F))
        }
        Slider(
            duration, { duration = it }, valueRange = 0f..30f
        )
        val rate = DecimalFormat("##.#").format(interest)
        Row {
            Text(text = "Interest Rate", Modifier.weight(4.0F))
            Text(text = "$rate %", Modifier.weight(1.0F))
        }
        Slider(
            interest, { interest = it }, valueRange = 0f..100f
        )
        Button(onClick = {
            maturity = calculateLumpSum(investmentAmount.toLong(), duration.toInt(), rate.toFloat())
        }, modifier = Modifier.align(alignment = Alignment.CenterHorizontally)) {
            Text(text = "Calculate")
        }
        Text(text = "" + maturity)
    }
}

@Composable
fun Greeting2(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    ComposeTestTheme {
        LumSumLayout()
    }
}