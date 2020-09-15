package com.gdxproject.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdxproject.game.Screens.PlayScreen;

public class GameProject extends Game {
	//Tamanho da tela virtual e Escala do Box2D(Pixels por metro)
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 224;
	public static final float PPM = 100; // pixels for meter

	//Box2D Bits de colisão
	public static final short NOTHING_BIT = 0;
	public static final short GROUND_BIT = 1;
	public static final short MARIO_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	
	public static final short OBJECT_BIT = 32;
	public static final short ENEMY_BIT = 64;
	public static final short ENEMY_HEAD_BIT = 128;
	public static final short ITEM_BIT = 256;
	public static final short MARIO_HEAD_BIT = 512;
	public static final short FIREBALL_BIT = 1024;

	public SpriteBatch batch;

	/* Aviso: utilizar o AssetManager de uma forma estatica pode causar problemas, especialmente se for em android.
	Uma maneira é passar o Assetmanager para as classes que precisam dele.
	Por enquanto será usado de maneira estatica para salvar o tempo. */
	public static AssetManager manager;
	 
	@Override
	public void create () {
		
		batch = new SpriteBatch(); 
		
		/// carregando assets no jogo
		manager = new AssetManager();
		manager.load("mario/audio/music/mario_music.ogg", Music.class); 
		manager.load("mario/audio/sounds/coin.wav", Sound.class);
		manager.load("mario/audio/sounds/bump.wav", Sound.class);
		manager.load("mario/audio/sounds/breakblock.wav", Sound.class);
		manager.load("mario/audio/sounds/powerup_spawn.wav", Sound.class);
		manager.load("mario/audio/sounds/powerup.wav", Sound.class);
		manager.load("mario/audio/sounds/powerdown.wav", Sound.class);
		manager.load("mario/audio/sounds/stomp.wav", Sound.class);
		manager.load("mario/audio/sounds/mariodie.wav", Sound.class);
		manager.load("audio/Blinding_Lights.mp3", Music.class);
		manager.finishLoading();

		//Seta a tela inicial
		setScreen(new PlayScreen(this));
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