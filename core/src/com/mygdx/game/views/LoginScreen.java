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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Account;
import com.mygdx.game.Tutor;

public class LoginScreen implements Screen {

	private Tutor parent;
	private Stage stage;
	private SpriteBatch batch;
	private Texture backgroundTexture;
	private String username;
	private String password;
	
	public LoginScreen(Tutor tutor) {
		parent = tutor;
		batch = new SpriteBatch();
		backgroundTexture = new Texture(Gdx.files.internal("images/background.png"));
		stage = new Stage(new ScreenViewport());
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
		stage.draw();
		parent.currentUser = new Account("","","question","answer","Test User"); // TODO this is only for testing, remove when testing is done
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
		Image ramLogo = new Image(new Texture(Gdx.files.internal("images/Ram.png")));
        Label usernameLabel = new Label("Username:", skin);
        Label passwordLabel = new Label("Password:", skin);
		TextField usernameText = new TextField("", skin);
		usernameText.setMessageText("Enter username...");
		TextField passwordText = new TextField("", skin);
		passwordText.setMessageText("Enter password...");
		passwordText.setPasswordCharacter('*');
		passwordText.setPasswordMode(true);
		ImageTextButton login = new ImageTextButton("Log in", skin, "green");
		ImageTextButton forgotPassword = new ImageTextButton("Forgot Password", skin);
		ImageTextButton createAccount = new ImageTextButton("Create Account", skin);
		ImageTextButton exit = new ImageTextButton("Exit", skin, "pink");
		usernameLabel.setAlignment(Align.center);
		usernameText.setAlignment(Align.center);
		passwordLabel.setAlignment(Align.center);
		passwordText.setAlignment(Align.center);
		Label iconAttribution = new Label("Picture icons made by Freepik from www.flaticon.com", skin, "noBackground");
		//layout:
		table.top();
		table.row();
		table.add().fillX().uniformX().pad(5).padBottom(213).width(Gdx.graphics.getWidth()/5);
		table.add(ramLogo).colspan(2).uniformX().pad(5);
		table.add().fillX().uniformX().pad(5).padBottom(213).width(Gdx.graphics.getWidth()/5);
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
		table.add(login).uniformX().pad(5).colspan(2).width(Gdx.graphics.getWidth()/5);
		table.row();
		table.add();
		table.add(forgotPassword).fillX().uniformX().pad(5).width(Gdx.graphics.getWidth()/5);
		table.add(createAccount).fillX().uniformX().pad(5).width(Gdx.graphics.getWidth()/5);
		table.row();
		table.add();
		table.add(exit).uniformX().pad(5).colspan(2).width(Gdx.graphics.getWidth()/5);
		table.row();
		table.add();
		table.add(iconAttribution).uniformX().pad(5).colspan(2);
		//adding button functionality
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
		login.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (parent.loginAccount(username, password)) {
					parent.changeScreen(Tutor.HOME);
				}
				parent.changeScreen(Tutor.HOME); // TODO remove to get username and password check
			}
		});
		forgotPassword.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(Tutor.RECOVERACC);
			}
		});
		createAccount.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(Tutor.CREATEACCOUNT);
			}
		});
		exit.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();
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
