package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Problem {

	private String problemText;
	private String correctAnswer;
	private String wrongAnswers[];
	private String selectedAnswer;
	private Image countingImage;
	private String countingObject;
	private Image comparisonImage1;
	private Image comparisonImage2;
	
	public Problem(String pt, String ca, String wa[], Image ci1, Image ci2) {
		//measurement or image fraction problem
		problemText = pt;
		correctAnswer = ca;
		wrongAnswers = wa;
		selectedAnswer = "";
		countingImage = null;
		countingObject = null;
		comparisonImage1 = ci1;
		comparisonImage2 = ci2;
	}
	
	public Problem(String pt, String ca, String wa[], Image ci, String co) {
		//multiple choice counting problem
		problemText = pt;
		correctAnswer = ca;
		wrongAnswers = wa;
		selectedAnswer = "";
		countingImage = ci;
		countingObject = co;
		comparisonImage1 = null;
		comparisonImage2 = null;
	}
	
	public Problem(String pt, String ca, Image ci, String co) {
		//counting problem
		problemText = pt;
		correctAnswer = ca;
		wrongAnswers = null;
		selectedAnswer = "";
		countingImage = ci;
		countingObject = co;
		comparisonImage1 = null;
		comparisonImage2 = null;
	}
	
	public Problem(String pt, String ca, String wa[]) {
		//multiple choice problem
		problemText = pt;
		correctAnswer = ca;
		wrongAnswers = wa;
		selectedAnswer = "";
		countingImage = null;
		countingObject = null;
		comparisonImage1 = null;
		comparisonImage2 = null;
	}
	
	public Problem(String pt, String ca) {
		//problem
		problemText = pt;
		correctAnswer = ca;
		wrongAnswers = null;
		selectedAnswer = "";
		countingImage = null;
		countingObject = null;
		comparisonImage1 = null;
		comparisonImage2 = null;
	}
	
	public Problem() {
		//generic constructor with no fields
		problemText = "";
		correctAnswer = "";
		wrongAnswers = null;
		selectedAnswer = "";
		countingImage = null;
		countingObject = null;
		comparisonImage1 = null;
		comparisonImage2 = null;
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
	
	public String getCountingObject() {
		return countingObject;
	}
	
	public Image getComparisonImage1() {
		return comparisonImage1;
	}
	
	public Image getComparisonImage2() {
		return comparisonImage2;
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
	
	public void setComparisonImage1(Image ci1) {
		comparisonImage1 = ci1;
	}
	
	public void setComparisonImage2(Image ci2) {
		comparisonImage2 = ci2;
	}
}
