package net.novate.base.simple

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * 简单 FragmentStateAdapter
 */
class SimpleFragmentStateAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, private vararg val fragments: Fragment) : FragmentStateAdapter(fragmentManager, lifecycle) {

    constructor(activity: FragmentActivity, vararg fragments: Fragment) : this(activity.supportFragmentManager, activity.lifecycle, *fragments)

    constructor(fragment: Fragment, vararg fragments: Fragment) : this(fragment.childFragmentManager, fragment.lifecycle, *fragments)

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]

}