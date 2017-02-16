package custome.atguigu.com.guigushejiao.model;

import android.content.Context;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;

/**
 * Created by 刘红豪 on 2017/2/15.
 */

public class GloableListener {
    public GloableListener(Context context){
        EMClient.getInstance().contactManager().setContactListener(new EMContactListener() {
            @Override
            public void onContactAdded(String s) {

            }

            @Override
            public void onContactDeleted(String s) {

            }
            //别人加你，接受好友邀请
            @Override
            public void onContactInvited(String s, String s1) {

            }
            //你加别人别人同意
            @Override
            public void onContactAgreed(String s) {

            }
            //你加别人别人拒绝
            @Override
            public void onContactRefused(String s) {

            }
        });
    }
}
