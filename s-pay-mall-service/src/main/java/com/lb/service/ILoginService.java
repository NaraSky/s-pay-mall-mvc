package com.lb.service;

import java.io.IOException;

public interface ILoginService {

    String createQrCodeTicket() throws Exception;

    String checkLogin(String ticket);

    void saveLoginState(String ticket, String openid) throws IOException;

}