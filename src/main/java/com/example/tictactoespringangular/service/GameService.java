package com.example.tictactoespringangular.service;

import com.example.tictactoespringangular.domain.Game;
import com.example.tictactoespringangular.dto.GameInfoDto;
import com.example.tictactoespringangular.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class GameService {
    private final GameInfoDto gameInfoDto;
    private static final int SUM_FIELD = 9;
    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
        this.gameInfoDto = new GameInfoDto();
    }

    /**
     *
     * @return A metódus felelős azért hogy új, üres tic-tac-toe tábla jöjjön létre,
     * ezt tárolódik a GameInfoDto-ban.
     */
    public GameInfoDto newGame() {
        List<String> newBoard = new ArrayList<>();

        for (int i = 0; i < SUM_FIELD; i++) {
            newBoard.add("");
        }
        this.gameInfoDto.setBoard(newBoard);
        this.gameInfoDto.setPlayerOne(true);
        this.gameInfoDto.setCondition(0);
        reset();

        return gameInfoDto;
    }

    /**
     *
     * @param request A frontend-től kapott játékkal kapcsolatos információkat hordozza.
     * @return A következő szimbólum (X vagy O) elhelyezésének logikáját tartalmazza.
     */

    public GameInfoDto nextStep(GameInfoDto request) {

        gameInfoDto.setBoard(playerMove(request.getCoordinate(),
                request.getBoard(), request.isPlayerOne()));
        gameInfoDto.setCondition(checkWinCondition(request.getBoard()));
        gameInfoDto.setPlayerOne(nextPlayer(request.isPlayerOne()));
        reset();

        if (!request.getBoard().contains("") && gameInfoDto.getCondition() != 1 &&
                request.getCoordinate() != 2) {
            gameInfoDto.setCondition(3);
            save(gameInfoDto.getCondition(), gameInfoDto.getGameId());
            return gameInfoDto;
        }

        save(gameInfoDto.getCondition(),gameInfoDto.getGameId());
        return gameInfoDto;
    }

    /**
     *
     * @param coordinate tartalmazza a szimbolum elhelyezésének pozícióját.
     * @param board maga a tábla ahol a szimbólumok tárolva vannak.
     * @param isFirstPlayer boolean, azt vizgálja, hogy az első játékos lépett-e.
     * @return Itt történik a szimbólum felhelyezése a táblára.
     */
    private List<String> playerMove(int coordinate, List<String> board,
                                    boolean isFirstPlayer) {

        if (coordinate < 0 || coordinate > SUM_FIELD - 1) {
            return null;
        }

        board.set(coordinate, isFirstPlayer ? "X" : "O");
        return board;
    }

    /**
     *
     * @return Itt történik annak a vizsgálata,hogy a pályán történt változás után van-e nyertes.
     */
    private int checkWinCondition(List<String> board) {
        int result = 0;
        for (int i = 0; i < board.size(); i++) {
            if (i % 3 == 0) {
                result = separatePlayer(i, 1, board);
            }
            if (result == 0 || result == 3) {
                if (i / 3 == 0) {
                    result = separatePlayer(i, 3, board);
                }
                if (result == 0 && (i == 0 || i == 2)) {
                    result = separatePlayer(0, 4, board);
                    if (result == 1 || result == 2) {
                        return result;
                    }

                    result = separatePlayer(2, 2, board);
                }
            }
            if (result != 0) {
                return result;
            }
        }

        return result;
    }

    /**
     *
     * @param index A tábla egy mezőjének helyét jelzi
     * @param step Ez egy segéd változó, ami a logikához szükséges
     * @return Segéd metódús ami vagy visszintesen vagy függőlegesen vagy átlósan vizsgálja,
     * hogy van-e összesen 3 azonos szimbólumú elem.
     */
    private int separatePlayer(int index, int step, List<String> board) {

        if (board.get(index).equals("X") && board.get(index + step).equals("X")
                && board.get(index + step * 2).equals("X")) {
            return 1;
        } else if (board.get(index).equals("O") && board.get(index + step).equals("O")
                && board.get(index + step * 2).equals("O")) {
            return 2;
        }

        return 0;
    }

    /**
     *
     * @return Ez a metódus felelős azért, hogy a másik játékos következzen
     */
    private boolean nextPlayer(boolean isFirstPlayer) {
        return !isFirstPlayer;
    }

    /**
     *
     * @param condition Azt mutatja meg, hogy valamelyik játékos megnyerte-e a játékot,
     *                 illetve döntetlen lett-e.
     * @param id Az adatbázisban tárolt statisztika id-ja
     * @return Tárolja az adatbázisban a jelenlegi statisztikát, ennek az értékét tárolja
     * a DTO-ban is, hogy megjeleníthető legyen.
     */
    public GameInfoDto save(int condition, Long id) {
        Game game = getGameById(id);

        if(game == null){
            game = new Game();
        }

        if (condition == 1) {
            game.setWonP1();
            this.gameRepository.save(game);
            this.gameInfoDto.setGameId(game.getId());
            this.gameInfoDto.setWonP1(game.getWonP1());
        } else if (condition == 2) {
            game.setWonP2();
            this.gameRepository.save(game);
            this.gameInfoDto.setGameId(game.getId());
            this.gameInfoDto.setWonP2(game.getWonP2());
        } else if (condition == 3) {
            game.setDraw();
            this.gameRepository.save(game);
            this.gameInfoDto.setGameId(game.getId());
            this.gameInfoDto.setDraw(game.getDraw());
        }

        return  gameInfoDto;
    }

    /**
     *
     * @return Abban az esetben van szükség erre a metódusra, ha töröltük a statisztikát és új játékot
     * kezdünk akkor megfelelően megjelenítse a kinullázott értékeket
     */
    private GameInfoDto reset(){
        List<Game> playersList = this.gameRepository.findAll();

        if(playersList.isEmpty()){
            this.gameInfoDto.setWonP1(0);
            this.gameInfoDto.setWonP2(0);
            this.gameInfoDto.setDraw(0);
        }

        return gameInfoDto;
    }

    /**
     *
     * @return Itt történik meg a törlés az adatbázisból
     */
    public GameInfoDto deleteAll(GameInfoDto gameInfoDto) {
        this.gameRepository.deleteAll();

        gameInfoDto.setWonP1(0);
        gameInfoDto.setWonP2(0);
        gameInfoDto.setDraw(0);

        return gameInfoDto;
    }


    /**
     *
     * @return Vissza adja az id paraméterben megadott azonosítójú statiszikát.
     * Alapvetően csak egy értéke lehet az adatbásinak, de ez azért szükséges, mert törlés után
     * létrehozott id is változik.
     */
    private Game getGameById(Long id){
        return gameRepository.getGameById(id);
    }
}
