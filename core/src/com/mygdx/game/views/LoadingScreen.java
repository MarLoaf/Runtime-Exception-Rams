package com.mygdx.game.views;

import com.badlogic.gdx.Screen;
import com.mygdx.game.Tutor;

public class LoadingScreen implements Screen {

	private Tutor parent;
	
	public LoadingScreen(Tutor tutor) {
		parent = tutor;
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		parent.changeScreen(Tutor.LOGIN);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
