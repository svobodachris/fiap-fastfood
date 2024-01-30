package io.fiap.fastfood.main.configuration;

import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@Configuration
public class WebClientConfiguration {

/*    @Bean("xptoClient")
    public WebClient dxlClient(@Value("${client.xpto.connectionTimeout:50000}") Integer connectionTimeout,
                               @Value("${client.xpto.responseTimeout:50000}") Integer responseTimeout,
                               @Value("${client.xpto.readTimeout:50000}") Integer readTimeout,
                               @Value("${client.xpto.writeTimeout:50000}") Integer writeTimeout,
                               @Value("${client.xpto.maxConnections:5}") Integer maxConnections,
                               @Value("${client.xpto.url:http://localhost:8081}") String uri,
                               SslContext sslContext) {
        return getWebClient(connectionTimeout, responseTimeout, readTimeout, writeTimeout, maxConnections, uri, "dxl", sslContext);
    }*/

    private WebClient getWebClient(Integer connectionTimeout, Integer responseTimeout,
                                   Integer readTimeout, Integer writeTimeout, Integer maxConnections,
                                   String uri, String name, SslContext sslContext) {

        HttpClient httpClient = HttpClient.create(ConnectionProvider.create(name, maxConnections))
            .secure(sslContextSpec -> sslContextSpec.sslContext(sslContext))
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeout)
            .responseTimeout(Duration.ofMillis(responseTimeout))
            .doOnConnected(conn ->
                conn.addHandlerLast(new ReadTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS))
                    .addHandlerLast(new WriteTimeoutHandler(writeTimeout, TimeUnit.MILLISECONDS)));

        return WebClient.builder()
            .baseUrl(uri)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();
    }

}
