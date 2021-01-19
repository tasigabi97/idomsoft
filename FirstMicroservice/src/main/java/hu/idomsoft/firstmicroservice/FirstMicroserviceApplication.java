package hu.idomsoft.firstmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.mavenproject1.OkmanyDTO;
import com.mycompany.mavenproject1.SzemelyDTO;


@SpringBootApplication
@RestController
public class FirstMicroserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(FirstMicroserviceApplication.class, args);
	}
	
    @PostMapping("/")
    SzemelyDTO post(@RequestBody Szemely sz){
        return sz.getSzemelyDTO();
    }
}
