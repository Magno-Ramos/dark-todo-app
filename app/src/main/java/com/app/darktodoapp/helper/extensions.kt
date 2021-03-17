package com.app.darktodoapp.helper

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.app.darktodoapp.R
import com.google.android.material.snackbar.Snackbar
import java.util.*


fun currentFormattedDate(): CharSequence {
    val date = Calendar.getInstance().time
    return DateFormat.format("EEEE',' dd", date).toString().capitalize()
}

fun Context.getCompatDrawable(@DrawableRes drawableRes: Int) =
    ContextCompat.getDrawable(this, drawableRes)

fun Context.getCompatColor(@ColorRes colorRes: Int) =
    ContextCompat.getColor(this, colorRes)

fun Number.percent(percent: Number) = (this.toDouble() / 100) * percent.toDouble()

fun View.snack(@StringRes stringRes: Int) = Snackbar.make(this, stringRes, Snackbar.LENGTH_LONG)

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachRoot: Boolean = false) =
    LayoutInflater.from(context).inflate(layoutRes, this, attachRoot)

fun <T> LiveData<T>.observe(lifecycleOwner: LifecycleOwner, callback: (T) -> Unit) {
    this.observe(lifecycleOwner, androidx.lifecycle.Observer { callback.invoke(it) })
}
