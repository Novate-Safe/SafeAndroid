package net.novate.safe.ui.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import net.novate.base.extensions.findParent
import net.novate.safe.R
import net.novate.safe.databinding.MineFragmentBinding

/**
 * TODO
 */
class MineFragment : Fragment() {

    private lateinit var binding: MineFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = MineFragmentBinding.inflate(inflater, container, false)
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
    }
}