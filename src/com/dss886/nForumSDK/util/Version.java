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

/**
 * 该类封装了一些关于nForumSDK版本的信息及方法
 * @author dss886
 * @since 2014-9-7
 */
public class Version {
	
	private static final String versionName = "1.0";
	private static final String versionCode = "1";
	private static final String nForumVersion = "2014-07-18";
	private static final String lastestChangelog = "本版本支持nForum 2.0 2014-07-18 的所有功能，\n" +
			"根据站点的设置不同，某些功能可能不可用，比如北邮人论坛使用API发文时无法显示签名档，\n" +
			"部分站点可能没有将API版本更新到最新版，使用时请注意区分版本";
	
	public static String getCurentVersion(){
		return versionName;
	}
	
	public static String getCurentCodeVersion(){
		return versionCode;
	}
	
	public static String getnForumVersion(){
		return nForumVersion;
	}
	
	public static String getLastestChangelog(){
		return lastestChangelog;
	} 
}
