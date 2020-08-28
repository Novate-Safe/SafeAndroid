package net.novate.safe.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
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
    }

}
