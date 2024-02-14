package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.voll.api.domain.usuario.DadosAutenticacao;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.infra.security.DadosTokenJWT;
import med.voll.api.infra.security.TokenService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/* esse é o controller responsável pela autenticação dos usuários.
   essa classe é responsável por disparar o processo 
   de autenticação
*/

/*
 * mapeamento de uma controller
 */
@RestController
@RequestMapping("/login")  //url onde o usuário fará login
public class AutenticacaoController {
   
    @Autowired
    private AuthenticationManager manager;

    //injetamos um novo objeto para usar o método de criação de tokens
    @Autowired
    private TokenService tokenService;
   
    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid  DadosAutenticacao dados){
       try{
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = manager.authenticate(authenticationToken);
        
        //variável responsável em gerar o token
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
       } catch(Exception e){
        e.printStackTrace();
        return ResponseEntity.badRequest().body(e.getMessage());
       }
    }
}
