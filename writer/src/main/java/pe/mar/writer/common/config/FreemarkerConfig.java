package pe.mar.writer.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration
public class FreemarkerConfig {
	@Bean
	freemarker.template.Configuration freemarkerConfiguration() throws Exception {
		FreeMarkerConfigurationFactoryBean factoryBean = new FreeMarkerConfigurationFactoryBean();
		factoryBean.setTemplateLoaderPath("classpath:/templates/");
		factoryBean.setPreferFileSystemAccess(false);
		factoryBean.afterPropertiesSet();
		factoryBean.setDefaultEncoding("utf-8");

		return factoryBean.getObject();
	}
}
