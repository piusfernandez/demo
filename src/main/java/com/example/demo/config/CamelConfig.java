package com.example.demo.config;

import java.util.concurrent.TimeUnit;

import org.apache.camel.ThreadPoolRejectedPolicy;
import org.apache.camel.spi.ThreadPoolProfile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfig {

	
/*	@Value("${esb.http.auth}")
	private String auth;

	@Value("${esb.http.username}")
	private String username;

	@Value("${esb.http.password}")
	private String password;*/

/*	@Bean
	CamelContextConfiguration contextConfiguration() {
		return new CamelContextConfiguration() {

			@Override
			public void beforeApplicationStart(CamelContext camelContext) {
				HttpComponent http = (HttpComponent) camelContext.getComponent("http");
				http.setHttpConfiguration(httpConfiguration());
			}

			@Override
			public void afterApplicationStart(CamelContext camelContext) {
			}
		};
	}*/

/*	private HttpConfiguration httpConfiguration() {
		HttpConfiguration httpConfiguration = new HttpConfiguration();
		httpConfiguration.setAuthMethod(auth);
		httpConfiguration.setAuthUsername(username);
		httpConfiguration.setAuthPassword(password);
		return httpConfiguration;
	}*/

/*	@Bean
	ThreadPoolProfile threadPoolProfile()	{
		return new ThreadPoolProfileBuilder("vehicleload")
				.poolSize(5)
				.maxPoolSize(10)
				.maxQueueSize(20)
				.build();
	}
*/	
	@Bean(name="defaultThreadPoolProfile")
	ThreadPoolProfile threadPoolProfile(){
		ThreadPoolProfile defaultThreadPoolProfile = new ThreadPoolProfile();
		defaultThreadPoolProfile.setDefaultProfile(true);
		defaultThreadPoolProfile.setId("defaultThreadPoolProfile");
		defaultThreadPoolProfile.setPoolSize(20);
		defaultThreadPoolProfile.setMaxPoolSize(100);
		defaultThreadPoolProfile.setMaxQueueSize(100); 
		defaultThreadPoolProfile.setTimeUnit(TimeUnit.SECONDS);
		defaultThreadPoolProfile.setKeepAliveTime(60 * 5L);
		defaultThreadPoolProfile.setRejectedPolicy(ThreadPoolRejectedPolicy.CallerRuns);
//		camelContext().getExecutorServiceManager().registerThreadPoolProfile(defaultThreadPoolProfile);
//		setDefaultThreadPoolProfile(defaultThreadPoolProfile);
		return defaultThreadPoolProfile;
	}
}
