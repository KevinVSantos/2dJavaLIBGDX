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
import com.gdxproject.entity.game.Sprites.Enemies.EnemyBF;
import com.gdxproject.entity.game.Sprites.Items.Book;
import com.gdxproject.entity.game.Sprites.Items.PlayerRun;
import com.gdxproject.system.game.GameProject;
import com.gdxproject.system.game.Menu.LevelSelect;

/** 
 * Classe respons�vel pela tela de Sucesso no final do jogo.
 * */
public class SuccessScreen implements Screen {
	
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
     * Vari�vel respons�vel pelo primeiro �udio que toca.
     * */
    private Music music1;
    
    /**
     * Vari�vel respons�vel pelo segundo �udio que toca.
     * */
    private Music music2;
    
    /**
     * Vari�vel respons�vel pelos pontos que o jogador adquire.
     * */
    private int slevel;
    
    /**
     * Vari�vel respons�vel pelo nome do jogador no jogo.
     * */
    String nickname;
    
    /**
     * Respons�vel por  trazer os frames da anima��o carregados para serem exibidos.
     * */
    private Book book ;
    
    /**
     * Respons�vel por  trazer os frames da anima��o carregados para serem exibidos.
     * */
    private PlayerRun run ;
    
    /**
     * Respons�vel por  trazer os frames da anima��o carregados para serem exibidos.
     * */
    private EnemyBF eb ;
    
    /**
     * Respons�vel por guardar o �ngulo que uma imagem ir� rotacionar.
     * */
    private float angle;
    
    /**
     * Construtor da classe.
     * <br>
     * Nela � poss�vel ver os valores sendo iniciados, como o de viewport, stage e gamecam.
     * <br>
     * A vari�vel de m�sica recebe o endere�o e o nome da m�sica selecionada juntamente com a classe respons�vel por carrega-la(Music.class)
     * */
    public SuccessScreen(GameProject game,int level,String nick){
        this.game = game;
        slevel = level;
        nickname=nick;

      //inicializa a camera que ir� seguir junto ao personagem
        gamecam = new OrthographicCamera();
        
        
        viewport = new FitViewport(GameProject.V_WIDTH, GameProject.V_HEIGHT, gamecam);
        
        stage = new Stage(viewport, ((GameProject) game).batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("!!GG!!", font);
        Label playAgainLabel = new Label("clique na tela para voltar ao menu", font);

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
        
        music2 =  GameProject.manager.get("audio/music/Paradise.mp3", Music.class);
        music2.play();

        
    }

    @Override
    public void show() {

    }

    /**
     * M�todo respons�vel por desenhar na tela.
     * */
    @Override
    public void render(float delta) {
    	
 	update(delta);       
        
        if(Gdx.input.justTouched()) {
        	try {
    			Thread.sleep(200);
    		} catch (InterruptedException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	music2.stop();
            game.setScreen(new LevelSelect((GameProject) game, nickname));
            dispose();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);  
                 
        book.setPosition(book.getX(), book.getY());
        book.setOrigin(book.getWidth() / 2, book.getHeight() / 2);
        angle=angle-4;
        book.setRotation(angle);
                
        ArrayList<Sprite> sprites = new ArrayList<Sprite>();
        sprites.add(book);
        sprites.add(eb);
        sprites.add(run);
        
        DrawScreenComponent.DrawScreen(gamecam, sprites, stage);
        
    }
    
    /**
     * M�todo respons�vel por atualizar o que ser� exibido.
     * */
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

    /**
     * M�todo respons�vel por liberar recursos.
     * */
    @Override
    public void dispose() {
        stage.dispose();
    }
}