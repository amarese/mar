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
public class NaverNewsCollectorTest {
	@Inject
	NaverNewsCollector collector;

	@Test
	public void splitBody() throws Exception {
		List<News> news = collector.splitBody(
				FileUtils.readFileToString(FileUtils.getFile("src", "test", "resources", "naver.html"), "UTF-8"));
		assertEquals("시진핑 2기 출범", news.get(0).getTitle());
		assertEquals("'트럼프 美 대통령' 행보는", news.get(1).getTitle());
		assertEquals("'北 도발' 美 입장·대응", news.get(2).getTitle());
		assertEquals("YTN", news.get(2).getMainArticle().getMedium());
		assertEquals(10, news.get(0).getSubArticles().size());
	}

	@Test
	public void execute() throws Exception {
		collector.execute(false);
	}
}
