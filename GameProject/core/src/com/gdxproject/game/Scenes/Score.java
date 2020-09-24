
package com.gdxproject.game.Scenes;

import java.io.Serializable;

/**
 * Pontuação dos jogardores.
 */
public class Score implements Serializable{
	
	/**
	 * Nome do jogador.
	 */
    public String nickname;
    
    /**
     * Pontuação do jogador
     */
    public int score;
    
    /**
     * Cria uma pontuação para o jogardor.
     * @param nickname - Nome do jogardor.
     * @param score - Pontuação do jogador.
     */
    public Score(String nickname, int score){
        this.nickname = nickname;
        this.score = score;
    }
}
