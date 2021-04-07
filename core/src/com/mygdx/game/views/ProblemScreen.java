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
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
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
	private String problemText;
	private String correctAnswer;
	private boolean correctAnswersCheck;
	public int correctAnswersCounter;
	private int problemNumber;
    private Label problem;
	
	public ProblemScreen(Tutor tutor) {
		parent = tutor;
		batch = new SpriteBatch();
		backgroundTexture = new Texture(Gdx.files.internal("images/background.png"));
		stage = new Stage(new ScreenViewport());
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
		stage.draw();
		problemText = "5 + 3 = ?";
		correctAnswer = "8";
		correctAnswersCheck = false;
		correctAnswersCounter = 0;
		problemNumber = 0;
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
        problem = new Label("", skin, "noBackground");
        problem.setText(problemText);
        problem.setAlignment(Align.center);
		TextField answer = new TextField("", skin);
		answer.setMessageText("Answer...");
		answer.setAlignment(Align.center);
		ImageTextButton next = new ImageTextButton("Next", skin, "green");
        //layout
		table.row();
		table.row().pad(10, 0, 0, 0);
		table.add(problem).fillX().uniformX();
		table.row().pad(10, 0, 0, 0);
		table.add(answer).fillX().uniformX();
		table.row().pad(10, 0, 0, 0);
		table.add(next).fillX().uniformX();
		//adding button functionality
		answer.setTextFieldListener(new TextField.TextFieldListener() {
			@Override
			public void keyTyped(TextField textField, char c) {
				if (textField.getText().equals(correctAnswer)) {
					correctAnswersCheck = true;
				}
			}
		});
		next.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (correctAnswersCheck) {
					parent.answerCounter++;
					correctAnswersCounter++;
					correctAnswersCheck = false;
				}
				if (problemNumber == 0) {
					problemText = "2 + 2 = ?";
					correctAnswer = "4";
					problemNumber++;
				}else if (problemNumber == 1) {
					problemText = "3 + 2 = ?";
					correctAnswer = "5";
					problemNumber++;
				}else if (problemNumber == 2) {
					problemNumber = 0;
					problemText = "5 + 3 = ?";
					correctAnswer = "8";
					parent.changeScreen(Tutor.RESULTS);
				}
		        problem.setText(problemText);
		        System.out.println(parent.answerCounter);
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
