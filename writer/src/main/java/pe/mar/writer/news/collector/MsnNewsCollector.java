package pe.mar.writer.news.collector;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import lombok.Getter;

@Service
public class MsnNewsCollector extends NewsCollectorBase {
	@Getter
	String url = "https://www.msn.com/ko-kr/news/world";

	List<News> splitBody(String html) {
		Pattern pattern = Pattern.compile(
				"(<h2 class=\"hide\".+?>(.+?)</h2>.+?<ul>.*?</ul>)[\\s]*</div>[\\s]*</div></div></div>",
				Pattern.DOTALL);
		Matcher matcher = pattern.matcher(html);
		List<News> result = Lists.newArrayList();
		while (matcher.find()) {
			String cutString = matcher.group(1);
			String title = matcher.group(2);

			News news = new News(title.trim(), cutString.trim());

			Article mainArticle = mainArticle(cutString);
			news.setMainArticle(mainArticle);
			List<Article> subArticles = subArticles(cutString);
			news.setSubArticles(subArticles);
			result.add(news);
		}
		return result;
	}

	Article mainArticle(String cutString) {

		Pattern pattern = Pattern.compile(
				"<li class=\"ip_a\">.*?<a.*?href=\"(.+?)\".*?>.*?<img.*?data-src=\".*?(//.+?)&quot;}\".*?<h3>(.+?)</h3>.*?<img.*?>[\\s]+<span>(.+?)</span>.*?</div>[\\s]+</a>[\\s]+</li>",
				Pattern.DOTALL);
		Matcher matcher = pattern.matcher(cutString);
		matcher.find();
		String link = matcher.group(1);
		String image = matcher.group(2);
		String title = matcher.group(3);
		String medium = matcher.group(4);

		if (link.startsWith("/")) {
			link = link.replaceFirst("/", "https://www.msn.com/");
		}

		image = image.replaceFirst("//", "http://");
		image = image.replaceAll("&amp;", "&");

		Article mainArticle = new Article(medium.trim(), title.trim(), null, link.trim(), image.trim());
		return mainArticle;

	}

	List<Article> subArticles(String cutString) {
		Pattern pattern = Pattern.compile(
				"<li.*?class=\".*?(mediuma|smalla).*?\".*?>.*?<a.*?href=\"(.+?)\".*?>.*?<img.*?data-src=\".*?(//.+?)&quot;}\".*?<h3>(.+?)</h3>[\\s]+<span class=\"sourcename\">[\\s]+<img.*?>(.+?)</span>[\\s]+</div>[\\s]+</a>[\\s]+</li>",
				Pattern.DOTALL);
		Matcher matcher = pattern.matcher(cutString);
		List<Article> result = Lists.newArrayList();
		while (matcher.find()) {
			String link = matcher.group(2);
			String image = matcher.group(3);
			String title = matcher.group(4);
			String medium = matcher.group(5);

			if (link.startsWith("/")) {
				link = link.replaceFirst("/", "https://www.msn.com/");
			}

			image = image.replaceFirst("//", "http://");
			image = image.replaceAll("&amp;", "&");

			Article article = new Article(medium.trim(), title.trim(), null, link.trim(), image.trim());
			result.add(article);
		}
		return result;
	}

	String decorateTitle(String title) {
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		return date + " " + title;
	}
}
