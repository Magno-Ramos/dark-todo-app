package com.app.darktodoapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.edit
import com.app.darktodoapp.features.MainActivity
import kotlinx.android.synthetic.main.activity_intro.*

const val INTRO_DISPLAYED_KEY = "INTRO_DISPLAYED_KEY"

class IntroActivity : AppCompatActivity(R.layout.activity_intro) {
    override fun onCreate(savedInstanceState: Bundle?) {
        checkIntroDisplayed()
        super.onCreate(savedInstanceState)
        btn_continue.setOnClickListener { goToMain() }
    }

    private fun checkIntroDisplayed() {
        val prefs = getPreferences(Context.MODE_PRIVATE)
        val displayed = prefs.getBoolean(INTRO_DISPLAYED_KEY, false)

        if (displayed) goToMain()
        else prefs.edit(commit = true) { putBoolean(INTRO_DISPLAYED_KEY, true) }
    }

    private fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}