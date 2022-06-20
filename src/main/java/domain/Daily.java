package domain;

import java.io.Serializable;

public class Daily implements Serializable {//日志
    private Integer id;//日志id
    private String username;//用户名
    private String oldpassword;//旧密码
    private String newpassword;//新密码
    private String date;//修改日期

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOldpassword() {
        return oldpassword;
    }

    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Daily{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", oldpassword='" + oldpassword + '\'' +
                ", newpassword='" + newpassword + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
