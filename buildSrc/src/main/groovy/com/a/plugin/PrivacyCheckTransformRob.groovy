package com.a.plugin


import com.a.plugin.privacycheck.PrivacyCheckRob
import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import javassist.ClassPool
import org.gradle.api.Project

class PrivacyCheckTransformRob extends Transform {

    ClassPool classPool = ClassPool.default
    Project project

    PrivacyCheckTransformRob(project) {
        this.project = project

    }

    @Override
    String getName() {
        return "PrivacyCheckTransformRob"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws Exception {
        super.transform(transformInvocation)

        println "----------Privacy check transform start----------"

        project.android.bootClasspath.each {
            classPool.appendClassPath(it.absolutePath)
        }
        File jarFile = transformInvocation.outputProvider.getContentLocation(
                "main", getOutputTypes(), getScopes(), Format.JAR);
        println("jarFile:" + jarFile.absolutePath)
        if (!jarFile.getParentFile().exists()) {
            jarFile.getParentFile().mkdirs();
        }
        if (jarFile.exists()) {
            jarFile.delete();
        }

        def box = ConvertUtils.toCtClasses(transformInvocation.inputs, classPool)
        println("ctClass size:" + box.size())

        PrivacyCheckRob.insertCode(box, jarFile)

        println "----------Privacy check transform end----------"

//        throw new NullPointerException(("hahahahahahaha"))
    }


}
