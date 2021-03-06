package com.gdxproject.system.game.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdxproject.system.game.GameProject;
import com.gdxproject.system.game.Menu.GameButton;
import com.gdxproject.system.game.Screens.PlayScreen;

public class LevelSelect implements Screen {
	/*
	 * Screen responsavel por criar uma matriz de bot�es relacionadas
	 *  a fases do jogo em conjunto com uma matriz visual dos bot�es
	 * */
	private TextureRegion reg;
	GameProject game;
	private GameButton[][] buttons;
	 private OrthographicCamera gamecam;
	 private Viewport viewport;
	 
	 private int level;
	 
	 Stage stage;
	 Group background;
		Group foreground;
		BackMenu back;
		
		//Musicas
	    private Music music1;
	    private String nickname;
	    
	public LevelSelect(GameProject game, String nick) {
		this.game = game;
	        nickname = nick;
	    //inicializa a camera que ir� seguir junto ao personagem
	    gamecam = new OrthographicCamera();
	        
	    viewport = new FitViewport(GameProject.V_WIDTH, GameProject.V_HEIGHT, gamecam);
		
		reg = new TextureRegion(new Texture(Gdx.files.internal("lvl_image.jpg")), 0, 0, 1600, 800);
	
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
		background.setBounds(100, 100, 100, 100);
		background.setPosition(100, 100);
		stage.addActor(background);
		background.addActor(new Image(reg)); // your background image here.

		back = new BackMenu(this, GameProject.V_WIDTH, GameProject.V_HEIGHT);
		
		music1 = GameProject.manager.get("audio/music/song_story_requiem_for_a_dream.mp3", Music.class);
        music1.play();
        music1.setLooping(true);
        
	}
	
	public void handleInput() {
	}
	
	public void update(float dt) {
		
		back.update(dt);
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
					music1.stop();
					game.setScreen(new PlayScreen((GameProject) game, level, nickname));
					//gsm.setState(GameStateManager.PLAY);
				}
			}
		}
		
		gamecam.update();
		
	}
	
	public void render(float delta) {
		
		
		update(delta);
    	
       Gdx.gl.glClearColor(0, 0, 0, 1);
       Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
       
       
        game.batch.setProjectionMatrix(gamecam.combined);
        
        game.batch.begin();        
        back.draw(game.batch);
        game.batch.end();
        
        
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
