/*
 * Copyright (C) 2010-2013 dss886
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
package com.dss886.nForumSDK.http;

import java.io.IOException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.dss886.nForumSDK.util.Constant;

/**
 * 该类封装了HTTP Post方法
 * @author dss886
 * @since 2014-9-7
 */
public class PostMethod {

	private DefaultHttpClient httpClient;
	private HttpPost httpPost;
	private HttpResponse response;
	private List<NameValuePair> params;
	
	public PostMethod(DefaultHttpClient httpClient, String auth, String url, List<NameValuePair> params){
		this.httpClient = httpClient;
		this.params = params;
		httpPost = new HttpPost(url);
		httpPost.setHeader("Accept-Encoding", "gzip, deflate");
		httpPost.setHeader("Authorization", "Basic " + auth);
	}
	
	public JSONObject postJSON() throws JSONException, NForumException, 
		ClientProtocolException, IOException{
		if (params != null) {
			httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		}
		response = httpClient.execute(httpPost);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != 200)
			throw new NForumException(Constant.EXCEPTION_NETWORK + ":" + statusCode);
		Header ceheader = response.getEntity().getContentEncoding();
		if (ceheader != null) {
			for (HeaderElement element : ceheader.getElements()) {
				if (element.getName().equalsIgnoreCase("gzip")) {
					response.setEntity(new GzipDecompressingEntity(response.getEntity()));
					break;
				}
			}
		}
		String result = ResponseProcessor.getStringFromResponse(response);
		httpPost.abort();
		
		JSONObject json = new JSONObject(result);
		if(json.has("msg")){
			throw new NForumException(json.getString("msg"));
		}
		return json;
	}
}
