package com.gdxproject.game.Sprites.Structure;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.gdxproject.game.GameProject;
import com.gdxproject.game.Scenes.Hud;
import com.gdxproject.game.Screens.PlayScreen;
import com.gdxproject.game.Sprites.Player.Player;


public class Brick extends InteractiveTiledObject {
    public Brick(PlayScreen screen, MapObject object){
    	super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(GameProject.BRICK_BIT);
    }

    @Override
    public void onHeadHit(Player mario) {
    	if(mario.isBig()) {
            setCategoryFilter(GameProject.DESTROYED_BIT);
            getCell().setTile(null);
            Hud.addScore(200);
            GameProject.manager.get("mario/audio/sounds/breakblock.wav", Sound.class).play();
        }
    	GameProject.manager.get("mario/audio/sounds/bump.wav", Sound.class).play();
    }

} 