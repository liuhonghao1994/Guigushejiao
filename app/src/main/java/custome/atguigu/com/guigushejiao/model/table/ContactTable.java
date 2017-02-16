package custome.atguigu.com.guigushejiao.model.table;

/**
 * Created by 刘红豪 on 2017/2/14.
 */

public class ContactTable {
    // 创建联系人数据库语句
        public static final String TABLE_NAME = "contact";//表明

        public static final String COL_USER_HXID = "hxid";//联系人id
        public static final String COL_USER_NAME = "name";//联系人名字
        public static final String COL_USER_PHOTO = "photo";//联系人头像
        public static final String COL_USER_NICK = "nick";//联系人昵称

        public static final String COL_IS_CONTACT = "is_contact";//联系人状态

        public static final String CREATE_TABLE = "create table "+TABLE_NAME+"("
                + COL_USER_HXID + " text primary key,"//id是唯一的设为主键
                + COL_USER_NAME + " text,"
                + COL_USER_PHOTO + " text,"
                + COL_USER_NICK + " text,"
                + COL_IS_CONTACT + " integer);";

}
