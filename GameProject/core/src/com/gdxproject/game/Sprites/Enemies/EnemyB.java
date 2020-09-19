package com.gdxproject.game.Sprites.Enemies;



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
import com.gdxproject.game.Screens.GameOverScreen;
import com.gdxproject.game.Screens.PlayScreen;
import com.gdxproject.game.Sprites.Player.Bullet;
import com.gdxproject.game.Sprites.Player.Player;


public class EnemyB extends Sprite
{
    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    protected GameOverScreen screen; 

    public EnemyB(GameOverScreen screen, float x, float y) {

        this.screen = screen; //recebe a screen
        //setPosition(2, 2);
        frames = new Array<TextureRegion>();       
        for(int i = 0; i < 5; i++) 
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("enemyb.png")),  i*544, 0,544, 544));
        
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("enemyb.png")),  0, 544,544, 544));
        walkAnimation = new Animation(0.1f, frames);        
        stateTime = 0;
        setBounds(0, 0, 100, 150 );
        Gdx.app.log("massa","cons");
    }

    public void update(float dt){
        stateTime += dt;   	
        setRegion(walkAnimation.getKeyFrame(stateTime, true));
      
    }

    public void draw(Batch batch){    	
    	
             super.draw(batch); 
    		 
    		
    }

}