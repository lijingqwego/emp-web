package com.kaisn.controller;


import com.kaisn.druid.QueryContant;
import com.kaisn.druid.QueryEntrance;
import com.kaisn.mail.EMailUtils;
import com.kaisn.mail.VerifyCodeUtil;
import com.kaisn.mysql.QueryParam;
import com.kaisn.mysql.QueryToStructUtils;
import com.kaisn.pojo.Msg;
import com.kaisn.service.IDynamicQueryService;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
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

    @ResponseBody
    @RequestMapping(value = "/count",method = RequestMethod.POST)
    public Msg count(HttpServletRequest request){
        String values = request.getParameter("values");
        Map<String, Object> resultMap = QueryEntrance.queryDruid(QueryContant.QUERY_TYPE_TOPN, values);
        Object data = resultMap.get("data");
        return Msg.success().add("list",data);
    }

    @ResponseBody
    @RequestMapping(value = "/push",method = RequestMethod.POST)
    public Msg push(HttpServletRequest request, @RequestParam("file") CommonsMultipartFile file){
        String path="/home/lijjing/"+System.currentTimeMillis()+file.getOriginalFilename();
        String tableName = request.getParameter("tableName");
        int count = 0;
        try {
            File newFile=new File(path);
            file.transferTo(newFile);
            String values = FileUtils.readFileToString(newFile);
            count = iDynamicQueryService.push(tableName,values);
        } catch (IOException e) {
            logger.error("上传文件失败",e);
        }
        return Msg.success().add("list",count);
    }

    /**
     * 发送自由编辑的邮件
     * @param request
     * @return
     */
    @RequestMapping(value={"/sendEmail"},method=RequestMethod.POST)
    @ResponseBody
    public Msg sendEmailOwn(HttpServletRequest request){
        try{
            //收件人邮箱
            String toEmailAddress = request.getParameter("toEmailAddress");
            //邮件主题
            String emailTitle = request.getParameter("emailTitle");
            //邮件内容
            String emailContent = request.getParameter("emailContent");
            //发送邮件
            EMailUtils.sendEmail(toEmailAddress, emailTitle, emailContent);
            return Msg.success();
        }catch(Exception e){
            logger.error("邮件发送失败",e);
            return Msg.fail();
        }
    }

    @RequestMapping(value={"/sendEmailSystem/"},method=RequestMethod.POST)
    @ResponseBody
    public Msg sendEmailSystem(@RequestParam("toEmailAddress") String toEmailAddress){
        try{
            //生成验证码
            String verifyCode = VerifyCodeUtil.generateVerifyCode(6);
            //邮件主题
            String emailTitle = "【好学堂】邮箱验证";
            //邮件内容
            String emailContent = "您正在【好学堂】进行邮箱验证，您的验证码为：" + verifyCode + "，请于10分钟内完成验证！";
            //发送邮件
            EMailUtils.sendEmail(toEmailAddress, emailTitle, emailContent);
            return Msg.success().add("verifyCode",verifyCode);
        } catch(Exception e) {
            logger.error("邮件发送失败",e);
            return Msg.fail();
        }
    }
}
