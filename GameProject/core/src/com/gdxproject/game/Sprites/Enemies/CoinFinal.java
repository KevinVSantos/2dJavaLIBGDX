package com.gdxproject.game.Sprites.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.gdxproject.game.GameProject;
import com.gdxproject.game.Scenes.Hud;
import com.gdxproject.game.Screens.PlayScreen;
import com.gdxproject.game.Sprites.Enemies.Enemy;
import com.gdxproject.game.Sprites.Player.FireBall;
import com.gdxproject.game.Sprites.Player.Player;

public class CoinFinal extends Enemy{
    
        private float stateTime;
        private Animation<TextureRegion> walkAnimation;
        private Array<TextureRegion> frames;

        private boolean setToDestroy;
        private boolean destroyed;
    
    public CoinFinal(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        
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
            //frames.add(new TextureRegion(screen.getAtlas().findRegion("goomba"), i * 16, 0, 16, 16));
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

    @Override
    protected void defineEnemy() {
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
                GameProject.BRICK_BIT |
                GameProject.OBJECT_BIT |
                GameProject.MARIO_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch batch){
        if(!destroyed || stateTime < 1)
            super.draw(batch);
    }
    
    
    
    @Override
    public void reverseVelocity(boolean x, boolean y){
        
    }
    
    @Override
    public void hitOnHead(Player mario) {

        
        
        //add score
    }
    
    public void getCoin(){
        GameProject.manager.get("mario/audio/sounds/coin.wav", Sound.class).play();
        setToDestroy = true;
        Hud.addScore(100);
    }
    
    @Override
    public void hitByEnemy(Enemy enemy) {
        
    }

	@Override
	public void hitbyFireball(FireBall fire) {
		// TODO Auto-generated method stub
		
	}

}
