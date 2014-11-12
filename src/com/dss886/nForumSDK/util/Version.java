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
	
	private static final String versionName = "0.2.1";
	private static final String versionCode = "3";
	private static final String nForumVersion = "2014-11-07";
	private static final String latestChangelog = "将使用的HttpClient的版本回滚至4.2.5，以避免在Android上的一些兼容性问题。";
	
	public static String getCurrentVersion(){
		return versionName;
	}
	
	public static String getCurrentCodeVersion(){
		return versionCode;
	}
	
	public static String getNForumVersion(){
		return nForumVersion;
	}
	
	public static String getLatestChangelog(){
		return latestChangelog;
	} 
}
