package com.gdxproject.component.game.Tools;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.gdxproject.entity.game.Sprites.Enemies.EnemyB;
import com.gdxproject.entity.game.Sprites.Items.IntroDev;
import com.gdxproject.entity.game.Sprites.Items.IntroStory;
import com.gdxproject.entity.game.Sprites.Items.Menu;
import com.gdxproject.system.game.GameProject;

/**
 * Component para desenhar na tela.
 */
public class DrawScreenComponent {
		
	/**
	 * Desenha as Screens.
	 */
	public static void DrawScreen(OrthographicCamera gameCam, ArrayList<Sprite> sprites, Stage stage) {
		
		GameProject.instance.batch.setProjectionMatrix(gameCam.combined);
        
		GameProject.instance.batch.begin();        
       
		for(Sprite sprite : sprites)
			sprite.draw(GameProject.instance.batch);
		
        GameProject.instance.batch.end();
       //Seta nosso batch para desenhar o que o a camera do HUD vê.
       //game.batch.setProjectionMatrix(stage.getCamera().combined);
        
        stage.draw();
		
	}
	
	/**
	 * Desenha a PlayScreen.
	 */
	public static void DrawScreen(OrthographicCamera gameCam, Array<Sprite> sprites) {
		
		GameProject.instance.batch.setProjectionMatrix(gameCam.combined);
        
		GameProject.instance.batch.begin();        
       
		for(Sprite sprite : sprites)
			sprite.draw(GameProject.instance.batch);
		
        GameProject.instance.batch.end();
       //Seta nosso batch para desenhar o que o a camera do HUD vê.
       //game.batch.setProjectionMatrix(stage.getCamera().combined);
        
        
		
	}

}
