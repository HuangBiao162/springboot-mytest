package normal.util;

import com.alibaba.fastjson.JSONObject;
import com.feidee.common.util.AESUtil;
import com.feidee.common.util.cryptograph.SimpleAES;
import org.csource.common.MyException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.*;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 加解密工具
 */
public class EncryptAndDecryptUtil {
    private Base64.Encoder encoder = Base64.getEncoder();
    private Base64.Decoder decoder = Base64.getDecoder();
    private PublicKey publicKey;

    private PrivateKey organizationPrivateKey;
    private Cipher encryptCipher;

    private Cipher decryptCipher;

    public EncryptAndDecryptUtil(String publicKeyFilePath, String organizationPrivateKeyPath) throws MyException {
        try {
            //公钥
            this.publicKey = readRSAPublicKey(publicKeyFilePath);

            encryptCipher = Cipher.getInstance("RSA");
            encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
            //私钥
            this.organizationPrivateKey = readRSAPrivateKey(organizationPrivateKeyPath);
            decryptCipher = Cipher.getInstance("RSA");
            decryptCipher.init(Cipher.DECRYPT_MODE, organizationPrivateKey);
        } catch (Exception e) {
            throw new MyException("百行反欺诈获取密钥文件异常");
        }
    }

    /**
     * 加密secret字段
     *
     * @return 加密secret字段
     */
    public String entrySecret(String secretKey) throws BadPaddingException, IllegalBlockSizeException {
        return encoder.encodeToString(encryptCipher.doFinal(secretKey.getBytes(Charset.forName("utf-8"))));
    }

    /**
     * 加密业务参数内容
     *
     * @param content 业务参数
     * @return 业务参数寂寞字符串
     */
    public String entryContent(String aesSecret, JSONObject content) throws Exception {
        byte[] bytes = content.toJSONString().getBytes(Charset.forName("utf-8"));
        return encoder.encodeToString(AESUtil.encrypt(bytes, aesSecret));
    }

    /**
     * @param secretDecry 百行返回的aes私钥
     * @return 私钥密文
     */
    public String decrySecret(String secretDecry) throws BadPaddingException, IllegalBlockSizeException {
        return new String(decryptCipher.doFinal(decoder.decode(secretDecry)), Charset.forName("utf-8"));
    }

    public String descyTest(String content) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        byte[] bytData = normal.util.baihang.Base64.base64ToByteArray(content);
        //byte[] bytData = decoder.decode(content);
        int inputLength = bytData.length;
        int offSet = 0;
        byte[] cache;
        int i = 0;
        String result = "";
        while (inputLength - offSet > 0) {
            if (inputLength - offSet > 128) {
                cache = decryptCipher.doFinal(bytData, offSet, 128);
            } else {
                cache = decryptCipher.doFinal(bytData, offSet, inputLength - offSet);
            }
            i++;
            offSet = i * 128;
            String cacheString =  new String(cache, Charset.forName("utf-8"));
            System.out.println("cacheString="+cacheString);
            result+=cacheString;
        }
        return result;
    }

    public String decryResponseData(String content, String aesSecret) {

        return SimpleAES.base64AndDecrypt(content, aesSecret);
    }

    private PublicKey readRSAPublicKey(String fileName)
            throws NoSuchAlgorithmException, IOException,
            InvalidKeySpecException {
        try (InputStreamReader isr = new InputStreamReader(new FileInputStream(fileName));
             BufferedReader br = new BufferedReader(isr)) {
            StringBuilder builder = new StringBuilder();

            for (String line = br.readLine(); line != null; line = br.readLine()) {
                builder.append(line.trim());
            }

            byte[] keyBytes = normal.util.baihang.Base64.base64ToByteArray((builder.toString()));
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            return KeyFactory.getInstance("RSA").generatePublic(spec);
        }

    }

    private PrivateKey readRSAPrivateKey(String fileName) throws NoSuchAlgorithmException, IOException,
            InvalidKeySpecException {

        StringBuilder builder = new StringBuilder();
        try (InputStreamReader isr = new InputStreamReader(new FileInputStream(fileName));
             BufferedReader br = new BufferedReader(isr)) {

            for (String line = br.readLine(); line != null; line = br.readLine()) {
                builder.append(line.trim());
            }
        }
        byte[] keyBytes = normal.util.baihang.Base64.base64ToByteArray((builder.toString()));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        return KeyFactory.getInstance("RSA").generatePrivate(spec);
    }
}
