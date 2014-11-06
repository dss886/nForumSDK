package com.dss886.nForumSDKDemo;

import java.io.File;
import java.io.IOException;

import org.json.JSONException;

import com.dss886.nForumSDK.NForumSDK;
import com.dss886.nForumSDK.http.NForumException;
import com.dss886.nForumSDK.model.Article;
import com.dss886.nForumSDK.model.Attachment;
import com.dss886.nForumSDK.model.Board;
import com.dss886.nForumSDK.model.Favorite;
import com.dss886.nForumSDK.model.Mail;
import com.dss886.nForumSDK.model.Mailbox;
import com.dss886.nForumSDK.model.Search;
import com.dss886.nForumSDK.model.Section;
import com.dss886.nForumSDK.model.Threads;
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
//			
//			Article article;
//			article = articleService.getArticle("BBSHELP", 2, 2);
//			Threads threads = articleService.getThreads("BBSHELP", 2, null, 10, 1);
//			article = articleService.postArticle("TEST", "test nForumSDK", "hah", -1, -1, 0, 0, 0);
//			articleService.forwardArticle("TEST", 72644, "dss886", 0, 0, 0, 0, 0);
//			articleService.crossArticle("TEST", 72644, "Hubei");
//			articleService.updateArticle("TEST", 72644, "test nForumSDK", "test updatePost() method\n--");
//			articleService.deleteArticle("Hubei", 384945);
//			
//			Attachment att;
//			att = attachmentService.getAttachment("Hubei", 384945);
//			att = attachmentService.addAttachment("Hubei", 384945, new File("D:\\test.txt"));
//			att = attachmentService.delAttachment("Hubei", 384945, "test.txt");
//			
//			Mail mail;
//			Mailbox mailbox;
//			mailbox = mailService.getMailbox(Constant.MAILBOX_INBOX, 20, 1);
//			mailbox = mailService.getMailboxInfo();
//			mail = mailService.getMail(Constant.MAILBOX_INBOX, 1);
//			mailService.sendMail("dss886", "test", "test2", 0, 0);
//			mailService.forwardMail(Constant.MAILBOX_INBOX, 1, "dss886", 0, 0);
//			mailService.replyMail(Constant.MAILBOX_INBOX, 1, "Re: mail index=1", "test replyMail", -1, 0);
//			mailService.deleteMail(Constant.MAILBOX_INBOX, 373);
//			
			Favorite fav;
			fav = favouriteService.getFavourite(0);
			favouriteService.addFavourite(0, "Flash", 0);
			favouriteService.delFavourite(0, "Flash", 0);
			
			Search search;
//			search = searchService.searchBoard("3");
			int i = 1;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
