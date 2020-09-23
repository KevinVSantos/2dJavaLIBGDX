package com.gdxproject.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdxproject.game.GameProject;
import com.gdxproject.game.Sprites.Items.IntroStory;

public class IntroStoryScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
	//Variaveis basicas da Playscreen
    private OrthographicCamera gamecam;

    private GameProject game;
    
    private IntroStory introStory ;
    

    //Musicas
    private Music music1;

    public IntroStoryScreen(GameProject game){
        this.game = game;
        
        
      //inicializa a camera que irá seguir junto ao personagem
        gamecam = new OrthographicCamera();
        
        
        viewport = new FitViewport(GameProject.V_WIDTH, GameProject.V_HEIGHT, gamecam);
        stage = new Stage(viewport, ((GameProject) game).batch);

        Table table = new Table();
        table.center();
        table.setFillParent(true);


        stage.addActor(table);
        
        
        
      //inicializa e seta nossa gamecam para ser centralizada corretamente no inicio do mapa
        gamecam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        
        introStory = new IntroStory(this.game, GameProject.V_WIDTH, GameProject.V_HEIGHT);
         
        
          
        music1 = GameProject.manager.get("audio/music/song_intro_daydreaming_story.mp3", Music.class);
        music1.play();
        
        music1.setOnCompletionListener(new Music.OnCompletionListener() {

            @Override
            public void onCompletion(Music music) {
            	closeScreen();            }
        });

        
        
       
        
    }

    @Override
    public void show() {

    }
    
    public void closeScreen() {
    	game.setScreen(new MenuScreen((GameProject) game));
        dispose();    	
    }

    @Override
    public void render(float delta) {
    	
    	update(delta);
    	
    	
        if(Gdx.input.justTouched()) {
        	music1.stop();
        	closeScreen();
        }
       Gdx.gl.glClearColor(0, 0, 0, 1);
       Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        
        game.batch.setProjectionMatrix(gamecam.combined);
        
        game.batch.begin();        
       
        introStory.draw(game.batch);        
        game.batch.end();
       
        
        
        stage.draw();
    
    }
    
    public void update(float dt){
    	introStory.update(dt);
    	gamecam.position.x = introStory.getX() + (GameProject.V_WIDTH / 2);
    	gamecam.position.y = introStory.getY() + (GameProject.V_HEIGHT / 2);
    	//Atualiza nossa gamecam com as coordenadas corretas após alteração
        gamecam.update();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
       
    }
}