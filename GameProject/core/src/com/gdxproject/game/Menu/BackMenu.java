package com.gdxproject.game.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class BackMenu extends Sprite
{
    private float stateTime;
    private Animation<TextureRegion> walkAnimation; 
    private Array<TextureRegion> frames;
    protected LevelSelect screen; 

    public BackMenu(LevelSelect screen, float x, float y) {

        this.screen = screen; //recebe a screen
        //setPosition(2, 2);
        frames = new Array<TextureRegion>();  
        frames.add( new TextureRegion(new Texture(Gdx.files.internal("lvl_image.jpg")), 0, 0, 1600, 800));
        walkAnimation = new Animation<TextureRegion>(0.1f, frames);        
        stateTime = 0;
        setBounds(0, 0, 400, 228 );
    }

    public void update(float dt){
        stateTime += dt;   	
        setRegion(walkAnimation.getKeyFrame(stateTime, true));
      
    }

    public void draw(Batch batch){    	
    	
             super.draw(batch); 
    		 
    		
    }

}