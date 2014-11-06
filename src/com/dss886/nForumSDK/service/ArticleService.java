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

import org.apache.http.impl.client.CloseableHttpClient;
import org.json.JSONException;

import com.dss886.nForumSDK.http.GetMethod;
import com.dss886.nForumSDK.http.NForumException;
import com.dss886.nForumSDK.http.PostMethod;
import com.dss886.nForumSDK.model.Article;
import com.dss886.nForumSDK.model.Threads;
import com.dss886.nForumSDK.util.ParamOption;

/**
 * 该类封装了文章和主题接口
 * 见https://github.com/xw2423/nForum/wiki/nForum-API
 * @author dss886
 * @since 2014-9-7
 */
public class ArticleService {

	private CloseableHttpClient httpClient;
	private String host;
	private String returnFormat;
	private String appkey;
	private String auth; 
	
	public ArticleService(CloseableHttpClient httpClient, String host,
			String returnFormat, String appkey, String auth){
		this.httpClient = httpClient;
		this.host = host;
		this.returnFormat = returnFormat;
		this.appkey = appkey;
		this.auth = auth;
	}
	
	/**
	 * 获取指定文章的信息
     * 可选参数：mode
	 * @param boardName 合法的版面名称
	 * @param id 文章或主题的id
	 * @return 文章元数据
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Article getArticle(String boardName, int id, ParamOption params) throws JSONException,
            NForumException, IOException {
		String url = host + "article/" + boardName + "/"+ id + returnFormat + appkey
				+ params;
		GetMethod getMethod = new GetMethod(httpClient, auth, url);
		return Article.parse(getMethod.getJSON());
	}
	
	/**
	 * 获取指定主题的信息
     * 可选参数 au, count, page
	 * @param boardName 合法的版面名称
	 * @param id 文章或主题的id
	 * @return 文章元数据，以及该元数据还包括以下两个属性：当前主题包含的文章元数据数组，和当前主题分页信息
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Threads getThreads(String boardName, int id, ParamOption params) throws 
	        JSONException, NForumException, IOException {
		String url = host + "threads/" + boardName + "/"+ id + returnFormat + appkey
				+ params;
		GetMethod getMethod = new GetMethod(httpClient, auth, url);
		return Threads.parse(getMethod.getJSON());
	}
	
	/**
	 * 发布新文章/主题
     * 可选参数：reid, signature, email, anonymous, outgo
	 * @param boardName 合法的版面名称
	 * @param title 新文章的标题
	 * @param content 新文章的内容，可以为空
	 * @return 已发表的文章元数据
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Article postArticle(String boardName, String title, String content, ParamOption params) throws
			JSONException, NForumException, IOException {
		String url = host + "article/" + boardName + "/post"  + returnFormat + appkey;
        params.addParams("title", title);
        if (null != content) {
            params.addParams("content", content);
        }
        PostMethod postMethod = new PostMethod(httpClient, auth, url, params);
		return Article.parse(postMethod.postJSON());
	}

	/**
	 * 转寄指定文章/主题
     * 可选参数 threads, noref, noatt, noansi, big5
	 * @param boardName 合法的版面名称
	 * @param id 文章或主题id
	 * @param target 合法的用户id
	 * @return 所转寄的文章/主题元数据
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Article forwardArticle(String boardName, int id, String target, ParamOption params)
            throws JSONException, NForumException, IOException {
		String url = host + "article/" + boardName + "/forward/" + id + returnFormat + appkey;
        params.addParams("target", target);
		PostMethod postMethod = new PostMethod(httpClient, auth, url, params);
		return Article.parse(postMethod.postJSON());
	}

	/**
	 * 转载指定文章
	 * @param boardName 合法的版面名称
	 * @param id 文章或主题id
	 * @param target 文章转载至的版面的名称
	 * @return 所转载的文章元数据
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Article crossArticle(String boardName, int id, String target) throws
		    JSONException, NForumException, IOException {
		String url = host + "article/" + boardName + "/cross/" + id + returnFormat + appkey;
        ParamOption params = new ParamOption().addParams("target", target);
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
	 * @throws NForumException
	 * @throws IOException
	 */
	public Article updateArticle(String boardName, int id, String title, String content) throws
		    JSONException, NForumException, IOException {
		String url = host + "article/" + boardName + "/update/" + id + returnFormat + appkey;
        ParamOption params = new ParamOption()
                .addParams("title", title)
                .addParams("content", content);
		PostMethod postMethod = new PostMethod(httpClient, auth, url, params);
		return Article.parse(postMethod.postJSON());
	}

	/**
	 * 删除指定文章
	 * @param boardName 合法的版面名称
	 * @param id 文章或主题id
	 * @return 已删除文章元数据
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Article deleteArticle(String boardName, int id) throws JSONException,
            NForumException, IOException {
		String url = host + "article/" + boardName + "/delete/" + id + returnFormat + appkey;
		PostMethod postMethod = new PostMethod(httpClient, auth, url, new ParamOption());
		return Article.parse(postMethod.postJSON());
	}
}
