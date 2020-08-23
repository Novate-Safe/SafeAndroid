package net.novate.safe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import net.novate.base.simple.SimpleFragmentStateAdapter
import net.novate.safe.databinding.HomeFragmentBinding

/**
 * TODO
 */
class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = SimpleFragmentStateAdapter(this, PasswordsFragment(), PasswordsFragment(), PasswordsFragment(), PasswordsFragment(), PasswordsFragment(), PasswordsFragment())

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = listOf("全部", "收藏", "支付", "社交", "游戏", "其他")[position]
        }.attach()
    }
}