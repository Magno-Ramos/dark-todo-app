package com.app.darktodoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.darktodoapp.helper.currentFormattedDate
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        txt_current_date.text = currentFormattedDate()
    }
}