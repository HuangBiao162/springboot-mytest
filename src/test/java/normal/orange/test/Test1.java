package normal.orange.test;


import normal.orange.util.BaseRespData;
import normal.util.OrangeRSAUtil;
import normal.orange.po.BindingCardInfo;
import normal.orange.po.SelBindingCardListRespDTO;
import normal.util.EncryptUtils;
import normal.util.SignUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;

public class Test1 {


    @Test
    public void selBindingCardListTest() {

        JSONObject result = new JSONObject();

        BindingCardInfo info1 = new BindingCardInfo(1L,"2","3","4","5");
        BindingCardInfo info2 = new BindingCardInfo(11L,"22","33","44","55");

        BindingCardInfo[] infos = {info1, info2};

        JSONObject infoJson = new JSONObject();
        infoJson.put("bindingCardInfos",infos);
        //result.put("result", infoJson);
        Object o = new Object();
        JSONObject o1 = (JSONObject)JSON.toJSON(info1);
        System.out.println("========");
        System.out.println(o1);
        System.out.println("========");
        result.put("result", infos);
        System.out.println(result.toJSONString());
        System.out.println(result.get("result").toString());

        BaseRespData<SelBindingCardListRespDTO> respData = selBindingCardListRespDTO(result);
        System.out.println(respData.toString());
        System.out.println(respData.getResult());
        System.out.println("==========");
        BindingCardInfo[] bindingCardInfos = respData.getResult().getBindingCardInfos();
        for(BindingCardInfo info : bindingCardInfos){
            System.out.println(info.toString());
        }
    }

    @Test
    public void selBindingCardListTest1(){
        JSONObject json =  new JSONObject();
        json.put("userId","11111");
        json.put("bankCode","22222");
        json.put("bankName","33333");
        json.put("cardNo","44444");
        json.put("cardId","55555");

        JSONObject result =  new JSONObject();
        result.put("result",json);

        System.out.println(result.get("result").toString());

        BaseRespData<BindingCardInfo> respData = bindingCardInfo(result);

        System.out.println(respData.toString());
        System.out.println(respData.getResult());
        System.out.println(respData.getResult().getUserId());
        System.out.println(respData.getResult().getBankCode());
        System.out.println(respData.getResult().getBankName());
        System.out.println(respData.getResult().getCardNo());
        System.out.println(respData.getResult().getCardId());


    }


    public BaseRespData <BindingCardInfo> bindingCardInfo(JSONObject result) {
        return BaseRespData.buildBaseRespData(result, BindingCardInfo.class);
    }


    public BaseRespData <SelBindingCardListRespDTO> selBindingCardListRespDTO(JSONObject result) {
        return BaseRespData.buildBaseRespData(result, SelBindingCardListRespDTO.class);
    }


