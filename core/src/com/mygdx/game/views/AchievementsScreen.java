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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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
		userInfoMessage = parent.currentUser.getFullName();
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
		Table topBarTable = new Table();
		topBarTable.setFillParent(true);
		stage.addActor(topBarTable);
		stage.addActor(table);
		//creating different buttons/textfields/labels
		Image treasureIcon = new Image(new Texture(Gdx.files.internal("images/Treasure.png")));
		Label userInfo = new Label("", skin);
		userInfo.setText(userInfoMessage);
		userInfo.setAlignment(Align.center);
		Button back = new Button(skin, "Exit");
		TextTooltip exitPopup = new TextTooltip(" Back ", skin);
		exitPopup.setInstant(true);
		//layout:
		topBarTable.top();
		topBarTable.row();
		topBarTable.add(treasureIcon).uniformX().pad(5);
		topBarTable.add().fillX().uniformX().pad(5).width(Gdx.graphics.getWidth()/5);
		topBarTable.add(userInfo).fillX().uniformX().pad(5).width(Gdx.graphics.getWidth()/5);
		topBarTable.add(back).uniformX().pad(5);
		table.row();
		generateAchievementVisual(table, skin, "Kindergarten", parent.currentUser.getkindergartenExam());
		generateAchievementVisual(table, skin, "1st Grade", parent.currentUser.getGrade1Exam());
		generateAchievementVisual(table, skin, "2nd Grade", parent.currentUser.getGrade2Exam());
		generateAchievementVisual(table, skin, "3rd Grade", parent.currentUser.getGrade3Exam());
		generateAchievementVisual(table, skin, "4th Grade", parent.currentUser.getGrade4Exam());
		table.row();
		generateAchievementVisual(table, skin, "Counting", parent.currentUser.getkindergartenCounting());
		generateAchievementVisual(table, skin, "Operations", parent.currentUser.getGrade1Operations());
		generateAchievementVisual(table, skin, "Operations", parent.currentUser.getGrade2Operations());
		generateAchievementVisual(table, skin, "Operations", parent.currentUser.getGrade3Operations());
		generateAchievementVisual(table, skin, "Operations", parent.currentUser.getGrade4Operations());
		table.row();
		generateAchievementVisual(table, skin, "Operations", parent.currentUser.getkindergartenOperations());
		generateAchievementVisual(table, skin, "Numbers", parent.currentUser.getGrade1Numbers());
		generateAchievementVisual(table, skin, "Numbers", parent.currentUser.getGrade2Numbers());
		generateAchievementVisual(table, skin, "Numbers", parent.currentUser.getGrade3Numbers());
		generateAchievementVisual(table, skin, "Numbers", parent.currentUser.getGrade4Numbers());
		table.row();
		generateAchievementVisual(table, skin, "Numbers", parent.currentUser.getkindergartenNumbers());
		generateAchievementVisual(table, skin, "Measurements", parent.currentUser.getGrade1Measurements());
		generateAchievementVisual(table, skin, "Measurements", parent.currentUser.getGrade2Measurements());
		generateAchievementVisual(table, skin, "Fractions", parent.currentUser.getGrade3Fractions());
		generateAchievementVisual(table, skin, "Fractions", parent.currentUser.getGrade4Fractions());
		table.row();
		generateAchievementVisual(table, skin, "Measurements", parent.currentUser.getkindergartenMeasurements());
		table.add().uniformX().pad(5);
		table.add().uniformX().pad(5).width(Gdx.graphics.getWidth()/11);
		table.add().uniformX().pad(5);
		table.add().uniformX().pad(5).width(Gdx.graphics.getWidth()/11);
		generateAchievementVisual(table, skin, "Measurements", parent.currentUser.getGrade3Measurements());
		generateAchievementVisual(table, skin, "Measurements", parent.currentUser.getGrade4Measurements());
		//adding button functionality
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
	
	private void generateAchievementVisual(Table table, Skin skin, String name, int a) {
		//adds an achievement picture + label to the table
		switch(a) {
		case 0:
			table.add(new Image(new Texture(Gdx.files.internal("images/NoMedal.png")))).uniformX().pad(5).align(Align.right);
			break;
		case 1:
			table.add(new Image(new Texture(Gdx.files.internal("images/Bronze.png")))).uniformX().pad(5).align(Align.right);
			break;
		case 2:
			table.add(new Image(new Texture(Gdx.files.internal("images/Silver.png")))).uniformX().pad(5).align(Align.right);
			break;
		case 3:
			table.add(new Image(new Texture(Gdx.files.internal("images/Gold.png")))).uniformX().pad(5).align(Align.right);
			break;
		}
		table.add(new Label(name, skin, "noBackground")).fillX().uniformX().pad(5).width(Gdx.graphics.getWidth()/11).align(Align.left);
	}

}
