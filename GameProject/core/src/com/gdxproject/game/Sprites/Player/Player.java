package com.gdxproject.game.Sprites.Player;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import com.gdxproject.game.Sprites.Enemies.Turtle;



public class Player extends Sprite {
	  public enum State { FALLING, JUMPING, STANDING, RUNNING, GROWING, DEAD };
	    public State currentState;
	    public State previousState;

    public World world;
    public Body b2body;
    private TextureRegion marioStand;
    
    private Animation<TextureRegion>  marioRun;
    private Animation<TextureRegion>  marioJump;
    private float stateTimer;
    private boolean runningRight;
    
    private TextureRegion bigMarioStand;
    private Animation<TextureRegion> bigMarioJump;
    private Animation<TextureRegion> bigMarioRun;
    
    private TextureRegion marioDead;      
    private Animation<TextureRegion> growMario;

    
    private boolean marioIsBig;
    private boolean runGrowAnimation;
    private boolean timeToDefineBigMario;
    private boolean timeToRedefineMario;
    private boolean marioIsDead;
    
    protected Fixture fixture;

    private Array<FireBall> fireballs;
    
    
    public Player(PlayScreen screen){
    	super(screen.getAtlas().findRegion("little_mario"));
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        
        
        Array<TextureRegion> frames = new Array<TextureRegion>();

        //get run animation frames and add them to marioRun Animation
        for(int i = 1; i < 10; i++)
            //frames.add(new TextureRegion(getTexture(), i * 16, 10, 16, 16));
        	frames.add(new TextureRegion(screen.getAtlas().findRegion("little_mario"), i * 16, 0, 16, 16));
        marioRun = new Animation(0.1f, frames);

        frames.clear();
        
        for(int i = 4; i < 6; i++)
            frames.add(new TextureRegion(getTexture(), i * 16, 10, 16, 16));
        marioJump = new Animation(0.1f, frames);

        frames.clear();
        
        for(int i = 1; i < 4; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), i * 16, 0, 16, 32));
        bigMarioRun = new Animation(0.1f, frames);

        frames.clear();
        
      //get jump animation frames and add them to marioJump Animation
        for(int i = 4; i < 6; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"),  i * 16, 0, 16, 32));
        bigMarioRun = new Animation(0.1f, frames);

        frames.clear();

      //get set animation frames from growing mario
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), 240, 0, 16, 32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), 0, 0, 16, 32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), 240, 0, 16, 32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), 0, 0, 16, 32));
        growMario = new Animation(0.2f, frames);
        
        frames.clear();

        

        
      //create texture region for mario standing
        marioStand = new TextureRegion(screen.getAtlas().findRegion("little_mario"), 0, 0, 16, 16);
        bigMarioStand = new TextureRegion(screen.getAtlas().findRegion("big_mario"), 0, 0, 16, 32);

        //create dead mario texture region
        marioDead = new TextureRegion(screen.getAtlas().findRegion("little_mario"), 96, 0, 16, 16);

        
        defineMario();
        //marioStand = new TextureRegion(getTexture(), 0, 10, 16, 16);
        setBounds(0, 0, 16 / GameProject.PPM, 16 / GameProject.PPM);
        setRegion(marioStand);
        
        
        fireballs = new Array<FireBall>();
        
    }


    public void update(float dt){
    	
         //update our sprite to correspond with the position of our Box2D body
         if(marioIsBig)
             setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 - 6 / GameProject.PPM);
         else
             setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
         
         //update sprite with the correct frame depending on marios current action
         setRegion(getFrame(dt));
         if(timeToDefineBigMario)
             defineBigMario();
         if(timeToRedefineMario) {
        	 redefineMario();
         }
             

        for(FireBall  ball : fireballs) {
             ball.update(dt);
             if(ball.isDestroyed())
                 fireballs.removeValue(ball, true);
         }
    }
    
    public TextureRegion getFrame(float dt){
        //get marios current state. ie. jumping, running, standing...
        currentState = getState();

        TextureRegion region;

        //depending on the state, get corresponding animation keyFrame.
        switch(currentState){
            case DEAD:
                region = marioDead;
                break;
            case GROWING:
                region = growMario.getKeyFrame(stateTimer);
                if(growMario.isAnimationFinished(stateTimer)) {
                    runGrowAnimation = false;
                }
                break;
            case JUMPING:
                region =  marioJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = marioRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region =  marioStand;
                break;
        }

        //if mario is running left and the texture isnt facing left... flip it.
        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }

        //if mario is running right and the texture isnt facing right... flip it.
        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
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
        if(marioIsDead)
            return State.DEAD;
        else if(runGrowAnimation)
            return State.GROWING;
        else
    	if((b2body.getLinearVelocity().y > 0 /*&& currentState == State.JUMPING*/) || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
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
    
    public void grow(){
        if( !isBig() ) {
            runGrowAnimation = true;
            marioIsBig = true;
            timeToDefineBigMario = true;
            setBounds(getX(), getY(), getWidth(), getHeight() * 2);
            GameProject.manager.get("mario/audio/sounds/powerup.wav", Sound.class).play();
        }
    }
    
   public void die() {

        if (!isDead()) {

            GameProject.manager.get("mario/audio/music/mario_music.ogg", Music.class).stop();
            GameProject.manager.get("mario/audio/sounds/mariodie.wav", Sound.class).play();
            marioIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = GameProject.NOTHING_BIT;

            for (Fixture fixture : b2body.getFixtureList()) {
                fixture.setFilterData(filter);
            }

            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
        }
    }

    public boolean isDead(){
        return marioIsDead;
    }

    public float getStateTimer(){
        return stateTimer;
    }

    public boolean isBig(){
        return marioIsBig;
    }

    public void hit(Enemy enemy){
        if(enemy instanceof Turtle && ((Turtle) enemy).currentState == Turtle.State.STANDING_SHELL)
            ((Turtle) enemy).kick(enemy.b2body.getPosition().x > b2body.getPosition().x ? Turtle.KICK_RIGHT : Turtle.KICK_LEFT);
        else {
            if (marioIsBig) {
                marioIsBig = false;
                timeToRedefineMario = true;                
                setBounds(getX(), getY(), getWidth(), getHeight() / 2);
                GameProject.manager.get("mario/audio/sounds/powerdown.wav", Sound.class).play();
               
                //
            } else {
            	 	die();
            }
        }
    }
    
    public void jump(){
        if ( currentState != State.JUMPING ) {
            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
            currentState = State.JUMPING;
        }
    }
    
    public void redefineMario(){
    	
    	Vector2 position = b2body.getPosition();
        world.destroyBody(b2body);

        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        Gdx.app.log("massa","DIED");
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / GameProject.PPM);
        fdef.filter.categoryBits = GameProject.MARIO_BIT;
        fdef.filter.maskBits = GameProject.GROUND_BIT |
                GameProject.COIN_BIT |
                GameProject.BRICK_BIT |
                GameProject.ENEMY_BIT |
                GameProject.OBJECT_BIT |
                GameProject.ENEMY_HEAD_BIT |
                GameProject.ITEM_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / GameProject.PPM, 6 / GameProject.PPM), new Vector2(2 / GameProject.PPM, 6 / GameProject.PPM));
        fdef.filter.categoryBits = GameProject.MARIO_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);

        timeToRedefineMario = false;

    }
    
	public void defineMario(){
		BodyDef bdef = new BodyDef();
        bdef.position.set(32 / GameProject.PPM, 32 / GameProject.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / GameProject.PPM);
        fdef.filter.categoryBits = GameProject.MARIO_BIT;
        fdef.filter.maskBits = GameProject.GROUND_BIT |
                GameProject.COIN_BIT |
                GameProject.BRICK_BIT |
                GameProject.ENEMY_BIT |
                GameProject.OBJECT_BIT |
                GameProject.ENEMY_HEAD_BIT |
                GameProject.ITEM_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / GameProject.PPM, 6 / GameProject.PPM), new Vector2(2 / GameProject.PPM, 6 / GameProject.PPM));
        fdef.filter.categoryBits = GameProject.MARIO_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);
    }
	
	 public void defineBigMario(){
		 Vector2 currentPosition = b2body.getPosition();
	        world.destroyBody(b2body);

	        BodyDef bdef = new BodyDef();
	        bdef.position.set(currentPosition.add(0, 10 / GameProject.PPM));
	        bdef.type = BodyDef.BodyType.DynamicBody;
	        b2body = world.createBody(bdef);

	        FixtureDef fdef = new FixtureDef();
	        CircleShape shape = new CircleShape();
	        shape.setRadius(6 / GameProject.PPM);
	        fdef.filter.categoryBits = GameProject.MARIO_BIT;
	        fdef.filter.maskBits = GameProject.GROUND_BIT |
	                GameProject.COIN_BIT |
	                GameProject.BRICK_BIT |
	                GameProject.ENEMY_BIT |
	                GameProject.OBJECT_BIT |
	                GameProject.ENEMY_HEAD_BIT |
	                GameProject.ITEM_BIT;

	        fdef.shape = shape;
	        b2body.createFixture(fdef).setUserData(this);
	        shape.setPosition(new Vector2(0, -14 / GameProject.PPM));
	        b2body.createFixture(fdef).setUserData(this);

	        EdgeShape head = new EdgeShape();
	        head.set(new Vector2(-2 / GameProject.PPM, 6 / GameProject.PPM), new Vector2(2 / GameProject.PPM, 6 / GameProject.PPM));
	        fdef.filter.categoryBits = GameProject.MARIO_HEAD_BIT;
	        fdef.shape = head;
	        fdef.isSensor = true;

	        b2body.createFixture(fdef).setUserData(this);
	        timeToDefineBigMario = false;
	    }
	 
	 	public void fire(PlayScreen screen){
	        fireballs.add(new FireBall(screen, b2body.getPosition().x, b2body.getPosition().y, runningRight ? true : false));
	    }

	    public void draw(Batch batch){
	        super.draw(batch);
	        for(FireBall ball : fireballs)
	            ball.draw(batch);
	    }

}
