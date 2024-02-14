package med.voll.api.infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.UsuarioRepository;

@Component
public class SecurityFilter extends OncePerRequestFilter{

    /*
     * para podermos usar o método getSubject() que está
     * na classe TokenService.java
     * declaramos a classe TokenService como um atributo, e 
     * pedimos para o Spring injetar a classe com a anotação
     * @Autowired
     */
    @Autowired
    private TokenService tokenService;

    //para injetar o repository neste filtro, para permitir uma consulta no BD
    @Autowired
    private UsuarioRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException
    {
        //varíável que recebe o token a partir de um método que extrai o token
        var tokenJWT = recuperarToken(request);
        //System.out.println(tokenJWT);
        System.out.println("FILTRO SENDO CHAMADO!!!!");

        /*
         * Valida, se existir, o token e recupera o seu subject
         */
        if (tokenJWT != null){
            var subject = tokenService.getSubject(tokenJWT);
            var usuario = repository.findByLogin(subject);
           
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null,usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println("LOGADO NA REQUISIÇÃO");
        }

        /*
         * para verificar se recuperou o subject do token
         */
        // System.out.println(subject);

        /*
         * para chamar o próximo filtro, encaminhando o request e o response
         */
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        //se existe o cabeçalho
        if (authorizationHeader!=null){
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
    
}
    

