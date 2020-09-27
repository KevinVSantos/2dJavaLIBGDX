package com.gdxproject.entity.game.Sprites.Items;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.gdxproject.system.game.GameProject;

/**
 * Menu Principal
 */
public class Menu extends Sprite
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
    protected GameProject screen; 

    /**
     * Criação do objeto.
     * @param screen - Tela atual.
     * @param x - Posição X.
     * @param y - Posição Y.
     */
    public Menu(GameProject screen, float x, float y) {

        this.screen = screen; //recebe a screen
        frames = new Array<TextureRegion>();       
        for(int i = 1; i < 10; i++) {
        	for(int j = 1; j < 5; j++) {
        		frames.add(new TextureRegion(new Texture(Gdx.files.internal("intro/menu/menu_animation.png")),  j*600, i*338, 600, 338));
        		}
        }   
            
        walkAnimation = new Animation<TextureRegion>(0.1f, frames);        
        stateTime = 0;
        setBounds(0, 0, GameProject.V_WIDTH, GameProject.V_HEIGHT);
    }

    /**
     * Método responsável por atualizar o que será exibido.
     * @param dt - Tempo desde a última atualização
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