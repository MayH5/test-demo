package com.asiainfo.qh.qhdataservice.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asiainfo.qh.qhdataservice.service.IWebserviceConfigService;
import com.asiainfo.qh.qhdataservice.util.QueryTask;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 中台接口
 */
@Controller
@RequestMapping("/qh/data")
public class DataController {

   // Logger logger = LoggerFactory.getLogger(DataController.class);
    Logger logger = org.apache.log4j.Logger.getLogger(DataController.class);


    @Autowired
    QueryTask queryTask;

    @Value("${spring.http.encoding.charset}")
    private String charset;





    //四轮及融合市场的运营分析->指标列表查询服务

    /**
     * searchType=0，查询全量指标
     * searchType=1，查询当前用户订阅指标
     * @param paramJSONObject
     * @return
     */
    @RequestMapping(value="/indexListQuery",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject indexListQuery(@RequestBody JSONObject paramJSONObject) {


        String kpiId = paramJSONObject.getString("kpiId");
        String kpiName = paramJSONObject.getString("kpiName");
        String kpiType = paramJSONObject.getString("kpiType");
        String subscribersPerson = paramJSONObject.getString("subscribersPerson");
        String cityId = paramJSONObject.getString("cityId");
        String searchType = paramJSONObject.getString("searchType");
        String searchTime = paramJSONObject.getString("searchTime");


        if(kpiId==null){
            kpiId = "";
        }
        if(kpiName==null){
            kpiName = "";
        }
        if(kpiType==null){
            kpiType = "";
        }
        if(subscribersPerson==null){
            subscribersPerson = "";
        }
        if(cityId==null){
            cityId = "";
        }
        if(searchType==null){
            searchType = "";
        }
        if(searchTime==null){
            searchTime = "";
        }


        JSONObject resultJsonObject = new JSONObject();//结果参数

        logger.debug("四轮及融合市场的运营分析->指标列表查询服务indexListQuery 输入参数:::kpiId="+kpiId+",kpiName="+kpiName+",kpiType="+kpiType+",subscribersPerson="+subscribersPerson+",cityId="+cityId+",searchType="+searchType+",searchTime="+searchTime);


        logger.info("***********************开始 四轮及融合市场的运营分析->指标列表查询服务indexListQuery 入参字段非空检查***********************");
        String bizCode = "0";
        String resultDesc = "";


        if(subscribersPerson.equals("")){
            logger.error("subscribersPerson参数为空~~~~~~~~~~~~~~~~~~~~~~~~~~`");
            bizCode = "-1";
        }
        if(cityId.equals("")){
            logger.error("cityId参数为空~~~~~~~~~~~~~~~~~~~~~~~~~~`");
            bizCode = "-1";
        }


        if(bizCode == "-1"){

            logger.info("***********************四轮及融合市场的运营分析->指标列表查询服务indexListQuery 入参字段非空检查不通过***********************");
            resultDesc = "subscribersPerson参数、cityId参数均不允许为空，请重置！";
            resultJsonObject.put("bizCode", bizCode);
            resultJsonObject.put("resultDesc", resultDesc);

        }else{

            logger.info("***********************四轮及融合市场的运营分析->指标列表查询服务indexListQuery 入参字段非空检查通过***********************");

            try {
                JSONArray resultJSONArray = queryTask.indexListQuery(kpiId, kpiName, kpiType, subscribersPerson, cityId,searchType,searchTime,Thread.currentThread().getStackTrace()[1].getMethodName());
                resultJsonObject.put("bizCode", bizCode);
                resultJsonObject.put("resultDesc", resultDesc);
                logger.info("四轮及融合市场的运营分析->指标列表查询服务indexListQuery 输出参数"+resultJSONArray.toString());
                resultJsonObject.put("resultData", resultJSONArray);
            } catch (Exception e) {
                bizCode = "-2";
                resultDesc = "数据查询失败，请重试！";
                resultJsonObject.put("bizCode", bizCode);
                resultJsonObject.put("resultDesc", resultDesc);
                e.printStackTrace();
            }


        }


        return resultJsonObject;

    }




    //四轮及融合市场的运营分析->指标数据查询服务
    //返回字段应新增数据日期

    /**
     *
     * @param paramJSONObject
     * @return
     */
    @RequestMapping(value="/indexDataQuery",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject indexDataQuery(@RequestBody JSONObject paramJSONObject) {

        String kpiId = paramJSONObject.getString("kpiId");
        String cycleStart = paramJSONObject.getString("cycleStart");
        String cycleEnd = paramJSONObject.getString("cycleEnd");
        String cityId = paramJSONObject.getString("cityId");
        String area_level = paramJSONObject.getString("area_level");
        String user_id = paramJSONObject.getString("user_id");

        if(kpiId==null){
            kpiId = "";
        }
        if(cycleStart==null){
            cycleStart = "";
        }
        if(cycleEnd==null){
            cycleEnd = "";
        }
        if(cityId==null){
            cityId = "";
        }
        if(area_level==null){
            area_level = "";
        }
        if(user_id==null){
            user_id = "";
        }


        JSONObject resultJSONObject = new JSONObject();//结果参数

        logger.debug("四轮及融合市场的运营分析->指标数据查询服务indexDataQuery 输入参数:::kpiId="+kpiId+",cycleStart="+cycleStart+",cycleEnd="+cycleEnd+",cityId="+cityId+",area_level="+area_level);

        logger.info("***********************开始 四轮及融合市场的运营分析->指标数据查询服务indexDataQuery 入参字段非空检查***********************");
        String bizCode = "0";
        String resultDesc = "";


        if(cycleStart.equals("")){
            logger.error("cycleStart参数为空~~~~~~~~~~~~~~~~~~~~~~~~~~`");
            bizCode = "-1";
        }
        if(cycleEnd.equals("")){
            logger.error("cycleEnd参数为空~~~~~~~~~~~~~~~~~~~~~~~~~~`");
            bizCode = "-1";
        }
        if(user_id.equals("")){
            logger.error("user_id参数为空~~~~~~~~~~~~~~~~~~~~~~~~~~`");
            bizCode = "-1";
        }


        if(bizCode == "-1"){
            logger.info("***********************四轮及融合市场的运营分析->指标数据查询服务indexDataQuery 入参字段非空检查不通过***********************");
            resultDesc = "cycleStart参数、cycleEnd参数、user_id参数均不允许为空，请重置！";
            resultJSONObject.put("bizCode", bizCode);
            resultJSONObject.put("resultDesc", resultDesc);
        }else{
            logger.info("***********************四轮及融合市场的运营分析->指标数据查询服务indexDataQuery 入参字段非空检查通过***********************");

            try {
//                JSONArray resultJSONArray = queryTask.indexDataQuery(kpiId, cycleStart, cycleEnd, cityId, area_level, Thread.currentThread().getStackTrace()[1].getMethodName());
                List<Map<String, Object>> resultList = queryTask.indexDataQuery(kpiId, cycleStart, cycleEnd, cityId, area_level, user_id,Thread.currentThread().getStackTrace()[1].getMethodName());
                resultJSONObject.put("bizCode", bizCode);
                resultJSONObject.put("resultDesc", resultDesc);
                logger.debug("四轮及融合市场的运营分析->指标数据查询服务indexDataQuery 输出参数:::"+resultList.toString());
                resultJSONObject.put("resultData", resultList);
            } catch (Exception e) {
                bizCode = "-2";
                resultDesc = "数据查询失败，请重试！";
                resultJSONObject.put("bizCode", bizCode);
                resultJSONObject.put("resultDesc", resultDesc);
                e.printStackTrace();
            }

        }

        return resultJSONObject;

    }


    //四轮及融合市场的运营分析->指标元数据查询
    @RequestMapping(value="/indexMetaQuery",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject indexMetaQuery(@RequestBody JSONObject paramJSONObject) {

        String kpiId = paramJSONObject.getString("kpiId");

        if(kpiId==null){
            kpiId = "";
        }

        JSONObject resultJSONObject = new JSONObject();

        logger.debug("四轮及融合市场的运营分析->指标元数据查询服务indexMetaQuery 输入参数:::kpiId="+kpiId);

        logger.info("***********************开始 四轮及融合市场的运营分析->指标元数据查询服务indexMetaQuery 入参字段非空检查***********************");
        String bizCode = "0";
        String resultDesc = "";


        if(bizCode == "-1"){
            logger.info("***********************四轮及融合市场的运营分析->指标元数据查询服务indexMetaQuery 入参字段非空检查不通过***********************");
            resultDesc = "kpiId参数不允许为空，请重置！";
            resultJSONObject.put("bizCode", bizCode);
            resultJSONObject.put("resultDesc", resultDesc);
        }else{
            logger.info("***********************四轮及融合市场的运营分析->指标元数据查询服务indexMetaQuery 入参字段非空检查通过***********************");

            try {
                JSONArray resultJSONArray = queryTask.indexMetaQuery(kpiId, Thread.currentThread().getStackTrace()[1].getMethodName());
                resultJSONObject.put("bizCode", bizCode);
                resultJSONObject.put("resultDesc", resultDesc);
                logger.debug("四轮及融合市场的运营分析->指标元数据查询服务indexMetaQuery 输出参数:::"+resultJSONArray.toString());
                resultJSONObject.put("resultData", resultJSONArray);
            } catch (Exception e) {
                bizCode = "-2";
                resultDesc = "数据查询失败，请重试！";
                resultJSONObject.put("bizCode", bizCode);
                resultJSONObject.put("resultDesc", resultDesc);
                e.printStackTrace();
            }

        }

        return resultJSONObject;

    }



    //四轮及融合市场的运营分析->指标异常预警查询
    @RequestMapping(value="/indexAabQuery",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject indexAabQuery(@RequestBody JSONObject paramJSONObject) {

        String kpiId = paramJSONObject.getString("kpiId");
        String timeRang = paramJSONObject.getString("timeRang");
        String cityId = paramJSONObject.getString("cityId");
        String activityType = paramJSONObject.getString("activityType");
        String subscribersId = paramJSONObject.getString("subscribersId");

        if(kpiId==null){
            kpiId = "";
        }
        if(timeRang==null){
            timeRang = "";
        }
        if(cityId==null){
            cityId = "";
        }
        if(activityType==null){
            activityType = "";
        }
        if(subscribersId==null){
            subscribersId = "";
        }


        JSONObject resultJSONObject = new JSONObject();

        logger.debug("四轮及融合市场的运营分析->指标异常预警查询indexAabQuery 输入参数:::kpiId="+kpiId+",timeRang="+timeRang+",cityId="+cityId+",activityType="+activityType+",subscribersId"+subscribersId);
        logger.info("***********************开始 四轮及融合市场的运营分析->指标异常预警查询indexAabQuery 入参字段非空检查***********************");

        String bizCode = "0";
        String resultDesc = "";
        if(timeRang.equals("")){
            logger.error("timeRang参数为空~~~~~~~~~~~~~~~~~~~~~~~~~~`");
            bizCode = "-1";
        }
        if(!timeRang.contains(",")){
            logger.error("timeRang参数格式错误，正确格式为：开始日期,结束日期~~~~~~~~~~~~~~~~~~~~~~~~~~`");
            bizCode = "-1";
        }


        if(bizCode == "-1"){
            logger.info("***********************四轮及融合市场的运营分析->指标异常预警查询indexAabQuery 入参字段非空检查不通过***********************");
            resultDesc = "timeRang参数为空或者格式错误，正确格式为：yyyyMMdd,yyyyMMdd，且后面的日期不能小于前面的日期，请重置！";
            resultJSONObject.put("bizCodebizCodebizCode", bizCode);
            resultJSONObject.put("resultDesc", resultDesc);

        }else{
            logger.info("***********************四轮及融合市场的运营分析->指标异常预警查询indexAabQuery 入参字段非空检查通过***********************");

            try {
                JSONArray resultJSONArray = queryTask.indexAabQuery(kpiId, timeRang, cityId, activityType, subscribersId, Thread.currentThread().getStackTrace()[1].getMethodName());
                resultJSONObject.put("bizCode", bizCode);
                resultJSONObject.put("resultDesc", resultDesc);
                logger.debug("四轮及融合市场的运营分析->指标异常预警查询indexAabQuery 输出参数:::"+resultJSONArray.toString());
                resultJSONObject.put("resultData", resultJSONArray);
            } catch (Exception e) {
                bizCode = "-2";
                resultDesc = "数据查询失败，请重试！";
                resultJSONObject.put("bizCode", bizCode);
                resultJSONObject.put("resultDesc", resultDesc);
                e.printStackTrace();
            }

        }

        return resultJSONObject;
    }



    //四轮及融合市场的运营分析->指标关联关系查询
    @RequestMapping(value="/indexAssQuery",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject indexAssQuery(@RequestBody JSONObject paramJSONObject) {

        String kpiId = paramJSONObject.getString("kpiId");

        if(kpiId==null){
            kpiId = "";
        }

        JSONObject resultJSONObject = new JSONObject();

        logger.debug("四轮及融合市场的运营分析->指标关联关系查询indexAssQuery 输入参数:::kpiId="+kpiId);
        logger.info("***********************开始四轮及融合市场的运营分析->指标关联关系查询indexAssQuery 入参字段非空检查***********************");

        String bizCode = "0";
        String resultDesc = "";


        if(bizCode == "-1"){

            logger.info("***********************四轮及融合市场的运营分析->指标关联关系查询indexAssQuery 入参字段非空检查不通过***********************");
            resultDesc = "kpiId参数不允许为空，请重置！";
            resultJSONObject.put("bizCode", bizCode);
            resultJSONObject.put("resultDesc", resultDesc);
        }else{
            logger.info("***********************四轮及融合市场的运营分析->指标关联关系查询indexAssQuery 入参字段非空检查通过***********************");

            try {
                JSONArray resultJSONArray = queryTask.indexAssQuery(kpiId, Thread.currentThread().getStackTrace()[1].getMethodName());
                resultJSONObject.put("bizCode", bizCode);
                resultJSONObject.put("resultDesc", resultDesc);
                logger.debug("四轮及融合市场的运营分析->指标关联关系查询indexAssQuery 输出参数:::"+resultJSONArray.toString());
                resultJSONObject.put("resultData", resultJSONArray);
            } catch (Exception e) {
                bizCode = "-2";
                resultDesc = "数据查询失败，请重试！";
                resultJSONObject.put("bizCode", bizCode);
                resultJSONObject.put("resultDesc", resultDesc);
                e.printStackTrace();
            }

        }
        return resultJSONObject;
    }


    //四轮及融合市场的运营分析->指标订阅

    /**
     *
     * @param paramJSONObject
     * @return
     */
    @RequestMapping(value="/indexSubscription",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject indexSubscription(@RequestBody JSONObject paramJSONObject) {

        String kpiId = paramJSONObject.getString("kpiId");
        String operationType = paramJSONObject.getString("operationType");
        String subscribersPerson = paramJSONObject.getString("subscribersPerson");


        if(kpiId==null){
            kpiId = "";
        }
        if(operationType==null){
            operationType = "";
        }
        if(subscribersPerson==null){
            subscribersPerson = "";
        }

        JSONObject resultJSONObject = new JSONObject();
        JSONArray resultJSONArray = new JSONArray();

        logger.debug("四轮及融合市场的运营分析->指标订阅indexSubscription 输入参数:::kpiId="+kpiId+",operationType="+operationType+",subscribersPerson="+subscribersPerson);
        logger.info("***********************开始四轮及融合市场的运营分析->指标订阅indexSubscription 入参字段非空检查***********************");

        String bizCode = "0";
        String resultDesc = "";



        if(!operationType.equals("")){
            if(operationType.equals("1")){
                if(kpiId.equals("") || subscribersPerson.equals("")){
                    logger.info("***********************四轮及融合市场的运营分析->指标订阅indexSubscription 增加指标订阅 入参字段非空检查不通过***********************");
                    bizCode = "-1";
                    resultDesc = "新增指标订阅：kpiId参数、subscribersPerson参数均不允许为空，请重置！";
                    resultJSONObject.put("bizCode", bizCode);
                    resultJSONObject.put("resultDesc", resultDesc);
                }
                if(!kpiId.equals("") || !subscribersPerson.equals("")){//新增
                    logger.info("***********************四轮及融合市场的运营分析->指标订阅indexSubscription 增加指标订阅 入参字段非空检查通过***********************");
                    try {
                        resultJSONArray = queryTask.indexSubscription(kpiId, operationType, subscribersPerson, Thread.currentThread().getStackTrace()[1].getMethodName());
                        resultJSONObject.put("bizCode", bizCode);
                        resultJSONObject.put("resultDesc", "新增指标订阅成功");
                        logger.debug("四轮及融合市场的运营分析->指标订阅indexSubscription 增加指标订阅 输出参数:::"+resultJSONArray.toString());
                        resultJSONObject.put("resultData", resultJSONArray);
                    } catch (Exception e) {
                        bizCode = "-1";
                        resultDesc = "增加指标订阅出错，请重试！";
                        resultJSONObject.put("bizCode", bizCode);
                        resultJSONObject.put("resultDesc", resultDesc);
                        e.printStackTrace();
                    }
                }
            }
            if(operationType.equals("2")){
                if(subscribersPerson.equals("")){
                    logger.info("***********************四轮及融合市场的运营分析->指标订阅indexSubscription 删除已订阅指标 入参字段非空检查不通过***********************");
                    bizCode = "-1";
                    resultDesc = "删除已订阅指标：subscribersPerson参数不允许为空，请重置！";
                    resultJSONObject.put("bizCode", bizCode);
                    resultJSONObject.put("resultDesc", resultDesc);
                }else{//删除
                    logger.info("***********************四轮及融合市场的运营分析->指标订阅indexSubscription 删除已订阅指标 入参字段非空检查通过***********************");
                    try {
                        resultJSONArray = queryTask.indexSubscription(kpiId, operationType, subscribersPerson, Thread.currentThread().getStackTrace()[1].getMethodName());
                        resultJSONObject.put("bizCode", bizCode);
                        resultJSONObject.put("resultDesc", "删除指标订阅成功");
                        logger.debug("四轮及融合市场的运营分析->指标订阅indexSubscription  删除已订阅指标 输出参数:::"+resultJSONArray.toString());
                        resultJSONObject.put("resultData", resultJSONArray);
                    } catch (Exception e) {
                        bizCode = "-1";
                        resultDesc = "删除已订阅指标出错，请重试！";
                        resultJSONObject.put("bizCode", bizCode);
                        resultJSONObject.put("resultDesc", resultDesc);
                        e.printStackTrace();
                    }
                }

            }
            if(operationType.equals("3")){
                if(subscribersPerson.equals("")){
                    logger.info("***********************四轮及融合市场的运营分析->指标订阅indexSubscription 查询已订阅指标 入参字段非空检查不通过***********************");
                    bizCode = "-1";
                    resultDesc = "查询已订阅指标：subscribersPerson参数不允许为空，请重置！";
                    resultJSONObject.put("bizCode", bizCode);
                    resultJSONObject.put("resultDesc", resultDesc);
                }else{//查询
                    logger.info("***********************四轮及融合市场的运营分析->指标订阅indexSubscription 查询已订阅指标 入参字段非空检查通过***********************");
                    try {
                        resultJSONArray = queryTask.indexSubscription(kpiId, operationType, subscribersPerson, Thread.currentThread().getStackTrace()[1].getMethodName());
                        resultJSONObject.put("bizCode", bizCode);
                        resultJSONObject.put("resultDesc", "查询指标订阅成功");
                        logger.debug("四轮及融合市场的运营分析->指标订阅indexSubscription 查询已订阅指标 输出参数:::"+resultJSONArray.toString());
                        resultJSONObject.put("resultData", resultJSONArray);
                    } catch (Exception e) {
                        bizCode = "-1";
                        resultDesc = "查询已订阅指标出错，请重试！";
                        resultJSONObject.put("bizCode", bizCode);
                        resultJSONObject.put("resultDesc", resultDesc);
                        e.printStackTrace();
                    }
                }
            }



        }else{
            logger.error("operationType参数为空~~~~~~~~~~~~~~~~~~~~~~~~~~`");
            logger.info("***********************四轮及融合市场的运营分析->指标订阅indexSubscription 入参字段非空检查不通过***********************");
            bizCode = "-1";
            resultDesc = "operationType参数不允许为空，请重置！";
            resultJSONObject.put("bizCode", bizCode);
            resultJSONObject.put("resultDesc", resultDesc);
        }



        return resultJSONObject;
    }




    //四轮及融合市场的运营分析->移动客户综合业务视图服务
    /**
     *
     * @param paramJSONObject
     * @return
     */
    @RequestMapping(value="/ViewForMobileCust",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject ViewForMobileCust(@RequestBody JSONObject paramJSONObject){

        String userId = paramJSONObject.getString("userId");
        String templateId = paramJSONObject.getString("templateId");

        if(userId==null){
            userId = "";
        }
        if(templateId==null){
            templateId = "";
        }

        JSONObject resultJSONObject = new JSONObject();

        logger.debug("四轮及融合市场的运营分析->移动客户综合业务视图服务ViewForMobileCust 输入参数:::userId="+userId+",templateId="+templateId);
        logger.info("***********************开始四轮及融合市场的运营分析->移动客户综合业务视图服务ViewForMobileCust 入参字段非空检查***********************");

        String bizCode = "0";
        String resultDesc = "";

        if(userId.equals("")){
            logger.error("userId参数为空~~~~~~~~~~~~~~~~~~~~~~~~~~`");
            bizCode = "-1";
        }
        if(templateId.equals("")){
            logger.error("templateId参数为空~~~~~~~~~~~~~~~~~~~~~~~~~~`");
            bizCode = "-1";
        }

        if(bizCode == "-1"){
            logger.info("***********************四轮及融合市场的运营分析->移动客户综合业务视图服务ViewForMobileCust 入参字段非空检查不通过***********************");
            resultDesc = "userId参数、templateId参数均不允许为空，请重置！";
            resultJSONObject.put("bizCode", bizCode);
            resultJSONObject.put("resultDesc", resultDesc);
        }else{
            logger.info("***********************四轮及融合市场的运营分析->移动客户综合业务视图服务ViewForMobileCust 入参字段非空检查通过***********************");
            try {
                JSONArray resultJSONArray = queryTask.ViewForMobileCust(userId, templateId, Thread.currentThread().getStackTrace()[1].getMethodName());
                resultJSONObject.put("bizCode", bizCode);
                resultJSONObject.put("resultDesc", resultDesc);
                logger.debug("四轮及融合市场的运营分析->移动客户综合业务视图服务ViewForMobileCust 输出参数:::"+resultJSONArray.toString());
                resultJSONObject.put("resultData", resultJSONArray);
            } catch (Exception e) {
                bizCode = "-2";
                resultDesc = "数据查询失败，请重试！";
                resultJSONObject.put("bizCode", bizCode);
                resultJSONObject.put("resultDesc", resultDesc);
                e.printStackTrace();
            }

        }

        return resultJSONObject;
    }



    //四轮及融合市场的运营分析->家庭客户综合业务视图服务
    @RequestMapping(value="/ViewSrviceforHomeCust",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject ViewSrviceforHomeCust(@RequestBody JSONObject paramJSONObject){

        String userId = paramJSONObject.getString("userId");
        String homeId = paramJSONObject.getString("homeId");
        String templateId = paramJSONObject.getString("templateId");

        if(userId==null){
            userId = "";
        }
        if(homeId==null){
            homeId = "";
        }
        if(templateId==null){
            templateId = "";
        }

        JSONObject resultJSONObject = new JSONObject();

        logger.debug("四轮及融合市场的运营分析->家庭客户综合业务视图服务ViewSrviceforHomeCust 输入参数:::userId="+userId+",templateId="+templateId+",homeId="+homeId);
        logger.info("***********************开始四轮及融合市场的运营分析->家庭客户综合业务视图服务ViewSrviceforHomeCust 入参字段非空检查***********************");

        String bizCode = "0";
        String resultDesc = "";

        if(templateId.equals("")){
            logger.error("templateId参数为空~~~~~~~~~~~~~~~~~~~~~~~~~~`");
            bizCode = "-1";
        }

        if(bizCode == "-1"){
            logger.info("***********************四轮及融合市场的运营分析->家庭客户综合业务视图服务ViewSrviceforHomeCust 入参字段非空检查不通过***********************");
            resultDesc = "templateId参数不允许为空，请重置！";
            resultJSONObject.put("bizCode", bizCode);
            resultJSONObject.put("resultDesc", resultDesc);
        }else{
            logger.info("***********************四轮及融合市场的运营分析->家庭客户综合业务视图服务ViewSrviceforHomeCust 入参字段非空检查通过***********************");
            try {
                JSONArray resultJSONArray = queryTask.ViewSrviceforHomeCust(userId, homeId, templateId, Thread.currentThread().getStackTrace()[1].getMethodName());
                resultJSONObject.put("bizCode", bizCode);
                resultJSONObject.put("resultDesc", resultDesc);
                logger.debug("四轮及融合市场的运营分析->家庭客户综合业务视图服务ViewSrviceforHomeCust 输出参数:::"+resultJSONArray.toString());
                resultJSONObject.put("resultData", resultJSONArray);
            } catch (Exception e) {
                bizCode = "-2";
                resultDesc = "数据查询失败，请重试！";
                resultJSONObject.put("bizCode", bizCode);
                resultJSONObject.put("resultDesc", resultDesc);
                e.printStackTrace();
            }

        }

        return resultJSONObject;

    }


    //四轮及融合市场的运营分析->5G分析服务->5G号码分析
    @RequestMapping(value="/Analysisfor5G",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject Analysisfor5G(@RequestBody JSONObject paramJSONObject){

        String cityId = paramJSONObject.getString("cityId");
        String request = paramJSONObject.getString("request");
        String range = paramJSONObject.getString("range");

        if(cityId==null){
            cityId = "";
        }
        if(range==null){
            range = "";
        }
        if(request==null){
            request = "";
        }

        logger.debug("开始四轮及融合市场的运营分析->5G分析服务 Analysisfor5G 输入参数:::cityId="+cityId+",range="+range+",request="+request);
        logger.info("***********************开始四轮及融合市场的运营分析->5G分析服务 Analysisfor5G 入参字段非空检查***********************");

        JSONObject resultJSONObject = new JSONObject();
        String resultCode = "0";
        String resultDesc = "";


        if(cityId.equals("")){
            logger.error("cityId参数为空~~~~~~~~~~~~~~~~~~~~~~~~~~`");
            resultCode = "-1";
        }
        if(range.equals("")){
            logger.error("range参数为空~~~~~~~~~~~~~~~~~~~~~~~~~~`");
            resultCode = "-1";
        }
        if(request.equals("")){
            logger.error("request参数为空~~~~~~~~~~~~~~~~~~~~~~~~~~`");
            resultCode = "-1";
        }


        if(resultCode == "-1"){
            logger.info("***********************四轮及融合市场的运营分析->5G分析服务 Analysisfor5G 入参字段非空检查不通过***********************");
            resultDesc = "cityId参数、range参数、request参数均不允许为空，请重置！";
            resultJSONObject.put("resultCode", resultCode);
            resultJSONObject.put("resultDesc", resultDesc);
        }else{
            logger.info("***********************四轮及融合市场的运营分析->5G分析服务 Analysisfor5G 入参字段非空检查通过***********************");
            try {
                JSONArray resultJSONArray = queryTask.Analysisfor5G(cityId,range,request, Thread.currentThread().getStackTrace()[1].getMethodName());
                logger.debug("四轮及融合市场的运营分析->5G分析服务 Analysisfor5G 输出参数:::"+resultJSONArray.toString());
                resultJSONObject.put("resultCode", resultCode);
                resultJSONObject.put("resultDesc", resultDesc);
                resultJSONObject.put("resultData", resultJSONArray);
            } catch (Exception e) {
                resultCode = "-2";
                resultDesc = "数据查询失败，请重试！";
                resultJSONObject.put("resultCode", resultCode);
                resultJSONObject.put("resultDesc", resultDesc);
                e.printStackTrace();
            }
        }

        return resultJSONObject;
    }



    //四轮及融合市场的运营分析->5G分析服务->5G号码分析
    @RequestMapping(value="/Analysisfor5GwithPer",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject Analysisfor5GwithPer(@RequestBody JSONObject paramJSONObject){

        String userId = paramJSONObject.getString("userId");

        if(userId==null){
            userId = "";
        }

        logger.debug("开始四轮及融合市场的运营分析->5G分析服务->5G号码分析Analysisfor5GwithPer 输入参数:::userId="+userId);
        logger.info("***********************开始四轮及融合市场的运营分析->5G分析服务->5G号码分析Analysisfor5GwithPer 入参字段非空检查***********************");

        JSONObject resultJSONObject = new JSONObject();
        String resultCode = "0";
        String resultDesc = "";

        if(userId.equals("")){
            logger.error("userId参数为空~~~~~~~~~~~~~~~~~~~~~~~~~~`");
            resultCode = "-1";
        }

        if(resultCode == "-1"){
            logger.info("***********************四轮及融合市场的运营分析->5G分析服务->5G号码分析Analysisfor5GwithPer 入参字段非空检查不通过***********************");
            resultDesc = "userId参数不允许为空，请重置！";
            resultJSONObject.put("resultCode", resultCode);
            resultJSONObject.put("resultDesc", resultDesc);
        }else{
            logger.info("***********************四轮及融合市场的运营分析->5G分析服务->5G号码分析Analysisfor5GwithPer 入参字段非空检查通过***********************");
            try {
                JSONArray resultJSONArray = queryTask.Analysisfor5GwithPer(userId, Thread.currentThread().getStackTrace()[1].getMethodName());
                logger.debug("四轮及融合市场的运营分析->5G分析服务->5G号码分析Analysisfor5GwithPer 输出参数:::"+resultJSONArray.toString());
                resultJSONObject.put("resultCode", resultCode);
                resultJSONObject.put("resultDesc", resultDesc);
                resultJSONObject.put("resultData", resultJSONArray);
            } catch (Exception e) {
                resultCode = "-2";
                resultDesc = "数据查询失败，请重试！";
                resultJSONObject.put("resultCode", resultCode);
                resultJSONObject.put("resultDesc", resultDesc);
                e.printStackTrace();
            }
        }
        return resultJSONObject;
    }


    //四轮及融合市场的运营分析->5G分析服务->5G分析视图
    @RequestMapping(value="/Analysisfor5GView",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject Analysisfor5GView(@RequestBody JSONObject paramJSONObject){

        String kpi_type = paramJSONObject.getString("kpi_type");
        String area_id = paramJSONObject.getString("area_id");
        String area_level = paramJSONObject.getString("area_level");

        if(kpi_type==null){
            kpi_type = "";
        }
        if(area_id==null){
            area_id = "";
        }
        if(area_level==null){
            area_level = "";
        }


        logger.debug("四轮及融合市场的运营分析->5G分析服务->5G分析视图Analysisfor5GView 输入参数:::kpi_type="+kpi_type+",area_id="+area_id+",area_level="+area_level);
        logger.info("***********************开始四轮及融合市场的运营分析->5G分析服务->5G分析视图Analysisfor5GView 入参字段非空检查***********************");

        JSONObject resultJSONObject = new JSONObject();
        String resultCode = "0";
        String resultDesc = "";

        if(kpi_type.equals("")){
            logger.error("kpi_type参数为空~~~~~~~~~~~~~~~~~~~~~~~~~~`");
            resultCode = "-1";
        }
        if(area_id.equals("")){
            logger.error("area_id参数为空~~~~~~~~~~~~~~~~~~~~~~~~~~`");
            resultCode = "-1";
        }
        if(area_level.equals("")){
            logger.error("area_level参数为空~~~~~~~~~~~~~~~~~~~~~~~~~~`");
            resultCode = "-1";
        }

        if(resultCode == "-1"){
            logger.info("***********************四轮及融合市场的运营分析->5G分析服务->5G分析视图Analysisfor5GView 入参字段非空检查不通过***********************");
            resultDesc = "kpi_type参数不允许为空，请重置！";
            resultJSONObject.put("resultCode", resultCode);
            resultJSONObject.put("resultDesc", resultDesc);
        }else{
            logger.info("***********************四轮及融合市场的运营分析->5G分析服务->5G分析视图Analysisfor5GView 入参字段非空检查通过***********************");
            try {
                JSONArray resultJSONArray = queryTask.Analysisfor5GView(kpi_type,area_id,area_level,Thread.currentThread().getStackTrace()[1].getMethodName());
                logger.debug("四轮及融合市场的运营分析->5G分析服务->5G分析视图Analysisfor5GView 输出参数:::"+resultJSONArray.toString());
                resultJSONObject.put("resultCode", resultCode);
                resultJSONObject.put("resultDesc", resultDesc);
                resultJSONObject.put("resultData", resultJSONArray);
            } catch (Exception e) {
                resultCode = "-2";
                resultDesc = "数据查询失败，请重试！";
                resultJSONObject.put("resultCode", resultCode);
                resultJSONObject.put("resultDesc", resultDesc);
                e.printStackTrace();
            }
        }
        return resultJSONObject;

    }


    //政企潜在客户挖掘->潜在客户识别服务
    @RequestMapping(value="/customerIdentify",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject customerIdentify(@RequestBody JSONObject paramJSONObject) {

        String gridId = paramJSONObject.getString("gridId");
        String channelType = paramJSONObject.getString("channelType");

        if(gridId==null){
            gridId = "";
        }
        if(channelType==null){
            channelType = "";
        }

        JSONObject resultJSONObject = new JSONObject();

        logger.debug("政企潜在客户挖掘->潜在客户识别服务:::gridId="+gridId+",channelType="+channelType);
        logger.info("***********************开始政企潜在客户挖掘->潜在客户识别服务 入参字段非空检查***********************");

        String resultCode = "0";
        String resultDesc = "";

        if(gridId.equals("")){
            logger.error("gridId参数为空~~~~~~~~~~~~~~~~~~~~~~~~~~`");
            resultCode = "-1";
        }
        if(channelType.equals("")){
            logger.error("channelType参数为空~~~~~~~~~~~~~~~~~~~~~~~~~~`");
            resultCode = "-1";
        }

        if(resultCode == "-1"){
            logger.info("***********************政企潜在客户挖掘->潜在客户识别服务 入参字段非空检查不通过***********************");
            resultDesc = "gridId参数、channelType参数均不允许为空，请重置！";
            resultJSONObject.put("resultCode", resultCode);
            resultJSONObject.put("resultDesc", resultDesc);
        }else{
            logger.info("***********************政企潜在客户挖掘->潜在客户识别服务 入参字段非空检查通过***********************");
            try {
                JSONArray resultJSONArray = queryTask.customerIdentify(gridId, channelType, Thread.currentThread().getStackTrace()[1].getMethodName());
                logger.debug("政企潜在客户挖掘->潜在客户识别服务 输出参数:::"+resultJSONArray.toString());
                resultJSONObject.put("resultCode", resultCode);
                resultJSONObject.put("resultDesc", resultDesc);
                resultJSONObject.put("resultData", resultJSONArray);
            } catch (Exception e) {
                resultCode = "-2";
                resultDesc = "数据查询失败，请重试！";
                resultJSONObject.put("resultCode", resultCode);
                resultJSONObject.put("resultDesc", resultDesc);
                e.printStackTrace();
            }
        }


        return resultJSONObject;
    }



    //测试参数
    @RequestMapping(value="/test20201016",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject test20201016(String kpiId,String kpiName,String kpiType,String subscribersPerson) {


        logger.debug("charset=========="+charset);
        logger.debug("入参>>>>>>");

        JSONObject resultJsonObject = new JSONObject();//结果参数

        logger.debug("四轮及融合市场的运营分析->指标列表查询服务indexListQuery 输入参数:::kpiId="+kpiId+",kpiName="+kpiName+",kpiType="+kpiType+",subscribersPerson="+subscribersPerson);


        logger.info("***********************开始 四轮及融合市场的运营分析->指标列表查询服务indexListQuery 入参字段非空检查***********************");
        String bizCode = "0";
        String resultDesc = "";
        if(subscribersPerson.equals("")){
            logger.error("subscribersPerson参数为空~~~~~~~~~~~~~~~~~~~~~~~~~~`");
            bizCode = "-1";
        }

        if(bizCode == "-1"){

            logger.info("***********************四轮及融合市场的运营分析->指标列表查询服务indexListQuery 入参字段非空检查不通过***********************");
            resultDesc = "请检查subscribersPerson参数，该参数不允许为空！";
            resultJsonObject.put("bizCode", bizCode);
            resultJsonObject.put("resultDesc", resultDesc);

        }else{

            logger.info("***********************四轮及融合市场的运营分析->指标列表查询服务indexListQuery 入参字段非空检查通过***********************");

            try {
                //JSONArray resultJSONArray = queryTask.indexListQuery(kpiId, kpiName, kpiType, subscribersPerson, Thread.currentThread().getStackTrace()[1].getMethodName());
                resultJsonObject.put("bizCode", "0");
                resultJsonObject.put("resultDesc", "测试!!!!!!!!!!!!!!!!!!!");
                //logger.info("四轮及融合市场的运营分析->指标列表查询服务indexListQuery 输出参数"+resultJSONArray.toString());
                // resultJsonObject.put("resultData", resultJSONArray);
            } catch (Exception e) {
                bizCode = "-2";
                resultDesc = "数据查询失败，请重试！";
                resultJsonObject.put("bizCode", bizCode);
                resultJsonObject.put("resultDesc", resultDesc);
                e.printStackTrace();
            }


        }


        return resultJsonObject;

    }








}
