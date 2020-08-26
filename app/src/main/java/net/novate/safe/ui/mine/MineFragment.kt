package net.novate.safe.ui.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
}