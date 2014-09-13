package com.dss886.nForumSDKDemo;

import java.io.IOException;

import org.json.JSONException;

import com.dss886.nForumSDK.NForumSDK;
import com.dss886.nForumSDK.http.NForumException;
import com.dss886.nForumSDK.service.AttachmentService;
import com.dss886.nForumSDK.util.Constant;

public class testDemo {
	public static void main(String... args){
		NForumSDK nSDK = new NForumSDK(Constant.HOST_BYR, "78e223c052793f0b", "dss886", "e!D3Qe!F728x");
		AttachmentService attachmentService = nSDK.getAttachmentService();
		try {
			attachmentService.delAttachment("Hubei", 384937, "test.txt");
		} catch (JSONException | NForumException | IOException e) {
			e.printStackTrace();
		}
	}
}
