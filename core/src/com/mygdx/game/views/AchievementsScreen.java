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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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
	
	public AchievementsScreen(Tutor tutor) {
		parent = tutor;
		batch = new SpriteBatch();
		backgroundTexture = new Texture(Gdx.files.internal("images/background.png"));
		stage = new Stage(new ScreenViewport());
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
		stage.draw();
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
//<<<<<<< Updated upstream
     
//=======
      
		//making tables
				Table table = new Table();
				table.setFillParent(true);
				stage.addActor(table);
		        //creating different buttons/textfields/labels
				ImageTextButton back = new ImageTextButton("Back", skin, "pink");
				//layout:
				table.add(back);
				//
				//Button logout = new Button(skin, "Exit");
				Image Iron1 = new Image(new Texture(Gdx.files.internal("images/iron.png")));
				Image AdditionIron = new Image(new Texture(Gdx.files.internal("images/iron.png")));
				Image AdditionDiamond = new Image(new Texture(Gdx.files.internal("images/diamond.png")));
				Image AdditionRuby = new Image(new Texture(Gdx.files.internal("images/ruby.png")));
				Image Ruby = new Image(new Texture(Gdx.files.internal("images/ruby.png")));
				Image Diamond = new Image(new Texture(Gdx.files.internal("images/diamond.png")));
				Image Iron = new Image(new Texture(Gdx.files.internal("images/iron.png")));
				Label achievement1 = new Label("Addition", skin, "noBackground");
				Label achievement2 = new Label("Subtraction", skin, "noBackground");
				Label achievement3 = new Label("Division", skin, "noBackground");
			
				ImageTextButton achiemeventsButton = new ImageTextButton("My Achievements", skin);
				//layout:
				table.top();
				table.row();
				table.add(achiemeventsButton).fillX().uniformX().pad(5).padBottom(100).width(Gdx.graphics.getWidth()/5);
				table.add();
				table.row();
				table.row();
				table.add();
				table.add(Iron1).pad(5).align(Align.right);
				table.add(Ruby).pad(5).align(Align.right);
				table.add(achievement1).pad(5).width(Gdx.graphics.getWidth()/5).align(Align.left);
				table.row();
				/*if (parent.additionAchievement == 1) {
					table.add(AdditionIron).pad(5).align(Align.right);
					table.add(addition).pad(5).width(Gdx.graphics.getWidth()/5).align(Align.right);
				} else if (parent.additionAchievement == 2) {
					table.add(AdditionDiamond).pad(5).align(Align.right);
					table.add(addition).pad(5).width(Gdx.graphics.getWidth()/5).align(Align.right);
				} else if (parent.additionAchievement == 3) {
					table.add(AdditionRuby).pad(5).align(Align.right);
					table.add(addition).pad(5).width(Gdx.graphics.getWidth()/5).align(Align.right);
				}else {
					table.add();
					table.add();
				}
				*/
				table.add(Diamond).pad(5).align(Align.right);
				table.add(achievement2).pad(5).width(Gdx.graphics.getWidth()/5).align(Align.left);
				table.row();
				table.add();
				table.add();
				table.add(Iron).pad(5).align(Align.right);
				table.add(achievement3).pad(5).width(Gdx.graphics.getWidth()/5).align(Align.left);
				//adding button functionality
				
				
//>>>>>>> Stashed changes
		//adding button functionality
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
