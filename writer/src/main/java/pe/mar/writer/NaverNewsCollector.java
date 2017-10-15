package pe.mar.writer;

import javax.inject.Inject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import pe.mar.common.utils.IsEmpty;

@Service
public class NaverNewsCollector {
	@Inject
	CloseableHttpClient httpClient;

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

	public String convert(String news) throws Exception {
		String beginStr = "<div class=\"section_headline headline_subordi\">";
		int start = news.indexOf(beginStr);
		int end = start + news.substring(start).indexOf("</div>");
		String cutString = news.substring(start + beginStr.length(), end);
		String replacedString = cutString
				.replace("class=\"compo_headtxt\"",
						"style=\"margin: 0px 0px 7px; padding: 0px; font-size: 14px; line-height: 21px; color: #2f57aa; font-family: 돋움, Dotum, 'Apple SD Gothic Neo', Helvetica, sans-serif;\"")
				.replace("class=\"compo_linkhead\"",
						"style=\"color: #2f57aa; text-decoration-line: none; display: inline-block; vertical-align: middle;\"")
				.replace("class=\"compo_more\"",
						"style=\"color: #d63f18; text-decoration-line: none; display: inline-block; margin: 0px 0px -2px 7px; font-size: 11px; font-weight: normal; letter-spacing: -1px; vertical-align: middle;\"")
				.replace("class=\"photo\"",
						"style=\"margin: 1px 14px 0px 0px; padding: 0px; height: auto; font-size: 18px; font-weight: bold; letter-spacing: -1px; position: relative; float: left; display: inline-block;\"")
				.replace("class=\"sphoto1\"",
						"style=\"margin: 0px; padding: 0px; clear: both; float: left; width: 573px; color: #2f2f2f; font-family: 돋움, Dotum, 'Apple SD Gothic Neo', Helvetica, sans-serif; font-size: 12px;\"")
				.replace("class=\"slist1\"",
						"style=\"margin: 0px; padding: 6px 0px 0px; clear: both; color: #2f2f2f; font-family: 돋움, Dotum, 'Apple SD Gothic Neo', Helvetica, sans-serif; font-size: 12px;\"")
				.replace("class=\"r_ico r_vod_small\"",
						"style=\"background-image: url('http://static.news.naver.net/image/news/2017/09/13/sp_news.png'); background-size: 354px 328px; display: block; position: absolute; font-size: 0px; line-height: 0; vertical-align: middle; width: 26px; height: 26px; background-position: -160px -88px; left: 4px; bottom: 4px;\"")
				.replace("<li>",
						"<li  style=\"margin: 0px; padding: 0px 0px 5px 11px; list-style: none; line-height: 14px; background: url('http://static.news.naver.net/image/news/2009/ico_list_sub2.gif') 0px 2px no-repeat;\">")
				.replace("<dt>",
						"<dt style=\"margin: 0px; padding: 3px 0px 0px; height: 21px; font-size: 18px; font-weight: bold; letter-spacing: -1px;\">")
				.replaceAll("onError=\".+\"", "");
		return replacedString;

	}

	public void publish(String news) throws Exception {
		System.out.println(news);

	}

	public void execute() throws Exception {
		String news = collect();
		if (IsEmpty.string(news)) {
			return;
		}
		publish(convert(news));
	}
}
