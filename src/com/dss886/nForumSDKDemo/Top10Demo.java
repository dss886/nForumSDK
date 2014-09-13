package com.dss886.nForumSDKDemo;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.dss886.nForumSDK.NForumSDK;
import com.dss886.nForumSDK.http.NForumException;
import com.dss886.nForumSDK.service.WidgetService;
import com.dss886.nForumSDK.util.Constant;
import com.dss886.nForumSDK.util.Log;

public class Top10Demo {
	public static void main(String... args){
		NForumSDK nSDK = new NForumSDK(Constant.HOST_TDRD, "guest", "");
		WidgetService widgetService = nSDK.getWidgetService();
		try {
			JSONObject top10 = widgetService.getWidgetTop10();
			Log.d("top10", top10.toString());
		} catch (JSONException | NForumException
				| IOException e) {
			e.printStackTrace();
		}
	}
}
