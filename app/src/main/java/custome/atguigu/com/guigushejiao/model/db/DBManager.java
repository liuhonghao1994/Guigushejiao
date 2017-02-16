package custome.atguigu.com.guigushejiao.model.db;

import android.content.Context;

import custome.atguigu.com.guigushejiao.model.dao.ContactDao;
import custome.atguigu.com.guigushejiao.model.dao.InvitationDao;


/**
 * Created by 刘红豪 on 2017/2/15.
 */
//DBManager要在Model中处理
public class DBManager {
    private final DBHelper dbHelper;
    private final InvitationDao invitationDao;
    private final ContactDao contactDao;

    public DBManager(Context context, String name){
         dbHelper = new DBHelper(context, name);
        //创建邀请信息操作类
         invitationDao = new InvitationDao(dbHelper);
        //创建联系人操作类
         contactDao = new ContactDao(dbHelper);

    }
    public InvitationDao getInvitationDao(){
        return invitationDao;
    }
    public ContactDao getContactDao(){
        return contactDao;
    }
    public void close(){
        dbHelper.close();
    }
}

