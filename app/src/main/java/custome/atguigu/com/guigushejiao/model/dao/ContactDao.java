package custome.atguigu.com.guigushejiao.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import custome.atguigu.com.guigushejiao.model.bean.UserInfo;
import custome.atguigu.com.guigushejiao.model.db.DBHelper;
import custome.atguigu.com.guigushejiao.model.table.ContactTable;

/**
 * Created by 刘红豪 on 2017/2/14.
 */

public class ContactDao {
    //因为联系人表和我们的邀请表在一个DB里面，所以传dbHelper
    private DBHelper dbHelper;
    public ContactDao(DBHelper db){
        this.dbHelper=db;
    }
    // 获取所有联系人
    public List<UserInfo> getContacts() {
        List<UserInfo> contacts = new ArrayList<>();

        // 获取数据库链接
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // 查询联系人如果是在线状态的话
        String sql = "select * from " + ContactTable.TABLE_NAME + " where " + ContactTable.COL_IS_CONTACT + "=1";
        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            UserInfo contact = new UserInfo();

            contact.setHxid(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_HXID)));
            contact.setUsername(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_NAME)));
            contact.setNick(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_NICK)));
            contact.setPhoto(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_PHOTO)));

            contacts.add(contact);
        }

        // 关闭cursor
        cursor.close();

        // 返回
        return contacts;
    }

    // 通过环信id获取用户联系人信息查询一群人
    public List<UserInfo> getContactsByHx(List<String> hxIds) {
        // 校验
        if (hxIds == null || hxIds.size() == 0) {
            return null;
        }

        List<UserInfo> contacts = new ArrayList<>();
        for (String hxid:hxIds) {
            UserInfo userinfo = getContactByHx(hxid);
            if(userinfo!=null){
                contacts.add(userinfo);
            }
        }
        return contacts;
    }

    // 通过环信id获取联系人单个信息，当有外来信息的话
    public UserInfo getContactByHx(String hxId) {
        // 校验
        if (hxId == null) {
            return null;
        }

        // 获取数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // 查询
        String sql = "select * from " + ContactTable.TABLE_NAME + " where " + ContactTable.COL_USER_HXID + " =?";
        Cursor cursor = db.rawQuery(sql, new String[]{hxId});

        UserInfo contact = null;

        if (cursor.moveToNext()) {
            contact = new UserInfo();

            contact.setHxid(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_HXID)));
            contact.setUsername(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_NAME)));
            contact.setNick(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_NICK)));
            contact.setPhoto(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_PHOTO)));

        }

        cursor.close();

        return contact;
    }

    // 保存单个联系人
    public void saveContact(UserInfo user, boolean isMyContact) {
        // 获取数据库链接
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 执行添加语句
        ContentValues values = new ContentValues();
        values.put(ContactTable.COL_USER_HXID, user.getHxid());
        values.put(ContactTable.COL_USER_NAME, user.getUsername());
        values.put(ContactTable.COL_USER_NICK, user.getNick());
        values.put(ContactTable.COL_USER_PHOTO, user.getPhoto());
        values.put(ContactTable.COL_IS_CONTACT, isMyContact ? 1 : 0);

        db.replace(ContactTable.TABLE_NAME, null, values);
    }


    // 保存联系人信息一群联系人
    public void saveContacts(List<UserInfo> contacts, boolean isMyContact) {
        // 校验
        if (contacts == null || contacts.size() == 0) {
            return;
        }

        for (UserInfo contact : contacts) {
            saveContact(contact, isMyContact);
        }
    }

    // 删除联系人信息
    public void deleteContactByHxId(String hxId) {
        // 校验
        if (hxId == null) {
            return;
        }

        // 获取数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // 删除操作
        db.delete(ContactTable.TABLE_NAME, ContactTable.COL_USER_HXID + "=?", new String[]{hxId});
    }
}
