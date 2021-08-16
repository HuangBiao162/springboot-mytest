package normal.util;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加解密
 * @author: Wang Hom
 * @Date: Created on 17:23 2018/6/14
 */
public class EncryptUtils {

    private static final String CHARSET = "UTF-8";

    private static final String ALGORITHM = "AES";

    private static final String MODE = "AES/ECB/PKCS5Padding";

    /**
     * AES加密
     * @param data
     * @param key
     * @return
     * @throws Exception 
     */
    public static String encrypt(String data, String key) throws Exception {

       // try {

            byte[] dataBytes = data.getBytes(CHARSET);

            byte[] keyBytes = key.getBytes(CHARSET);

            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, ALGORITHM);

            byte[] enCodeFormat = secretKey.getEncoded();

            SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, ALGORITHM);

            Cipher cipher = Cipher.getInstance(MODE);

            cipher.init(Cipher.ENCRYPT_MODE, seckey);

            byte[] result = cipher.doFinal(dataBytes);

            return new String(Base64.encode(result));

      //  } catch (Exception e) {

        //    throw new RuntimeException("encrypt fail!", e);

        //}

    }
    
    

    /**
     * 解密
     * @param data
     * @param key
     * @return
     */
    public static String decrypt(String data, String key) {

        try {

            byte[] dataBytes = Base64.decode(data.getBytes(CHARSET));

            byte[] keyBytes = key.getBytes(CHARSET);

            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, ALGORITHM);

            byte[] enCodeFormat = secretKey.getEncoded();

            SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, ALGORITHM);

            Cipher cipher = Cipher.getInstance(MODE);

            cipher.init(Cipher.DECRYPT_MODE, seckey);

            byte[] result = cipher.doFinal(dataBytes);

            return new String(result, CHARSET);

        } catch (Exception e) {

            throw new RuntimeException("decrypt fail!", e);

        }

    }


}
