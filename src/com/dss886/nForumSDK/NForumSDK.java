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
package com.dss886.nForumSDK;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.dss886.nForumSDK.service.ArticleService;
import com.dss886.nForumSDK.service.AttachmentService;
import com.dss886.nForumSDK.service.BlacklistService;
import com.dss886.nForumSDK.service.BoardService;
import com.dss886.nForumSDK.service.FavouriteService;
import com.dss886.nForumSDK.service.MailService;
import com.dss886.nForumSDK.service.ReferService;
import com.dss886.nForumSDK.service.SearchService;
import com.dss886.nForumSDK.service.SectionService;
import com.dss886.nForumSDK.service.UserService;
import com.dss886.nForumSDK.service.VoteService;
import com.dss886.nForumSDK.service.WidgetService;
import com.dss886.nForumSDK.util.Host;

/**
 * SDK主类，封装了一些参数设置函数，
 * 并能返回用于调用接口的对象
 * @author dss886
 * @since 2014-9-7
 */
public class NForumSDK {
	
	private CloseableHttpClient httpClient;
	private String host;
	private String appkey;
	private String auth;
	
	private String returnFormat = Host.RETURN_FORMAT_JSON;

	/**
	 * 需要appkey的构造函数
	 * @param host 需要包括完整的协议和尾部的斜杠，例如：http://api.byr.cn/，
	 * 			或者使用 Host.HOST_* 常量。
	 * @param appkey 不包含参数名"?appkey="和尾部多余的"&"字符
	 * @param username 用户名
	 * @param password 密码
	 */
	public NForumSDK(String host, String appkey, String username, String password){
		httpClient = HttpClients.createDefault();
		auth = new String(Base64.encodeBase64((username + ":" + password).getBytes()));
		this.host = host;
		this.returnFormat = returnFormat + "?";
		this.appkey = "&appkey=" + appkey;
	}
	
	/**
	 * 不需要appkey的构造函数
	 * @param host 需要包括完整的协议和尾部的斜杠，例如：http://api.byr.cn/，
	 * 			或者使用 HOST.HOST_* 常量。
	 * @param username 用户名
	 * @param password 密码
	 */
	public NForumSDK(String host, String username, String password) {
        httpClient = HttpClients.createDefault();
        auth = new String(Base64.encodeBase64((username + ":" + password).getBytes()));
        this.host = host;
        this.returnFormat = returnFormat + "?";
        this.appkey = "";
    }

	/**
	 * @return 用户接口封装对象
	 */
	public UserService getUserService(){
		return new UserService(httpClient, host, returnFormat, appkey, auth);
	}
	
	/**
	 * @return 分区接口封装对象
	 */
	public SectionService getSectionService(){
		return new SectionService(httpClient, host, returnFormat, appkey, auth);
	}
	
	/**
	 * @return 版面接口封装对象
	 */
	public BoardService getBoardService(){
		return new BoardService(httpClient, host, returnFormat, appkey, auth);
	}
	
	/**
	 * @return 文章接口封装对象
	 */
	public ArticleService getArticleService(){
		return new ArticleService(httpClient, host, returnFormat, appkey, auth);
	}
	
	/**
	 * @return 附件接口封装对象
	 */
	public AttachmentService getAttachmentService(){
		return new AttachmentService(httpClient, host, returnFormat, appkey, auth);
	}
	
	/**
	 * @return 信件接口封装对象
	 */
	public MailService getMailService(){
		return new MailService(httpClient, host, returnFormat, appkey, auth);
	}
	
	/**
	 * @return 收藏夹接口封装对象
	 */
	public FavouriteService getFavouriteService(){
		return new FavouriteService(httpClient, host, returnFormat, appkey, auth);
	}
	
	/**
	 * @return 提醒接口封装对象
	 */
	public ReferService getReferService(){
		return new ReferService(httpClient, host, returnFormat, appkey, auth);
	}
	
	/**
	 * @return 搜索接口封装对象
	 */
	public SearchService getSearchService(){
		return new SearchService(httpClient, host, returnFormat, appkey, auth);
	}
	
	/**
	 * @return Widget接口封装对象
	 */
	public WidgetService getWidgetService(){
		return new WidgetService(httpClient, host, returnFormat, appkey, auth);
	}
	
	/**
	 * @return 黑名单接口封装对象
	 */
	public BlacklistService getBlacklistService(){
		return new BlacklistService(httpClient, host, returnFormat, appkey, auth);
	}
	
	/**
	 * @return Widget接口封装对象
	 */
	public VoteService getVoteService(){
		return new VoteService(httpClient, host, returnFormat, appkey, auth);
	}
	
}
