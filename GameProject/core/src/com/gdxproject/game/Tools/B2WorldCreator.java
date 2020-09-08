package com.gdxproject.game.Tools;


import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
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
import com.gdxproject.game.Sprites.Enemies.Goomba;
import com.gdxproject.game.Sprites.Enemies.Turtle;
import com.gdxproject.game.Sprites.Structure.Brick;
import com.gdxproject.game.Sprites.Structure.Coin;

public class B2WorldCreator {
	
	private Array<Goomba> goombas;
    private Array<Turtle> turtles;

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
         
       //Cria os tubos para bodies/fixtures
         for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
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
         
       //Cria os brick bodies/fixtures
         for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
         	new Brick(screen, object);
         }
         
       //Cria os blocos de moedas bodies/fixtures
         for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {   
         	new Coin(screen, object);        
         }
         
       //create all goombas
        goombas = new Array<Goomba>();
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            goombas.add(new Goomba(screen, rect.getX() / GameProject.PPM, rect.getY() / GameProject.PPM));
        }
        turtles = new Array<Turtle>();
        for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            turtles.add(new Turtle(screen, rect.getX() / GameProject.PPM, rect.getY() / GameProject.PPM));
        }
         
    }
    
    //recebe os goombas
    public Array<Goomba> getGoombas() {
        return goombas;
    }
    
    //recebe os inimigos
    public Array<Enemy> getEnemies(){
        Array<Enemy> enemies = new Array<Enemy>();
        enemies.addAll(goombas);
        enemies.addAll(turtles);
        return enemies;
    }     
    
}