package com.gdxproject.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdxproject.game.GameProject;
import com.gdxproject.game.Menu.LevelSelect;
import com.gdxproject.game.Sprites.Enemies.EnemyB;
import com.gdxproject.game.Sprites.Enemies.EnemyBF;
import com.gdxproject.game.Sprites.Items.Book;
import com.gdxproject.game.Sprites.Items.PlayerRun;

public class SuccessScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private OrthographicCamera gamecam;

    private GameProject game;
    private int slevel;
    
    private Book book ;
    private PlayerRun run ;
    private EnemyBF eb ;
    private float angle;
    
  //Musicas
    private Music music1;
    private Music music2;
    
    
    String nickname;
    
    public SuccessScreen(GameProject game,int level,String nick){
        this.game = game;
        slevel = level;
        nickname=nick;

      //inicializa a camera que irá seguir junto ao personagem
        gamecam = new OrthographicCamera();
        
        
        viewport = new FitViewport(GameProject.V_WIDTH, GameProject.V_HEIGHT, gamecam);
        
        stage = new Stage(viewport, ((GameProject) game).batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("GG", font);
        Label playAgainLabel = new Label("Click to Play Again", font);

        table.add(gameOverLabel).expandX();
        table.row();
        table.add(playAgainLabel).expandX().padTop(10f);

        stage.addActor(table);
        
        
        
        //inicializa e seta nossa gamecam para ser centralizada corretamente no inicio do mapa
        gamecam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        
        book = new Book(this, GameProject.V_WIDTH, GameProject.V_HEIGHT);
        run = new PlayerRun(this, GameProject.V_WIDTH, GameProject.V_HEIGHT);   
        eb = new EnemyBF(this, GameProject.V_WIDTH, GameProject.V_HEIGHT);
        
        eb.setPosition(GameProject.V_WIDTH/8, GameProject.V_HEIGHT/12);
        run.setPosition(GameProject.V_WIDTH/2.1f, GameProject.V_HEIGHT/12);
        book.setPosition(GameProject.V_WIDTH/1.3f, GameProject.V_HEIGHT/6);
        
        music1 = GameProject.manager.get("audio/voice/ofendendo.mp3", Music.class);
        music1.play();
        
      //  music2 =  GameProject.manager.get("audio/voice/feliz.mp3", Music.class);
        music1.setOnCompletionListener(new Music.OnCompletionListener() {

            @Override
            public void onCompletion(Music music) {
            	//music2.play();

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
            game.setScreen(new LevelSelect((GameProject) game, nickname));
            dispose();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        
        game.batch.setProjectionMatrix(gamecam.combined);
        
        game.batch.begin();        
       
        /*   book.setOrigin(0, 0);
        book.setCenter(GameProject.V_WIDTH/1.3f, GameProject.V_HEIGHT/8);
        angle=angle+4;
        book.setRotation(angle);*/
        
      book.setPosition(book.getX(), book.getY());
        book.setOrigin(book.getWidth() / 2, book.getHeight() / 2);
        angle=angle-4;
        book.setRotation(angle);
        
        
        
        book.draw(game.batch);   
        run.draw(game.batch);        
        eb.draw(game.batch);        
        game.batch.end();
        
        
        
        stage.draw();
    }
    
    public void update(float dt){
    	book.update(dt);
    	run.update(dt);
    	eb.update(dt);
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