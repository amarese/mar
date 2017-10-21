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

	List<News> splitBody(String news) {
		Pattern pattern = Pattern.compile(
				"<div class=\"issue_news_list.+?>[\\s]+(<h3><img.+?>(.+?)</h3>[\\s]+<ul class=\"news\">.*?</ul>)",
				Pattern.DOTALL);
		Matcher matcher = pattern.matcher(news);
		List<News> result = Lists.newArrayList();
		while (matcher.find()) {
			String cutString = matcher.group(1);
			String title = matcher.group(2);
			result.add(new News(title.trim(), cutString.trim()));
		}
		return result;
	}

	String decorateTitle(String title) {
		return title;
	}

	String decorateBody(String cutString) {
		String replacedString = cutString.replace("<a href=\"/", "<a href=\"http://news.zum.com/");
		return replacedString;
	}
}
