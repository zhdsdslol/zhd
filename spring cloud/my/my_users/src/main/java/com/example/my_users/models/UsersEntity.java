package com.example.my_users.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "my", catalog = "")
@JsonIgnoreProperties({"password","hibernateLazyInitializer"})
public class UsersEntity extends user{
    private int id;
    private String loginname;
    private String password;
    private String username;
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
    @Column(name = "loginname")
    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
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
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersEntity that = (UsersEntity) o;
        return id == that.id &&
                Objects.equals(loginname, that.loginname) &&
                Objects.equals(password, that.password) &&
                Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, loginname, password, username);
    }

    @Basic
    @Column(name = "qqb")
    public Integer getQqb() {
        return qqb;
    }

    public void setQqb(Integer qqb) {
        this.qqb = qqb;
    }
}
