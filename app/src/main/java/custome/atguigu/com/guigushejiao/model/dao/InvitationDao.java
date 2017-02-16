package custome.atguigu.com.guigushejiao.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import custome.atguigu.com.guigushejiao.model.bean.GroupInfo;
import custome.atguigu.com.guigushejiao.model.bean.InvitationInfo;
import custome.atguigu.com.guigushejiao.model.bean.UserInfo;
import custome.atguigu.com.guigushejiao.model.db.DBHelper;
import custome.atguigu.com.guigushejiao.model.table.InvitationTable;

/**
 * Created by 刘红豪 on 2017/2/14.
 */

public class InvitationDao {
    //两张表是不一样的db，所以这里穿db，不传和AccountDao一样的context
    private DBHelper dbHelper;
    public InvitationDao(DBHelper db){
        this.dbHelper=db;
    }
    // 添加
    public void addInvitation(InvitationInfo invitationInfo){
            //校验
        if(invitationInfo==null){
            return;
        }
        SQLiteDatabase readableDatabase = dbHelper.getReadableDatabase();
        //操作
        ContentValues contentValues=new ContentValues();
        contentValues.put(InvitationTable.COL_REASON,invitationInfo.getReason());
        contentValues.put(InvitationTable.COL_STATUS,invitationInfo.getStatus().ordinal());//枚举变为int值加ordinal
        if(invitationInfo.getUser()==null){
            //群邀请
            contentValues.put(InvitationTable.COL_GROUP_HXID,invitationInfo.getGroup().getGroupId());
            contentValues.put(InvitationTable.COL_GROUP_NAME,invitationInfo.getGroup().getGroupName());
            contentValues.put(InvitationTable.COL_USER_HXID,invitationInfo.getGroup().getInvitePerson());
        }else{
            //当用户是空的时候将邀请人放进去
            //联系人邀请
            contentValues.put(InvitationTable.COL_USER_HXID,invitationInfo.getUser().getHxid());
            contentValues.put(InvitationTable.COL_USER_NAME,invitationInfo.getUser().getUsername());
        }
        readableDatabase.insert(InvitationTable.TABLE_NAME,null, contentValues);

    }

    // 获取所有邀请信息
    public List<InvitationInfo> getInvitations(){
        //获取数据库连接
        SQLiteDatabase readableDatabase = dbHelper.getReadableDatabase();
        //数据库操作
        String sql="select * from "+InvitationTable.TABLE_NAME;
        Cursor cursor = readableDatabase.rawQuery(sql, null);
       List<InvitationInfo> invitationInfos=new ArrayList<>();
        while (cursor.moveToNext()){
            InvitationInfo invitationInfo=new InvitationInfo();
            if(cursor.getString(cursor.getColumnIndex(InvitationTable.COL_GROUP_HXID))==null){
                        //联系人邀请
                UserInfo userInfo=new UserInfo();
                userInfo.setHxid(cursor.getString(cursor.getColumnIndex(InvitationTable.COL_USER_HXID)));
                userInfo.setUsername(cursor.getString(cursor.getColumnIndex(InvitationTable.COL_USER_NAME)));
                invitationInfo.setUser(userInfo);
            }else{
                    //群邀请
                GroupInfo groupInfo=new GroupInfo();
                groupInfo.setInvitePerson(cursor.getString(cursor.getColumnIndex(InvitationTable.COL_USER_HXID)));
                groupInfo.setGroupName(cursor.getString(cursor.getColumnIndex(InvitationTable.COL_GROUP_NAME)));
                groupInfo.setGroupId(cursor.getString(cursor.getColumnIndex(InvitationTable.COL_GROUP_HXID)));
                invitationInfo.setGroup(groupInfo);
             }
                invitationInfo.setReason(cursor.getString(cursor.getColumnIndex(InvitationTable.COL_REASON)));
                invitationInfo.setStatus(int2InviteStatus(cursor.getInt(cursor.getColumnIndex(InvitationTable.COL_STATUS))));
                //把单个invitationInfo装进集合李
                invitationInfos.add(invitationInfo);
        }

             //关闭资源返回数据
            cursor.close();
            return invitationInfos;
    }

