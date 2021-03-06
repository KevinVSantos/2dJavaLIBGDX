package com.gdxproject.system.game.Screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdxproject.component.game.Tools.DrawScreenComponent;
import com.gdxproject.entity.game.Sprites.Items.IntroStory;
import com.gdxproject.system.game.GameProject;

/** 
 * Classe respons�vel pela tela de introdu��o com a hist�ria do jogo.
 * */
public class IntroStoryScreen implements Screen {
	
	/**
	 * Gerencia uma c�mera e determina como as coordenadas mundiais s�o mapeadas para a tela.
	 * */
    private Viewport viewport;
    
    /**
     * Vari�vel respons�vel por lidar com a janela de visualiza��o e distribui eventos de entrada.
     * */
    private Stage stage;
    
    /**
     * Variaveis basicas da Playscreen.
     * */
    private OrthographicCamera gamecam;
    
    /**
     * Respons�vel por trazer caracter�sticas do jogo.
     * */
    private GameProject game;
    
    /**
     * Respons�vel por  trazer os frames carregados para serem exibidos.
     * */
    private IntroStory introStory ;
    

    /**
     * Vari�vel respons�vel pela m�sica de fundo.
     * */
    private Music music1;

    /**
     * Construtor da classe.
     * <br>
     * Nela � poss�vel ver os valores sendo iniciados, como o de viewport, stage e gamecam.
     * <br>
     * A vari�vel de m�sica recebe o endere�o e o nome da m�sica selecionada juntamente com a classe respons�vel por carrega-la(Music.class)
     * */
    public IntroStoryScreen(GameProject game){
        this.game = game;
        
        
      //inicializa a camera que ir� seguir junto ao personagem
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
        
        /**
         * A m�sica � iniciada.
         * */
        music1.play();
        
        /**
         * M�todo chamada quando a m�sica termina de tocar.
         * */
        music1.setOnCompletionListener(new Music.OnCompletionListener() {

            @Override
            public void onCompletion(Music music) {
            	closeScreen();            }
        });   
    }

    @Override
    public void show() {

    }
    
    /**
     * M�todo respons�vel por encerrar a janela e chamar outra.
     * */
    public void closeScreen() {
    	game.setScreen(new MenuScreen((GameProject) game));
        dispose();    	
    }

    /**
     * M�todo respons�vel por desenhar na tela.
     * */
    @Override
    public void render(float delta) {
    	
    	update(delta);
    	
    	
        if(Gdx.input.justTouched()) {
        	music1.stop();
        	closeScreen();
        }
       Gdx.gl.glClearColor(0, 0, 0, 1);
       Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
       ArrayList<Sprite> sprites = new ArrayList<Sprite>();
       sprites.add(introStory); 
       
       DrawScreenComponent.DrawScreen(gamecam, sprites, stage);  
    
    }
    
    /**
     * M�todo respons�vel por atualizar o que ser� exibido.
     * */
    public void update(float dt){
    	introStory.update(dt);
    	gamecam.position.x = introStory.getX() + (GameProject.V_WIDTH / 2);
    	gamecam.position.y = introStory.getY() + (GameProject.V_HEIGHT / 2);
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

    /**
     * M�todo respons�vel por liberar recursos.
     * */
    @Override
    public void dispose() {
        stage.dispose();
       
    }
}