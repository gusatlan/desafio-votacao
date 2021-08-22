package br.com.oneguy.votacao.repositories;

import br.com.oneguy.votacao.domain.persistence.AssociateVotePU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAssociateVoteRepository extends JpaRepository<AssociateVotePU, String> {
}
