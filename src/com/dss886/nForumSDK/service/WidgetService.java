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
import org.json.JSONObject;

import com.dss886.nForumSDK.http.GetMethod;
import com.dss886.nForumSDK.http.NForumException;
import com.dss886.nForumSDK.model.Widget;

/**
 * 该类封装了Wigdet接口，
 * 见https://github.com/xw2423/nForum/wiki/nForum-API
 * @author dss886
 * @since 2014-9-7
 */
public class WidgetService {

	private DefaultHttpClient httpClient; 
	private String host;
	private String returnFormat;
	private String appkey;
	private String auth; 
	
	public WidgetService(DefaultHttpClient httpClient, String host,
			String returnFormat, String appkey, String auth){
		this.httpClient = httpClient;
		this.host = host;
		this.returnFormat = returnFormat;
		this.appkey = appkey;
		this.auth = auth;
	}
	
	public Widget getWidgetTopten() throws ClientProtocolException, JSONException,
		NForumException, IOException {
		String url = host + "widget/topten" + returnFormat + appkey;
		GetMethod getMethod = new GetMethod(httpClient, auth, url);
		return Widget.parse(getMethod.getJSON());
	}
	
	public Widget getWidgetRecommend() throws ClientProtocolException, JSONException,
		NForumException, IOException {
		String url = host + "widget/recommend" + returnFormat + appkey;
		GetMethod getMethod = new GetMethod(httpClient, auth, url);
		return Widget.parse(getMethod.getJSON());
	}
	
	public Widget getWidgetSection(int sectionName) throws ClientProtocolException,
		JSONException, NForumException, IOException {
		String url = host + "widget/section-" + sectionName + returnFormat + appkey;
		GetMethod getMethod = new GetMethod(httpClient, auth, url);
		return Widget.parse(getMethod.getJSON());
	}
}
