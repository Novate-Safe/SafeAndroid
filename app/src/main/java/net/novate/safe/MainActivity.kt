package net.novate.safe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import net.novate.base.binding.bindContentView
import net.novate.base.simple.SimpleFragmentStateAdapter
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

        binding.viewPager.adapter = SimpleFragmentStateAdapter(this, HomeFragment(), DynamicFragment(), MineFragment())
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bottomNavigationView.selectedItemId = when (position) {
                    0 -> R.id.home
                    1 -> R.id.dynamic
                    2 -> R.id.mine
                    else -> throw UnsupportedOperationException("")
                }

            }
        })

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            binding.viewPager.setCurrentItem(
                when (it.itemId) {
                    R.id.home -> 0
                    R.id.dynamic -> 1
                    R.id.mine -> 2
                    else -> throw UnsupportedOperationException("")
                }, true
            )
            binding.toolbar.title = it.title
            true
        }
    }
}