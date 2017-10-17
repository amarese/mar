package pe.mar.writer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import pe.mar.common.utils.IsEmpty;

@Service
@Slf4j
public class NaverNewsCollector {
	@Inject
	CloseableHttpClient httpClient;
	@Inject
	XmlRpcBlogWriter blogWriter;

	public String collect() throws Exception {
		String url = "http://news.naver.com/main/main.nhn?mode=LSD&mid=shm&sid1=104";

		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
		HttpEntity httpEntity = httpResponse.getEntity();
		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			String entity = EntityUtils.toString(httpEntity);
			return entity;
		}
		return null;
	}

	public String splitBody(String news) throws Exception {
		String beginStr = "<div class=\"section_headline headline_subordi\">";
		int start = news.indexOf(beginStr);
		int end = start + news.substring(start).indexOf("</div>");
		String cutString = news.substring(start + beginStr.length(), end);
		return cutString;

	}

	String decorate(String cutString) {
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

	String title(String cutString) {
		Pattern pattern = Pattern.compile("<h5[^>]*>(.+?)</h5>");
		Matcher matcher = pattern.matcher(cutString);
		if (matcher.find()) {
			String title = matcher.group(1).replaceAll("<[^>]+>", "").replace("더보기", "");
			return title;
		} else {
			log.error("title not found, matcher count {}", matcher.groupCount());
			throw new RuntimeException();
		}
	}

	public void publish(String title, String news) throws Exception {
		String rs = blogWriter.write("소식", title, news);
		log.info("publish result : {}", rs);
		// log.info(title + "\n\n\n\n\n\n" + news);
	}

	public void execute(boolean publish) {
		try {
			String news = collect();
			if (IsEmpty.string(news)) {
				log.error("empty result");
				return;
			}
			String body = splitBody(news);
			if (publish) {
				publish(title(body), decorate(body));
			} else {
				log.info("title : {}, body : {}", title(body), decorate(body));
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
