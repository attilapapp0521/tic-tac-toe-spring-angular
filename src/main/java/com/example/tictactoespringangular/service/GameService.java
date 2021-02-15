package com.example.tictactoespringangular.service;

import com.example.tictactoespringangular.dto.GameInfoDto;
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

    @Autowired
    public GameService() {
        this.gameInfoDto = new GameInfoDto();
    }


    public GameInfoDto newGame() {
        List<String> newBoard = new ArrayList<>();

        for (int i = 0; i < SUM_FIELD; i++) {
            newBoard.add("");
        }
        this.gameInfoDto.setBoard(newBoard);
        this.gameInfoDto.setPlayerOne(true);
        this.gameInfoDto.setCondition(0);

        return gameInfoDto;
    }

    public GameInfoDto nextStep(GameInfoDto request) {

        gameInfoDto.setBoard(playerMove(request.getCoordinate(),
                request.getBoard(), request.isPlayerOne()));
        gameInfoDto.setCondition(checkWinCondition(request.getBoard()));
        gameInfoDto.setPlayerOne(nextPlayer(request.isPlayerOne()));

        if (!request.getBoard().contains("") && gameInfoDto.getCondition() != 1 &&
                request.getCoordinate() != 2) {
            gameInfoDto.setCondition(3);
            return gameInfoDto;
        }

        return gameInfoDto;
    }

    private List<String> playerMove(int coordinate, List<String> board,
                                    boolean isFirstPlayer) {

        if (coordinate < 0 || coordinate > SUM_FIELD - 1) {
            return null;
        }

        board.set(coordinate, isFirstPlayer ? "X" : "O");
        return board;
    }

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

    private boolean nextPlayer(boolean isFirstPlayer) {
        return !isFirstPlayer;
    }
}
