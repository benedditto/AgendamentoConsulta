package med.voll.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //anotação da classe
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args); //esse método "run" que vai fazer a inicialização do projeto
		//isto é, esse método que vai inicializar o Tom Cat, vai ler as classes do projeto, vai fazer toda a inicialização do projeto
	}

}
