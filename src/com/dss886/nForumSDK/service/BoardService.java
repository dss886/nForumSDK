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

import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import com.dss886.nForumSDK.http.GetMethod;
import com.dss886.nForumSDK.http.NForumException;
import com.dss886.nForumSDK.model.Board;

/**
 * 该类封装了版面接口，
 * 见https://github.com/xw2423/nForum/wiki/nForum-API
 * @author dss886
 * @since 2014-9-7
 */
public class BoardService {
	
	private DefaultHttpClient httpClient; 
	private String host;
	private String returnFormat;
	private String appkey;
	private String auth; 
	
	public BoardService(DefaultHttpClient httpClient, String host,
			String returnFormat, String appkey, String auth){
		this.httpClient = httpClient;
		this.host = host;
		this.returnFormat = returnFormat;
		this.appkey = appkey;
		this.auth = auth;
	}
	
	/**
	 * 获取指定版面的信息
	 * @param name 合法的版面名称
	 * @param mode 可选，版面文章列表模式，0-6，默认为2
	 * @param count 可选，每页文章的数量，默认为30
	 * @param page 可选，文章的页数，默认为1
	 * @return 版面元数据，以及在元数据含有以下两个属性：当前版面模式所包含的文章元数组，和当前版面模式分页信息
	 * @throws ClientProtocolException
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Board getBoard(String name, int mode, int count, int page) throws 
		ClientProtocolException, JSONException, NForumException, IOException {
		String url = host + "board/" + name + returnFormat + appkey + "&mode=" + 
				mode + "&count=" + count +"&page=" + page;
		GetMethod getMethod = new GetMethod(httpClient, auth, url);
		return Board.parse(getMethod.getJSON());
	}
}
