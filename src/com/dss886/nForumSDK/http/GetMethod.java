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
package com.dss886.nForumSDK.http;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 该类封装了HTTP Get方法
 * @author dss886
 * @since 2014-9-7
 */
public class GetMethod {
	
	private DefaultHttpClient httpClient;
	private HttpGet httpGet;

    public GetMethod(DefaultHttpClient httpClient, String auth, String url){
		this.httpClient = httpClient;
		httpGet = new HttpGet(url);
		httpGet.setHeader("Accept-Encoding", "gzip, deflate");
		httpGet.setHeader("Authorization", "Basic " + auth);
	}
	
	public JSONObject getJSON() throws JSONException, NForumException, IOException{
        HttpResponse response = httpClient.execute(httpGet);
		
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != 200)
			throw new NForumException(NForumException.EXCEPTION_NETWORK + ":" + statusCode);
		Header header = response.getEntity().getContentEncoding();
		if (header != null) {
			for (HeaderElement element : header.getElements()) {
				if (element.getName().equalsIgnoreCase("gzip")) {
					response.setEntity(new GzipDecompressingEntity(response.getEntity()));
					break;
				}
			}
		}
		String result = ResponseProcessor.getStringFromResponse(response);
		httpGet.abort();
		
		JSONObject json = new JSONObject(result);
		if(json.has("msg")){
			throw new NForumException(json.getString("msg"));
		}
		return json;
	}
	
}
