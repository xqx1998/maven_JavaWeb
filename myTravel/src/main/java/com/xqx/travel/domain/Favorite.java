package com.xqx.travel.domain;


import java.util.Date;

/**
 * @author xqx
 */
public class Favorite {

  private Integer rid;
  private Date date;
  private Integer uid;


  public Integer getRid() {
    return rid;
  }

  public void setRid(Integer rid) {
    this.rid = rid;
  }


  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }


  public Integer getUid() {
    return uid;
  }

  public void setUid(Integer uid) {
    this.uid = uid;
  }

}
