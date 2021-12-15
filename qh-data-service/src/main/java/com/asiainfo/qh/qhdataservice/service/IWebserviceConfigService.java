package com.asiainfo.qh.qhdataservice.service;

import com.asiainfo.qh.qhdataservice.bean.WebserviceConfigBean;

import java.util.List;
import java.util.Map;

public interface IWebserviceConfigService {

   public List<WebserviceConfigBean> queryWebserviceConfig(Map<String, String> param);
}
