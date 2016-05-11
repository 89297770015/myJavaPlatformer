package ru.drewru.neo.window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

import ru.drewru.neo.framework.GameObject;
import ru.drewru.neo.framework.ObjectId;
import ru.drewru.neo.objects.Test;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = -1739443567028741921L;

	private boolean running = false;
	private Thread thread;
	
	Hendler hendler;
	
	Random rand = new Random();
	
	public void init(){
		hendler = new Hendler();
		for(int i = 0;i<50;i++)	hendler.addObject(new Test(rand.nextInt(800), rand.nextInt(600), ObjectId.Test));
		
		//for(int i = 0;i<50;i++){
		//g.fillRect(rand.nextInt(800), rand.nextInt(600), 32, 32);
		//}
	}

	public synchronized void start(){
		if(running) return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		
		init();
		this.requestFocus();
		
		long lastTime = System.nanoTime();
		double amongOfTicks = 60.0;
		double ns = 1000000000/ amongOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime)/ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
			
			if(System.currentTimeMillis()-timer>1000){
				timer += 1000;
				System.out.println("fps: "+frames+" ticks: "+ updates);
				frames = 0;
				updates = 0;
			}
		}
	}

	private void tick() {
		hendler.tick();
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		////////////////////
		// Draw here
		g.setColor(Color.black);
		g.fillRect(0,0, getWidth(), getHeight());
		
		hendler.render(g);
		
		//////////////
		g.dispose();
		bs.show();
	}

	public static void main(String args[]){
		new Window(800, 600, "Neo Platformer Game", new Game());
	}

}
