package normal.other;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feidee.common.util.cryptograph.SimpleAES;
import com.robert.vesta.service.intf.IdService;
import normal.po.UserPO;
import normal.po.dto.PersonInfoDTO;
import normal.util.EncryptAndDecryptUtil;
import normal.util.RSAUtils;
import normal.util.SimpleAESUtil_lexin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StringutilsTest {

    private String publicRsaSecretPath = "/properties/baihang/baihang_anti_fraud_public_key_prod.txt";
    private String organizationPrivateRsaSecretPath = "/properties/baihang/baihang_anti_fraud_organization_private_key_prod.txt";
    private Base64.Encoder encoder = Base64.getEncoder();
    private Base64.Decoder decoder = Base64.getDecoder();
    private EncryptAndDecryptUtil encryptAndDecryptUtil;
    private final String testFinal = "Test_Final";

    @Autowired
    private IdService idService;

    /**
     * 百行AES密钥
     */
    private static final String AES_SECRET = "baihangl53SBpmxu";

    @Test
    public void encryAndDecryTest() throws Exception {
        EncryptAndDecryptUtil encryptAndDecryptUtil = new EncryptAndDecryptUtil(this.getClass().getResource(publicRsaSecretPath).getPath(),
                this.getClass().getResource(organizationPrivateRsaSecretPath).getPath());

        String secretKey = "G0fpDUaYXv8SRU+H4ZbApHj5rvGuBlwXWrrm86V8bivr+0CdtCVP6ESyF6A9633P1UUr2LHRLa3i+1EjNMHM0vexGbd7hhMsLr0LnJ/ACIG/DTGvHCgDNCDcP3TAz85uVLOltRuD9Qy+5ehFkdxikfLu+Jy/IAsXpYD8PwP/Nhw=";
        String content = "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";
        //System.out.println("secretKey.length = "+ secretKey.getBytes().length);
        //System.out.println("content.length = "+ decoder.decode(content).length);
        String aesSecret = encryptAndDecryptUtil.decrySecret(secretKey);
        System.out.println("解密后：secretKey = " + aesSecret);

        //String aesContent = encryptAndDecryptUtil.decryResponseData(content,aesSecret);
        //System.out.println("解密后：content = " + aesContent);
    }


    @Test
    public void myEnDeTest1() throws Exception {
        JSONObject jsonObject = new JSONObject();
        //jsonObject.put("p1","djasklldsjalkdsajdsajdaksldioasjdqiowejqwioejiquieqwuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuudddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddda12d1as32d1as3d2a1da3s2d1as32d1as32d1as23d15xc4zx6c4zx65cz1czx23cz1c32z1c32c1z321cas23d1a5sd4qw56e1qw23e1qeq312cxz3c1z32c1zx32c1zx32c1zx231ca65d4sa56d4asd5sd4a6dsa4das65das4444445555555555555555555555555555555555555555555djasklldsjalkdsajdsajdaksldioasjdqiowejqwioejiquieqwuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuudddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddda12d1as32d1as3d2a1da3s2d1as32d1as32d1as23d15xc4zx6c4zx65cz1czx23cz1c32z1c32c1z321cas23d1a5sd4qw56e1qw23e1qeq312cxz3c1z32c1zx32c1zx32c1zx231ca65d4sa56d4asd5sd4a6dsa4das65das4444445555555555555555555555555555555555555555555djasklldsjalkdsajdsajdaksldioasjdqiowejqwioejiquieqwuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuudddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddda12d1as32d1as3d2a1da3s2d1as32d1as32d1as23d15xc4zx6c4zx65cz1czx23cz1c32z1c32c1z321cas23d1a5sd4qw56e1qw23e1qeq312cxz3c1z32c1zx32c1zx32c1zx231ca65d4sa56d4asd5sd4a6dsa4das65das4444445555555555555555555555555555555555555555555djasklldsjalkdsajdsajdaksldioasjdqiowejqwioejiquieqwuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuudddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddda12d1as32d1as3d2a1da3s2d1as32d1as32d1as23d15xc4zx6c4zx65cz1czx23cz1c32z1c32c1z321cas23d1a5sd4qw56e1qw23e1qeq312cxz3c1z32c1zx32c1zx32c1zx231ca65d4sa56d4asd5sd4a6dsa4das65das4444445555555555555555555555555555555555555555555djasklldsjalkdsajdsajdaksldioasjdqiowejqwioejiquieqwuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuudddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddda12d1as32d1as3d2a1da3s2d1as32d1as32d1as23d15xc4zx6c4zx65cz1czx23cz1c32z1c32c1z321cas23d1a5sd4qw56e1qw23e1qeq312cxz3c1z32c1zx32c1zx32c1zx231ca65d4sa56d4asd5sd4a6dsa4das65das4444445555555555555555555555555555555555555555555djasklldsjalkdsajdsajdaksldioasjdqiowejqwioejiquieqwuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuudddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddda12d1as32d1as3d2a1da3s2d1as32d1as32d1as23d15xc4zx6c4zx65cz1czx23cz1c32z1c32c1z321cas23d1a5sd4qw56e1qw23e1qeq312cxz3c1z32c1zx32c1zx32c1zx231ca65d4sa56d4asd5sd4a6dsa4das65das4444445555555555555555555555555555555555555555555djasklldsjalkdsajdsajdaksldioasjdqiowejqwioejiquieqwuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuudddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddda12d1as32d1as3d2a1da3s2d1as32d1as32d1as23d15xc4zx6c4zx65cz1czx23cz1c32z1c32c1z321cas23d1a5sd4qw56e1qw23e1qeq312cxz3c1z32c1zx32c1zx32c1zx231ca65d4sa56d4asd5sd4a6dsa4das65das4444445555555555555555555555555555555555555555555djasklldsjalkdsajdsajdaksldioasjdqiowejqwioejiquieqwuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuudddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddda12d1as32d1as3d2a1da3s2d1as32d1as32d1as23d15xc4zx6c4zx65cz1czx23cz1c32z1c32c1z321cas23d1a5sd4qw56e1qw23e1qeq312cxz3c1z32c1zx32c1zx32c1zx231ca65d4sa56d4asd5sd4a6dsa4das65das4444445555555555555555555555555555555555555555555djasklldsjalkdsajdsajdaksldioasjdqiowejqwioejiquieqwuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuudddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddda12d1as32d1as3d2a1da3s2d1as32d1as32d1as23d15xc4zx6c4zx65cz1czx23cz1c32z1c32c1z321cas23d1a5sd4qw56e1qw23e1qeq312cxz3c1z32c1zx32c1zx32c1zx231ca65d4sa56d4asd5sd4a6dsa4das65das4444445555555555555555555555555555555555555555555");
        //jsonObject.put("p2","djasklldsjalkdsajdsajdaksldioasjdqiowejqwioejiquieqwuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuudddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddda12d1as32d1as3d2a1da3s2d1as32d1as32d1as23d15xc4zx6c4zx65cz1czx23cz1c32z1c32c1z321cas23d1a5sd4qw56e1qw23e1qeq312cxz3c1z32c1zx32c1zx32c1zx231ca65d4sa56d4asd5sd4a6dsa4das65das4444445555555555555555555555555555555555555555555djasklldsjalkdsajdsajdaksldioasjdqiowejqwioejiquieqwuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuudddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddda12d1as32d1as3d2a1da3s2d1as32d1as32d1as23d15xc4zx6c4zx65cz1czx23cz1c32z1c32c1z321cas23d1a5sd4qw56e1qw23e1qeq312cxz3c1z32c1zx32c1zx32c1zx231ca65d4sa56d4asd5sd4a6dsa4das65das4444445555555555555555555555555555555555555555555djasklldsjalkdsajdsajdaksldioasjdqiowejqwioejiquieqwuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuudddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddda12d1as32d1as3d2a1da3s2d1as32d1as32d1as23d15xc4zx6c4zx65cz1czx23cz1c32z1c32c1z321cas23d1a5sd4qw56e1qw23e1qeq312cxz3c1z32c1zx32c1zx32c1zx231ca65d4sa56d4asd5sd4a6dsa4das65das4444445555555555555555555555555555555555555555555djasklldsjalkdsajdsajdaksldioasjdqiowejqwioejiquieqwuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuudddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddda12d1as32d1as3d2a1da3s2d1as32d1as32d1as23d15xc4zx6c4zx65cz1czx23cz1c32z1c32c1z321cas23d1a5sd4qw56e1qw23e1qeq312cxz3c1z32c1zx32c1zx32c1zx231ca65d4sa56d4asd5sd4a6dsa4das65das4444445555555555555555555555555555555555555555555djasklldsjalkdsajdsajdaksldioasjdqiowejqwioejiquieqwuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuudddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddda12d1as32d1as3d2a1da3s2d1as32d1as32d1as23d15xc4zx6c4zx65cz1czx23cz1c32z1c32c1z321cas23d1a5sd4qw56e1qw23e1qeq312cxz3c1z32c1zx32c1zx32c1zx231ca65d4sa56d4asd5sd4a6dsa4das65das4444445555555555555555555555555555555555555555555djasklldsjalkdsajdsajdaksldioasjdqiowejqwioejiquieqwuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuudddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddda12d1as32d1as3d2a1da3s2d1as32d1as32d1as23d15xc4zx6c4zx65cz1czx23cz1c32z1c32c1z321cas23d1a5sd4qw56e1qw23e1qeq312cxz3c1z32c1zx32c1zx32c1zx231ca65d4sa56d4asd5sd4a6dsa4das65das4444445555555555555555555555555555555555555555555djasklldsjalkdsajdsajdaksldioasjdqiowejqwioejiquieqwuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuudddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddda12d1as32d1as3d2a1da3s2d1as32d1as32d1as23d15xc4zx6c4zx65cz1czx23cz1c32z1c32c1z321cas23d1a5sd4qw56e1qw23e1qeq312cxz3c1z32c1zx32c1zx32c1zx231ca65d4sa56d4asd5sd4a6dsa4das65das4444445555555555555555555555555555555555555555555djasklldsjalkdsajdsajdaksldioasjdqiowejqwioejiquieqwuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuudddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddda12d1as32d1as3d2a1da3s2d1as32d1as32d1as23d15xc4zx6c4zx65cz1czx23cz1c32z1c32c1z321cas23d1a5sd4qw56e1qw23e1qeq312cxz3c1z32c1zx32c1zx32c1zx231ca65d4sa56d4asd5sd4a6dsa4das65das4444445555555555555555555555555555555555555555555djasklldsjalkdsajdsajdaksldioasjdqiowejqwioejiquieqwuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuudddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddda12d1as32d1as3d2a1da3s2d1as32d1as32d1as23d15xc4zx6c4zx65cz1czx23cz1c32z1c32c1z321cas23d1a5sd4qw56e1qw23e1qeq312cxz3c1z32c1zx32c1zx32c1zx231ca65d4sa56d4asd5sd4a6dsa4das65das4444445555555555555555555555555555555555555555555");
        //jsonObject.put("p3","djasklldsjalkdsajdsajdaksldioasjdqiowejqwioejiquieqwuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuudddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddda12d1as32d1as3d2a1da3s2d1as32d1as32d1as23d15xc4zx6c4zx65cz1czx23cz1c32z1c32c1z321cas23d1a5sd4qw56e1qw23e1qeq312cxz3c1z32c1zx32c1zx32c1zx231ca65d4sa56d4asd5sd4a6dsa4das65das4444445555555555555555555555555555555555555555555djasklldsjalkdsajdsajdaksldioasjdqiowejqwioejiquieqwuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuudddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddda12d1as32d1as3d2a1da3s2d1as32d1as32d1as23d15xc4zx6c4zx65cz1czx23cz1c32z1c32c1z321cas23d1a5sd4qw56e1qw23e1qeq312cxz3c1z32c1zx32c1zx32c1zx231ca65d4sa56d4asd5sd4a6dsa4das65das4444445555555555555555555555555555555555555555555djasklldsjalkdsajdsajdaksldioasjdqiowejqwioejiquieqwuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuudddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddda12d1as32d1as3d2a1da3s2d1as32d1as32d1as23d15xc4zx6c4zx65cz1czx23cz1c32z1c32c1z321cas23d1a5sd4qw56e1qw23e1qeq312cxz3c1z32c1zx32c1zx32c1zx231ca65d4sa56d4asd5sd4a6dsa4das65das4444445555555555555555555555555555555555555555555djasklldsjalkdsajdsajdaksldioasjdqiowejqwioejiquieqwuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuudddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddda12d1as32d1as3d2a1da3s2d1as32d1as32d1as23d15xc4zx6c4zx65cz1czx23cz1c32z1c32c1z321cas23d1a5sd4qw56e1qw23e1qeq312cxz3c1z32c1zx32c1zx32c1zx231ca65d4sa56d4asd5sd4a6dsa4das65das4444445555555555555555555555555555555555555555555djasklldsjalkdsajdsajdaksldioasjdqiowejqwioejiquieqwuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuudddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddda12d1as32d1as3d2a1da3s2d1as32d1as32d1as23d15xc4zx6c4zx65cz1czx23cz1c32z1c32c1z321cas23d1a5sd4qw56e1qw23e1qeq312cxz3c1z32c1zx32c1zx32c1zx231ca65d4sa56d4asd5sd4a6dsa4das65das4444445555555555555555555555555555555555555555555djasklldsjalkdsajdsajdaksldioasjdqiowejqwioejiquieqwuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuudddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddda12d1as32d1as3d2a1da3s2d1as32d1as32d1as23d15xc4zx6c4zx65cz1czx23cz1c32z1c32c1z321cas23d1a5sd4qw56e1qw23e1qeq312cxz3c1z32c1zx32c1zx32c1zx231ca65d4sa56d4asd5sd4a6dsa4das65das4444445555555555555555555555555555555555555555555djasklldsjalkdsajdsajdaksldioasjdqiowejqwioejiquieqwuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuudddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddda12d1as32d1as3d2a1da3s2d1as32d1as32d1as23d15xc4zx6c4zx65cz1czx23cz1c32z1c32c1z321cas23d1a5sd4qw56e1qw23e1qeq312cxz3c1z32c1zx32c1zx32c1zx231ca65d4sa56d4asd5sd4a6dsa4das65das4444445555555555555555555555555555555555555555555djasklldsjalkdsajdsajdaksldioasjdqiowejqwioejiquieqwuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuudddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddda12d1as32d1as3d2a1da3s2d1as32d1as32d1as23d15xc4zx6c4zx65cz1czx23cz1c32z1c32c1z321cas23d1a5sd4qw56e1qw23e1qeq312cxz3c1z32c1zx32c1zx32c1zx231ca65d4sa56d4asd5sd4a6dsa4das65das4444445555555555555555555555555555555555555555555djasklldsjalkdsajdsajdaksldioasjdqiowejqwioejiquieqwuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuudddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddda12d1as32d1as3d2a1da3s2d1as32d1as32d1as23d15xc4zx6c4zx65cz1czx23cz1c32z1c32c1z321cas23d1a5sd4qw56e1qw23e1qeq312cxz3c1z32c1zx32c1zx32c1zx231ca65d4sa56d4asd5sd4a6dsa4das65das4444445555555555555555555555555555555555555555555");
        jsonObject.put("name", "张三");
        jsonObject.put("age", "18");

        EncryptAndDecryptUtil encryptAndDecryptUtil = new EncryptAndDecryptUtil(this.getClass().getResource(publicRsaSecretPath).getPath(),
                this.getClass().getResource(organizationPrivateRsaSecretPath).getPath());

        String s = encryptAndDecryptUtil.entrySecret(JSON.toJSONString(jsonObject));
        System.out.println(s);

        String s1 = encryptAndDecryptUtil.descyTest(s);
        System.out.println(s1);
    }


    @Test
    public void novaRsaTest() throws GeneralSecurityException {
        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJNm0jjSJVJ21+S/aXz+Vgk0PbPvwVCm9AFHtm/b/NdMDUCXq3X+V/9HN7+mfJqlF7M+4sTHc463awxZ3CNKpRTR+NxAEYNTnWpba4KBbY2QsOl3kRjVKJfC6DgPUqiA6lFWHa0f7CrW0Vw0e56inr5F3DiFXjWutDv4s0U8jKfTAgMBAAECgYBNE/FN4SW/D4IpjaY9xEl9GqJBW1f9MrBZkwRlTeXTN2OeqTDxzIR7auROBWMXAsl346R1Pc7BjEwf80VX9wqHI0jMNrtdzahmzGoyjOActK0S1VdVLVZEQlotVA6+ReqDMhMYkcLK5pvUOhKU/377d24QnCUFdTxL51cnYkMnAQJBAMplBN1ppuOBh3pnKwWPyQmrDk/qJUDzcYCeYkGE4D946HvZtkDVSScI0vVriSzwBoR0XN6uu2vJBFNN4VqO0xMCQQC6cRvzCm6x1EocCcXS8pjw59R5OWvQAJucBBEhLzg6NMJTMGrzQ22iw9aybMPEJgII9S1IXvAjM+C949p3pLBBAkBjhNKkkIvIOGvGay5DWNUwlNS/xNE2o72+0aJctxqQ+0HNjFrKfFWxBOy+UncsCoYZ4SG9OUvdDwW6dIQHxEzTAkA4m/1rh0BjGF1ENCdvzJ9pjkqyYB//MSAOeCGnXMoKylgyoCHpKRER+bqnxGSQmyXFCISuU71AQxE7NwLvjv7BAkEAknRhYa+apu81lqHLy964vlwUV+OOPvWPFQp86klDwI/0Uc7HHo9aotj0MqxRYMZ7KoHrj/S/Gmq1qwyQ29z06Q==";
        String secretKey = "G0fpDUaYXv8SRU+H4ZbApHj5rvGuBlwXWrrm86V8bivr+0CdtCVP6ESyF6A9633P1UUr2LHRLa3i+1EjNMHM0vexGbd7hhMsLr0LnJ/ACIG/DTGvHCgDNCDcP3TAz85uVLOltRuD9Qy+5ehFkdxikfLu+Jy/IAsXpYD8PwP/Nhw=";
        String decrypt = RSAUtils.decrypt(secretKey, privateKey);
        System.out.println(decrypt);
    }

    @Test
    public void test1() {
        UserPO u1 = new UserPO();
        u1.setName("name1");
        u1.setAge(11);

        UserPO u2 = new UserPO();
        u2.setName("name1");
        u2.setAge(11);

        String serviceCode = "talkingdata.fraud";
        String u1Key = getHaskKey(u1, serviceCode);
        String u2Key = getHaskKey(u2, serviceCode);
        System.out.println("u1Key:" + u1Key);
        System.out.println("u2Key:" + u2Key);
        System.out.println(u1Key.equals(u2Key));

    }

    public String getHaskKey(Object... objects) {
        Long count = 0L;
        for (int i = 0; i < objects.length; i++) {
            count += objects[i].hashCode();
        }
        return String.valueOf(count);
    }

    @Test
    public void getVestId() {
        String s = SimpleAES.encryptAndBase64("6222032106000492687", "&*($eIa61M$(&^%M");
        System.out.println(s);
    }

    @Test
    public void booTest() {
        String req = "{\"version\": \"01\", \"cmdId\": \"DCAccessChain\", \"merCustId\": \"FuDai\", \"merPriv\": \"\\u6a21\\u62df\\u63a5\\u53e3\\u8fdb\\u4ef6\", \"traceNo\": \"2021012810173030\", \"bgRetUrl\": \"http://10.201.5.156:10018/nova/callback\", \"cardNum\": \"35042319811125631X\", \"name\": \"\\u5218\\u6d25\\u98d2\", \"phone\": \"13902431844\", \"suid\": \"u_qukxm@kd.ssj\", \"applicationInfo\": {\"partner\": \"2\", \"activeId\": \"2019110615490258\", \"appDateTime\": \"2020-10-19 18:08:58\", \"source\": \"001\", \"ssjId\": \"123422\"}, \"deviceInfo\": {\"ip\": \"172.168.1.1\", \"deviceOsType\": \"2\", \"imei\": \"23134323\", \"idfa\": \"43fddfs4\", \"udid\": \"androidId-fa837019e632e0dd0\", \"gpsInfo\": \"{\\\"altitude\\\":5e-324,\\\"latitude\\\":22.540764,\\\"longitude\\\":113.961516,\\\"city\\\":\\\"\\u5e7f\\u5dde\\u5e02\\\",\\\"district\\\":\\\"\\u5357\\u5c71\\u533a\\\",\\\"province\\\":\\\"\\u5e7f\\u4e1c\\u7701\\\",\\\"street\\\":\\\"\\u79d1\\u6280\\u5357\\u5341\\u4e8c\\u8def\\\",\\\"streetNumber\\\":\\\"5\\u53f7\\\",\\\"cityCode\\\":\\\"340\\\"}\", \"wifiMac\": \"1C:C3:EB:AA:B0:11\", \"wifiBssid\": \"24:79:2a:8a:94:18\", \"phoneVendor\": \"NOVA\", \"phoneModel\": \"V20\"}}";
        String replace = req.replace("\\", "");
        System.out.println(replace);
    }

    @Test
    public void hashCodeTest() {
        PersonInfoDTO personInfoDTO = new PersonInfoDTO();
        personInfoDTO.setUserName("黄");
        personInfoDTO.setLoginphone("155");
        Random random = new Random(100);
        int num = random.nextInt();
        personInfoDTO.setQueryId(num + "");
        int hashCode1 = personInfoDTO.hashCode();
        num = random.nextInt();
        personInfoDTO.setQueryId(num + "");
        int hashCode2 = personInfoDTO.hashCode();
        System.out.println("hashCode1=" + hashCode1);
        System.out.println("hashCode2=" + hashCode2);
        System.out.println(hashCode1 == hashCode2);
    }

    public static void main(String[] args) {
        Random rd = new Random();
        int index = rd.nextInt(2);
        System.out.println(index);

        Integer a = 1;
        Integer b = 1;
        int c = 1;
        int d = 1;
        System.out.println(a == b);
        System.out.println(c == d);

        List <Integer> list = new ArrayList();
        list.add(2);
        list.add(3);
        list.add(1);
        list.add(5);
        list.add(4);
        System.out.println(list);
        Collections.sort(list, new Comparator <Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        System.out.println(list);

        TreeMap <Integer, String> treeMap = new TreeMap(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return (int) o2 - (int) o1;
            }
        });
        treeMap.put(1, "1");
        treeMap.put(3, "3");
        treeMap.put(2, "2");
        for (Map.Entry <Integer, String> map : treeMap.entrySet()) {
            System.out.println(map.getKey() + " " + map.getValue());
        }
    }

    @Test
    public void formatDate() {
        Date date = new Date();

        long timestamp = date.getTime();
        System.out.println("timestamp=" + timestamp);
        Date date1 = new Date(timestamp);
        System.out.println("date=" + date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY/MM/dd");
        String format = simpleDateFormat.format(date);
        System.out.println("formatTime=" + format);

    }

    @Test
    public void testPair() {
        System.out.println("号加多少(单位：元，丢弃按'y')：");
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        System.out.println("输入：" + s);
    }


    @Test
    public void testTreeList() {
        List <Integer> list = new ArrayList <>();
        list.add(1);
        list.add(3);
        list.add(2);
        System.out.println(list);
        //对牌进行字面大小排序
        Collections.sort(list, (o1, o2) -> {
            return o1 - o2;
        });
        System.out.println(list);

        Integer remove = list.remove(0);
        System.out.println(remove);
        System.out.println(list.toString());

    }

    @Test
    public void testStringBuffer() {
        String date = "20210407";
        StringBuilder stringBuilder = new StringBuilder(date);
        StringBuilder insert = stringBuilder.insert(4, "/").insert(7, "/");
        System.out.println(insert.toString());

        Integer money = 100516;
        BigDecimal amount = new BigDecimal(money);
        //对分进行保留两位小数
        Number num = 3;
        System.out.println(num);
        System.out.println(amount.divide(new BigDecimal(100)).toString());
        System.out.println(amount.divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).toString());

    }

    @Test
    public void testJSON() {
        UserPO userPO = new UserPO();
        userPO.setName("name");
        userPO.setAge(18);
        UserPO userPO1 = new UserPO();
        userPO1.setName("name1");
        userPO1.setAge(1);
        System.out.println(userPO);
        System.out.println(userPO1);
        UserPO[] userPOS = {userPO, userPO1};
        for (UserPO user : userPOS) {
            System.out.println(user);
        }
    }

    @Test
    public void refrectTest() throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        /*UserPO userPO = new UserPO();
        Class <? extends UserPO> userPOClass = userPO.getClass();
        Field name = userPOClass.getDeclaredField("name");
        name.setAccessible(true);
        name.set(userPO,"黄彪");
        System.out.println(userPO.getName());*/

        UserPO userPO = BeanUtils.instantiateClass(UserPO.class);
        String isPass = "true";
        userPO.setAge(Boolean.parseBoolean(isPass) ? AccessResultTypeEnum.PERMIT.getCode() : AccessResultTypeEnum.REJECT.getCode());
        System.out.println(userPO.toString());

        String decrypt = SimpleAESUtil_lexin.decrypt("331b4b44bdc525ade50ebf75423e4e8050834c546c78acaefc55ee592ed264e2");
        System.out.println(decrypt);

    }

    @Test
    public void dataUtilsTest() {


        String chars = "{\\\"head\\\":{\\\"clientId\\\":\\\"1\\\",\\\"appUDID\\\":\\\"7f90d96476404b80ec16a2cf6940fbbe5b0da9533d64e9f9be78382340957ef0be64f5b95eab05b263b769299ead87c8\\\",\\\"appVersion\\\":\\\"\\\",\\\"channelId\\\":\\\"\\\",\\\"innerMedia\\\":\\\"\\\",\\\"outerMedia\\\":\\\"\\\",\\\"subClientId\\\":\\\"0\\\",\\\"systemName\\\":\\\"ios\\\",\\\"systemVersion\\\":\\\"13.1.2\\\",\\\"origin\\\":\\\"\\\",\\\"agent\\\":\\\"Mozilla/5.0 (iPhone; CPU iPhone OS 13_1_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148 MyMoney_HotTopics_iPhone/4.0 MyMoney/12.88.0\\\",\\\"os\\\":\\\"IOS\\\",\\\"userName\\\":\\\"\\\",\\\"firstLevelDomain\\\":\\\"ssjlicai.com\\\",\\\"token\\\":\\\"00743e39-fdcf-4c35-bc55-2f06044b4a43\\\",\\\"productVersion\\\":\\\"\\\",\\\"partner\\\":null,\\\"clientIp\\\":\\\"183.238.182.84\\\",\\\"tokenType\\\":\\\"Bearer\\\",\\\"uid\\\":1100088206,\\\"ssjid\\\":254069212,\\\"userInfo\\\":{\\\"uid\\\":1100088206,\\\"ssjId\\\":254069212,\\\"ssjUserName\\\":\\\"u_7j9jew@kd.ssj\\\",\\\"ssjNickName\\\":\\\"18565687SLP\\\",\\\"email\\\":\\\"yesiwillbethere@126.com\\\",\\\"icon\\\":\\\"http://cloud.feidee.cn/group1/M00/E9/92/CskDIF9PcnSETdu-AAAAAJQ6mZ4892.jpg\\\",\\\"phone\\\":\\\"18565687532\\\",\\\"registerTime\\\":1522236995000,\\\"registerFrom\\\":\\\"MyLoads\\\",\\\"registerType\\\":\\\"mobile\\\",\\\"isVip\\\":false,\\\"bindPhone\\\":true}},\\\"body\\\":{\\\"contactInfos\\\":[{\\\"seqNo\\\":1,\\\"linkTel\\\":\\\"13143529639\\\",\\\"linkRelationCode\\\":\\\"3\\\",\\\"linkName\\\":\\\"统统\\\",\\\"spouseCertNo\\\":\\\"123456199302081234\\\"},{\\\"seqNo\\\":2,\\\"linkTel\\\":\\\"13143529633\\\",\\\"linkRelationCode\\\":\\\"4\\\",\\\"linkName\\\":\\\"模样\\\"}],\\\"personalInfo\\\":{\\\"marriageCode\\\":\\\"2\\\",\\\"liveAddress\\\":\\\"同鳄鱼\\\",\\\"email\\\":\\\"888888@sina.com\\\",\\\"educationCode\\\":\\\"3\\\",\\\"liveCityCode\\\":\\\"120100\\\",\\\"liveProvinceCode\\\":\\\"120000\\\",\\\"liveAreaCode\\\":\\\"120101\\\"},\\\"jobInfo\\\":{\\\"workIncome\\\":\\\"3\\\",\\\"workYears\\\":\\\"3\\\",\\\"companyName\\\":\\\"可否科技\\\",\\\"companyTel\\\":\\\"13510559988\\\",\\\"companyIndustryCode\\\":\\\"2\\\",\\\"companyAddress\\\":\\\"我现在一\\\",\\\"companyDutyCode\\\":\\\"4\\\",\\\"companyCityCode\\\":\\\"110100\\\",\\\"companyProvinceCode\\\":\\\"110000\\\",\\\"companyAreaCode\\\":\\\"110101\\\"},\\\"systemType\\\":2,\\\"productId\\\":\\\"L0003\\\",\\\"channelType\\\":1}}";
        System.out.println(chars.replace("\\", ""));
    }

    @Test
    public void t2() {
        String str1 = "通话";
        String str2 = "重地";
        System.out.println(String.format("str1：%d | str2：%d", str1.hashCode(), str2.hashCode()));

        UserPO zs = UserPO.builder().age(1).name("张三").girlFriends(new String[]{"1", "2"}).build();
        UserPO ls = UserPO.builder().age(2).name("李四").build();

        String s = JSONObject.toJSONString(zs);
        System.out.println(s);

    }

    @Test
    public void t3() {
        UserPO po = new UserPO("11", 11, new String[]{"1"});
        UserPO po1 = new UserPO("11", 11, new String[]{"1"});
        UserPO zs = UserPO.builder().name("zs").build();
        UserPO po2 = po;
        System.out.println(po == po2);
        System.out.println(po.equals(po1));
        System.out.println(po1.hashCode());
        System.out.println(po2.hashCode());
        String aa = "abcdefg";
        String substring = aa.substring(3);
        System.out.println(substring);

        String a =null;
        String b= a;
        if(a == null){
            a="hi";
        }
        System.out.println(b);

        System.out.println("JSONString"+JSON.toJSONString(zs));

    }

}
