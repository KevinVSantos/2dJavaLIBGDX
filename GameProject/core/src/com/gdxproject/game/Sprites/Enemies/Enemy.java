package com.gdxproject.game.Sprites.Enemies;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.gdxproject.game.Screens.PlayScreen;
import com.gdxproject.game.Sprites.Player.Bullet;
import com.gdxproject.game.Sprites.Player.Player;
 

public abstract class Enemy extends Sprite {
	//atributos que serão setados para os inimigos
    protected World world;
    protected PlayScreen screen; 
    public Body b2body;
    public Vector2 velocity;

    public Enemy(PlayScreen screen, float x, float y){
        this.world = screen.getWorld(); //  recebe o mundo da screen
        this.screen = screen; //recebe a screen
        setPosition(x, y);
        defineEnemy(); // classe que será responsavel por definir o inimigo
        velocity = new Vector2(-1, -2); // vetor de velocidade do inimigo
        b2body.setActive(false); 
       
    } 

    protected abstract void defineEnemy(); // defini/cria o inimigo
    public abstract void update(float dt); // atualiza as informaçoes do inimigo
    public abstract void hitOnHead(Player mario); //se player pular em cima do inimigo
    public abstract void hitByEnemy(Enemy enemy); //se inimigos se chocarem
    public abstract void hitbyBullet(Bullet bullet); //se player pular em cima do inimigo
    public abstract boolean isDestroyed(); //se player pular em cima do inimigo
    public abstract void hitHole();

     
    //função responsavel por inverter o vetor de direção do inimigo
    public void reverseVelocity(boolean x, boolean y){
        if(x)
            velocity.x = -velocity.x;
        if(y)
            velocity.y = -velocity.y;
    }
}