package pe.mar.writer.news.collector;

import java.util.List;

import javax.inject.Inject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import pe.mar.common.utils.IsEmpty;
import pe.mar.writer.news.publisher.MetaWeblogClient;

@Service
@Slf4j
public abstract class NewsCollectorBase {
	@Inject
	CloseableHttpClient httpClient;
	@Inject
	MetaWeblogClient blogWriter;

	abstract String getUrl();

	public String collect(String url) throws Exception {
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.62 Safari/537.36");
		CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
		HttpEntity httpEntity = httpResponse.getEntity();
		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			String entity = EntityUtils.toString(httpEntity);
			return entity;
		}
		return null;
	}

	abstract List<News> splitBody(String news);

	abstract String decorateTitle(String title);

	abstract String decorateBody(String cutString);

	public void publish(String title, String news) throws Exception {
		String rs = blogWriter.publish("소식", title, news);
		log.info("publish result : {}", rs);
	}

	public void execute(boolean publish) {
		try {
			String html = collect(getUrl());
			if (IsEmpty.string(html)) {
				log.error("empty result");
				return;
			}
			List<News> list = splitBody(html);
			for (News news : list) {
				if (publish) {
					publish(decorateTitle(news.getTitle()), decorateBody(news.getBody()));
				} else {
					log.info("title : {}, body : {}", decorateTitle(news.getTitle()), decorateBody(news.getBody()));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Data
	@AllArgsConstructor
	public class News {
		String title;
		String body;
	}
}
