package pe.mar.writer;

import java.net.URL;
import java.util.Map;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

@Component
@PropertySource("classpath:blog.properties")
public class XmlRpcBlogWriter {
	@Value("${blog.api.url}")
	String apiUrl;
	@Value("${blog.api.username}")
	String apiUserName;
	@Value("${blog.api.password}")
	String apiPassword;

	public String write(String category, String title, String desc) throws Exception {
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL(apiUrl));

		Map<String, Object> contents = Maps.newHashMap();
		contents.put("categories", new String[] { category });
		contents.put("title", title);
		contents.put("description", desc);

		int blogId = 0;
		String userName = apiUserName;
		String password = apiPassword;
		boolean publish = true;

		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);

		String rsString = (String) client.execute("metaWeblog.newPost",
				new Object[] { blogId, userName, password, contents, publish });

		return rsString;
	}
}
