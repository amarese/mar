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
public class ZumNewsCollectorTest {
	@Inject
	ZumNewsCollector collector;

	@Test
	public void splitBody() throws Exception {
		List<News> news = collector.splitBody(
				FileUtils.readFileToString(FileUtils.getFile("src", "test", "resources", "zum.html"), "UTF-8"));
		assertEquals("中 제19차 당대회", news.get(0).getTitle());
		assertEquals("日 총선, 아베 자민당 압승", news.get(1).getTitle());
		assertEquals("스페인, 카탈루냐 자치정부 해산 결정", news.get(2).getTitle());
		assertEquals("연합뉴스", news.get(2).getSubArticles().get(2).getMedium());
		assertEquals(3, news.get(0).getSubArticles().size());
	}

	@Test
	public void execute() {
		collector.execute(false);
	}
}
