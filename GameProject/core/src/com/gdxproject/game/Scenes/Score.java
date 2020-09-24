
package com.gdxproject.game.Scenes;

import java.io.Serializable;

/**
 * Pontua��o dos jogardores.
 */
public class Score implements Serializable{
	
	/**
	 * Nome do jogador.
	 */
    public String nickname;
    
    /**
     * Pontua��o do jogador
     */
    public int score;
    
    /**
     * Cria uma pontua��o para o jogardor.
     * @param nickname - Nome do jogardor.
     * @param score - Pontua��o do jogador.
     */
    public Score(String nickname, int score){
        this.nickname = nickname;
        this.score = score;
    }
}
