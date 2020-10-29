package net.novate.safe.ui

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.plus
import androidx.navigation.findNavController
import net.novate.base.color.toArgbString
import net.novate.base.color.toColor
import net.novate.base.ui.system.appearBehindNavigationBar
import net.novate.base.ui.system.appearBehindStatusBar
import net.novate.safe.R

/**
 * 导航页
 */
class NavigationActivity : AppCompatActivity() {

    private val navController by lazy { findNavController(R.id.navigationHost) }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.appearBehindStatusBar = true
        window.appearBehindNavigationBar = true
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_activity)

        printColor()
    }

}

// 颜色计算小工具
fun printColor() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        println("Color = ${("#0E0E0E".toColor() + Color.valueOf(1f, 1f, 1f, 0.06f)).toArgbString()}")
    }
}

