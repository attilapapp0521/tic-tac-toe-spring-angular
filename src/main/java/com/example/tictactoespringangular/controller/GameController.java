package com.example.tictactoespringangular.controller;

import com.example.tictactoespringangular.dto.GameInfoDto;
import com.example.tictactoespringangular.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "http://localhost:4200")
public class GameController {
    private GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public ResponseEntity<GameInfoDto> newGame(){
        return new ResponseEntity<>(gameService.newGame(),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GameInfoDto> nextStep(@RequestBody GameInfoDto request) {
        GameInfoDto gameInfoDto = gameService.nextStep(request);

        if (request.getBoard() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(gameInfoDto, HttpStatus.OK);
    }
}
