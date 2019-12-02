package com.example.my_movies.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "dianzan", schema = "my", catalog = "")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class DianzanEntity {
    private int userid;
    private String movieid;

    @Id
    @Column(name = "userid")
    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Basic
    @Column(name = "movieid")
    public String getMovieid() {
        return movieid;
    }

    public void setMovieid(String movieid) {
        this.movieid = movieid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DianzanEntity that = (DianzanEntity) o;
        return userid == that.userid &&
                Objects.equals(movieid, that.movieid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, movieid);
    }
}
