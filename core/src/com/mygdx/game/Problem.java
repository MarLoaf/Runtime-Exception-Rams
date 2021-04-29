package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Problem {

	private String problemText;
	private String correctAnswer;
	private String wrongAnswers[];
	private String selectedAnswer;
	private Image countingImage;
	private String countingObject;
	
	public Problem(String pt, String ca, String wa[], String sa, Image ci, String co) {
		problemText = pt;
		correctAnswer = ca;
		wrongAnswers = wa;
		selectedAnswer = sa;
		countingImage = ci;
		countingObject = co;
	}
	
	public Problem(String pt, String ca, String wa[], Image ci, String co) {
		problemText = pt;
		correctAnswer = ca;
		wrongAnswers = wa;
		selectedAnswer = "";
		countingImage = ci;
		countingObject = co;
	}
	
	public Problem(String pt, String ca, String wa[], String sa) {
		problemText = pt;
		correctAnswer = ca;
		wrongAnswers = wa;
		selectedAnswer = sa;
		countingImage = null;
		countingObject = null;
	}
	
	public Problem(String pt, String ca, String wa[]) {
		problemText = pt;
		correctAnswer = ca;
		wrongAnswers = wa;
		selectedAnswer = "";
		countingImage = null;
		countingObject = null;
	}
	
	public Problem(String pt, String ca) {
		problemText = pt;
		correctAnswer = ca;
		wrongAnswers = null;
		selectedAnswer = "";
		countingImage = null;
		countingObject = null;
	}
	
	public Problem(String pt, String ca, Image ci, String co) {
		problemText = pt;
		correctAnswer = ca;
		wrongAnswers = null;
		selectedAnswer = "";
		countingImage = ci;
		countingObject = co;
	}
	
	public Problem() {
		problemText = "";
		correctAnswer = "";
		wrongAnswers = null;
		selectedAnswer = "";
		countingImage = null;
		countingObject = null;
	}
	
	public boolean checkAnswer() {
		if (selectedAnswer.equals(correctAnswer)) return true;
		return false;
	}
	
	public void addCountingImage(String object, int number) {
		object = "apple";//TODO remove after adding more images
		switch (object) {
		case "apple":
			switch (number) {
			case 1:
				setCountingImage(new Image(new Texture(Gdx.files.internal("images/1Apple.png"))));
				break;
			case 2:
				setCountingImage(new Image(new Texture(Gdx.files.internal("images/2Apple.png"))));
				break;
			case 3:
				setCountingImage(new Image(new Texture(Gdx.files.internal("images/3Apple.png"))));
				break;
			case 4:
				setCountingImage(new Image(new Texture(Gdx.files.internal("images/4Apple.png"))));
				break;
			case 5:
				setCountingImage(new Image(new Texture(Gdx.files.internal("images/5Apple.png"))));
				break;
			case 6:
				setCountingImage(new Image(new Texture(Gdx.files.internal("images/6Apple.png"))));
				break;
			case 7:
				setCountingImage(new Image(new Texture(Gdx.files.internal("images/7Apple.png"))));
				break;
			case 8:
				setCountingImage(new Image(new Texture(Gdx.files.internal("images/8Apple.png"))));
				break;
			case 9:
				setCountingImage(new Image(new Texture(Gdx.files.internal("images/9Apple.png"))));
				break;
			}
			break;
		case "orange":
			break;
		case "lemon":
			break;
		}
	}
	
	public String getProblemText() {
		return problemText;
	}
	
	public String getCorrectAnswer() {
		return correctAnswer;
	}
	
	public String[] getWrongAnswers() {
		return wrongAnswers;
	}
	
	public String getSelectedAnswer() {
		return selectedAnswer;
	}
	
	public Image getCountingImage() {
		return countingImage;
	}
	
	public String getCountingObject() {
		return countingObject;
	}
	
	public void setProblemText(String pt) {
		problemText = pt;;
	}
	
	public void setCorrectAnswer(String ca) {
		correctAnswer = ca;
	}
	
	public void setCorrectAnswers(String[] wa) {
		wrongAnswers = wa;
	}
	
	public void setSelectedAnswer(String sa) {
		selectedAnswer = sa;
	}
	
	public void setCountingImage(Image ci) {
		countingImage = ci;
	}
	
	public void setCountingObject(String co) {
		countingObject = co;
	}
}
