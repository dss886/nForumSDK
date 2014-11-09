package com.dss886.nForumSDKDemo;

import com.dss886.nForumSDK.NForumSDK;
import com.dss886.nForumSDK.http.NForumException;
import com.dss886.nForumSDK.model.Board;
import com.dss886.nForumSDK.service.BoardService;
import com.dss886.nForumSDK.util.Host;
import com.dss886.nForumSDK.util.LogUtils;
import com.dss886.nForumSDK.util.ParamOption;
import org.json.JSONException;

import java.io.IOException;

/**
 * 这个Demo展示了如何使用ParamOption进行「可选参数的调用」
 * Created by dss886 on 14/11/9.
 */
public class ParamDemo {

    /**
     * 使用API默认的参数
     */
    public static void useDefaultParams() {
        NForumSDK nSDK = new NForumSDK(Host.HOST_BYR_TEST, "guest", "");
        BoardService boardService = nSDK.getBoardService();
        try {
            LogUtils.d("Use Default Parameters", "by create a empty ParamOption object");
            /* 在这里默认的count为20，page为1 */
            Board board = boardService.getBoard("SL", new ParamOption());
            LogUtils.d("Board article page count (count)", String.valueOf(board.pagination.item_page_count));
            LogUtils.d("Board page current count (page)", String.valueOf(board.pagination.page_current_count));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NForumException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用自定义的参数
     */
    public static void useCustomParams() {
        NForumSDK nSDK = new NForumSDK(Host.HOST_BYR_TEST, "guest", "");
        BoardService boardService = nSDK.getBoardService();
        try {
            LogUtils.d("Use Custom Parameters", "by create a ParamOption object and add parameters to it");
            ParamOption params = new ParamOption()
                    .addParams("count", "5")
                    .addParams("page", "2");
            Board board = boardService.getBoard("SL", params);
            LogUtils.d("Board article page count (count)", String.valueOf(board.pagination.item_page_count));
            LogUtils.d("Board page current count (page)", String.valueOf(board.pagination.page_current_count));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NForumException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
