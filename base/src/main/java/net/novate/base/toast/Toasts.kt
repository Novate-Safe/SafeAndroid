package net.novate.base.toast

import android.content.Context
import android.widget.Toast
import androidx.annotation.IntDef
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

@Retention(AnnotationRetention.SOURCE)
@IntDef(Toast.LENGTH_SHORT, Toast.LENGTH_LONG)
annotation class Duration

fun Context.toast(text: CharSequence, @Duration duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, text, duration).show()

fun Context.toast(@StringRes resId: Int, @Duration duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, resId, duration).show()

fun Fragment.toast(text: CharSequence, @Duration duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(requireContext(), text, duration).show()

fun Fragment.toast(@StringRes resId: Int, @Duration duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(requireContext(), resId, duration).show()