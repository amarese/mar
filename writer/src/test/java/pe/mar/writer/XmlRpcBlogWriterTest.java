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
public class XmlRpcBlogWriterTest {
	@Inject
	XmlRpcBlogWriter writer;

	@Test
	public void test() throws Exception {
		String rs = writer.write("소식", "20171015", "IS의 상징적 수도 락까 함락 임박");
		System.out.println(rs);
	}

}
