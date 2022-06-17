package com.a.plugin


import com.a.plugin.PrivacyCheckTransformRob
import com.a.plugin.PrivacyExtension
import com.a.plugin.privacycheck.PrivacyConfig
import org.gradle.api.Plugin
import org.gradle.api.Project

class PrivacyCheckPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println "this is my custom plugin PrivacyCheckPlugin in buildSrc test"
        project.extensions.create('privacyExtension', PrivacyExtension)
        project.afterEvaluate {
            println '配置信息======='
            Set<String> privacySet = project['privacyExtension'].privacySet
            if (privacySet != null && !privacySet.isEmpty()) {
                for (item in privacySet) {
                    println '方法名=' + item
                }
//                PrivacyConfig.methodSet.clear()
                PrivacyConfig.methodSet.addAll(privacySet)
            }

            Set<String> fieldSet = project['privacyExtension'].fieldSet
            if (fieldSet != null && !fieldSet.isEmpty()) {
//              PrivacyConfig.fieldSet.clear()
                PrivacyConfig.fieldSet.addAll(project['privacyExtension'].fieldSet)
            }
            Set<String> ignoreClass = project['privacyExtension'].ignoreClass
            if (ignoreClass != null && !ignoreClass.isEmpty()) {
                PrivacyConfig.ignoreClasses.clear()
                PrivacyConfig.ignoreClasses.addAll(ignoreClass)
            }

//            if (project['privacyExtension'].statementAllowBool != null) {
//                PrivacyConfig.Statement_Allow_bool = project['privacyExtension'].statementAllowBool
//                println 'Statement_Allow_bool=' + PrivacyConfig.Statement_Allow_bool
//            }

            if (project['privacyExtension'].statementRejectValue != null) {
                PrivacyConfig.Statement_Reject_Method = project['privacyExtension'].statementRejectValue
                println 'Statement_Reject_Value=' + PrivacyConfig.Statement_Reject_Method
            }
//            if (project['privacyExtension'].logTagAllow != null) {
//                PrivacyConfig.Log_tag_Allow = project['privacyExtension'].logTagAllow
//                println 'Log_tag_Allow=' + PrivacyConfig.Log_tag_Allow
//            }
//            if (project['privacyExtension'].logTagReject != null) {
//                PrivacyConfig.Log_tag_Reject = project['privacyExtension'].logTagReject
//                println 'Log_tag_Reject=' + PrivacyConfig.Log_tag_Reject
//            }


        }
        project.android.registerTransform(new PrivacyCheckTransformRob(project))
    }

}
