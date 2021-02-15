package com.example.tictactoespringangular.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int wonP1;
    private int wonP2;
    private int draw;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getWonP1() {
        return wonP1;
    }

    public void setWonP1() {
        this.wonP1++;
    }

    public int getWonP2() {
        return wonP2;
    }

    public void setWonP2() {
        this.wonP2++;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw() {
        this.draw++;
    }
}
