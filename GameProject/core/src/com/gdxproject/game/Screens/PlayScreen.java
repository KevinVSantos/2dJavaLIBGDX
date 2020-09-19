package com.gdxproject.game.Screens;


import java.util.concurrent.LinkedBlockingQueue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdxproject.game.GameProject;
import com.gdxproject.game.Scenes.Hud;
import com.gdxproject.game.Sprites.Enemies.Enemy;
import com.gdxproject.game.Sprites.Items.Coin;
import com.gdxproject.game.Sprites.Items.Helicoptero;
import com.gdxproject.game.Sprites.Player.Player;
import com.gdxproject.game.Sprites.Structure.WorldContactListener;
import com.gdxproject.game.Tools.B2WorldCreator;

public class PlayScreen implements Screen {

	private GameProject game;
	private TextureAtlas atlas;
    
	//Variaveis basicas da Playscreen
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;
    
    //Variaveis do Mapa Tiled
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    
    //Variaveis do Box2d
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator; // construçao do mapa

    //Sprites
    private Player player;
    
    //Musicas
    private Music music;

    
        
	public PlayScreen(GameProject game) {
		
		//carrega o atlas referente as texturas
		atlas = new TextureAtlas("Mario_and_Enemies.pack");
		
		//recebe a classe principal do jogo
		this.game = game;
		
		//inicializa a camera que irá seguir junto ao personagem
        gamecam = new OrthographicCamera();
        
        
        //Cria um FitViewport para manter a proporção virtual apesar do tamanho da tela
        gamePort = new FitViewport(GameProject.V_WIDTH / GameProject.PPM, GameProject.V_HEIGHT / GameProject.PPM, gamecam);
        
       
        //Cria nossa HUD para pontuação/timers/level informaçoes
        hud = new Hud(game.batch);
        
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
        //Permissão para debug lines do nosso mundo Box2D. 
        b2dr = new Box2DDebugRenderer();       
        //Inicializa a construção do nosso tiled map
        creator = new B2WorldCreator(this);

        
        //Cria o jogador no nosso mundo
        player = new Player(this);
        
        //Seta as normas que serão utilizadas para o contato
        world.setContactListener(new WorldContactListener());
        
        
        Gdx.app.log("massa","DIED");
        //Defini a musica de fundo do jogo
        music = GameProject.manager.get("audio/music/Blinding_Lights.mp3", Music.class);
        
        
        music.setLooping(true);
        music.setVolume(0.3f);
        music.play();


	}
	


    public TextureAtlas getAtlas(){
        return atlas;
    }

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
	
	public void handleInput(float dt){
        //Controla nosso Player usando impulsos imediatos
	   if(player.currentState != Player.State.DEAD) {
           if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && player.currentState != Player.State.FALLING && player.currentState != Player.State.JUMPING) {
        	   GameProject.manager.get("audio/effects/jump.ogg", Sound.class).play();
           		player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
        	   
           }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
             if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
                player.fire(this);
        }

    }

    public void update(float dt){
    	//Update será responsavel por atualizar os elementos da tela
    	
        //chamada para os input do jogador
    	handleInput(dt);
        //handleSpawningItems();
    	
    	//takes 1 step in the physics simulation(60 times per second)
        world.step(1 / 60f, 6, 2);
        
        //atualiza o jogador
    	player.update(dt);
    	
    	//atualiza os inimigos
    	//lembrar que inimigos só são ativados após a aproximação do jogador
       for(Enemy enemy : creator.getEnemies()) {
            enemy.update(dt);
            if(enemy.getX() < player.getX() + 400 / GameProject.PPM) {
                enemy.b2body.setActive(true);
            }
        }

       for(Coin coin : creator.getCoins()) {
    	   coin.update(dt);
           if(coin.getX() < player.getX() + 400 / GameProject.PPM) {
        	   coin.b2body.setActive(true);
           }
       }
       
       for(Helicoptero helicoptero : creator.getHelicopteros()) {
    	   helicoptero.update(dt);
           if(helicoptero.getX() < player.getX() + 400 / GameProject.PPM) {
        	   helicoptero.b2body.setActive(true);
           }
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
        } else {
        		 gamecam.position.x = player.b2body.getPosition().x;
        }


        //Atualiza nossa gamecam com as coordenadas corretas após alteração
        gamecam.update();
        //Passa ao renderer para desenhar na tela apenas o que nossa camera pode ver no nosso mundo.
        renderer.setView(gamecam);
        
        //renderer.setView(gamecam.combined,0,0,width,height);

    }
    
  
    
	@Override
	public void render(float delta) {
		
		//Separação do nosso update logico para o renderer 
        update(delta);
        
        //Defini a nossa tela do jogo como preta
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Renderiza nosso mapa do jogo
        renderer.render();
        
        //renderer our Box2DDebugLines
        b2dr.render(world, gamecam.combined);
        
        //Seta a projeção para a gamecam e desenha o conteudo definido na tela
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        for (Enemy enemy : creator.getEnemies())
            enemy.draw(game.batch);
        for (Coin coin : creator.getCoins())
        	coin.draw(game.batch);
        for (Helicoptero helicoptero : creator.getHelicopteros())
        	helicoptero.draw(game.batch);
        
        game.batch.end();
        

		//Seta nosso batch para desenhar o que o a camera do HUD vê.
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
		
			
		//se ocorrer um gameover a tela de GameOver será chamada e irá ocorrer um dispose dos recursos do game
		if(gameOver()){
	        game.setScreen(new GameOverScreen(game));
	        dispose();
		}
		 
	}

	public boolean gameOver(){
		//verifica se ocorreu um gameover
        if(player.currentState == Player.State.DEAD && player.getStateTimer() > 3){
            return true;
        }
        return false;
    }

    
    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

        //atualiza a nossa Viewport do jogo
        gamePort.update(width,height);


    }

    public TiledMap getMap(){
    	//Retorna o TiledMap para a Playscreen
        return map;
    }
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

	@Override
	public void dispose() {
		//dispose de todos os nossos recursos abertos
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
		
	}
	
	public Hud getHud(){ 
		//Retorna o HUD para a Playscreen
		return hud; 
	}
}
