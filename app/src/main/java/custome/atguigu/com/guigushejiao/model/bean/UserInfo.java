package custome.atguigu.com.guigushejiao.model.bean;

/**
 * Created by 刘红豪 on 2017/2/14.
 */

public class UserInfo {
    private String username;
    private  String hxid;
    private  String photo;
    private String nick;

    public  UserInfo(){

    }
    public UserInfo(String username) {
        this.username = username;
        this.hxid=username;
    }

    public String getUsername() {
        return username;
    }

    public String getHxid() {
        return hxid;
    }

    public String getPhoto() {
        return photo;
    }

    public String getNick() {
        return nick;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setHxid(String hxid) {
        this.hxid = hxid;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}

