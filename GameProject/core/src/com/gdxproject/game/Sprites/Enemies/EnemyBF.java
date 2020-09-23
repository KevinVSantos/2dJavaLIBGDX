package com.gdxproject.game.Sprites.Enemies;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.gdxproject.game.Screens.SuccessScreen;


public class EnemyBF extends Sprite
{
    private float stateTime;
    private Animation<TextureRegion> walkAnimation; 
    private Array<TextureRegion> frames;
    protected SuccessScreen screen; 

    public EnemyBF(SuccessScreen screen, float x, float y) {

        this.screen = screen; //recebe a screen
        //setPosition(2, 2);
        frames = new Array<TextureRegion>();      
        for(int i = 0; i < 5; i++) 
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("end/eb.png")),  i*192, 0,192, 192));
        walkAnimation = new Animation<TextureRegion>(0.03f, frames);        
        stateTime = 0;
        setBounds(0, 0, 40, 70 );
    }

    public void update(float dt){
        stateTime += dt;   	
        setRegion(walkAnimation.getKeyFrame(stateTime, true));
      
    }

    public void draw(Batch batch){    	
    	
             super.draw(batch); 
    		 
    		
    }

}