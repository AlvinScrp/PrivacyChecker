package com.a.plugin.privacycheck;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;

public class PrivacyCheckRob {

    public static void insertCode(List<CtClass> ctClasses, File jarFile) throws Exception {
        long startTime = System.currentTimeMillis();
        ZipOutputStream outStream = new JarOutputStream(new FileOutputStream(jarFile));
        for (CtClass ctClass : ctClasses) {
            if (ctClass.isFrozen()) ctClass.defrost();
            if (!ctClass.isFrozen() && !PrivacyConfig.ignoreClasses.contains(ctClass.getName())) {
//            if (!ctClass.isFrozen()&&!ctClass.getName().equals("com.a.privacychecker.MainApp")) {
                for (CtMethod ctMethod : ctClass.getDeclaredMethods()) {
                    ctMethod.instrument(new ExprEditor() {
                        @Override
                        public void edit(MethodCall m) throws CannotCompileException {
                            String mLongName = m.getClassName() + "." + m.getMethodName();

//                            if (PrivacyConfig.methodHookValueSet.contains(mLongName) && !skipPackage(ctMethod)) {
//                                systemOutPrintln(mLongName, m.getLineNumber(), ctMethod);
//                                InjectHookReturnValue.execute(m);
//                            }
                            if (PrivacyConfig.methodSet.contains(mLongName) && !skipPackage(ctMethod)) {
                                systemOutPrintln(mLongName, m.getLineNumber(), ctMethod);
                                InjectAddLog.execute(m);
//                                InjectHookReturnValue.execute(m);
//                                InjectMethodProxy.execute(m);
                            }

                        }

//                        @Override
//                        public void edit(FieldAccess fieldAccess) throws CannotCompileException {
//                            String mLongName = fieldAccess.getClassName() + "." + fieldAccess.getFieldName();
//                            if (PrivacyConfig.fieldHookValueSet.contains(mLongName) && fieldAccess.isReader()) {
//                                systemOutPrintln(mLongName, fieldAccess.getLineNumber(), ctMethod);
//                                InjectHookReturnValue.execute(fieldAccess);
//                            }
//                        }

                        private boolean skipPackage(CtMethod ctMethod) {
                            String ctMethodLongName = ctMethod.getLongName();
                            return ctMethodLongName.startsWith("androidx")
                                    || ctMethodLongName.startsWith("io.flutter")
                                    || ctMethodLongName.startsWith("com.ta.a.d.e")
                                    || ctMethodLongName.startsWith("com.ta.utdid2.device.c")
                                    || ctMethodLongName.startsWith("com.idlefish");
                        }

                        private void systemOutPrintln(String mLongName, int lineNumber, CtMethod ctMethod) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("\n========");
                            sb.append("\ncall: " + mLongName);
                            sb.append("\n  at: " + ctMethod.getLongName() + "(" + ctMethod.getDeclaringClass().getSimpleName() + ".java:" + lineNumber + ")");
                            System.out.println(sb.toString());
                        }
                    });
                }

            }
            zipFile(ctClass.toBytecode(), outStream, ctClass.getName().replaceAll("\\.", "/") + ".class");
        }
        outStream.close();
        float cost = (System.currentTimeMillis() - startTime) / 1000.0f;
        System.out.println("insertCode cost " + cost + " second");

    }

    public static void zipFile(byte[] classBytesArray, ZipOutputStream zos, String entryName) {
        try {
            ZipEntry entry = new ZipEntry(entryName);
            zos.putNextEntry(entry);
            zos.write(classBytesArray, 0, classBytesArray.length);
            zos.closeEntry();
            zos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
