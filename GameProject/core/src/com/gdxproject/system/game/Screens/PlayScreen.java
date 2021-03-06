package com.gdxproject.system.game.Screens;


import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdxproject.component.game.Scenes.Hud;
import com.gdxproject.component.game.Sprites.Structure.WorldContactListener;
import com.gdxproject.component.game.Tools.B2WorldCreatorComponent;
import com.gdxproject.component.game.Tools.DrawScreenComponent;
import com.gdxproject.entity.game.Sprites.Enemies.Enemy;
import com.gdxproject.entity.game.Sprites.Items.Coin;
import com.gdxproject.entity.game.Sprites.Items.Helicoptero;
import com.gdxproject.entity.game.Sprites.Player.Player;
import com.gdxproject.system.game.GameProject;

public class PlayScreen implements Screen {

    /**
     * Respons�vel por trazer caracter�sticas do jogo.
     * */
	private GameProject game;
	private TextureAtlas atlas;
    
	/**
     * Variavel basicas da Playscreen.
     * */
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    
	/**
     * Classe de pontua��o e controle de tempo.
     * */
    private Hud hud;
    
	/**
     * Variaveis do Mapa Tiled.
     * */
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    
	/**
     * Variaveis do Box2d.
     * */
    private World world;
    
	/**
     * Constru��o do mapa.
     * */
    private B2WorldCreatorComponent creator;

  //private Box2DDebugRenderer b2dr;
    
    //Sprites
    private Player player;
    
	/**
     * M�sicas.
     * */
    private Music music;

	/**
     * Vari�vel de estado.
     * */
    private int stateGame;
    
	/**
     * Vari�vel que carrega o atlas referente as texturas.
     * */
    public static int slevel;
    
	/**
     * Apelido do jogador.
     * */
    String nickname;
    
	public PlayScreen(GameProject game,int level, String nick) {
		stateGame = 0;
		nickname=nick;
		
		//carrega o atlas referente as texturas
		 slevel = level;
		 
		//recebe a classe principal do jogo
		this.game = game;
		
		//inicializa a camera que ir� seguir junto ao personagem
        gamecam = new OrthographicCamera();
        
        
        //Cria um FitViewport para manter a propor��o virtual apesar do tamanho da tela
        gamePort = new FitViewport(GameProject.V_WIDTH / GameProject.PPM, GameProject.V_HEIGHT / GameProject.PPM, gamecam);
        
       
        //Cria nossa HUD para pontua��o/timers/level informa�oes
        hud = new Hud(game.batch, nickname);
        
        //Carrega nosso mapa e configura nosso rederizador do mapa 
        maploader = new TmxMapLoader();
        
       // map = maploader.load("mario/level1.tmx");   
        map = maploader.load("map/map.tmx");
        
        renderer = new OrthogonalTiledMapRenderer(map, 1  / GameProject.PPM);
       
        //inicializa e seta nossa gamecam para ser centralizada corretamente no inicio do mapa
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        
        //gamecam.setToOrtho(false,GameProject.V_WIDTH/ GameProject.PPM,GameProject.V_HEIGHT/ GameProject.PPM);
        
         
        
        //cria nosso mundo Box2D, configura nenhuma gravidade para X, -10 gravidade em Y, e mantem os corpos em repouso
        world = new World(new Vector2(0, -10), true);
        
        //Permiss�o para debug lines do nosso mundo Box2D. 
        //b2dr = new Box2DDebugRenderer();     
        
        //Inicializa a constru��o do nosso tiled map
        creator = new B2WorldCreatorComponent(this);

        
        //Cria o jogador no nosso mundo
        player = new Player(this);
        
        //Seta as normas que ser�o utilizadas para o contato
        world.setContactListener(new WorldContactListener());
        
        
        
        //Defini a musica de fundo do jogo
        music = GameProject.manager.get("audio/music/Blinding_Lights.mp3", Music.class);
        music.setLooping(true);
        music.setVolume(0.3f);
        music.play();


	}
	
