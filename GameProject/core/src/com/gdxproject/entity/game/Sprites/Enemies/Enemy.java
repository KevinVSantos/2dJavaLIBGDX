package com.gdxproject.entity.game.Sprites.Enemies;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.gdxproject.entity.game.Sprites.Player.Bullet;
import com.gdxproject.entity.game.Sprites.Player.Player;
import com.gdxproject.system.game.Screens.PlayScreen;
 
/**
 * Defini��o gen�rica de inimigos.
 */
public abstract class Enemy extends Sprite {
	//atributos que ser�o setados para os inimigos
	/**
	 * Mundo do jogo.
	 */
    protected World world;
    
    /**
     * Tela atual.
     */
    protected PlayScreen screen; 
    
    /**
     * Corpo para contato.
     */
    public Body b2body;
    
    /**
     * Velocidade.
     */
    public Vector2 velocity;
    
    /**
     * Cria��o de um inimigo.
     * @param screen - Tela atual.
     * @param x - Posi��o no eixo x.
     * @param y - Posi��o no eixo y.
     */
    public Enemy(PlayScreen screen, float x, float y){
        this.world = screen.getWorld(); //  recebe o mundo da screen
        this.screen = screen; //recebe a screen
        setPosition(x, y);
        defineEnemy(); // classe que ser� responsavel por definir o inimigo
        velocity = new Vector2(-1, -2); // vetor de velocidade do inimigo
        b2body.setActive(false); 
       
    } 

    /**
     * Defini o inimigo.
     */
    protected abstract void defineEnemy(); // defini/cria o inimigo
    
    /**
     * M�todo respons�vel por atualizar o que ser� exibido.
     * @param dt - Tempo desde a �ltima atualiza��o.
     */
    public abstract void update(float dt); // atualiza as informa�oes do inimigo
    
    /**
     * Se o plauer pular na cabe�a do inimigo.
     * @param mario - Player.
     */
    public abstract void hitOnHead(Player mario); //se player pular em cima do inimigo
    
    /**
     * Se inimigos se chocarem.
     * @param enemy - Inimigo que se chocou.
     */
    public abstract void hitByEnemy(Enemy enemy); //se inimigos se chocarem
    
    /**
     * Se uma bala acertar o inimigo.
     * @param bullet - Bala que o atingiu.
     */
    public abstract void hitbyBullet(Bullet bullet); //se player pular em cima do inimigo
    
    /**
     * Verifica se est� destru�do.
     */
    public abstract boolean isDestroyed(); //se player pular em cima do inimigo
    
    /**
     * Ao atingir um buraco.
     */
    public abstract void hitHole();

     
    /**
     * Fun��o responsavel por inverter o vetor de dire��o do inimigo
     * @param x - Inverter eixo X.
     * @param y - Inverter eixo Y.
     */
    public void reverseVelocity(boolean x, boolean y){
        if(x)
            velocity.x = -velocity.x;
        if(y)
            velocity.y = -velocity.y;
    }
}