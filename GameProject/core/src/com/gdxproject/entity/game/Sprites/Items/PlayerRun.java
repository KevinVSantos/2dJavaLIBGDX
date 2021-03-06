package com.gdxproject.entity.game.Sprites.Items;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.gdxproject.system.game.Screens.SuccessScreen;

/**
 * Sprite final.
 */
public class PlayerRun extends Sprite
{
	/**
	 * Tempo entre anima��es.
	 */
    private float stateTime;
    
    /**
     * Anima��o e movimento.
     */
    private Animation<TextureRegion> walkAnimation; 
    
    /**
     * Frames que ser�o exibidos.
     */
    private Array<TextureRegion> frames;
    
    /**
     * Tela de sucesso.
     */
    protected SuccessScreen screen; 

    /**
     * Cria��o do objeto.
     * @param screen - Tela atual.
     * @param x - Posi��o X.
     * @param y - Posi��o Y.
     */
    public PlayerRun(SuccessScreen screen, float x, float y) {

        this.screen = screen; //recebe a screen
        
        frames = new Array<TextureRegion>();   
        for(int i = 0; i < 9; i++) 
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("end/run.png")),  16+(i*67), 0,50, 50));
        walkAnimation = new Animation<TextureRegion>(0.1f, frames);        
        stateTime = 0;
        setBounds(0, 0, 40, 70 );
        
    }
    
    /**
     * M�todo respons�vel por atualizar o que ser� exibido.
     * @param dt - Tempo desde a �ltima atualiza��o
     */
    public void update(float dt){
        stateTime += dt;
        
        TextureRegion region = walkAnimation.getKeyFrame(stateTime, true);
        if(!region.isFlipX())
        	region.flip(true, false);
        setRegion(region);
      
    }

    /**
     * M�todo respons�vel por desenhar na tela.
     */
    public void draw(Batch batch){    	
    	
             super.draw(batch); 
    		 
    		
    }

}