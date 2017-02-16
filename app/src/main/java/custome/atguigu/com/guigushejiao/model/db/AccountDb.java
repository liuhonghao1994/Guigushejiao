package custome.atguigu.com.guigushejiao.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import custome.atguigu.com.guigushejiao.model.table.AccountTable;

/**
 * Created by 刘红豪 on 2017/2/14.
 */

public class AccountDb extends SQLiteOpenHelper{

    public AccountDb(Context context) {
        super(context, "account.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建数据库
        sqLiteDatabase.execSQL(AccountTable.CREATE_TABLE);

    }
    //更新数据库
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
