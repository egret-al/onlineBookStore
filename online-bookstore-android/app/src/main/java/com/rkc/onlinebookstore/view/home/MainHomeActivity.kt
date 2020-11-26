package com.rkc.onlinebookstore.view.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.view.login.MainActivity

class MainHomeActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_home)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        //因为该应用是单页面应用，整个app除了登录注册之外，只使用了一个activity，因此我们需要对底部导航栏的显示和隐藏进行逻辑处理
        //fragment之间的切换添加监听，只有在主页指定的几个fragment下才显示底部导航栏
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            run {
                if (isMainFragment(destination.id)) {
                    navView.visibility = View.VISIBLE
                } else {
                    navView.visibility = View.GONE
                }
            }
        }
        appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_home, R.id.nav_classify, R.id.nav_mine, R.id.nav_shopping))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //处理菜单项点击
        when(item.itemId) {
            //返回到登录页面
            R.id.menu_exit_login -> startActivity(Intent(this, MainActivity::class.java))
        }
        return false
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun isMainFragment(id: Int): Boolean {
        if (id == R.id.nav_home || id == R.id.nav_classify || id == R.id.nav_mine || id == R.id.nav_shopping) {
            return true
        }
        return false
    }
}