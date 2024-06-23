package rs.ac.uns.acs.nais.exhibition_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ExhibitionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExhibitionServiceApplication.class, args);
	}

}
