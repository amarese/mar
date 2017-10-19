package pe.mar.writer.common.config;

import java.net.URL;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;

@Configuration
@PropertySource("classpath:blog.properties")
@Getter
public class XmlRpcClientConfig {
	@Value("${blog.api.url}")
	String apiUrl;
	@Value("${blog.api.username}")
	String apiUserName;
	@Value("${blog.api.password}")
	String apiPassword;

	@Bean
	XmlRpcClient xmlRpcClient() throws Exception {
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL(apiUrl));

		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);
		return client;
	}
}
