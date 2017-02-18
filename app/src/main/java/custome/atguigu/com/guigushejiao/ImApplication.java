package custome.atguigu.com.guigushejiao;

import android.app.Application;
import android.content.Context;

import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;

import custome.atguigu.com.guigushejiao.model.Model;

/**
 * Created by 刘红豪 on 2017/2/14.
 */

public class ImApplication extends Application{
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化环信
        initHXSdk();
        //初始化Modle
        Model.getInstance().init(this);
        context=this;

    }
    private void initHXSdk() {
        EMOptions options=new EMOptions();
        options.setAutoAcceptGroupInvitation(false);// 不自动接受群邀请信息
        options.setAcceptInvitationAlways(false);// 不总是一直接受所有邀请
        //初始化EaseUI
        EaseUI.getInstance().init(this,options);
    }
    public static Context getContext(){
        return context;
    }
    
}
