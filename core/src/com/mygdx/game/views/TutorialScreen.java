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
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Tutor;
import java.awt.Desktop;
import java.net.URI;


public class TutorialScreen implements Screen {

	private Tutor parent;
	private Stage stage;
	private SpriteBatch batch;
	private Texture backgroundTexture;
	private String userInfoMessage;
	private String tutorialMessage;
	
	public TutorialScreen(Tutor tutor) {
		parent = tutor;
		batch = new SpriteBatch();
		backgroundTexture = new Texture(Gdx.files.internal("images/background.png"));
		stage = new Stage(new ScreenViewport());
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
		stage.draw();
		userInfoMessage = "Student: " + parent.currentUser.getFullName();
		tutorialMessage = parent.gradeSelection + " " + parent.topicSelection;
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
		Label userInfo = new Label(userInfoMessage, skin);
		userInfo.setAlignment(Align.center);
		Label tutorialTopic = new Label(tutorialMessage, skin);
		tutorialTopic.setAlignment(Align.center);
		Button video = new Button(skin, "Video");
		ImageTextButton additionalHelp = new ImageTextButton("Links to additional Help", skin);
		ImageTextButton askTeacher = new ImageTextButton("Click here for help!", skin, "pink");
		Button back = new Button(skin, "Exit");
		TextTooltip exitPopup = new TextTooltip("Back", skin);
		exitPopup.setInstant(true);
		TextTooltip videoPopup = new TextTooltip("Video", skin);
		videoPopup.setInstant(true);
		//layout:
		table.top();
		table.row();
		table.add().fillX().uniformX().pad(5).padBottom(150).width(Gdx.graphics.getWidth()/5);
		table.add().fillX().uniformX().pad(5).padBottom(150).width(Gdx.graphics.getWidth()/5);
		table.add(userInfo).fillX().uniformX().pad(5).padBottom(150).width(Gdx.graphics.getWidth()/5);
		table.add(back).uniformX().pad(5).padBottom(150);
		table.row();
		table.add();
		table.add(tutorialTopic).fillX().uniformX().pad(5).colspan(2).width(Gdx.graphics.getWidth()/5);
		table.add();
		table.row();
		table.add();
		table.add(video).fillX().uniformX().pad(5).colspan(2).width(Gdx.graphics.getWidth()/5);
		table.add();
		table.row();
		table.add();
		table.add(additionalHelp).fillX().uniformX().pad(5).width(Gdx.graphics.getWidth()/5).padBottom(100);
		table.add(askTeacher).fillX().uniformX().pad(5).width(Gdx.graphics.getWidth()/5).padBottom(100);
		//adding button functionality
		back.addListener(exitPopup);
		video.addListener(videoPopup);
		back.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(Tutor.HOME);
			}
		});
		video.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
					switch(parent.gradeSelection) {
					case "Kindergarten":
						switch(parent.topicSelection) {
						case "Counting":
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://www.youtube.com/watch?v=QtBDL8EiNZo"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							break;
						case "Operations":
							break;
						case "Numbers":
							break;
						case "Measurements":
							break;
						}
						break;
					case "1st Grade":
						switch(parent.topicSelection) {
						case "Operations":
							break;
						case "Numbers":
							break;
						case "Measurements":
							break;
						}
						break;
					case "2nd Grade":
						switch(parent.topicSelection) {
						case "Operations":
							break;
						case "Numbers":
							break;
						case "Measurements":
							break;
						}
						break;
					case "3rd Grade":
						switch(parent.topicSelection) {
						case "Operations":
							break;
						case "Numbers":
							break;
						case "Fractions":
							break;
						case "Measurements":
							break;
						}
						break;
					case "4th Grade":
						switch(parent.topicSelection) {
						case "Operations":
							break;
						case "Numbers":
							break;
						case "Fractions":
							break;
						case "Measurements":
							break;
						}
						break;
					}
				}
			});
		additionalHelp.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
					switch(parent.gradeSelection) {
					case "Kindergarten":
						switch(parent.topicSelection) {
						case "Counting":
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://www.kidsmathtv.com/2nd-grade-videos/"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							break;
						case "Operations":
							break;
						case "Numbers":
							break;
						case "Measurements":
							break;
						}
						break;
					case "1st Grade":
						switch(parent.topicSelection) {
						case "Operations":
							break;
						case "Numbers":
							break;
						case "Measurements":
							break;
						}
						break;
					case "2nd Grade":
						switch(parent.topicSelection) {
						case "Operations":
							break;
						case "Numbers":
							break;
						case "Measurements":
							break;
						}
						break;
					case "3rd Grade":
						switch(parent.topicSelection) {
						case "Operations":
							break;
						case "Numbers":
							break;
						case "Fractions":
							break;
						case "Measurements":
							break;
						}
						break;
					case "4th Grade":
						switch(parent.topicSelection) {
						case "Operations":
							break;
						case "Numbers":
							break;
						case "Fractions":
							break;
						case "Measurements":
							break;
						}
						break;
					}
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
