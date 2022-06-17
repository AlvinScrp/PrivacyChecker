package com.a.plugin.privacycheck;

import java.util.Arrays;

import javassist.CannotCompileException;
import javassist.expr.MethodCall;
import javassist.runtime.Desc;

public class InjectMethodProxy {

    public static void execute(MethodCall m) throws CannotCompileException {
        String mLongName = m.getClassName() + "." + m.getMethodName();
        StringBuilder sb=new StringBuilder();
        sb.append("{ ");
        sb.append("   android.util.Log.d(\"alvin\",android.util.Log.getStackTraceString(new Throwable(\"" + mLongName + "\"))); ");
        sb.append( "  $_ =($r)( com.a.privacychecker.MainApp.privacyVisitProxy(\""+ m.getClassName()+"\",\""+m.getMethodName()+"\", $0,$sig, $args)); ");
        sb.append("}");
        String replace =sb.toString();
        m.replace(replace);



//        String replace = "{  $_ =($r)( com.a.privacychecker.MainApp.privacyVisitProxy(\""+ m.getClassName()+"\",\""+m.getMethodName()+"\", $0,$sig, $args)); }";
//        m.replace(replace);


    }
}
