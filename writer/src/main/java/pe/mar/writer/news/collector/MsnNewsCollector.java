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

	List<News> splitBody(String news) {
		Pattern pattern = Pattern.compile(
				"(<h2 class=\"hide\".+?>(.+?)</h2>.+?<ul>.*?</ul>)[\\s]*</div>[\\s]*</div></div></div>",
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
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		return date + " " + title;
	}

	String decorateBody(News news) {
		String cutString = news.getBody();
		String replacedString = cutString.replace("<a href=\"/", "<a href=\"https://www.msn.com/")
				.replaceAll(" src=\".+?\"", " ").replaceAll(" data-src=\".+?(//.+?)&quot;}\"", " src=\"$1\"")
				.replaceAll("&amp;", "&").replaceAll("(?s)<li  class=\"showcasead\">.+?</li>", "");
		return replacedString;
	}
}
