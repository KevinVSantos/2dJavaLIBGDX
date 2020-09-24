package com.gdxproject.game.Sprites.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.gdxproject.game.GameProject;
import com.gdxproject.game.Scenes.Hud;
import com.gdxproject.game.Screens.PlayScreen;

/**
 * Moedas
 */
public class Coin extends Sprite {
	
	/**
	 * Mundo do jogo.
	 */
    protected World world;
    
    /**
     * Tela atual.
     */
    protected PlayScreen screen; 
    
    /**
     * Corpo de colisão.
     */
    public Body b2body;
    
    /**
     * Velocidade.
     */
    public Vector2 velocity;
    
    /**
     * Tempo entre animações
     */
    private float stateTime;
    
    /**
     * Aniamção e movimento.
     */
    private Animation<TextureRegion> walkAnimation;
    
    /**
     * Frames a serem exibidos.
     */
    private Array<TextureRegion> frames;

    /**
     * Deverá ser destruído.
     */
    private boolean setToDestroy;
    
    /**
     * Se está destruído.
     */
    private boolean destroyed;

    /**
     * Cria a moeda.
     * @param screen - Tela atual.
     * @param x - Posição X.
     * @param y - Posição Y.
     */
    public Coin(PlayScreen screen, float x, float y) {
       
        
        this.world = screen.getWorld(); //  recebe o mundo da screen
        this.screen = screen; //recebe a screen
        setPosition(x, y);
        defineCoin(); // classe que será responsavel por definir o inimigo
        b2body.setActive(false); 
        
        frames = new Array<TextureRegion>();
        
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("coins/coin.png")), 0, 0, 20, 20));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("coins/coin1.png")), 0, 0, 20, 20));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("coins/coin2.png")), 0, 0, 20, 20));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("coins/coin3.png")), 0, 0, 20, 20));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("coins/coin4.png")), 0, 0, 20, 20));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("coins/coin5.png")), 0, 0, 20, 20));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("coins/coin6.png")), 0, 0, 20, 20));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("coins/coin7.png")), 0, 0, 20, 20));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("coins/coin8.png")), 0, 0, 20, 20));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("coins/coin9.png")), 0, 0, 20, 20));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("coins/coin10.png")), 0, 0, 20, 20));
        walkAnimation = new Animation<TextureRegion>(0.2f, frames);
        stateTime = 0;
       
        setBounds(getX(), getY(), 16 / GameProject.PPM, 16 / GameProject.PPM);
        setToDestroy = false; 
        destroyed = false;
        
        
    }

    /**
     * Método responsável por atualizar o que será exibido.
     * @param dt - Tempo desde a última atualização
     */
    public void update(float dt){
        stateTime += dt;   	
        if(setToDestroy && !destroyed){ 	
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(new TextureRegion(new Texture(Gdx.files.internal("coins/coin.png")), 0, 0, 20, 20));
            stateTime = 0; 
        }
        else if(!destroyed) {
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        }
    }

    /**
     * Define a moeda.
     */
    protected void defineCoin() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / GameProject.PPM);
        fdef.filter.categoryBits = GameProject.COIN_BIT;
        fdef.filter.maskBits = GameProject.GROUND_BIT |
                GameProject.COIN_BIT |
                GameProject.HOLE_BIT |
                GameProject.OBJECT_BIT |
                GameProject.PLAYER_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    /**
     * Método responsável por desenhar na tela.
     */
    public void draw(Batch batch){
        if(!destroyed)
            super.draw(batch);
    }
    
    
    /**
     * Pega uma moeda.
     */
    public void getCoin(){
        GameProject.manager.get("audio/coin.wav", Sound.class).play();
        setToDestroy = true;
        Hud.addScore(300);
    }
    
    /**
     * Verifica se esta destruído.
     */
    public boolean isDestroyed() {
    	return destroyed;
    }
    

}
