package br.com.oneguy.votacao.repositories;

import br.com.oneguy.votacao.domain.persistence.AssociatePU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface IAssociateRepository extends JpaRepository<AssociatePU, String> {

    public Collection<AssociatePU> findByIdentification(final String identification);
}
