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


/**
 * Classe do protagonista.
 */
public class Player extends Sprite {

	/**
	 * Enumerador de estado.
	 */
	public enum State { FALLING, JUMPING, STANDING, RUNNING, SHOOTING, GROWING, DEAD, SAVE };
	  
	/**
	 * Status atual.
	 */
	public State currentState;
    
    /**
     * Status anterior.
     */
    public State previousState;

    /**
     * Mundo do jogo.
     */
    public World world;
    
    /**
     * Corpo de colisões. 
     */
    public Body b2body;
    
    /**
     * Animação do player parado.
     */
    private Animation<TextureRegion> playerStand;    
    
    /**
     * Animação do player correndo.
     */
    private Animation<TextureRegion>  playerRun;
    
    /**
     * Animação do player pulando.
     */
    private Animation<TextureRegion>  playerJump;
    
    /**
     * Animação do player atirando.
     */
    private Animation<TextureRegion>  playerShoot;
    
    /**
     * Tempo decorrido entre animações
     */
    private float stateTimer;
    
    /**
     * Delay entre disparos.
     */
    private float shootTimer;
    
    /**
     * Direção da corrida.
     */
    private boolean runningRight;
    
    /**
     * Player esta salvo.
     */
    public boolean saved = false;
    
    /**
     * Animação do player morrendoo.
     */
    private Animation<TextureRegion> playerDead;     
    
    /**
     * Singleton do player.
     */
    public static Player instance;   
     
    /**
     * Se o player está morto.
     */
    private boolean playerIsDead;
    
    /**
     * Estrutura do jogador.
     */
    protected Fixture fixture;

    /**
     * Lista de Balas.
     */
    private Array<Bullet> bullets;
    
	/**
	 * Cria o personagem Principal.
	 */
    public Player(PlayScreen screen){
    	super();
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        instance = this;
        
        Array<TextureRegion> frames = new Array<TextureRegion>();

        //get run animation frames and add them to marioRun Animation
        for(int i = 1; i < 4; i++)
        	frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/player.png")),  4+(50*i), 43, 38, 40));
        playerRun = new Animation<TextureRegion>(0.1f, frames);
        
        frames.clear();
        
        for(int i = 1; i < 5; i++)
        	frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/player.png")),  4+(50*i), 129, 38, 40));
        playerShoot = new Animation<TextureRegion>(1f, frames);

        frames.clear();
        
         frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/player.png")),  4+(50), 43, 38, 40));
         playerJump = new Animation<TextureRegion>(0.1f, frames);

        frames.clear();
        
        for(int i = 1; i < 5; i++)
        	frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/player.png")),  4+(50*i), 3, 38, 40));
        	//frames.add(new TextureRegion(new Texture(Gdx.files.internal("enemyb.png")),  0, 0, 544, 544));
        	
        playerStand = new Animation<TextureRegion>(1f, frames);

       frames.clear();
       
       for(int i = 1; i < 9; i++)
       frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/player.png")),   12+(50*i)+i, 172, 38, 40));
       for(int i = 1; i < 8; i++)
       frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/player.png")),   12+(50*i)+i, 215, 38, 40));
       
       for(int i = 1; i < 8; i++)
       frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/player.png")),   12+(50*8), 215, 38, 40));
       
       
       playerDead = new Animation<TextureRegion>(0.2f, frames);


        
        definePlayer();
        setBounds(0, 0, 22 / GameProject.PPM, 30 / GameProject.PPM);
        
        
        bullets = new Array<Bullet>();
        
    }

    /**
     * Método responsável por atualizar o que será exibido.
     * @param dt - Tempo desde a última atualização
     */
    public void update(float dt){
    	
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 - 6 / GameProject.PPM);       
        
        if(shootTimer ==0)
        	currentState = getState();
    	if(currentState == State.SHOOTING && shootTimer <=10) {
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
    
    /**
     * Seleciona o frame da bala a ser exibido.
     * @param dt - Tempo decorrido.
     */
    public TextureRegion getFrameFire(float dt){
        

        TextureRegion region;

        region = playerShoot.getKeyFrame(dt, true);
		

        if((b2body.getLinearVelocity().x > 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }

        else if((b2body.getLinearVelocity().x < 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;

    }
    
    /**
     * Seleciona o frame a ser exibido.
     * @param dt - Tempo decorrido.
     */
    public TextureRegion getFrame(float dt){     

        TextureRegion region;

        switch(currentState){
            case DEAD:
                region = playerDead.getKeyFrame(stateTimer, true);
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
                region =  playerStand.getKeyFrame(stateTimer, true);
                break;
        }

        if((b2body.getLinearVelocity().x > 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }

        else if((b2body.getLinearVelocity().x < 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;

    }

    /**
     * Retorna o status do personagem.
     */
    public State getState(){
        if(playerIsDead)
            return State.DEAD;
        else if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            return State.SHOOTING;
        else if((b2body.getLinearVelocity().y > 0) || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
    		return State.JUMPING;
        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }
    
   /**
    * Mata o personagem.
    */
   public void die() {

        if (!isDead()) {        	
            GameProject.manager.get("audio/music/Blinding_Lights.mp3", Music.class).stop();
            
            GameProject.manager.get("audio/effects/death.mp3", Sound.class).play();
            playerIsDead = true;
            GameProject.manager.get("audio/effects/gameover.mp3", Music.class).play();
            
        }
    }
   
   /**
    * Finaliza o player.
    */
   public void finish(){
       Filter filter = new Filter();
       filter.maskBits = GameProject.NOTHING_BIT;

       for (Fixture fixture : b2body.getFixtureList()) {
           fixture.setFilterData(filter);
       }
      
   }

   /**
    * Verifica se o player está morto.
    */
    public boolean isDead(){
        return playerIsDead;
    }
    
    /**
     * Seta o player como morto.
     */
    public void setDead(boolean status) {
    	playerIsDead  = status;
    }

    /**
     * Retorna o tempo entre animações.
     */
    public float getStateTimer(){
        return stateTimer;
    }

	/**
	 * Ao ser atingido por um inimigo.
	 */
    public void hit(Enemy enemy){
    	die();
    }
    
    /**
     * Pula.
     */
    public void jump(){
        if ( currentState != State.JUMPING &&  currentState != State.FALLING) {
            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
            currentState = State.JUMPING;
        } 
    }
    
    /**
     * Define um jogador.
     */
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
                GameProject.ITEM_BIT |
                GameProject.FINAL_BIT;

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
	
	 /**
	  * Atirar.
	  */
 	public void fire(PlayScreen screen){
 		
 		bullets.add(new Bullet(screen, b2body.getPosition().x, b2body.getPosition().y, runningRight ? true : false));
    }

 	/**
 	 * Seta o player como salvo.
 	 */
 	public void setSaved(boolean status)
 	{
 		saved = status;
 	}
 	
 	/**
     * Método responsável por desenhar na tela.
     */
    public void draw(Batch batch){
    	if(!saved)
    	{
	        super.draw(batch);
	        for(Bullet ball : bullets)
	            ball.draw(batch);
    	}
    }

}
