package pe.mar.writer.news.publisher;

import java.util.Map;

import javax.inject.Inject;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import pe.mar.writer.common.config.XmlRpcClientConfig;

@Service
public class MetaWeblogClient {
	@Inject
	XmlRpcClient xmlRpcClient;
	@Inject
	XmlRpcClientConfig xmlRpcClientConfig;

	public String publish(String category, String title, String desc) throws Exception {
		int blogId = 0;
		String userName = xmlRpcClientConfig.getApiUserName();
		String password = xmlRpcClientConfig.getApiPassword();

		Map<String, Object> contents = Maps.newHashMap();
		contents.put("categories", new String[] { category });
		contents.put("title", title);
		contents.put("description", desc);

		boolean publish = true;

		String rsString = newPost(blogId, userName, password, contents, publish);

		return rsString;
	}

	private String newPost(int blogId, String userName, String password, Map<String, Object> contents, boolean publish)
			throws XmlRpcException {
		String rsString = (String) xmlRpcClient.execute("metaWeblog.newPost",
				new Object[] { blogId, userName, password, contents, publish });
		return rsString;
	}
}
