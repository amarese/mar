package pe.mar.writer.news;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import pe.mar.writer.news.collector.DaumNewsCollector;
import pe.mar.writer.news.collector.NateNewsCollector;
import pe.mar.writer.news.collector.NaverNewsCollector;

@Service
public class GlobalNewsExecuter {
	@Inject
	private NaverNewsCollector naverNewsCollector;
	@Inject
	private NateNewsCollector nateNewsCollector;
	@Inject
	private DaumNewsCollector daumNewsCollector;

	public void execute(String source) {
		if ("naver".equals(source)) {
			this.naverNewsCollector.execute(true);
		}
		if ("nate".equals(source)) {
			this.nateNewsCollector.execute(true);
		}
		if ("daum".equals(source)) {
			this.daumNewsCollector.execute(true);
		}
	}
}
