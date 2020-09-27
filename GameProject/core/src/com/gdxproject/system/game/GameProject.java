package com.gdxproject.system.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdxproject.system.game.Menu.BBInputProcessor;
import com.gdxproject.system.game.Screens.IntroDevScreen;

/**
 * Classe Principal do Projeto.
 * <br>
 * Esta classe ser� a entrada para execu��o das demais rotinas, sendo assim, � respons�vel por fazer load de arquivos e
 * libera��o de recursos, al�m de fazer a chamada para classes mais internas. 
 */

public class GameProject extends Game {
	//Tamanho da tela virtual e Escala do Box2D(Pixels por metro)
	
	/**
	 *  Largura da tela virtual.
	 */
	public static final int V_WIDTH = 400;
	
	/**
	 * Altura da tela Virtual.
	 */
	public static final int V_HEIGHT = 224;
	
	/**
	 * Pixel por metro.
	 */
	public static final float PPM = 100;

	//Box2D Bits de colis�o.
	
	/**
	 * Bit que representa o vazio, o ar, algo que n�o haver� contato.
	 */
	public static final short NOTHING_BIT = 0;
	
	/**
	 * Bit que representa o contato com a terra.
	 */
	public static final short GROUND_BIT = 1;
	
	/**
	 * Bit que representa o contato com o player.
	 */
	public static final short PLAYER_BIT = 2;
	
	/**
	 * Bit que representa o contato com o buraco.
	 */
	public static final short HOLE_BIT = 4;
	
	/**
	 * Bit que representa o contato com a moeda.
	 */
	public static final short COIN_BIT = 8;
	
	/**
	 * Bit que representa a destrui��o de um bloco.
	 */
	public static final short DESTROYED_BIT = 16;
	
	/**
	 * Bit que representa um objeto.
	 */
	public static final short OBJECT_BIT = 32;
	
	/**
	 * Bit que representa o contato com o corpo um inimigo.
	 */
	public static final short ENEMY_BIT = 64;
	
	/**
	 * Bit que representa o contato com a cabe�a de um inimigo.
	 */
	public static final short ENEMY_HEAD_BIT = 128;
	
	/**
	 * Bit que representa um item.
	 */
	public static final short ITEM_BIT = 256;
	
	/**
	 * Bit que representa a cabe�a do personagem.
	 */
	public static final short PLAYER_HEAD_BIT = 512;
	
	/**
	 * Bit que representa o contato com as balas.
	 */
	public static final short BULLET_BIT = 1024;
	
	/**
	 * Bit que representa o final da fase.
	 */
	public static final short FINAL_BIT = 2048;
	
	/**
	 * Bit que representa um bloco invisivel que inimigos n�o podem passar.
	 */
	public static final short ENEMY_GROUND_BIT = 4096;

	/**
	 * Batch principal para desenhar na tela.
	 */
	public SpriteBatch batch;
	
	/**
	 * Singleton de GameProject.
	 */
	public static GameProject instance;

	public GameProject() {
		instance = this;
	}
	
	/** 
	 * Respons�vel por carregar arquivos.
	 * <br>
	 *  <p>
	 *  Aviso: utilizar o AssetManager de uma forma estatica pode causar problemas, especialmente se for em android.
	 *	Uma maneira � passar o Assetmanager para as classes que precisam dele.
	 *	Por enquanto ser� usado de maneira estatica para salvar o tempo. 
	 *	</p>
	*/
	public static AssetManager manager;

	
	/**
	 * M�todo inicial do programa.
	 * <br>
	 * <p>
	 * Respons�vel por fazer o load de arquivos que ser�o utilizados.
	 * </p>
	 */
	@Override
	public void create () {
		
		batch = new SpriteBatch(); 
		
		Gdx.input.setInputProcessor(new BBInputProcessor());
		
		/// carregando assets no jogo
		manager = new AssetManager();
		manager.load("hit.wav", Music.class); 
		manager.load("audio/effects/gameover.mp3", Music.class); 
		manager.load("audio/coin.wav", Sound.class);
		manager.load("audio/stomp.wav", Sound.class);
		manager.load("audio/bump.wav", Sound.class);
		manager.load("audio/effects/jump.ogg", Sound.class);
		manager.load("audio/effects/death.mp3", Sound.class);
		manager.load("audio/music/Blinding_Lights.mp3", Music.class);
		manager.load("audio/music/song_intro_daydreaming.mp3", Music.class);
		manager.load("audio/music/song_intro_daydreaming_story.mp3", Music.class);
		manager.load("audio/music/song_story_requiem_for_a_dream.mp3", Music.class);
		manager.load("audio/voice/hahaha.mp3", Music.class);
		manager.load("audio/voice/idiota.mp3", Music.class);
		manager.load("audio/voice/ofendendo.mp3", Music.class);
		manager.load("audio/voice/coquinho.mp3", Music.class);
		manager.load("audio/voice/feliz.mp3", Music.class);
		manager.load("audio/music/Paradise.mp3", Music.class);
		manager.finishLoading();
		
		//Seta a tela inicial		
		setScreen(new IntroDevScreen(this));
	}


	/**
	 * Libera��o de recursos.
	 */
	@Override
	public void dispose() {
		//metodo que receber� o dispose de Game
		super.dispose();
		manager.dispose();
		batch.dispose();
	}

	/**
	 * M�todo principal para desenhar os sprites na tela.
	 */
	@Override
	public void render () {
		//metodo que receber� o render de Game
		super.render();
	}
}