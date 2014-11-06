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
import java.net.URLEncoder;

import com.dss886.nForumSDK.util.ParamOption;
import org.apache.http.impl.client.CloseableHttpClient;
import org.json.JSONException;

import com.dss886.nForumSDK.http.GetMethod;
import com.dss886.nForumSDK.http.NForumException;
import com.dss886.nForumSDK.model.Search;

/**
 * 该类封装了搜索接口，
 * 见https://github.com/xw2423/nForum/wiki/nForum-API
 * @author dss886
 * @since 2014-9-7
 */
public class SearchService {

	private CloseableHttpClient httpClient;
	private String host;
	private String returnFormat;
	private String appkey;
	private String auth; 
	
	public SearchService(CloseableHttpClient httpClient, String host,
			String returnFormat, String appkey, String auth){
		this.httpClient = httpClient;
		this.host = host;
		this.returnFormat = returnFormat;
		this.appkey = appkey;
		this.auth = auth;
	}
	
	/** 
	 * 搜索版面和目录，
	 * 支持中文关键词，参数编码请使用utf-8
	 * @param keyword 版面或目录的关键词或描述
	 * @return 搜索结果结构体
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Search searchBoard(String keyword) throws JSONException,
		NForumException, IOException {
		String board = URLEncoder.encode(keyword, "GBK");
        ParamOption params = new ParamOption().addParams("board", board);
        String url = host + "search/board" + returnFormat + appkey +params;
		GetMethod getMethod = new GetMethod(httpClient, auth, url);
		return Search.parse(getMethod.getJSON());
	}
	
	/**
	 * 搜索单个版面的文章，
	 * 搜索结果为时间顺序，o参数为1显示所有非回复文章，如果主题帖被删则结果中不会有此主题，
	 * 但是在/search/threads中会显示此主题，搜索版面主题请尽量使用/search/threads，
     * 可选参数：title1, title2, titlen, author, day, m, a, o, count, page
	 * @param board 单个合法版面
	 * @return 搜索结果结构体
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Search searchArticle(String board, ParamOption params) throws
		JSONException,NForumException, IOException {
        params.addParams("board", board);
        String url = host + "search/article" + returnFormat + appkey + params;
        GetMethod getMethod = new GetMethod(httpClient, auth, url);
        return Search.parse(getMethod.getJSON());
    }

    /**
	 * 搜索单个版面的主题，
	 * 搜索结果为时间倒序，如果主题帖被删除则会显示回复文章，
	 * 搜索结果会受到搜索最大返回结果值的限制，
     * 可选参数：title1, title2, titlen, author, day, m, a, count, page
	 * @param board 单个合法版面
	 * @return 搜索结果结构体
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Search searchThreads(String board, ParamOption params) throws
			JSONException,NForumException, IOException {
        params.addParams("board", board);
		String url = host + "search/threads" + returnFormat + appkey + params;
		GetMethod getMethod = new GetMethod(httpClient, auth, url);
		return Search.parse(getMethod.getJSON());
	}
	
}
