package com.a.plugin.test

import com.a.plugin.PrivacyCheckTransformRob
import org.gradle.api.Plugin
import org.gradle.api.Project

class PrivacyCheckPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println "this is my custom plugin PrivacyCheckPlugin in buildSrc test"

        project.android.registerTransform(new PrivacyCheckTransformRob(project))
    }

}
