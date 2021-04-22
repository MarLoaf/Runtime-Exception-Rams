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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Tutor;

public class AchievementsScreen implements Screen {

	private Tutor parent;
	private Stage stage;
	private SpriteBatch batch;
	private Texture backgroundTexture;
	private String userInfoMessage;
	
	public AchievementsScreen(Tutor tutor) {
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
		//creating different buttons/textfields/labels
		Label userInfo = new Label("", skin);
		userInfo.setText(userInfoMessage);
		userInfo.setAlignment(Align.center);
		Image Gold1 = new Image(new Texture(Gdx.files.internal("images/Gold.png")));
		Image Silver1 = new Image(new Texture(Gdx.files.internal("images/Silver.png")));
		Image Bronze1 = new Image(new Texture(Gdx.files.internal("images/Bronze.png")));
		Image Gold2 = new Image(new Texture(Gdx.files.internal("images/Gold.png")));
		Image Silver2 = new Image(new Texture(Gdx.files.internal("images/Silver.png")));
		Image Bronze2 = new Image(new Texture(Gdx.files.internal("images/Bronze.png")));
		Image Bronze3 = new Image(new Texture(Gdx.files.internal("images/Bronze.png")));
		Image Bronze4 = new Image(new Texture(Gdx.files.internal("images/Bronze.png")));
		Label List1 = new Label("Kindergarten", skin, "noBackground");
		List1.setAlignment(Align.center);
		Label List2 = new Label("1st Grade", skin, "noBackground");
		List2.setAlignment(Align.center);
		Label List3 = new Label("2nd Grade", skin, "noBackground");
		List3.setAlignment(Align.center);
		Label List4 = new Label("3th Grade", skin, "noBackground");
		List4.setAlignment(Align.center);
		Label List5 = new Label("4th Grade", skin, "noBackground");
		List5.setAlignment(Align.center);
		Label progress1 = new Label("Addition 100%", skin, "noBackground");
		Label progress2 = new Label("Multiplication 80%", skin, "noBackground");
		Label progress3 = new Label("Fractions 65%", skin, "noBackground");
		Label progress4 = new Label("Division 65%", skin, "noBackground");
		Label collected1 = new Label("Addition", skin, "noBackground");
		Label collected2 = new Label("Multiplication", skin, "noBackground");
		Label collected3 = new Label("Fractions", skin, "noBackground");
		Label collected4 = new Label("Division", skin, "noBackground");
		Button back = new Button(skin, "Exit");
		TextTooltip exitPopup = new TextTooltip("Back", skin);
		exitPopup.setInstant(true);
		TextTooltip GoldPopup = new TextTooltip("Top achievement", skin);
		GoldPopup.setInstant(true);
		TextTooltip SilverPopup = new TextTooltip("Medium achievement", skin);
		SilverPopup.setInstant(true);
		TextTooltip BronzePopup = new TextTooltip("Lowest achievement", skin);
		BronzePopup.setInstant(true);
		// TODO add description for the achievements via pop-ups
		//layout:
		table.top();
		table.row();
		table.add().fillX().uniformX().pad(5).padBottom(100).width(Gdx.graphics.getWidth()/5);
		table.add().fillX().uniformX().pad(5).padBottom(100).width(Gdx.graphics.getWidth()/5);
		table.add(userInfo).fillX().uniformX().pad(5).padBottom(100).width(Gdx.graphics.getWidth()/5);
		table.add(back).uniformX().pad(5).padBottom(100);
		table.row();
		table.add(List1).colspan(2).pad(5).width(Gdx.graphics.getWidth()/6);
		table.add(List2).colspan(2).pad(5).width(Gdx.graphics.getWidth()/6);
		table.add(List3).colspan(2).pad(5).width(Gdx.graphics.getWidth()/6);
		table.add(List4).colspan(2).pad(5).width(Gdx.graphics.getWidth()/6);
		table.add(List5).colspan(2).pad(5).width(Gdx.graphics.getWidth()/10);
		table.row();
		generateAchievementVisual(table, skin, "Operations K", "Addition", 3);
		generateAchievementVisual(table, skin, "Operations 1st", "Addition", 3);
		generateAchievementVisual(table, skin, "Operations 2nd", "Addition", 3);
		generateAchievementVisual(table, skin, "Operations 3rd", "Addition", 3);
		generateAchievementVisual(table, skin, "Operations 4th", "Addition", 3);
		table.row();
		table.add(Silver1).uniformX().pad(5).align(Align.right);
		table.add(collected2).pad(5).width(Gdx.graphics.getWidth()/5).align(Align.left);
		table.add(Silver2).uniformX().pad(5).align(Align.right);
		table.add(progress2).pad(5).width(Gdx.graphics.getWidth()/5).align(Align.left);
		table.row();
		table.add(Bronze1).uniformX().pad(5).align(Align.right);
		table.add(collected3).pad(5).width(Gdx.graphics.getWidth()/5).align(Align.left);
		table.add(Bronze2).uniformX().pad(5).align(Align.right);
		table.add(progress3).pad(5).width(Gdx.graphics.getWidth()/5).align(Align.left);
		table.row();
		generateAchievementVisual(table, skin, "AdditionFun 100%", "", 3);
		generateAchievementVisual(table, skin, "Multiplication 80%", "", 2);
		table.row();
		generateAchievementVisual(table, skin, "Fractions 65%", "", 1);
		generateAchievementVisual(table, skin, "Division 65%", "", 1);
		//adding button functionality
		Gold1.addListener(GoldPopup);
		Silver1.addListener(SilverPopup);
		Bronze1.addListener(BronzePopup);
		Gold2.addListener(GoldPopup);
		Silver2.addListener(SilverPopup);
		Bronze2.addListener(BronzePopup);
		Bronze3.addListener(BronzePopup);
		Bronze4.addListener(BronzePopup);
		back.addListener(exitPopup);
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
		if (grade.equals("")) table.add(new Label(topic, skin, "noBackground")).pad(5).width(Gdx.graphics.getWidth()/10).align(Align.left);
		else table.add(new Label(grade + " " + topic, skin, "noBackground")).pad(5).width(Gdx.graphics.getWidth()/10).align(Align.left);
	}

}
