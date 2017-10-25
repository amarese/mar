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

	List<News> splitBody(String news) {
		Pattern pattern = Pattern.compile(
				"<div class=\"mduCluster\">([ \\s]*<h4[^>]*>(.+?)</h4>.*?<ul class=\"mduList1\">.+?</ul>[ \\s]*</div>)",
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
		if ("세계 주요 뉴스".equals(title)) {
			String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
			return date + " " + title;
		}
		return title;
	}

	String decorateBody(News news) {
		String cutString = news.getBody();
		String replacedString = cutString
				.replace("<h4",
						"<h4 style=\"margin: 0px; padding: 0px 0px 15px; font-stretch: normal; font-size: 12pt; line-height: normal; font-family: Dotum, Helvetica, sans-serif; color: #2d589e; letter-spacing: -1px;\"")
				.replace("class=\"clusterType1\"",
						"style=\"color: #666666; font-family: Gulim, Dotum, Helvetica, sans-serif; font-size: 12px;\"")
				.replace("class=\"mlt01\"", "style=\"position: relative; overflow: hidden; margin: 0px;\"")
				.replace("class=\"lt1\"",
						"style=\"text-decoration-line: none; padding: 0px 0px 3px; color: #777777 !important;\"")
				.replace("class=\"ib\"",
						"style=\"position: relative; display: block; float: left; height: 80px; margin-right: 15px;\"")
				.replace("class=\"tb\"",
						"style=\"overflow: hidden; display: block; line-height: 18px; color: #666666;\"")
				.replace("class=\"tit\"",
						"style=\"display: block; padding-bottom: 4px; font-family: Dotum, Helvetica, sans-serif; font-size: 16px; color: #111111; letter-spacing: -1px; line-height: normal;\"")
				.replace("class=\"medium\"",
						"style=\"font-stretch: normal; font-size: 11px; line-height: normal; font-family: Dotum, Helvetica, sans-serif; letter-spacing: -1px; display: inline-block; padding-top: 3px; color: #b89494 !important;\"")
				.replace("<dt><a",
						"<dt><a style=\"color: #6e4987; text-decoration-line: none; display: inline-block; width: 399px; height: 21px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis;\"")
				.replace("<li><a", "<li><a style=\"color: #6e4987; text-decoration-line: none; margin-right: 4px;\"")
				.replace("<li>",
						"<li style=\"margin: 0px; padding: 0px 0px 5px 11px; list-style: none; line-height: 14px; background: url('http://static.news.naver.net/image/news/2009/ico_list_sub2.gif') 0px 2px no-repeat;\">")
				.replace("<img ", "<img style=\"border: 1px solid #e8e8e8; display: block;\" ")
				.replaceAll("onclick=\".+?\"", "").replaceAll("onerror=\".+?\"", "");
		return replacedString;
	}
}
