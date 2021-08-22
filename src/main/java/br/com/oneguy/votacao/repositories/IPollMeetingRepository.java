package br.com.oneguy.votacao.repositories;

import br.com.oneguy.votacao.domain.persistence.PollMeetingPU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPollMeetingRepository extends JpaRepository<PollMeetingPU, String> {
}
