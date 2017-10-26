package pe.mar.writer.news.collector;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import lombok.Getter;
import pe.mar.common.utils.IsNotEmpty;

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

	String decorateBody(News news) {
		String title = String.format("<h3>%s</h3>", news.getTitle());
		String intro = "";
		if ("세계 주요 뉴스".equals(news.getTitle()) || "지구촌 뉴스".equals(news.getTitle())) {
			String date = new SimpleDateFormat("yyyy년 MM월 dd일").format(new Date());
			intro = IsNotEmpty.string(news.getLink())
					? String.format("%s, \"<a href=\"%s\">%s</a>\" 입니다.<br>", date, news.getLink(), news.getTitle())
					: String.format("%s, \"%s\" 입니다.<br>", date, news.getTitle());
		} else {
			intro = IsNotEmpty.string(news.getLink()) ? String
					.format("최근 국제 정가에서는 \"<a href=\"%s\">%s</a>\" 관련 이슈가 화제다.<br>", news.getLink(), news.getTitle())
					: String.format("최근 국제 정가에서는 \"%s\" 관련 이슈가 화제다.<br>", news.getTitle());
		}
		Article mainArticle = news.getMainArticle();

		String image = IsNotEmpty.string(mainArticle.getImage())
				? String.format("<a href=\"%s\"><img src=\"%s\" align=\"left\"></a>", mainArticle.getLink(),
						mainArticle.getImage())
				: "";

		String main = String.format("<p>최근 %s 에서는  \"<a href=\"%s\">%s</a>\" 의 제목으로 아래와 같은 보도를 전했다.</p>" + "%s" + "%s",
				mainArticle.getMedium(), mainArticle.getLink(), mainArticle.getTitle(), image,
				mainArticle.getContent());

		String sub = "<br><br>그 외에도 각종 언론사들은 다음과 같은 보도를 이어나갔다.<br><br>";
		for (Article article : news.getSubArticles()) {
			sub += String.format("<a href=\"%s\">%s</a> - %s<br>\n", article.getLink(), article.getTitle(),
					article.getMedium());
		}
		String result = String.format("%s\n%s\n%s\n%s", title, intro, main, sub);
		return result;
	}
}
