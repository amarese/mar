package pe.mar.writer.news.collector;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import lombok.Getter;

@Service
public class BingNewsCollector extends NewsCollectorBase {
	@Getter
	String url = "https://www.bing.com/news/infinitescrollajax?nvaug=%5bNewsVertical+Category%3d%22rt_World%22%5d&FORM=NWRFSH&InfiniteScroll=1&q=&first=1&IG=D99D2382393843B19B185CDECD0E98F0&IID=NEWS.302&SFX=1&PCW=768";

	List<News> splitBody(String news) {
		List<News> result = Lists.newArrayList();
		String cutString = news;
		String title = "국제 주요 뉴스";
		result.add(new News(title.trim(), cutString.trim()));
		return result;
	}

	String decorateTitle(String title) {
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		return date + " " + title;
	}

	String decorateBody(News news) {
		String cutString = news.getBody();
		String replacedString = cutString
				.replace("class=\"emphsnippet_g\"",
						"style=\"color: #444444; line-height: 17px; margin: 4px 0px 0px; max-height: 34px; overflow: hidden;\"")
				.replace("class=\"title\"",
						"style=\"color: #600090; text-decoration-line: none; font-size: 15px; line-height: 20px; display: block;\"")
				.replace("class=\"image\"",
						"style=\"margin-right: 16px; float: left; position: relative; overflow: hidden; width: 204px; height: 115px;\"")
				.replace("class=\"imagelink\"",
						"style=\"color: #600090; text-decoration-line: none; display: inline-block; position: relative;\"")
				.replace("class=\"source\"",
						"style=\"position: absolute; bottom: 6px; left: 0px; line-height: 17px; color: #767676;\"")
				.replace("class=\"timestamp\"", "style=\"margin-left: 10px;\"")
				.replace("class=\"caption\"",
						"style=\"float: left; position: relative; margin-left: 0px; width: 568px; height: 115px;\"")
				.replace(" src=\"/", " src=\"https://www.bing.com/")
				.replace(" href=\"/", " href=\"https://www.bing.com/");
		return replacedString;
	}
}
