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

import com.dss886.nForumSDK.util.ParamOption;
import org.apache.http.impl.client.DefaultHttpClient;
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

    public static final String MAILBOX_INBOX = "inbox";
    public static final String MAILBOX_OUTBOX = "outbox";
    public static final String MAILBOX_RECYCLE = "deleted";

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
     * 可选参数：count, page
	 * @param boxName 只能为inbox|outbox|deleted中的一个，分别是收件箱|发件箱|回收站，
	 * 		建议使用MailService.MAILBOX_*常量
	 * @return 信箱元数据
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Mailbox getMailbox(String boxName, ParamOption params) throws
		JSONException, NForumException, IOException {
		String url = host + "mail/"+ boxName + returnFormat + appkey + params;
		GetMethod getMethod = new GetMethod(httpClient, auth, url);
		return Mailbox.parse(getMethod.getJSON());
	}
	
	/**
	 * 信箱属性信息，包括是否有新邮件
	 * @return 信箱元数据
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Mailbox getMailboxInfo() throws
		JSONException, NForumException, IOException {
		String url = host + "mail/info" + returnFormat + appkey;
		GetMethod getMethod = new GetMethod(httpClient, auth, url);
		return Mailbox.parse(getMethod.getJSON());
	}
	
	/**
	 * 获取指定信件信息
	 * @param boxName 只能为inbox|outbox|deleted中的一个，分别是收件箱|发件箱|回收站
	 * 			建议使用MailService.MAILBOX_*常量
	 * @param num 信件在信箱的索引,为信箱信息的信件列表中每个信件对象的index值
	 * @return 信件元数据
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Mail getMail(String boxName, int num) throws
		JSONException, NForumException, IOException {
        String url = host + "mail/" + boxName + "/" + num + returnFormat + appkey;
        GetMethod getMethod = new GetMethod(httpClient, auth, url);
        return Mail.parse(getMethod.getJSON());
    }

    /**
	 * 发送新信件
     * 可选参数：title, content, signature, backup
	 * @param id 合法的用户id
	 * @return 发送成功返回true，失败则会报NForumException错误，包含错误的具体信息
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public boolean sendMail(String id, ParamOption params)
            throws JSONException, NForumException, IOException {
		String url = host + "mail/send" + returnFormat + appkey;
        params.addParams("id", id);
		PostMethod postMethod = new PostMethod(httpClient, auth, url, params);
		return postMethod.postJSON().getBoolean("status");
	}
	
	/**
	 * 转寄指定邮箱的邮件
     * 可选参数：noansi, big5
	 * @param boxName 只能为inbox|outbox|deleted中的一个，分别是收件箱|发件箱|回收站，
	 * 			建议使用MailService.MAILBOX_*常量
	 * @param num 信件在信箱的索引,为信箱信息的信件列表中每个信件对象的index值
	 * @param target 合法的用户id
	 * @return 所转寄的邮件元数据
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Mail forwardMail(String boxName, int num, String target, ParamOption params)
            throws JSONException, NForumException, IOException {
		String url = host + "mail/" + boxName + "/forward/" + num + returnFormat + appkey;
        params.addParams("target", target);
		PostMethod postMethod = new PostMethod(httpClient, auth, url, params);
		return Mail.parse(postMethod.postJSON());
	}
	
	/**
	 * 回复指定信箱中的邮件
     * 可选参数：title, content, signature, backup
	 * @param boxName 只能为inbox|outbox|deleted中的一个，分别是收件箱|发件箱|回收站，
	 * 			建议使用MailService.MAILBOX_*常量
	 * @param num 信件在信箱的索引,为信箱信息的信件列表中每个信件对象的index值
	 * @return 所回复的邮件元数据
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Mail replyMail(String boxName, int num, ParamOption params) throws JSONException,
            NForumException,IOException {
		String url = host + "mail/" + boxName + "/reply/" + num + returnFormat + appkey;
        params.addParams("boxName", boxName)
                .addParams("num", num);
		PostMethod postMethod = new PostMethod(httpClient, auth, url, params);
		return Mail.parse(postMethod.postJSON());
	}
	
	/**
	 * 删除指定信件
	 * @param boxName 只能为inbox|outbox|deleted中的一个，分别是收件箱|发件箱|回收站，
	 * 			建议使用MailService.MAILBOX_*常量
	 * @param num 信件在信箱的索引,为信箱信息的信件列表中每个信件对象的index值
	 * @return 已删除的信件元数据
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Mail deleteMail(String boxName, int num) throws
		JSONException, NForumException,IOException {
		String url = host + "mail/" + boxName + "/delete/" + num + returnFormat + appkey;
        ParamOption params = new ParamOption()
                .addParams("boxName", boxName)
                .addParams("num", num);
		PostMethod postMethod = new PostMethod(httpClient, auth, url, params);
		return Mail.parse(postMethod.postJSON());
	}
}
