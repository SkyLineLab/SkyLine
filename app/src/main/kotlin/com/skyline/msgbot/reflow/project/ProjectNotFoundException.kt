package com.skyline.msgbot.reflow.project

class ProjectNotFoundException(projectName: String) : RuntimeException(projectName) {}