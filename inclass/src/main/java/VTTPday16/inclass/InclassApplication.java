package VTTPday16.inclass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InclassApplication {

	public static void main(String[] args) {
		SpringApplication.run(InclassApplication.class, args);
	}

}

//no extra dependency needed from spring-boot initializer
//just like jedis, we need to insert the Json P default provider dependency inside the pom.xml file