package normal.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ThreeDESUtil {

    // 3DESECB加密,key必须是长度大于等于 3*8 = 24 位哈
    public String encryptThreeDESECB(final String src, final String key) throws Exception {
        final DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
        final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        final SecretKey securekey = keyFactory.generateSecret(dks);

        final Cipher cipher = Cipher.getInstance("DESede");
        cipher.init(Cipher.ENCRYPT_MODE, securekey);
        final byte[] b = cipher.doFinal(src.getBytes());

        final BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(b).replaceAll("\r", "").replaceAll("\n", "");

    }

    // 3DESECB解密,key必须是长度大于等于 3*8 = 24 位哈
    public String decryptThreeDESECB(final String src, final String key) throws Exception {
        // --通过base64,将字符串转成byte数组
        final BASE64Decoder decoder = new BASE64Decoder();
        final byte[] bytesrc = decoder.decodeBuffer(src);
        // --解密的key
        final DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
        final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        final SecretKey securekey = keyFactory.generateSecret(dks);

        // --Chipher对象解密
        final Cipher cipher = Cipher.getInstance("DESede");
        cipher.init(Cipher.DECRYPT_MODE, securekey);
        final byte[] retByte = cipher.doFinal(bytesrc);

        return new String(retByte);
    }

}
