package com.xqx.travel.dao;

import com.xqx.travel.domain.Seller;

/**
 * @author： xingquanxiang
 * createTime：2019/9/24 15:45
 * description:
 */
public interface SellerDao {
    /**
     * 根据sid查询卖家信息
     * @param sid
     * @return
     */
    Seller findOne(Integer sid);
}
