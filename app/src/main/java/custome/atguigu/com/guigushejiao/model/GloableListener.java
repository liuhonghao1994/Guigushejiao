package custome.atguigu.com.guigushejiao.model;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

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
    private final LocalBroadcastManager manager;
    public GloableListener(Context context){
        manager = LocalBroadcastManager.getInstance(context);

        Log.e("TAG","111初始化全局监听");
        //注册联系人监听
        EMClient.getInstance().contactManager().setContactListener(new EMContactListener() {

            //被添加的时候
            @Override
            public void onContactAdded(String username) {
                //添加联系人
                Model.getInstance().getDbManager().getContactDao()
                                           .saveContact(new UserInfo(username),true);
                //发送广播
                manager.sendBroadcast(new Intent(Contacts.CONTACT_CHANGED));

            }
            //被删除时
            @Override
            public void onContactDeleted(String username) {
                //删除邀请信息
                Model.getInstance().getDbManager().getInvitationDao()
                                           .removeInvitation(username);
                //删除联系人
                Model.getInstance().getDbManager().getContactDao()
                        .deleteContactByHxId(username);
                //发送广播
                manager.sendBroadcast(new Intent(Contacts.CONTACT_CHANGED));

            }
            //别人加你，接受好友邀请
            @Override
            public void onContactInvited(String username, String reason) {
                InvitationInfo invitation = new InvitationInfo();
                invitation.setUser(new UserInfo(username));
                invitation.setReason(reason);
                invitation.setStatus(InvitationInfo.InvitationStatus.NEW_INVITE);

                Model.getInstance().getDbManager().getInvitationDao()
                        .addInvitation(invitation);

                //保存小红点的状态
                SpUtils.getInstace().save(SpUtils.NEW_INVITE,true);
                //发送广播
                manager.sendBroadcast(new Intent(Contacts.NEW_INVITE_CHAGED));
            }
            //你加别人别人同意
            @Override
            public void onContactAgreed(String username) {
                //添加到邀请信息表
                InvitationInfo invitationInfo = new InvitationInfo();
                invitationInfo.setUser(new UserInfo(username));
                invitationInfo.setReason("邀请被接受");
                invitationInfo.setStatus(InvitationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER);
                Model.getInstance().getDbManager().getInvitationDao()
                        .addInvitation(invitationInfo);

                //保存小红点的状态
                SpUtils.getInstace().save(SpUtils.NEW_INVITE,true);
                //发送广播
                manager.sendBroadcast(new Intent(Contacts.NEW_INVITE_CHAGED));
            }
            //你加别人别人拒绝
            @Override
            public void onContactRefused(String username) {
                //保存小红点的状态
                SpUtils.getInstace().save(SpUtils.NEW_INVITE,true);
                //发送广播
                manager.sendBroadcast(new Intent(Contacts.NEW_INVITE_CHAGED));

            }
        });
    }
}
