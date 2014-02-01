package com.spantons.gameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.spantons.audio.AudioPlayer;
import com.spantons.entity.Entity;
import com.spantons.entity.character.DanaScullyXFiles;
import com.spantons.entity.character.GordonFreeman;
import com.spantons.entity.character.Jason;
import com.spantons.entity.character.LeonTheProfessional‎;
import com.spantons.entity.character.LizSherman;
import com.spantons.entity.character.Preso;
import com.spantons.main.GamePanel;
import com.spantons.tileMap.TileMap;
import com.spantons.tileMap.TileSet;

public class Level1Stage extends Stage {

	private TileMap tileMap;
	private ArrayList<Entity> characters;
	private int currentCharacter;
	private boolean secondaryMenu = false;
	private AudioPlayer player;

	public Level1Stage(GameStagesManager gsm) {
		this.gsm = gsm;
		init();
	}
	/****************************************************************************************/
	@Override
	public void init() {
		TileSet tileSet = new 
				TileSet("/tilesets/isometric_grass_and_water.png",64, 64, 0, 0); 
		tileMap = new TileMap(64, 32, tileSet);
		tileMap.loadMap("/maps/map.txt");
		
//		tileMap.setPosition(
//				-GamePanel.RESOLUTION_WIDTH / 2, 
//				GamePanel.RESOLUTION_HEIGHT / 2);
		
		
		// Personajes
		characters = new ArrayList<Entity>();
		
		Jason jason = new Jason(tileMap, 0.10);
		jason.setMapPosition(0,0);
		characters.add(jason);
		
		LeonTheProfessional‎ leon = new LeonTheProfessional‎(tileMap, 0.10);
		leon.setMapPosition(0,2);
		characters.add(leon);
		
//		Preso preso = new Preso(tileMap, 0.10);
//		preso.setPosition(tileMap.RESOLUTION_WIDTH_FIX / 2, 
//				tileMap.RESOLUTION_HEIGHT_FIX / 4);
//		characters.add(preso);
//		
//		GordonFreeman gf = new GordonFreeman(tileMap, 0.10);
//		gf.setPosition(tileMap.RESOLUTION_WIDTH_FIX / 2, 
//				tileMap.RESOLUTION_HEIGHT_FIX / 4);
//		characters.add(gf);
//		
//		LizSherman ls = new LizSherman(tileMap, 0.10);
//		ls.setPosition(tileMap.RESOLUTION_WIDTH_FIX / 2, 
//				tileMap.RESOLUTION_HEIGHT_FIX / 4);
//		characters.add(ls);
//		
//		DanaScullyXFiles dcxf = new DanaScullyXFiles(tileMap, 0.10);
//		dcxf.setPosition(tileMap.RESOLUTION_WIDTH_FIX / 2, 
//				tileMap.RESOLUTION_HEIGHT_FIX / 4);
//		characters.add(dcxf);
		
		// Personaje actual
		currentCharacter = 0;
		
		//sonido del juego
		player = new AudioPlayer("/music/terror.wav");
		player.loop();
		
	}
	/****************************************************************************************/
	@Override
	public void update() {
		
		// Actualizar personajes actual
		characters.get(currentCharacter).update(characters,currentCharacter);
		
		for (int i = 0; i < characters.size(); i++){
			if (currentCharacter != i)
				characters.get(i).updateOtherCharacters();
		}
	}
	/****************************************************************************************/
	@Override
	public void draw(Graphics2D g) {
		// Dibujar tilemap
		tileMap.draw(g);
		// Dibujar personajes
		for (int i = 0; i < characters.size(); i++)
			characters.get(i).draw(g);
		
		// Menu secundario
		if(secondaryMenu == true){
			g.setColor(Color.WHITE);
			g.drawString("Resume (R)", 
				tileMap.RESOLUTION_WIDTH_FIX / 2 + 50, 
				-50 + tileMap.RESOLUTION_HEIGHT_FIX / 2);
			g.drawString("Main Menu (M)", 
				tileMap.RESOLUTION_WIDTH_FIX / 2 + 50, 
				tileMap.RESOLUTION_HEIGHT_FIX / 2);
			g.drawString("Quit Game (Q)", 
				tileMap.RESOLUTION_WIDTH_FIX / 2 + 50, 
				50 + tileMap.RESOLUTION_HEIGHT_FIX / 2);
			}
	}
	/****************************************************************************************/
	public void selectNextCurrentCharacter(){
		characters.get(currentCharacter).setAllMov(false);
		
		if (currentCharacter == characters.size() - 1) 
			currentCharacter = 0;
		else 
			currentCharacter++;		
		
	}
	/****************************************************************************************/
	@Override
	public void keyPressed(int k) {
		// Mover personajes
		if (k == KeyEvent.VK_LEFT)
			characters.get(currentCharacter).setMovLeft(true);
		if (k == KeyEvent.VK_RIGHT)
			characters.get(currentCharacter).setMovRight(true);
		if (k == KeyEvent.VK_UP)
			characters.get(currentCharacter).setMovUp(true);
		if (k == KeyEvent.VK_DOWN)
			characters.get(currentCharacter).setMovDown(true);
		if (k == KeyEvent.VK_SPACE)
			characters.get(currentCharacter).setMovJumping(true);
		if (k == KeyEvent.VK_TAB)
			selectNextCurrentCharacter();
		if(k == KeyEvent.VK_ESCAPE)
			secondaryMenu = true;
		if(k == KeyEvent.VK_R && secondaryMenu)
			secondaryMenu = false;
		if(k == KeyEvent.VK_Q && secondaryMenu){
			player.stop();
			player.close();
			System.exit(0);
		}
		if(k == KeyEvent.VK_M && secondaryMenu){
			player.stop();
			player.close();
			gsm.setStage(GameStagesManager.MENU_STAGE);
		}
	}
	/****************************************************************************************/
	@Override
	public void keyReleased(int k) {
		
		// Mover personajes
		if (k == KeyEvent.VK_LEFT)
			characters.get(currentCharacter).setMovLeft(false);
		if (k == KeyEvent.VK_RIGHT)
			characters.get(currentCharacter).setMovRight(false);
		if (k == KeyEvent.VK_UP)
			characters.get(currentCharacter).setMovUp(false);
		if (k == KeyEvent.VK_DOWN)
			characters.get(currentCharacter).setMovDown(false);
		if (k == KeyEvent.VK_SPACE)
			characters.get(currentCharacter).setMovJumping(false);
	}

}
