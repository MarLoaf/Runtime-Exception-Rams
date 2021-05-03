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
		userInfoMessage = parent.currentUser.getFullName();
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
		TextTooltip exitPopup = new TextTooltip(" Back ", skin);
		exitPopup.setInstant(true);
		TextTooltip videoPopup = new TextTooltip(" Video ", skin);
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
		table.add(additionalHelp).fillX().uniformX().pad(5).colspan(2).width(Gdx.graphics.getWidth()/5);
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
								d.browse(new URI("https://www.youtube.com/watch?v=ZJEIKkPXirg"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							break;
						case "Operations":
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://www.youtube.com/watch?v=igcoDFokKzU"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							break;
						case "Numbers":
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://www.youtube.com/watch?v=0b2tbYP4a_Q"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							break;
						case "Measurements":
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://www.youtube.com/watch?v=8lD3qDZdnp8"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							break;
						}
						break;
					case "1st Grade":				
						switch(parent.topicSelection) {
						case "Operations":
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://www.youtube.com/watch?v=FtjkzSnZ4G4"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							break;
						case "Numbers":
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://www.youtube.com/watch?v=2sghJbCPnyw"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							break;
						case "Measurements":
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://www.youtube.com/watch?v=2wUsdsae0ro"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							break;
						}
						break;
					case "2nd Grade":
						switch(parent.topicSelection) {
						case "Operations":
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://www.youtube.com/watch?v=LaZu6W2xFy4"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							break;
						case "Numbers":
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://www.youtube.com/watch?v=PyzVG3xkONs"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							break;
						case "Measurements":
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://www.youtube.com/watch?v=iz7Ux7kq1Ho"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							break;
						}
						break;
					case "3rd Grade":
						switch(parent.topicSelection) {
						case "Operations":
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://www.youtube.com/watch?v=rmhTkanhVyI"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							break;
						case "Numbers":
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://www.youtube.com/watch?v=HuiYOLPOzEk"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							break;
						case "Fractions":
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://www.youtube.com/watch?v=_EEDtmFY-hg"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							break;
						case "Measurements":
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://www.youtube.com/watch?v=cKbmvLv-FRo"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							break;
						}
						break;
					case "4th Grade":
						switch(parent.topicSelection) {
						case "Operations":
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://www.youtube.com/watch?v=GG-hoRpD-yQ"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							break;
						case "Numbers":
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://www.youtube.com/watch?v=wUBQF0DI_xg"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							break;
						case "Fractions":
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://www.youtube.com/watch?v=lqNFLeUCWAE&t=73s"));
								d.browse(new URI("https://www.youtube.com/watch?v=82kKb093oic"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							break;
						case "Measurements":
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://www.youtube.com/watch?v=1jmScxZ0ffM"));
								d.browse(new URI("https://www.youtube.com/watch?v=dOy-yqKjg3k"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
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
								d.browse(new URI("https://classace.io/learn/math/1stgrade/counting-by-10s-and-1s"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							break;
						case "Operations":
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://www.k5learning.com/free-preschool-kindergarten-worksheets/addition"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://www.k5learning.com/free-preschool-kindergarten-worksheets/subtraction"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							break;
						case "Numbers":
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://www.mrsrichardsonsclass.com/a-little-funky-fractions/"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							break;
						case "Measurements":
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://www.kindergartenkindergarten.com/measurement/"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							break;
						}
						break;
					case "1st Grade":
						switch(parent.topicSelection) {
						case "Operations":
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://www.k5learning.com/free-math-worksheets/first-grade-1/addition"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							break;
						case "Numbers":
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://learnzillion.com/wikis/64125-developing-addition-and-subtraction-strategies/"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							break;
						case "Measurements":
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://learnzillion.com/wikis/64122-ordering-and-comparing-lengths/"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							break;
						}
						break;
					case "2nd Grade":
						switch(parent.topicSelection) {
						case "Operations":
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://www.whatihavelearnedteaching.com/models-strategies-for-two-digit-addition-subtraction/"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							break;
						case "Numbers":
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://classace.io/learn/math/2ndgrade/skip-counting-by-100"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							break;
						case "Measurements":
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://classace.io/learn/math/2ndgrade/units-of-length-2"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							break;
						}
					case "3rd Grade":
						switch(parent.topicSelection) {
						case "Operations":
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://www.mathsisfun.com/operation-order-pemdas.html"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							break;
						case "Numbers":
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://www.keynotesupport.com/excel-functions/rounding-numbers.shtml#:~:text=Each%20digit%20in%20a%20number,the%20digit%20to%20its%20right"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							break;
						case "Fractions":
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://www.splashlearn.com/math-vocabulary/fractions/fraction"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							break;
						case "Measurements":
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://classace.io/learn/math/3rdgrade/units-of-length"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							break;
						}
						break;
					case "4th Grade":
						switch(parent.topicSelection) {
						case "Operations":
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://content.byui.edu/file/b8b83119-9acc-4a7b-bc84-efacf9043998/1/Math-1-6-1.html"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							break;
						case "Numbers":
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://www.mathsisfun.com/numbers/estimation.html"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							break;
						case "Fractions":
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://www.splashlearn.com/math-vocabulary/fractions/fraction"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
							break;
						case "Measurements":
							try {
								Desktop d = Desktop.getDesktop();
								d.browse(new URI("https://www.splashlearn.com/math-vocabulary/measurements/measurement-units"));
							} catch (Exception e) {
								System.out.println("URL fail @ tutorial page");
							}
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
