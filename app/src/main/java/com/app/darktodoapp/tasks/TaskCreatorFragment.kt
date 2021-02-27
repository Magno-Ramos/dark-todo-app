package com.app.darktodoapp.tasks

import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.darktodoapp.R
import com.app.sdk.repository.TaskRepository
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_task_creator.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TaskCreatorFragment : BottomSheetDialogFragment() {

    override fun getTheme(): Int = R.style.Theme_MaterialComponents_BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.AppTheme)
        return inflater.cloneInContext(contextThemeWrapper)
            .inflate(R.layout.fragment_task_creator, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = TaskRepository(view.context.applicationContext)

        btn_save.setOnClickListener {
            val text = edt_task_description.text
            if (text.isNullOrBlank().not()) {
                GlobalScope.launch {
                    repository.save(text.toString().trim())
                }
                dismiss()
            }
        }
    }
}