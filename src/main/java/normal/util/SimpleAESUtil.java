package normal.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

public class SimpleAESUtil {
    protected static Logger logger = LoggerFactory.getLogger(SimpleAESUtil.class);

    private static final String ALGORITHM = "AES";
    //--从服务器获取密钥
    private static String secret;

    static {
        String runEnv = System.getProperty("spring.profiles.active");
        if (StringUtils.isNotEmpty(runEnv) && (runEnv.startsWith("dev"))) {
            // 开发环境key
            secret = "&*($HJDGH4867%&T345386754OHYOH*^(ughiuR5fu&f&$KHAOS$&^%";
        } else {
            secret = System.getProperty("system.simpleAes.key");
        }
    }

    private SimpleAESUtil() {
    }

    /**
     * 加密 对传输进来的明文进行加密处理
     * plainText：明文
     *
     * @author yxt
     */
    public static String encrypt(String plainText) {
        String encrypt = null;
        logger.debug("加密前:{}", plainText);
        if (StringUtils.isNotBlank(plainText)) {
            try {
                encrypt = encrypt(plainText, secret);
            } catch (Exception e) {
                logger.error("加密异常：", e);
            }
        }
        logger.debug("加密后：{}", encrypt);
        return encrypt;
    }

    /**
     * 解密 以String密文输入,String明文输出
     * cipherText：密文
     *
     * @author yxt
     */
    public static String decrypt(String cipherText) {
        String decrypt = null;
        logger.debug("解密前:{}", cipherText);
        if (StringUtils.isBlank(cipherText)) {
            return null;
        }
        try {
            decrypt = decrypt(cipherText, secret);
        } catch (Exception e) {
            logger.error("对传输进来的密文解密失败", e);
        }
        logger.debug("解密后：{}", decrypt);
        return decrypt;
    }

    /**
     * 加密
     *
     * @param plainText 明文
     *                  16位的随机码
     * @return
     * @throws Exception
     */
    public static String encrypt(String plainText, String password) {
        return SecurityUtil.toHex(encrypt(plainText.getBytes(StandardCharsets.UTF_8), password));
    }

    /**
     * 解密 以String密文输入,String明文输出
     *
     * @return
     * @throws Exception
     */
    public static String decrypt(String cipherText, String password) throws GeneralSecurityException, UnsupportedEncodingException {
        byte[] bytes = decrypt(SecurityUtil.hexTobytes(cipherText), password);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * 加密以byte[]明文输入,byte[]密文输出
     *
     * @param byteS
     * @throws Exception
     */
    public static byte[] encrypt(byte[] byteS, String pwd) {

        byte[] byteFina = null;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(getKey(pwd), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byteFina = cipher.doFinal(byteS);
        } catch (GeneralSecurityException | UnsupportedEncodingException e) {
            logger.error("加密出现异常+", e);
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
    public static byte[] decrypt(byte[] byteD, String pwd) throws GeneralSecurityException, UnsupportedEncodingException {
        byte[] byteFina = null;
        Cipher cipher = Cipher.getInstance(ALGORITHM);

        SecretKeySpec keySpec = new SecretKeySpec(getKey(pwd), "AES");

        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        byteFina = cipher.doFinal(byteD);

        return byteFina;
    }

    private static byte[] getKey(String password) throws UnsupportedEncodingException {
        password = SecurityUtil.getAESKey(password);
        return password.getBytes(StandardCharsets.UTF_8);
    }
}


