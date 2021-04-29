package com.mygdx.game.views;

import java.util.ArrayList;

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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
	private Image countingProblemImage;
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
		Label currentAssignment = new Label(parent.gradeSelection + " " + parent.topicSelection, skin);
		currentAssignment.setAlignment(Align.center);
		Label userInfo = new Label("", skin);
		userInfo.setText(userInfoMessage);
		userInfo.setAlignment(Align.center);
		if (parent.problems.get(parent.problemNumber).getCountingImage()!=null) {
			countingProblemImage = parent.problems.get(parent.problemNumber).getCountingImage();
		}
        problem = new Label("", skin, "noBackground");
        problem.setText(parent.problems.get(parent.problemNumber).getProblemText());
        problem.setAlignment(Align.center);
		answer = new TextField(parent.problems.get(parent.problemNumber).getSelectedAnswer(), skin);
		answer.setMessageText("Answer...");
		answer.setAlignment(Align.center);
		Button next = new Button(skin, "ArrowRight");
		Button previous = new Button(skin, "ArrowLeft");
		Button finish = new Button(skin, "OK");
		Button cancel = new Button(skin, "Cancel");
		TextTooltip nextPopup = new TextTooltip(" Next ", skin);
		nextPopup.setInstant(true);
		TextTooltip previousPopup = new TextTooltip(" Previous ", skin);
		previousPopup.setInstant(true);
		TextTooltip finishPopup = new TextTooltip(" Finish ", skin);
		finishPopup.setInstant(true);
		TextTooltip cancelPopup = new TextTooltip(" Cancel ", skin);
		cancelPopup.setInstant(true);
		Button back = new Button(skin, "Exit");
		rightAnswer = new CheckBox(parent.problems.get(parent.problemNumber).getCorrectAnswer(), skin);
		if(parent.problems.get(parent.problemNumber).getSelectedAnswer().equals(parent.problems.get(parent.problemNumber).getCorrectAnswer())) rightAnswer.setChecked(true);
		wrongAnswer0 = new CheckBox("", skin);
		wrongAnswer1 = new CheckBox("", skin);
		wrongAnswer2 = new CheckBox("", skin);
		ButtonGroup<CheckBox> answersGroup = new ButtonGroup<CheckBox>(rightAnswer, wrongAnswer0, wrongAnswer1, wrongAnswer2);
		answersGroup.setMaxCheckCount(1);
		answersGroup.setMinCheckCount(0);
		answersGroup.setUncheckLast(true);
		if (parent.problems.get(parent.problemNumber).getWrongAnswers()!=null) {
			wrongAnswer0.setText(parent.problems.get(parent.problemNumber).getWrongAnswers()[0]);
			if(parent.problems.get(parent.problemNumber).getSelectedAnswer().equals(parent.problems.get(parent.problemNumber).getWrongAnswers()[0])) wrongAnswer0.setChecked(true);
			wrongAnswer1.setText(parent.problems.get(parent.problemNumber).getWrongAnswers()[1]);
			if(parent.problems.get(parent.problemNumber).getSelectedAnswer().equals(parent.problems.get(parent.problemNumber).getWrongAnswers()[1])) wrongAnswer1.setChecked(true);
			wrongAnswer2.setText(parent.problems.get(parent.problemNumber).getWrongAnswers()[2]);
			if(parent.problems.get(parent.problemNumber).getSelectedAnswer().equals(parent.problems.get(parent.problemNumber).getWrongAnswers()[2])) wrongAnswer2.setChecked(true);
		}
		TextTooltip exitPopup = new TextTooltip(" Back ", skin);
		exitPopup.setInstant(true);
        //layout
		table.top();
		table.row();
		if (parent.problems.get(parent.problemNumber).getCountingImage()!=null) {
			table.add(currentAssignment).fillX().uniformX().pad(5).padBottom(25).width(Gdx.graphics.getWidth()/5);
			table.add().fillX().uniformX().pad(5).padBottom(25).width(Gdx.graphics.getWidth()/5);
			table.add(userInfo).fillX().uniformX().pad(5).padBottom(25).width(Gdx.graphics.getWidth()/5);
			table.add(back).uniformX().pad(5).padBottom(25);
		}else {
			table.add(currentAssignment).fillX().uniformX().pad(5).padBottom(270).width(Gdx.graphics.getWidth()/5);
			table.add().fillX().uniformX().pad(5).padBottom(270).width(Gdx.graphics.getWidth()/5);
			table.add(userInfo).fillX().uniformX().pad(5).padBottom(270).width(Gdx.graphics.getWidth()/5);
			table.add(back).uniformX().pad(5).padBottom(270);
		}
		if (parent.problems.get(parent.problemNumber).getCountingImage()!=null) {
			table.row();
			table.add(countingProblemImage).colspan(4).uniformX();
		}
		table.row();
		table.add();
		table.add(problem).colspan(2).fillX().uniformX().width(Gdx.graphics.getWidth()/4);
		if (parent.problems.get(parent.problemNumber).getWrongAnswers()!=null) {
			table.row();
			ArrayList<CheckBox> answers = new ArrayList<CheckBox>(4);
			int n;
			answers.add(rightAnswer);
			answers.add(wrongAnswer0);
			answers.add(wrongAnswer1);
			answers.add(wrongAnswer2);
			for(int i=4; i>0; i--) {
				n = (int)Math.floor(Math.random()*i);
				table.add(answers.remove(n)).pad(5).fillX().uniformX();
			}
		}else {
			table.row();
			table.add();
			table.add(answer).colspan(2).pad(5).fillX().uniformX().width(Gdx.graphics.getWidth()/4);
		}
		table.row();
		table.add();
		if (parent.problemNumber == 0) table.add(cancel).pad(5).uniformX();
		else table.add(previous).pad(5).uniformX();
		if (parent.problemNumber == parent.problems.size()-1) table.add(finish).pad(5).uniformX();
		else table.add(next).pad(5).uniformX();
		//adding button functionality
		next.addListener(nextPopup);
		previous.addListener(previousPopup);
		finish.addListener(finishPopup);
		cancel.addListener(cancelPopup);
		back.addListener(exitPopup);
		answer.setTextFieldListener(new TextField.TextFieldListener() {
			@Override
			public void keyTyped(TextField textField, char c) {
				parent.problems.get(parent.problemNumber).setSelectedAnswer(textField.getText());
			}
		});
		rightAnswer.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.problems.get(parent.problemNumber).setSelectedAnswer(rightAnswer.getText().toString());
			}
		});
		wrongAnswer0.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.problems.get(parent.problemNumber).setSelectedAnswer(wrongAnswer0.getText().toString());
			}
		});
		wrongAnswer1.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.problems.get(parent.problemNumber).setSelectedAnswer(wrongAnswer1.getText().toString());
			}
		});
		wrongAnswer2.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.problems.get(parent.problemNumber).setSelectedAnswer(wrongAnswer2.getText().toString());
			}
		});
		next.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (parent.problemNumber == parent.problems.size()-1) {
					parent.problemNumber = 0;
					confirmAnswers();
					clearAnswers();
					parent.changeScreen(Tutor.RESULTS);
				}else {
					parent.problemNumber++;
					parent.changeScreen(Tutor.PROBLEM);
				}
			}
		});
		previous.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (parent.problemNumber != 0) {
					parent.problemNumber--;
					parent.changeScreen(Tutor.PROBLEM);
				}
			}
		});
		finish.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (parent.problemNumber == parent.problems.size()-1) {
					parent.problemNumber = 0;
					confirmAnswers();
					clearAnswers();
					parent.changeScreen(Tutor.RESULTS);
				}
			}
		});
		cancel.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (parent.problemNumber == 0) {
					parent.changeScreen(Tutor.HOME);
				}
			}
		});
		back.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.problemNumber = 0; //TODO if resume functionality is back in, this has to be removed
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
	
	private void confirmAnswers() {
		for(int i = 0; i < parent.problems.size(); i++) {
			if (parent.problems.get(i).checkAnswer()) parent.answerCounter++;
		}
	}
	
	private void clearAnswers() {
		for(int i = 0; i < parent.problems.size(); i++) {
			parent.problems.get(i).setSelectedAnswer("");
		}
	}

}
