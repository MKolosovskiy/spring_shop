package com.shop.util;

import java.util.Random;

public class CodeGeneratorUtil {

    public static String generate(){
       return String.format("%04d", new Random().nextInt(10000));
   }
}
