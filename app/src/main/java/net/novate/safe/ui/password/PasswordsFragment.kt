package net.novate.safe.ui.password

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import net.novate.safe.databinding.PasswordsFragmentBinding
import net.novate.safe.ui.password.adapter.PasswordsAdapter2

/**
 * TODO
 */
class PasswordsFragment : Fragment() {

    private lateinit var binding: PasswordsFragmentBinding
    private val viewModel by viewModels<PasswordsViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifecycleScope
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = PasswordsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PasswordsAdapter2()
        binding.recyclerView.adapter = adapter
//        binding.lifecycleOwner = this

        val list = (0..10).map { "密码$it" }
//        adapter.data = list


        val pagingSource = object : PagingSource<Int, String>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, String> {
                println("5555 ${System.currentTimeMillis()}")
                return LoadResult.Page(list, null, null)
            }
        }


        val pager = Pager<Int, String>(PagingConfig(10)) {
            pagingSource
        }

//        binding.lifecycleOwner = this
//        binding.lifecycle = lifecycle
//        binding.adapter = adapter
//        binding.passwords = pager.liveData

        println("2222 ${System.currentTimeMillis()}")



        lifecycleScope.launch {
            pager.flow.collectLatest {

                println("1111 ${System.currentTimeMillis()}")
                adapter.submitData(it)
            }
        }

//        adapter.submitData()
    }

    override fun onStart() {
        super.onStart()
        println("3333 ${System.currentTimeMillis()}")
    }

    override fun onResume() {
        super.onResume()
        println("4444 ${System.currentTimeMillis()}")
    }
}