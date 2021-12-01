package it.univr.championship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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

	@GetMapping("/ranking")
	public Map<String, String> getRanking() {
		Map<String, Integer> teams = new HashMap<>();

		for (String team : getAllTeams().get("teams")) {
			teams.put(team, 0);
		}

		Match.MatchResult result;

		for (Match m : getMatches().get("matches")) {
			result = m.computeResult();

			if (result == Match.MatchResult.A_WINNER) {
				teams.replace(m.getTeamA(), teams.get(m.getTeamA()) + 3);
			} else if (result == Match.MatchResult.B_WINNER) {
				teams.replace(m.getTeamB(), teams.get(m.getTeamB()) + 3);
			} else {
				teams.replace(m.getTeamA(), teams.get(m.getTeamA()) + 1);
				teams.replace(m.getTeamB(), teams.get(m.getTeamB()) + 1);
			}
		}

		Map<String, Integer> sortedTeams = teams.entrySet().stream().sorted(
				Map.Entry.comparingByValue(Comparator.reverseOrder())).collect(
				Collectors.toMap(
						Map.Entry::getKey,
						Map.Entry::getValue,
						(oldValue, newValue) -> oldValue, LinkedHashMap::new
				)
		);

		Map<String, String> res = new HashMap<>();

		// FIXME Da print il risultato è corretto ma quando esegue il test il valore è null
		int i = 1;
		for (String teamName : sortedTeams.keySet()) {
			res.put(i + ".team", teamName);
			res.put(i + ".points", sortedTeams.get(teamName).toString());

			// System.out.println(i + ".team: " + teamName);
			// System.out.println(i + ".points: " + sortedTeams.get(teamName));

			i++;
		}

		return res;
	}

	@GetMapping("/winner")
	public Map<String, String> getWinner() {
		Map<String, Integer> teams = new HashMap<>();

		for (String team : getAllTeams().get("teams")) {
			teams.put(team, 0);
		}

		Match.MatchResult result;

		for (Match m : getMatches().get("matches")) {
			result = m.computeResult();

			if (result == Match.MatchResult.A_WINNER) {
				teams.replace(m.getTeamA(), teams.get(m.getTeamA()) + 3);
			} else if (result == Match.MatchResult.B_WINNER) {
				teams.replace(m.getTeamB(), teams.get(m.getTeamB()) + 3);
			} else {
				teams.replace(m.getTeamA(), teams.get(m.getTeamA()) + 1);
				teams.replace(m.getTeamB(), teams.get(m.getTeamB()) + 1);
			}
		}

		Map<String, String> res = new HashMap<>();

		res.put(
				"winner",
				Collections.max(teams.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey()
		);

		return res;
	}

	@GetMapping("/teams")
	public Map<String, Iterable<String>> getAllTeams() {
		Map<String, Iterable<String>> res = new HashMap<>();
		Set<String> teams = new HashSet<>();

		matchRepository.findAll().forEach(match -> {
			teams.add(match.getTeamA());
			teams.add(match.getTeamB());
		});

		res.put("teams", teams);

		return res;
	}
}