	public OrthographicCamera getCam(){
        return gamecam;
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
	/**
     * Controla nosso Player usando impulsos imediatos.
     * */
	public void handleInput(float dt){
        //Controla nosso Player usando impulsos imediatos
	   if(player.currentState != Player.State.DEAD) {
           if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && player.currentState != Player.State.FALLING && player.currentState != Player.State.JUMPING && player.currentState != Player.State.SHOOTING) {
        	   GameProject.manager.get("audio/effects/jump.ogg", Sound.class).play();
           		player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
        	   
           }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
             if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && player.currentState != Player.State.SHOOTING)
                player.fire(this);
        }

    }

	/**
	 * Update ser� responsavel por atualizar os elementos da tela.
	 * */
    public void update(float dt){
    	
        //chamada para os input do jogador
    	handleInput(dt);
        //handleSpawningItems();
    	
    	//takes 1 step in the physics simulation(60 times per second)
        world.step(1 / 60f, 6, 2);
        
        //atualiza o jogador
    	player.update(dt);
    	
    	//atualiza os inimigos
    	//lembrar que inimigos s� s�o ativados ap�s a aproxima��o do jogador
    	 
    	
        
        
       for(Enemy enemy : creator.getEnemies()) {
            enemy.update(dt);
            if(player.currentState == Player.State.DEAD) 
            	enemy.b2body.setLinearVelocity(new Vector2(0f, 0f));
            if(!enemy.isDestroyed() && (enemy.getX() < player.getX() + 400 / GameProject.PPM)) {
                enemy.b2body.setActive(true);
            }
        }
    	 

       for(Coin coin : creator.getCoins()) {
    	   coin.update(dt);
           if(!coin.isDestroyed() &&  (coin.getX() < player.getX() + 400 / GameProject.PPM)) {
        	   coin.b2body.setActive(true);
           }
       }
       
       	Helicoptero helicoptero = creator.getHelicoptero();
    	helicoptero.update(dt);
        if(helicoptero.getX() < player.getX() + 400 / GameProject.PPM) {
        	helicoptero.b2body.setActive(true);
        }

        //atualiza o HUD
        hud.update(dt);
        

        //anexa a nossa gamecam com o nosso personagem principal(jogador) apenas na coordenada X.
        if(player.currentState != Player.State.DEAD) {
            gamecam.position.x = player.b2body.getPosition().x;
        }
        
        if(gamecam.position.x < 2) {
           	gamecam.position.x = 2;
           }else if(gamecam.position.x > 41){
           	gamecam.position.x = 41;        	
           }else if(stateGame == 1){
       	 gamecam.position.x = helicoptero.getX();
        }else  {
    	   
    	    gamecam.position.x = player.b2body.getPosition().x;
           
    	   
        }
       

        
        //Atualiza nossa gamecam com as coordenadas corretas ap�s altera��o
        gamecam.update();
        //Passa ao renderer para desenhar na tela apenas o que nossa camera pode ver no nosso mundo.
        renderer.setView(gamecam);        
        //renderer.setView(gamecam.combined,0,0,width,height);

    }
    
  
	/**
	 * M�todo para redenriza��o.
	 * */
	@Override
	public void render(float delta) {
		
		//Separa��o do nosso update logico para o renderer 
        update(delta);
        
        //Defini a nossa tela do jogo como preta
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Renderiza nosso mapa do jogo
        renderer.render();
               
        Array<Sprite> sprites = new Array<Sprite>();
        sprites.addAll(creator.getEnemies()); 
        sprites.addAll(creator.getCoins());
        sprites.addAll(creator.getHelicoptero());
        sprites.add(player);
        DrawScreenComponent.DrawScreen(gamecam, sprites);  

		//Seta nosso batch para desenhar o que o a camera do HUD v�.
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
		
			
        //se ocorrer um gameover a tela de GameOver ser� chamada e ir� ocorrer um dispose dos recursos do game

        if(gameOver()){

            game.setScreen(new GameOverScreen(game, slevel, nickname));

                Hud.saveScore();

            dispose();

        }
		
		if(player.currentState == Player.State.SAVE)
		{
			game.setScreen(new SuccessScreen(game, slevel, nickname));
			 Hud.saveScore();
			music.stop();
	        dispose();
		}
		 
	}

	/**
	 * Verifica se ocorreu um gameover.
	 * */
	public boolean gameOver(){
        if(player.currentState == Player.State.DEAD && player.getStateTimer() > 3){
            return true;
        }
        return false;
    }

	/**
	 * Verifica se ocorreu um gameover.
	 * */
	public boolean completeGame(){
        return Player.instance.saved;
    }
	
	/**
	 * Atualiza a nossa Viewport do jogo.
	 * */
    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

        //atualiza a nossa Viewport do jogo
        gamePort.update(width,height);


    }

	/**
	 * Retorna o TiledMap para a Playscreen.
	 * */
    public TiledMap getMap(){
    	//Retorna o TiledMap para a Playscreen
        return map;
    }
    
	/**
	 * Retorna o mundo para a Playscreen.
	 * */
    public World getWorld(){
    	//Retorna o mundo para a Playscreen
        return world;
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

	/**
	 * Seta o estado do game.
	 * */
	public void setStateGame(){
    	stateGame = 1;
    }
	
	/**
	 * Dispose de todos os nossos recursos abertos
	 * */
	@Override
	public void dispose() {
		//dispose de todos os nossos recursos abertos
        map.dispose();
        renderer.dispose();
        world.dispose();
        //b2dr.dispose();
        hud.dispose();
		
	}
	
	/**
	 * Retorna o HUD para a Playscreen.
	 * */
	public Hud getHud(){ 
		//Retorna o HUD para a Playscreen
		return hud; 
	}
}
