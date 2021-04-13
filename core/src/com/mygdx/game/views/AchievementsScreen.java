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
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Tutor;

public class AchievementsScreen implements Screen {

	private Tutor parent;
	private Stage stage;
	private SpriteBatch batch;
	private Texture backgroundTexture;
	private String userInfoMessage;
	
	public AchievementsScreen(Tutor tutor) {
		parent = tutor;
		batch = new SpriteBatch();
		backgroundTexture = new Texture(Gdx.files.internal("images/background.png"));
		stage = new Stage(new ScreenViewport());
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
		stage.draw();
		userInfoMessage = "Student: " + parent.currentUser.getFullName();
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
		//creating different buttons/textfields/labels
		Label userInfo = new Label("", skin);
		userInfo.setText(userInfoMessage);
		userInfo.setAlignment(Align.center);
		Image Ruby1 = new Image(new Texture(Gdx.files.internal("images/ruby.png")));
		Image Diamond1 = new Image(new Texture(Gdx.files.internal("images/diamond.png")));
		Image Iron1 = new Image(new Texture(Gdx.files.internal("images/iron.png")));
		Image Ruby2 = new Image(new Texture(Gdx.files.internal("images/ruby.png")));
		Image Diamond2 = new Image(new Texture(Gdx.files.internal("images/diamond.png")));
		Image Iron2 = new Image(new Texture(Gdx.files.internal("images/iron.png")));
		Image Iron3 = new Image(new Texture(Gdx.files.internal("images/iron.png")));
		Image Iron4 = new Image(new Texture(Gdx.files.internal("images/iron.png")));
		Label List1 = new Label("Collected Achievements", skin, "noBackground");
		List1.setAlignment(Align.center);
		Label List2 = new Label("Achievements in-progress", skin, "noBackground");
		List2.setAlignment(Align.center);
		Label progress1 = new Label("Ruby Addition 100%", skin, "noBackground");
		Label progress2 = new Label("Diamond Multiplication 80%", skin, "noBackground");
		Label progress3 = new Label("Iron Fractions 65%", skin, "noBackground");
		Label progress4 = new Label("Iron Division 65%", skin, "noBackground");
		Label collected1 = new Label("Ruby Addition", skin, "noBackground");
		Label collected2 = new Label("Diamond Multiplication", skin, "noBackground");
		Label collected3 = new Label("Iron Fractions", skin, "noBackground");
		Label collected4 = new Label("Iron Division", skin, "noBackground");
		Button back = new Button(skin, "Exit");
		TextTooltip exitPopup = new TextTooltip("Back", skin);
		exitPopup.setInstant(true);
		// TODO add description for the achievements via pop-ups
		//layout:
		table.top();
		table.row();
		table.add().fillX().uniformX().pad(5).padBottom(100).width(Gdx.graphics.getWidth()/5);
		table.add().fillX().uniformX().pad(5).padBottom(100).width(Gdx.graphics.getWidth()/5);
		table.add(userInfo).fillX().uniformX().pad(5).padBottom(100).width(Gdx.graphics.getWidth()/5);
		table.add(back).uniformX().pad(5).padBottom(100);
		table.row();
		table.add(List1).colspan(2).pad(5).width(Gdx.graphics.getWidth()/5);
		table.add(List2).colspan(2).pad(5).width(Gdx.graphics.getWidth()/5);
		table.row();
		table.add(Ruby1).uniformX().pad(5).align(Align.right);
		table.add(collected1).pad(5).width(Gdx.graphics.getWidth()/5).align(Align.left);
		table.add(Ruby2).uniformX().pad(5).align(Align.right);
		table.add(progress1).pad(5).width(Gdx.graphics.getWidth()/5).align(Align.left);
		table.row();
		table.add(Diamond1).uniformX().pad(5).align(Align.right);
		table.add(collected2).pad(5).width(Gdx.graphics.getWidth()/5).align(Align.left);
		table.add(Diamond2).uniformX().pad(5).align(Align.right);
		table.add(progress2).pad(5).width(Gdx.graphics.getWidth()/5).align(Align.left);
		table.row();
		table.add(Iron1).uniformX().pad(5).align(Align.right);
		table.add(collected3).pad(5).width(Gdx.graphics.getWidth()/5).align(Align.left);
		table.add(Iron2).uniformX().pad(5).align(Align.right);
		table.add(progress3).pad(5).width(Gdx.graphics.getWidth()/5).align(Align.left);
		table.row();
		table.add(Iron3).uniformX().pad(5).align(Align.right);
		table.add(collected4).pad(5).width(Gdx.graphics.getWidth()/5).align(Align.left);
		table.add(Iron4).uniformX().pad(5).align(Align.right);
		table.add(progress4).pad(5).width(Gdx.graphics.getWidth()/5).align(Align.left);
		//adding button functionality
		back.addListener(exitPopup);
		back.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
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
