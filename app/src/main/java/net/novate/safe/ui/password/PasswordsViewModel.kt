package net.novate.safe.ui.password

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.liveData

/**
 * TODO
 */
class PasswordsViewModel : ViewModel() {
    val list = (0..100).map { "密码$it" }

    val pagingSource = object : PagingSource<Int, String>() {
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, String> {
            return LoadResult.Page(list, null, null)
        }
    }


    val pager = Pager<Int, String>(PagingConfig(Int.MAX_VALUE)) {
        pagingSource
    }.liveData
}