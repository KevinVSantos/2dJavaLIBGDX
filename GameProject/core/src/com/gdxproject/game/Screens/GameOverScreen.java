package com.gdxproject.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdxproject.game.GameProject;
import com.gdxproject.game.Sprites.Enemies.Enemy;
import com.gdxproject.game.Sprites.Enemies.EnemyB;
import com.gdxproject.game.Sprites.Items.Coin;
import com.gdxproject.game.Sprites.Items.Helicoptero;

public class GameOverScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
	//Variaveis basicas da Playscreen
    private OrthographicCamera gamecam;

    private GameProject game;
    
    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    private EnemyB enemyb ;
    

    //Musicas
    private Music music1;
    private Music music2;


    public GameOverScreen(GameProject game){
        this.game = game;
        
        
      //inicializa a camera que ir� seguir junto ao personagem
        gamecam = new OrthographicCamera();
        
        
        viewport = new FitViewport(GameProject.V_WIDTH, GameProject.V_HEIGHT, gamecam);
        stage = new Stage(viewport, ((GameProject) game).batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("GAME OVER", font);
        Label playAgainLabel = new Label("Click to Play Again", font);

        table.add(gameOverLabel).expandX();
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
            game.setScreen(new PlayScreen((GameProject) game));
            dispose();
        }
       Gdx.gl.glClearColor(0, 0, 0, 1);
       Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        
        game.batch.setProjectionMatrix(gamecam.combined);
        
        game.batch.begin();        
       
        enemyb.draw(game.batch);        
        game.batch.end();
       
        
      //Seta nosso batch para desenhar o que o a camera do HUD v�.
       //game.batch.setProjectionMatrix(stage.getCamera().combined);
        
        stage.draw();
    
    }
    
    public void update(float dt){
    	enemyb.update(dt);
    	/*gamecam.position.x = enemyb.getX();
    	gamecam.position.y = enemyb.getY();*/
    	//Atualiza nossa gamecam com as coordenadas corretas ap�s altera��o
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