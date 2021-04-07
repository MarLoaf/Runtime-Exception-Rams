package com.mygdx.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.Hinting;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Tutor;

public class ResultsScreen implements Screen {

	private Tutor parent;
	private Stage stage;
	private SpriteBatch batch;
	private Texture backgroundTexture;
	private String message;
	
	public ResultsScreen(Tutor tutor) {
		parent = tutor;
		batch = new SpriteBatch();
		backgroundTexture = new Texture(Gdx.files.internal("images/background.png"));
		stage = new Stage(new ScreenViewport());
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
		stage.draw();
		message = "You got " + parent.answerCounter + " problems correct!";
	}

	@Override
	public void show() {
		//skin stuff - ignore
		Skin skin = new Skin(Gdx.files.internal("skin/tutorSkin.json")) {
            //Override json loader to process FreeType fonts from skin JSON
            @Override
            protected Json getJsonLoader(final FileHandle skinFile) {
                Json json = super.getJsonLoader(skinFile);
                final Skin skin = this;

                json.setSerializer(FreeTypeFontGenerator.class, new Json.ReadOnlySerializer<FreeTypeFontGenerator>() {
                    @Override
                    public FreeTypeFontGenerator read(Json json,
                            JsonValue jsonData, Class type) {
                        String path = json.readValue("font", String.class, jsonData);
                        jsonData.remove("font");

                        Hinting hinting = Hinting.valueOf(json.readValue("hinting", 
                                String.class, "AutoMedium", jsonData));
                        jsonData.remove("hinting");

                        TextureFilter minFilter = TextureFilter.valueOf(
                                json.readValue("minFilter", String.class, "Nearest", jsonData));
                        jsonData.remove("minFilter");

                        TextureFilter magFilter = TextureFilter.valueOf(
                                json.readValue("magFilter", String.class, "Nearest", jsonData));
                        jsonData.remove("magFilter");

                        FreeTypeFontParameter parameter = json.readValue(FreeTypeFontParameter.class, jsonData);
                        parameter.hinting = hinting;
                        parameter.minFilter = minFilter;
                        parameter.magFilter = magFilter;
                        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(skinFile.parent().child(path));
                        BitmapFont font = generator.generateFont(parameter);
                        skin.add(jsonData.name, font);
                        if (parameter.incremental) {
                            generator.dispose();
                            return null;
                        } else {
                            return generator;
                        }
                    }
                });

                return json;
            }
        };
        //making tables
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		//creating actors
		Label userInfo = new Label("Student: John Smith", skin);
		userInfo.setAlignment(Align.center);
        Label resultText = new Label("", skin);
        resultText.setText(message);
        resultText.setAlignment(Align.center);
		Button back = new Button(skin, "Exit");
		ImageTextButton ok = new ImageTextButton("OK", skin, "green");
		Image Ruby = new Image(new Texture(Gdx.files.internal("images/ruby.png")));
		Image Diamond = new Image(new Texture(Gdx.files.internal("images/diamond.png")));
		Image Iron = new Image(new Texture(Gdx.files.internal("images/iron.png")));
        //layout
		table.top();
		table.row();
		table.add().fillX().uniformX().pad(5).padBottom(270).width(Gdx.graphics.getWidth()/5);
		table.add().fillX().uniformX().pad(5).padBottom(270).width(Gdx.graphics.getWidth()/5);
		table.add(userInfo).fillX().uniformX().pad(5).padBottom(270).width(Gdx.graphics.getWidth()/5);
		table.add(back).uniformX().pad(5).padBottom(270);
		table.row();
		table.add();
		table.add(resultText).colspan(2).pad(5).fillX().uniformX().width(Gdx.graphics.getWidth()/4);
		table.row();
		table.add();
		if (parent.additionAchievement < parent.answerCounter) parent.additionAchievement = parent.answerCounter;
		if (parent.answerCounter == 1) {
			table.add(Iron).colspan(2).uniformX();
		}else if (parent.answerCounter == 2) {
			table.add(Diamond).colspan(2).uniformX();
		}else if (parent.answerCounter == 3) {
			table.add(Ruby).colspan(2).uniformX();
		}
		table.row();
		table.add();
		table.add(ok).colspan(2).pad(5).width(Gdx.graphics.getWidth()/4);
		//adding button functionality
		back.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.answerCounter = 0;
				parent.changeScreen(Tutor.HOME);
			}
		});
		ok.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.answerCounter = 0;
				parent.changeScreen(Tutor.HOME);
			}
		});
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(backgroundTexture, 0, 0);
		batch.end();
		Gdx.input.setInputProcessor(stage);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
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
		stage.dispose();
	}

}
