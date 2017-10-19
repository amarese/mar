package pe.mar.writer;

import javax.inject.Inject;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WriterApplication implements ApplicationRunner {
	@Inject
	private NaverNewsCollector naverNewsCollector;
	@Inject
	private NateNewsCollector nateNewsCollector;
	@Inject
	private DaumNewsCollector daumNewsCollector;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		for (String name : args.getNonOptionArgs()) {
			if ("naver".equals(name)) {
				this.naverNewsCollector.execute(true);
			}
			if ("nate".equals(name)) {
				this.nateNewsCollector.execute(true);
			}
			if ("daum".equals(name)) {
				this.daumNewsCollector.execute(true);
			}
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(WriterApplication.class, args);
	}
}
