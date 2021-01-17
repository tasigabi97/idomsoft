package hu.idomsoft.secondmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.mavenproject1.OkmanyDTO;

@SpringBootApplication
@RestController
public class SecondMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecondMicroserviceApplication.class, args);
	}
	
    @PostMapping("/param")
    OkmanyDTO postParam(Okmany o){

        return o.getOkmanyDTO();
        
    }

}
