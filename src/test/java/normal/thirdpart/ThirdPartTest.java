package normal.thirdpart;


import com.alibaba.fastjson.JSONObject;
import com.feidee.common.util.HttpUtil;
import com.feidee.common.util.cryptograph.SimpleAES;
import javafx.util.Pair;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class ThirdPartTest {

    public static final String LOCAL_URL = "http://localhost:8040/risk-third-platform";
    public static final String TEST_URL = "https://lcts6.ssjlicai.com/risk-third-platform";
    public static final String PRO_URL = "https://riskinter.cardniu.com/risk-inter";
    protected static String interUserName = "testInter"; //接口调用方账户 adminInter
    //adminInter *963.852  wujun2 12345678978945612320  caifusuishouweili 5d9bebd9a7b621d8
    protected static String interPassword = "riskTest123";
    public static final String fraudScoreUrl = "/antiFraud/talkingdata/getFraudScore";
    public static final String creditScoreUrl = "/antiFraud/talkingdata/getCreditScore";
    public static final String verifyIdCardURL = "/idCardUnityVerify/verify";
    public static final String BINGJIAN_HAOYUEURL = "/antiFraud/bingjian/getHaoYuefen";
    //借款意向
    public static final String JIEKUAN_YIXIANG="/thirdpart/baiRongMultiplateLoan";
    public static final String MOBILE_VERIFY="/personalInfo/verify";
    public static final String MOBILE_PERIOD="/personalInfo/verifyMobileUsePeriod";
    public static final String MOBILE_STATUS="/personalInfo/verifyMobileStatus";
    public static final String RONGHUI_CREDITSCORE="/antiFraud/ronghui/consumeStage/creditScore";
    public static final String ANTI_AFUSCORE="/antiFraud/zhicheng/getAfuAscord";
    public static final String ANTI_TENGXUNYUN_FRAUDSCORE="/antiFraud/score";
    public static final String ANTI_BAIHANG_FRAUDSCORE="/baihang/antiFraudInfo";

    public static final String OLD_TEST_URL = "http://tg.feidee.net/risk-inter";
    public static final String BAIRONG_PRO_URL = "https://riskinter.cardniu.com/risk-inter/thirdpart/baiRongMultiplateLoan";



    /**
     * 百行欺诈分
     */
    @Test
    public void baiHangAntiScore() {
        String result = HttpUtil.post( LOCAL_URL+"/baihang/antiFraudInfo", getParamMap(), getHeaderMap());
        //String result = HttpUtil.post( TEST_URL+"/baihang/antiFraudInfo", getParamMap(), getHeaderMap());
        System.out.println("百行欺诈分接口========\n" + result);
    }



    /**
     * 腾讯云欺诈分-知道创宇反欺诈接口
     */
    @Test
    public void tenXunYunAntiScore() {
        String result = HttpUtil.post(LOCAL_URL + ANTI_TENGXUNYUN_FRAUDSCORE, getParamMap(), getHeaderMap());
        System.out.println("腾讯云欺诈分-知道创宇反欺诈接口========\n" + result);
    }


    //=============================第一批riskBizNo返回字段是否存在检测
    /**
     * 致诚阿福分
     */
    @Test
    public void aFuScore() {
        String result = HttpUtil.post(LOCAL_URL + ANTI_AFUSCORE, getParamMap(), getHeaderMap());
        System.out.println("致诚阿福分接口========\n" + result);
    }

    /**
     * 融慧-消费分期信用分
     */
    @Test
    public void rongHuiCreditScore() {
        String result = HttpUtil.post(LOCAL_URL + RONGHUI_CREDITSCORE, getParamMap(), getHeaderMap());
        System.out.println("融慧-消费分期信用分接口========\n" + result);
    }

    /**
     * 手机在网状态
     */
    @Test
    public void mobileStatus() {
        String result = HttpUtil.post(LOCAL_URL + "/personalInfo/verifyMobileStatus", getParamMap(), getHeaderMap());
        System.out.println("手机在网状态接口========\n" + result);
    }

    /**
     * 手机在网时长
     */
    @Test
    public void mobilePeriod() {
        String result = HttpUtil.post(LOCAL_URL + "/personalInfo/verifyMobileUsePeriod", getParamMap(), getHeaderMap());
        System.out.println("手机在网时长接口========\n" + result);
    }

    /**
     * 手机实名认证
     */
    @Test
    public void mobileVerify() {
        String result = HttpUtil.post(LOCAL_URL + "/personalInfo/verifyMobile", getParamMap(), getHeaderMap());
        System.out.println("手机实名认证接口========\n" + result);
    }

    /**
     * 借款意向
     */
    @Test
    public void jieKuanYiXiangTest() {
        String result = HttpUtil.post(LOCAL_URL + JIEKUAN_YIXIANG, getParamMap(), getHeaderMap());
        System.out.println("借款意向查询接口========\n" + result);
    }

    /**
     * 冰鉴皓月分
     */
    @Test
    public void bingJianHaoYueTest() {
        //https://riskinter.cardniu.com/risk-inter/antiFraud/bingjian/getHaoYuefen  + "/antiFraud/bingjian/huiYanFen"
        String result = HttpUtil.post(" https://riskinter.cardniu.com/risk-inter/bingjian/multi" , getParamMap(), getHeaderMap());
        System.out.println("冰鉴皓月分验证接口========\n" + result);
    }

    /**
     * 身份验证二要素
     */
    @Test
    public void verifyIdCardTest() {
        String result = HttpUtil.post(LOCAL_URL+verifyIdCardURL, getParamMap(), getHeaderMap());
        System.out.println("身份验证二要素接口========\n" + result);
    }


    /**
     * 测试talkingdata欺诈分接口
     *
     * @throws Exception
     */
    @Test
    public void fraudScoreTest() {
        String result = HttpUtil.post(LOCAL_URL + fraudScoreUrl, getParamMap(), getHeaderMap());
        System.out.println("talkingdata欺诈分接口========\n" + result);
    }

    @Test
    public void photoFaceDetect() {
        String result = HttpUtil.post(LOCAL_URL + "/photoFaceDetect/detect", getParamMap(), getHeaderMap());
        System.out.println("【旷视】图片质量检测接口========\n" + result);
    }


    /**
     * 测试百融多头接口
     */
    @Test
    public void baiRongMultiplateLoanTest() {
        String result = HttpUtil.post(LOCAL_URL +"/thirdpart/baiRongMultiplateLoan", getParamMap(), getHeaderMap());
        //String result = HttpUtil.post(TEST_URL+"/thirdpart/baiRongMultiplateLoan",getParamMap(),getHeaderMap());
        //String result = HttpUtil.post(OLD_TEST_URL+"/thirdpart/baiRongMultiplateLoan",getParamMap(),getHeaderMap());
        System.out.println("百融多头接口========\n" + result);
    }


    /**
     * 银行卡验证
     */
    @Test
    public void bankVerify(){
        String result = HttpUtil.post(LOCAL_URL+"/personalInfo/verifyBankCard",getParamMap(),getHeaderMap());
        System.out.println("银行卡验证接口：\n"+result);
    }

    /**
     * 电话邦电话标签查询接口
     */
    @Test
    public void PhoneTag(){
        String result = HttpUtil.post(LOCAL_URL+"/personalInfo/queryPhoneTag",getParamMap(),getHeaderMap());
        System.out.println("电话邦电话标签查询接口：\n"+result);
    }



    /**
     * 【融慧金科】
     */
    @Test
    public void ronghuiDuoTou(){
        /**
         * 多头 - "/antiFraud/ronghui/getDuoTouNum"
         * 消费分期信用 - "/antiFraud/ronghui/consumeStage/creditScore"
         * 高危借贷人群 - "/antiFraud/ronghui/highRisk/loanCrowd"
         * 反欺诈风险名单 - "/antiFraud/ronghui/getAntiFraud"
         */
        /*String result = HttpUtil.post(TEST_URL+"/antiFraud/ronghui/getDuoTouNum",getParamMap(),getHeaderMap());
        String result = HttpUtil.post(OLD_TEST_URL+"/antiFraud/ronghui/getDuoTouNum",getParamMap(),getHeaderMap());
        System.out.println("【融慧金科】多头接口：\n"+result);*/
       /* result = HttpUtil.post(LOCAL_URL+"/antiFraud/ronghui/consumeStage/creditScore",getParamMap(),getHeaderMap());
        System.out.println("【融慧金科】消费分期接口：\n"+result);
        result = HttpUtil.post(LOCAL_URL+"/antiFraud/ronghui/highRisk/loanCrowd",getParamMap(),getHeaderMap());
        System.out.println("【融慧金科】高危信贷人群接口：\n"+result);*/
        //String result = HttpUtil.post(LOCAL_URL+"/antiFraud/ronghui/getAntiFraud",getParamMap(),getHeaderMap());
        String result = HttpUtil.post(LOCAL_URL+"/antiFraud/ronghui/getAntiFraud",getParamMap(),getHeaderMap());
        //String result = HttpUtil.post(OLD_TEST_URL+"/antiFraud/ronghui/getAntiFraud",getParamMap(),getHeaderMap());
        System.out.println("【融慧金科】反欺诈接口：\n"+result);
    }



    /**
     * 百行借贷意向分
     */
    @Test
    public void baiHangLoanWishCredit(){
        //String result = HttpUtil.post(LOCAL_URL+"/baihang/loanWishCredit",getParamMap(),getHeaderMap());
        String result = HttpUtil.post(LOCAL_URL+"/baihang/specialFocusList",getParamMap(),getHeaderMap());
        System.out.println("【百行借贷意向分】接口：\n"+result);
    }

    /**
     * 参数Map
     *
     * @return
     */
    public Map <String, String> getParamMap() {
        Map <String, String> paramMap = new HashMap <String, String>();
        JSONObject jsonObject = new JSONObject();

        /*jsonObject.put("idCard", "452123199211210718");
        jsonObject.put("custName", "韦尊严");
        jsonObject.put("loginphone", "15697553983");
        jsonObject.put("cardNo","6222032106000492687");*/

        /*jsonObject.put("idCard", "43122919980220161X");
        jsonObject.put("userName", "吴长城");
        jsonObject.put("loginphone", "18576207361");
        jsonObject.put("cardNo","6222032106000492687");*/
        /*jsonObject.put("idCard", "63212219950201567X");
        jsonObject.put("userName", "李玉");*/
        //jsonObject.put("phones",new String[]{"15289663321"});
        /*jsonObject.put("idCard", "441323198609200599");
        jsonObject.put("userName", "陈亚雄");
        jsonObject.put("loginphone", "13826738869");
        jsonObject.put("cardNo","6216602000007144155");*/
        /*jsonObject.put("idCard", "43122919980220161X");
        jsonObject.put("custName", "吴长城");
        jsonObject.put("loginphone", "18576207356");
        jsonObject.put("cardNo","6216602000007144155");*/
        //jsonObject.put("talkingDataType","00");
        //jsonObject.put("merchantNo", "KANIUC01");
        jsonObject.put("idCard", "43122919980220161X");
        jsonObject.put("userName", "郭亚伟");
        jsonObject.put("loginphone", "13433699686");
        jsonObject.put("imei","777777");
        jsonObject.put("userId","u_7wcepr");
        jsonObject.put("userType","0");
        jsonObject.put("userKey","456123");
        jsonObject.put("business","test");
        jsonObject.put("queryReason","2");
        String requestData = null; //base64 编码
        try {
            requestData = Base64.encodeBase64URLSafeString(jsonObject.toString().getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        paramMap.put("data", requestData);  //请求参数
        return paramMap;
    }

    /**
     * 得到请求头
     *
     * @return
     */
    public Map <String, String> getHeaderMap() {
        Map <String, String> headerMap = new HashMap <String, String>();
        String timestamp = String.valueOf(new Date().getTime());
        String authorization = SimpleAES.encrypt(timestamp + SimpleAES.encrypt(interUserName, interPassword) + timestamp, interPassword);
        headerMap.put("timestamp", timestamp);  //头部鉴权参数
        headerMap.put("userName", interUserName);
        headerMap.put("authorization", authorization);
        return headerMap;
    }

}
