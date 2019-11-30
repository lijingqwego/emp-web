package com.kaisn.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kaisn.mysql.InWhere;
import com.kaisn.mysql.OutWhere;
import com.kaisn.mysql.QueryParam;
import com.kaisn.mysql.QueryToStructUtils;
import com.kaisn.pojo.Msg;
import com.kaisn.service.IDynamicQueryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/emp")
public class EmployeeController {

    private static Logger logger = Logger.getLogger(EmployeeController.class);

    @Autowired
    private IDynamicQueryService iDynamicQueryService;

    @ResponseBody
    @RequestMapping(value = "/query",method = RequestMethod.POST)
    public Msg query(HttpServletRequest request){
        String values = request.getParameter("values");
        QueryParam queryParam = QueryToStructUtils.createQueryParam(values);
        List<Map<String, Object>> maps = iDynamicQueryService.querySql(queryParam);
        return Msg.success().add("list",maps);
    }
}
