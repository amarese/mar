package pe.mar.writer.news.collector;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import lombok.Getter;

@Service
public class ZumNewsCollector extends NewsCollectorBase {
	@Getter
	String url = "http://news.zum.com/front?c=04";

	List<News> splitBody(String html) {
		Pattern pattern = Pattern.compile(
				"<div class=\"issue_news_list.+?>[\\s]+(<h3><img.+?>(.+?)</h3>[\\s]+<ul class=\"news\">.*?</ul>)",
				Pattern.DOTALL);
		Matcher matcher = pattern.matcher(html);
		List<News> result = Lists.newArrayList();
		while (matcher.find()) {
			String cutString = matcher.group(1);
			String title = matcher.group(2);

			News news = new News(title.trim(), cutString.trim());

			List<Article> subArticles = subArticles(cutString);
			news.setSubArticles(subArticles);
			result.add(news);
		}
		return result;
	}

	List<Article> subArticles(String cutString) {
		Pattern pattern = Pattern.compile(
				"<li>.*?<div class=\"img\">.*?<img src=\"(.+?)\".*?<div class=\"title\">.*?<a.*?href=\"(.+?)\".*?>(.+?)</a>.*?<div class=\"content\">[\\s]*<a.*?>(.+?)</a>.*?<span>(.+?)[\\s]*<span.*?</li>",
				Pattern.DOTALL);
		Matcher matcher = pattern.matcher(cutString);
		List<Article> result = Lists.newArrayList();
		while (matcher.find()) {
			String image = matcher.group(1);
			String link = matcher.group(2);
			String title = matcher.group(3);
			String content = matcher.group(4);
			String medium = matcher.group(5);

			if (link.startsWith("/")) {
				link = link.replaceFirst("/", "http://news.zum.com/");
			}

			Article article = new Article(medium, title, content, link, image);
			result.add(article);
		}
		return result;
	}

	String decorateTitle(String title) {
		return title;
	}
}
