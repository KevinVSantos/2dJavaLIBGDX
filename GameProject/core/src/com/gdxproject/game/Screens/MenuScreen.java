package com.gdxproject.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdxproject.game.GameProject;
import com.gdxproject.game.Menu.LevelSelect;
import com.gdxproject.game.Scenes.Score;
import com.gdxproject.game.Sprites.Enemies.Enemy;
import com.gdxproject.game.Sprites.Enemies.EnemyB;
import com.gdxproject.game.Sprites.Items.Coin;
import com.gdxproject.game.Sprites.Items.Helicoptero;
import com.gdxproject.game.Sprites.Items.IntroDev;
import com.gdxproject.game.Sprites.Items.Menu;

public class MenuScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
	//Variaveis basicas da Playscreen
    private OrthographicCamera gamecam;

    private GameProject game;
    
    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    private Menu menu ;
    
    private Skin skin;
    public int count = 0;
    private TextField textField;
    
    
    //Musicas
    private Music music1;

    public MenuScreen(GameProject game){
        this.game = game;
        
        
      //inicializa a camera que irá seguir junto ao personagem
        gamecam = new OrthographicCamera();
        
        
        viewport = new FitViewport(GameProject.V_WIDTH, GameProject.V_HEIGHT, gamecam);
        stage = new Stage(viewport, ((GameProject) game).batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);


        stage.addActor(table);
        
        
        
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        textField = new TextField("", skin);
        textField.setX(100);
        textField.setY(100);
        textField.setWidth(200);
        textField.setHeight(50);
        //stage.addActor(textArea);
        stage.addActor(textField);
        
        
        
      //inicializa e seta nossa gamecam para ser centralizada corretamente no inicio do mapa
        gamecam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        
        menu = new Menu(this.game, GameProject.V_WIDTH, GameProject.V_HEIGHT);
         
        
          
        music1 = GameProject.manager.get("audio/music/song_story_requiem_for_a_dream.mp3", Music.class);
        music1.play();
        
        music1.setOnCompletionListener(new Music.OnCompletionListener() {

            @Override
            public void onCompletion(Music music) {
            	closeScreen();
            }
        });
       
    }
    
    public void verifyDigit(){
        String texto="";
        if(count <= 0 || count >= 7){
            count = 0;
            if(Gdx.input.isKeyPressed(Input.Keys.A)){
                texto = textField.getText();
                textField.setText(texto + "A");
            }else if(Gdx.input.isKeyPressed(Input.Keys.B)){
                texto = textField.getText();
                textField.setText(texto + "B");
            }else if(Gdx.input.isKeyPressed(Input.Keys.C)){
                texto = textField.getText();
                textField.setText(texto + "C");
            }else if(Gdx.input.isKeyPressed(Input.Keys.D)){
                texto = textField.getText();
                textField.setText(texto + "D");
            }else if(Gdx.input.isKeyPressed(Input.Keys.E)){
                texto = textField.getText();
                textField.setText(texto + "E");
            }else if(Gdx.input.isKeyPressed(Input.Keys.F)){
                texto = textField.getText();
                textField.setText(texto + "F");
            }else if(Gdx.input.isKeyPressed(Input.Keys.G)){
                texto = textField.getText();
                textField.setText(texto + "G");
            }else if(Gdx.input.isKeyPressed(Input.Keys.H)){
                texto = textField.getText();
                textField.setText(texto + "H");
            }else if(Gdx.input.isKeyPressed(Input.Keys.I)){
                texto = textField.getText();
                textField.setText(texto + "I");
            }else if(Gdx.input.isKeyPressed(Input.Keys.J)){
                texto = textField.getText();
                textField.setText(texto + "J");
            }else if(Gdx.input.isKeyPressed(Input.Keys.K)){
                texto = textField.getText();
                textField.setText(texto + "K");
            }else if(Gdx.input.isKeyPressed(Input.Keys.L)){
                texto = textField.getText();
                textField.setText(texto + "L");
            }else if(Gdx.input.isKeyPressed(Input.Keys.M)){
                texto = textField.getText();
                textField.setText(texto + "M");
            }else if(Gdx.input.isKeyPressed(Input.Keys.N)){
                texto = textField.getText();
                textField.setText(texto + "N");
            }else if(Gdx.input.isKeyPressed(Input.Keys.O)){
                texto = textField.getText();
                textField.setText(texto + "O");
            }else if(Gdx.input.isKeyPressed(Input.Keys.P)){
                texto = textField.getText();
                textField.setText(texto + "P");
            }else if(Gdx.input.isKeyPressed(Input.Keys.Q)){
                texto = textField.getText();
                textField.setText(texto + "Q");
            }else if(Gdx.input.isKeyPressed(Input.Keys.R)){
                texto = textField.getText();
                textField.setText(texto + "R");
            }else if(Gdx.input.isKeyPressed(Input.Keys.S)){
                texto = textField.getText();
                textField.setText(texto + "S");
            }else if(Gdx.input.isKeyPressed(Input.Keys.T)){
                texto = textField.getText();
                textField.setText(texto + "T");
            }else if(Gdx.input.isKeyPressed(Input.Keys.U)){
                texto = textField.getText();
                textField.setText(texto + "U");
            }else if(Gdx.input.isKeyPressed(Input.Keys.V)){
                texto = textField.getText();
                textField.setText(texto + "V");
            }else if(Gdx.input.isKeyPressed(Input.Keys.W)){
                texto = textField.getText();
                textField.setText(texto + "W");
            }else if(Gdx.input.isKeyPressed(Input.Keys.X)){
                texto = textField.getText();
                textField.setText(texto + "X");
            }else if(Gdx.input.isKeyPressed(Input.Keys.Y)){
                texto = textField.getText();
                textField.setText(texto + "Y");
            }else if(Gdx.input.isKeyPressed(Input.Keys.Z)){
                texto = textField.getText();
                textField.setText(texto + "Z");
            }else if(Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)){
                texto = textField.getText();
                if(texto.length() > 0)
                    textField.setText(texto.substring(0, texto.length()-1));
            }
        }
        count++;

    }
    public void closeScreen() {
    	try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	game.setScreen(new LevelSelect((GameProject) game));
        dispose();    	
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
    	
    	update(delta);
    	
    	
        if(Gdx.input.justTouched()) {
        	music1.stop();
            closeScreen();
        }
       Gdx.gl.glClearColor(0, 0, 0, 1);
       Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        
        game.batch.setProjectionMatrix(gamecam.combined);
        
        game.batch.begin();        
       
        menu.draw(game.batch);        
        game.batch.end();
       
        
      //Seta nosso batch para desenhar o que o a camera do HUD vê.
       //game.batch.setProjectionMatrix(stage.getCamera().combined);
        
        stage.draw();
    
    }
    
    public void update(float dt){
    	menu.update(dt);
    	gamecam.position.x = menu.getX() + (game.V_WIDTH / 2);
    	gamecam.position.y = menu.getY() + (game.V_HEIGHT / 2);
    	//Atualiza nossa gamecam com as coordenadas corretas após alteração
        gamecam.update();
        verifyDigit();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
       
    }
}