package pe.mar.writer.news.collector;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import lombok.Getter;
import pe.mar.common.utils.IsNotEmpty;

@Service
public class NaverNewsCollector extends NewsCollectorBase {
	@Getter
	String url = "http://news.naver.com/main/main.nhn?mode=LSD&mid=shm&sid1=104";

	List<News> splitBody(String html) {
		Pattern pattern = Pattern.compile(
				"<div class=\"section_headline headline_subordi\">([ \\s]*<h5[^>]*><a href=\"(.+?)\"[^>]*>(.+?)</a>.*?</h5>.*?<ul class=\"slist1\">.+?</ul>[ \\s]*)</div>",
				Pattern.DOTALL);
		Matcher matcher = pattern.matcher(html);
		List<News> result = Lists.newArrayList();
		while (matcher.find()) {
			String cutString = matcher.group(1);
			String link = matcher.group(2);
			String title = matcher.group(3);
			News news = new News(title.trim(), cutString.trim());
			news.setLink(link);

			Article mainArticle = mainArticle(cutString);
			news.setMainArticle(mainArticle);
			List<Article> subArticles = subArticles(cutString);
			news.setSubArticles(subArticles);
			result.add(news);
		}
		return result;
	}

	Article mainArticle(String cutString) {

		Pattern pattern = Pattern.compile("<dt class=\"photo\">.+?<img src=\"(.+?)\".+?", Pattern.DOTALL);
		Matcher matcher = pattern.matcher(cutString);
		String image = "";
		if (matcher.find()) {
			image = matcher.group(1);
		}
		Pattern pattern2 = Pattern.compile(
				"<dt>[\\s]*<a.+?href=\"(.+?)\" >[\\s]*(.+?)</a>.+?<dd.+?>(.+?)<span class=\"writing\">(.+?)</span>",
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
				"<li>.+?<a.+?href=\"(.+?)\".+?>(.+?)</a>.+?<span class=\"writing\">(.+?)</span>.+?</li>",
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
		return title;
	}

	String decorateBody(News news) {
		String title = String.format("<h3>%s</h3>", news.getTitle());
		String intro = String.format("현재 국제사회에서 \"<a href=\"%s\">%s</a>\" 이슈가 화제입니다.<br>", news.getLink(),
				news.getTitle());
		Article mainArticle = news.getMainArticle();

		String image = IsNotEmpty.string(mainArticle.getImage())
				? String.format("<a href=\"%s\"><img src=\"%s\" align=\"left\"></a>", mainArticle.getLink(),
						mainArticle.getImage())
				: "";

		String main = String.format(
				"<p>최근 %s 에서는  \"<a href=\"%s\">%s</a>\" 의 제목으로 아래와 같은 보도를 전했습니다.</p>" + "%s" + "%s",
				mainArticle.getMedium(), mainArticle.getLink(), mainArticle.getTitle(), image,
				mainArticle.getContent());

		String sub = "<br><br>그 외에도 각종 언론사들은 다음과 같은 보도를 이어나갔습니다.<br><br>";
		for (Article article : news.getSubArticles()) {
			sub += String.format("<a href=\"%s\">%s</a> - %s<br>\n", article.getLink(), article.getTitle(),
					article.getMedium());
		}
		String result = String.format("%s\n%s\n%s\n%s", title, intro, main, sub);
		return result;
	}
}
