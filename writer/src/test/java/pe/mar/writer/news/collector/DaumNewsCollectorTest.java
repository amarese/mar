package pe.mar.writer.news.collector;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pe.mar.writer.news.collector.DaumNewsCollector;

@RunWith(SpringRunner.class)
@SpringBootTest	
public class DaumNewsCollectorTest {
	@Inject
	DaumNewsCollector collector;
	@Test
	public void execute() {
		collector.execute(false);
	}
}
