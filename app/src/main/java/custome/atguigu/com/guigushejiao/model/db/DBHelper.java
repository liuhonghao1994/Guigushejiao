package custome.atguigu.com.guigushejiao.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import custome.atguigu.com.guigushejiao.model.table.ContactTable;
import custome.atguigu.com.guigushejiao.model.table.InvitationTable;

/**
 * Created by 刘红豪 on 2017/2/14.
 */

public class DBHelper extends SQLiteOpenHelper {
    //为什么留名字,每一个用户都有sqLiteDatabase
    public DBHelper(Context context, String name) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建两张表，一个是邀请人信息表，另外一个表是联系人列表
        sqLiteDatabase.execSQL(ContactTable.CREATE_TABLE);//联系人列表
        sqLiteDatabase.execSQL(InvitationTable.CREATE_TABLE);//创建邀请信息的表

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
