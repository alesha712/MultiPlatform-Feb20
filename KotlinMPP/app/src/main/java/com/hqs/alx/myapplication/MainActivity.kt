package com.hqs.alx.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
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

        ConversionsRepo().getConversionRates {
            CoroutineScope(Dispatchers.Main).launch {
                Log.d("alex", "this is before coroutines")
                try {
                    Log.d("alex", "getConversions success: ${it.base}")
                }catch (e: Exception){
                    Log.d("alex", "getConversions success: error")
                }

                Log.d("alex", "this is after coroutines")
            }
        }
    }
}
