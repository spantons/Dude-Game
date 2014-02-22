package com.spantons.entity.character;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.spantons.entity.Animation;
import com.spantons.entity.Entity;
import com.spantons.gameState.Stage;
import com.spantons.tileMap.TileMap;

public class DanaScullyXFiles extends Entity {

		// Animacion
		private ArrayList<BufferedImage[]> sprites;

		// Acciones de la animacion
		private static final int WALKING_FRONT = 0;
		private static final int WALKING_BACK = 1;
		private static final int WALKING_SIDE = 2;
		private static final int WALKING_PERSPECTIVE_FRONT = 3;
		private static final int WALKING_PERSPECTIVE_BACK = 4;
		private static final int IDLE = 3;
		private static final int DEAD = 5;

		/****************************************************************************************/
		public DanaScullyXFiles(TileMap _tm, Stage _stage, int _xMap, int _yMap, double _scale) {
			
			super(_tm,_stage);
			xMap = _xMap;
			yMap = _yMap;
			scale = _scale;
			
			visible = true;
			description = "Dana Scully X-Files";
			health = 5;
			maxHealth = 5;
			perversity = 0;
			maxPerversity = 100;
			damage = 0.5f;
			flinchingIncreaseDeltaTimePerversity = 1000;
			flinchingDecreaseDeltaTimePerversity = 1000;
			deltaForReduceFlinchingIncreaseDeltaTimePerversity = 50;
			dead = false;
			moveSpeed = 1;
			facingRight = true;

			loadSprite();
			
			animation = new Animation();
			currentAnimation = IDLE;
			animation.setFrames(sprites.get(IDLE));
			animation.setDelayTime(1000);
		}
		/****************************************************************************************/
		private void loadSprite() {
			try {

				face = ImageIO.read(getClass()
						.getResourceAsStream("/hud/DanaScullyXFiles.png"));
				
				BufferedImage spriteSheet2 = ImageIO.read(getClass()
						.getResourceAsStream("/characteres_sprites/DanaScullyXFiles.png"));
				
				spriteWidth = ((int) (spriteSheet2.getWidth() / 3 * scale));
				spriteHeight = ((int) (spriteSheet2.getHeight() /2 * scale));
				collisionBoxWidth = (int) (432 * scale);
				collisionBoxHeight = (int) (521 * scale);
				
				// Redimencionar SpriteSheet
				int newWidth = (int) (spriteSheet2.getWidth() * scale);
				int newHeight = (int) (spriteSheet2.getHeight() * scale); 

				BufferedImage spriteSheet = new BufferedImage(newWidth,
						newHeight, spriteSheet2.getType());
				Graphics2D g = spriteSheet.createGraphics();
				g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
						RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				g.drawImage(spriteSheet2, 0, 0, newWidth, newHeight, 0, 0,
						spriteSheet2.getWidth(),
						spriteSheet2.getHeight(), null);

				sprites = new ArrayList<BufferedImage[]>();

				// WALKING_FRONT
				BufferedImage[] bi = new BufferedImage[1];
				bi[0] = spriteSheet.getSubimage(0, 0, spriteWidth,
						spriteHeight);
				sprites.add(bi);
				
				// WALKING_BACK
				bi = new BufferedImage[1];
				bi[0] = spriteSheet.getSubimage(spriteWidth, 0, spriteWidth,
						spriteHeight);
				sprites.add(bi);
				
				// WALKING_SIDE
				bi = new BufferedImage[1];
				bi[0] = spriteSheet.getSubimage(spriteWidth * 2, 0, spriteWidth,
						spriteHeight);
				sprites.add(bi);
				
				// WALKING_PERSPECTIVE_FRONT
				bi = new BufferedImage[1];
				bi[0] = spriteSheet.getSubimage(0, spriteHeight, spriteWidth,
						spriteHeight);
				sprites.add(bi);
				
				// WALKING_PERSPECTIVE_BACK
				bi = new BufferedImage[1];
				bi[0] = spriteSheet.getSubimage(spriteWidth, spriteHeight, spriteWidth,
						spriteHeight);
				sprites.add(bi);
				
				// DEAD
				bi = new BufferedImage[1];
				bi[0] = spriteSheet.getSubimage(spriteWidth * 2, spriteHeight, spriteWidth,
						spriteHeight);
				sprites.add(bi);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/****************************************************************************************/
		public void update() {
			super.update();
		}
		/****************************************************************************************/
		public void updateAnimation(){
			
			if (dead) {
				if (currentAnimation != DEAD) {
					currentAnimation = DEAD;
					animation.setFrames(sprites.get(DEAD));
					animation.setDelayTime(150);
				}
			}
			else {
				if((movLeft && movDown) || (movRight && movDown)){
					if (currentAnimation != WALKING_PERSPECTIVE_FRONT) {
						currentAnimation = WALKING_PERSPECTIVE_FRONT;
						animation.setFrames(sprites.get(WALKING_PERSPECTIVE_FRONT));
						animation.setDelayTime(150);
					}
				} else if((movLeft && movUp) || (movRight && movUp)){
					if (currentAnimation != WALKING_PERSPECTIVE_BACK) {
						currentAnimation = WALKING_PERSPECTIVE_BACK;
						animation.setFrames(sprites.get(WALKING_PERSPECTIVE_BACK));
						animation.setDelayTime(150);
					}
				} else if (movDown) {
					if (currentAnimation != WALKING_FRONT) {
						currentAnimation = WALKING_FRONT;
						animation.setFrames(sprites.get(WALKING_FRONT));
						animation.setDelayTime(100);
					}
				} else if (movUp) {
					if (currentAnimation != WALKING_BACK) {
						currentAnimation = WALKING_BACK;
						animation.setFrames(sprites.get(WALKING_BACK));
						animation.setDelayTime(40);
					}
				} else if (movLeft || movRight) {
					if (currentAnimation != WALKING_SIDE) {
						currentAnimation = WALKING_SIDE;
						animation.setFrames(sprites.get(WALKING_SIDE));
						animation.setDelayTime(150);
					}	
				} else {
					if (currentAnimation != IDLE) {
						currentAnimation = IDLE;
						animation.setFrames(sprites.get(IDLE));
						animation.setDelayTime(1000);
					}
				}

				// direccion de la cara del jugador
				if (movRight)
					facingRight = true;
				if (movLeft)
					facingRight = false;

				animation.update();
			}
		}
		/****************************************************************************************/
		public void draw(Graphics2D g) {
			super.draw(g);
		}

}
