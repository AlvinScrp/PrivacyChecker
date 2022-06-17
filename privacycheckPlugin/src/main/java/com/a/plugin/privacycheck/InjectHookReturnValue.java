package com.a.plugin.privacycheck;

import javassist.CannotCompileException;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;

public class InjectHookReturnValue {

    public static void execute(MethodCall m) throws CannotCompileException {

        boolean isAllow = PrivacyConfig.isAllow;
        String mLongName = m.getClassName() + "." + m.getMethodName();
        StringBuilder sb = new StringBuilder();
        sb.append("\n{ ");
        if (isAllow) {
            sb.append("\n         {  ");
            sb.append("\n             $_ = $proceed($$); ");
            sb.append("\n" + PrivacyConfig.Statement_log + "(true,\"" + mLongName + "\");");
            sb.append("\n         }");
        } else {
            sb.append("\n$_ =($r)(" + PrivacyConfig.Statement_Reject_Method + "(\"" + m.getClassName() + "\",\"" + m.getMethodName() + "\", $0,$sig, $args));");
            sb.append("\n" + PrivacyConfig.Statement_log + "(false,\"" + mLongName + "\");");
        }


        sb.append("\n}");
        String replace = sb.toString();
        m.replace(replace);
    }

    public static void execute(FieldAccess f) throws CannotCompileException {
        boolean isAllow = PrivacyConfig.isAllow;
        String mLongName = f.getClassName() + "." + f.getFieldName();
        StringBuilder sb = new StringBuilder();
        sb.append("\n{ ");
        if (isAllow) {
            sb.append("\n         {  ");
            sb.append("\n             $_ = $proceed($$); ");
            sb.append("\n" + PrivacyConfig.Statement_log + "(true,\"" + mLongName + "\");");
            sb.append("\n         }");
        } else {
            sb.append("\n$_ =($r)(" + PrivacyConfig.Statement_Reject_Field + "(\"" + mLongName + "\"));");
            sb.append("\n" + PrivacyConfig.Statement_log + "(false,\"" + mLongName + "\");");
        }

        sb.append("\n}");
        String replace = sb.toString();
        f.replace(replace);
    }

}
