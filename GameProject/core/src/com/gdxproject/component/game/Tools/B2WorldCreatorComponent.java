package com.gdxproject.component.game.Tools;


import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.gdxproject.entity.game.Sprites.Enemies.Enemy;
import com.gdxproject.entity.game.Sprites.Enemies.Enemy1;
import com.gdxproject.entity.game.Sprites.Enemies.Enemy2;
import com.gdxproject.entity.game.Sprites.Items.Coin;
import com.gdxproject.entity.game.Sprites.Items.Helicoptero;
import com.gdxproject.system.game.GameProject;
import com.gdxproject.system.game.Screens.PlayScreen;


/**
 * Mundo do Jogo e instancia de seus blocos.
 */
public class B2WorldCreatorComponent {
	
	/**
	 * Lista de inimigos 1.
	 */
	private Array<Enemy1> enemies1;
	
	/**
	 * Lista de inimigos 2.
	 */
    private Array<Enemy2> enemies2;
    
    /**
     * Lista de coins.
     */
    private Array<Coin> coins;
    
    /**
     * Helicoptero.
     */
    private Helicoptero helicoptero;

    /**
     * Cria o mundo, inimigos e itens e define seus corpos.
     */
    public B2WorldCreatorComponent(PlayScreen screen){
    	World world = screen.getWorld(); // recebe o mundo da screen
    	TiledMap map = screen.getMap(); // recebe o tiledmap da  screen
    	BodyDef bdef = new BodyDef(); // recebe os corpos definidos na screen
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
         
         for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
         	Rectangle rect = ((RectangleMapObject) object).getRectangle();// cria um retangulo para definir o objeto
         	
         	bdef.type = BodyType.StaticBody;//defini o objeto como estatico
         	
         	//defini a posi��o do bdef 
         	bdef.position.set((rect.getX() + rect.getWidth() / 2 ) / GameProject.PPM, (rect.getY() + rect.getHeight() / 2)/ GameProject.PPM);
         	
         	// cria o corpo no mundo
         	body = world.createBody(bdef); 
         	shape.setAsBox((rect.getWidth() / 2)/ GameProject.PPM, (rect.getHeight() / 2)/ GameProject.PPM);// defini a forma
         	fdef.shape = shape;// passa a forma para o corpo
         	body.createFixture(fdef); //cria a fixture no mundo
         }
         
         for(MapObject object : map.getLayers().get(3).getObjects().getByType(PolygonMapObject.class)) {
          	Polygon pol = ((PolygonMapObject) object).getPolygon();// cria um retangulo para definir o objeto
 
          	bdef.type = BodyType.StaticBody;//defini o objeto como estatico
          	
          	//defini a posi��o do bdef
          	//bdef.position.set((pol.getX() / 2 ) / GameProject.PPM, (pol.getY()  / 2)/ GameProject.PPM);
          	
          	      	
          	bdef.position.set(((pol.getX())/ GameProject.PPM), ((pol.getY() )/ GameProject.PPM));
          	pol.setPosition(0, 0);
          	pol.setScale(1  / GameProject.PPM,1  / GameProject.PPM);

          	shape.set(pol.getTransformedVertices());
        
          	// cria o corpo no mundo
          	body = world.createBody(bdef);
          	fdef.shape = shape;// passa a forma para o corpo
          	body.createFixture(fdef); //cria a fixture no mundo
          }
          
         
         for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
         	Rectangle rect = ((RectangleMapObject) object).getRectangle(); // cria um retangulo para definir o objeto
         	
         	bdef.type = BodyType.StaticBody;  //defini o objeto como estatico
         	
         	//defini a posi��o do bdef
         	bdef.position.set((rect.getX() + rect.getWidth() / 2 ) / GameProject.PPM, (rect.getY() + rect.getHeight() / 2)/ GameProject.PPM);
         	
             
         	body = world.createBody(bdef); // cria o corpo no mundo
         	
