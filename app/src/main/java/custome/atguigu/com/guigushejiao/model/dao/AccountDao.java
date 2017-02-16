package custome.atguigu.com.guigushejiao.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import custome.atguigu.com.guigushejiao.model.bean.UserInfo;
import custome.atguigu.com.guigushejiao.model.db.AccountDb;
import custome.atguigu.com.guigushejiao.model.table.AccountTable;

/**
 * Created by 刘红豪 on 2017/2/14.
 */

public class AccountDao {
    private final AccountDb accountDb;

    public AccountDao(Context context) {
        accountDb=new AccountDb(context);
    }
    //添加数据倒数据库
    public void addAccount(UserInfo user){
        //验证
        if(user==null){
            return;
        }
        SQLiteDatabase readableDatabase = accountDb.getReadableDatabase();//获取数据库连接
        ContentValues contentValues=new ContentValues();
        contentValues.put(AccountTable.COL_USER_HXID,user.getHxid());
        contentValues.put(AccountTable.COL_USER_NAME,user.getUsername());
        contentValues.put(AccountTable.COL_USER_NICK,user.getNick());
        contentValues.put(AccountTable.COL_USER_PHOTO,user.getPhoto());
        //利用替换
        readableDatabase.replace(AccountTable.TABLE_NAME,null,contentValues);
    }
    //根据环信id获取所有用户信息
    public UserInfo getAccountByHxId(String hxId){
        if(hxId==null || TextUtils.isEmpty(hxId)){ return null;}
        //首先连接数据库
        SQLiteDatabase readableDatabase = accountDb.getReadableDatabase();
        //第二个参数是条件选择
        String sql="select * from "+AccountTable.TABLE_NAME
                +" where "+AccountTable.COL_USER_HXID+"=?";
        Cursor cursor = readableDatabase.rawQuery(sql, new String[]{hxId});
        UserInfo userInfo=null;
        if(cursor.moveToNext()){
            userInfo=new UserInfo();
            userInfo.setHxid(cursor.getString(cursor.getColumnIndex(AccountTable.COL_USER_HXID)));
            userInfo.setNick(cursor.getString(cursor.getColumnIndex(AccountTable.COL_USER_NICK)));
            userInfo.setPhoto(cursor.getString(cursor.getColumnIndex(AccountTable.COL_USER_PHOTO)));
            userInfo.setUsername(cursor.getString(cursor.getColumnIndex(AccountTable.COL_USER_NAME)));
        }
        cursor.close();
        return userInfo;

    }
}
