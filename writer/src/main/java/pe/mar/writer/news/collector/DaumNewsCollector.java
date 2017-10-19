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

	List<News> splitBody(String news) {
		Pattern pattern = Pattern.compile(
				"<li >(.+?<a href=\"/issue/[0-9]+\"[^>]*>(.+?)</a>.*?<div class=\"relate_news\">.+?</div>[ \\s]*)</li>",
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
		String replacedString = cutString
				.replace("class=\"emph_g\"",
						"style=\"font-weight: bold; color: #396dbb; display: block; padding: 3px 0px 4px; font-size: 14px; line-height: 20px; letter-spacing: -0.7px;\"")
				.replace("class=\"link_txt\"",
						"style=\"color: #396dbb; text-decoration-line: none;\"")
				.replace("class=\"link_thumb\"",
						"style=\"color: #333333; text-decoration-line: none; display: block; position: relative; float: right; margin-top: 8px; font-size: 14px; letter-spacing: -0.7px;\"")
				.replace("class=\"thumb_g\"",
						"style=\"border: 0px none; vertical-align: top;\"")
				.replace("class=\"cont_thumb\"",
						"style=\"margin: 0px; padding: 0px 40px 0px 0px; overflow: hidden; color: #333333; font-size: 14px; letter-spacing: -0.7px;\"")
				.replace("class=\"tit_thumb\"",
						"style=\"display: block; overflow: hidden; font-size: 21px; line-height: 30px; letter-spacing: -2px; text-overflow: ellipsis; white-space: nowrap;\"")
				.replace("class=\"info_news\"",
						"style=\"display: inline-block; padding-left: 4px; font-weight: normal; font-size: 12px; color: #888888;\"")
				.replace("class=\"screen_out\"",
						"style=\"margin: 0px; padding: 0px; overflow: hidden; position: absolute; width: 0px; height: 0px; line-height: 0; text-indent: -9999px;\"")
				.replace("class=\"item_relate\"",
						"style=\"margin: 0px; padding: 0px; height: 22px; font-size: 16px; line-height: 22px;\"")
				;
		return replacedString;
	}
}
