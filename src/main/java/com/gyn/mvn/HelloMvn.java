package com.gyn.mvn;

import com.google.common.base.Strings;

public class HelloMvn {
    public static void main(String[] args) {
        String str = "Hello maven!!!";
        if(!Strings.isNullOrEmpty(str))
            System.out.println(str);
    }
}
