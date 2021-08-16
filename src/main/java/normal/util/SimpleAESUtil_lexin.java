package normal.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

public class SimpleAESUtil_lexin {
	protected static Logger logger = LoggerFactory.getLogger(SimpleAESUtil_lexin.class);

	private static final String Algorithm = "AES";
	//--从服务器获取密钥
	private static final String pwd = "&*($HJDGH4867%&T345386754OHYOH*^(ughiuR5fu&f&$KHAOS$&^%";

	/*static {
		String runEnv = System.getProperty("spring.profiles.active");
		if (StringUtils.isNotEmpty(runEnv) && (runEnv.startsWith("dev"))) {
			// 开发环境key
			pwd = "&*($HJDGH4867%&T345386754OHYOH*^(ughiuR5fu&f&$KHAOS$&^%";
		} else {
			pwd = System.getProperty("system.simpleAes.key");
		}
	}*/

	/**
	 * 加密 对传输进来的明文进行加密处理
	 * plainText：明文
	 * @author yxt
	 */
	public static String encrypt(String plainText) {
		String encrypt=null;
		logger.debug("加密前:{}", plainText);
		if(StringUtils.isNotBlank(plainText)){
			try {
				encrypt = encrypt(plainText,pwd);
			} catch (Exception e) {
				logger.error("加密异常：",e);
			}
		}
		logger.debug("加密后：{}", encrypt);
		return encrypt;
	}

	/**
	 * 解密 以String密文输入,String明文输出
	 * cipherText：密文
	 * @author yxt
	 */
	public static String decrypt(String cipherText) {
		String decrypt = null;
		logger.debug("解密前:{}", cipherText);
		if(StringUtils.isBlank(cipherText)){
			return null;
		}
		try {
			decrypt = decrypt(cipherText,pwd);
		} catch (Exception e) {
			logger.error("对传输进来的密文解密失败",e);
		}
		logger.debug("解密后：{}", decrypt);
		return decrypt;
	}
	/**
	 * 加密
	 *
	 * @param plainText
	 *            明文
	 *            16位的随机码
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String plainText, String password) throws Exception {
    	try{
    		return SecurityUtil.toHex(encrypt(plainText.getBytes("UTF-8"),password));
    	}catch (UnsupportedEncodingException e) {
			throw new Exception(e);
		}
    }

	/**
	 * 解密 以String密文输入,String明文输出
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String cipherText, String password) throws Exception {
    	try{
	    	byte[] bytes = decrypt(SecurityUtil.hexTobytes(cipherText),password);
	        return new String(bytes,"UTF-8");
	    }catch (UnsupportedEncodingException e) {
			throw new Exception(e);
		}
    }

	 /**
		 * 加密以byte[]明文输入,byte[]密文输出
		 *
		 * @param byteS
		 * @throws Exception
		 */
		public static byte[] encrypt(byte[] byteS, String pwd){

	        byte[] byteFina = null;
	        Cipher cipher;
	        try {
	            cipher = Cipher.getInstance(Algorithm);
				SecretKeySpec keySpec = new SecretKeySpec(getKey(pwd), "AES");
				cipher.init(Cipher.ENCRYPT_MODE, keySpec);
				byteFina = cipher.doFinal(byteS);
	        } catch (Exception e) {
		       logger.error("加密出现异常+",e);
	        } finally {
	            cipher = null;
	        }

	        return byteFina;
	    }

		/**
		 * 解密以byte[]密文输入,以byte[]明文输出
		 *
		 * @param byteD
		 * @return
		 * @throws Exception
		 */
		public static byte[] decrypt(byte[] byteD, String pwd) throws Exception {
	        Cipher cipher;
	        byte[] byteFina = null;
	        try {
	            cipher = Cipher.getInstance(Algorithm);

	    		SecretKeySpec keySpec = new SecretKeySpec(getKey(pwd), "AES");

	    		cipher.init(Cipher.DECRYPT_MODE, keySpec);

	    		byteFina = cipher.doFinal(byteD);

	        } catch (Exception e) {
				throw new Exception(e);
	        } finally {
	            cipher = null;
	        }
	        return byteFina;
	    }

	    private static byte[] getKey(String password) throws UnsupportedEncodingException {
	    	// 使用256位密码
	    	if(password.length() > 16) {
				password = password.substring(0, 16);
			} else if(password.length() < 16){
	    		int count = (16 - password.length());
	    		for(int i=0;i<count;i++){
	    			password+="0";
	    		}
	    	}

	    	return password.getBytes("UTF-8");
	    }
}


