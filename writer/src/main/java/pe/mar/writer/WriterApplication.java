package pe.mar.writer;

import javax.inject.Inject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WriterApplication implements CommandLineRunner {
	@Inject
	private NaverNewsCollector naverNewsCollector;

	@Override
	public void run(String... args) {
		this.naverNewsCollector.execute(true);
	}

	public static void main(String[] args) {
		SpringApplication.run(WriterApplication.class, args);
	}
}
