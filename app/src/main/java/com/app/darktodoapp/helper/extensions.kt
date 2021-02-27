package com.app.darktodoapp.helper

import android.text.format.DateFormat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import java.util.*


fun currentFormattedDate(): CharSequence {
    val date = Calendar.getInstance().time
    return DateFormat.format("EEEE',' dd", date).toString().capitalize()
}

fun <T> LiveData<T>.observe(lifecycleOwner: LifecycleOwner, callback: (T) -> Unit) {
    this.observe(lifecycleOwner, androidx.lifecycle.Observer { callback.invoke(it) })
}
