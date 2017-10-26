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
public class BingNewsCollector extends NewsCollectorBase {
	@Getter
	String url = "https://www.bing.com/news/infinitescrollajax?nvaug=%5bNewsVertical+Category%3d%22rt_World%22%5d&FORM=NWRFSH&InfiniteScroll=1&q=&first=1&IG=D99D2382393843B19B185CDECD0E98F0&IID=NEWS.302&SFX=1&PCW=768";

	List<News> splitBody(String html) {
		List<News> result = Lists.newArrayList();
		String cutString = html;
		String title = "국제 주요 뉴스";
		News news = new News(title.trim(), cutString.trim());

		List<Article> subArticles = subArticles(cutString);
		news.setSubArticles(subArticles);
		result.add(news);
		return result;
	}

	List<Article> subArticles(String cutString) {
		Pattern pattern = Pattern.compile(
				"<div class=\"newsitem.+?<img.*?src=\"(.+?)\".+?<a.+?href=\"(.+?)\".+?>(.+?)</a>.*?<div class=\"snippet\">(.+?)</div>.*?<div class=\"source\">.*?<a.*?>(.+?)<span.+?</div>",
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
				link = link.replaceFirst("/", "https://www.bing.com/");
			}
			if (image.startsWith("/")) {
				image = image.replaceFirst("/", "https://www.bing.com/");
			}
			Article article = new Article(medium, title, content, link, image);
			result.add(article);
		}
		return result;
	}

	String decorateTitle(String title) {
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		return date + " " + title;
	}
}
