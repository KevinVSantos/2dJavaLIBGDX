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
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.gdxproject.game.GameProject;
import com.gdxproject.game.Scenes.Hud;
import com.gdxproject.game.Screens.PlayScreen;
import com.gdxproject.game.Sprites.Enemies.Enemy;
import com.gdxproject.game.Sprites.Player.Player;

public class Coin extends Sprite {
    
	
	//atributos que serão setados para os inimigos
    protected World world;
    protected PlayScreen screen; 
    public Body b2body;
    public Vector2 velocity;
    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;

    private boolean setToDestroy;
    private boolean destroyed;

    
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
        walkAnimation = new Animation(0.2f, frames);
        stateTime = 0;
       
        setBounds(getX(), getY(), 16 / GameProject.PPM, 16 / GameProject.PPM);
        setToDestroy = false; 
        destroyed = false;
        
        
    }

    public void update(float dt){
        stateTime += dt;   	
        if(setToDestroy && !destroyed){ 	
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(new TextureRegion(new Texture(Gdx.files.internal("coins/coin.png")), 0, 0, 20, 20));
            stateTime = 0; 
        }
        else if(!destroyed) {
            //b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        }
    }

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

    public void draw(Batch batch){
        if(!destroyed)
            super.draw(batch);
    }
    
    
    
    public void getCoin(){
        GameProject.manager.get("audio/coin.wav", Sound.class).play();
        setToDestroy = true;
        Hud.addScore(100);
    }
    

}
