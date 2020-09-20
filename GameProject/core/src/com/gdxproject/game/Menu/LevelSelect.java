package com.gdxproject.game.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdxproject.game.Menu.GameButton;
import com.gdxproject.game.Screens.PlayScreen;
import com.gdxproject.game.GameProject;

public class LevelSelect implements Screen {
	
	private TextureRegion reg;
	GameProject game;
	private GameButton[][] buttons;
	 private OrthographicCamera gamecam;
	 private Viewport viewport;
	 
	 private int level;
	 
	 Stage stage;
	 Group background;
		Group foreground;
	
	public LevelSelect(GameProject game) {
		this.game = game;
	        
		//Gdx.input.getInputProcessor().keyUp(keycode);
	    //inicializa a camera que irá seguir junto ao personagem
	    gamecam = new OrthographicCamera();
	        
	    viewport = new FitViewport(GameProject.V_WIDTH, GameProject.V_HEIGHT, gamecam);
		
		reg = new TextureRegion(new Texture(Gdx.files.internal("bgs.jpg")), 0, 0, 400, 228);
	
		TextureRegion buttonReg = new TextureRegion(new Texture(Gdx.files.internal("hud.png")), 0, 0, 32, 32);
		buttons = new GameButton[5][5];
		for(int row = 0; row < buttons.length; row++) {
			for(int col = 0; col < buttons[0].length; col++) {
				buttons[row][col] = new GameButton(buttonReg, 80 + col * 40, 200 - row * 40, gamecam);
				buttons[row][col].setText(row * buttons[0].length + col + 1 + "");
			}
		}
		gamecam.setToOrtho(false, GameProject.V_WIDTH, GameProject.V_HEIGHT);
		
		
		stage = new Stage();

		background = new Group();
		background.setBounds(0, 0, GameProject.V_WIDTH, GameProject.V_HEIGHT);
		background.setPosition(100, 100);
	//	foreground = new Group();
	//	foreground.setBounds(0, 0, GameProject.V_WIDTH, GameProject.V_HEIGHT);

		// Notice the order
		stage.addActor(background);
	//	stage.addActor(foreground);

	//	foreground.addActor(new Actor());
		// Or anything else you want to add like you normally would to the stage. 

		background.addActor(new Image(reg)); // your background image here.

		
		//gamecamsetBounds(0,GameProject.V_WIDTH, GameProject.V_HEIGHT);
	        
	      //inicializa e seta nossa gamecam para ser centralizada corretamente no inicio do mapa
	      //  gamecam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
	        
	}
	
	public void handleInput() {
	}
	
	public void update(float dt) {
		
		handleInput();
		
		for(int row = 0; row < buttons.length; row++) {
			for(int col = 0; col < buttons[0].length; col++) {
				buttons[row][col].update(dt);				
				if(buttons[row][col].isClicked()) {
					Gdx.app.log("BUTTON", "playButton Pressed");
					level = row * buttons[0].length + col + 1;
					String s =Integer.toString(level);
					GameProject.manager.get("hit.wav", Music.class).play();
					//buttons[row][col].setClicked();
					Gdx.app.log("BUTTON", s);
					game.setScreen(new PlayScreen((GameProject) game, level));
					//gsm.setState(GameStateManager.PLAY);
				}
			}
		}
		
		gamecam.update();
		
	}
	
	public void render(float delta) {
		
		
		update(delta);
    	
    	
      /*  if(Gdx.input.justTouched()) {
        	game.setScreen(new PlayScreen((GameProject) game));
            dispose();
        }*/
       Gdx.gl.glClearColor(0, 0, 0, 1);
       Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
       
       
      // stage.draw();
        game.batch.setProjectionMatrix(gamecam.combined);
        
        game.batch.begin();        
       
      //  enemyb.draw(game.batch);  
        game.batch.draw(reg, 0, 0); 
        game.batch.end();
        

     /*   gamecam.position.x = stage.getHeight();
    	gamecam.position.y = stage.();*/
        
		for(int row = 0; row < buttons.length; row++) {
			for(int col = 0; col < buttons[0].length; col++) {
				buttons[row][col].render(game.batch);
			}
		}
		
	}
	
	public void dispose() {
		// everything is in the resource manager com.neet.blockbunny.handlers.Content
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	
}
