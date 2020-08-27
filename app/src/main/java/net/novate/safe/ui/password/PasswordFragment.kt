package net.novate.safe.ui.password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import net.novate.base.simple.SimpleFragmentStateAdapter
import net.novate.base.view.findParent
import net.novate.safe.R
import net.novate.safe.databinding.PasswordFragmentBinding

/**
 * TODO
 */
class PasswordFragment : Fragment() {

    private lateinit var binding: PasswordFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = PasswordFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.findParent<DrawerLayout>()?.let { drawerLayout ->
            binding.toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_menu, null)
            binding.toolbar.setNavigationOnClickListener {
                drawerLayout.open()
            }
        }

        binding.viewPager.adapter = SimpleFragmentStateAdapter(this, PasswordsFragment(), PasswordsFragment(), PasswordsFragment(), PasswordsFragment(), PasswordsFragment(), PasswordsFragment())

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = listOf("全部", "收藏", "支付", "社交", "游戏", "其他")[position]
        }.attach()
    }
}