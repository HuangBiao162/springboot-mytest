package normal;

import com.feidee.common.util.cryptograph.SimpleAES;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@SpringBootTest
class MytestspringbootApplicationTests {

    @Test
    public void md532bitTest() {
        System.out.println(MD5min("431126199805233233"));
        System.out.println(stringMD5("431126199805233233"));
    }

    public String MD5min(String str) {
        String result = "";
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update((str).getBytes("UTF-8"));
            byte b[] = md5.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
            return result;
        } catch (Exception e) {
            return null;
        }
    }


    public static String stringMD5(String pw) {
        try {
            MessageDigest messageDigest =MessageDigest.getInstance("MD5");
            byte[] inputByteArray = pw.getBytes();
            messageDigest.update(inputByteArray);
            byte[] resultByteArray = messageDigest.digest();
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String byteArrayToHex(byte[] byteArray) {

        char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'a','b','c','d','e','f' };
        char[] resultCharArray =new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b& 0xf];
        }
        return new String(resultCharArray);
    }

    @Test
    public void decrypt(){
        String orignPassword = "323a17c00c3db59cff2267ee9fefd84c";
        String aesPassword = "&*($HJDGH4867%&T345386754OHYOH*^(ughiuR5fu&f&$KHAOS$&^%";
        System.out.println(SimpleAES.decrypt("323a17c00c3db59cff2267ee9fefd84c", "&*($HJDGH4867%&T345386754OHYOH*^(ughiuR5fu&f&$KHAOS$&^%"));
        SecureRandom secureRandom = new SecureRandom(aesPassword.getBytes());
    }


}
