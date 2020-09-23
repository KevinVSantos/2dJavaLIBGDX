package com.gdxproject.game.Sprites.Items;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.gdxproject.game.Screens.SuccessScreen;


public class Book extends Sprite
{
    private float stateTime;
    private Animation<TextureRegion> walkAnimation; 
    private Array<TextureRegion> frames;
    protected SuccessScreen screen; 

    public Book(SuccessScreen screen, float x, float y) {

        this.screen = screen; //recebe a screen
        frames = new Array<TextureRegion>();
        for(int i = 0; i < 5; i++) 
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("end/book.png")),  i*256, 0,256, 256));
        walkAnimation = new Animation<TextureRegion>(0.1f, frames);        
        stateTime = 0;
        setBounds(0, 0, 30, 30 );
    }

    public void update(float dt){
        stateTime += dt;   	
        TextureRegion region = walkAnimation.getKeyFrame(stateTime, true);
        setRegion(region);
      
    }

    public void draw(Batch batch){    	
    	
             super.draw(batch); 
    		 
    		
    }

}