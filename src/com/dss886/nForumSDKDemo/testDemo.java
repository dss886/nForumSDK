package com.dss886.nForumSDKDemo;

import java.io.File;
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.dss886.nForumSDK.NForumSDK;
import com.dss886.nForumSDK.http.NForumException;
import com.dss886.nForumSDK.service.ArticleService;
import com.dss886.nForumSDK.service.AttachmentService;
import com.dss886.nForumSDK.util.Constant;

public class testDemo {
	public static void main(String... args){
		NForumSDK nSDK = new NForumSDK(Constant.HOST_BYR, "78e223c052793f0b", "dss886", "e!D3Qe!F728x");
		AttachmentService attachmentService = nSDK.getAttachmentService();
		File file = new File("D:\\test.txt");
		try {
			attachmentService.addAttachment("Hubei", 384937, file);
		} catch (JSONException | NForumException | IOException e) {
			e.printStackTrace();
		}
	}
}
