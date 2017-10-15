package pe.mar.writer;

import java.io.IOException;

import javax.inject.Inject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(WriterApplication.class)
public class HttpClientConfigTest {
	@Inject
	CloseableHttpClient httpClient;

	@Test
	public void test() throws ClientProtocolException, IOException {
		String target = "http://news.naver.com/main/main.nhn?mode=LSD&mid=shm&sid1=104";
		HttpGet httpGet = new HttpGet(target);
		CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
		HttpEntity httpEntity = httpResponse.getEntity();
		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			String entity = EntityUtils.toString(httpEntity);
			System.out.println(entity);
		}

	}
	
}
