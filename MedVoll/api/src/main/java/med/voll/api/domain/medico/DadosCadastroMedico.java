package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;
/*
 * classe que recebe os parâmetros do JSON
 * 
 * Para receber as Especialidades utilizou um parâmetro do tipo ENUM 
 * 
 * O endereço, como contém alguns campos (nome da rua, número, cep), é um 
 * parâmetro do tipo RECORD - então foi criada uma classe do tipo RECORD para receber o endereço
 * 
 * 
 */
public record DadosCadastroMedico(

                @NotBlank //não podem ser vazios e nem nulos
                String nome, 

                @NotBlank
                @Email //verifica se tem o formato de um endereço de e-mail
                String email, 
                @NotBlank //campo obrigatório
                String telefone,
                @NotBlank
                @Pattern(regexp = "\\d{4,6}") //Expressão regular: campo tipo dígito, de 4 a 6 dígitos
                String crm, 
                
                @NotNull //é obrigatório - Não usou @NotBlank pois esse campo não é uma String
                Especialidade especialidade, 
                
                @NotNull
                @Valid //para validar os campos que estão dentro deste outro DTO
                DadosEndereco endereco ) { //Especialidade é um ENUM

}
