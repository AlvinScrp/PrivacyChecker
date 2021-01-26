package com.a.plugin.privacycheck;

import javassist.CannotCompileException;
import javassist.expr.MethodCall;

public class InjectAddLog {

    public static void execute(MethodCall m) throws CannotCompileException {
        String mLongName = m.getClassName() + "." + m.getMethodName();
        //simple inject log
        String replace = "{  android.util.Log.d(\"alvin\",android.util.Log.getStackTraceString(new Throwable(\""+mLongName+"\"))); $_ = $proceed($$); }";
        m.replace(replace);
    }
}
