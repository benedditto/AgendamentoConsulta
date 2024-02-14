package med.voll.api.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import med.voll.api.domain.usuario.Usuario;

/*
 * para que o spring carregue essa classe e também
 * para indicar que é uma classe que prestará 
 * um serviço, use a anotação @Service
 */
    @Service
    public class TokenService {
        /*
         * anotação para que o atributo "chavesecreta" obtenha o valor que 
         * está armazenado no arquivo Application.properties 
         */
        @Value("${api.security.token.secret}")
        private String chaveSecreta; 

        public String gerarToken(Usuario usuario){

            //caso queira verificar se o Spring leu a chave secreta do arquivo Application.properties    
            // System.out.println(chaveSecreta);

            try {
                var algoritmo  = Algorithm.HMAC256(chaveSecreta);
                return JWT.create()
                    .withIssuer("API Voll.med")
                    .withSubject(usuario.getLogin()) //como cada usuário pode ter um token, o usuário é passado por parâmetro na assinatura do método
                    .withExpiresAt(dataExpiracao())
                    .sign((Algorithm) algoritmo);
            } catch (JWTCreationException exception){
                throw new RuntimeException("erro ao gerar token jwt", exception);
            }
        }

        private Instant dataExpiracao() {  //Instant é de uma API do Java 8, e retorna uma data   
            return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
        }    

        /*
         * método para para validar o token, extrair e devolver 
         * o subject(usuário) do token
         */
        public String getSubject(String tokenJWT) {
        try {
                var algoritmo  = Algorithm.HMAC256(chaveSecreta);
                return JWT.require(algoritmo)
                .withIssuer("API Voll.med")
                .build()
                .verify(tokenJWT)  //até este ponto, estamos verificando se o token é válido com o algoritmo e com o Issuer configurados no token
                .getSubject(); // se chegou neste ponto é porque o token é valido, e agora pegamos o subject.
            } catch (JWTVerificationException exception){
                /*
                 *  se caiu no catch é porque ocorreu uma exceção
                 * ou é inválido
                 * ou está incorreto
                 * ou o token expirou
                */
                throw new RuntimeException("Token JWT inválido ou expirado" +tokenJWT);
             }
        }
    }