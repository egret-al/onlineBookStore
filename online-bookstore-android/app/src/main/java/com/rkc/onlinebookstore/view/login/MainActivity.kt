package com.rkc.onlinebookstore.view.login

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.rkc.onlinebookstore.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarFullTransparent()
        setContentView(R.layout.activity_main)
        //NavigationUI.setupActionBarWithNavController(this, findNavController(R.id.fragment))
    }

    private fun setStatusBarFullTransparent() {
        //21 -> 5.0
        if (Build.VERSION.SDK_INT >= 21) {
            window.apply {
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                statusBarColor = Color.TRANSPARENT
            }
        } else if (Build.VERSION.SDK_INT >= 19) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }
}