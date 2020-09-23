package com.gdxproject.game.Sprites.Items;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.gdxproject.game.GameProject;


public class IntroDev extends Sprite
{
    private float stateTime;
    private Animation<TextureRegion> walkAnimation; 
    private Array<TextureRegion> frames;
    protected GameProject screen; 

    public IntroDev(GameProject screen, float x, float y) {

        this.screen = screen; //recebe a screen
        frames = new Array<TextureRegion>();       
        for(int i = 1; i < 3; i++) 
            frames.add(new TextureRegion(new Texture(Gdx.files.internal("intro/intro_dev/image_" + i + ".png")), 0, 0, 1280, 720));
            
            
        walkAnimation = new Animation<TextureRegion>(5.5f, frames);        
        stateTime = 0;
        setBounds(0, 0, GameProject.V_WIDTH, GameProject.V_HEIGHT);
    }

    public void update(float dt){
        stateTime += dt;   	
        setRegion(walkAnimation.getKeyFrame(stateTime, true));
      
    }

    public void draw(Batch batch){    	
             super.draw(batch); 
    }

}