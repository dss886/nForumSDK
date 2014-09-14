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
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import com.dss886.nForumSDK.http.GetMethod;
import com.dss886.nForumSDK.http.NForumException;
import com.dss886.nForumSDK.http.PostMethod;
import com.dss886.nForumSDK.model.Favourite;

/**
 * 该类封装了收藏夹接口，
 * 见https://github.com/xw2423/nForum/wiki/nForum-API
 * @author dss886
 * @since 2014-9-7
 */
public class FavouriteService {

	private DefaultHttpClient httpClient; 
	private String host;
	private String returnFormat;
	private String appkey;
	private String auth; 
	
	public FavouriteService(DefaultHttpClient httpClient, String host,
			String returnFormat, String appkey, String auth){
		this.httpClient = httpClient;
		this.host = host;
		this.returnFormat = returnFormat;
		this.appkey = appkey;
		this.auth = auth;
	}
	
	/**
	 * 获取收藏夹信息
	 * @param level 收藏夹层数，顶层为0
	 * @return 收藏夹结构体
	 * @throws ClientProtocolException
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Favourite getFavourite(int level) throws ClientProtocolException, JSONException,
		NForumException, IOException {
		String url = host + "favorite/" + level + returnFormat + appkey;
		GetMethod getMethod = new GetMethod(httpClient, auth, url);
		return Favourite.parse(getMethod.getJSON());
	}
	
	/**
	 * 向收藏夹删除版面/目录
	 * @param level 收藏夹层数，顶层为0,
	 * @param name 新的版面或自定义目录，版面为版面name，如Flash；
	 * @param dir 是否为自定义目录 0不是，1是
	 * @return 收藏夹结构体
	 * @throws ClientProtocolException
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Favourite addFavourite(int level, String name, int dir) throws ClientProtocolException,
			JSONException, NForumException, IOException {
		String url = host + "favorite/add" + level + returnFormat + appkey;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("dir", dir+""));
		PostMethod postMethod = new PostMethod(httpClient, auth, url, params);
		return Favourite.parse(postMethod.postJSON());
	}
	
	/**
	 * 从收藏夹删除版面/目录
	 * @param level 收藏夹层数，顶层为0,
	 * @param name 要删除的版面或自定义目录，版面为版面name，如Flash；
	 * @param dir 是否为自定义目录 0不是，1是
	 * @return 收藏夹结构体
	 * @throws ClientProtocolException
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Favourite delFavourite(int level, String name, int dir) throws ClientProtocolException,
	JSONException, NForumException, IOException {
		String url = host + "favorite/delete" + level + returnFormat + appkey;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("dir", dir+""));
		PostMethod postMethod = new PostMethod(httpClient, auth, url, params);
		return Favourite.parse(postMethod.postJSON());
	}
}
