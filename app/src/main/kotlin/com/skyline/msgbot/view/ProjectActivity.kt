package com.skyline.msgbot.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.skyline.msgbot.reflow.project.Project
import com.skyline.msgbot.reflow.project.ProjectManager
import com.skyline.msgbot.reflow.project.create.ProjectCreator
import com.skyline.msgbot.reflow.script.JSEngineType
import com.skyline.msgbot.view.compose.ProjectUI

class ProjectActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectUI()
        }

//        ProjectCreator.createProject(
//            "test",
//            JSEngineType.GRAALVM_JS
//        )

        ProjectManager.initialize()
    }
}