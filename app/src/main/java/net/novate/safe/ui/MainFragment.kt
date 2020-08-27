package net.novate.safe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import net.novate.safe.R

/**
 * 主界面
 */
class MainFragment : Fragment() {

    private val navController by lazy { requireActivity().findNavController(R.id.mainNavigationHost) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<NavigationView>(R.id.navigationView)?.setupWithNavController(navController)
        view.findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.setupWithNavController(navController)
    }
}