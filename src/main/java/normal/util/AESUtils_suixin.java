package normal.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;

/**
 * AES加密解密安全组件
 * <p>
 * <p>
 * Created by guanlichang@kingdee.com on 2017/8/15.
 * </p>
 **/
public abstract class AESUtils_suixin {

    /**
     * 编码方式
     */
    public static final String EN_CODE = "UTF-8";

    /**
     * 秘钥算法
     */
    public static final String KEY_ALGORITHM = "AES";

    /**
     * 加密/解密算法/工作模式/填充方式 Java 6支持PKCS5PADDING填充方式 Bouncy
     * Castle支持PKCS7Padding填充方式
     */

    public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * AES要求密钥长度为128位、192位或256位
     *
     * @param key
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getKey(String key) throws UnsupportedEncodingException {
        // 使用256位密码
        if (key.length() > 16) {
            key = key.substring(0, 16);
        } else if (key.length() < 16) {
            int count = (16 - key.length());
            for (int i = 0; i < count; i++) {
                key += "0";
            }
        }
        return key;
    }

    /**
     * 转换密钥
     *
     * @param key 二进制密钥
     * @return Key密钥
     * @throws Exception
     */
    private static Key toKey(byte[] key) throws Exception {
        // 实例化DES密钥材料
        SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM);
        return secretKey;
    }

    /**
     * 加密
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return String 加密数据
     * @throws Exception
     */
    public static String encrypt(String data, String key) throws Exception {
        byte[] encrypt = encrypt(data.getBytes(EN_CODE), getKey(key).getBytes(EN_CODE));
        return toHex(encrypt);
    }

    public static String toHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length * 3);
        for (int i = 0; i < bytes.length; i++) {
            int val = (bytes[i]) & 0xff;
            if (val < 16) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString();
    }

    /**
     * 加密
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return byte[]加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 还原密钥
        Key k = toKey(key);
        /*
         *
         * 实例化
         *
         * 使用PKCS7Padding填充方式，按如下方式实现
         *
         * Cipher.getInstance(CIPHER_ALGORITHM，"BC");
         *
         */
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

        // 初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, k);

        // 执行操作
        return cipher.doFinal(data);
    }

    /**
     * 解密
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return String 解密数据
     * @throws Exception
     */
    public static String decrypt(String data, String key) throws Exception {
        byte[] decrypt = decrypt(hexTobytes(data), getKey(key).getBytes(EN_CODE));
        return new String(decrypt, EN_CODE);
    }

    public static byte[] hexTobytes(String str) {
        int l = str.length();
        if ((l % 2) != 0) {
            throw new IllegalArgumentException("长度不是偶数!");
        }
        byte[] bytes = new byte[l / 2];
        for (int i = 0; i < l; i = i + 2) {
            String item = str.substring(i, i + 2);
            bytes[i / 2] = (byte) Integer.parseInt(item, 16);
        }
        return bytes;
    }

    /**
     * 解密
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return byte[]解密数据
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 还原密钥
        Key k = toKey(key);
        /*
         *
         * 实例化
         *
         * 使用PKCS7Padding填充方式，按如下方式实现
         *
         * Cipher.getInstance(CIPHER_ALGORITHM，"BC");
         *
         */
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        // 初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);
        // 执行操作
        return cipher.doFinal(data);
    }

    /**
     * 生成密钥＜br＞
     *
     * @return byte[]二进制密钥
     * @throws Exception
     */
    public static byte[] initKey() throws Exception {
        // 实例化
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        // AES要求密钥长度为128位、192位或256位
        kg.init(128);
        // 生成秘密密钥
        SecretKey secretKey = kg.generateKey();
        // 获得密钥的二进制编码形式
        return secretKey.getEncoded();
    }
}



