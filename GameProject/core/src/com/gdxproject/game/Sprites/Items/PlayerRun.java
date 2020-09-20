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
import com.gdxproject.game.Screens.GameOverScreen;
import com.gdxproject.game.Screens.PlayScreen;
import com.gdxproject.game.Screens.SuccessScreen;
import com.gdxproject.game.Sprites.Player.Bullet;
import com.gdxproject.game.Sprites.Player.Player;


public class PlayerRun extends Sprite
{
    private float stateTime;
    private Animation<TextureRegion> walkAnimation; 
    private Array<TextureRegion> frames;
    protected SuccessScreen screen; 

    public PlayerRun(SuccessScreen screen, float x, float y) {

        this.screen = screen; //recebe a screen
        
        frames = new Array<TextureRegion>();      // 603x50
        for(int i = 0; i < 9; i++) 
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("end/run.png")),  16+(i*67), 0,50, 50));
        walkAnimation = new Animation(0.1f, frames);        
        stateTime = 0;
        setBounds(0, 0, 40, 70 );
        
    }
    
    public void update(float dt){
        stateTime += dt;
        
        TextureRegion region = walkAnimation.getKeyFrame(stateTime, true);
        if(!region.isFlipX())
        	region.flip(true, false);
        setRegion(region);
      
    }

    public void draw(Batch batch){    	
    	
             super.draw(batch); 
    		 
    		
    }

}