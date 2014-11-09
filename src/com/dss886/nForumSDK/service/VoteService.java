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
import org.apache.http.impl.client.CloseableHttpClient;
import org.json.JSONException;

import com.dss886.nForumSDK.http.GetMethod;
import com.dss886.nForumSDK.http.NForumException;
import com.dss886.nForumSDK.http.PostMethod;
import com.dss886.nForumSDK.model.Vote;

/**
 * 该类封装了投票接口，
 * 见https://github.com/xw2423/nForum/wiki/nForum-API
 * @author dss886
 * @since 2014-9-7
 */
public class VoteService {

    public static final String CATEGORY_ME = "me";
    public static final String CATEGORY_JOIN = "join";
    public static final String CATEGORY_LIST = "list";
    public static final String CATEGORY_NEW = "new";
    public static final String CATEGORY_HOT = "hot";
    public static final String CATEGORY_ALL = "all";

	private CloseableHttpClient httpClient;
	private String host;
	private String returnFormat;
	private String appkey;
	private String auth; 
	
	public VoteService(CloseableHttpClient httpClient, String host,
			String returnFormat, String appkey, String auth){
		this.httpClient = httpClient;
		this.host = host;
		this.returnFormat = returnFormat;
		this.appkey = appkey;
		this.auth = auth;
	}
	
	/**
	 * 获取投票信息
	 * @param vid 投票vid
	 * @return 投票元数据
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Vote getVote(int vid) throws JSONException,
		NForumException, IOException {
		String url = host + "vote/" + vid + returnFormat + appkey;
		GetMethod getMethod = new GetMethod(httpClient, auth, url);
        return Vote.parse(getMethod.getJSON());
	}


    /**
     * 投票操作（单选）
     *
     * @param vid  投票vid
     * @param viid 所提交的投票选项
     * @return 投票元数据
     * @throws JSONException
     * @throws NForumException
     * @throws IOException
     */
    public Vote vote(int vid, int viid) throws
            JSONException, NForumException, IOException {
        String url = host + "vote/" + vid + returnFormat + appkey;
        ParamOption params = new ParamOption()
                .addParams("vote", viid);
        PostMethod postMethod = new PostMethod(httpClient, auth, url, params);
        return Vote.parse(postMethod.postJSON());
    }

    /**
     * 投票操作（多选）
     *
     * @param vid   投票vid
     * @param viids 所有提交的投票选项项的viid集合
     * @return 投票元数据
     * @throws JSONException
     * @throws NForumException
     * @throws IOException
     */
    public Vote vote(int vid, int[] viids) throws
            JSONException, NForumException, IOException {
        String url = host + "vote/" + vid + returnFormat + appkey;
        ParamOption params = new ParamOption();
        for (int i = 0; i < viids.length; i++) {
            params.addParams("vote[" + i + "]", viids[i]);
        }
        PostMethod postMethod = new PostMethod(httpClient, auth, url, params);
        return Vote.parse(postMethod.postJSON());
    }

    /**
	 * 获取投票列表
	 * @param category 投票列表类型，取值为me|join|list|new|hot|all，
	 *		建议使用VoteService.CATEGORY_*常量
	 * @return 投票元数据
	 * @throws JSONException
	 * @throws NForumException
	 * @throws IOException
	 */
	public Vote getVoteList(String category, ParamOption params) throws JSONException,
		NForumException, IOException {
		String url = host + "vote/category/" + category + returnFormat + appkey + params;
		GetMethod getMethod = new GetMethod(httpClient, auth, url);
		return Vote.parse(getMethod.getJSON());
	}
	
}
