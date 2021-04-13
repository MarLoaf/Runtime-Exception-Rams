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
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.ui.TooltipManager;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
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
	private String gradeSelection = "";
	private String topicSelection = "";
	private String lessonSelection = "";
	
	public HomeScreen(Tutor tutor) {
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
		topicSelectBox.setItems("Topic 1", "Topic 2", "Topic 3", "Topic 4");
		topicSelectBox.setAlignment(Align.center);
		final SelectBox<String> lessonSelectBox = new SelectBox<String>(skin);
		lessonSelectBox.setItems("Practice", "Test", "Exam", "Tutorial");
		lessonSelectBox.setAlignment(Align.center);
		Button begin = new Button(skin, "ArrowRight"); //creates an arrow button pointing to the right
		TextTooltip beginPopup = new TextTooltip("Begin", skin);
		beginPopup.setInstant(true);
		TextTooltip continuePopup = new TextTooltip("Continue", skin);
		continuePopup.setInstant(true);
		Label userInfo = new Label("", skin);
		userInfo.setText(userInfoMessage);
		userInfo.setAlignment(Align.center);
		Button logout = new Button(skin, "Exit"); //creates button with the "exit door"
		Image Iron1 = new Image(new Texture(Gdx.files.internal("images/iron.png"))); //image NOT BUTTON with the iron pickaxe
		Image AdditionIron = new Image(new Texture(Gdx.files.internal("images/iron.png")));
		Image AdditionDiamond = new Image(new Texture(Gdx.files.internal("images/diamond.png"))); //image NOT BUTTON with the diamond
		Image AdditionRuby = new Image(new Texture(Gdx.files.internal("images/ruby.png"))); //image NOT BUTTON with the ruby
		Image Ruby = new Image(new Texture(Gdx.files.internal("images/ruby.png")));
		Image Diamond = new Image(new Texture(Gdx.files.internal("images/diamond.png")));
		Image Iron = new Image(new Texture(Gdx.files.internal("images/iron.png")));
		Label achievement1 = new Label("Numbers", skin, "noBackground"); //creates a label without a background - just text
		Label achievement2 = new Label("Subtraction", skin, "noBackground");
		Label achievement3 = new Label("Division", skin, "noBackground");
		Label latestAchievements = new Label("Latest Achivements:", skin); //creates a label with a purple background
		latestAchievements.setAlignment(Align.center);
		Label greatestAchievements = new Label("Greatest Achievements:", skin);
		greatestAchievements.setAlignment(Align.center);
		Label fractions = new Label("Fractions", skin, "noBackground");
		Label addition = new Label("Addition", skin, "noBackground");
		ImageTextButton achiemeventsButton = new ImageTextButton("My Achievements", skin); //creates a blue button (blue is default when no color is specified)
		TextTooltip RubyPopup = new TextTooltip("Top achievement", skin);
		RubyPopup.setInstant(true);
		TextTooltip DiamondPopup = new TextTooltip("Medium achievement", skin);
		DiamondPopup.setInstant(true);
		TextTooltip IronPopup = new TextTooltip("Lowest achievement", skin);
		IronPopup.setInstant(true);
		//layout:
		table.top();
		table.row();
		table.add(achiemeventsButton).fillX().uniformX().pad(5).padBottom(100).width(Gdx.graphics.getWidth()/5);
		table.add();
		table.add(userInfo).fillX().uniformX().pad(5).padBottom(100).width(Gdx.graphics.getWidth()/5);
		table.add(logout).uniformX().pad(5).padBottom(100);
		table.row();
		table.add(gradeSelectBox).fillX().uniformX().pad(5).padBottom(100).width(Gdx.graphics.getWidth()/5);
		table.add(topicSelectBox).fillX().uniformX().pad(5).padBottom(100).width(Gdx.graphics.getWidth()/5);
		table.add(lessonSelectBox).fillX().uniformX().pad(5).padBottom(100).width(Gdx.graphics.getWidth()/5);
		table.add(begin).uniformX().pad(5).padBottom(100);
		table.row();
		table.add(latestAchievements).colspan(2).width(Gdx.graphics.getWidth()/5).align(Align.center);
		table.add(greatestAchievements).colspan(2).width(Gdx.graphics.getWidth()/5).align(Align.center);
		table.row();
		table.add(Iron1).pad(5).align(Align.right);
		table.add(fractions).pad(5).width(Gdx.graphics.getWidth()/5).align(Align.right);
		table.add(Ruby).pad(5).align(Align.right);
		table.add(achievement1).pad(5).width(Gdx.graphics.getWidth()/5).align(Align.left);
		table.row();
		if (parent.additionAchievement == 1) {
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
		table.add(Diamond).pad(5).align(Align.right);
		table.add(achievement2).pad(5).width(Gdx.graphics.getWidth()/5).align(Align.left);
		table.row();
		table.add();
		table.add();
		table.add(Iron).pad(5).align(Align.right);
		table.add(achievement3).pad(5).width(Gdx.graphics.getWidth()/5).align(Align.left);
		//adding button functionality
		if(parent.problemNumber==0) begin.addListener(beginPopup);
		else begin.addListener(continuePopup);
		Ruby.addListener(RubyPopup);
		Diamond.addListener(DiamondPopup);
		Iron.addListener(IronPopup);
		Iron1.addListener(IronPopup);
		AdditionIron.addListener(IronPopup);
		AdditionDiamond.addListener(DiamondPopup);
		AdditionRuby.addListener(RubyPopup);
		gradeSelectBox.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				gradeSelection = gradeSelectBox.getSelected();
			}
		});
		topicSelectBox.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				topicSelection = topicSelectBox.getSelected();
			}
		});
		lessonSelectBox.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				lessonSelection = lessonSelectBox.getSelected();
			}
		});
		logout.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(Tutor.LOGIN);
			}
		});
		begin.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (lessonSelection.equals("Tutorial")) parent.changeScreen(Tutor.TUTORIAL);
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
		gradeSelection = gradeSelectBox.getSelected();
		topicSelection = topicSelectBox.getSelected();
		lessonSelection = lessonSelectBox.getSelected();
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
