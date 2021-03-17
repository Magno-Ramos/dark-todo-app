package com.app.darktodoapp.common

import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.darktodoapp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BottomSheet : BottomSheetDialogFragment() {

    internal abstract val layoutId: Int

    override fun getTheme(): Int = R.style.BottomSheetStyle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.AppTheme)
        return inflater.cloneInContext(contextThemeWrapper).inflate(layoutId, container)
    }
}