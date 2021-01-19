package hu.idomsoft.secondmicroservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.mavenproject1.OkmanyDTO;

@SpringBootApplication
@RestController
public class SecondMicroserviceApplication {
	public static String dateFormat;
	public static void main(String[] args) {
		SpringApplication.run(SecondMicroserviceApplication.class, args);
	}
	@Autowired
	public void setDateFormat(@Value("${spring.jackson.date-format}") String dateFormat) {
		this.dateFormat=dateFormat;
	}
	
    
    @PostMapping("/")
    OkmanyDTO post(@RequestBody Okmany o){
        return o.getOkmanyDTO();
    }

}
