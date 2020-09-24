package com.gdxproject.game.Sprites.Enemies;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.gdxproject.game.GameProject;
import com.gdxproject.game.Scenes.Hud;
import com.gdxproject.game.Screens.PlayScreen;
import com.gdxproject.game.Sprites.Player.Bullet;
import com.gdxproject.game.Sprites.Player.Player;

/**
 * Primeiro Inimigo.
 */
public class Enemy1 extends Enemy
{
	/**
	 * Tempo entre animações.
	 */
    private float stateTime;
    
    /**
     * Região da animação de andar.
     */
    private Animation<TextureRegion> walkAnimation;
    
    /**
     * Frames para exibição.
     */
    private Array<TextureRegion> frames;
    
    /**
     * Deve ser destruído.
     */
    private boolean setToDestroy;
    
    /**
     * Está destruido.
     */
    private boolean destroyed;

    /**
     * Cria o inimigo.
     * @param screen - Tela atual.
     * @param x - Posição X .
     * @param y - Posição Y.
     */
    public Enemy1(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for(int i = 0; i < 4; i++) 
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/Enemy.png")),  4+(i * 24), 5, 16, 20));
        walkAnimation = new Animation<TextureRegion>(0.4f, frames);
        stateTime = 0;
       
        setBounds(getX(), getY(), 16 / GameProject.PPM, 24 / GameProject.PPM);
        setToDestroy = false; 
        destroyed = false;
    }

    /**
     * Método responsável por atualizar o que será exibido.
     * @param dt - Tempo desde a última atualização.
     */
    public void update(float dt){
        stateTime += dt;   	
        if(setToDestroy && !destroyed){ 	
            world.destroyBody(b2body);
            destroyed = true;
           setRegion(getFrame(walkAnimation.getKeyFrame(stateTime, true),false));
           
            
            stateTime = 0;
        }
        else if(!destroyed) {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
           setRegion(getFrame(walkAnimation.getKeyFrame(stateTime, true),true));
        }
    }
    
    /**
     * Seleciona o frame a ser exibido.
     */
    public TextureRegion getFrame(TextureRegion region, boolean t){
  
    	if(t) {
    		
            if((b2body.getLinearVelocity().x < 0 ) && !region.isFlipX()){
                region.flip(true, false);
            }

            else if((b2body.getLinearVelocity().x > 0) && region.isFlipX()){
                region.flip(true, false);
            }

    		
    	}else {
    		
    		region.flip(false, true);
    		
    	}
     
        //return our final adjusted frame
        return region;

    }

    /**
     * Defini o inimigo.
     */
    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / GameProject.PPM);
        fdef.filter.categoryBits = GameProject.ENEMY_BIT;
        fdef.filter.maskBits = GameProject.GROUND_BIT |
                GameProject.COIN_BIT |
                GameProject.ENEMY_BIT |
                GameProject.OBJECT_BIT |
                GameProject.PLAYER_BIT |                
                GameProject.HOLE_BIT |    
                GameProject.ENEMY_HEAD_BIT |
                GameProject.ITEM_BIT |
                GameProject.FINAL_BIT |
                GameProject.BULLET_BIT |
                GameProject.ENEMY_GROUND_BIT;
        
        

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
        shape.setPosition(new Vector2(0, -7 / GameProject.PPM));
        b2body.createFixture(fdef).setUserData(this);

        //Create the Head here:
        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-5, 8).scl(1 / GameProject.PPM);
        vertice[1] = new Vector2(5, 8).scl(1 / GameProject.PPM);
        vertice[2] = new Vector2(-3, 10).scl(1 / GameProject.PPM);
        vertice[3] = new Vector2(3, 10).scl(1 / GameProject.PPM);
        head.set(vertice);

        fdef.shape = head;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = GameProject.ENEMY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);

    }

    /**
     * Método responsável por desenhar na tela.
     */
    public void draw(Batch batch){
       if(!destroyed || stateTime < 1)
            super.draw(batch);
    }

    /**
     * Se o plauer pular na cabeça do inimigo.
     * @param mario - Player.
     */
    @Override
   public void hitOnHead(Player mario) {
        setToDestroy = true;
        GameProject.manager.get("audio/stomp.wav", Sound.class).play();
        Hud.addScore(185);

    }
    
    /**
     * Se uma bala acertar o inimigo.
     * @param bullet - Bala que o atingiu.
     */
    @Override
    public void hitbyBullet(Bullet bullet) {
    	bullet.setToDestroy();
         setToDestroy = true;
         GameProject.manager.get("audio/stomp.wav", Sound.class).play();
         Hud.addScore(100);

         
     }
     
    /**
     * Se inimigos se chocarem.
     * @param enemy - Inimigo que se chocou.
     */
    @Override
    public void hitByEnemy(Enemy enemy) {
            reverseVelocity(true, false);
    }
    
    /**
     * Verifica se está destruído.
     */
    @Override
    public boolean isDestroyed() {
    	return destroyed;
    }
    
    /**
     * Ao atingir um buraco.
     */
    public void hitHole() {
        setToDestroy = true;
    }
    
    
}