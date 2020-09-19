package com.gdxproject.game.Sprites.Player;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.gdxproject.game.GameProject;
import com.gdxproject.game.Screens.PlayScreen;
import com.gdxproject.game.Sprites.Enemies.Enemy;



public class Player extends Sprite {
	  public enum State { FALLING, JUMPING, STANDING, RUNNING, SHOOTING, GROWING, DEAD };
	    public State currentState;
	    public State previousState;

    public World world;
    public Body b2body;
    private TextureRegion playerStand;
    
    private Animation<TextureRegion>  playerRun;
    private Animation<TextureRegion>  playerJump;
    private Animation<TextureRegion>  playerShoot;
    private float stateTimer;
    private float shootTimer;
    private boolean runningRight;
    
    
    private TextureRegion playerDead;      
    private boolean playerIsDead;
    
    protected Fixture fixture;

    private Array<Bullet> bullets;
    
    
    public Player(PlayScreen screen){
    	super(screen.getAtlas().findRegion("little_mario"));
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        
        
        Array<TextureRegion> frames = new Array<TextureRegion>();

        //get run animation frames and add them to marioRun Animation
        for(int i = 1; i < 4; i++)
        	frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/player.png")),  4+(50*i), 43, 38, 40));
        playerRun = new Animation(0.1f, frames);
        
        frames.clear();
        
        for(int i = 1; i < 4; i++)
        	frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/player.png")),  4+(50*i), 129, 38, 40));
        playerShoot = new Animation(0.4f, frames);

        frames.clear();
        
         frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/player.png")),  4+(50), 43, 38, 40));
         playerJump = new Animation(0.1f, frames);

        frames.clear();
        
        

        
      //create texture region for mario standing
        playerStand = new TextureRegion(new Texture(Gdx.files.internal("sprites/player.png")),  4, 3, 38, 40);

        //create dead mario texture region
        playerDead = new TextureRegion(new Texture(Gdx.files.internal("sprites/player.png")),  4, 3, 38, 40);

        
        definePlayer();
        //marioStand = new TextureRegion(getTexture(), 0, 10, 16, 16);
        setBounds(0, 0, 22 / GameProject.PPM, 30 / GameProject.PPM);
        setRegion(playerStand);
        
        
        bullets = new Array<Bullet>();
        
    }


    public void update(float dt){
    	
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 - 6 / GameProject.PPM);       
         //update sprite with the correct frame depending on marios current action
        
        currentState = getState();
    	if(currentState == State.SHOOTING && shootTimer <=4) {
    		setRegion(getFrameFire(dt));
    		shootTimer ++;
    	}    		
    	else {
    		shootTimer = 0;
    		setRegion(getFrame(dt));
    	}
    		


