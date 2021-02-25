package com.app.darktodoapp.helper

import android.text.format.DateFormat
import java.util.*


fun currentFormattedDate(): CharSequence {
    val date = Calendar.getInstance().time
    return DateFormat.format("EEEE',' dd", date).toString().capitalize()
}
