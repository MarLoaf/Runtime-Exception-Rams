package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Problem {

	private String problemText;
	private String correctAnswer;
	private String wrongAnswers[];
	private String selectedAnswer;
	private Image countingImage;
	
	public Problem(String pt, String ca, String wa[], String sa, Image ci) {
		problemText = pt;
		correctAnswer = ca;
		wrongAnswers = wa;
		selectedAnswer = sa;
		countingImage = ci;
	}
	
	public Problem(String pt, String ca, String wa[], String sa) {
		problemText = pt;
		correctAnswer = ca;
		wrongAnswers = wa;
		selectedAnswer = sa;
		countingImage = null;
	}
	
	public Problem(String pt, String ca, String wa[]) {
		problemText = pt;
		correctAnswer = ca;
		wrongAnswers = wa;
		selectedAnswer = "";
		countingImage = null;
	}
	
	public Problem(String pt, String ca) {
		problemText = pt;
		correctAnswer = ca;
		wrongAnswers = null;
		selectedAnswer = "";
		countingImage = null;
	}
	
	public Problem() {
		problemText = "";
		correctAnswer = "";
		wrongAnswers = null;
		selectedAnswer = "";
		countingImage = null;
	}
	
	public boolean checkAnswer() {
		if (selectedAnswer.equals(correctAnswer)) return true;
		return false;
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
}
