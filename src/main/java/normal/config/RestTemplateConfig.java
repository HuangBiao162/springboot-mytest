package normal.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * restTemplate配置
 *
 * @author jianlong_li
 * @date 2019/2/14
 */
@Configuration
@EnableConfigurationProperties(RestTemplateConfig.ConfigProperties.class)
public class RestTemplateConfig {

    private static final int KEEP_ALIVE_GAP_TIME = 1000;

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory httpRequestFactory, ConfigProperties configProperties) {
        RestTemplate restTemplate = new RetryRestTemplate(httpRequestFactory, configProperties.getRetryTimes());

        // StringHttpMessageConverter 默认使用ISO-8859-1编码，此处修改为UTF-8
        List <HttpMessageConverter <?>> messageConverters = restTemplate.getMessageConverters();
        for (HttpMessageConverter <?> converter : messageConverters) {
            if (converter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) converter).setDefaultCharset(Charset.forName("UTF-8"));
            }
        }

        return restTemplate;
    }

    @Bean
    public ClientHttpRequestFactory httpRequestFactory(HttpClient httpClient) {
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    @Bean
    public HttpClient httpClient(ConfigProperties configProperties) {

        int keepAliveDuration = configProperties.getKeepAliveDuration();

        // 设置连接池中连接保存的时间比keep-alive的短1秒，保证连接都是有效的
        int keepPoolConnectionAlive = keepAliveDuration;
        if (keepAliveDuration > KEEP_ALIVE_GAP_TIME) {
            keepPoolConnectionAlive = keepAliveDuration - KEEP_ALIVE_GAP_TIME;
        }
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(keepPoolConnectionAlive, TimeUnit.MILLISECONDS);

        // 总的最大连接数
        connectionManager.setMaxTotal(configProperties.getMaxTotal());
        // 每个主机最多能拿到的连接数
        connectionManager.setDefaultMaxPerRoute(configProperties.getMaxPerRoute());

        RequestConfig requestConfig = RequestConfig.custom()
                // http读取数据超时时间
                .setSocketTimeout(configProperties.getSocketTimeout())
                // 获取指定主机的连接超时时间
                .setConnectTimeout(configProperties.getConnectTimeout())
                // 连接池获取连接的超时时间
                .setConnectionRequestTimeout(configProperties.getConnectionRequestTimeout())
                .build();


        return HttpClientBuilder.create()
                .setKeepAliveStrategy((response, context) -> keepAliveDuration)
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                .setRetryHandler(new DefaultHttpRequestRetryHandler())
                // 禁止本身的重试机制
                .disableAutomaticRetries()
                .build();
    }

    @Data
    @ConfigurationProperties(prefix = "rest-template")
    public static class ConfigProperties {
        /**
         * 总的最大连接数
         */
        private int maxTotal = 400;

        /**
         * 每个主机最多能拿到的连接数
         */
        private int maxPerRoute = 80;

        /**
         * http读取数据超时时间
         */
        private int socketTimeout = 15000;

        /**
         * 获取指定主机的连接超时时间
         */
        private int connectTimeout = 3000;

        /**
         * 连接池获取连接的超时时间
         */
        private int connectionRequestTimeout = 500;

        /**
         * 重试次数
         */
        private int retryTimes = 3;

        /**
         * keepAlive，默认20秒
         */
        private int keepAliveDuration = 20000;

    }

    @Slf4j
    public static class RetryRestTemplate extends RestTemplate {
        private int maxRetryTimes;
        private static List <Class <? extends Exception>> excludeExceptionList = Arrays.asList(UnknownHostException.class, SSLException.class, SocketTimeoutException.class);

        private RetryRestTemplate(ClientHttpRequestFactory requestFactory, int maxRetryTimes) {
            super(requestFactory);
            this.maxRetryTimes = maxRetryTimes;
        }

        @Override
        protected <T> T doExecute(URI url, HttpMethod method, RequestCallback requestCallback, ResponseExtractor <T> responseExtractor) {
            try {
                return super.doExecute(url, method, requestCallback, responseExtractor);
            } catch (Exception e) {
                // 进行重试
                if (isRetryRequest(url, e, this.maxRetryTimes)) {
                    return this.doExecute(url, method, requestCallback, responseExtractor);
                }

                // 不再重试，直接抛出异常
                throw e;
            }
        }

        /**
         * 是否进行重试
         */
        public static boolean isRetryRequest(URI url, Exception e, int maxRetryTimes) {
            return false;
        }
    }

}