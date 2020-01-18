package com.example.tintint_jw.new_package.util.extension

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

fun Context.getDrawableById(@DrawableRes res: Int): Drawable = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) getDrawable(res)!! else resources.getDrawable(res)

fun Context.getColorById(@ColorRes res: Int): Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) getColor(res) else resources.getColor(res)

fun Context.showToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
fun Context.showToast(resId: Int) = Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()