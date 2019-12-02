package com.example.shop.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "my", catalog = "")
public class UsersEntity {
    private int id;
    private String username;
    private String password;
    private String loginname;
    private Integer qqb;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "loginname")
    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    @Basic
    @Column(name = "qqb")
    public Integer getQqb() {
        return qqb;
    }

    public void setQqb(Integer qqb) {
        this.qqb = qqb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersEntity that = (UsersEntity) o;
        return id == that.id &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(loginname, that.loginname) &&
                Objects.equals(qqb, that.qqb);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, loginname, qqb);
    }
}
