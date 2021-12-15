package com.asiainfo.qh.qhdataservice.mapper;

import com.asiainfo.qh.qhdataservice.bean.WebserviceConfigBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

//@Mapper
public interface WebserviceConfigMapper {

List<WebserviceConfigBean> queryWebserviceConfig(Map<String, String> param);
}
