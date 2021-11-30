package it.univr.championship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class ChampionshipController {
	@Autowired
	private MatchRepository matchRepository;

	@PostMapping("/match")
	public Match createMatch(@RequestBody Match match) {
		matchRepository.save(match);

		return match;
	}

	@GetMapping("/match/{matchId}")
	public Optional<Match> getMatch(@PathVariable("matchId") long id) {
		return matchRepository.findById(id);
	}

	@DeleteMapping("/match/{matchId}")
	public Optional<Match> deleteMatch(@PathVariable("matchId") long id) {
		matchRepository.deleteById(id);

		return matchRepository.findById(id);
	}

	@PutMapping("/match/{matchId}")
	public Match updateMatch(
			@PathVariable("matchId") long id,
			@RequestBody Match match) {
		match.setId(id);
		matchRepository.save(match);

		return match;
	}

	@GetMapping("/matches")
	public Map<String, Iterable<Match>> getMatches() {
		Map<String, Iterable<Match>> res = new HashMap<>();
		res.put("matches", matchRepository.findAll());

		return res;
	}

	@GetMapping("/matches/{teamName}")
	public Map<String, Iterable<Match>> getAllMatchesOfTeam(@PathVariable("teamName") String teamName) {
		Map<String, Iterable<Match>> res = new HashMap<>();
		res.put("matches", matchRepository.findAllByTeamAOrTeamB(teamName, teamName));

		return res;
	}
}