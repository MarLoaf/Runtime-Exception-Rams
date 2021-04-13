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
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Tutor;

public class ProblemScreen implements Screen {

	private Tutor parent;
	private Stage stage;
	private SpriteBatch batch;
	private Texture backgroundTexture;
	private boolean correctAnswersCheck;
    private Label problem;
	private TextField answer;
	private CheckBox rightAnswer;
	private CheckBox wrongAnswer0;
	private CheckBox wrongAnswer1;
	private CheckBox wrongAnswer2;
	private String userInfoMessage;
	
	public ProblemScreen(Tutor tutor) {
		parent = tutor;
		batch = new SpriteBatch();
		backgroundTexture = new Texture(Gdx.files.internal("images/background.png"));
		stage = new Stage(new ScreenViewport());
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
		stage.draw();
		userInfoMessage = "Student: " + parent.currentUser.getFullName();
		//setting up the problem
		correctAnswersCheck = false;
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
		Label userInfo = new Label("", skin);
		userInfo.setText(userInfoMessage);
		userInfo.setAlignment(Align.center);
        problem = new Label("", skin, "noBackground");
        problem.setText(parent.problems[parent.problemNumber].getProblemText());
        problem.setAlignment(Align.center);
		answer = new TextField("", skin);
		answer.setMessageText("Answer...");
		answer.setAlignment(Align.center);
		Button next = new Button(skin, "ArrowRight");
		TextTooltip nextPopup = new TextTooltip("Next", skin);
		nextPopup.setInstant(true);
		Button back = new Button(skin, "Exit");
		rightAnswer = new CheckBox(parent.problems[parent.problemNumber].getCorrectAnswer(), skin);
		wrongAnswer0 = new CheckBox("", skin);
		wrongAnswer1 = new CheckBox("", skin);
		wrongAnswer2 = new CheckBox("", skin);
		ButtonGroup<CheckBox> answersGroup = new ButtonGroup<CheckBox>(rightAnswer, wrongAnswer0, wrongAnswer1, wrongAnswer2);
		answersGroup.setMaxCheckCount(1);
		answersGroup.setMinCheckCount(0);
		answersGroup.setUncheckLast(true);
		if (parent.problems[parent.problemNumber].getWrongAnswers()!=null) {
			wrongAnswer0.setText(parent.problems[parent.problemNumber].getWrongAnswers()[0]);
			wrongAnswer1.setText(parent.problems[parent.problemNumber].getWrongAnswers()[1]);
			wrongAnswer2.setText(parent.problems[parent.problemNumber].getWrongAnswers()[2]);
		}
		TextTooltip exitPopup = new TextTooltip("Back", skin);
		exitPopup.setInstant(true);
        //layout
		table.top();
		table.row();
		table.add().fillX().uniformX().pad(5).padBottom(270).width(Gdx.graphics.getWidth()/5);
		table.add().fillX().uniformX().pad(5).padBottom(270).width(Gdx.graphics.getWidth()/5);
		table.add(userInfo).fillX().uniformX().pad(5).padBottom(270).width(Gdx.graphics.getWidth()/5);
		table.add(back).uniformX().pad(5).padBottom(270);
		table.row();
		table.add();
		table.add(problem).colspan(2).fillX().uniformX().width(Gdx.graphics.getWidth()/4);
		if (parent.problems[parent.problemNumber].getWrongAnswers()!=null) {
			table.row();
			table.add(rightAnswer).pad(5).fillX().uniformX();
			table.add(wrongAnswer0).pad(5).fillX().uniformX();
			table.add(wrongAnswer1).pad(5).fillX().uniformX();
			table.add(wrongAnswer2).pad(5).fillX().uniformX();
		}else {
			table.row();
			table.add();
			table.add(answer).colspan(2).pad(5).fillX().uniformX().width(Gdx.graphics.getWidth()/4);
		}
		table.row();
		table.add();
		table.add(next).colspan(2).pad(5).uniformX();
		//adding button functionality
		next.addListener(nextPopup);
		back.addListener(exitPopup);
		answer.setTextFieldListener(new TextField.TextFieldListener() {
			@Override
			public void keyTyped(TextField textField, char c) {
				if (textField.getText().equals(parent.problems[parent.problemNumber].getCorrectAnswer())) {
					correctAnswersCheck = true;
				}
			}
		});
		rightAnswer.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				correctAnswersCheck = true;
			}
		});
		wrongAnswer0.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				correctAnswersCheck = false;
			}
		});
		wrongAnswer1.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				correctAnswersCheck = false;
			}
		});
		wrongAnswer2.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				correctAnswersCheck = false;
			}
		});
		next.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (correctAnswersCheck) {
					parent.answerCounter++;
					correctAnswersCheck = false;
				}
				if (parent.problemNumber == parent.problems.length-1) {
					parent.problemNumber = 0;
					parent.changeScreen(Tutor.RESULTS);
				}else {
					parent.problemNumber++;
					parent.changeScreen(Tutor.PROBLEM);
				}
			}
		});
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
