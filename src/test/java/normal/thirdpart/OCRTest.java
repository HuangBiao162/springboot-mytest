package normal.thirdpart;

import com.alibaba.fastjson.JSON;
import normal.po.BO.IdCardOCRParamBO;
import normal.util.SimpleAESUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;

import static java.nio.charset.StandardCharsets.UTF_8;



@Slf4j
public class OCRTest {

    @Resource
    private RestTemplate restTemplate;

    @Test
    public void test1(){
        IdCardOCRParamBO idCardOCRParamBO = new IdCardOCRParamBO();
        idCardOCRParamBO.setIdCard("441323198609200599");
        idCardOCRParamBO.setUrl("https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E5%9B%BE%E7%89%87&step_word=&hs=2&pn=0&spn=0&di=5610&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=3363295869%2C2467511306&os=892371676%2C71334739&simid=4203536407%2C592943110&adpicid=0&lpn=0&ln=1615&fr=&fmq=1611559887509_R&fm=&ic=undefined&s=undefined&hd=undefined&latest=undefined&copyright=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=https%3A%2F%2Fgimg2.baidu.com%2Fimage_search%2Fsrc%3Dhttp%3A%2F%2Fa0.att.hudong.com%2F30%2F29%2F01300000201438121627296084016.jpg%26refer%3Dhttp%3A%2F%2Fa0.att.hudong.com%26app%3D2002%26size%3Df9999%2C10000%26q%3Da80%26n%3D0%26g%3D0n%26fmt%3Djpeg%3Fsec%3D1614151889%26t%3D7d1efb8d404740413e6e14118f977c10&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bfhyvg8_z%26e3Bv54AzdH3F4AzdH3Fetjo_z%26e3Brir%3Fwt1%3Dmb9l9&gsm=1&rpstart=0&rpnum=0&islist=&querylist=&force=undefined");
        String s = doHttp(idCardOCRParamBO);
        System.out.println(s);
    }


    public String doHttp(IdCardOCRParamBO paramBO) {
        HttpHeaders headers = buildKaNiuHeaders();
        HttpEntity<ByteArrayResource> filePart = buildFilePart(paramBO.getUrl());
        HttpEntity<String> jsonPart = buildJsonPart(paramBO);

        MultiValueMap<String, Object> total = new LinkedMultiValueMap<>();
        total.add("data", jsonPart);
        total.add("file", filePart);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(total, headers);

        String ocrUrl = "http://localhost:8040/risk-third-platform/photoFaceDetect/detect";
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(ocrUrl, httpEntity, String.class);
        return getResponse(ocrUrl, responseEntity);
    }

    private HttpHeaders buildKaNiuHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        String timestamp = String.valueOf(Instant.now().toEpochMilli());
        String interUserName = "adminInter";
        String interPassword = "123456";
        String authorization;
        try {
            authorization = SimpleAESUtil.encrypt(timestamp + SimpleAESUtil.encrypt(interUserName, interPassword) + timestamp, interPassword);
        } catch (Exception e) {
            throw new RuntimeException("卡牛请求头加密出错！", e);
        }

        httpHeaders.add("timestamp", timestamp);
        httpHeaders.add("userName", interUserName);
        httpHeaders.add("authorization", authorization);
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE);
        return httpHeaders;
    }

    private HttpEntity<ByteArrayResource> buildFilePart(String imageUrl) {
        byte[] bs;
        ByteArrayResource byteArrayResource = null;
        try {
            URL url = new URL(imageUrl);
            bs = IOUtils.toByteArray(url);
            byteArrayResource = new ByteArrayResource(bs) {
                @Override
                public String getFilename() {
                    return url.getFile();
                }
            };
        } catch (IOException e) {
            log.error("身份证地址获取图片异常,Url为{}", imageUrl, e);
            throw new RuntimeException("身份证获取图片请求失败, url=" + imageUrl);
        }
        HttpHeaders fileHeader = new HttpHeaders();
        fileHeader.setContentType(MediaType.IMAGE_PNG);
        return new HttpEntity<>(byteArrayResource, fileHeader);
    }

    private HttpEntity<String> buildJsonPart(IdCardOCRParamBO paramBO) {
        String requestData = JSON.toJSONString(paramBO);
        String requestDataBase64 = Base64.encodeBase64URLSafeString(requestData.getBytes(UTF_8));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(requestDataBase64, httpHeaders);
    }

    /**
     * 获取http响应json数据结果
     */
    private String getResponse(String url, ResponseEntity<String> responseEntity) {
        if (responseEntity == null ||
                responseEntity.getStatusCode() == null ||
                !responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("请求失败, url=" + url + ", responseEntity：" + responseEntity);
        }
        return responseEntity.getBody();
    }

}
