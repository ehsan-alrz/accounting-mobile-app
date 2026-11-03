package com.example.balanceapp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                BalanceScreen()
            }
        }
    }
}

@Composable
fun BalanceScreen() {
    var balanceText by remember { mutableStateOf("اضغط لجلب الرصيد من السيرفر") }
    val apiService = remember { ApiService.create() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = balanceText, fontSize = 18.sp)

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            apiService.getBalance(1L).enqueue(object : Callback<Account> {
                override fun onResponse(call: Call<Account>, response: Response<Account>) {
                    if (response.isSuccessful && response.body() != null) {
                        val acc = response.body()!!
                        balanceText = "رقم الحساب: ${acc.accountNumber}\nالرصيد: ${acc.balance} ${acc.currency}"
                    } else {
                        balanceText = "فشل في جلب البيانات!"
                    }
                }

                override fun onFailure(call: Call<Account>, t: Throwable) {
                    balanceText = "خطأ في الاتصال: ${t.message}"
                }
            })
        }) {
            Text(text = "استعلم عن الرصيد")
        }
    }
}