        for(Bullet  bullet : bullets) {
        	bullet.update(dt);
             if(bullet.isDestroyed())
            	 bullets.removeValue(bullet, true);
         }
    }
    
    public TextureRegion getFrameFire(float dt){
        //get marios current state. ie. jumping, running, standing...
        

        TextureRegion region;

        region = playerShoot.getKeyFrame(dt, true);
		

        //if mario is running left and the texture isnt facing left... flip it.
        if((b2body.getLinearVelocity().x > 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }

        //if mario is running right and the texture isnt facing right... flip it.
        else if((b2body.getLinearVelocity().x < 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }

        //if the current state is the same as the previous state increase the state timer.
        //otherwise the state has changed and we need to reset timer.
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        //update previous state
        previousState = currentState;
        //return our final adjusted frame
        return region;

    }
    
    public TextureRegion getFrame(float dt){
        //get marios current state. ie. jumping, running, standing...
        

        TextureRegion region;

        //depending on the state, get corresponding animation keyFrame.
        switch(currentState){
            case DEAD:
                region = playerDead;
                break;
            case JUMPING:
                region =  playerJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = playerRun.getKeyFrame(stateTimer, true);
                break;            
            case FALLING:
            case STANDING:
            default:
                region =  playerStand;
                break;
        }

        //if mario is running left and the texture isnt facing left... flip it.
        if((b2body.getLinearVelocity().x > 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }

        //if mario is running right and the texture isnt facing right... flip it.
        else if((b2body.getLinearVelocity().x < 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }

        //if the current state is the same as the previous state increase the state timer.
        //otherwise the state has changed and we need to reset timer.
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        //update previous state
        previousState = currentState;
        //return our final adjusted frame
        return region;

    }

    public State getState(){
        //Test to Box2D for velocity on the X and Y-Axis
        //if mario is going positive in Y-Axis he is jumping... or if he just jumped and is falling remain in jump state
        if(playerIsDead)
            return State.DEAD;
        else if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            return State.SHOOTING;
        else if((b2body.getLinearVelocity().y > 0 /*&& currentState == State.JUMPING*/) || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
    		return State.JUMPING;
        //if negative in Y-Axis mario is falling
        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        //if mario is positive or negative in the X axis he is running
        else if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        //if none of these return then he must be standing
        else
            return State.STANDING;
    }
    
    
   public void die() {

        if (!isDead()) {
        	//GameProject.manager.get("audio/effects/gameover.wav", Sound.class).play();
            GameProject.manager.get("audio/music/Blinding_Lights.mp3", Music.class).stop();
            GameProject.manager.get("audio/effects/death.mp3", Sound.class).play();
            playerIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = GameProject.NOTHING_BIT;

            for (Fixture fixture : b2body.getFixtureList()) {
                fixture.setFilterData(filter);
            }

            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
        }
    }

    public boolean isDead(){
        return playerIsDead;
    }

    public float getStateTimer(){
        return stateTimer;
    }


    public void hit(Enemy enemy){
    	die();
    }
    
    public void jump(){
        if ( currentState != State.JUMPING &&  currentState != State.FALLING) {
            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
            currentState = State.JUMPING;
        } 
    }
    
    
	public void definePlayer(){
		BodyDef bdef = new BodyDef();
        bdef.position.set(32 / GameProject.PPM, 64 / GameProject.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / GameProject.PPM);
        fdef.filter.categoryBits = GameProject.PLAYER_BIT;
        fdef.filter.maskBits = GameProject.GROUND_BIT |
                GameProject.COIN_BIT |
                GameProject.ENEMY_BIT |
                GameProject.OBJECT_BIT |
                GameProject.HOLE_BIT |
                GameProject.ENEMY_HEAD_BIT |
                GameProject.ITEM_BIT;

        fdef.shape = shape;
         b2body.createFixture(fdef).setUserData(this);
	        shape.setPosition(new Vector2(0, -14 / GameProject.PPM));
	        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / GameProject.PPM, 6 / GameProject.PPM), new Vector2(2 / GameProject.PPM, 6 / GameProject.PPM));
        fdef.filter.categoryBits = GameProject.PLAYER_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);
    }
	
	/*public void definePlayer(){
		 Vector2 currentPosition = b2body.getPosition();
	        world.destroyBody(b2body);

	        BodyDef bdef = new BodyDef();
	        bdef.position.set(currentPosition.add(0, 10 / GameProject.PPM));
	        bdef.type = BodyDef.BodyType.DynamicBody;
	        b2body = world.createBody(bdef);

	        FixtureDef fdef = new FixtureDef();
	        CircleShape shape = new CircleShape();
	        shape.setRadius(6 / GameProject.PPM);
	        fdef.filter.categoryBits = GameProject.PLAYER_BIT;
	        fdef.filter.maskBits = GameProject.GROUND_BIT |
	                GameProject.COIN_BIT |
	                GameProject.ENEMY_BIT |
	                GameProject.OBJECT_BIT |
	                GameProject.HOLE_BIT |
	                GameProject.ENEMY_HEAD_BIT |
	                GameProject.ITEM_BIT;

	        fdef.shape = shape;
	        b2body.createFixture(fdef).setUserData(this);
	        shape.setPosition(new Vector2(0, -14 / GameProject.PPM));
	        b2body.createFixture(fdef).setUserData(this);

	        EdgeShape head = new EdgeShape();
	        head.set(new Vector2(-2 / GameProject.PPM, 6 / GameProject.PPM), new Vector2(2 / GameProject.PPM, 6 / GameProject.PPM));
	        fdef.filter.categoryBits = GameProject.PLAYER_HEAD_BIT;
	        fdef.shape = head;
	        fdef.isSensor = true;

	        b2body.createFixture(fdef).setUserData(this);
	    }*/
	
	
	 
	 	public void fire(PlayScreen screen){
	 		
	 		bullets.add(new Bullet(screen, b2body.getPosition().x, b2body.getPosition().y, runningRight ? true : false));
	    }

	    public void draw(Batch batch){
	        super.draw(batch);
	        for(Bullet ball : bullets)
	            ball.draw(batch);
	    }

}
