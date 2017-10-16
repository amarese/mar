package pe.mar.writer;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(WriterApplication.class)
public class NaverNewsCollectorTest {
	@Inject
	NaverNewsCollector collector;

	@Test
	public void execute() throws Exception {
		collector.execute();
	}

	@Test
	public void title() throws Exception {
		String expected = "'北 도발' 美 입장·대응";
		String actual = collector
				.title("\n<h5 class=\"compo_headtxt\"><a href=\"http://news.naver.com/main/hotissue/sectionList.nhn?mid=hot&sid1=100&cid=1049580\" class=\"compo_linkhead\">"
						+ expected
						+ "</a><a href=\"http://news.naver.com/main/hotissue/sectionList.nhn?mid=hot&sid1=100&cid=1049580\" class=\"compo_more\">더보기</a></h5>\n어쩌구");
		assertEquals(expected, actual);
	}
}
