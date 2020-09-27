package com.gdxproject.system.game.Menu;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;

public class BBInputProcessor extends InputAdapter {
	
	/**
	 * Método para movimento do mouse.
	 * @param int x posição x
	 * @param int y posição y
	 */
	public boolean mouseMoved(int x, int y) {
		BBInput.x = x;
		BBInput.y = y;
		return true;
	}
	
	/**
	 * Método para arraste do mouse.
	 * @param int x posição x
	 * @param int y posição y
	 * @param int pointer
	 */
	public boolean touchDragged(int x, int y, int pointer) {
		BBInput.x = x;
		BBInput.y = y;
		BBInput.down = true;
		return true;
	}
	
	/**
	 * Método para click down do mouse.
	 * @param int x posição x
	 * @param int y posição y
	 * @param int pointer
	 * @param int button
	 */
	public boolean touchDown(int x, int y, int pointer, int button) {
		BBInput.x = x;
		BBInput.y = y;
		BBInput.down = true;
		return true;
	}
	
	/**
	 * Método para click up do mouse.
	 * @param int x posição x
	 * @param int y posição y
	 * @param int pointer
	 * @param int button
	 */
	public boolean touchUp(int x, int y, int pointer, int button) {
		BBInput.x = x;
		BBInput.y = y;
		BBInput.down = false;
		return true;
	}
	
	/**
	 * Método para down tecla.
	 *  @param int k tecla
	 */
	public boolean keyDown(int k) {
		if(k == Keys.Z) BBInput.setKey(BBInput.BUTTON1, true);
		if(k == Keys.X) BBInput.setKey(BBInput.BUTTON2, true); 
		return true;
	}
	
	/**
	 * Método para up tecla.
	 *  @param int k tecla
	 */
	public boolean keyUp(int k) {
		if(k == Keys.Z) BBInput.setKey(BBInput.BUTTON1, false);
		if(k == Keys.X) BBInput.setKey(BBInput.BUTTON2, false); 
		return true;
	}
	
}
