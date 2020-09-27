package com.gdxproject.system.game.Screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdxproject.component.game.Tools.DrawScreenComponent;
import com.gdxproject.entity.game.Sprites.Items.Menu;
import com.gdxproject.system.game.GameProject;
import com.gdxproject.system.game.Menu.LevelSelect;


/** 
 * Classe responsável pela tela de Menu.
 * */
public class MenuScreen implements Screen {
	
	/**
	 * Gerencia uma câmera e determina como as coordenadas mundiais são mapeadas para a tela.
	 * */
    private Viewport viewport;
    
    /**
     * Variável responsável por lidar com a janela de visualização e distribui eventos de entrada.
     * */
    private Stage stage;
    
    /**
     * Variaveis basicas da Playscreen.
     * */
    private OrthographicCamera gamecam;
    
    /**
     * Responsável por trazer características do jogo.
     * */
    private GameProject game;
    
    /**
     * Responsável por  trazer os frames carregados para serem exibidos.
     * */
    private Menu menu ;
    
    /**
     * Responsável por  trazer os traços de fontes através de um json.
     * */
    private Skin skin;
    
    /**
     * Variável de controle por tempo de digitação.
     * */
    public int count = 0;
    
    /**
     * Componente responsável pela caixa de texto que aparece no menu.
     * */
    private TextField textField;
    
    /**
     * Variável responsável pela música de fundo.
     * */
    private Music music1;
    
    /**
     * Construtor da classe.
     * <br>
     * Nela é possível ver os valores sendo iniciados, como o de viewport, stage e gamecam.
     * <br>
     * A variável de música recebe o endereço e o nome da música selecionada juntamente com a classe responsável por carrega-la(Music.class)
     * */
    public MenuScreen(GameProject game){
        this.game = game;
        
        
      //inicializa a camera que irá seguir junto ao personagem
        gamecam = new OrthographicCamera();
        
        
        viewport = new FitViewport(GameProject.V_WIDTH, GameProject.V_HEIGHT, gamecam);
        stage = new Stage(viewport, ((GameProject) game).batch);

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
    
    /**
     * Método Responsável por verificar a digitação do usuário e colocar na tela.
     * */
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
    
    /**
     * Método responsável por encerrar a janela e chamar outra.
     * */
    public void closeScreen() {
    	try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	game.setScreen(new LevelSelect((GameProject) game, textField.getText()));
        dispose();    	
    }

    @Override
    public void show() {

    }


    /**
     * Método responsável por desenhar na tela.
     * */
    @Override
    public void render(float delta) {
    	
    	update(delta);
    	
    	
        if(Gdx.input.justTouched() && textField.getText().length() > 0) {
        	
        	music1.stop();
            closeScreen();
        }
       Gdx.gl.glClearColor(0, 0, 0, 1);
       Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
       ArrayList<Sprite> sprites = new ArrayList<Sprite>();
       sprites.add(menu);  
       
       DrawScreenComponent.DrawScreen(gamecam, sprites, stage);        
    
    }
    
    /**
     * Método responsável por atualizar o que será exibido.
     * */
    public void update(float dt){
    	menu.update(dt);
    	gamecam.position.x = menu.getX() + (GameProject.V_WIDTH / 2);
    	gamecam.position.y = menu.getY() + (GameProject.V_HEIGHT / 2);
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

    /**
     * Método responsável por liberar recursos.
     * */
    @Override
    public void dispose() {
        stage.dispose();
       
    }
}