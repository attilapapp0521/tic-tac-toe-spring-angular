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

    /**
     *
     * @return Visszaadja az újonnan generált tic-tac-toe táblát
     */
    @GetMapping
    public ResponseEntity<GameInfoDto> newGame(){
        return new ResponseEntity<>(gameService.newGame(),HttpStatus.OK);
    }

    /**
     *
     * @param request frontendtől kapott a lépés megtételéhez is szükséges információkat tartalmazza.
     * @return Vissza adja a lépés utáni állapotot
     */
    @PostMapping
    public ResponseEntity<GameInfoDto> nextStep(@RequestBody GameInfoDto request) {
        GameInfoDto gameInfoDto = gameService.nextStep(request);

        if (request.getBoard() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(gameInfoDto, HttpStatus.OK);
    }

    /**
     *
     * @return Visszaadja a staisztika törölésekor lenullálozodott értékeket
     */
    @PutMapping
    public ResponseEntity<GameInfoDto> deleteAll(@RequestBody GameInfoDto gameInfoDto) {

        return new ResponseEntity<>(this.gameService.deleteAll(gameInfoDto), HttpStatus.OK);
    }


}
