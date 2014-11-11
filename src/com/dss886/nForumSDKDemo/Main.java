package com.dss886.nForumSDKDemo;

import com.dss886.nForumSDK.NForumSDK;
import com.dss886.nForumSDK.http.NForumException;
import com.dss886.nForumSDK.model.Vote;
import com.dss886.nForumSDK.service.VoteService;
import com.dss886.nForumSDK.util.Host;
import com.dss886.nForumSDK.util.LogUtils;
import org.json.JSONException;

import java.io.IOException;

public class Main {
	public static void main(String... args) {
//        GetDemo.getRecommend();
//        GetDemo.getBoard();
//        ParamDemo.useDefaultParams();
//        ParamDemo.useCustomParams();

        NForumSDK nSDK = new NForumSDK(Host.HOST_BYR, "", "guest", "");
        VoteService voteService = nSDK.getVoteService();
        try {
//            Vote vote = voteService.getVoteList(VoteService.CATEGORY_ALL, new ParamOption());
//            Vote vote = voteService.getVote(5758);
            Vote vote = voteService.vote(5758,34161);
            LogUtils.d("vote title", vote.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NForumException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
