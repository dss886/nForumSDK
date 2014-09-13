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
import com.dss886.nForumSDK.model.Mail;
import com.dss886.nForumSDK.model.Mailbox;

/**
 * 该类封装了信箱及信件接口，
 * 见https://github.com/xw2423/nForum/wiki/nForum-API
 * @author dss886
 * @since 2014-9-7
 */
public class MailService {

	private DefaultHttpClient httpClient; 
	private String host;
	private String returnFormat;
	private String appkey;
	private String auth; 
	
	public MailService(DefaultHttpClient httpClient, String host,
			String returnFormat, String appkey, String auth){
		this.httpClient = httpClient;
		this.host = host;
		this.returnFormat = returnFormat;
		this.appkey = appkey;
		this.auth = auth;
	}
	
	/**
	 * 获取指定信箱信息
	 * @param boxName 只能为inbox|outbox|deleted中的一个，分别是收件箱|发件箱|回收站，
	 * 		建议使用Constant.MAILBOX*常量
	 * @param count 每页信件的数量，最小1 最大50 默认20
	 * @param page 信箱的页数，默认1
	 * @return 信箱元数据
	 * @throws ClientProtocolException
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Mailbox getMailbox(String boxName, int count, int page) throws ClientProtocolException,
		JSONException, NForumException, IOException {
		String url = host + "mail/"+ boxName + returnFormat + appkey 
				+ "&count=" + count +"&page=" + page;
		GetMethod getMethod = new GetMethod(httpClient, auth, url);
		return Mailbox.parse(getMethod.getJSON());
	}
	
	/**
	 * 信箱属性信息，包括是否有新邮件
	 * @return 信箱元数据
	 * @throws ClientProtocolException
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Mailbox getMailboxInfo() throws ClientProtocolException,
		JSONException, NForumException, IOException {
		String url = host + "mail/info" + returnFormat + appkey;
		GetMethod getMethod = new GetMethod(httpClient, auth, url);
		return Mailbox.parse(getMethod.getJSON());
	}
	
	/**
	 * 获取指定信件信息
	 * @param boxName 只能为inbox|outbox|deleted中的一个，分别是收件箱|发件箱|回收站
	 * 			建议使用Constant.MAILBOX*常量
	 * @param num 信件在信箱的索引,为信箱信息的信件列表中每个信件对象的index值
	 * @return 信件元数据
	 * @throws ClientProtocolException
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Mail getMail(String boxName, int num) throws ClientProtocolException,
		JSONException, NForumException, IOException {
		String url = host + "mail/" + boxName + "/" + num +returnFormat + appkey;
		GetMethod getMethod = new GetMethod(httpClient, auth, url);
		return Mail.parse(getMethod.getJSON());
	}
	
	/**
	 * 发送新信件
	 * @param id 合法的用户id
	 * @param title 信件的标题
	 * @param content 信件的内容
	 * @param signature 信件使用的签名档，0为不使用，从1开始表示使用第几个签名档，默认使用上一次的签名档
	 * @param backup 是否备份到发件箱，0为不备份，1为备份，默认为0
	 * @return 发送成功返回true，失败则会报NForumException错误，包含错误的具体信息
	 * @throws ClientProtocolException
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public boolean sendMail(String id, String title, String content, int signature,
			int backup) throws ClientProtocolException,JSONException, NForumException,
			IOException {
		String url = host + "mail/send" + returnFormat + appkey;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", id));
		params.add(new BasicNameValuePair("title", title));
		params.add(new BasicNameValuePair("content", content));
		params.add(new BasicNameValuePair("signature", signature+""));
		params.add(new BasicNameValuePair("backup", backup+""));
		PostMethod postMethod = new PostMethod(httpClient, auth, url, params);
		return postMethod.postJSON().getBoolean("status");
	}
	
	/**
	 * 转寄指定邮箱的邮件
	 * @param boxName 只能为inbox|outbox|deleted中的一个，分别是收件箱|发件箱|回收站，
	 * 			建议使用Constant.MAILBOX*常量
	 * @param num 信件在信箱的索引,为信箱信息的信件列表中每个信件对象的index值
	 * @param target 合法的用户id
	 * @param noansi 是否不保留ansi字符，0:保留，1:不保留，默认为0
	 * @param big5 是否使用big5编码，0:不使用，1:使用，默认为0
	 * @return 所转寄的邮件元数据
	 * @throws ClientProtocolException
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Mail forwardMail(String boxName, int num, String target, int noansi,
			int big5) throws ClientProtocolException,JSONException, NForumException,
			IOException {
		String url = host + "mail/" + boxName + "/forward/" + num + returnFormat + appkey;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("target", target));
		params.add(new BasicNameValuePair("noansi", noansi+""));
		params.add(new BasicNameValuePair("big5", big5+""));
		PostMethod postMethod = new PostMethod(httpClient, auth, url, params);
		return Mail.parse(postMethod.postJSON());
	}
	
	/**
	 * 回复指定信箱中的邮件
	 * @param boxName 只能为inbox|outbox|deleted中的一个，分别是收件箱|发件箱|回收站，
	 * 			建议使用Constant.MAILBOX*常量
	 * @param num 信件在信箱的索引,为信箱信息的信件列表中每个信件对象的index值
	 * @param title 信件的标题
	 * @param content 信件的内容
	 * @param signature 信件使用的签名档，0为不使用，从1开始表示使用第几个签名档，默认使用上一次的签名档
	 * @param backup 是否备份到发件箱，0为不备份，1为备份，默认为0
	 * @return 所回复的邮件元数据
	 * @throws ClientProtocolException
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Mail replyMail(String boxName, int num, String title, String content, int signature,
			int backup) throws ClientProtocolException,JSONException, NForumException,IOException {
		String url = host + "mail/" + boxName + "/reply/" + num + returnFormat + appkey;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("boxName", boxName));
		params.add(new BasicNameValuePair("num", num+""));
		params.add(new BasicNameValuePair("title", title));
		params.add(new BasicNameValuePair("content", content));
		params.add(new BasicNameValuePair("signature", signature+""));
		params.add(new BasicNameValuePair("backup", backup+""));
		PostMethod postMethod = new PostMethod(httpClient, auth, url, params);
		return Mail.parse(postMethod.postJSON());
	}
	
	/**
	 * 删除指定信件
	 * @param boxName 只能为inbox|outbox|deleted中的一个，分别是收件箱|发件箱|回收站，
	 * 			建议使用Constant.MAILBOX*常量
	 * @param num 信件在信箱的索引,为信箱信息的信件列表中每个信件对象的index值
	 * @return 已删除的信件元数据
	 * @throws ClientProtocolException
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Mail deleteMail(String boxName, int num) throws ClientProtocolException,
		JSONException, NForumException,IOException {
		String url = host + "mail/" + boxName + "/delete/" + num + returnFormat + appkey;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("boxName", boxName));
		params.add(new BasicNameValuePair("num", num+""));
		PostMethod postMethod = new PostMethod(httpClient, auth, url, params);
		return Mail.parse(postMethod.postJSON());
	}
}
