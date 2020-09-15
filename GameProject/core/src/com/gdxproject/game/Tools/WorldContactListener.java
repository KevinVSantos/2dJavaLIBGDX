package com.gdxproject.game.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.gdxproject.game.GameProject;
import com.gdxproject.game.Sprites.Enemies.Enemy;
import com.gdxproject.game.Sprites.Items.Item;
import com.gdxproject.game.Sprites.Player.FireBall;
import com.gdxproject.game.Sprites.Player.Player;
import com.gdxproject.game.Sprites.Structure.InteractiveTiledObject;;;


public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
    	//recebe os objetos que se chocaram
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;//bit de contato que difine qual é o objeto
        
       
        //switch para o tipo de contato 
        switch (cDef){           
        case GameProject.MARIO_HEAD_BIT | GameProject.BRICK_BIT:
        case GameProject.MARIO_HEAD_BIT | GameProject.COIN_BIT:
            if(fixA.getFilterData().categoryBits == GameProject.MARIO_HEAD_BIT)
                ((InteractiveTiledObject) fixB.getUserData()).onHeadHit((Player) fixA.getUserData());
            else
                ((InteractiveTiledObject) fixA.getUserData()).onHeadHit((Player) fixB.getUserData());
            break;
        case GameProject.ENEMY_HEAD_BIT | GameProject.MARIO_BIT:
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
        case GameProject.MARIO_BIT | GameProject.ENEMY_BIT: 
            if(fixA.getFilterData().categoryBits == GameProject.MARIO_BIT)
                ((Player) fixA.getUserData()).hit((Enemy)fixB.getUserData());
            else
                ((Player) fixB.getUserData()).hit((Enemy)fixA.getUserData());
            break;
        case GameProject.ENEMY_BIT | GameProject.ENEMY_BIT:
            ((Enemy)fixA.getUserData()).hitByEnemy((Enemy)fixB.getUserData());
            ((Enemy)fixB.getUserData()).hitByEnemy((Enemy)fixA.getUserData());
            break;
        case GameProject.ITEM_BIT | GameProject.OBJECT_BIT:
            if(fixA.getFilterData().categoryBits == GameProject.ITEM_BIT)
                ((Item)fixA.getUserData()).reverseVelocity(true, false);
            else
                ((Item)fixB.getUserData()).reverseVelocity(true, false);
            break;
        case GameProject.ITEM_BIT | GameProject.MARIO_BIT:
            if(fixA.getFilterData().categoryBits == GameProject.ITEM_BIT)
                ((Item)fixA.getUserData()).use((Player) fixB.getUserData());
            else
                ((Item)fixB.getUserData()).use((Player) fixA.getUserData());
            break;
        case GameProject.FIREBALL_BIT | GameProject.OBJECT_BIT:
            if(fixA.getFilterData().categoryBits == GameProject.FIREBALL_BIT)
                ((FireBall)fixA.getUserData()).setToDestroy();
            else
                ((FireBall)fixB.getUserData()).setToDestroy();
            break;
        case GameProject.FIREBALL_BIT | GameProject.ENEMY_BIT:
        	if(fixA.getFilterData().categoryBits == GameProject.ENEMY_BIT)
                ((Enemy)fixA.getUserData()).hitbyFireball((FireBall) fixB.getUserData());
            else
                ((Enemy)fixB.getUserData()).hitbyFireball((FireBall) fixA.getUserData());
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