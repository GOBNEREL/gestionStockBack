package com.blackfield.StockManagement.service;

import com.blackfield.StockManagement.bean.AuthenticationRequestBean;
import com.blackfield.StockManagement.bean.AuthenticationResponseBean;

public interface AuthentificationService {

    AuthenticationResponseBean authenticateByLoginAndPassword(AuthenticationRequestBean request);
}
