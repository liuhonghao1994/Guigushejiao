package custome.atguigu.com.guigushejiao.model;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import custome.atguigu.com.guigushejiao.model.dao.AccountDao;
import custome.atguigu.com.guigushejiao.model.db.DBManager;

/**
 * Created by 刘红豪 on 2017/2/13.
 */

public class Model {
    private DBManager manager;

    //单例，饿汉式
    //1.创建私有构造器2.创建实例3.对完提供静态公共方法，返回实例。
    private Model(){};
    private Context context;
    private AccountDao accountDao;
    //直接创建，是饿汉试
    private  static Model model=new Model();
    //获取他的一个是实例，返回自身对象。
    public  static Model getInstance(){
        return model;
    }
    //初始化Model的方法
    public void init(Context context){
        this.context=context;
        accountDao=new AccountDao(context);
        //初始化全局监听
        new GloableListener(context);
    }
   /*线程池分为四种，第一种 有多少开启多少
    2.固定大小
    3.延期周期只想
    4.单个*/
    private ExecutorService service= Executors.newCachedThreadPool();
    //得到ExecutorService的对象
    public  ExecutorService getGloableThread(){
        return  service;
    }
    //登陆成功后的方法
    public void loginSuccess(String currentUser) {
            //启用数据库的管理方法
        if(manager==null){
            return;
        }

        if(manager!=null) {
            manager.close();
        }
        manager=new DBManager(context,currentUser+".db");

    }

    public AccountDao getAccountDao() {
        return  accountDao;
    }

    public DBManager getDbManager() {
        return manager;
    }
}
