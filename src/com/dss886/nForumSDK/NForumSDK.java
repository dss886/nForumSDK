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
package com.dss886.nForumSDK;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.dss886.nForumSDK.service.ArticleService;
import com.dss886.nForumSDK.service.AttachmentService;
import com.dss886.nForumSDK.service.BoardService;
import com.dss886.nForumSDK.service.SectionService;
import com.dss886.nForumSDK.service.UserService;
import com.dss886.nForumSDK.service.WidgetService;
import com.dss886.nForumSDK.util.Constant;

/**
 * SDK主类，封装了一些参数设置函数，
 * 并能返回用于调用接口的对象
 * @author dss886
 * @since 2014-9-7
 */
public class NForumSDK {
	
	private DefaultHttpClient httpClient;
	private String host;
	private String appkey;
	private String auth;
	
	private String returnFormat = Constant.RETRUN_FORMAT_JSON;
	private int timeout = 10000;

	/**
	 * 需要appkey的构造函数
	 * @param host 需要包括完整的协议和尾部的斜杠，例如：http://api.byr.cn/，
	 * 			或者使用 Contant.HOST_* 常量。
	 * @param appkey 不包含参数名"?appkey="和尾部多余的"&"字符
	 * @param username 用户名
	 * @param password 密码
	 */
	public NForumSDK(String host, String appkey, String username, String password){
		httpClient = new DefaultHttpClient();
		HttpParams param = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(param, timeout);
		HttpConnectionParams.setSoTimeout(param, timeout);
		httpClient = new DefaultHttpClient(param);
		auth = new String(Base64.encodeBase64((username + ":" + password).getBytes()));
		this.host = host;
		this.returnFormat = returnFormat + "?";
		this.appkey = "&appkey=" + appkey;
	}
	
	/**
	 * 不需要appkey的构造函数
	 * @param host 需要包括完整的协议和尾部的斜杠，例如：http://api.byr.cn/，
	 * 			或者使用 Contant.HOST_* 常量。
	 * @param username 用户名
	 * @param password 密码
	 */
	public NForumSDK(String host, String username, String password){
		httpClient = new DefaultHttpClient();
		HttpParams param = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(param, timeout);
		HttpConnectionParams.setSoTimeout(param, timeout);
		httpClient = new DefaultHttpClient(param);
		auth = new String(Base64.encodeBase64((username + ":" + password).getBytes()));
		this.host = host;
		this.returnFormat = returnFormat + "?";
		this.appkey = "";
	}
	
	public int getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout 网络超时毫秒数
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	
	/**
	 * @return 文章接口封装对象
	 */
	public ArticleService getArticleService(){
		return new ArticleService(httpClient, host, returnFormat, appkey, auth);
	}
	
	/**
	 * @return 附件接口封装对象
	 */
	public AttachmentService getAttachmentService(){
		return new AttachmentService(httpClient, host, returnFormat, appkey, auth);
	}
	
	/**
	 * @return 版面接口封装对象
	 */
	public BoardService getBoardService(){
		return new BoardService(httpClient, host, returnFormat, appkey, auth);
	}
	
	/**
	 * @return 分区接口封装对象
	 */
	public SectionService getSectionService(){
		return new SectionService(httpClient, host, returnFormat, appkey, auth);
	}
	
	/**
	 * @return 用户接口封装对象
	 */
	public UserService getUserService(){
		return new UserService(httpClient, host, returnFormat, appkey, auth);
	}
	
	/**
	 * @return Widget接口封装对象
	 */
	public WidgetService getWidgetService(){
		return new WidgetService(httpClient, host, returnFormat, appkey, auth);
	}
	
}