    // 将int类型状态转换为邀请的状态
    private InvitationInfo.InvitationStatus int2InviteStatus(int intStatus){
        // 将int类型状态转换为邀请的状态


            if (intStatus == InvitationInfo.InvitationStatus.NEW_INVITE.ordinal()) {
                return InvitationInfo.InvitationStatus.NEW_INVITE;
            }

            if (intStatus == InvitationInfo.InvitationStatus.INVITE_ACCEPT.ordinal()) {
                return InvitationInfo.InvitationStatus.INVITE_ACCEPT;
            }

            if (intStatus == InvitationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER.ordinal()) {
                return InvitationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER;
            }

            if (intStatus == InvitationInfo.InvitationStatus.NEW_GROUP_INVITE.ordinal()) {
                return InvitationInfo.InvitationStatus.NEW_GROUP_INVITE;
            }

            if (intStatus == InvitationInfo.InvitationStatus.NEW_GROUP_APPLICATION.ordinal()) {
                return InvitationInfo.InvitationStatus.NEW_GROUP_APPLICATION;
            }

            if (intStatus == InvitationInfo.InvitationStatus.GROUP_INVITE_ACCEPTED.ordinal()) {
                return InvitationInfo.InvitationStatus.GROUP_INVITE_ACCEPTED;
            }

            if (intStatus == InvitationInfo.InvitationStatus.GROUP_APPLICATION_ACCEPTED.ordinal()) {
                return InvitationInfo.InvitationStatus.GROUP_APPLICATION_ACCEPTED;
            }

            if (intStatus == InvitationInfo.InvitationStatus.GROUP_INVITE_DECLINED.ordinal()) {
                return InvitationInfo.InvitationStatus.GROUP_INVITE_DECLINED;
            }

            if (intStatus == InvitationInfo.InvitationStatus.GROUP_APPLICATION_DECLINED.ordinal()) {
                return InvitationInfo.InvitationStatus.GROUP_APPLICATION_DECLINED;
            }

            if (intStatus == InvitationInfo.InvitationStatus.GROUP_ACCEPT_INVITE.ordinal()) {
                return InvitationInfo.InvitationStatus.GROUP_ACCEPT_INVITE;
            }

            if (intStatus == InvitationInfo.InvitationStatus.GROUP_ACCEPT_APPLICATION.ordinal()) {
                return InvitationInfo.InvitationStatus.GROUP_ACCEPT_APPLICATION;
            }

            if (intStatus == InvitationInfo.InvitationStatus.GROUP_REJECT_APPLICATION.ordinal()) {
                return InvitationInfo.InvitationStatus.GROUP_REJECT_APPLICATION;
            }

            if (intStatus == InvitationInfo.InvitationStatus.GROUP_REJECT_INVITE.ordinal()) {
                return InvitationInfo.InvitationStatus.GROUP_REJECT_INVITE;
            }


            return null;
    }

    // 删除邀请
    public void removeInvitation(String hxId){
        //校验
        if(hxId==null){
            return;
        }
        SQLiteDatabase readableDatabase = dbHelper.getReadableDatabase();
        readableDatabase.delete(InvitationTable.TABLE_NAME,InvitationTable.COL_USER_HXID+"?",new String[]{hxId});

    }

    // 更新邀请状态
    public void updateInvitationStatus(InvitationInfo.InvitationStatus invitationStatus, String hxId){
        if(hxId==null|| TextUtils.isEmpty(hxId)){
            return;
        }
        SQLiteDatabase readableDatabase = dbHelper.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        //修改的字段和值，但是他本来是个枚举，所以我们要给他转换为int类型的值
        contentValues.put(InvitationTable.COL_STATUS, invitationStatus.ordinal());
        readableDatabase.update(InvitationTable.TABLE_NAME,contentValues,InvitationTable.COL_USER_HXID+"?",new String[]{hxId});
    }
}
