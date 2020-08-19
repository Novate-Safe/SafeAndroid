package net.novate.base.base

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri

/**
 * 基础初始化器
 */
abstract class BaseInitializer : ContentProvider() {
    final override fun onCreate(): Boolean {
        onInit()
        return true
    }

    abstract fun onInit()

    final override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    final override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?) = 0

    final override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?) = 0

    final override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? = null

    final override fun getType(uri: Uri): String? = null
}