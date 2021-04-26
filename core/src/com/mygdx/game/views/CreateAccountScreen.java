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
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
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

public class CreateAccountScreen implements Screen {

	private Tutor parent;
	private Stage stage;
	private SpriteBatch batch;
	private Texture backgroundTexture;
	private String username;
	private String password;
	private String fullName;
	private String secretQuestion;
	private String secretAnswer;
	
	public CreateAccountScreen(Tutor tutor) {
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
        //making tables
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		//creating different buttons/textfields/labels
		final SelectBox<String> secretQuestionBox = new SelectBox<String>(skin);
		secretQuestionBox.setItems("What's your favorite color?","What's the name of your first pet?","What's your mother's maiden name?");
		secretQuestionBox.setAlignment(Align.center);
		Label createAccountLabel = new Label("Create Account", skin);
		createAccountLabel.setAlignment(Align.center);
		Label usernameLabel = new Label("Username:", skin);
	    Label passwordLabel = new Label("Password:", skin);
	    Label fullnameLabel = new Label("Full Name:", skin);
	    TextField usernameText = new TextField("", skin);
	    usernameText.setMessageText("Enter username...");
	    TextField passwordText = new TextField("", skin);
	    passwordText.setMessageText("Enter password...");
	    TextField fullnameText = new TextField("", skin);
	    fullnameText.setMessageText("Enter your full name...");
	    TextField secretAnswerText = new TextField("", skin);
	    secretAnswerText.setMessageText("Enter secret answer...");
		Button back = new Button(skin, "Exit");
		ImageTextButton createAccount = new ImageTextButton("Create Account", skin,"green");
		usernameLabel.setAlignment(Align.center);
		usernameText.setAlignment(Align.center);
		passwordLabel.setAlignment(Align.center);
		passwordText.setAlignment(Align.center);
		fullnameLabel.setAlignment(Align.center);
		fullnameText.setAlignment(Align.center);
		secretAnswerText.setAlignment(Align.center);
		TextTooltip lengthPopup = new TextTooltip("At least 5 characters", skin);
		lengthPopup.setInstant(true);
		TextTooltip secretQuestionPopup = new TextTooltip("Secret Question", skin);
		secretQuestionPopup.setInstant(true);
		TextTooltip exitPopup = new TextTooltip("Back", skin);
		exitPopup.setInstant(true);
		//layout:
		table.top();
		table.row();
		table.add().fillX().uniformX().pad(5).padBottom(148).width(Gdx.graphics.getWidth()/5);
		table.add(createAccountLabel).colspan(2).fillX().uniformX().pad(5).padBottom(148).width(Gdx.graphics.getWidth()/5);
		table.add(back).uniformX().pad(5).padBottom(148);
		table.row();
		table.add();
		table.add(usernameLabel).fillX().uniformX().pad(5).width(Gdx.graphics.getWidth()/5);
		table.add(usernameText).fillX().uniformX().pad(5).width(Gdx.graphics.getWidth()/5);
		table.row();
		table.add();
		table.add(passwordLabel).fillX().uniformX().pad(5).width(Gdx.graphics.getWidth()/5);
		table.add(passwordText).fillX().uniformX().pad(5).width(Gdx.graphics.getWidth()/5);
		table.row();
		table.add();
		table.add(fullnameLabel).fillX().uniformX().pad(5).width(Gdx.graphics.getWidth()/5);
		table.add(fullnameText).fillX().uniformX().pad(5).width(Gdx.graphics.getWidth()/5);
		table.row();
		table.add();
		table.add(secretQuestionBox).fillX().uniformX().pad(5).width(Gdx.graphics.getWidth()/5);
		table.add(secretAnswerText).fillX().uniformX().pad(5).width(Gdx.graphics.getWidth()/5);
		table.row();
		table.add();
		table.add(createAccount).pad(5).colspan(2).width(Gdx.graphics.getWidth()/5);
		//adding button functionality
		secretQuestionBox.addListener(secretQuestionPopup);
		usernameLabel.addListener(lengthPopup);
		passwordLabel.addListener(lengthPopup);
		usernameText.addListener(lengthPopup);
		passwordText.addListener(lengthPopup);
		back.addListener(exitPopup);
		secretQuestionBox.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				secretQuestion = secretQuestionBox.getSelected();
			}
		});
		usernameText.setTextFieldListener(new TextField.TextFieldListener() {
			@Override
			public void keyTyped(TextField textField, char c) {
				username = textField.getText();
			}
		});
		passwordText.setTextFieldListener(new TextField.TextFieldListener() {
			@Override
			public void keyTyped(TextField textField, char c) {
				password = textField.getText();
			}
		});
		fullnameText.setTextFieldListener(new TextField.TextFieldListener() {
			@Override
			public void keyTyped(TextField textField, char c) {
				fullName = textField.getText();
			}
		});
		secretAnswerText.setTextFieldListener(new TextField.TextFieldListener() {
			@Override
			public void keyTyped(TextField textField, char c) {
				secretAnswer = textField.getText();
			}
		});
		back.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(Tutor.LOGIN);
			}
		});
		createAccount.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(username!=null && password!=null && secretQuestion!=null && secretAnswer!=null && fullName!=null) {
					if (!username.contains(",") && !password.contains(",") && !secretQuestion.contains(",") && !secretAnswer.contains(",") && !fullName.contains(",") && !username.contains(".") && !password.contains(".") && !secretQuestion.contains(".") && !secretAnswer.contains(".") && !fullName.contains(".")) {
						if(username.length()>4 && password.length()>4 && secretAnswer.length()>0 && fullName.length()>0 && !parent.checkDuplicateUsername(username)) {
							//only creates accounts with at least 5 character username and password and at least something for secret answer and full name. also no fields can contain "," or "."
							parent.addAccountSecure(username, password, secretQuestion, secretAnswer, fullName);
							parent.changeScreen(Tutor.LOGIN);
						}
					}
				}
			}
		});
		//default value
		secretQuestion = secretQuestionBox.getSelected();
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
