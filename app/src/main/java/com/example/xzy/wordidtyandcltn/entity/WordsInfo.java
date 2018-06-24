package com.example.xzy.wordidtyandcltn.entity;

/**
 * Created by xzy on 6/24/18.
 */

public class WordsInfo {
    private String word;
    private int top_left_X ;
    private int top_left_Y ;
    private int right_bottom_X ;
    private int right_bottom_Y ;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getTop_left_X() {
        return top_left_X;
    }

    public void setTop_left_X(int top_left_X) {
        this.top_left_X = top_left_X;
    }

    public int getTop_left_Y() {
        return top_left_Y;
    }

    public void setTop_left_Y(int top_left_Y) {
        this.top_left_Y = top_left_Y;
    }

    public int getRight_bottom_X() {
        return right_bottom_X;
    }

    public void setRight_bottom_X(int right_bottom_X) {
        this.right_bottom_X = right_bottom_X;
    }

    public int getRight_bottom_Y() {
        return right_bottom_Y;
    }

    public void setRight_bottom_Y(int right_bottom_Y) {
        this.right_bottom_Y = right_bottom_Y;
    }

}