    @Test
    public void decEnTest(){
        String result = "sjXBsChR_1adyM6SZ6JP1S8JynKXvDQJGkOG4dGZMpbsJv8RqvyxVqpLKLEc96iWkmJBUZEgJkbJnbtmEZNGdAshgBwTpnYbBa8Ad5o2ipFMg2LdvU9wgVsUD8JfIoow0IYu-Wm0YQmB3efsdco42P3j8O9G-NP3E3WnXQGyd8aaJ3d2ybs4D6Yv8TeX5ZzPlmi46Hv86RgSpp8AYze4abn5iej9b6pfkHtfnjrJaQp0uENNo-B7fYCla11EpKbyc_PzeJDNyBXDzAoRjxrob1j63_4SnCz60rsDtFdtexnUL1ehr_enn77FCxblUzZAZ8ScX-iKdr_cg0ds-oC6NA";
        String decrypt = OrangeRSAUtil.buildRSADecryptByPublicKey(result, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsxr0tlK9XNP01dikpmChVZXNNlgq/BWG+327RwOsyxDopTUXHPq7/5Nr/aWkosEWyr8yXpIVd+TUg13csI7uNbocxW9Dno0LX/fXdyZ+qCxOG7F0o/0/YYrtuEpaEz3aSEAkYJe4hkYyApAQPhXTa3+84b6J3gkCB90CT1x8OCRKczF5WWf/PYp8WHnbWCkDymz1bjEU+Iu9bsGWG/y9UNh9uzFswy+yM7s6M+ueHKT8VHFozxRh3pTbsxhlRSTsRRFJnRI253UCayixgfmROk1VyyAtuY2TovycdWcHTGMesWmOTKJR9es+NyiDWho6QLpPBC1KLCZwDcC6O6V6bQIDAQAB");
        System.out.println(decrypt);
    }

    @Test
    public void decTest(){
        String data = "SP5MRUra4emxCWOz7mmRuP-zeRlklQr1aa9uOhi4BsMa0hbOp3clLw0QU1OtWubeb-e_hqs0hGadzCStYtNOKcl__fk6nxU6tG3e1CXB4MzseG0co0zPPrcIj8JWm6djGhHp2qOaExdKIJXPjYEKJNM4Gi16wLIBDEdKK5xNwD3DmNnBl-0CoeUIgbTtllXjrekwIdUXIoOcF-T9REpfMX6ebolOBH-rbdblSyIk7ZVZ72auljbsTZMr_mxbDSNGvoOvlagr8OV40Wd5RCJaINLz2EvANykYVZMas17HmqLurJQ7o0h1BypjID5tO5uX9yK_fWxdeK5yL0JWBZqUog";
        String publicKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhK0WpZWb50g8-LVrSI4BAbhhaMXwVI19FF0WezjSiWetfecqznMLcqTcdZ6JZOs3S7WN4mIYgHSv32KzBvVVPrQI3nza2VAT2uFa84H_7WJ1hfKJDFYW2lQ0LMvVjc_abStoOeaeZAitG297IeAsuIEEUX3AvchAUqSQOWRaD0_yOfeUWvKLAs0u2P6m6c4pOWld5gXqOwXY7BR0tpyBk1YcojaqraEZcNmIZpdgye3BDp-PizxFd0DI0IWd7BWhe4MYq1ex44GYMbbv9QTxQINnQZkfBmR-xkgNZQQGfF9tumz5ftxNOEJFVJeRi8thhloN_jLFO20pHv-1TMy0twIDAQAB";
        System.out.println(OrangeRSAUtil.buildRSADecryptByPublicKey(data, publicKey));
    }

    @Test
    public void enBank() throws Exception {
        String data = "{\"bankName\":\"中国银行\",\"bankNo\":\"BOC\",\"cardType\":\"1\",\"supported\":\"1\"}";
        String aesKey = "3q08nh0qw3r98nb3";
        String salt = "bseron0n0n034qn0";
        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALIILQwQkV/4cr7w" +
                "vIyCRMrDxNT38/gfieFwAAow4W7Ot7bVq5P+u4xZjwPFyFuHmCHqFtKXLk55yqpV" +
                "95iuSgA6tc/w2n6CsBixsYm4A3+/1qmzMgkQ8tAdMauDzTT7UULSalqGSeMRXIcJ" +
                "evoU7a1SzpK3EzxpqQlx81/pA8NVAgMBAAECgYA1utf1ky58u1vOGpOdCFfApjLK" +
                "X4bm9IXFXur0KGpw6bxyVqwwvylVI1ZROijMzvBwE9kNd4kHMCSlIcrW7orwY0qi" +
                "L8PPHDJf4l5u9/AQymjz5K9TBLfVHaEeSPSHY/xkz/bK5iYIlvNraDke0pLqDF0w" +
                "8AUrlsm+2Vs/gGRFYQJBAOubdr0Qh8E5WsOcSby/d7DH0uHIBSUg0oyM6mn99V5d" +
                "UWs9W7hw/bgkiiGiMgTzHM4UADPDBaM8d6nFg2gmcDkCQQDBcPhK8ay4uJT+m20E" +
                "trXMvcKXwAnZORwUJEHRdeKpoH0u2lR81ndLUVoCaXiOscCB8WUhyUm8tmaxfSBR" +
                "B7P9AkBqqS2+983Nbs6lQYqsNS3VTREt/6Q8o7hNwrmYlEzRlIN4fgWY4SYrh/c0" +
                "tL3aOckvjiRtSnux43z00aLksooZAkEAoDn+r7T2qBrb7vi2mEzu0SPs5t0+Sxsu" +
                "0tAlZGYM1QwGJKTbjnvmXBCKSa0rGqbJGdjlN9MOrK046pMGDZgf0QJBAKQYMk16" +
                "r/9XwKD+iwFNg/ZHRXU2g2Etb7/MUQm2pgd/U1zxzKwHTPWtmi57f067krdAdGzH" +
                "hKVFtqNiTtbltqU=";
        String wrapData = EncryptUtils.encrypt(data + salt, aesKey);
        String sign= SignUtils.sign(wrapData,privateKey);
        System.out.println(wrapData);
        System.out.println("====================================================================");
        System.out.println(sign);
    }


}
