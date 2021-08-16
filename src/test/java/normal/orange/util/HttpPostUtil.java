package normal.orange.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.params.CoreConnectionPNames;

public class HttpPostUtil {

    private static final String CONTENT_CHARSET = "UTF-8";// httpclient读取内容时使用的字符集

    public static String postJsonText(String url, String data) {
        String responseBody = null;
        PostMethod postMethod = null;
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1800000);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 1800000);
            postMethod = new PostMethod(url);
            postMethod.setRequestBody(data);

            postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
                CONTENT_CHARSET);
            postMethod.setRequestHeader("Content-type", "text/plain");//特别设置，其他形式不支持
            int statusCode = httpClient.executeMethod(postMethod);

            if (statusCode == HttpStatus.SC_OK) {

                BufferedReader reader = new BufferedReader(
                    new InputStreamReader(postMethod.getResponseBodyAsStream()));
                StringBuffer stringBuffer = new StringBuffer();
                String str = "";
                while ((str = reader.readLine()) != null) {
                    stringBuffer.append(str);
                }
                responseBody = stringBuffer.toString();
                // responseBody=postMethod.getResponseBodyAsString();

            } else {
                responseBody = statusCode + ";" + postMethod.getStatusText();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            postMethod.releaseConnection(); // 释放连接
        }
        return responseBody;
    }

}
