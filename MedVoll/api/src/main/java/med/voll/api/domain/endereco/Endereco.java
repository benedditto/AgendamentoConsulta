package med.voll.api.domain.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
//colocou a anotação do Lombok (dependência) para gerar getters and setters automaticamente
@Getter //gera os getters
@NoArgsConstructor //gera o construtor da classe
@AllArgsConstructor //gera um construtor para obter todos os campos
public class Endereco {

    //construtor criado para receber os dados do Endereço
    public Endereco(DadosEndereco endereco) {
        this.bairro = endereco.bairro();
        this.cep=endereco.cep();
        this.cidade=endereco.cidade();
        this.complemento=endereco.complemento();
        this.numero=endereco.numero();
        this.uf=endereco.uf();
        this.logradouro=endereco.logradouro();
    }
    private String logradouro;
    private String bairro;
    private String cep;
    private String numero;
    private String complemento;
    private String cidade;
    private String uf;

    /*
     * método criado para atualizar o endereço do médico
     */
    public void atualizarInformacoes(DadosEndereco dados) {
        if(dados.bairro()!=null){
            this.bairro = dados.bairro();
        }

        if(dados.cep()!=null){
        this.cep=dados.cep();
        }
        if(dados.cidade()!=null){
        this.cidade=dados.cidade();
        }
        if(dados.complemento()!=null){
        this.complemento=dados.complemento();
        }
        if(dados.numero()!=null){
        this.numero=dados.numero();
        }
        if(dados.uf()!=null){
        this.uf=dados.uf();
        }
        if(dados.logradouro()!=null){
        this.logradouro=dados.logradouro();
        }
    }
}
