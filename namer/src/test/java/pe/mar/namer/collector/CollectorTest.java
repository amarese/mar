package pe.mar.namer.collector;

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

import pe.mar.namer.NamerApplication;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(NamerApplication.class)
public class CollectorTest {
	@Inject
	CloseableHttpClient httpClient;

	@Test
	public void test() throws ClientProtocolException, IOException {
		String target = "https://www.naver.com/";
		HttpGet httpGet = new HttpGet(target);
		CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
		HttpEntity httpEntity = httpResponse.getEntity();
		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			String entity = EntityUtils.toString(httpEntity);
			System.out.println(entity);
		}

	}

}
