package com.app.darktodoapp.features.home

import android.os.Bundle
import android.view.View
import com.app.darktodoapp.R
import com.app.darktodoapp.common.BottomSheet
import kotlinx.android.synthetic.main.fragment_choose_creation.*

enum class CreatorType {
    TASK,
    PROJECT
}

class ChooseCreationFragment : BottomSheet() {

    private var listener: ((CreatorType) -> Unit) ? = null

    override val layoutId: Int = R.layout.fragment_choose_creation

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_task.setOnClickListener { listener?.invoke(CreatorType.TASK) }
        btn_project.setOnClickListener { listener?.invoke(CreatorType.PROJECT) }
    }

    fun setCreatorListener(listener: (CreatorType) -> Unit) {
        this.listener = listener
    }
}