package com.hqs.alx.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.hqs.alx.sharedmodule.createApplicationScreenMessage
import com.hqs.alx.sharedmodule.repo.ConversionsRepo
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvMain.text = createApplicationScreenMessage()

        ConversionsRepo().convertRates("USD", "ILS", "5.0") {
            CoroutineScope(Dispatchers.Main).launch {
                Log.d("alex", "this is before coroutines")
                try {
//                    Log.d("alex", "getConversions success: ${it.rates}")
//                    Log.d("alex", "getConversions size: ${it.rates?.keys?.size}")
                    Log.d("alex", "list size: ${it.result}")
                } catch (e: Exception) {
                    Log.d("alex", "getConversions error: ${e.message}")
                }
            }
        }
    }
}
