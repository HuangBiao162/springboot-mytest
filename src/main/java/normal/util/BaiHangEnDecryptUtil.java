package normal.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class BaiHangEnDecryptUtil {

    /**
     * 解密服务器返回的数据
     *
     * @param response 服务器响应内容
     * @param key      加解密的key
     * @return 返回结果
     * @throws Exception 异常
     */
    public static String decryptResponse(String response, String key) throws Exception {

        DESedeKeySpec dks = new DESedeKeySpec(new BASE64Decoder().decodeBuffer(key));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        SecretKey secretKey = keyFactory.generateSecret(dks);

        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] b = cipher.doFinal(new BASE64Decoder().decodeBuffer(response));
        return new String(b, StandardCharsets.UTF_8);
    }

    /**
     * 加密请求参数
     *
     * @param secretKey 秘钥
     * @param paramsString 参数String
     * @return 加密后的请求参数
     * @throws Exception 异常
     */
    public static String encryptRequestParams(String secretKey, String paramsString) throws Exception {
        byte[] keyBytes = new BASE64Decoder().decodeBuffer(secretKey);

        DESedeKeySpec dks = new DESedeKeySpec(keyBytes);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        SecretKey key = keyFactory.generateSecret(dks);

        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] b = cipher.doFinal(paramsString.getBytes(StandardCharsets.UTF_8));
        return new BASE64Encoder().encode(b);
    }

    /**
     * 生成签名
     *
     * @param secretId secretId
     * @param secretKey secretKey
     * @param requestRefId requestRefId
     * @return 结果
     * @throws Exception 异常
     */
    public static String getSignature(String secretId, String secretKey, String requestRefId) throws Exception {
        //请求号前缀标识符号可根据各机构自定义设置("机构简称")
        String beforeSign = "requestRefId=" + requestRefId + "&secretId=" + secretId;
        byte[] keyBytes = new BASE64Decoder().decodeBuffer(secretKey);
        SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(beforeSign.getBytes());
        return new BASE64Encoder().encode(rawHmac);
    }

}
