package com.gdxproject.game.Sprites.Structure;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.joints.FrictionJointDef;
import com.gdxproject.game.GameProject;
import com.gdxproject.game.Sprites.Enemies.Enemy;
import com.gdxproject.game.Sprites.Items.Coin;
import com.gdxproject.game.Sprites.Items.Helicoptero;
import com.gdxproject.game.Sprites.Player.Bullet;
import com.gdxproject.game.Sprites.Player.Player;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
    	//recebe os objetos que se chocaram
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;//bit de contato que difine qual é o objeto
        
       
        //switch para o tipo de contato 
        switch (cDef){           
        case GameProject.PLAYER_HEAD_BIT | GameProject.HOLE_BIT:
     
        case GameProject.ENEMY_HEAD_BIT | GameProject.PLAYER_BIT:
            if(fixA.getFilterData().categoryBits == GameProject.ENEMY_HEAD_BIT)
                ((Enemy)fixA.getUserData()).hitOnHead((Player) fixB.getUserData());
            else
                ((Enemy)fixB.getUserData()).hitOnHead((Player) fixA.getUserData());
            break;
        case GameProject.ENEMY_BIT | GameProject.OBJECT_BIT:
            if(fixA.getFilterData().categoryBits == GameProject.ENEMY_BIT)
                ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
            else
                ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
            break;
        case GameProject.PLAYER_BIT | GameProject.ENEMY_BIT: 
            if(fixA.getFilterData().categoryBits == GameProject.PLAYER_BIT)
                ((Player) fixA.getUserData()).hit((Enemy)fixB.getUserData());
            else
                ((Player) fixB.getUserData()).hit((Enemy)fixA.getUserData());
            break;
        case GameProject.ENEMY_BIT | GameProject.ENEMY_BIT:
            ((Enemy)fixA.getUserData()).hitByEnemy((Enemy)fixB.getUserData());
            ((Enemy)fixB.getUserData()).hitByEnemy((Enemy)fixA.getUserData());
            break;       
        case GameProject.ENEMY_BIT | GameProject.ENEMY_HEAD_BIT:
            ((Enemy)fixA.getUserData()).hitByEnemy((Enemy)fixB.getUserData());
            ((Enemy)fixB.getUserData()).hitByEnemy((Enemy)fixA.getUserData());
            break;       
        case GameProject.ENEMY_HEAD_BIT | GameProject.ENEMY_HEAD_BIT:
            ((Enemy)fixA.getUserData()).hitByEnemy((Enemy)fixB.getUserData());
            ((Enemy)fixB.getUserData()).hitByEnemy((Enemy)fixA.getUserData());
            break;        
        case GameProject.BULLET_BIT | GameProject.OBJECT_BIT: 
            if(fixA.getFilterData().categoryBits == GameProject.BULLET_BIT)
                ((Bullet)fixA.getUserData()).setToDestroy();
            else
                ((Bullet)fixB.getUserData()).setToDestroy();
            break;
        case GameProject.BULLET_BIT | GameProject.ENEMY_BIT:
        	if(fixA.getFilterData().categoryBits == GameProject.ENEMY_BIT)
                ((Enemy)fixA.getUserData()).hitbyBullet((Bullet) fixB.getUserData());
            else
                ((Enemy)fixB.getUserData()).hitbyBullet((Bullet) fixA.getUserData());
            break;
        case GameProject.PLAYER_BIT | GameProject.COIN_BIT:
            ((Coin)fixB.getUserData()).getCoin();
            break;
        case GameProject.PLAYER_BIT | GameProject.FINAL_BIT:
        	if(fixA.getFilterData().categoryBits == GameProject.PLAYER_BIT) {
        		((Helicoptero) fixB.getUserData()).bind(((Player) fixA.getUserData()));
            }else {
            	((Helicoptero) fixA.getUserData()).bind(((Player) fixB.getUserData()));
            }
        	break;
        case GameProject.PLAYER_HEAD_BIT | GameProject.FINAL_BIT:
        	if(fixA.getFilterData().categoryBits == GameProject.PLAYER_HEAD_BIT) {
        		((Helicoptero) fixB.getUserData()).bind(((Player) fixA.getUserData()));
            }else {
            	((Helicoptero) fixA.getUserData()).bind(((Player) fixB.getUserData()));
            }
        	  break;
        case GameProject.GROUND_BIT | GameProject.ENEMY_BIT:
    		if(fixA.getFilterData().categoryBits == GameProject.ENEMY_BIT) {
    			((Enemy)fixA.getUserData()).hitByEnemy((Enemy)fixA.getUserData());
    		}else {
    			((Enemy)fixB.getUserData()).hitByEnemy((Enemy)fixB.getUserData());
    		}
    		  break; 
        case GameProject.FINAL_BIT | GameProject.ENEMY_BIT:
    		if(fixA.getFilterData().categoryBits == GameProject.ENEMY_BIT) {
    			((Enemy)fixA.getUserData()).hitByEnemy((Enemy)fixA.getUserData());
    		}else {
    			((Enemy)fixB.getUserData()).hitByEnemy((Enemy)fixB.getUserData());
    		}
    		  break; 
        }
    }
    

    

    @Override
    public void endContact(Contact contact) {
    	//Gdx.app.log("End Contact", "");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}