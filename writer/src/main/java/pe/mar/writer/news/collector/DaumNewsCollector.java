package pe.mar.writer.news.collector;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import lombok.Getter;

@Service
public class DaumNewsCollector extends NewsCollectorBase {
	@Getter
	String url = "http://media.daum.net/foreign/";

	List<News> splitBody(String html) {
		Pattern pattern = Pattern.compile(
				"<a href=\"/issue/\" class=\"link_txt\">이슈</a>(.+?<a href=\"(/issue/[0-9]+)\"[^>]*>(.+?)</a>.*?<div class=\"relate_news\">.+?</div>[ \\s]*)</li>",
				Pattern.DOTALL);
		Matcher matcher = pattern.matcher(html);
		List<News> result = Lists.newArrayList();
		while (matcher.find()) {
			String cutString = matcher.group(1);
			String link = matcher.group(2);
			String title = matcher.group(3);
			News news = new News(title.trim(), cutString.trim());

			if (link.startsWith("/")) {
				link = link.replaceFirst("/", "http://media.daum.net/");
			}
			news.setLink(link);

			Article mainArticle = mainArticle(cutString);
			news.setMainArticle(mainArticle);
			List<Article> subArticles = subArticles(cutString);
			news.setSubArticles(subArticles);
			result.add(news);
		}
		return result;
	}

	Article mainArticle(String cutString) {
		Pattern pattern = Pattern.compile("<img src=\"(.+?)\" class=\"thumb_g\"", Pattern.DOTALL);
		Matcher matcher = pattern.matcher(cutString);
		String image = "";
		if (matcher.find()) {
			image = matcher.group(1);
		}
		Pattern pattern2 = Pattern.compile(
				"<strong class=\"tit_thumb\">[\\s]*<a.*?href=\"(.+?)\".*?>(.+?)</a>.*?<span class=\"info_news\">(.+?)</span>",
				Pattern.DOTALL);
		Matcher matcher2 = pattern2.matcher(cutString);
		matcher2.find();
		String link = matcher2.group(1);
		String title = matcher2.group(2);
		String medium = matcher2.group(3);

		if (link.startsWith("/")) {
			link = link.replaceFirst("/", "http://media.daum.net/");
		}

		Article mainArticle = new Article(medium, title, null, link, image);
		return mainArticle;

	}

	List<Article> subArticles(String cutString) {
		Pattern pattern = Pattern.compile(
				"<div class=\"item_relate\">.+?<a.+?href=\"(.+?)\".+?>(.+?)</a>.+?<span class=\"info_news\">(.+?)</span>.+?</div>",
				Pattern.DOTALL);
		Matcher matcher = pattern.matcher(cutString);
		List<Article> result = Lists.newArrayList();
		while (matcher.find()) {
			String link = matcher.group(1);
			String title = matcher.group(2);
			String medium = matcher.group(3);

			if (link.startsWith("/")) {
				link = link.replaceFirst("/", "http://media.daum.net/");
			}
			Article article = new Article(medium, title, null, link, null);
			result.add(article);
		}
		return result;
	}

	String decorateTitle(String title) {
		return title;
	}
}
