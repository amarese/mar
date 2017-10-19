package pe.mar.writer;

import javax.inject.Inject;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import pe.mar.writer.news.GlobalNewsExecuter;

@SpringBootApplication
public class WriterApplication implements ApplicationRunner {
	@Inject
	private GlobalNewsExecuter globalNewsPublisher;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		for (String name : args.getNonOptionArgs()) {
			this.globalNewsPublisher.execute(name);
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(WriterApplication.class, args);
	}
}
