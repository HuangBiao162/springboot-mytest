package normal.util;

/**
 * 安全常量类
 *
 * @Author: hex
 * @Date: Create in 15:35 2017/10/25
 */
public class SecurityContants {

	/**
	 * 版本号
	 */
	public static final String VERSION = "V1.0";

	/**
	 * 编码格式：UTF-8
	 */
	public static final String CHARSET_UTF8 = "UTF-8";

	/**
	 * 密钥算法：RSA
	 */
	public static final String KEY_ALGORITHM_RSA = "RSA";

	/**
	 * 密钥算法：AES
	 */
	public static final String KEY_ALGORITHM_AES = "AES";

	/**
	 * RSA密钥位数
	 */
	public static final int RSA_KEYSIZE_1024 = 1024;

	/**
	 * 签名算法
	 */
	public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

	/**
	 * AES加密、填充模式：AES/ECB/PKCS5Padding
	 */
	public static final String AES_ALGORITHM_MODE = "AES/ECB/PKCS5Padding";

	/**
	 * KEY-签名字段
	 */
	public static final String KEY_SIGN = "sign";

}