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
import com.dss886.nForumSDK.model.Article;

/**
 * 该类封装了文章和主题接口
 * 见https://github.com/xw2423/nForum/wiki/nForum-API
 * @author dss886
 * @since 2014-9-7
 */
public class ArticleService {

	private DefaultHttpClient httpClient; 
	private String host;
	private String returnFormat;
	private String appkey;
	private String auth; 
	
	public ArticleService(DefaultHttpClient httpClient, String host,
			String returnFormat, String appkey, String auth){
		this.httpClient = httpClient;
		this.host = host;
		this.returnFormat = returnFormat;
		this.appkey = appkey;
		this.auth = auth;
	}
	
	/**
	 * 获取指定文章的信息
	 * @param boardName 合法的版面名称
	 * @param id 文章或主题的id
	 * @param mode 可选，文章所在版面的模式，访问文摘、保留、回收站、垃圾箱中的文章需要指定mode，默认为2
	 * @return 文章元数据
	 * @throws ClientProtocolException
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Article getArticle(String boardName, int id, int mode) throws 
		ClientProtocolException, JSONException, NForumException, IOException {
		if(mode<=0 || mode>=6){
			mode = 2;
		}
		String url = host + "article/" + boardName + "/"+ id + returnFormat + appkey
				+ "&mode=" + mode;
		GetMethod getMethod = new GetMethod(httpClient, auth, url);
		return Article.parse(getMethod.getJSON());
	}
	
	/**
	 * 获取指定主题的信息
	 * @param boardName 合法的版面名称
	 * @param id 文章或主题的id
	 * @param au 可选，只显示该主题中某一用户的文章，au为该用户的用户名
	 * @param count 可选，每页文章的数量，默认为10
	 * @param page 可选，文章的页数，默认为1
	 * @return 文章元数据，以及该元数据还包括以下两个属性：当前主题包含的文章元数据数组，和当前主题分页信息
	 * @throws ClientProtocolException
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Article getThreads(String boardName, int id, String au, int count, int page) throws 
		ClientProtocolException, JSONException, NForumException, IOException {
		String url = host + "threads/" + boardName + "/"+ id + returnFormat + appkey
				+ "&count=" + count +"&page=" + page;
		if(au != null){
			url = url + "&au=" + au ;
		}
		if(count > 0){
			url = url + "&count=" + count ;
		}
		if(page > 0){
			url = url + "&page=" + page ;
		}
		GetMethod getMethod = new GetMethod(httpClient, auth, url);
		return Article.parse(getMethod.getJSON());
	}
	
	/**
	 * 发布新文章/主题
	 * @param boardName 合法的版面名称
	 * @param title 新文章的标题
	 * @param content 新文章的内容，可以为空
	 * @param reid 可选，新文章回复其他文章的id
	 * @param signature 可选，新文章使用的签名档，0为不使用，从1开始使用第几个，默认使用上一次的签名档
	 * @param email 可选，新文章是否启用回文转寄，0不使用，1使用，默认为0
	 * @param anonymous 可选，新文章是否匿名发表，0不匿名，1匿名，默认为0，若为匿名发表需要版面属性允许匿名发文
	 * @param outgo 可选，新文章是否对外专信，0不转信，1转信，默认为0，需要版面属性允许转信
	 * @return 已发表的文章元数据
	 * @throws ClientProtocolException
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Article postArticle(String boardName, String title, String content, int reid,
			int signature, int email, int anonymous, int outgo) throws ClientProtocolException,
			JSONException, NForumException, IOException {
		String url = host + "article/" + boardName + "/post"  + returnFormat + appkey;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("title", title));
		params.add(new BasicNameValuePair("content", content));
		if (reid >= 0) {
			params.add(new BasicNameValuePair("reid", reid + ""));
		}
		if (signature >= 0) {
			params.add(new BasicNameValuePair("signature", signature + ""));
		}
		params.add(new BasicNameValuePair("email", email + ""));
		params.add(new BasicNameValuePair("anonymous", anonymous + ""));
		params.add(new BasicNameValuePair("outgo", outgo + ""));
		
		PostMethod postMethod = new PostMethod(httpClient, auth, url, params);
		return Article.parse(postMethod.postJSON());
	}
	
	/**
	 * 转寄指定文章/主题
	 * @param boardName 合法的版面名称
	 * @param id 文章或主题id
	 * @param target 合法的用户id
	 * @param threads 可选，是否合集转寄该文章所在的主题，0:否，1:同主题合集转寄，默认为0
	 * @param noref 可选，在threads为1时，是否不保留引文，0:保留，1:不保留，默认为0
	 * @param noatt 可选，是否不保留附件，0:保留，1:不保留，默认为0
	 * @param noansi 可选，是否不保留ansi字符，0:保留，1:不保留，默认为0
	 * @param big5 可选，是否使用big5编码，0:不使用，1:使用，默认为0
	 * @return 所转寄的文章/主题元数据
	 * @throws ClientProtocolException
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Article forwardArticle(String boardName, int id, String target, int threads,
			int noref, int noatt, int noansi, int big5) throws ClientProtocolException,
			JSONException, NForumException, IOException {
		String url = host + "article/" + boardName + "/forward/" + id + returnFormat + appkey;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("target", target));
		params.add(new BasicNameValuePair("threads", threads+""));
		params.add(new BasicNameValuePair("noref", noref + ""));
		params.add(new BasicNameValuePair("noatt", noref + ""));
		params.add(new BasicNameValuePair("noansi", noref + ""));
		params.add(new BasicNameValuePair("big5", noref + ""));
		PostMethod postMethod = new PostMethod(httpClient, auth, url, params);
		return Article.parse(postMethod.postJSON());
	}
	
	/**
	 * 转载指定文章
	 * @param boardName 合法的版面名称
	 * @param id 文章或主题id
	 * @param target 合法的用户id
	 * @return 所转载的文章元数据
	 * @throws ClientProtocolException
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Article crossArticle(String boardName, int id, String target) throws 
		ClientProtocolException, JSONException, NForumException, IOException {
		String url = host + "article/" + boardName + "/cross/" + id + returnFormat + appkey;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("target", target));
		PostMethod postMethod = new PostMethod(httpClient, auth, url, params);
		return Article.parse(postMethod.postJSON());
	}
	
	/**
	 * 更新指定文章
	 * @param boardName 合法的版面名称
	 * @param id 文章或主题id
	 * @param title 修改后的文章标题
	 * @param content 修改后的文章内容
	 * @return 已更新的文章元数据
	 * @throws ClientProtocolException
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Article updateArticle(String boardName, int id, String title, String content) throws 
		ClientProtocolException, JSONException, NForumException, IOException {
		String url = host + "article/" + boardName + "/update/" + id + returnFormat + appkey;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("title", title));
		params.add(new BasicNameValuePair("content", content));
		PostMethod postMethod = new PostMethod(httpClient, auth, url, params);
		return Article.parse(postMethod.postJSON());
	}
	
	/**
	 * 删除指定文章
	 * @param boardName 合法的版面名称
	 * @param id 文章或主题id
	 * @return 已删除文章元数据
	 * @throws ClientProtocolException
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Article deleteArticle(String boardName, int id) throws ClientProtocolException,
		JSONException, NForumException, IOException {
		String url = host + "article/" + boardName + "/delete/" + id + returnFormat + appkey;
		PostMethod postMethod = new PostMethod(httpClient, auth, url, null);
		return Article.parse(postMethod.postJSON());
	}
}
