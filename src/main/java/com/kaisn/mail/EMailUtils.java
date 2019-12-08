package com.kaisn.mail;

import com.kaisn.utils.encry.SymmetricEncoder;
import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class EMailUtils {

    //邮件服务器主机名
    // QQ邮箱的 SMTP 服务器地址为: smtp.qq.com
    private static final String EMAIL_HOST = "smtp.qq.com";
    //发件人邮箱
    private static final String EMAIL_ACCOUNT = "1169318609@qq.com";
    //发件人邮箱密码（授权码）
    //在开启SMTP服务时会获取到一个授权码，把授权码填在这里
    private static final String EMAIL_PASSWORD = "dTs4sIasvmOWzu5DJnxxxw==";
    /**
     * 邮件单发（自由编辑短信，并发送，适用于私信）
     * @param address 收件箱地址
     * @param title 邮件主题
     * @param content 邮件内容
     */
    public static void sendEmail(String address,String title,String content) {
        Transport transport = null;
        try {

            String emailPass = SymmetricEncoder.AESDncode(EMAIL_PASSWORD);

            Properties props = new Properties();
            // 开启debug调试
            props.setProperty("mail.debug", "true");
            // 发送服务器需要身份验证
            props.setProperty("mail.smtp.auth", "true");
            // 端口号
            props.put("mail.smtp.port", 465);
            // 设置邮件服务器主机名
            props.setProperty("mail.smtp.host", EMAIL_HOST);
            // 发送邮件协议名称
            props.setProperty("mail.transport.protocol", "smtp");
            /**SSL认证，注意腾讯邮箱是基于SSL加密的，所以需要开启才可以使用**/
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            //设置是否使用ssl安全连接（一般都使用）
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.ssl.socketFactory", sf);
            //创建会话
            Session session = Session.getInstance(props);
            //获取邮件对象
            //发送的消息，基于观察者模式进行设计的
            Message msg = new MimeMessage(session);
            //设置邮件标题
            msg.setSubject(title);
            //设置显示的发件时间
            msg.setSentDate(new Date());
            //设置邮件内容
            msg.setText(content);
            //设置发件人邮箱
            // InternetAddress 的三个参数分别为: 发件人邮箱, 显示的昵称(只用于显示, 没有特别的要求), 昵称的字符集编码
            msg.setFrom(new InternetAddress(EMAIL_ACCOUNT,"好学堂", "UTF-8"));
            //得到邮差对象
            transport = session.getTransport();
            //连接自己的邮箱账户
            //密码不是自己QQ邮箱的密码，而是在开启SMTP服务时所获取到的授权码
            //connect(host, user, password)
            transport.connect(EMAIL_HOST, EMAIL_ACCOUNT, emailPass);
            //发送邮件
            transport.sendMessage(msg, new Address[] { new InternetAddress(address) });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(transport != null){
                try {
                    transport.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
