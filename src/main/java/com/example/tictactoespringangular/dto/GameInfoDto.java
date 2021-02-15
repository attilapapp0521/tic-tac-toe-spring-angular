package com.example.tictactoespringangular.dto;

import java.util.List;

public class GameInfoDto {
    private List<String> board;
    private Boolean playerOne;
    private Integer coordinate;
    private Integer condition;
    private Integer wonP1;
    private Integer wonP2;
    private Integer draw;
    private Long gameId;


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

    public Boolean getPlayerOne() {
        return playerOne;
    }

    public Integer getWonP1() {
        return wonP1;
    }

    public void setWonP1(Integer wonP1) {
        this.wonP1 = wonP1;
    }

    public Integer getWonP2() {
        return wonP2;
    }

    public void setWonP2(Integer wonP2) {
        this.wonP2 = wonP2;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }
}
