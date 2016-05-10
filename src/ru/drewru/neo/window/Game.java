package ru.drewru.neo.window;

import java.awt.Canvas;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = -1739443567028741921L;

	private boolean running = false;
	private Thread thread;
	
	public synchronized void start(){
		if(running) return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		System.out.println("Thread has began");		
	}

	
	public static void main(String args[]){
		new Window(800, 600, "Neo Platformer Game", new Game());
	}
//скоро продолжим   gjk.,jve
}
