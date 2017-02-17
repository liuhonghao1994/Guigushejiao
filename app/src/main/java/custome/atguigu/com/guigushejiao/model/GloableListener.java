package custome.atguigu.com.guigushejiao.model;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;

import custome.atguigu.com.guigushejiao.model.bean.InvitationInfo;
import custome.atguigu.com.guigushejiao.model.bean.UserInfo;
import custome.atguigu.com.guigushejiao.utils.Contacts;
import custome.atguigu.com.guigushejiao.utils.SpUtils;

/**
 * Created by 刘红豪 on 2017/2/15.
 */

public class GloableListener {
    private final LocalBroadcastManager lm;
    public GloableListener(Context context){
        lm = LocalBroadcastManager.getInstance(context);
        //注册联系人监听
        EMClient.getInstance().contactManager().setContactListener(new EMContactListener() {


            @Override
            public void onContactAdded(String s) {
                //添加联系人
                Model.getInstance().getDbManager()
                        .getContactDao()
                        .saveContact(new UserInfo(s),true);
                //发送广播
                lm.sendBroadcast(new Intent(Contacts.CONTACT_CHANGED));

            }

            @Override
            public void onContactDeleted(String s) {
                Model.getInstance().getDbManager().getContactDao()
                        .deleteContactByHxId(s);
                //删除邀请信息
                Model.getInstance().getDbManager().getInvitationDao()
                        .removeInvitation(s);
                //发送广播
                lm.sendBroadcast(new Intent(Contacts.CONTACT_CHANGED));

            }
            //别人加你，接受好友邀请
            @Override
            public void onContactInvited(String s, String s1) {
                //加到邀请信息表
                InvitationInfo invitationInfo=new InvitationInfo();
                invitationInfo.setUser(new UserInfo(s));
                invitationInfo.setReason(s1);
                invitationInfo.setStatus(InvitationInfo.InvitationStatus.NEW_INVITE);
                Model.getInstance().getDbManager().getInvitationDao().addInvitation(invitationInfo);
                //保存你小红点的状态
                SpUtils.getInstace().save(SpUtils.NEW_INVITE,true);
                //发送广播
                lm.sendBroadcast(new Intent(Contacts.NEW_INVITE_CHAGED));
            }
            //你加别人别人同意
            @Override
            public void onContactAgreed(String s) {
                InvitationInfo invitationInfo=new InvitationInfo();
                invitationInfo.setUser(new UserInfo(s));
                invitationInfo.setReason("邀请被接受");
                invitationInfo.setStatus(InvitationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER);
                //添加到邀请信息表
                Model.getInstance().getDbManager().getInvitationDao().addInvitation(invitationInfo);
                //显示小红点
                SpUtils.getInstace().save(SpUtils.NEW_INVITE,true);
                //发送广播
                lm.sendBroadcast(new Intent(Contacts.NEW_INVITE_CHAGED));
            }
            //你加别人别人拒绝
            @Override
            public void onContactRefused(String s) {
                //显示小红点
                SpUtils.getInstace().save(SpUtils.NEW_INVITE,true);
                //发送广播
                lm.sendBroadcast(new Intent(Contacts.NEW_INVITE_CHAGED));

            }
        });
    }
}
