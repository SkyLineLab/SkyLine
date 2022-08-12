package com.skyline.msgbot.viewmodel

import android.app.Activity
import com.skyline.msgbot.model.ProjectModel

class ProjectViewModel {
    private var activity: Activity? = null
    private var model: ProjectModel? = null
    fun ViewModel(activity: Activity?) {
        this.activity = activity
        model = ProjectModel
        initView()
    }

    private fun initView() {
        //TODO
        //View의 표현과 Model과의 상호작용
    }
}