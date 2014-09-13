package com.dss886.nForumSDKDemo;

import java.io.IOException;

import org.json.JSONException;

import com.dss886.nForumSDK.NForumSDK;
import com.dss886.nForumSDK.http.NForumException;
import com.dss886.nForumSDK.service.WidgetService;
import com.dss886.nForumSDK.util.Constant;
import com.dss886.nForumSDK.util.Log;

public class testDemo {
	public static void main(String... args){
		NForumSDK nSDK = new NForumSDK(Constant.HOST_BYR, "78e223c052793f0b", "guest", "");
		WidgetService service = nSDK.getWidgetService();
		try {
			Log.d("testDemo", service.getWidgetSection(1).articles.get(0).user.id);
		} catch (JSONException | NForumException | IOException e) {
			e.printStackTrace();
		}
	}
}
