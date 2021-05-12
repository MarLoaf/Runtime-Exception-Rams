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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Tutor;

public class HomeScreen implements Screen {
	
	private Tutor parent;
	private Stage stage;
	private SpriteBatch batch;
	private Texture backgroundTexture;
	private String userInfoMessage;
	
	public HomeScreen(Tutor tutor) {
		parent = tutor;
		batch = new SpriteBatch();
		backgroundTexture = new Texture(Gdx.files.internal("images/background.png"));
		stage = new Stage(new ScreenViewport());
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
		stage.draw();
		userInfoMessage = parent.currentUser.getFullName();
	}

	@Override
	public void show() {
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
		//creating objects
		final SelectBox<String> gradeSelectBox = new SelectBox<String>(skin);
		gradeSelectBox.setItems("Kindergarten", "1st Grade", "2nd Grade", "3rd Grade", "4th Grade");
		gradeSelectBox.setAlignment(Align.center);
		final SelectBox<String> topicSelectBox = new SelectBox<String>(skin);
		topicSelectBox.setItems("Counting", "Operations", "Numbers", "Measurements");
		topicSelectBox.setAlignment(Align.center);
		final SelectBox<String> lessonSelectBox = new SelectBox<String>(skin);
		lessonSelectBox.setItems("Test", "Exam", "Tutorial"); //this used to have a practice option, but it became useless with time, now a test can be used as practice
		lessonSelectBox.setAlignment(Align.center);
		Button begin = new Button(skin, "ArrowRight"); //creates an arrow button pointing to the right
		TextTooltip beginPopup = new TextTooltip(" Begin ", skin);
		beginPopup.setInstant(true);
		TextTooltip continuePopup = new TextTooltip(" Continue ", skin);
		continuePopup.setInstant(true);
		Label userInfo = new Label("", skin);
		userInfo.setText(userInfoMessage);
		userInfo.setAlignment(Align.center);
		Button logout = new Button(skin, "Exit"); //creates button with the "exit door"
		Label latestAchievements = new Label("Latest Treasures:", skin); //creates a label with a purple background
		latestAchievements.setAlignment(Align.center);
		Label greatestAchievements = new Label("Greatest Treasures:", skin);
		greatestAchievements.setAlignment(Align.center);
		//ImageTextButton achiemeventsButton = new ImageTextButton("My Achievements", skin); //creates a blue button (blue is default when no color is specified)
		Button achiemeventsButton = new Button(skin, "Treasure");
		TextTooltip exitPopup = new TextTooltip(" Log out ", skin);
		exitPopup.setInstant(true);
		TextTooltip treasurePopup = new TextTooltip(" My treasures ", skin);
		treasurePopup.setInstant(true);
		//layout:
		table.top();
		table.row();
		table.add(achiemeventsButton).uniformX().pad(5).padBottom(100);
		table.add();
		table.add(userInfo).fillX().uniformX().pad(5).padBottom(100).width(Gdx.graphics.getWidth()/5);
		table.add(logout).uniformX().pad(5).padBottom(100);
		table.row();
		table.add(gradeSelectBox).fillX().uniformX().pad(5).padBottom(100).width(Gdx.graphics.getWidth()/5);
		table.add(topicSelectBox).fillX().uniformX().pad(5).padBottom(100).width(Gdx.graphics.getWidth()/5);
		table.add(lessonSelectBox).fillX().uniformX().pad(5).padBottom(100).width(Gdx.graphics.getWidth()/5);
		table.add(begin).uniformX().pad(5).padBottom(100);
		table.row();
		table.add(latestAchievements).colspan(2).align(Align.center);
		table.add(greatestAchievements).colspan(2).align(Align.center);
		table.row();
		if(!parent.currentUser.getLatestAchievements()[0].equals("none")) generateAchievementVisual(table, skin, parent.currentUser.getLatestAchievements()[0], parent.currentUser.getLatestAchievements()[1], parent.currentUser.getAchievement(parent.currentUser.getLatestAchievements()[0], parent.currentUser.getLatestAchievements()[1]));
		else {
			table.add();
			table.add();
		}
		if(!parent.currentUser.getGreatestAchievements()[0].equals("none")) generateAchievementVisual(table, skin, parent.currentUser.getGreatestAchievements()[0], parent.currentUser.getGreatestAchievements()[1], parent.currentUser.getAchievement(parent.currentUser.getGreatestAchievements()[0], parent.currentUser.getGreatestAchievements()[1]));
		else {
			table.add();
			table.add();
		}
		table.row();
		if(!parent.currentUser.getLatestAchievements()[2].equals("none")) generateAchievementVisual(table, skin, parent.currentUser.getLatestAchievements()[2], parent.currentUser.getLatestAchievements()[3], parent.currentUser.getAchievement(parent.currentUser.getLatestAchievements()[2], parent.currentUser.getLatestAchievements()[3]));
		else {
			table.add();
			table.add();
		}
		if(!parent.currentUser.getGreatestAchievements()[2].equals("none")) generateAchievementVisual(table, skin, parent.currentUser.getGreatestAchievements()[2], parent.currentUser.getGreatestAchievements()[3], parent.currentUser.getAchievement(parent.currentUser.getGreatestAchievements()[2], parent.currentUser.getGreatestAchievements()[3]));
		else {
			table.add();
			table.add();
		}
		table.row();
		if(!parent.currentUser.getLatestAchievements()[4].equals("none")) generateAchievementVisual(table, skin, parent.currentUser.getLatestAchievements()[4], parent.currentUser.getLatestAchievements()[5], parent.currentUser.getAchievement(parent.currentUser.getLatestAchievements()[4], parent.currentUser.getLatestAchievements()[5]));
		else {
			table.add();
			table.add();
		}
		if(!parent.currentUser.getGreatestAchievements()[4].equals("none")) generateAchievementVisual(table, skin, parent.currentUser.getGreatestAchievements()[4], parent.currentUser.getGreatestAchievements()[5], parent.currentUser.getAchievement(parent.currentUser.getGreatestAchievements()[4], parent.currentUser.getGreatestAchievements()[5]));
		else {
			table.add();
			table.add();
		}
		//adding button functionality
		if(parent.problemNumber==0) begin.addListener(beginPopup);
		else begin.addListener(continuePopup);
		logout.addListener(exitPopup);
		achiemeventsButton.addListener(treasurePopup);
		gradeSelectBox.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.gradeSelection = gradeSelectBox.getSelected();
				if(parent.gradeSelection.equals("Kindergarten")) {
					topicSelectBox.setItems("Counting", "Operations", "Numbers", "Measurements");
				}
				else if(parent.gradeSelection.equals("1st Grade")) {
					topicSelectBox.setItems("Operations", "Numbers", "Measurements");
				}
				else if(parent.gradeSelection.equals("2nd Grade")) {
					topicSelectBox.setItems("Operations", "Numbers", "Measurements");
				}
				else if(parent.gradeSelection.equals("3rd Grade")) {
					topicSelectBox.setItems("Operations", "Numbers", "Fractions", "Measurements");
				}
				else if(parent.gradeSelection.equals("4th Grade")) {
					topicSelectBox.setItems("Operations", "Numbers", "Fractions", "Measurements");
				}
			}
		});
		topicSelectBox.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.topicSelection = topicSelectBox.getSelected();
			}
		});
		lessonSelectBox.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.lessonSelection = lessonSelectBox.getSelected();
				if(parent.lessonSelection.equals("Exam")) {
					topicSelectBox.setDisabled(true);
				}
				else {
					topicSelectBox.setDisabled(false);
				}
			}
		});
		logout.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.currentUser = null;
				parent.changeScreen(Tutor.LOGIN);
			}
		});
		begin.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (parent.lessonSelection.equals("Tutorial")) parent.changeScreen(Tutor.TUTORIAL);
				else if (parent.problemNumber == 0) parent.changeScreen(Tutor.PROBLEMENTRY);
				else parent.changeScreen(Tutor.PROBLEM);
			}
		});
		achiemeventsButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(Tutor.ACHIEVEMENTS);
			}
		});
		//default values
		parent.gradeSelection = gradeSelectBox.getSelected();
		parent.topicSelection = topicSelectBox.getSelected();
		parent.lessonSelection = lessonSelectBox.getSelected();
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
	
	private void generateAchievementVisual(Table table, Skin skin, String grade, String topic, int a) {
		//adds an achievement picture + label to the table
		switch(a) {
		case 0:
			table.add(new Image(new Texture(Gdx.files.internal("images/NoMedal.png")))).pad(5).align(Align.right);
			break;
		case 1:
			table.add(new Image(new Texture(Gdx.files.internal("images/Bronze.png")))).pad(5).align(Align.right);
			break;
		case 2:
			table.add(new Image(new Texture(Gdx.files.internal("images/Silver.png")))).pad(5).align(Align.right);
			break;
		case 3:
			table.add(new Image(new Texture(Gdx.files.internal("images/Gold.png")))).pad(5).align(Align.right);
			break;
		}
		table.add(new Label(grade + " " + topic, skin, "noBackground")).pad(5).width(Gdx.graphics.getWidth()/5).align(Align.left);
	}

}
