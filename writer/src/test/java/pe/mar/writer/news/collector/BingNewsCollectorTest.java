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
public class BingNewsCollectorTest {
	@Inject
	BingNewsCollector collector;

	@Test
	public void splitBody() throws Exception {
		List<News> news = collector.splitBody(
				FileUtils.readFileToString(FileUtils.getFile("src", "test", "resources", "bing.html"), "UTF-8"));
		assertEquals("중국서 韓단체관광 상품 7개월만에 등장…'사드 금한령' 풀리나", news.get(0).getSubArticles().get(0).getTitle());
		assertEquals("http://www.yonhapnews.co.kr/international/2017/10/26/0601020000AKR20171026047000083.HTML",
				news.get(0).getSubArticles().get(0).getLink());
		assertEquals("연합뉴스", news.get(0).getSubArticles().get(0).getMedium());
		assertEquals(
				"https://www.bing.com/th?id=ON.3DDBE724E591212F0AE52FE5EAAC730F&amp;pid=News&amp;w=204&amp;h=115&amp;c=7&amp;rs=2&amp;qlt=90",
				news.get(0).getSubArticles().get(0).getImage());
		assertEquals(20, news.get(0).getSubArticles().size());
	}

	@Test
	public void execute() {
		collector.execute(false);
	}
}
