/*
 * Copyright (C) 2010-2013 dss886
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

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 该类封装了一些调试用输出日志的方法
 * @author dss886
 * @since 2014-9-7
 */
public class Log {
	
	public static void d(String tag, String msg){
		int len = tag.length();
		String str = "";
		for(int i = 0; i < 15 - len; i ++){
			str = str + " ";
		}
		
		int len2 = msg.length();
		String str2 = "";
		for(int i = 0; i < 15 - len2; i ++){
			str2 = str2 + " ";
		}
		
		System.out.println(tag + str + " | " + msg);
	}
	
	public static void e(String tag, String msg, StackTraceElement[] stackTraces){
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("标签：" + tag + "\n");
			sb.append("时间：" + getSystemTime() + "\n");
			sb.append("错误详情：" + msg + "\n");
			
			if(stackTraces != null){
				for(StackTraceElement starckTrace: stackTraces){
					sb.append(starckTrace.toString() + "\n");
				}
			}
			
			for(int i = 0; i < 50; i ++){
				sb.append("-");
			}
			sb.append("\n");
			
			FileWriter writer = new FileWriter("./log.txt", true);
            writer.write(sb.toString());
            writer.close();
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static String getSystemTime(){
		Date date = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return dateFormat.format(date);
	}
}
