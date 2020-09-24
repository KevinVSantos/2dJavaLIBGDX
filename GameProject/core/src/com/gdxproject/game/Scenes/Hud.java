package com.gdxproject.game.Scenes;
 


 import com.badlogic.gdx.graphics.Color;
 import com.badlogic.gdx.graphics.OrthographicCamera;
 import com.badlogic.gdx.graphics.g2d.BitmapFont;
 import com.badlogic.gdx.graphics.g2d.SpriteBatch;
 import com.badlogic.gdx.scenes.scene2d.Stage;
 import com.badlogic.gdx.scenes.scene2d.ui.Label;
 import com.badlogic.gdx.scenes.scene2d.ui.Table;
 import com.badlogic.gdx.utils.Disposable;
 import com.badlogic.gdx.utils.viewport.FitViewport;
 import com.badlogic.gdx.utils.viewport.Viewport;
 import com.gdxproject.game.FileSave.FileSave;
 import com.gdxproject.game.GameProject;
 import com.gdxproject.game.Scenes.Score;
 import java.util.ArrayList;
 
 /**
  * HUD do jogo, ou seja, define as informa��es constantes na tela.
  */
public class Hud implements Disposable{
     //Scene2D.ui Stage e dua propria Viewport para HUD
	/**
     * Vari�vel respons�vel por lidar com a janela de visualiza��o e distribui eventos de entrada.
     * */
     public Stage stage;
     
     /**
 	 * Gerencia uma c�mera e determina como as coordenadas mundiais s�o mapeadas para a tela.
 	 * */
     private Viewport viewport;
 
    //Jogador pontua��o/time variaveis de rastreamento
     /**
      * Variavel que conta o tempo do jogo para exibir.
      */
     private Integer worldTimer;
     
     /**
      * Variavel que indica se o tempo chegou a zero.
      */
     private boolean timeUp; // true quando o timer do mundo chega a 0.
     
     /**
      * Tempo real.
      */
     private float timeCount;
     
     /**
      * Pontua��o.
      */
     private static Integer score;
 
    //Scene2D widgets
     private Label countdownLabel;
     
     /**
      * Label para exibi��o da pontua��o.
      */
     private static Label scoreLabel;
     
     /**
      * Label para exibi��o do tempo.
      */
     private Label timeLabel;
     
     /**
      * Label que indica a fase.
      */
     private Label levelLabel;
     
     /**
      * Label que indica o mundo.
      */
     private Label worldLabel;
     
     /**
      * Label do protagonista.
      */
     private Label playerLabel;
     
     /**
      * Label da Pontua��o nos recordes.
      */
     private Label scoreRank;
     
     /**
      * Label do nome da pontua��o nos recordes.
      */
     private Label scoreRankName;

     /**
      * Lista de pontua��es.
      */
     public static ArrayList<Score> scores;
     
     /**
      * Arquivo de pontua��o.
      */
     FileSave<ArrayList<Score>> file;
     
     /**
      * Nome do Jogador.
      */
     public static String nickname; 
   
     /**
      * Cria��o da HUD do jogo, ou seja, define as informa��es constantes na tela.
      * @param sb - Batch para desenhar na tela.
      * @param nick - Nome do jogador.
      */
     public Hud(SpriteBatch sb, String nick){
         String aux = "";
         nickname = nick;
         //defini��o das nossas variaveis de rastreamento
         worldTimer = 300;
         timeCount = 0;
         score = 0;
 
        file = new FileSave<ArrayList<Score>>();

         scores = file.ReadBin("score.bin");
         
         if(scores != null){
             for(Score a : scores){
                 System.out.println("nickname: " + a.nickname);
                 System.out.println("score: " + a.score);
             }
         }else{
             scores = new ArrayList<Score>();
         }
 
        //Configura��o do ViewPort HUD usando uma nova camera separada para nossa gamecam 
         //define our stage using that viewport and our games spritebatch
         viewport = new FitViewport(GameProject.V_WIDTH, GameProject.V_HEIGHT, new OrthographicCamera());

         stage = new Stage(viewport, sb);
 
        //Defini uma tabela usada para organizar nossas label do HUD
         Table table = new Table();

         //Alinhamento da tabela no Topo
         table.top();

         //faz a tabela filtrar o stage inteiro
         table.setFillParent(true);
 
        //Defini nossa label utilizando String, e um estilo para a albel consistindo uma fonte e cor
         countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
         scoreLabel =new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
         timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
         levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
         worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
         playerLabel = new Label(nickname, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
         scoreRankName = new Label("RANK", new Label.LabelStyle(new BitmapFont(), Color.WHITE));


         for(Score a : scores){
             aux += "\n " + a.nickname + " " + a.score;
         }

         BitmapFont font = new BitmapFont();
         font.getData().setScale(0.5f);        

         scoreRank = new Label(aux, new Label.LabelStyle(font, Color.WHITE)); 
        scoreRank.setHeight(10);
         scoreRank.setWidth(10);


         //adiciona nossas labels para a nossa tabela, padding(margem) no topo, e defini a todo o conteudo um width com expandX de maneira igual
         table.add(playerLabel).expandX().padTop(10);
         table.add(worldLabel).expandX().padTop(10);
         table.add(timeLabel).expandX().padTop(10);
         table.add(scoreRankName).expandX().padTop(10);


         //adiciona nossa segunda linha para a nossa tabela
         table.row();

         table.add(scoreLabel).expandX();
         table.add(levelLabel).expandX();
         table.add(countdownLabel).expandX();
         table.add(scoreRank).expandX();
 
        //adiciona nossa tabela para o stage
         stage.addActor(table);

     }
 
     /**
      * M�todo respons�vel por atualizar o que ser� exibido.
      * @param dt - Tempo desde a �ltima atualiza��o
      */
    public void update(float dt){
         //atualiza o contador
         timeCount += dt;
         
         if(timeCount >= 1){
             if (worldTimer > 0) {
                 worldTimer--;
             } else {
                 timeUp = true;
             }

             countdownLabel.setText(String.format("%03d", worldTimer));
             timeCount = 0;

         }
     }
 
    /**
     * Aumenta a pontua��o do jogador.
     * @param value - Pontua��o
     */
    public static void addScore(int value){
         //adiciona pontua��o
         score += value;
         scoreLabel.setText(String.format("%06d", score));
     }


    /**
     * Salva a pontua��o do jogador.
     */
     public static void saveScore(){
         try
         {
             int min = 0;
             if(score != null){
                 if(scores.size() < 5)
                     scores.add(new Score(nickname, score));
                 else{
                     for(Score a : scores){
                         if(a.score < score){
                             min = a.score;
                         }
                     }


                     for(Score a : scores){
                         if(a.score == min){
                             a.nickname = nickname;
                             a.score = score;
                         }
                     }
                 }
             }
         }catch(Exception ex){
             System.out.println(ex);
         }
     }        
 
     /**
      * Libera��o de recursos
      */
    @Override
     public void dispose() {
         stage.dispose();
         file.WriteBin("score.bin", scores);
     }
 
    /**
     * M�todo que verifia se o tempo limite foi atingido.
     */
     public boolean isTimeUp() { return timeUp; }
 
}