package com.example.shop.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "orders", schema = "my", catalog = "")
public class OrdersEntity {
    private int id;
    private String addtime;
    private int price;
    private String name;
    private int userid;
    private String pic;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "addtime")
    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    @Basic
    @Column(name = "price")
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "userid")
    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Basic
    @Column(name = "pic")
    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdersEntity that = (OrdersEntity) o;
        return id == that.id &&
                price == that.price &&
                userid == that.userid &&
                Objects.equals(addtime, that.addtime) &&
                Objects.equals(name, that.name) &&
                Objects.equals(pic, that.pic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, addtime, price, name, userid, pic);
    }
}
