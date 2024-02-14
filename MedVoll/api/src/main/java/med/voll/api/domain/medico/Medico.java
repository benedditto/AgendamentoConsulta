package med.voll.api.domain.medico;

import med.voll.api.domain.endereco.Endereco;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * para transformar a classe Medico.java se transformar em uma
 * entidade JPA e fazer o mapeamento corretamente, é necessário 
 * adicionar as anotações
 * 
 */
@Table(name="medicos")
@Entity(name="Medico")
//colocou a anotação do Lombok (dependência) para gerar getters and setters automaticamente
@Getter //gera os getters
@NoArgsConstructor //gera o construtor da classe
@AllArgsConstructor //gera um construtor para obter todos os campos
@EqualsAndHashCode(of = "id") //para gerar os equals and hashcodes apenas do atributo "id" 
public class Medico {

    public Medico(DadosCadastroMedico dados) { //Construtor que irá receber o DTO
        //Atribuição dos atributos
       /*
        * campo adicionado para a funcionalidade de
        * exclusão lógica de registros
        * no momento do cadastro ele já deve receber o valor "true", pois
        * estamos cadastrando o médico
        */
        this.ativo = true; 
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.crm = dados.crm();
        this.especialidade=dados.especialidade();
        this.endereco = new Endereco(dados.endereco()); //instanciou o objeto Endereco(), e criou um construtor dentro deste, para receber os dados do endereço (DTO)
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String telefone;
    private String crm;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade; //classe Enum

    @Embedded  //não será criada uma tabela de Endereço e fazer os relacionamentos,
                //será usado o esquema de "Embeddable Attribute" da JPA . Considerará que os 
                // campos de Endereço fazem parte da mesma tabela de médicos.
                // deve-se colocar a notação "Embeddable" na classe "Endereco"
    private Endereco endereco;  //não temos essa classe, teremos que criar.

    /*
     * campo adicionado para a funcionalidade de
     * exclusão lógica de registros
     */
    private Boolean ativo;

    public void atualizarInformacoes(@Valid DadosAtualizacaoMedico dados) {
        /*
         * atualizando os campos com os novos dados que estão chegando
         */
        //primeiro verifica se o dados que chegou não é nulo, para depois atualizar
        if (dados.nome() != null){
            this.nome = dados.nome();
        }
        if (dados.telefone()!=null){
            this.telefone=dados.telefone();
        }
        
        if(dados.endereco()!=null){
            //endereço é um objeto, vamos criar um método para atualizar os endereços, na classe endereço
            this.endereco.atualizarInformacoes(dados.endereco());

        }

    }

    public void excluir() {
        this.ativo=false;
    }

}
