package net.novate.safe.ui.password.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import net.novate.base.base.BaseBindingAdapter
import net.novate.safe.databinding.PasswordItemBinding

/**
 * TODO
 */
class PasswordsAdapter : BaseBindingAdapter<String, PasswordItemBinding>() {

    override fun onCreateViewDataBinding(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): PasswordItemBinding {
        return PasswordItemBinding.inflate(inflater, parent, false)
    }

    override fun onBindViewDataBinding(binding: PasswordItemBinding, item: String, position: Int) {
        binding.password = item
    }

    override fun areItemsTheSame(old: String, new: String): Boolean = old == new

    override fun areContentsTheSame(old: String, new: String): Boolean = old == new

}