package com.a.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class PrivacyCheckPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println "this is my custom plugin PrivacyCheckPlugin"

//        project.android.registerTransform(new PrivacyCheckTransform(project))
        project.android.registerTransform(new PrivacyCheckTransformRob(project))
    }

}
