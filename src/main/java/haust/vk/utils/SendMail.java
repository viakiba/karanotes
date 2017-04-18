package haust.vk.utils;

import java.security.Security;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMail {
		// 设置服务器
		private static String KEY_SMTP = "mail.smtp.host";
		private static String VALUE_SMTP = "smtp.qq.com";
		// 服务器验证
		private static String KEY_PROPS = "mail.smtp.auth";
		private static boolean VALUE_PROPS = true;
		// 发件人用户名、密码
		private static String SEND_USER = "892645423@qq.com";
		private static String SEND_UNAME = "892645423@qq.com";
		private static String SEND_PWD = "sxavlagxjlyabfbe";
		// 建立会话
		private MimeMessage message;
		private Session s;
		
		/*
		 * 初始化方法
		 */
		public SendMail() {
			Properties props = System.getProperties();
			props.setProperty(KEY_SMTP, VALUE_SMTP);
			props.put(KEY_PROPS, "true");
			//props.put("mail.smtp.auth", "true");
			s =  Session.getDefaultInstance(props, new Authenticator(){
			      protected PasswordAuthentication getPasswordAuthentication() {
			          return new PasswordAuthentication(SEND_UNAME, SEND_PWD);
			      }});
			s.setDebug(true);
			message = new MimeMessage(s);
		}

		/**
		 * 发送邮件
		 * 
		 * @param headName
		 *            邮件头文件名
		 * @param sendHtml
		 *            邮件内容
		 * @param receiveUser
		 *            收件人地址
		 */
		public void doSendHtmlEmail(String headName, String sendHtml,
				String receiveUser) {
			try {
				// 发件人
				InternetAddress from = new InternetAddress(SEND_USER);
				message.setFrom(from);
				// 收件人
				InternetAddress to = new InternetAddress(receiveUser);
				message.setRecipient(Message.RecipientType.TO, to);
				// 邮件标题
				message.setSubject(headName);
				String content = sendHtml.toString();
				// 邮件内容,也可以使纯文本"text/plain"
				message.setContent(content, "text/html;charset=GBK");
				message.saveChanges();
				Transport transport = s.getTransport("smtp");
				// smtp验证，就是你用来发邮件的邮箱用户名密码
				transport.connect(VALUE_SMTP, SEND_UNAME, SEND_PWD);
				// 发送
				transport.sendMessage(message, message.getAllRecipients());
				transport.close();
				System.out.println("send success!");
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
		
//		private static String username="service@Xdailiao.com";
//		private static String username="service@service.xdailiao.com";
//		private static String password="Tuiguang123";
		/**
		 * ssl加密方式
		 * 参数解释
		 * @param to_mail 发送目标
		 * @param key     型号
		 * @param num	    数量
		 * @param url1	    提交链接
		 */
	    public static void sendMailSSL(String to_mail,String user_name,String code) {
	    	String content = "Hi，"+user_name+"</br>"+
	    					"验证码是："+code;
	    	try {
	            //设置SSL连接、邮件环境
	            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());  
	            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";  
	            Properties props = System.getProperties();
//	            props.setProperty("mail.smtp.host", "smtp.exmail.qq.com");
	            props.setProperty("mail.smtp.host","smtpdm.aliyun.com");
	            props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
	            props.setProperty("mail.smtp.socketFactory.fallback", "false");
	            props.setProperty("mail.smtp.port", "465");
	            props.setProperty("mail.smtp.socketFactory.port", "465");
	            props.setProperty("mail.smtp.auth", "true");
	            //建立邮件会话
	            Session session = Session.getDefaultInstance(props, new Authenticator() {
	                //身份认证
	                protected PasswordAuthentication getPasswordAuthentication() {
	                    return new PasswordAuthentication(SEND_UNAME, SEND_PWD);
	                }
	            });
	            //建立邮件对象
	            MimeMessage message = new MimeMessage(session);
	            Address[] addresses = new Address[1];
	            addresses[0]=new InternetAddress(SEND_UNAME);
	            message.setReplyTo(addresses);
	            //设置邮件的发件人、收件人、主题   附带发件人名字
//	            message.setFrom(new InternetAddress("from_mail@qq.com", "optional-personal"));
	            message.setFrom(new InternetAddress(SEND_UNAME));
	            message.setRecipients(Message.RecipientType.TO, to_mail);
	            message.setSubject("验证码");
	            //文本部分
	            MimeBodyPart textPart = new MimeBodyPart();
	            textPart.setContent(content, "text/html;charset=UTF-8");
	            MimeMultipart mmp1 = new MimeMultipart();
	            mmp1.addBodyPart(textPart);
	            MimeMultipart mmp2 = new MimeMultipart();
	            mmp2.setSubType("mixed");
	            message.setContent(mmp1);
	            message.saveChanges();
	            //发送邮件
	            Transport.send(message);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
		    
}
