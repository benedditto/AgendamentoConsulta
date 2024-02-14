package med.voll.api.domain.medico;

import med.voll.api.domain.endereco.*;

public record DadosDetalhamentoMedico(Long id, String nome, String email, String crm, String telefone, Especialidade especialidade, Endereco endereco) {
    //construtor da classe, que irá receber o objeto medico que está vindo lá do controller MedicoController
    public DadosDetalhamentoMedico(Medico medico){
        /*
        * este construtor irá chamar o construtor principal  
        * deste record, passando os parâmetros
        */
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getTelefone(), medico.getEspecialidade(), medico.getEndereco());
    }
}
