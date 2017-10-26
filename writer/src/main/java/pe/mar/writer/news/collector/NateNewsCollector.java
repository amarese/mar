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
public class NateNewsCollector extends NewsCollectorBase {
	@Getter
	String url = "http://news.nate.com/section?mid=n0500";

	List<News> splitBody(String html) {
		Pattern pattern = Pattern.compile(
				"<div class=\"mduCluster\">([ \\s]*<h4[^>]*>(.+?)</h4>.*?<ul class=\"mduList1\">.+?</ul>[ \\s]*</div>)",
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

		Pattern pattern = Pattern.compile("<span class=\"ib\">.+?<img src=\"(.+?)\".+?", Pattern.DOTALL);
		Matcher matcher = pattern.matcher(cutString);
		String image = "";
		if (matcher.find()) {
			image = matcher.group(1);
		}
		Pattern pattern2 = Pattern.compile(
				"<a.+?href=\"(.+?)\".+?<span class=\"tb\">[\\s]*<strong class=\"tit\">(.+?)</strong>[\\s]*(.+?)</span>[\\s]*</a>[\\s]*<span class=\"medium\">(.+?)<em>",
				Pattern.DOTALL);
		Matcher matcher2 = pattern2.matcher(cutString);
		matcher2.find();
		String link = matcher2.group(1);
		String title = matcher2.group(2);
		String content = matcher2.group(3);
		String medium = matcher2.group(4);

		Article mainArticle = new Article(medium, title, content, link, image);
		return mainArticle;

	}

	List<Article> subArticles(String cutString) {
		Pattern pattern = Pattern.compile(
				"<li>.+?<a.+?href=\"(.+?)\".+?>(.+?)</a>.+?<span class=\"medium\">(.+?)</span>.+?</li>",
				Pattern.DOTALL);
		Matcher matcher = pattern.matcher(cutString);
		List<Article> result = Lists.newArrayList();
		while (matcher.find()) {
			String link = matcher.group(1);
			String title = matcher.group(2);
			String medium = matcher.group(3);

			Article article = new Article(medium, title, null, link, null);
			result.add(article);
		}
		return result;
	}

	String decorateTitle(String title) {
		if ("세계 주요 뉴스".equals(title) || "지구촌 뉴스".equals(title)) {
			String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
			return date + " " + title;
		}
		return title;
	}
}
