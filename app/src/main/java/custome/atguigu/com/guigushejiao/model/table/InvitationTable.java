package custome.atguigu.com.guigushejiao.model.table;

/**
 * Created by 刘红豪 on 2017/2/14.
 */

public class InvitationTable {
    // 邀请信息的建表类


        public static final String TABLE_NAME = "invitation";

        public static final String COL_USER_NAME = "user_name";
        public static final String COL_USER_HXID = "user_hxid";

        public static final String COL_GROUP_NAME = "group_name";
        public static final String COL_GROUP_HXID = "group_hxid";

        public static final String COL_REASON = "reason";
        public static final String COL_STATUS = "status";

        public static final String CREATE_TABLE = "create table "+TABLE_NAME +"("
                +COL_USER_HXID + " text primary key,"
                +COL_USER_NAME +" text,"
                +COL_GROUP_NAME +" text,"
                +COL_GROUP_HXID +" text,"
                +COL_REASON +" text,"
                +COL_STATUS +" integer);";//状态

}
