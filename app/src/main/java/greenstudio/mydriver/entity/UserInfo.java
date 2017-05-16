package greenstudio.mydriver.entity;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by wangyu on 07/02/2017.
 */

public class UserInfo extends BmobObject implements Serializable {
    private String mobile;//手机号
    private String password;//密码
    private String name;//昵称
    private int gender;//性别 1男(默认) 2女
    private String birth;//出生日期
    private String email;//电子邮箱
    private String Q1;//问题1
    private String A1;//答案1
    private String Q2;//问题2
    private String A2;//答案2
    private String Q3;//问题3
    private String A3;//答案3

    public UserInfo() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQ1() {
        return Q1;
    }

    public void setQ1(String q1) {
        Q1 = q1;
    }

    public String getA1() {
        return A1;
    }

    public void setA1(String a1) {
        A1 = a1;
    }

    public String getQ2() {
        return Q2;
    }

    public void setQ2(String q2) {
        Q2 = q2;
    }

    public String getA2() {
        return A2;
    }

    public void setA2(String a2) {
        A2 = a2;
    }

    public String getQ3() {
        return Q3;
    }

    public void setQ3(String q3) {
        Q3 = q3;
    }

    public String getA3() {
        return A3;
    }

    public void setA3(String a3) {
        A3 = a3;
    }

}
