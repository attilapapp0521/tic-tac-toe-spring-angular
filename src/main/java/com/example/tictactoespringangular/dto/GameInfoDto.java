package com.example.tictactoespringangular.dto;

import java.util.List;

public class GameInfoDto {
    private List<String> board;
    private Boolean playerOne;
    private Integer coordinate;
    private Integer condition;


    public List<String> getBoard() {
        return board;
    }

    public void setBoard(List<String> board) {
        this.board = board;
    }

    public Boolean isPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(Boolean playerOne) {
        this.playerOne = playerOne;
    }

    public Integer getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Integer coordinate) {
        this.coordinate = coordinate;
    }

    public Integer getCondition() {
        return condition;
    }

    public void setCondition(Integer condition) {
        this.condition = condition;
    }
}
