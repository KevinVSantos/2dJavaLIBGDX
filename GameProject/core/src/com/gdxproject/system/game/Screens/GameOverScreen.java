package com.gdxproject.system.game.Screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdxproject.component.game.Tools.DrawScreenComponent;
import com.gdxproject.entity.game.Sprites.Enemies.EnemyB;
import com.gdxproject.system.game.GameProject;


/** 
 * Classe responsável pela tela de Game Over.
 * */
public class GameOverScreen implements Screen {
	
	/**
	 * Gerencia uma câmera e determina como as coordenadas mundiais são mapeadas para a tela.
	 * */
    private Viewport viewport;
    
    /**
     * Variável responsável por lidar com a janela de visualização e distribui eventos de entrada.
     * */
    private Stage stage;
    
    /**
     * Variaveis basicas da Playscreen.
     * */
    private OrthographicCamera gamecam;
    
    /**
     * Responsável por trazer características do jogo.
     * */
    private GameProject game;
    
    /**
     * Responsável por  trazer os frames carregados para serem exibidos.
     * */
    private EnemyB enemyb ;
    

    /**
     * Variável responsável pelo primeiro áudio que toca.
     * */
    private Music music1;
    
    /**
     * Variável responsável pelo segundo áudio que toca.
     * */
    private Music music2;
    
    /**
     * Variável responsável pelos pontos que o jogador adquire.
     * */
    private int slevel;
    
    /**
     * Variável responsável pelo nome do jogador no jogo.
     * */
    String nickname;

    /**
     * Construtor da classe.
     * <br>
     * Nela é possível ver os valores sendo iniciados, como o de viewport, stage e gamecam.
     * <br>
     * A variável de música recebe o endereço e o nome da música selecionada juntamente com a classe responsável por carrega-la(Music.class)
     * */
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
        
        /**
         * O áudio é iniciado.
         * */
        music1.play();
        
        music2 =  GameProject.manager.get("audio/voice/idiota.mp3", Music.class);
        
        /**
         * Método chamado quando o áudio termina de tocar.
         * */
        music1.setOnCompletionListener(new Music.OnCompletionListener() {

            @Override
            public void onCompletion(Music music) {
            	/**
                 * O segundo áudio é iniciado.
                 * */
            	music2.play();
            }
        });

        
        
       
        
    }

    @Override
    public void show() {

    }

    /**
     * Método responsável por desenhar na tela.
     * */
    @Override
    public void render(float delta) {
    	
    	update(delta);
    	
    	
        if(Gdx.input.justTouched()) {
            game.setScreen(new PlayScreen((GameProject) game, slevel, nickname));
            dispose();
        }
       Gdx.gl.glClearColor(0, 0, 0, 1);
       Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
       
       ArrayList<Sprite> sprites = new ArrayList<Sprite>();
       sprites.add(enemyb);
       
       DrawScreenComponent.DrawScreen(gamecam, sprites, stage);     
    }
    
    /**
     * Método responsável por atualizar o que será exibido.
     * */
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

    /**
     * Método responsável por liberar recursos.
     * */
    @Override
    public void dispose() {
        stage.dispose();
       
    }
}