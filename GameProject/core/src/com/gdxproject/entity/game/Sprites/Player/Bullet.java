package com.gdxproject.entity.game.Sprites.Player;


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
import com.gdxproject.system.game.GameProject;
import com.gdxproject.system.game.Screens.PlayScreen;


public class Bullet extends Sprite {

	/**
	 * Tela atual.
	 */
    PlayScreen screen;
    
    /**
     * Mundo do jogo.
     */
    World world;
    
    /**
     * Animação do disparo.
     */
    Animation<TextureRegion> fireAnimation;
    
    /**
     * Tempo entre animações
     */
    float stateTime;
    
    /**
     * Se está destruído.
     */
    boolean destroyed;  
    
    /**
     * Deve ser destruído.
     */
    boolean setToDestroy;
    
    /**
     * Direção do disparo.
     */
    boolean fireRight;

    /**
     * Corpo de colisão.
     */
    Body b2body;
    
    /**
     * Criação do disparo.
     * @param screen - Tela atual.
     * @param x - Posição no eixo X.
     * @param y - Posição no eixo Y.
     * @param fireRight - Direção do disparo.
     */
    public Bullet(PlayScreen screen, float x, float y, boolean fireRight){
    
    	this.fireRight = fireRight;
        this.screen = screen;
        this.world = screen.getWorld(); 
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 0; i < 1; i++){
        	frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/bullet.png")), (i *10), 4, 35, 15));
        }
        fireAnimation = new Animation<TextureRegion>(0.2f, frames);
        setRegion(fireAnimation.getKeyFrame(0));
        setBounds(x, y-0.1f, 14 / GameProject.PPM, 6 / GameProject.PPM);
        defineFireBall();
    }

    /**
     * Define disparo.
     */
    public void defineFireBall(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(fireRight ?  getX() + 12 /GameProject.PPM : getX() - 12 /GameProject.PPM, getY()+0.04f);
        bdef.type = BodyDef.BodyType.DynamicBody;
        if(!world.isLocked())
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(0.7f / GameProject.PPM);
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
        b2body.setLinearVelocity(new Vector2(!fireRight ? 6 : -6, 0));
        
    }

    /**
     * Método responsável por atualizar o que será exibido.
     * @param dt - Tempo desde a última atualização
     */
    public void update(float dt){
        stateTime += dt;
        
        if((stateTime > 3 || setToDestroy) && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
        }
        if(setToDestroy && !destroyed){ 	
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(getFrame(fireAnimation.getKeyFrame(stateTime, true), false));
           
            
            stateTime = 0;
        }
        else if(!destroyed) {
            //  b2body.setLinearVelocity(velocity);
            setRegion(getFrame(fireAnimation.getKeyFrame(stateTime, true), true));
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
           //setRegion(getFrame(stateTime));
        }
       /* if(b2body.getLinearVelocity().y > 2f)
            b2body.setLinearVelocity(b2body.getLinearVelocity().x, 2f);*/
       /* if((!fireRight && b2body.getLinearVelocity().x < 0) || (fireRight && b2body.getLinearVelocity().x > 0))
            setToDestroy();*/
    }
    
    /**
     * Seleciona o frame a ser exibido.
     * @param dt - Tempo decorrido.
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
     * Seta para ser destruído.
     */
    public void setToDestroy(){
        setToDestroy = true;
    }

    /**
     * Verifica se está destruído.
     */
    public boolean isDestroyed(){
        return destroyed;
    }


}