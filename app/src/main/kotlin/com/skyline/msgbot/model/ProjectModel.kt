package com.skyline.msgbot.model

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.skyline.msgbot.core.CoreHelper
import com.skyline.msgbot.reflow.project.create.ProjectCreator
import com.skyline.msgbot.reflow.script.JSEngineType
import com.skyline.msgbot.reflow.script.api.FileStream
import com.skyline.msgbot.ui.theme.*

object ProjectModel {
    fun createProject(projectName: String, type: JSEngineType){
        ProjectCreator.createProject(projectName, type)
    }

    fun getProjectJsonArray(): JsonArray {
        val json = if (!FileStream.exists("projects.json")) {
            CoreHelper.GSON.fromJson("""
                {
                    "projects": [
                        {
                            "name": "NO PROJECTS FOUND1",
                            "engineType": "GRAALVM_JS"
                        },
                        {
                            "name": "NO PROJECTS FOUND2",
                            "engineType": "GRAALVM_JS"
                        },
                        {
                            "name": "NO PROJECTS FOUND3",
                            "engineType": "GRAALVM_JS"
                        }
                    ]
                }
                """.trimIndent(), JsonObject::class.java)
        } else {
            CoreHelper.GSON.fromJson(FileStream.read("projects.json"), JsonObject::class.java)
        }
        return json.getAsJsonArray("projects")
    }

    @Composable
    fun ProjectCard(jsonArray: JsonArray, index: Int) {
        Card(colors = CardDefaults.cardColors(contentColor = Purple80, containerColor = Purple40),
            modifier = Modifier
                .offset(x = 10.dp, y = 100.dp)
                .wrapContentSize(align = Alignment.Center)
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.07f)
                .clickable { }
        ) {
            Text(jsonArray.get(index).asJsonObject.get("name").asString, fontSize = 20.sp)
        }
    }
}