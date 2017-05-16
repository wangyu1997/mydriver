package greenstudio.mydriver.entity;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by wangyu on 20/02/2017.
 */

public class jiaxiaoInfoEntity extends BmobObject implements Serializable {
    private String name;
    private String type;
    private String imgUrl;
    private String address;
    private String price;
    private String range;
    private String jlName;
    private String contact;
    private String city;

    public jiaxiaoInfoEntity() {
    }

    public jiaxiaoInfoEntity(String name, String type, String imgUrl, String address, String price, String range, String jlName, String contact) {
        this.name = name;
        this.type = type;
        this.imgUrl = imgUrl;
        this.address = address;
        this.price = price;
        this.range = range;
        this.jlName = jlName;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getJlName() {
        return jlName;
    }

    public void setJlName(String jlName) {
        this.jlName = jlName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
