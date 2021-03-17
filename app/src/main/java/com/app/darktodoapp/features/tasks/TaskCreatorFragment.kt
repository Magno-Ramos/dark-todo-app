package com.app.darktodoapp.features.tasks

import android.os.Bundle
import android.view.View
import com.app.darktodoapp.R
import com.app.darktodoapp.common.BottomSheet
import com.app.sdk.repository.TaskRepository
import kotlinx.android.synthetic.main.fragment_task_creator.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TaskCreatorFragment(private val projectId: Int? = null) : BottomSheet() {
    
    override val layoutId: Int = R.layout.fragment_task_creator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = TaskRepository(view.context.applicationContext)
        edt_task_description.requestFocus()

        btn_save.setOnClickListener {
            val text = edt_task_description.text
            if (text.isNullOrBlank().not()) {
                GlobalScope.launch {
                    repository.save(text.toString().trim(), projectId)
                }
                dismiss()
            }
        }
    }
}