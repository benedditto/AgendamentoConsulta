package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/* para que ela se transforme em um Repository, essa classe 
* precisa herdar da interface do Spring Data "JpaRepository"
* temos que lhe passar dois generics, os dois tipos de objetos.
* O primeiro é a entidade, que este repository irá trabalhar,
* e o segundo é o tipo do atributo da chave primária.
* Assim nossa interface herda todos métodos e atributos 
* da interface JpaRepository. 
* Agora podemos usar esse repository no controller MedicoController.
*/
public interface MedicoRepository extends JpaRepository<Medico, Long> {

    /*
     * o método abaixo foi criado para que, na listagem dos médicos,
     * apareçam apenas os que tiverem o atributo "ativo" == true.
     * este método devolve um Page de Médicos
     */
    Page<Medico> findAllByAtivoTrue(Pageable paginacao);
    
}
