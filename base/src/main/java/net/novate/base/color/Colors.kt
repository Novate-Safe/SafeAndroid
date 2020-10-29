package net.novate.base.color

import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import okio.Buffer
import java.util.*

/**
 * 颜色计算工具
 */
@RequiresApi(Build.VERSION_CODES.O)
fun Color.toArgbString(): String = "#${Buffer().writeInt(toArgb()).readByteString().hex().toUpperCase(Locale.getDefault())}"

@RequiresApi(Build.VERSION_CODES.O)
fun String.toColor(): Color = Color.valueOf(Color.parseColor(this))