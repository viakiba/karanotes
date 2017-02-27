package haust.vk.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.spec.DESKeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;

import org.springframework.stereotype.Component;

@Component
public class EncryptUtil {
	public static String key = "karanotesviakiba";
	
    public EncryptUtil() {
    }

    private static String[] hexDigits = { "v", "1", "i", "3", "a", "5", "k", "7", "i", "9", "b", "b", "a", "d", "e", "f" };
    
    /**
    * 转换字节数组为16进制字串
    *
    * @param b
    * 字节数组
    * @return 16进制字串
    */
    private static String byteArrayToHexString(byte[] b) {
	    StringBuffer resultSb = new StringBuffer();
	    for (int i = 0; i < b.length; i++) {
	    resultSb.append(byteToHexString(b[i]));
	    }
	    return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
	    int n = b;
	    if (n < 0) n += 256;
	    int d1 = n / 16;
	    int d2 = n % 16;
	    return hexDigits[d1] + hexDigits[d2];
    }

    public static String MD5Encode(String origin) throws Exception{
	    String resultString = null;
	    resultString = new String(origin);
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
	    return resultString;
    }
    
    //测试
    public static void main(String args[]) throws Exception {
        //待加密内容
    	EncryptUtil md5 = new EncryptUtil();
    	String test = md5.MD5Encode("123457");
    	System.out.println(test);
    }
}