package pe.mar.writer.news.collector;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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

	@Inject
	Configuration freemarkerConfig;

	abstract String getUrl();

	public String collect(String url) throws Exception {
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.62 Safari/537.36");
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

	String decorateBody(News news) throws Exception {
		if (isRoutineTitle(news.getTitle())) {
			news.setUpdateTime(new Date());
		}
		freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");
		Template t = freemarkerConfig.getTemplate("entry.ftl");
		String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, news);
		return text;
	}

	private boolean isRoutineTitle(String title) {
		String[] routineTitles = { "국제 주요 뉴스", "세계 주요 뉴스", "지구촌 뉴스", "최근 소식", "지구촌 화제" };
		return Arrays.asList(routineTitles).contains(title);
	}

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
					publish(decorateTitle(news.getTitle()), decorateBody(news));
				} else {
					log.info("title : {}, body : {}", decorateTitle(news.getTitle()), decorateBody(news));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Data
	@RequiredArgsConstructor
	public class News {
		@NonNull
		String title;
		@NonNull
		String body;
		String link;
		Article mainArticle;
		List<Article> subArticles;
		Date updateTime;
	}

	@Data
	@AllArgsConstructor
	public class Article {
		String medium;
		String title;
		String content;
		String link;
		String image;
	}
}
