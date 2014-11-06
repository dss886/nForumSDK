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
package com.dss886.nForumSDK.util;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 该类封装了调用API时的可选参数的设置方法，
 * 拥有可选参数的Service方法需要本类进行参数传递，
 * 但如不选，可置null。
 * 多余的参数将会被忽略。
 * Created by dss886 on 14/11/6.
 */
public class ParamOption {

    Map<String, String> params;
    List<NameValuePair> nameValuePairs;

    public ParamOption() {
        params = new HashMap<String, String>();
    }

    public ParamOption(List<NameValuePair> nameValuePairs) {
        this.nameValuePairs = nameValuePairs;
    }

    /**
     * 添加参数名和参数值，
     * 返回本对象共链式调用。
     */
    public ParamOption addParams(String key, String value) {
        params.put(key, value);
        return this;
    }

    /**
     * 添加参数名和参数值，
     * 返回本对象共链式调用。
     */
    public ParamOption addParams(String key, int value) {
        params.put(key, String.valueOf(value));
        return this;
    }

    /**
     * 添加参数名和参数值，
     * 返回本对象共链式调用。
     */
    public ParamOption addParams(String key, boolean value) {
        params.put(key, String.valueOf(value));
        return this;
    }

    @Override
    public String toString() {
        String result = "";

        String[] tempArray = new String[params.size()];
        for (String key : params.keySet().toArray(tempArray)) {
            result = result + "&" + key + "=" + params.get(key);
        }

        return result;
    }

    public Map<String, String> getParams() {
        return params;
    }


    public List<NameValuePair> toNamePair() {
        if (nameValuePairs != null) {
            return nameValuePairs;
        }
        List<NameValuePair> nameValues = new ArrayList<NameValuePair>();
        String[] tempArray = new String[params.size()];
        for (String key : params.keySet().toArray(tempArray)) {
            nameValues.add(new BasicNameValuePair(key, params.get(key)));
        }
        return nameValues;
    }

}
