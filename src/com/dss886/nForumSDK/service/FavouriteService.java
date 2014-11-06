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
import org.apache.http.impl.client.CloseableHttpClient;
import org.json.JSONException;

import com.dss886.nForumSDK.http.GetMethod;
import com.dss886.nForumSDK.http.NForumException;
import com.dss886.nForumSDK.http.PostMethod;
import com.dss886.nForumSDK.model.Favorite;

/**
 * 该类封装了收藏夹接口，
 * 见https://github.com/xw2423/nForum/wiki/nForum-API
 * @author dss886
 * @since 2014-9-7
 */
public class FavouriteService {

	private CloseableHttpClient httpClient;
	private String host;
	private String returnFormat;
	private String appkey;
	private String auth; 
	
	public FavouriteService(CloseableHttpClient httpClient, String host,
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
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Favorite getFavourite(int level) throws JSONException,
		NForumException, IOException {
		String url = host + "favorite/" + level + returnFormat + appkey;
		GetMethod getMethod = new GetMethod(httpClient, auth, url);
		return Favorite.parse(getMethod.getJSON());
	}
	
	/**
	 * 向收藏夹删除版面/目录
	 * @param level 收藏夹层数，顶层为0,
	 * @param name 新的版面或自定义目录，版面为版面name，如Flash；
	 * @param dir 是否为自定义目录 0不是，1是
	 * @return 收藏夹结构体
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Favorite addFavourite(int level, String name, int dir) throws
			JSONException, NForumException, IOException {
		String url = host + "favorite/add" + level + returnFormat + appkey;
        ParamOption params = new ParamOption()
                .addParams("name", name)
                .addParams("dir", dir);
		PostMethod postMethod = new PostMethod(httpClient, auth, url, params);
		return Favorite.parse(postMethod.postJSON());
	}
	
	/**
	 * 从收藏夹删除版面/目录
	 * @param level 收藏夹层数，顶层为0,
	 * @param name 要删除的版面或自定义目录，版面为版面name，如Flash；
	 * @param dir 是否为自定义目录 0不是，1是
	 * @return 收藏夹结构体
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Favorite delFavourite(int level, String name, int dir) throws
            JSONException, NForumException, IOException {
		String url = host + "favorite/delete" + level + returnFormat + appkey;
        ParamOption params = new ParamOption()
                .addParams("name", name)
                .addParams("dir", dir);
		PostMethod postMethod = new PostMethod(httpClient, auth, url, params);
		return Favorite.parse(postMethod.postJSON());
	}
}
