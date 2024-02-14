package med.voll.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosAtualizacaoMedico(
    @NotNull //campo obrigatório
    Long id, 
    String nome, 
    String telefone, 
    DadosEndereco endereco) {

}
