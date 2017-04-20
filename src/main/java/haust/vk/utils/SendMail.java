package haust.vk.utils;

import java.security.Security;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.sun.jmx.snmp.Timestamp;


public class SendMail {
	/**
	 * 
	 * @Author : viakiba
	 * @param tomail  邮箱
	 * @param tokenid 验证码
	 * 2017-04-20
	 */
    public static void sendMail(String tomail,String tokenid) throws Exception{
            //设置SSL连接、邮件环境
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());  
            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";  
            Properties props = System.getProperties();
            props.setProperty("mail.smtp.host", "smtp.qq.com");
            props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            props.setProperty("mail.smtp.port", "465");
            props.setProperty("mail.smtp.socketFactory.port", "465");
            props.setProperty("mail.smtp.auth", "true");
            //建立邮件会话
            Session session = Session.getDefaultInstance(props, new Authenticator() {
                //身份认证
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("892645423@qq.com","gnvmezqboyvpbceg");
                }
            });
            //建立邮件对象
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("892645423@qq.com"));//from_mail@qq.com
            message.setRecipients(Message.RecipientType.TO, tomail);//to_mail@qq.com
            message.setSubject("KaraNotes找回密码");
            //文本部分
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent("Hi：</br> 您的验证码是："+tokenid+(new Timestamp()).getDateTime(), "text/html;charset=UTF-8");
            MimeMultipart mmp1 = new MimeMultipart();
            mmp1.addBodyPart(textPart);
            mmp1.setSubType("related");
            MimeBodyPart textImagePart = new MimeBodyPart();
            textImagePart.setContent(mmp1);
            message.setContent(mmp1);
            message.saveChanges();
            //发送邮件
            Transport.send(message);
    }
    
    public static void main(String[] args) throws Exception {
		sendMail("viakiba@126.com","sa");
	}
}