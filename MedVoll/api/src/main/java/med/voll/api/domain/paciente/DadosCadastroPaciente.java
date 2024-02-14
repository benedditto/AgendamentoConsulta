package med.voll.api.domain.paciente;

import med.voll.api.domain.endereco.DadosEndereco;

/*
 *  dados que este método irá receber:
 *  nome, e-mail, telefone, CPF e endereço completo.
 *  Todas informações são de preenchimento obrigatório, 
 *  exceto o número e complemento
 * 
 */

public record DadosCadastroPaciente(String nome, String email, String telefone, String cpf, DadosEndereco endereco ) {

}
