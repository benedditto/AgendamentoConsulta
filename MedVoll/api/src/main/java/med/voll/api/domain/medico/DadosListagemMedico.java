package med.voll.api.domain.medico;
/*
 * DTO DadosListagemMedico que irá retornar os dados abaixo.
 * Especialidade é um ENUM
 * 
 */
public record DadosListagemMedico(Long id, String nome, String email, String crm, Especialidade especialidade, Boolean ativo) {
    //criação de um construtor capaz de receber como parâmetro um objeto do tipo medico
    public DadosListagemMedico(Medico medico){
             //chamamos o construtor do record, passando os parâmetros
            this(medico.getId(),medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade(), medico.getAtivo() );
    }
}
