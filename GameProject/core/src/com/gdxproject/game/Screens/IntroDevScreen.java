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
import com.gdxproject.game.Sprites.Items.IntroDev;

/** 
 * Classe responsável pela tela de introdução com o nome dos desenvolvedores do jogo.
 * */
public class IntroDevScreen implements Screen {
	
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
    private IntroDev introDev;
    

    /**
     * Variável responsável pela música de fundo.
     * */
    private Music music1;
    
    /**
     * Construtor da classe.
     * <br>
     * Nela é possível ver os valores sendo iniciados, como o de viewport, stage e gamecam.
     * <br>
     * A variável de música recebe o endereço e o nome da música selecionada juntamente com a classe responsável por carrega-la(Music.class)
     * */
    public IntroDevScreen(GameProject game){
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
        
        introDev = new IntroDev(this.game, GameProject.V_WIDTH, GameProject.V_HEIGHT);
         
        
          
        music1 = GameProject.manager.get("audio/music/song_intro_daydreaming.mp3", Music.class);
        
        /**
         * A música é iniciada.
         * */
        music1.play();
        
        /**
         * Método chamado quando a música termina de tocar.
         * */
        music1.setOnCompletionListener(new Music.OnCompletionListener() {

            @Override
            public void onCompletion(Music music) {
            	closeScreen();
            }
        });
       
    }
    
    /**
     * Método responsável por encerrar a janela e chamar outra.
     * */
    public void closeScreen() {
    	game.setScreen(new IntroStoryScreen((GameProject) game));
        dispose();    	
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
        	music1.stop();
            closeScreen();
        }
       Gdx.gl.glClearColor(0, 0, 0, 1);
       Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        
        game.batch.setProjectionMatrix(gamecam.combined);
        
        game.batch.begin();        
       
        introDev.draw(game.batch);        
        game.batch.end();
       
        
      //Seta nosso batch para desenhar o que o a camera do HUD vê.
       //game.batch.setProjectionMatrix(stage.getCamera().combined);
        
        stage.draw();
    
    }
    
    /**
     * Método responsável por atualizar o que será exibido.
     * */
    public void update(float dt){
    	introDev.update(dt);
    	gamecam.position.x = introDev.getX() + (GameProject.V_WIDTH / 2);
    	gamecam.position.y = introDev.getY() + (GameProject.V_HEIGHT / 2);
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