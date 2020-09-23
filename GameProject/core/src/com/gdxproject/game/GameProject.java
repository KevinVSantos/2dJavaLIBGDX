package com.gdxproject.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdxproject.game.Menu.BBInputProcessor;
import com.gdxproject.game.Screens.IntroDevScreen;


public class GameProject extends Game {
	//Tamanho da tela virtual e Escala do Box2D(Pixels por metro)
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 224;
	public static final float PPM = 100; // pixels for meter

	//Box2D Bits de colisão
	public static final short NOTHING_BIT = 0;
	public static final short GROUND_BIT = 1;
	public static final short PLAYER_BIT = 2;
	public static final short HOLE_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	
	public static final short OBJECT_BIT = 32;
	public static final short ENEMY_BIT = 64;
	public static final short ENEMY_HEAD_BIT = 128;
	public static final short ITEM_BIT = 256;
	public static final short PLAYER_HEAD_BIT = 512;
	public static final short BULLET_BIT = 1024;
	public static final short FINAL_BIT = 2048;
	public static final short ENEMY_GROUND_BIT = 4096;

	public SpriteBatch batch;

	/* Aviso: utilizar o AssetManager de uma forma estatica pode causar problemas, especialmente se for em android.
	Uma maneira é passar o Assetmanager para as classes que precisam dele.
	Por enquanto será usado de maneira estatica para salvar o tempo. */
	public static AssetManager manager;

	
	
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


	@Override
	public void dispose() {
		//metodo que receberá o dispose de Game
		super.dispose();
		manager.dispose();
		batch.dispose();
	}

	@Override
	public void render () {
		//metodo que receberá o render de Game
		super.render();
	}
}