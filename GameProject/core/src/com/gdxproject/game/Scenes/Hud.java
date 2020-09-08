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
import com.gdxproject.game.GameProject;


public class Hud implements Disposable{

    //Scene2D.ui Stage e dua propria Viewport para HUD
    public Stage stage;
    private Viewport viewport;

    //Jogador pontuação/time variaveis de rastreamento
    private Integer worldTimer;
    private boolean timeUp; // true quando o timer do mundo chega a 0.
    private float timeCount;
    private static Integer score;

    //Scene2D widgets
    private Label countdownLabel;
    private static Label scoreLabel;
    private Label timeLabel;
    private Label levelLabel;
    private Label worldLabel;
    private Label playerLabel;

    public Hud(SpriteBatch sb){
        //definição das nossas variaveis de rastreamento
        worldTimer = 300;
        timeCount = 0;
        score = 0;


        //Configuração do ViewPort HUD usando uma nova camera separada para nossa gamecam 
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
        playerLabel = new Label("MARIO", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //adiciona nossas labels para a nossa tabela, padding(margem) no topo, e defini a todo o conteudo um width com expandX de maneira igual
        table.add(playerLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        
        //adiciona nossa segunda linha para a nossa tabela
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        //adiciona nossa tabela para o stage
        stage.addActor(table);

    }

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

    public static void addScore(int value){
    	//adiciona pontuação
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }

    @Override
    public void dispose() { stage.dispose(); }

    //verifica se atingiu o tempo limite
    public boolean isTimeUp() { return timeUp; }
}