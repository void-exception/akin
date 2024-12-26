package com.example.demo.security;

//import org.apache.http.HttpHost;
//import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
//import org.apache.http.ssl.SSLContexts;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestClientBuilder;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.net.ssl.SSLContext;
//
//@Configuration
//public class ElasticsearchConfig {
//
//    @Bean
//    public RestHighLevelClient elasticsearchClient() {
//        try {
//            // Создаём SSLContext, который доверяет всем сертификатам
//            SSLContext sslContext = SSLContexts.custom()
//                    .loadTrustMaterial((chain, authType) -> true) // Игнорируем проверки сертификата
//                    .build();
//
//            RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200, "https"))
//                    .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
//                            .setSSLContext(sslContext)
//                            .setSSLHostnameVerifier((hostname, session) -> true)); // Игнорируем проверку хоста
//
//            return new RestHighLevelClient(builder);
//
//        } catch (Exception e) {
//            throw new RuntimeException("Ошибка при настройке клиента Elasticsearch", e);
//        }
//    }
//}
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;

@Configuration
public class ElasticsearchConfig {

    @Value("${spring.elasticsearch.uris}")
    private String elasticsearchUri; // URI вида https://localhost:9200

    @Value("${spring.elasticsearch.username}")
    private String username;

    @Value("${spring.elasticsearch.password}")
    private String password;

    @Bean
    public RestHighLevelClient elasticsearchClient() {
        try {
            // Настройка SSLContext для самоподписанных сертификатов
            SSLContext sslContext = SSLContexts.custom()
                    .loadTrustMaterial((chain, authType) -> true) // Доверять всем сертификатам (не для продакшн)
                    .build();

            // Настройка Basic Auth
            BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

            // Настройка RestHighLevelClient с HTTPS
            RestClientBuilder builder = RestClient.builder(HttpHost.create(elasticsearchUri)) // https://localhost:9200
                    .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                            .setDefaultCredentialsProvider(credentialsProvider) // Basic Auth
                            .setSSLContext(sslContext)); // Добавляем SSLContext для работы с HTTPS

            return new RestHighLevelClient(builder);

        } catch (Exception e) {
            throw new RuntimeException("Ошибка при настройке клиента Elasticsearch", e);
        }
    }
}
