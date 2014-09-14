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
package com.dss886.nForumSDK.service;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import com.dss886.nForumSDK.http.GetMethod;
import com.dss886.nForumSDK.http.NForumException;
import com.dss886.nForumSDK.http.PostMethod;
import com.dss886.nForumSDK.model.Refer;

/**
 * 该类封装了提醒接口，
 * 见https://github.com/xw2423/nForum/wiki/nForum-API
 * @author dss886
 * @since 2014-9-7
 */
public class ReferService {

	private DefaultHttpClient httpClient; 
	private String host;
	private String returnFormat;
	private String appkey;
	private String auth; 
	
	public ReferService(DefaultHttpClient httpClient, String host,
			String returnFormat, String appkey, String auth){
		this.httpClient = httpClient;
		this.host = host;
		this.returnFormat = returnFormat;
		this.appkey = appkey;
		this.auth = auth;
	}
	
	/**
	 * 获取指定提醒类型列表
	 * @param type 只能为at|reply中的一个，分别是@我的文章和回复我的文章，
	 * 				建议使用Constant.REFER_*常量
	 * @param count 每页提醒文章的数量，最小1 最大50 默认20
	 * @param page 提醒列表的页数，默认1
	 * @return 提醒结构体
	 * @throws ClientProtocolException
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Refer getRefer(String type, int count, int page) throws ClientProtocolException, JSONException,
		NForumException, IOException {
		String url = host + "refer/" + type + returnFormat + appkey 
				+ "&count=" + count +"&page=" + page;
		GetMethod getMethod = new GetMethod(httpClient, auth, url);
		return Refer.parse(getMethod.getJSON());
	}
	
	/**
	 * 获取指定类型提醒的属性信息
	 * @param type 只能为at|reply中的一个，分别是@我的文章和回复我的文章，
	 * 			建议使用Constant.REFER_*常量
	 * @return 提醒结构体
	 * @throws ClientProtocolException
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Refer getReferInfo(String type) throws ClientProtocolException, JSONException,
	NForumException, IOException {
		String url = host + "refer/" + type + "/info" +returnFormat + appkey;
		GetMethod getMethod = new GetMethod(httpClient, auth, url);
		return Refer.parse(getMethod.getJSON());
	}
	
	/**
	 * 设置指定提醒为已读
	 * @param type 只能为at|reply中的一个，分别是@我的文章和回复我的文章，
	 * 			建议使用Constant.REFER_*常量
	 * @param index 提醒的索引，为提醒元数据中的index值
	 * @return 已读的提醒元数据
	 * @throws ClientProtocolException
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Refer setReferRead(String type, int index) throws ClientProtocolException, JSONException,
	NForumException, IOException {
		String url = host + "refer/" + type + "/setRead/" + index + returnFormat + appkey;
		PostMethod postMethod = new PostMethod(httpClient, auth, url, new ArrayList<NameValuePair>());
		return Refer.parse(postMethod.postJSON());
	}
	
	/**
	 * 设置指定类型的所有提醒为已读
	 * @param type 只能为at|reply中的一个，分别是@我的文章和回复我的文章，
	 * 			建议使用Constant.REFER_*常量
	 * @return 成功则返回true，不成功则抛出nForumException异常
	 * @throws ClientProtocolException
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public boolean setReferRead(String type) throws ClientProtocolException, JSONException,
	NForumException, IOException {
		String url = host + "refer/" + type + "/setRead" + returnFormat + appkey;
		PostMethod postMethod = new PostMethod(httpClient, auth, url, new ArrayList<NameValuePair>());
		return postMethod.postJSON().getBoolean("status");
	}
	
	/**
	 * 删除指定提醒
	 * @param type 只能为at|reply中的一个，分别是@我的文章和回复我的文章，
	 * 			建议使用Constant.REFER_*常量
	 * @param index 提醒的索引，为提醒元数据中的index值
	 * @return 已读的提醒元数据
	 * @throws ClientProtocolException
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Refer deleteRefer(String type, int index) throws ClientProtocolException, JSONException,
	NForumException, IOException {
		String url = host + "refer/" + type + "/delete/" + index + returnFormat + appkey;
		PostMethod postMethod = new PostMethod(httpClient, auth, url, new ArrayList<NameValuePair>());
		return Refer.parse(postMethod.postJSON());
	}
	
	/**
	 * 删除指定类型的所有提醒
	 * @param type 只能为at|reply中的一个，分别是@我的文章和回复我的文章，
	 * 			建议使用Constant.REFER_*常量
	 * @return 成功则返回true，不成功则抛出nForumException异常
	 * @throws ClientProtocolException
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public boolean deleteRefer(String type) throws ClientProtocolException, JSONException,
	NForumException, IOException {
		String url = host + "refer/" + type + "/delete" + returnFormat + appkey;
		PostMethod postMethod = new PostMethod(httpClient, auth, url, new ArrayList<NameValuePair>());
		return postMethod.postJSON().getBoolean("status");
	}
	
}
