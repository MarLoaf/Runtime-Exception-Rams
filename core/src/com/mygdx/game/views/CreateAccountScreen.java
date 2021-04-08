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
		secretQuestionBox.setItems("Secret Question","What's your favorite color?", "What's the name of your first pet?", "What's your mother's maiden name?");
		secretQuestionBox.setAlignment(Align.center);
		Label createAccountLabel = new Label("Create Account", skin);
		createAccountLabel.setAlignment(Align.center);
		Label username = new Label("Username:", skin);
	    Label password = new Label("Password:", skin);
	    Label fullname = new Label("Full Name:", skin);
	    TextField usernameText = new TextField("", skin);
	    usernameText.setMessageText("Enter username...");
	    TextField passwordText = new TextField("", skin);
	    passwordText.setMessageText("Enter password...");
	    TextField fullnameText = new TextField("", skin);
	    fullnameText.setMessageText("Enter your full name...");
	    TextField secretAnswerText = new TextField("", skin);
	    secretAnswerText.setMessageText("Enter answer...");
		Button back = new Button(skin, "Exit");
		ImageTextButton createAccount = new ImageTextButton("Create Account", skin,"green");
		username.setAlignment(Align.center);
		usernameText.setAlignment(Align.center);
		password.setAlignment(Align.center);
		passwordText.setAlignment(Align.center);
		fullname.setAlignment(Align.center);
		fullnameText.setAlignment(Align.center);
		secretAnswerText.setAlignment(Align.center);
		//layout:
		table.top();
		table.row();
		table.add().fillX().uniformX().pad(5).padBottom(100).width(Gdx.graphics.getWidth()/5);
		table.add(createAccountLabel).colspan(2).fillX().uniformX().pad(5).padBottom(100).width(Gdx.graphics.getWidth()/5);
		table.add(back).uniformX().pad(5).padBottom(100);
		table.row();
		table.add();
		table.add(username).fillX().uniformX().pad(5).width(Gdx.graphics.getWidth()/5);
		table.add(usernameText).fillX().uniformX().pad(5).width(Gdx.graphics.getWidth()/5);
		table.row();
		table.add();
		table.add(password).fillX().uniformX().pad(5).width(Gdx.graphics.getWidth()/5);
		table.add(passwordText).fillX().uniformX().pad(5).width(Gdx.graphics.getWidth()/5);
		table.row();
		table.add();
		table.add(fullname).fillX().uniformX().pad(5).width(Gdx.graphics.getWidth()/5);
		table.add(fullnameText).fillX().uniformX().pad(5).width(Gdx.graphics.getWidth()/5);
		table.row();
		table.add();
		table.add(secretQuestionBox).fillX().uniformX().pad(5).width(Gdx.graphics.getWidth()/5);
		table.add(secretAnswerText).fillX().uniformX().pad(5).width(Gdx.graphics.getWidth()/5);
		table.row();
		table.add();
		table.add(createAccount).pad(5).colspan(2).width(Gdx.graphics.getWidth()/5);
		//adding button functionality
		back.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(Tutor.LOGIN);
			}
		});
		createAccount.addListener(new ChangeListener() {
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
