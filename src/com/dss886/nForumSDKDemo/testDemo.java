package com.dss886.nForumSDKDemo;

import java.io.IOException;

import org.json.JSONException;

import com.dss886.nForumSDK.NForumSDK;
import com.dss886.nForumSDK.http.NForumException;
import com.dss886.nForumSDK.model.Article;
import com.dss886.nForumSDK.model.Board;
import com.dss886.nForumSDK.model.Section;
import com.dss886.nForumSDK.model.User;
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
import com.dss886.nForumSDK.util.Constant;
import com.dss886.nForumSDK.util.Log;

public class testDemo {
	public static void main(String... args){
		NForumSDK nSDK = new NForumSDK(Constant.HOST_BYR, "", "guest", "");
//		NForumSDK nSDK = new NForumSDK(Constant.HOST_TDRD, "guest", "");
		
		UserService userService = nSDK.getUserService();
		SectionService sectionService = nSDK.getSectionService();
		BoardService boardService = nSDK.getBoardService();
		ArticleService articleService = nSDK.getArticleService();
		AttachmentService attachmentService = nSDK.getAttachmentService();
		MailService mailService = nSDK.getMailService();
		FavouriteService favouriteService = nSDK.getFavouriteService();
		ReferService referService = nSDK.getReferService();
		SearchService searchService = nSDK.getSearchService();
		WidgetService widgetService = nSDK.getWidgetService();
		BlacklistService blacklistService = nSDK.getBlacklistService();
		VoteService voteService = nSDK.getVoteService();
		
		try {
//			User user;
//			user = userService.queryById("youting");
//			user = userService.login();
//			user = userService.logout();
//			
//			Section section;
//			section = sectionService.getSection();
//			section = sectionService.getSection("5");
//			
//			Board board;
//			board = boardService.getBoard("BBSHELP", 2, 30, 1);
			
			Article article;
			article = articleService.getArticle("BBSHELP", 2, 2);
			article = articleService.getThreads("BBSHELP", 2, null, 10, 1);
			
			mailService.sendMail("dss886", "test", "test2", 0, 0);
		} catch (JSONException | NForumException | IOException e) {
			e.printStackTrace();
		}
	}
}
