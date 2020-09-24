package com.gdxproject.game.Sprites.Enemies;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.gdxproject.game.Screens.GameOverScreen;

/**
 * Inimigo exibido ao morrer.
 */
public class EnemyB extends Sprite
{
	/**
	 * Tempo entre anima��es.
	 */
    private float stateTime;
    
    /**
     * Regi�o da anima��o de andar.
     */
    private Animation<TextureRegion> walkAnimation; 
    
    /**
     * Frames para exibi��o.
     */
    private Array<TextureRegion> frames;
    
    /**
     * Tela Atual;
     */
    protected GameOverScreen screen; 

    /**
     * Cria o inimigo.
     * @param screen - Tela atual.
     * @param x - Posi��o X.
     * @param y - Posi��o Y.
     */
    public EnemyB(GameOverScreen screen, float x, float y) {

        this.screen = screen; //recebe a screen
        frames = new Array<TextureRegion>();       
        for(int i = 0; i < 5; i++) 
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("enemyb.png")),  i*544, 0,544, 544));
        
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("enemyb.png")),  0, 544,544, 544));
        walkAnimation = new Animation<TextureRegion>(0.1f, frames);        
        stateTime = 0;
        setBounds(0, 0, 100, 150 );
    }

    
    /**
     * M�todo respons�vel por atualizar o que ser� exibido.
     * @param dt - Tempo desde a �ltima atualiza��o.
     */
    public void update(float dt){
        stateTime += dt;   	
        setRegion(walkAnimation.getKeyFrame(stateTime, true));
      
    }

    /**
     * M�todo respons�vel por desenhar na tela.
     */
    public void draw(Batch batch){    	
    	
             super.draw(batch); 
    		 
    		
    }

}