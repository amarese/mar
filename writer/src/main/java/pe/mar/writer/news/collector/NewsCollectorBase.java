package pe.mar.writer.news.collector;

import java.text.SimpleDateFormat;
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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.mar.common.utils.IsEmpty;
import pe.mar.common.utils.IsNotEmpty;
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

	String decorateBody(News news) {
		String title = String.format("<h3>%s</h3>", news.getTitle());
		String intro = "";
		if ("국제 주요 뉴스".equals(news.getTitle()) || "세계 주요 뉴스".equals(news.getTitle()) || "지구촌 뉴스".equals(news.getTitle())
				|| "최근 소식".equals(news.getTitle())) {
			String date = new SimpleDateFormat("yyyy년 MM월 dd일").format(new Date());
			intro = IsNotEmpty.string(news.getLink())
					? String.format("%s, \"<a href=\"%s\">%s</a>\" 입니다.<br>", date, news.getLink(), news.getTitle())
					: String.format("%s, \"%s\" 입니다.<br>", date, news.getTitle());
		} else {
			intro = IsNotEmpty.string(news.getLink()) ? String
					.format("현재 국제 정가에서는 \"<a href=\"%s\">%s</a>\" 관련 이슈가 화제입니다.<br>", news.getLink(), news.getTitle())
					: String.format("현재 국제 사회에서는 \"%s\" 관련 이슈가 화제입니다.<br>", news.getTitle());
		}
		Article mainArticle = news.getMainArticle();

		String main = "";
		String sub = "";
		if (mainArticle != null) {
			main += IsNotEmpty.string(mainArticle.getImage())
					? String.format("<a href=\"%s\"><img src=\"%s\" align=\"left\"></a>", mainArticle.getLink(),
							mainArticle.getImage())
					: "";

			main += IsNotEmpty.string(mainArticle.getContent())
					? String.format("<p>최근 %s 에서는  \"<a href=\"%s\">%s</a>\" 의 제목으로 아래와 같은 보도를 전했습니다.</p>" + "%s",
							mainArticle.getMedium(), mainArticle.getLink(), mainArticle.getTitle(),
							mainArticle.getContent())
					: String.format("<p>최근 %s 에서는  \"<a href=\"%s\">%s</a>\" 라도 보도했습니다.</p>", mainArticle.getMedium(),
							mainArticle.getLink(), mainArticle.getTitle());
			sub = "<br><br>그 외에도 각종 언론사들은 다음과 같은 보도를 이어나갔습니다.<br><br>";
		} else {
			sub = "<br><br>여러 언론사들은 다음과 같은 보도로 관련 소식을 전했습니다.<br><br>";
		}
		sub += "<ul>\n";
		for (Article article : news.getSubArticles()) {
			sub += "<li style=\"list-style:none;\"><dl style=\"overflow:hidden;\">";
			sub += IsNotEmpty.string(article.getImage()) ? String.format(
					"<dt><a href=\"%s\"><img src=\"%s\" style=\"position:relative; float:left; display: inline;\"></a></dt>\n",
					article.getLink(), article.getImage()) : "";
			sub += String.format("<dt><a href=\"%s\">%s</a> - %s</dt>\n", article.getLink(), article.getTitle(),
					article.getMedium());
			sub += IsNotEmpty.string(article.getContent()) ? String.format("<dd>%s</dd>\n", article.getContent()) : "";
			sub += "</li>";
		}
		sub += "</ul>\n";
		String result = String.format("%s\n%s\n%s\n%s", title, intro, main, sub);
		return result;
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
