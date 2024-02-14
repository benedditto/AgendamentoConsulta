package med.voll.api.domain.paciente;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.endereco.Endereco;

@Table(name="pacientes")
@Entity(name="Paciente")
//Lombok
@Getter //gera os getters
@NoArgsConstructor //gera o construtor da classe
@AllArgsConstructor //gera um construtor para obter todos os campos
@EqualsAndHashCode(of = "id")
public class Paciente {
    public Paciente(DadosCadastroPaciente dados) {
        this.ativo = true;
        this.cpf = dados.cpf();
        this.email=dados.email();
        this.nome=dados.nome();
        this.telefone=dados.telefone();
        this.endereco = new Endereco(dados.endereco()); //instanciou a classe Endereco. Nesta há um construtor para receber os dados do endereço.
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String telefone;
    private String cpf;

    private Boolean ativo;

     @Embedded
    private Endereco endereco;
    /*
     * método criado para atualizar os dados dos pacientes
     */
    public void atualizarInformacoes(@Valid DadosAtualizacaoPaciente dados) {
    //primeiro verifica se o dado que chegou não é nulo, para depois atualizar
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
        this.ativo = false;
    }
    
}
