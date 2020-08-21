package net.novate.base.permission

import android.util.SparseArray
import androidx.annotation.MainThread
import androidx.core.app.ActivityCompat
import androidx.core.util.keyIterator
import androidx.core.util.set
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

internal class PermissionsFragment : Fragment() {

    private val requests by lazy { SparseArray<Pair<Array<out String>, (IntArray) -> Unit>>() }
    private fun requestCode(): Int = requests.keyIterator().asSequence().withIndex().firstOrNull { it.index != it.value }?.index ?: requests.size()

    @MainThread
    fun requestPermissions(vararg permissions: String, onResult: (IntArray) -> Unit) {
        lifecycle.addObserver(object : LifecycleObserver {
            val requestCode = requestCode()

            init {
                requests[requestCode] = permissions to onResult
                requestPermissions(permissions, requestCode)
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                requests.remove(requestCode)
            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, results: IntArray) {
        requests[requestCode]?.let { (permissions, onResult) ->
            requests.remove(requestCode)
            onResult(permissions.mapIndexed { index, permission ->
                if (permissions.size != results.size) {
                    CANCEL
                } else {
                    if (results[index] == GRANTED) {
                        GRANTED
                    } else {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), permission)) {
                            DENIED
                        } else {
                            IGNORE
                        }
                    }
                }
            }.toIntArray())
        }
    }

    companion object {
        val TAG = "${PermissionsFragment::class.qualifiedName}.TAG"
    }
}