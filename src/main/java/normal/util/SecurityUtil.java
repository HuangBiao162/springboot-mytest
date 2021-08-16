package normal.util;

import java.io.UnsupportedEncodingException;

/*******************************************
 * SecurityUtil.java
 * @version 0.1
 * @date 2017-12-29
 *******************************************/
public class SecurityUtil {

    private SecurityUtil() {
    }

    /**
     * Convert byte array to hex string
     */
    public static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 3);
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
     * Convert hex string to byte array
     *
     * @param str
     * @return
     */
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
     * AES要求密钥长度为128位、192位或256位
     *
     * @param key
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getAESKey(String key) throws UnsupportedEncodingException {
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

}
