package com.rkc.onlinebookstore.view.home

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
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

    @SuppressLint("UseCompatLoadingForColorStateLists")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //透明状态栏
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        //透明导航栏
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        setStatusBarFullTransparent()
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
        appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_home, R.id.nav_classify, R.id.nav_mine, R.id.nav_shopping, R.id.nav_search))
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        //设置底部导航栏字体选中和没选中的颜色
        navView.itemTextColor = resources.getColorStateList(R.color.bottom_selector)
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
        if (id == R.id.nav_home || id == R.id.nav_classify || id == R.id.nav_mine || id == R.id.nav_shopping || id == R.id.nav_search) {
            return true
        }
        return false
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