package pe.mar.writer.news.collector;

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
	public void execute() throws Exception {
		collector.execute(false);
	}
}
