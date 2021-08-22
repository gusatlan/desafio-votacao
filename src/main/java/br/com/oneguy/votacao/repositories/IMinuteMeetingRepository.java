package br.com.oneguy.votacao.repositories;

import br.com.oneguy.votacao.domain.persistence.MinuteMeetingPU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMinuteMeetingRepository extends JpaRepository<MinuteMeetingPU, String> {
}
