package it.univr.championship;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MatchRepository extends CrudRepository<Match, Long> {
	Iterable<Match> findAllByTeamAOrTeamB(String teamA, String teamB);
}
