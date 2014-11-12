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

import com.dss886.nForumSDK.util.ParamOption;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import com.dss886.nForumSDK.http.GetMethod;
import com.dss886.nForumSDK.http.NForumException;
import com.dss886.nForumSDK.http.PostMethod;
import com.dss886.nForumSDK.model.Blacklist;

/**
 * 该类封装了黑名单接口，
 * 见https://github.com/xw2423/nForum/wiki/nForum-API
 * @author dss886
 * @since 2014-9-7
 */
public class BlacklistService {

	private DefaultHttpClient httpClient;
	private String host;
	private String returnFormat;
	private String appkey;
	private String auth; 
	
	public BlacklistService(DefaultHttpClient httpClient, String host,
			String returnFormat, String appkey, String auth){
		this.httpClient = httpClient;
		this.host = host;
		this.returnFormat = returnFormat;
		this.appkey = appkey;
		this.auth = auth;
	}
	
	/**
	 * 用户黑名单列表
     * 可选参数：count, page
	 * @return 黑名单结构体
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Blacklist getBlacklist(ParamOption params) throws JSONException,
		NForumException, IOException {
        String url = host + "blacklist/list" + returnFormat + appkey + params;
        GetMethod getMethod = new GetMethod(httpClient, auth, url);
        return Blacklist.parse(getMethod.getJSON());
    }

    /**
	 * 黑名单添加用户
	 * @param id 所要添加的用户ID
	 * @return 成功为true，不成功则抛出nForumException异常
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public boolean addBlacklist(String id) throws JSONException,
		NForumException, IOException {
		String url = host + "blacklist/add" + returnFormat + appkey;
        ParamOption params = new ParamOption().addParams("id", id);
		PostMethod postMethod = new PostMethod(httpClient, auth, url, params);
		return postMethod.postJSON().getBoolean("status");
	}
	
	/**
	 * 黑名单删除用户
	 * @param id 所要删除的用户ID
	 * @return 成功为true，不成功则抛出nForumException异常
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public boolean delBlacklist(String id) throws JSONException,
	NForumException, IOException {
		String url = host + "blacklist/delete" + returnFormat + appkey;
        ParamOption params = new ParamOption().addParams("id", id);
		PostMethod postMethod = new PostMethod(httpClient, auth, url, params);
		return postMethod.postJSON().getBoolean("status");
	}
	
}
