package net.novate.safe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.novate.base.ui.appearBehindNavigationBar
import net.novate.base.ui.appearBehindStatusBar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        window.appearBehindStatusBar()
        window.appearBehindNavigationBar()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}