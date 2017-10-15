package pe.mar.writer;

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
	public void test() throws Exception {
		collector.execute();
	}

}