         	shape.setAsBox((rect.getWidth() / 2)/ GameProject.PPM, (rect.getHeight() / 2)/ GameProject.PPM); // defini a forma
         	fdef.shape = shape; // passa a forma para o corpo
         	fdef.filter.categoryBits = GameProject.OBJECT_BIT; // defini o tipo de bit de contato do corpo
         	body.createFixture(fdef); //cria a fixture no mundo
         
         
         }
         
         for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
          	Rectangle rect = ((RectangleMapObject) object).getRectangle(); // cria um retangulo para definir o objeto
          	
          	bdef.type = BodyType.StaticBody;  //defini o objeto como estatico
          	
          	//defini a posi��o do bdef
          	bdef.position.set((rect.getX() + rect.getWidth() / 2 ) / GameProject.PPM, (rect.getY() + rect.getHeight() / 2)/ GameProject.PPM);
          	
              
          	body = world.createBody(bdef); // cria o corpo no mundo
          	
          	shape.setAsBox((rect.getWidth() / 2)/ GameProject.PPM, (rect.getHeight() / 2)/ GameProject.PPM); // defini a forma
          	fdef.shape = shape; // passa a forma para o corpo
          	fdef.filter.categoryBits = GameProject.HOLE_BIT; // defini o tipo de bit de contato do corpo
          	body.createFixture(fdef); //cria a fixture no mundo
          
          
          }
         
         for(MapObject object : map.getLayers().get(12).getObjects().getByType(RectangleMapObject.class)) {
           	Rectangle rect = ((RectangleMapObject) object).getRectangle(); // cria um retangulo para definir o objeto
           	
           	bdef.type = BodyType.StaticBody;  //defini o objeto como estatico
           	
           	//defini a posi��o do bdef
           	bdef.position.set((rect.getX() + rect.getWidth() / 2 ) / GameProject.PPM, (rect.getY() + rect.getHeight() / 2)/ GameProject.PPM);
           	
               
           	body = world.createBody(bdef); // cria o corpo no mundo
           	
           	shape.setAsBox((rect.getWidth() / 2)/ GameProject.PPM, (rect.getHeight() / 2)/ GameProject.PPM); // defini a forma
           	fdef.shape = shape; // passa a forma para o corpo
           	fdef.filter.categoryBits = GameProject.ENEMY_GROUND_BIT; // defini o tipo de bit de contato do corpo
           	body.createFixture(fdef); //cria a fixture no mundo
           
           
           }
         
         for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
           	Rectangle rect = ((RectangleMapObject) object).getRectangle(); // cria um retangulo para definir o objeto
           	
           	bdef.type = BodyType.StaticBody;  //defini o objeto como estatico
           	
           	//defini a posi��o do bdef
           	bdef.position.set((rect.getX() + rect.getWidth() / 2 ) / GameProject.PPM, (rect.getY() + rect.getHeight() / 2)/ GameProject.PPM);
           	
               
           	body = world.createBody(bdef); // cria o corpo no mundo
           	
           	shape.setAsBox((rect.getWidth() / 2)/ GameProject.PPM, (rect.getHeight() / 2)/ GameProject.PPM); // defini a forma
           	fdef.shape = shape; // passa a forma para o corpo
           	fdef.filter.categoryBits = GameProject.OBJECT_BIT; // defini o tipo de bit de contato do corpo
           	body.createFixture(fdef); //cria a fixture no mundo
           
           
           }


         enemies1 = new Array<Enemy1>();
        for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            enemies1.add(new Enemy1(screen, rect.getX() / GameProject.PPM, rect.getY() / GameProject.PPM));
        }
        enemies2 = new Array<Enemy2>();
        for(MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            enemies2.add(new Enemy2(screen, rect.getX() / GameProject.PPM, rect.getY() / GameProject.PPM));
        }
        
        coins = new Array<Coin>();
        for(MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            coins.add(new Coin(screen, rect.getX() / GameProject.PPM, rect.getY() / GameProject.PPM));
        }
        
        MapObject object = map.getLayers().get(11).getObjects().getByType(RectangleMapObject.class).first();
        Rectangle rect = ((RectangleMapObject) object).getRectangle();
        helicoptero = new Helicoptero(screen, rect.getX() / GameProject.PPM, rect.getY() / GameProject.PPM);
     
         
    }
    
    /**
     * Pega a lista de Enimies1.
     */
    public Array<Enemy1> getEnemies1() {
        return enemies1;
    }


    /**
     * Pega a lista de Enimies2.
     */
    public Array<Enemy> getEnemies(){
        Array<Enemy> enemies = new Array<Enemy>();
        enemies.addAll(enemies1);
        enemies.addAll(enemies2);
        return enemies;
    }
    
    /**
     * Pega a lista de Coins.
     */
    public Array<Coin> getCoins(){
        Array<Coin> multicoins = new Array<Coin>();
        multicoins.addAll(coins);
        return multicoins;
    }
    
    /**
     * Pega a lista de Helicopteros.
     */
    public Helicoptero getHelicoptero(){
        return helicoptero;
    }
    
}