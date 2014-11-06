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
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import com.dss886.nForumSDK.http.GetMethod;
import com.dss886.nForumSDK.http.NForumException;
import com.dss886.nForumSDK.model.Section;

/**
 * 该类封装了分区接口，
 * 见https://github.com/xw2423/nForum/wiki/nForum-API
 * @author dss886
 * @since 2014-9-7
 */
public class SectionService {
	
	private CloseableHttpClient httpClient;
	private String host;
	private String returnFormat;
	private String appkey;
	private String auth; 
	
	public SectionService(CloseableHttpClient httpClient, String host,
			String returnFormat, String appkey, String auth){
		this.httpClient = httpClient;
		this.host = host;
		this.returnFormat = returnFormat;
		this.appkey = appkey;
		this.auth = auth;
	}
	
	/**
	 * 获取所有根分区信息
	 * @return 根分区数量，和所有根分区元数据所组成的数组
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Section getSection() throws JSONException,
		NForumException, IOException {
		String url = host + "section" + returnFormat + appkey;
		GetMethod getMethod = new GetMethod(httpClient, auth, url);
		return Section.parse(getMethod.getJSON());
	}
	
	/**
	 * 获取指定分区的信息
	 * @param name 合法的分区名称
	 * @return 当前分区包含的分区目录名数，和当前分区包含的版面元数据数组
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Section getSection(String name) throws JSONException,
		NForumException, IOException {
		String url = host + "section/"+ name + returnFormat + appkey;
		GetMethod getMethod = new GetMethod(httpClient, auth, url);
		return Section.parse(getMethod.getJSON());
	}
	
}
