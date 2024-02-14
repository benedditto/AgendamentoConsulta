package med.voll.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * Inserção das NOTAÇÕES. A cada notação, o vscode insere, automaticamente
 * os imports acima.
 * Como estamos trabalhando com uma API Rest, e não com uma aplicação web, 
 * usamos a notação: @RestController
 * Outra notação que usamos para essa classe é @RequestMapping
 * ela indica qual é o mapeamento, isto é, qual é o URL que esse controller irá responder.
 * @RequestMapping("/hello") - indica que se chegar uma requisição para localhost:8080/hello - essa requisição irá cair neste controller
 * 
 * @GetMapping --> essa notação indica que quando chegar uma 
 * requisição para esse controller, e essa requisição for 
 * do tipo GET, execute o método "olaMundo()"
 * 
 * 
 */
@RestController 
@RequestMapping("/hello") 
public class HelloController { //classe construtor
    /* esse controller deve chamar algum método, 
    no caso, esse método é "olaMundo()"
    */
    @GetMapping 
    public String olaMundo(){
        return "Hello World";
    }

}
