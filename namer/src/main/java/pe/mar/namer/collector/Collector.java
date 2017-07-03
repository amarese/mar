package pe.mar.namer.collector;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;

public class Collector {
	@Bean
	CloseableHttpClient httpClient() {
		RequestConfig accountRequestConfig = RequestConfig.custom().build();

		return HttpClientBuilder.create().setDefaultRequestConfig(accountRequestConfig).build();
	}

}
