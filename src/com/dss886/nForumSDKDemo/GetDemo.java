package com.dss886.nForumSDKDemo;

import com.dss886.nForumSDK.NForumSDK;
import com.dss886.nForumSDK.http.NForumException;
import com.dss886.nForumSDK.model.Article;
import com.dss886.nForumSDK.model.Board;
import com.dss886.nForumSDK.model.Widget;
import com.dss886.nForumSDK.service.BoardService;
import com.dss886.nForumSDK.service.WidgetService;
import com.dss886.nForumSDK.util.Host;
import com.dss886.nForumSDK.util.LogUtils;
import com.dss886.nForumSDK.util.ParamOption;
import org.json.JSONException;

import java.io.IOException;

/**
 * 这个Demo展示了如何利用本SDK「读取数据」
 * Created by dss886 on 14/11/9.
 */
public class GetDemo {

    /**
     * 得到推荐文章列表
     */
    public static void getRecommend() {
        NForumSDK nSDK = new NForumSDK(Host.HOST_BYR_TEST, "guest", "");
        WidgetService widgetService = nSDK.getWidgetService();
        try {
            Widget recommend = widgetService.getWidgetRecommend();
            LogUtils.d("Recommend articles num", String.valueOf(recommend.articles.size()));

            for (int i = 0; i < recommend.articles.size(); i++) {
                Article article = recommend.articles.get(i);
                LogUtils.d("Recommend articles " + i + " title", article.title);
                LogUtils.d("Recommend articles " + i + " user id", article.user.id);
                LogUtils.d("Recommend articles " + i + " content", article.title);
                LogUtils.d("Recommend articles " + i + " post time", String.valueOf(article.post_time));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NForumException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到版面列表
     */
    public static void getBoard() {
        NForumSDK nSDK = new NForumSDK(Host.HOST_BYR_TEST, "guest", "");
        BoardService boardService = nSDK.getBoardService();
        try {
            Board board = boardService.getBoard("SL", new ParamOption());
            LogUtils.d("Board name", board.name);
            LogUtils.d("Board section", board.section);
            LogUtils.d("Board Chinese description", board.description);
            LogUtils.d("Board manager", board.manager);
            LogUtils.d("Board article all count", String.valueOf(board.pagination.item_all_count));
            LogUtils.d("Board article page count", String.valueOf(board.pagination.item_page_count));
            LogUtils.d("Board page all count", String.valueOf(board.pagination.page_all_count));
            LogUtils.d("Board page current count", String.valueOf(board.pagination.page_current_count));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NForumException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
