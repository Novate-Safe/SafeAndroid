package net.novate.safe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import net.novate.safe.R
import net.novate.safe.databinding.MainFragmentBinding

/**
 * 主界面
 */
class MainFragment : Fragment() {

    private val navController by lazy { requireActivity().findNavController(R.id.mainNavigationHost) }

    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigationView.setupWithNavController(navController)
    }
}