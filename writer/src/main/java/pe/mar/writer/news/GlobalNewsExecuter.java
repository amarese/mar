package pe.mar.writer.news;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import pe.mar.writer.news.collector.BingNewsCollector;
import pe.mar.writer.news.collector.DaumNewsCollector;
import pe.mar.writer.news.collector.MsnNewsCollector;
import pe.mar.writer.news.collector.NateNewsCollector;
import pe.mar.writer.news.collector.NaverNewsCollector;
import pe.mar.writer.news.collector.ZumNewsCollector;

@Service
public class GlobalNewsExecuter {
	@Inject
	private NaverNewsCollector naverNewsCollector;
	@Inject
	private NateNewsCollector nateNewsCollector;
	@Inject
	private DaumNewsCollector daumNewsCollector;
	@Inject
	private ZumNewsCollector zumNewsCollector;
	@Inject
	private MsnNewsCollector msnNewsCollector;
	@Inject
	private BingNewsCollector bingNewsCollector;

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
		if ("zum".equals(source)) {
			this.zumNewsCollector.execute(true);
		}
		if ("msn".equals(source)) {
			this.msnNewsCollector.execute(true);
		}
		if ("bing".equals(source)) {
			this.bingNewsCollector.execute(true);
		}
	}
}
