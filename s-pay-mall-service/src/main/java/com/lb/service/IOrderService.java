package com.lb.service;

import com.lb.domain.req.ShopCartReq;
import com.lb.domain.res.PayOrderRes;

public interface IOrderService {

    PayOrderRes createOrder(ShopCartReq shopCartReq) throws Exception;

}
