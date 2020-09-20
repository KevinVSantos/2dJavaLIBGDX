package com.gdxproject.game.Sprites.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.FrictionJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Array;
import com.gdxproject.game.GameProject;
import com.gdxproject.game.Screens.PlayScreen;
import com.gdxproject.game.Sprites.Player.Player;
import com.gdxproject.game.Sprites.Player.Player.State;

public class Helicoptero extends Sprite {

	//atributos que serão setados para os inimigos
    protected World world;
    protected PlayScreen screen; 
    public Body b2body;
    public Vector2 velocity;
    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    private boolean startSave = false;
    
    private float startSaveTime;

    private boolean setToDestroy;
    private boolean destroyed;
  //Musicas
    private Music music;
	
	public Helicoptero(PlayScreen screen, float x, float y) {
       
        this.world = screen.getWorld(); //  recebe o mundo da screen
        this.screen = screen; //recebe a screen
        
        setPosition(x, y);
        System.out.println(x);

        System.out.println(y);

        defineHelicoptero(); // classe que será responsavel por definir o inimigo
        b2body.setActive(false); 
        
        frames = new Array<TextureRegion>();
        
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("helicoptero/helicoptero.png")), 0, 0, 74, 76));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("helicoptero/helicoptero.png")), 74, 0, 75, 76));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("helicoptero/helicoptero.png")), 146, 0, 75, 76));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("helicoptero/helicoptero.png")), 218, 0, 75, 76));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("helicoptero/helicoptero.png")), 298, 0, 75, 76));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("helicoptero/helicoptero.png")), 372, 0, 85, 76));
        
        walkAnimation = new Animation(0.2f, frames);
        stateTime = 0;
       
        setBounds(getX(), getY(), 100 / GameProject.PPM, 75 / GameProject.PPM);
        setToDestroy = false; 
        destroyed = false;
        
        
    }
	
	protected void defineHelicoptero() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        b2body.setGravityScale(0.0f);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(20 / GameProject.PPM);
        shape.setPosition(new Vector2(-0.1f, -0.1f));
        fdef.filter.categoryBits = GameProject.FINAL_BIT;
        fdef.filter.maskBits = GameProject.GROUND_BIT |
                GameProject.PLAYER_BIT |
                GameProject.PLAYER_HEAD_BIT |
                GameProject.ENEMY_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }
	
	public void draw(Batch batch){
		
        if(!destroyed)
            super.draw(batch);
    }
    
	public void update(float dt){
        stateTime += dt;
        
        if(Player.instance.saved)
        {
        	if(!startSave)
        	{
        		startSave = true;
        		startSaveTime = 2;
        		b2body.setLinearVelocity(new Vector2(1, 1));
        	}else {
        		if(startSaveTime < 0)
        		{
        			Player.instance.currentState = State.SAVE;
        		}else {
        			startSaveTime -= dt;
        		}
        	}
        }
        if(setToDestroy && !destroyed){ 	
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
            stateTime = 0; 
        }else if(!destroyed) {
            //b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        }
    }
	
	public void bind(Player player) {
		
		music = GameProject.manager.get("audio/voice/coquinho.mp3", Music.class);
        music.play();
		player.finish();
		player.setSaved(true);
        this.screen.setStateGame();
	}
    
}
