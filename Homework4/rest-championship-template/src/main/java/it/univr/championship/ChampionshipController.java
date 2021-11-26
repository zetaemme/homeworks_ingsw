package it.univr.championship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ChampionshipController {

    @Autowired
    private MatchRepository matchRepository;


}