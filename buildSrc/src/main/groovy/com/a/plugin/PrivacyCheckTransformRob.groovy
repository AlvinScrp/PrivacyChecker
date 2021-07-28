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
        return "PrivacyCheckTransformBuildSrc"
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

        println "----------Privacy check transform start buildsrc----------"

        project.android.bootClasspath.each {
            classPool.appendClassPath(it.absolutePath)
        }

        //所有的class经过修改后汇集到这个jar文件中
        File jarFile = generateAllClassOutJarFile(transformInvocation)

        //汇集所有class，包括我们编写的java代码和第三方jar中的class
        def ctClasses = ConvertUtils.toCtClasses(transformInvocation.inputs, classPool)

        //修改并打包进jarFile
        PrivacyCheckRob.insertCode(ctClasses, jarFile)

        println "----------Privacy check transform end buildSrc----------"

//        throw new NullPointerException(("hahahahahahaha"))
    }

    private File generateAllClassOutJarFile(TransformInvocation transformInvocation) {
        File jarFile = transformInvocation.outputProvider.getContentLocation(
                "main", getOutputTypes(), getScopes(), Format.JAR);
        println("jarFile:" + jarFile.absolutePath)
        if (!jarFile.getParentFile().exists()) {
            jarFile.getParentFile().mkdirs();
        }
        if (jarFile.exists()) {
            jarFile.delete();
        }
        return jarFile
    }
}
