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

import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.DefaultHttpClient;
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

	private DefaultHttpClient httpClient; 
	private String host;
	private String returnFormat;
	private String appkey;
	private String auth; 
	
	public SearchService(DefaultHttpClient httpClient, String host,
			String returnFormat, String appkey, String auth){
		this.httpClient = httpClient;
		this.host = host;
		this.returnFormat = returnFormat;
		this.appkey = appkey;
		this.auth = auth;
	}
	
	/** 
	 * 搜索版面和目录，
	 * 支持中文关键词
	 * @param keyword 版面或目录的关键词或描述
	 * @return 搜索结果结构体
	 * @throws ClientProtocolException
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Search searchBoard(String keyword) throws ClientProtocolException, JSONException,
		NForumException, IOException {
		String board = URLEncoder.encode(keyword, "GBK");
		String url = host + "search/board" + returnFormat + appkey
				+ "&board=" + board;
		GetMethod getMethod = new GetMethod(httpClient, auth, url);
		return Search.parse(getMethod.getJSON());
	}
	
	/**
	 * 搜索单个版面的文章，
	 * 搜索结果为时间顺序，o参数为1显示所有非回复文章，如果主题帖被删则结果中不会有此主题，
	 * 但是在/search/threads中会显示此主题，搜索版面主题请尽量使用/search/threads
	 * @param board 单个合法版面
	 * @param title1 文章标题包含此关键词，不选置null或空字符串
	 * @param title2 文章标题同时包含此关键词，不选置null或空字符串
	 * @param titlen 文章标题不包含此关键词，不选置null或空字符串
	 * @param author 文章的作者，不选置null或空字符串
	 * @param day 搜索距今多少天内的文章，默认7
	 * @param m 文章是否含有m标记 含有为1，不含为0
	 * @param a 文章是否含有附件 含有为1，不含为0
	 * @param o 是否只显示主题文章 是为1，否为0
	 * @param count 每页文章的数量，最小1 最大50 默认30
	 * @param page 文章的页数， 默认1
	 * @return 搜索结果结构体
	 * @throws ClientProtocolException
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Search searchArticle(String board, String title1, String title2, String titlen,
		String author, int day, int m, int a, int o, int count, int page) throws 
		ClientProtocolException, JSONException,NForumException, IOException {
		StringBuilder params = new StringBuilder();
		if(null != title1 && !title1.isEmpty()){
			params.append("&title1="+title1);
		}
		if(null != title2 && !title2.isEmpty()){
			params.append("&title1="+title1);
		}
		if(null != titlen && !titlen.isEmpty()){
			params.append("&title1="+title1);
		}
		if(null != author && !author.isEmpty()){
			params.append("&title1="+title1);
		}
		if(day != -1 && day != 7){
			params.append("&day="+day);
		}
		params.append("&m="+m);
		params.append("&a="+a);
		params.append("&o="+o);
		if(count != -1 && count != 30){
			params.append("&count="+count);
		}
		if(page != -1 && page != 1){
			params.append("&page="+page);
		}
		String url = host + "search/article" + returnFormat + appkey + params.toString();
		GetMethod getMethod = new GetMethod(httpClient, auth, url);
		return Search.parse(getMethod.getJSON());
	}
	
	/**
	 * 搜索单个版面的主题，
	 * 搜索结果为时间倒序，如果主题帖被删除则会显示回复文章，
	 * 搜索结果会受到搜索最大返回结果值的限制
	 * @param board 单个合法版面
	 * @param title1 文章标题包含此关键词，不选置null或空字符串
	 * @param title2 文章标题同时包含此关键词，不选置null或空字符串
	 * @param titlen 文章标题不包含此关键词，不选置null或空字符串
	 * @param author 文章的作者，不选置null或空字符串
	 * @param day 搜索距今多少天内的文章，默认7
	 * @param m 文章是否含有m标记 含有为1，不含为0
	 * @param a 文章是否含有附件 含有为1，不含为0
	 * @param count 每页文章的数量，最小1 最大50 默认30
	 * @param page 文章的页数， 默认1
	 * @return 搜索结果结构体
	 * @throws ClientProtocolException
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Search searchThreads(String board, String title1, String title2, String titlen,
			String author, int day, int m, int a, int count, int page) throws 
			ClientProtocolException, JSONException,NForumException, IOException {
		StringBuilder params = new StringBuilder();
		if(null != title1 && !title1.isEmpty()){
			params.append("&title1="+title1);
		}
		if(null != title2 && !title2.isEmpty()){
			params.append("&title1="+title1);
		}
		if(null != titlen && !titlen.isEmpty()){
			params.append("&title1="+title1);
		}
		if(null != author && !author.isEmpty()){
			params.append("&title1="+title1);
		}
		if(day != -1 && day != 7){
			params.append("&day="+day);
		}
		params.append("&m="+m);
		params.append("&a="+a);
		if(count != -1 && count != 30){
			params.append("&count="+count);
		}
		if(page != -1 && page != 1){
			params.append("&page="+page);
		}
		String url = host + "search/threads" + returnFormat + appkey + params.toString();
		GetMethod getMethod = new GetMethod(httpClient, auth, url);
		return Search.parse(getMethod.getJSON());
	}
	
}
