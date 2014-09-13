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

import java.io.File;
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.json.JSONException;

import com.dss886.nForumSDK.http.GetMethod;
import com.dss886.nForumSDK.http.NForumException;
import com.dss886.nForumSDK.http.PostMethod;
import com.dss886.nForumSDK.model.Attachment;

/**
 * 该类封装了附件接口，
 * 见https://github.com/xw2423/nForum/wiki/nForum-API
 * @author dss886
 * @since 2014-9-7
 */
public class AttachmentService {
	
	private DefaultHttpClient httpClient; 
	private String host;
	private String returnFormat;
	private String appkey;
	private String auth; 
	
	public AttachmentService(DefaultHttpClient httpClient, String host,
			String returnFormat, String appkey, String auth){
		this.httpClient = httpClient;
		this.host = host;
		this.returnFormat = returnFormat;
		this.appkey = appkey;
		this.auth = auth;
	}
	
	/**
	 * 获取附件信息
	 * @param name 合法的版面名称
	 * @param id 可选，如果指定文章id则返回该文章的附件列表，
	 * 否则(id=-1)返回当前用户上传文件的列表，当前用户的附件列表在下一次发文时会附加至新文章中
	 * @return 返回指定文章的附件列表
	 * @throws ClientProtocolException
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Attachment getAttachment(String name, int id) throws 
		ClientProtocolException, JSONException, NForumException, IOException {
		String url;
		if(id == -1){
			url = host + "attachment/" + name + returnFormat + appkey;
		}
		url = host + "attachment/" + name + "/" + id + returnFormat + appkey;
		GetMethod getMethod = new GetMethod(httpClient, auth, url);
		return Attachment.parse(getMethod.getJSON());
	}
	
	/**
	 * 如果指定文章id则向该文章添加新附加，否则（id=-1）向当前用户的附件列表添加新附件
	 * @param boardName 合法的版面名称
	 * @param id 文章或主题id
	 * @param file 需上传的附件
	 * @return 用户空间/文章附件元数据
	 * @throws ClientProtocolException
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Attachment addAttachment(String boardName, int id, File file) throws ClientProtocolException, JSONException,
		NForumException, IOException {
		String url;
		if(id == -1){
			url = host + "attachment/" + boardName + "/add" + returnFormat + appkey;
		}
		url = host + "attachment/" + boardName + "/add" + "/" + id + returnFormat + appkey;
		FileBody fileBody = new FileBody(file);
		MultipartEntity mEntity = new MultipartEntity();
		mEntity.addPart("file", fileBody);
		PostMethod postMethod = new PostMethod(httpClient, auth, url, mEntity);
		return Attachment.parse(postMethod.postJSON());
	}
	
}
