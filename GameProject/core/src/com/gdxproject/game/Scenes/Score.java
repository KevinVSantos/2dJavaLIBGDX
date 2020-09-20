
package com.gdxproject.game.Scenes;

import java.io.Serializable;

public class Score implements Serializable{
    public String nickname;
    public int score;
    
    public Score(String nickname, int score){
        this.nickname = nickname;
        this.score = score;
    }
}
