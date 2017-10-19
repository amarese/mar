package pe.mar.writer;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NaverNewsCollectorTest {
	@Inject
	NaverNewsCollector collector;

	@Test
	public void decorate() throws Exception {
		String expected = "\n<h5 style=\"margin: 0px 0px 7px; padding: 0px; font-size: 14px; line-height: 21px; color: #2f57aa;\"><a href=\"http://news.naver.com/main/hotissue/sectionList.nhn?mid=hot&sid1=100&cid=1049580\" style=\"color: #2f57aa; text-decoration-line: none; display: inline-block; vertical-align: middle;\">'北 도발' 美 입장·대응</a><a href=\"http://news.naver.com/main/hotissue/sectionList.nhn?mid=hot&sid1=100&cid=1049580\" style=\"color: #d63f18; text-decoration-line: none; display: inline-block; margin: 0px 0px -2px 7px; font-size: 11px; font-weight: normal; letter-spacing: -1px; vertical-align: middle;\">더보기</a></h5>\n어쩌구";
		String actual = collector.decorateBody(
				"\n<h5 class=\"compo_headtxt\"><a href=\"http://news.naver.com/main/hotissue/sectionList.nhn?mid=hot&sid1=100&cid=1049580\" class=\"compo_linkhead\">'北 도발' 美 입장·대응</a><a href=\"http://news.naver.com/main/hotissue/sectionList.nhn?mid=hot&sid1=100&cid=1049580\" class=\"compo_more\">더보기</a></h5>\n어쩌구");
		assertEquals(expected, actual);
	}

	@Test
	public void execute() throws Exception {
		collector.execute(false);
	}
}
