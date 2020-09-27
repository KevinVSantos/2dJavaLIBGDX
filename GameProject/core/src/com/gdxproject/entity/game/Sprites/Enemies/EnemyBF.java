package com.gdxproject.entity.game.Sprites.Enemies;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.gdxproject.system.game.Screens.SuccessScreen;

/**
 * Inimigo exibido ao concluir nível.
 */
public class EnemyBF extends Sprite
{
	/**
	 * Tempo entre animações.
	 */
    private float stateTime;
    
    /**
     * Região da animação de andar.
     */
    private Animation<TextureRegion> walkAnimation; 
    
    /**
     * Frames para exibição.
     */
    private Array<TextureRegion> frames;
    
    /**
     * Tela Atual;
     */
    protected SuccessScreen screen; 

    /**
     * Cria o inimigo.
     * @param screen - Tela atual.
     * @param x - Posição X.
     * @param y - Posição Y.
     */
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

    /**
     * Método responsável por atualizar o que será exibido.
     * @param dt - Tempo desde a última atualização.
     */
    public void update(float dt){
        stateTime += dt;   	
        setRegion(walkAnimation.getKeyFrame(stateTime, true));
      
    }

    /**
     * Método responsável por desenhar na tela.
     */
    public void draw(Batch batch){    	
    	
             super.draw(batch); 
    		 
    		
    }

}