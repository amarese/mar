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
public class NateNewsCollectorTest {
	@Inject
	NateNewsCollector collector;

	@Test
	public void splitBody() throws Exception {
		List<News> news = collector.splitBody(
				FileUtils.readFileToString(FileUtils.getFile("src", "test", "resources", "nate.html"), "UTF-8"));
		assertEquals("시진핑 2기 출범", news.get(0).getTitle());
		assertEquals("세계 주요 뉴스", news.get(1).getTitle());
		assertEquals("지구촌 뉴스", news.get(2).getTitle());
		assertEquals("연합뉴스", news.get(3).getMainArticle().getMedium());
		assertEquals(7, news.get(4).getSubArticles().size());
	}

	@Test
	public void execute() throws Exception {
		collector.execute(false);
	}
}
