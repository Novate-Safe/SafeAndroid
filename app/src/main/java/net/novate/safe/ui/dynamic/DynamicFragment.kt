package net.novate.safe.ui.dynamic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import net.novate.safe.databinding.DynamicFragmentBinding

/**
 * TODO
 */
class DynamicFragment : Fragment() {

    private lateinit var binding: DynamicFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DynamicFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}