package com.gdxproject.game.Tools;


import com.badlogic.gdx.Gdx;
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
import com.gdxproject.game.GameProject;
import com.gdxproject.game.Screens.PlayScreen;
import com.gdxproject.game.Sprites.Enemies.Enemy;
import com.gdxproject.game.Sprites.Enemies.Enemy1;
import com.gdxproject.game.Sprites.Enemies.Enemy2;
import com.gdxproject.game.Sprites.Items.Coin;
import com.gdxproject.game.Sprites.Items.Helicoptero;

public class B2WorldCreator {
	
	private Array<Enemy1> enemies1;
    private Array<Enemy2> enemies2;
    private Array<Coin> coins;
    private Helicoptero helicoptero;

    public B2WorldCreator(PlayScreen screen){
    	World world = screen.getWorld(); // recebe o mundo da screen
    	TiledMap map = screen.getMap(); // recebe o tiledmap da  screen
    	BodyDef bdef = new BodyDef(); // recebe os corpos definidos na screen
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
         
         //Cria a superficie de contato(ground) para bodies/fixtures
        //lembrando que o get(2) é referente a camada desejada do tiledmap
         for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
         	Rectangle rect = ((RectangleMapObject) object).getRectangle();// cria um retangulo para definir o objeto
         	
         	bdef.type = BodyType.StaticBody;//defini o objeto como estatico
         	
         	//defini a posição do bdef 
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
          	
          	//defini a posição do bdef
          	//bdef.position.set((pol.getX() / 2 ) / GameProject.PPM, (pol.getY()  / 2)/ GameProject.PPM);
          	
          	      	
          	bdef.position.set(((pol.getX())/ GameProject.PPM), ((pol.getY() )/ GameProject.PPM));
          	pol.setPosition(0, 0);
          	pol.setScale(1  / GameProject.PPM,1  / GameProject.PPM);

          	shape.set(pol.getTransformedVertices());
        
          	// cria o corpo no mundo
          	body = world.createBody(bdef);
          	//shape.setAsBox((pol.getScaleX() / 2)/ GameProject.PPM, (pol.getScaleY() / 2)/ GameProject.PPM);// defini a forma
          	fdef.shape = shape;// passa a forma para o corpo
          	body.createFixture(fdef); //cria a fixture no mundo
          }
          
         
       //Cria os tubos para bodies/fixtures
         for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
         	Rectangle rect = ((RectangleMapObject) object).getRectangle(); // cria um retangulo para definir o objeto
         	
         	bdef.type = BodyType.StaticBody;  //defini o objeto como estatico
         	
         	//defini a posição do bdef
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
          	
          	//defini a posição do bdef
          	bdef.position.set((rect.getX() + rect.getWidth() / 2 ) / GameProject.PPM, (rect.getY() + rect.getHeight() / 2)/ GameProject.PPM);
          	
              
          	body = world.createBody(bdef); // cria o corpo no mundo
          	
          	shape.setAsBox((rect.getWidth() / 2)/ GameProject.PPM, (rect.getHeight() / 2)/ GameProject.PPM); // defini a forma
          	fdef.shape = shape; // passa a forma para o corpo
          	fdef.filter.categoryBits = GameProject.OBJECT_BIT; // defini o tipo de bit de contato do corpo
          	body.createFixture(fdef); //cria a fixture no mundo
          
          
          }
         
         for(MapObject object : map.getLayers().get(12).getObjects().getByType(RectangleMapObject.class)) {
           	Rectangle rect = ((RectangleMapObject) object).getRectangle(); // cria um retangulo para definir o objeto
           	
           	bdef.type = BodyType.StaticBody;  //defini o objeto como estatico
           	
           	//defini a posição do bdef
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
           	
           	//defini a posição do bdef
           	bdef.position.set((rect.getX() + rect.getWidth() / 2 ) / GameProject.PPM, (rect.getY() + rect.getHeight() / 2)/ GameProject.PPM);
           	
               
           	body = world.createBody(bdef); // cria o corpo no mundo
           	
           	shape.setAsBox((rect.getWidth() / 2)/ GameProject.PPM, (rect.getHeight() / 2)/ GameProject.PPM); // defini a forma
           	fdef.shape = shape; // passa a forma para o corpo
           	fdef.filter.categoryBits = GameProject.OBJECT_BIT; // defini o tipo de bit de contato do corpo
           	body.createFixture(fdef); //cria a fixture no mundo
           
           
           }
         
       //create all goombas
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
    
    //recebe os goombas
    public Array<Enemy1> getEnemies1() {
        return enemies1;
    }
    
    //recebe os inimigos
    public Array<Enemy> getEnemies(){
        Array<Enemy> enemies = new Array<Enemy>();
        enemies.addAll(enemies1);
        enemies.addAll(enemies2);
        return enemies;
    }
    
    //recebe os inimigos
    public Array<Coin> getCoins(){
        Array<Coin> multicoins = new Array<Coin>();
        multicoins.addAll(coins);
        return multicoins;
    }
    

    public Helicoptero getHelicoptero(){
        return helicoptero;
    }
    
}