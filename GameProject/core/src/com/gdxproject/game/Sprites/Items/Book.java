package com.gdxproject.game.Sprites.Items;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.gdxproject.game.Screens.SuccessScreen;

/**
 * Livro.
 */
public class Book extends Sprite
{
	/**
	 * Tempo entre animações.
	 */
    private float stateTime;
    
    /**
     * Animação e movimento.
     */
    private Animation<TextureRegion> walkAnimation; 
    
    /**
     * Frames que serão exibidos.
     */
    private Array<TextureRegion> frames;
    
    /**
     * Tela de sucesso.
     */
    protected SuccessScreen screen; 

    /**
     * Criação do objeto.
     * @param screen - Tela atual.
     * @param x - Posição X.
     * @param y - Posição Y.
     */
    public Book(SuccessScreen screen, float x, float y) {

        this.screen = screen; //recebe a screen
        frames = new Array<TextureRegion>();
        for(int i = 0; i < 5; i++) 
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("end/book.png")),  i*256, 0,256, 256));
        walkAnimation = new Animation<TextureRegion>(0.1f, frames);        
        stateTime = 0;
        setBounds(0, 0, 30, 30 );
    }

    /**
     * Método responsável por atualizar o que será exibido.
     * @param dt - Tempo desde a última atualização
     */
    public void update(float dt){
        stateTime += dt;   	
        TextureRegion region = walkAnimation.getKeyFrame(stateTime, true);
        setRegion(region);
      
    }

    /**
     * Método responsável por desenhar na tela.
     */
    public void draw(Batch batch){    	
    	
             super.draw(batch); 
    		 
    		
    }

}