package com.gdxproject.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdxproject.game.GameProject;
import com.gdxproject.game.Sprites.Enemies.EnemyB;

public class GameOverScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
	//Variaveis basicas da Playscreen
    private OrthographicCamera gamecam;
    private GameProject game;
    
    private EnemyB enemyb ;
    

    //Musicas
    private Music music1;
    private Music music2;
    private int slevel;
    
    String nickname;

    public GameOverScreen(GameProject game,int level, String nick){
        this.game = game;
        slevel = level;
        nickname= nick;
      //inicializa a camera que irá seguir junto ao personagem
        gamecam = new OrthographicCamera();
        
        
        viewport = new FitViewport(GameProject.V_WIDTH, GameProject.V_HEIGHT, gamecam);
        stage = new Stage(viewport, ((GameProject) game).batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("GAME OVER", font); 
        Label giveUpLabel = new Label("Não desista", font);
        Label humanityLabel = new Label("A humanidade depende de você", font);
        Label playAgainLabel = new Label("Clique na tela para tentar de novo", font);

        table.add(gameOverLabel).expandX();
        table.row();
        table.add(giveUpLabel).expandX().padTop(2f);
        table.row();
        table.add(humanityLabel).expandX().padTop(2f);
        table.row();
        table.row();
        table.add(playAgainLabel).expandX().padTop(10f);

        stage.addActor(table);
        
        
        
      //inicializa e seta nossa gamecam para ser centralizada corretamente no inicio do mapa
        gamecam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        
        enemyb = new EnemyB(this, GameProject.V_WIDTH, GameProject.V_HEIGHT);
         
        
          
        music1 = GameProject.manager.get("audio/voice/hahaha.mp3", Music.class);
        music1.play();
        
        music2 =  GameProject.manager.get("audio/voice/idiota.mp3", Music.class);
        music1.setOnCompletionListener(new Music.OnCompletionListener() {

            @Override
            public void onCompletion(Music music) {
            	music2.play();

            }
        });

        
        
       
        
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
    	
    	update(delta);
    	
    	
        if(Gdx.input.justTouched()) {
            game.setScreen(new PlayScreen((GameProject) game, slevel, nickname));
            dispose();
        }
       Gdx.gl.glClearColor(0, 0, 0, 1);
       Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        
        game.batch.setProjectionMatrix(gamecam.combined);
        
        game.batch.begin();        
       
        enemyb.draw(game.batch);        
        game.batch.end();
       
        
      //Seta nosso batch para desenhar o que o a camera do HUD vê.
       //game.batch.setProjectionMatrix(stage.getCamera().combined);
        
        stage.draw();
    
    }
    
    public void update(float dt){
    	enemyb.update(dt);
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