package com.dss886.nForumSDKDemo;

import java.io.IOException;

import org.json.JSONException;

import com.dss886.nForumSDK.NForumSDK;
import com.dss886.nForumSDK.http.NForumException;
import com.dss886.nForumSDK.service.MailService;
import com.dss886.nForumSDK.util.Constant;

public class testDemo {
	public static void main(String... args){
		NForumSDK nSDK = new NForumSDK(Constant.HOST_BYR, "", "guest", "");
		MailService service = nSDK.getMailService();
		try {
			service.sendMail("dss886", "test", "test2", 0, 0);
		} catch (JSONException | NForumException | IOException e) {
			e.printStackTrace();
		}
	}
}
