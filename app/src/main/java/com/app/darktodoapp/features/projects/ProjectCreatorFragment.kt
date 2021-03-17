package com.app.darktodoapp.features.projects

import android.os.Bundle
import android.view.View
import com.app.darktodoapp.R
import com.app.darktodoapp.common.BottomSheet
import com.app.sdk.repository.ProjectRepository
import kotlinx.android.synthetic.main.fragment_project_creator.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProjectCreatorFragment : BottomSheet() {

    override val layoutId: Int = R.layout.fragment_project_creator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repo = ProjectRepository(view.context.applicationContext)
        btn_save.setOnClickListener {
            val name = edt_project_name.text?.toString()?.trim()
            if (name != null && name.isNotBlank()) {
                GlobalScope.launch { repo.save(name) }
                dismiss()
            }
        }
    }
}