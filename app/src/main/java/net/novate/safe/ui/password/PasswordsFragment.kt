package net.novate.safe.ui.password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import net.novate.safe.databinding.PasswordsFragmentBinding
import net.novate.safe.ui.password.adapter.PasswordsAdapter

/**
 * TODO
 */
class PasswordsFragment : Fragment() {

    private lateinit var binding: PasswordsFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = PasswordsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.adapter = PasswordsAdapter()
        binding.passwords = listOf(
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码",
            "密码"
        ).mapIndexed { index, s -> "$index $s" }
    }
}