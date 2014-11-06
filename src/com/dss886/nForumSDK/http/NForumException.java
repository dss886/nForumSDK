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

/**
 * 自定义异常类
 * @author dss886
 * @since 2014-9-7
 */
public class NForumException extends Exception{

	private static final long serialVersionUID = 6109206721671985192L;
    public static final String EXCEPTION_NETWORK = "网络异常";

	public NForumException(){
        super();
    }   
	
    public NForumException(String message){
        super(message);
    }

}
