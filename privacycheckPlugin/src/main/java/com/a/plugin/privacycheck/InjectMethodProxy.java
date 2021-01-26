package com.a.plugin.privacycheck;

import java.util.Arrays;

import javassist.CannotCompileException;
import javassist.expr.MethodCall;
import javassist.runtime.Desc;

public class InjectMethodProxy {

    public static void execute(MethodCall m) throws CannotCompileException {
        System.out.println(m.getSignature());
//        System.out.println(Arrays.toString(Desc.getParams(m.getSignature())));
        String replace = "{  $_ =($r)( com.a.privacychecker.MainApp.privacyVisitProxy(\""+ m.getClassName()+"\",\""+m.getMethodName()+"\", $0,$sig, $args)); }";
        m.replace(replace);


    }
}
