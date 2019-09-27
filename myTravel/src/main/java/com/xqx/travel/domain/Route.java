package com.xqx.travel.domain;


import java.util.List;

/**
 * @author xqx
 */
public class Route {
    /**
     * 线路id 必填
     */
    private Integer rid;
    /**
     * 线路名称，必填
     */
    private String rname;
    /**
     * 价格，必填
     */
    private Double price;
    /**
     * 线路介绍
     */
    private String routeIntroduce;
    /**
     * 是否上架，必填
     * 0代表没有上架 1代表上架
     */
    private String rflag;
    /**
     * 上架时间
     */
    private String rdate;
    /**
     * 是否主题旅游， 必填
     * 0代表不是， -1代表是
     */
    private String isThemeTour;
    /**
     * 收藏数量
     */
    private Integer count;
    /**
     * 所属分类
     */
    private Integer cid;
    /**
     *缩略图
     */
    private String rimage;
    /**
     *所属商家
     */
    private Integer sid;
    /**
     *抓取数据的来源id
     */
    private String sourceId;
    /**
     * 所属分类
     */
    private Category category;
    /**
     *所属商家
     */
    private Seller seller;
    /**
     *商品详情图片列表
     */
    private List<RouteImg> routeImgList;

    public Route() {
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }


    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


    public String getRouteIntroduce() {
        return routeIntroduce;
    }

    public void setRouteIntroduce(String routeIntroduce) {
        this.routeIntroduce = routeIntroduce;
    }


    public String getRflag() {
        return rflag;
    }

    public void setRflag(String rflag) {
        this.rflag = rflag;
    }


    public String getRdate() {
        return rdate;
    }

    public void setRdate(String rdate) {
        this.rdate = rdate;
    }


    public String getIsThemeTour() {
        return isThemeTour;
    }

    public void setIsThemeTour(String isThemeTour) {
        this.isThemeTour = isThemeTour;
    }


    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }


    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }


    public String getRimage() {
        return rimage;
    }

    public void setRimage(String rimage) {
        this.rimage = rimage;
    }


    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }


    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public List<RouteImg> getRouteImgList() {
        return routeImgList;
    }

    public void setRouteImgList(List<RouteImg> routeImgList) {
        this.routeImgList = routeImgList;
    }
}
