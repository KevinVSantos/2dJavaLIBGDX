package com.gdxproject.game.Sprites.Player;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.gdxproject.game.GameProject;
import com.gdxproject.game.Screens.PlayScreen;


public class Bullet extends Sprite {

    PlayScreen screen;
    World world;
    //Array<TextureRegion> frames;
    Animation<TextureRegion> fireAnimation;
    float stateTime;
    boolean destroyed;  
    boolean setToDestroy;
    boolean fireRight;

    Body b2body;
    public Bullet(PlayScreen screen, float x, float y, boolean fireRight){
    
    	this.fireRight = fireRight;
        this.screen = screen;
        this.world = screen.getWorld(); 
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 0; i < 1; i++){
        	frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/bullet.png")), (i *10), 4, 35, 15));
        }
        fireAnimation = new Animation(0.2f, frames);
        setRegion(fireAnimation.getKeyFrame(0));
        setBounds(x, y, 14 / GameProject.PPM, 6 / GameProject.PPM);
        defineFireBall();
    }

    public void defineFireBall(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(fireRight ?  getX() + 12 /GameProject.PPM : getX() - 12 /GameProject.PPM, getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        if(!world.isLocked())
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(3 / GameProject.PPM);
        fdef.filter.categoryBits = GameProject.BULLET_BIT;
        fdef.filter.maskBits = GameProject.GROUND_BIT |
                GameProject.COIN_BIT |
                GameProject.HOLE_BIT |
                GameProject.ENEMY_BIT |
                GameProject.OBJECT_BIT;

        fdef.shape = shape;
        fdef.restitution = 1;
        fdef.friction = 0;
        b2body.createFixture(fdef).setUserData(this);
        b2body.setGravityScale(0.0f);
        b2body.setLinearVelocity(new Vector2(fireRight ? 6 : -6, 0));
    }

    public void update(float dt){
        stateTime += dt;
        setRegion(fireAnimation.getKeyFrame(stateTime, true));
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        if((stateTime > 3 || setToDestroy) && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
        }
       /* if(b2body.getLinearVelocity().y > 2f)
            b2body.setLinearVelocity(b2body.getLinearVelocity().x, 2f);*/
        if((fireRight && b2body.getLinearVelocity().x < 0) || (!fireRight && b2body.getLinearVelocity().x > 0))
            setToDestroy();
    }

    public void setToDestroy(){
        setToDestroy = true;
    }

    public boolean isDestroyed(){
        return destroyed;
    }


}