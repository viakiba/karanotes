package haust.vk.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMail {
    private static final String ALIDM_SMTP_HOST = "smtpdm.aliyun.com";
    private static final int ALIDM_SMTP_PORT = 25;

    public static void sendEmail(String to_email, String code, String username) throws MessagingException {
        // 配置发送邮件的环境属性
        final Properties props = new Properties();
        // 表示SMTP发送邮件，需要进行身份验证
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", ALIDM_SMTP_HOST);
        props.put("mail.smtp.port", ALIDM_SMTP_PORT);   

        // 发件人的账号
        props.put("mail.user", "karanotes@email.viakiba.cn");
        // 访问SMTP服务时需要提供的密码
        props.put("mail.password", "18037650338QazZ");

        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
        // 使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(props, authenticator);
        // 创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);
        // 设置发件人
        InternetAddress form = new InternetAddress(
                props.getProperty("mail.user"));
        message.setFrom(form);

        // 设置收件人
        InternetAddress to = new InternetAddress(to_email);
        message.setRecipient(MimeMessage.RecipientType.TO, to);

        // 设置邮件标题
        message.setSubject("karanotes:验证码");
        // 设置邮件的内容体
        message.setContent("<p>    你好："+username+
        					"</p><p>    &nbsp; &nbsp; &nbsp; 您在karanotes的验证码是："+
        					"</p><p>    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;"+code+
        					"</p><p>    &nbsp; &nbsp; &nbsp; 感谢您的使用。</p>请不要回复此邮件，请联系: <a href=\"mailto:karanotes@viakiba.cn\">"+
        					"karanotes</a>", "text/html;charset=UTF-8");
        // 发送邮件
        Transport.send(message);
    }
    
    public static void main(String[] args) throws MessagingException {
		SendMail sendMail = new SendMail();
		sendMail.sendEmail("892645423@qq.com", "111", "viakiba");
	}
}