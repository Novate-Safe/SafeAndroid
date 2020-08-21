@file:JvmName("Permissions")

package net.novate.base.permission

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity


const val GRANTED = PackageManager.PERMISSION_GRANTED
const val DENIED = PackageManager.PERMISSION_DENIED
const val IGNORED = -2
const val CANCELED = -3

fun Context.hasPermissions(vararg permissions: String) = permissions.all { ActivityCompat.checkSelfPermission(this, it) == GRANTED }

fun Fragment.hasPermissions(vararg permissions: String) = permissions.all { ActivityCompat.checkSelfPermission(requireContext(), it) == GRANTED }

fun FragmentActivity.requestPermission(permission: String, onResult: (result: Int) -> Unit) {
    requestPermissions(permission) { onResult(it.first()) }
}

fun FragmentActivity.requestPermissions(vararg permissions: String, onResult: (results: IntArray) -> Unit) {
    permissionFragment.requestPermissions(*permissions, onResult = onResult)
}

fun FragmentActivity.requestAllManifestPermissions(onResult: (results: IntArray) -> Unit) {
    requestPermissions(*allManifestPermissions, onResult = onResult)
}

fun Fragment.requestPermission(permission: String, onResult: (result: Int) -> Unit) {
    requireActivity().requestPermission(permission, onResult)
}

fun Fragment.requestPermissions(vararg permissions: String, onResult: (results: IntArray) -> Unit) {
    requireActivity().requestPermissions(*permissions, onResult = onResult)
}

fun Fragment.requestAllManifestPermissions(onResult: (results: IntArray) -> Unit) {
    requireActivity().requestAllManifestPermissions(onResult)
}

private val Context.allManifestPermissions
    get() = packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS).requestedPermissions ?: emptyArray()

private val FragmentActivity.permissionFragment
    get() = with(supportFragmentManager) {
        findFragmentByTag(PermissionsFragment.TAG) ?: PermissionsFragment().also {
            beginTransaction().add(it, PermissionsFragment.TAG).commitNowAllowingStateLoss()
        }
    } as PermissionsFragment