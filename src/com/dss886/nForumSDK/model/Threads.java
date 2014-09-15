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
package com.dss886.nForumSDK.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 主题结构体
 * @author dss886
 * @since 2014-9-7
 */
public class Threads {
	
	/** 当前主题包含的文章元数据数组 */
	public List<Article> articles = new ArrayList<Article>();
	/** 当前主题分页信息 */
	public Pagination pagination;
	
	public static Threads parse(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            return Threads.parse(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
	
	public static Threads parse(JSONObject jsonObject) {
        if (null == jsonObject) {
            return null;
        }
        Threads threads = new Threads();
        JSONArray jsonArticles = jsonObject.optJSONArray("article");
        if(null != jsonArticles){
        	for(int i = 0; i < jsonArticles.length(); i++){
        		threads.articles.add(Article.parse(jsonArticles.optJSONObject(i)));
        	}
        }
        threads.pagination = Pagination.parse(jsonObject.optJSONObject("pagination"));
        return threads;
	}
}
