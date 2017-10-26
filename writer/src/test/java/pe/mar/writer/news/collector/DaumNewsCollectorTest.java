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
public class DaumNewsCollectorTest {
	@Inject
	DaumNewsCollector collector;

	@Test
	public void splitBody() throws Exception {
		List<News> news = collector.splitBody(
				FileUtils.readFileToString(FileUtils.getFile("src", "test", "resources", "daum.html"), "UTF-8"));
		assertEquals("중국 공산당대회 폐막", news.get(0).getTitle());
		assertEquals("케냐 대통령 선거", news.get(1).getTitle());
		assertEquals("카탈루냐, 분리독립 추진", news.get(2).getTitle());
		assertEquals("연합뉴스", news.get(0).getMainArticle().getMedium());
		assertEquals("뉴시스", news.get(2).getSubArticles().get(2).getMedium());
		assertEquals(3, news.get(0).getSubArticles().size());
	}

	@Test
	public void execute() {
		collector.execute(false);
	}
}
