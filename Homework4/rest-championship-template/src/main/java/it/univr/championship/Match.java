package it.univr.championship;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Match {

    public enum MatchResult {A_WINNER, B_WINNER, TIE}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String teamA;
    private Integer teamAScore;
    private String teamB;
    private Integer teamBScore;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamA() {
        return teamA;
    }

    public void setTeamA(String teamA) {
        this.teamA = teamA;
    }

    public Integer getTeamAScore() {
        return teamAScore;
    }

    public void setTeamAScore(Integer teamAScore) {
        this.teamAScore = teamAScore;
    }

    public String getTeamB() {
        return teamB;
    }

    public void setTeamBName(String teamB) {
        this.teamB = teamB;
    }

    public Integer getTeamBScore() {
        return teamBScore;
    }

    public void setTeamBScore(Integer teamBScore) {
        this.teamBScore = teamBScore;
    }

    public MatchResult computeResult() {
        if (teamAScore > teamBScore) {
            return MatchResult.A_WINNER;
        } else if (teamBScore > teamAScore) {
            return MatchResult.B_WINNER;
        } else {
            return MatchResult.TIE;
        }
    }
}
