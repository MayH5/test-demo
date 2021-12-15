package com.asiainfo.qh.qhdataservice.service.impl;

import com.asiainfo.qh.qhdataservice.bean.WebserviceConfigBean;
import com.asiainfo.qh.qhdataservice.mapper.WebserviceConfigMapper;
import com.asiainfo.qh.qhdataservice.service.IWebserviceConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

//@Service
public class WebserviceConfigServiceImpl implements IWebserviceConfigService {

    @Autowired
    WebserviceConfigMapper webserviceConfigMapper;

    public List<WebserviceConfigBean> queryWebserviceConfig(Map<String, String> param){

        String webservice_code = param.get("webservice_code");
        List<WebserviceConfigBean> resultList = webserviceConfigMapper.queryWebserviceConfig(param);
        return resultList;
    }
}
