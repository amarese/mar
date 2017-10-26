package pe.mar.writer.news.collector;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pe.mar.writer.news.collector.NewsCollectorBase.News;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MsnNewsCollectorTest {
	@Inject
	MsnNewsCollector collector;

	@Test
	public void splitBody() throws Exception {
		List<News> news = collector.splitBody(
				FileUtils.readFileToString(FileUtils.getFile("src", "test", "resources", "msn.html"), "UTF-8"));
		assertEquals("서울신문", news.get(0).getMainArticle().getMedium());
		assertEquals("도쿄 모터쇼 사로잡은 &#39;스스로 균형잡는 오토바이&#39;", news.get(0).getMainArticle().getTitle());
		assertEquals(29, news.get(0).getSubArticles().size());
		assertEquals("연합뉴스", news.get(0).getSubArticles().get(0).getMedium());
		assertEquals(
				"https://www.msn.com/ko-kr/news/world/%e2%80%9c%ed%95%9c%eb%af%b8-%ed%9b%88%eb%a0%a8%ec%9d%80-%ec%84%a0%ec%a0%9c%ed%83%80%ea%b2%a9%ea%b3%bc-%ed%95%b5%ec%a0%84%ec%9f%81-%ec%a4%80%eb%b9%84%e2%80%9d%e2%80%a6%ec%9c%a0%ec%97%94%ec%97%90-%ed%95%ad%ec%9d%98%ed%95%9c-%eb%b6%81%ed%95%9c/ar-AAu3BSt",
				news.get(0).getSubArticles().get(1).getLink());
		assertEquals(
				"http://img-s-msn-com.akamaized.net/tenant/amp/entityid/AAu3r2K.img?h=64&w=80&m=6&q=60&u=t&o=t&l=f&f=jpg",
				news.get(0).getSubArticles().get(2).getImage());
	}

	@Test
	public void execute() {
		collector.execute(false);
	}
}
