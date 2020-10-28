package net.novate.safe.ui

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.plus
import androidx.navigation.findNavController
import net.novate.base.ui.system.appearBehindNavigationBar
import net.novate.base.ui.system.appearBehindStatusBar
import net.novate.safe.R
import okio.Buffer
import java.util.*

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

        c()
    }

}

// 颜色计算小工具
fun c() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        println("#${Buffer().writeInt((Color.valueOf(Color.parseColor("#121212")).plus(Color.valueOf(Color.parseColor("#1FFFFFFF")))).toArgb()).readByteString().hex().toUpperCase(Locale.getDefault())}")
    }
}

