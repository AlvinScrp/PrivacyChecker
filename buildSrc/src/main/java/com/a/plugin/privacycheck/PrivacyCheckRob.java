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
import javassist.expr.MethodCall;

public class PrivacyCheckRob {

    public static void insertCode(List<CtClass> box, File jarFile) throws Exception {
        long startTime = System.currentTimeMillis();
        ZipOutputStream outStream = new JarOutputStream(new FileOutputStream(jarFile));
        for (CtClass ctClass : box) {
            if (ctClass.isFrozen()) ctClass.defrost();
            if (!ctClass.isFrozen()&&!ctClass.getName().equals("com.a.privacychecker.MainApp")) {
                for (CtMethod ctMethod : ctClass.getDeclaredMethods()) {
                    ctMethod.instrument(new ExprEditor() {
                        @Override
                        public void edit(MethodCall m) throws CannotCompileException {
                            String mLongName = m.getClassName() + "." + m.getMethodName();
                            if (PrivacyConstants.privacySet.contains(mLongName)) {
                                systemOutPrintln(mLongName,m,ctMethod);
//                                InjectAddLog.execute(m);
//                                InjectHookReturnValue.execute(m);
                                InjectMethodProxy.execute(m);
                            }
                        }
                        private  void systemOutPrintln(String mLongName, MethodCall m,CtMethod ctMethod) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("\n========");
                            sb.append("\ncall: " + mLongName);
                            sb.append("\n  at: " + ctMethod.getLongName() + "(" + ctMethod.getDeclaringClass().getSimpleName() + ".java:" + m.getLineNumber() + ")");
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
