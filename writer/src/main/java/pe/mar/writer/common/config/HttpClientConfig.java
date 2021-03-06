package pe.mar.writer.common.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfig {
	@Bean
	CloseableHttpClient httpClient() {
		RequestConfig accountRequestConfig = RequestConfig.custom().build();

		return HttpClientBuilder.create().setDefaultRequestConfig(accountRequestConfig).build();
	}
}
