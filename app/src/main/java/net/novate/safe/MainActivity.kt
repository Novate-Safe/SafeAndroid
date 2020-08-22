package net.novate.safe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.main_activity.*
import net.novate.base.ui.system.appearBehindNavigationBar
import net.novate.base.ui.system.appearBehindStatusBar
import net.novate.base.ui.system.lowProfileSystemUi

class MainActivity : AppCompatActivity() {
    var a = false
    override fun onCreate(savedInstanceState: Bundle?) {
        window.lowProfileSystemUi = true
        window.appearBehindStatusBar = true
        window.appearBehindNavigationBar = true
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        test.setOnClickListener {
            window.lowProfileSystemUi = a
            window.appearBehindStatusBar = a
            window.appearBehindNavigationBar = a
            println("setOnClickListener ${window.decorView.systemUiVisibility}")
            a = !a
        }
    }
}