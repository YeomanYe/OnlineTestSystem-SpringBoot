package cn.edu.tjut.ots;

import cn.edu.tjut.ots.filter.MyFilter;
import cn.edu.tjut.ots.listener.ApplicationListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

import javax.servlet.Filter;
import javax.servlet.ServletContextListener;

@SpringBootApplication
public class OnlineTestSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineTestSystemApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean someFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(myFilter());
		registration.addUrlPatterns("/*");
		registration.addInitParameter("excludeStr", ".jpg;.png;.js;.css;.ico");
		registration.setName("myFilter");
		return registration;
	}

	@Bean
	public ServletListenerRegistrationBean someListenerRegistrationBean(){
		ServletListenerRegistrationBean listenerRegistrationBean = new ServletListenerRegistrationBean();
		listenerRegistrationBean.setListener(myListener());
		return listenerRegistrationBean;
	}

	@Bean
	@Order(1)
	public Filter myFilter(){
		return new MyFilter();
	}

	@Bean
	public ServletContextListener myListener(){
		return new ApplicationListener();
	}
}
