/*
 * Copyright (C) 2010-2014 dss886
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dss886.nForumSDK.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 该类封装了一些调试用输出日志的方法
 * @author dss886
 * @since 2014-9-7
 */
public class LogUtils {

    private static boolean enableLog = true;

    public static void v(String tag, String msg){
        print("v",tag,msg);
    }

    public static void d(String tag, String msg){
        print("d",tag,msg);
    }

    public static void w(String tag, String msg){
        print("w",tag,msg);
    }

    public static void e(String tag, String msg){
        printE("e",tag,msg);
    }

    private static void print(String type, String tag, String msg){
        if(!enableLog){
            return;
        }
        System.out.print(getTime());
        System.out.print(" ");
        System.out.print(type);
        System.out.print("/");
        System.out.print(getClassName());
        System.out.print(" ");
        System.out.print(tag);
        System.out.print(": ");
        System.out.println(msg);
    }

    private static void printE(String type, String tag, String msg){
        if(!enableLog){
            return;
        }
        System.err.print(getTime());
        System.err.print(" ");
        System.err.print(type);
        System.err.print("/");
        System.err.print(getClassName());
        System.err.print(" ");
        System.err.print(tag);
        System.err.print(": ");
        System.err.println(msg);
    }

    private static String getTime(){
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(date);
    }

    private static String getClassName(){
        String fullName = Thread.currentThread().getStackTrace()[4].getClassName();
        String[] fullNames = fullName.split("\\.");
        return fullNames[fullNames.length - 1];
    }

}
