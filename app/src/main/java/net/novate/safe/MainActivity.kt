package net.novate.safe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.novate.base.binding.bindContentView
import net.novate.base.ui.system.appearBehindNavigationBar
import net.novate.base.ui.system.appearBehindStatusBar
import net.novate.safe.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        window.appearBehindStatusBar = true
        window.appearBehindNavigationBar = true
        super.onCreate(savedInstanceState)
        binding = bindContentView(R.layout.main_activity)

        supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment()).commit()

        binding.navigation.setOnNavigationItemSelectedListener {
            supportFragmentManager.beginTransaction().replace(
                R.id.container, when (it.itemId) {
                    R.id.home -> HomeFragment()
                    R.id.dynamic -> DynamicFragment()
                    R.id.mine -> MineFragment()
                    else -> throw UnsupportedOperationException("")
                }
            ).commit()
            true
        }
    }
}