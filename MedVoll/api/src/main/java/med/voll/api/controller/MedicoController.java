package med.voll.api.controller;

import med.voll.api.domain.medico.DadosAtualizacaoMedico;
import med.voll.api.domain.medico.DadosCadastroMedico;
import med.voll.api.domain.medico.DadosDetalhamentoMedico;
import med.voll.api.domain.medico.DadosListagemMedico;
import med.voll.api.domain.medico.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;

@RestController //com essa notação indicamos que estamos usando Spring. Essa classe é um controller
@RequestMapping("medicos") //mapeia a url "/medicos"
public class MedicoController {
    
    @Autowired //Notação para fazer a injeção de dependências, automaticamente
    private MedicoRepository repository; //Precisamos da classe Repository, então criamos este atributo.

    @PostMapping //esse método que vai receber os dados pelo método POST
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder){ //@RequestBody -> notação que indica que o parâmetro vem do corpo da requisição. Esse método recebe o parâmetro passado utilizando uma classe (DadosCadastroMedico) que foi chamada de "dados"
      var medico = new Medico(dados);
      repository.save(medico);
      var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
      return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    //método para listar os médicos
    /* ao contrário do método cadastrar, que não retorna nenhuma informação,
    *  esse método irá retornar. List, do Java Util.
    * O verbo do protocolo http que será utilizado (anotação usada) é o GET, pois esse
    * método não estará recebendo dados. 
    * O método "Listar" não está postando informações mas
    * devolvendo.
    * Não necessita da anotação @Transactional pois este método é de apenas
    * Leitura. (não está salvando, deletando ou alterando dados).
    * Vamos criar um DTO para retornar a lista de dados dos médicos: "DadosListagemMedico"
    */
    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size=10,sort={"nome"}) Pageable paginacao){
      var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new); //armazenou o que será retornado em uma variável
      return ResponseEntity.ok(page); //retornou a variável (as páginas com os dados dos médicos)   
    }


    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
      var medico = repository.getReferenceById(dados.id());
      medico.atualizarInformacoes(dados); 
      //neste método é interessante retornar a informação que foi atualizada, utilizando um DTO
      return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));//criar um DTO do objeto "medico"
    }

    /*
     * método para exclusão de registros
     * o id do registro a ser excluído virá na URL da requisição, por exemplo:
     * http://localhost:8080/medicos/3
     * 
     */
    @DeleteMapping("/{id}") //{id} --> id do registro. Colocado entre {} porque é um parâmetro dinâmico
    @Transactional
     public ResponseEntity excluir(@PathVariable Long id) { 
      var medico = repository.getReferenceById(id); //recuperou o médico do BD.
      //agora temos que setar o atribuito ativo para false
      medico.excluir();  //criou esse método na entidade médico
      return ResponseEntity.noContent().build();
     }

    @GetMapping("/{id}") //{id} --> id do registro. Colocado entre {} porque é um parâmetro dinâmico
     public ResponseEntity detalhar(@PathVariable Long id) { 
       var medico = repository.getReferenceById(id); //recuperou o médico do BD.
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
     }

  }
