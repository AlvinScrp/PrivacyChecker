package com.a

import org.gradle.api.Plugin
import org.gradle.api.Project

class MyCustomPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println "this is my custom plugin "
        def log = project.logger
        log.error "========================";
        log.error "完整的MyPlugin，开始修改Class!";
        log.error "========================";

    }

}
