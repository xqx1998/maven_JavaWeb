package com.xqx.travel.domain;


/**
 * @author xqx
 */
public class Category {

    private Integer cid;
    private String cname;

    public Category(String cname) {

    }

    public Category(Integer cid, String cname) {
        this.cid = cid;
        this.cname = cname;
    }

    public Category() {

    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }


    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

}
