package pe.mar.writer.news.collector;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import lombok.Getter;

@Service
public class NaverNewsCollector extends NewsCollectorBase {
	@Getter
	String url = "http://news.naver.com/main/main.nhn?mode=LSD&mid=shm&sid1=104";

	List<News> splitBody(String news) {
		Pattern pattern = Pattern.compile(
				"<div class=\"section_headline headline_subordi\">([ \\s]*<h5[^>]*><a[^>]*>(.+?)</a>.*?</h5>.*?<ul class=\"slist1\">.+?</ul>[ \\s]*)</div>",
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
				.replace("class=\"compo_headtxt\"",
						"style=\"margin: 0px 0px 7px; padding: 0px; font-size: 14px; line-height: 21px; color: #2f57aa;\"")
				.replace("class=\"compo_linkhead\"",
						"style=\"color: #2f57aa; text-decoration-line: none; display: inline-block; vertical-align: middle;\"")
				.replace("class=\"compo_more\"",
						"style=\"color: #d63f18; text-decoration-line: none; display: inline-block; margin: 0px 0px -2px 7px; font-size: 11px; font-weight: normal; letter-spacing: -1px; vertical-align: middle;\"")
				.replace("class=\"photo\"",
						"style=\"margin: 1px 14px 0px 0px; padding: 0px; height: auto; font-size: 18px; font-weight: bold; letter-spacing: -1px; position: relative; float: left; display: inline-block;\"")
				.replace("class=\"sphoto1\"",
						"style=\"margin: 0px; padding: 0px; clear: both; float: left; width: 573px; color: #2f2f2f; font-size: 12px;\"")
				.replace("class=\"slist1\"",
						"style=\"margin: 0px; padding: 6px 0px 0px; clear: both; color: #2f2f2f; font-size: 12px;\"")
				.replace("class=\"writing\"",
						"style=\"display: inline-block; margin: 0px 0px 0px 10px; font-size: 11px; color: #c64c4c; letter-spacing: -1px; line-height: 17px;\"")
				.replace("class=\"r_ico r_vod_small\"",
						"style=\"background-image: url('http://static.news.naver.net/image/news/2017/09/13/sp_news.png'); background-size: 354px 328px; display: block; position: absolute; font-size: 0px; line-height: 0; vertical-align: middle; width: 26px; height: 26px; background-position: -160px -88px; left: 4px; bottom: 4px;\"")
				.replace("<dt><a",
						"<dt><a style=\"color: #6e4987; text-decoration-line: none; display: inline-block; width: 399px; height: 21px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis;\"")
				.replace("<li><a", "<li><a style=\"color: #6e4987; text-decoration-line: none; margin-right: 4px;\"")
				.replace("<li>",
						"<li style=\"margin: 0px; padding: 0px 0px 5px 11px; list-style: none; line-height: 14px; background: url('http://static.news.naver.net/image/news/2009/ico_list_sub2.gif') 0px 2px no-repeat;\">")
				.replace("<dt>",
						"<dt style=\"margin: 0px; padding: 3px 0px 0px; height: 21px; font-size: 18px; font-weight: bold; letter-spacing: -1px;\">")
				.replaceAll("onError=\".+\"", "");
		return replacedString;
	}
}
