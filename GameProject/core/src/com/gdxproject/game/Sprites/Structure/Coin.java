package com.gdxproject.game.Sprites.Structure;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import com.gdxproject.game.GameProject;
import com.gdxproject.game.Scenes.Hud;
import com.gdxproject.game.Screens.PlayScreen;
import com.gdxproject.game.Sprites.Items.ItemDef;
import com.gdxproject.game.Sprites.Items.Mushroom;
import com.gdxproject.game.Sprites.Player.Player;

public class Coin extends InteractiveTiledObject {
    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 28;

    public Coin(PlayScreen screen, MapObject object){
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(GameProject.COIN_BIT);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
       
    }

    @Override
    public void onHeadHit(Player mario) {    		
        if(getCell().getTile().getId() == BLANK_COIN)
            GameProject.manager.get("mario/audio/sounds/bump.wav", Sound.class).play();
        else {
        	if(object.getProperties().containsKey("mushroom")) {
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / GameProject.PPM),
                        Mushroom.class));
                GameProject.manager.get("mario/audio/sounds/powerup_spawn.wav", Sound.class).play();
            }
            else
                GameProject.manager.get("mario/audio/sounds/coin.wav", Sound.class).play();
            getCell().setTile(tileSet.getTile(BLANK_COIN));
            Hud.addScore(100);
        }
        
    }
}