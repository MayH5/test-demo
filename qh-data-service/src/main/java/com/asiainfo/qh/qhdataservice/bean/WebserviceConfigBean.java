package com.asiainfo.qh.qhdataservice.bean;

/**
 * 接口配置bean，含接口编码、接口查询地址
 */
public class WebserviceConfigBean {
    private String webservice_code;

    public String getWebservice_code() {
        return webservice_code;
    }

    public void setWebservice_code(String webservice_code) {
        this.webservice_code = webservice_code;
    }

    public String getWebservice_query_sql() {
        return webservice_query_sql;
    }

    public void setWebservice_query_sql(String webservice_query_sql) {
        this.webservice_query_sql = webservice_query_sql;
    }

    private String webservice_query_sql;
}
