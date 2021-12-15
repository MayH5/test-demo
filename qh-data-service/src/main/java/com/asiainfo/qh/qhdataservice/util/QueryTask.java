package com.asiainfo.qh.qhdataservice.util;

import com.alibaba.fastjson.JSONArray;
import com.asiainfo.qh.qhdataservice.controller.DataController;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class QueryTask {

    @Resource(name = "jdbcTemplate_oracle")
    JdbcTemplate jdbcTemplate_oracle;

//    @Resource(name = "jdbcTemplate_db2")
//    JdbcTemplate jdbcTemplate_db2;

   // Logger logger = LoggerFactory.getLogger(QueryTask.class);

    org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(QueryTask.class);







    /**
     * 接口查询sql
     * @param webservice_code
     * @return
     */
    public String getQuerySql(String webservice_code) {
        String configSql = "select webservice_code,webservice_query_sql from ucr_chan_gridsop.webservice_config_sql where webservice_code='" + webservice_code + "'";
        List<Map<String, Object>> result = jdbcTemplate_oracle.queryForList(configSql);
        JSONArray jsonArray = JSONArray.parseArray(JSONArray.toJSON(result).toString());
        logger.debug("getQuerySql-------------------------"+jsonArray.toJSONString());
        return jsonArray.getJSONObject(0).getString("WEBSERVICE_QUERY_SQL");

    }



    //四轮及融合市场的运营分析->指标列表查询服务  new
    public JSONArray indexListQuery(
            String kpiId,
            String kpiName,
            String kpiType,
            String subscribersPerson,
            String cityId,
            String searchType,
            String searchTime,
            String webservice_code){

        String querySql = "";




        if(searchType.equals("0")){//订阅指标
            //webservice_code = webservice_code+"0";
            //querySql = getQuerySql(webservice_code);
            querySql += "select a.kpi_id as kpiId,a.kpi_name as kpiName,a.kpi_type_name as kpiType,b.subscribers_person as subscribersPerson from (select * from ucr_chan_gridsop.kpi_config where effective_time<=to_char(sysdate,'yyyyMMdd') and expire_time>=to_char(sysdate,'yyyyMMdd') and city_id ='"+cityId+"') a left join ucr_chan_gridsop.kpi_sub_info b on a.kpi_id=b.kpi_id where 1=1 and b.kpi_id is not null";

            if(!kpiId.equals("")){
                querySql += " and a.kpi_id='"+kpiId+"'";
            }
            if(!kpiName.equals("")){
                querySql += " and a.kpi_name like '%"+kpiName+"%'";
            }
            if(!kpiType.equals("")){
                querySql += " and a.kpi_type_name ='"+kpiType+"'";
            }
            if(!subscribersPerson.equals("")){
                querySql += " and b.subscribers_person ='"+subscribersPerson+"'";
            }
//            if(!cityId.equals("")){
//                querySql += " and a.city_id ='"+cityId+"'";
//            }
            if(!searchTime.equals("")){
                querySql += " and a.effective_time<='"+searchTime+"' and a.expire_time>='"+searchTime+"'";
            }
        }else if(searchType.equals("1")){//全量有效指标
            // webservice_code = webservice_code+"1";
            //querySql = getQuerySql(webservice_code);
            querySql += "select a.kpi_id as kpiId,a.kpi_name as kpiName,a.kpi_type_name as kpiType,b.subscribers_person as subscribersPerson ,(case when b.subscribers_person='"+subscribersPerson+"' then '已订阅' else '未订阅' end) as is_sub from (select * from ucr_chan_gridsop.kpi_config where effective_time<=to_char(sysdate,'yyyyMMdd') and expire_time>=to_char(sysdate,'yyyyMMdd') and city_id ='"+cityId+"') a left join (select * from ucr_chan_gridsop.kpi_sub_info where subscribers_person='"+subscribersPerson+"') b on a.kpi_id=b.kpi_id where 1=1";

            if(!kpiId.equals("")){
                querySql += " and a.kpi_id='"+kpiId+"'";
            }

            if(!kpiName.equals("")){
                querySql += " and a.kpi_name like '%"+kpiName+"%'";
            }
            if(!kpiType.equals("")){
                querySql += " and a.kpi_type_name ='"+kpiType+"'";
            }

//            if(!cityId.equals("")){
//                querySql += " and a.city_id ='"+cityId+"'";
//            }
            if(!searchTime.equals("")){
                querySql += " and a.effective_time <='"+searchTime+"' and a.expire_time>='"+searchTime+"'";
            }

        }



        logger.info("指标列表查询服务 当前执行sql:::::"+querySql);

        List<Map<String, Object>> resultList = jdbcTemplate_oracle.queryForList(querySql);
        JSONArray jsonArray = JSONArray.parseArray(JSONArray.toJSON(resultList).toString());


        return jsonArray;

    }


    //四轮及融合市场的运营分析->指标数据查询服务
    public List<Map<String, Object>> indexDataQuery(String kpiId,String cycleStart,String cycleEnd,String cityId,String area_level,String user_id,String webservice_code) {

        String querySql = getQuerySql(webservice_code);

        if(!kpiId.equals("")){
            querySql += " and a.zb_code='"+kpiId+"'";
        }else{
            querySql += " and a.zb_code in (select kpi_id from ucr_chan_gridsop.kpi_sub_info where subscribers_person='"+user_id+"')";
        }
        if(!cycleStart.equals("")){
            querySql += " and a.op_time>='"+cycleStart+"'";
        }
        if(!cycleEnd.equals("")){
            querySql += " and a.op_time<='"+cycleEnd+"'";
        }
        if(!cityId.equals("")){
            querySql += " and a.region_id='"+cityId+"'";
        }
        if(!area_level.equals("")){
            querySql += " and b.area_level='"+area_level+"'";
        }


        logger.info("指标数据查询服务 当前执行sql:::::"+querySql);

        List<Map<String, Object>> resultList = jdbcTemplate_oracle.queryForList(querySql);

        //JSONArray jsonArray = JSONArray.parseArray(JSONArray.toJSON(resultList).toString());



        return resultList;
    }


    //四轮及融合市场的运营分析->指标元数据查询
    public JSONArray indexMetaQuery(String kpiId,String webservice_code) {
        String querySql = getQuerySql(webservice_code);

        if(!kpiId.equals("")){
            querySql += " and kpi_id='"+kpiId+"'";
        }

        logger.info("指标元数据查询 当前执行sql:::::"+querySql);

        List<Map<String, Object>> resultList = jdbcTemplate_oracle.queryForList(querySql);

        JSONArray jsonArray = JSONArray.parseArray(JSONArray.toJSON(resultList).toString());

        logger.debug("indexMetaQuery  222  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+jsonArray.toJSONString());

        return jsonArray;
    }


    //四轮及融合市场的运营分析->指标异常预警查询
    public JSONArray indexAabQuery(
            String kpiId,
            String timeRang,
            String cityId,
            String activityType,
            String subscribersId,
            String webservice_code) {

        String querySql = getQuerySql(webservice_code);

        String[] timeAttr = timeRang.split(",");
        String startDate = timeAttr[0];
        String endDate = timeAttr[1];


        if(!kpiId.equals("")){
            querySql += " and kpiId='"+kpiId+"'";
        }
        if(!cityId.equals("")){
            querySql += " and cityId='"+cityId+"'";
        }
        if(!activityType.equals("")){
            querySql += " and kpi_type_name='"+activityType+"'";
        }
        if(!subscribersId.equals("")){
            querySql += " and subscribersId='"+subscribersId+"'";
        }
        if(!startDate.equals("")){
            querySql += " and op_time>='"+startDate+"'";
        }
        if(!endDate.equals("")){
            querySql += " and op_time<='"+endDate+"'";
        }

        logger.info("指标元数据查询 当前执行sql:::::"+querySql);

        List<Map<String, Object>> resultList = jdbcTemplate_oracle.queryForList(querySql);

        JSONArray jsonArray = JSONArray.parseArray(JSONArray.toJSON(resultList).toString());

        return jsonArray;
    }



    //四轮及融合市场的运营分析->指标关联关系查询
    public JSONArray indexAssQuery(String kpiId,String webservice_code) {

        String querySql = getQuerySql(webservice_code);

        if(!kpiId.equals("")){
            querySql += " and kpiId='"+kpiId+"'";
        }

        logger.info("指标关联关系查询 当前执行sql:::::"+querySql);

        List<Map<String, Object>> resultList = jdbcTemplate_oracle.queryForList(querySql);

        JSONArray jsonArray = JSONArray.parseArray(JSONArray.toJSON(resultList).toString());

        return jsonArray;
    }


    //四轮及融合市场的运营分析->指标订阅
    public JSONArray indexSubscription(String kpiId,String operationType,String subscribersPerson,String webservice_code) {

        String querySql = getQuerySql(webservice_code);

        if(operationType.equals("1")){//新增
            jdbcTemplate_oracle.update("delete from  ucr_chan_gridsop.kpi_sub_info where kpi_id='"+kpiId+"' and subscribers_person='"+subscribersPerson+"'");//订阅前先清空重复数据
            jdbcTemplate_oracle.update("insert into ucr_chan_gridsop.kpi_sub_info values (?,?,?)", String.valueOf(System.currentTimeMillis()),kpiId,subscribersPerson);
        }else if(operationType.equals("2")){//删除
            jdbcTemplate_oracle.update("delete from ucr_chan_gridsop.kpi_sub_info where kpi_id='"+kpiId+"' and subscribers_person='"+subscribersPerson+"'");

        }else if(operationType.equals("3")){//查询

        }

        if(!kpiId.equals("")){
            querySql += " and a.kpi_id='"+kpiId+"'";
        }
        if(!subscribersPerson.equals("")){
            querySql += " and a.subscribers_person='"+subscribersPerson+"'";
        }

        logger.info("指标订阅 当前执行sql:::::"+querySql);

        List<Map<String, Object>> resultList = jdbcTemplate_oracle.queryForList(querySql);

        JSONArray jsonArray = JSONArray.parseArray(JSONArray.toJSON(resultList).toString());

        return jsonArray;
    }


    //四轮及融合市场的运营分析->移动客户综合业务视图服务
    public JSONArray ViewForMobileCust(String userId, String templateId,String webservice_code) {

        String querySql = getQuerySql(webservice_code);

        querySql += " and acc_nbr='"+userId+"' and template_id='"+templateId+"'";

        logger.info("移动客户综合业务视图服务 当前执行sql:::::"+querySql);

        List<Map<String, Object>> resultList = jdbcTemplate_oracle.queryForList(querySql);

        JSONArray jsonArray = JSONArray.parseArray(JSONArray.toJSON(resultList).toString());

        return jsonArray;
    }



    //四轮及融合市场的运营分析->家庭客户综合业务视图服务
    public JSONArray ViewSrviceforHomeCust(String userId,String homeId, String templateId,String webservice_code) {

        String querySql = getQuerySql(webservice_code);

        if(!userId.equals("")){
            querySql += "and acc_nbr='"+userId+"'";
        }
        if(!templateId.equals("")){
            querySql += "and template_id='"+templateId+"'";
        }
        if(!homeId.equals("")){
            querySql += "and family_id='"+homeId+"'";
        }

        logger.info("家庭客户综合业务视图服务 当前执行sql:::::"+querySql);

        List<Map<String, Object>> resultList = jdbcTemplate_oracle.queryForList(querySql);

        JSONArray jsonArray = JSONArray.parseArray(JSONArray.toJSON(resultList).toString());

        return jsonArray;
    }




    //四轮及融合市场的运营分析->5G分析服务
    /**
     * 地域参数	cityId	String
     请求方	request	String
     范围	range	String
     * @return
     */
    public JSONArray Analysisfor5G(String cityId,String range,String request, String webservice_code) {

        String querySql = getQuerySql(webservice_code);

        querySql += " and cityId='"+cityId+"' and request='"+request+"' and range='"+range+"'";


        List<Map<String, Object>> resultList = jdbcTemplate_oracle.queryForList(querySql);

        JSONArray jsonArray = JSONArray.parseArray(JSONArray.toJSON(resultList).toString());

        return jsonArray;
    }


    //四轮及融合市场的运营分析->5G分析服务->5G号码分析
    public JSONArray Analysisfor5GwithPer(String userId,String webservice_code){

        String querySql = getQuerySql(webservice_code);

        querySql += " and acc_nbr='"+userId+"'";

        logger.info("5G号码分析 当前执行sql:::::"+querySql);

        List<Map<String, Object>> resultList = jdbcTemplate_oracle.queryForList(querySql);
        JSONArray jsonArray = JSONArray.parseArray(JSONArray.toJSON(resultList).toString());

        return jsonArray;
    }


    //四轮及融合市场的运营分析->5G分析服务->5G分析视图
    public JSONArray Analysisfor5GView(String kpi_type,String area_id,String area_level,String webservice_code){

        String querySql = getQuerySql(webservice_code);

        if(!kpi_type.equals("")){
            querySql += "and kpi_type='"+kpi_type+"'";
        }
        if(!area_id.equals("")){
            querySql += "and region_id='"+area_id+"'";
        }
        if(!area_level.equals("")){
            querySql += "and region_level='"+area_level+"'";
        }


        logger.info("5G分析视图 当前执行sql:::::"+querySql);

        List<Map<String, Object>> resultList = jdbcTemplate_oracle.queryForList(querySql);
        JSONArray jsonArray = JSONArray.parseArray(JSONArray.toJSON(resultList).toString());

        return jsonArray;
    }



    //政企潜在客户挖掘->潜在客户识别服务
    public JSONArray customerIdentify(String gridId,String channelType,String webservice_code){
        String querySql = getQuerySql(webservice_code);

        if(!gridId.equals("")){
            querySql += " and gridId='"+gridId+"'";
        }
        if(!channelType.equals("")){
            querySql += " and channelType='"+channelType+"'";
        }


        logger.info("政企潜在客户挖掘->潜在客户识别服务 当前执行sql:::::"+querySql);

        List<Map<String, Object>> resultList = jdbcTemplate_oracle.queryForList(querySql);
        JSONArray jsonArray = JSONArray.parseArray(JSONArray.toJSON(resultList).toString());

        return jsonArray;
    }

}
