package com.mygdx.game;

public class Account {
	
	private String username;
	private String password;
	private String secretQuestion;
	private String secretAnswer;
	private String fullName;
	
	//achievements, 0 - nothing, 1 - bronze, 2 - silver, 3 - gold
	private int kindergardenCounting;
	private int kindergardenOperations;
	private int kindergardenNumbers;
	private int kindergardenMeasurements;

	private int grade1Operations;
	private int grade1Numbers;
	private int grade1Measurements;

	private int grade2Operations;
	private int grade2Numbers;
	private int grade2Measurements;

	private int grade3Operations;
	private int grade3Numbers;
	private int grade3Fractions;
	private int grade3Measurements;

	private int grade4Operations;
	private int grade4Numbers;
	private int grade4Fractions;
	private int grade4Measurements;
	
	private String[] latestAchievements;
	
	public Account(String u, String p, String sq, String sa, String fn, int kc, int ko, int kn, int km, int o1, int n1, int m1, int o2, int n2, int m2, int o3, int n3, int f3, int m3, int o4, int n4, int f4, int m4, String[] la) {
		username = u;
		password = p;
		secretQuestion = sq;
		secretAnswer = sa;
		fullName = fn;
		
		kindergardenCounting = kc;
		kindergardenOperations = ko;
		kindergardenNumbers = kn;
		kindergardenMeasurements = km;

		grade1Operations = o1;
		grade1Numbers = n1;
		grade1Measurements = m1;

		grade2Operations = o2;
		grade2Numbers = n2;
		grade2Measurements = m2;

		grade3Operations = o3;
		grade3Numbers = n3;
		grade3Fractions = f3;
		grade3Measurements = m3;

		grade4Operations = o4;
		grade4Numbers = n4;
		grade4Fractions = f4;
		grade4Measurements = m4;
		
		latestAchievements = la;
	}
	
	public Account(String u, String p, String sq, String sa, String fn) {
		username = u;
		password = p;
		secretQuestion = sq;
		secretAnswer = sa;
		fullName = fn;
		
		kindergardenCounting = 0;
		kindergardenOperations = 0;
		kindergardenNumbers = 0;
		kindergardenMeasurements = 0;

		grade1Operations = 0;
		grade1Numbers = 0;
		grade1Measurements = 0;

		grade2Operations = 0;
		grade2Numbers = 0;
		grade2Measurements = 0;

		grade3Operations = 0;
		grade3Numbers = 0;
		grade3Fractions = 0;
		grade3Measurements = 0;

		grade4Operations = 0;
		grade4Numbers = 0;
		grade4Fractions = 0;
		grade4Measurements = 0;
		
		latestAchievements = new String[] {"none","none","none"};
	}
	
	public Account() {
		username = "";
		password = "";
		secretQuestion = "";
		secretAnswer = "";
		fullName = "";
		
		kindergardenCounting = 0;
		kindergardenOperations = 0;
		kindergardenNumbers = 0;
		kindergardenMeasurements = 0;

		grade1Operations = 0;
		grade1Numbers = 0;
		grade1Measurements = 0;

		grade2Operations = 0;
		grade2Numbers = 0;
		grade2Measurements = 0;

		grade3Operations = 0;
		grade3Numbers = 0;
		grade3Fractions = 0;
		grade3Measurements = 0;

		grade4Operations = 0;
		grade4Numbers = 0;
		grade4Fractions = 0;
		grade4Measurements = 0;
		
		latestAchievements = new String[] {"none","none","none"};
	}
	
	public void gainAchievement(String gradeAndTopic, int a) {
		switch(gradeAndTopic) {
		case "kindergardenCounting":
			if (kindergardenCounting < a) kindergardenCounting = a;
			break;
		case "kindergardenOperations":
			if (kindergardenOperations < a) kindergardenOperations = a;
			break;
		case "kindergardenNumbers":
			if (kindergardenNumbers < a) kindergardenNumbers = a;
			break;
		case "kindergardenMeasurements":
			if (kindergardenMeasurements < a) kindergardenMeasurements = a;
			break;
		case "grade1Operations":
			if (grade1Operations < a) grade1Operations = a;
			break;
		case "grade1Numbers":
			if (grade1Numbers < a) grade1Numbers = a;
			break;
		case "grade1Measurements":
			if (grade1Measurements < a) grade1Measurements = a;
			break;
		case "grade2Operations":
			if (grade2Operations < a) grade2Operations = a;
			break;
		case "grade2Numbers":
			if (grade2Numbers < a) grade2Numbers = a;
			break;
		case "grade2Measurements":
			if (grade2Measurements < a) grade2Measurements = a;
			break;
		case "grade3Operations":
			if (grade3Operations < a) grade3Operations = a;
			break;
		case "grade3Numbers":
			if (grade3Numbers < a) grade3Numbers = a;
			break;
		case "grade3Fractions":
			if (grade3Fractions < a) grade3Fractions = a;
			break;
		case "grade3Measurements":
			if (grade3Measurements < a) grade3Measurements = a;
			break;
		case "grade4Operations":
			if (grade4Operations < a) grade4Operations = a;
			break;
		case "grade4Numbers":
			if (grade4Numbers < a) grade4Numbers = a;
			break;
		case "grade4Fractions":
			if (grade4Fractions < a) grade4Fractions = a;
			break;
		case "grade4Measurements":
			if (grade4Measurements < a) grade4Measurements = a;
			break;
		}
		updateLatestAchievements(gradeAndTopic);
	}
	
	public void updateLatestAchievements(String gradeAndTopic) {
		if (latestAchievements[0].equals("none")) latestAchievements[0] = gradeAndTopic;
		else {
			latestAchievements[2] = latestAchievements[1];
			latestAchievements[1] = latestAchievements[0];
			latestAchievements[0] = gradeAndTopic;
		}
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getSecretQuestion() {
		return secretQuestion;
	}
	
	public String getSecretAnswer() {
		return secretAnswer;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public void setUsername(String u) {
		username = u;
	}
	
	public void setPassword(String p) {
		password = p;
	}
	
	public void setSecretQuestion(String sq) {
		secretQuestion = sq;
	}
	
	public void setSecretAnswer(String sa) {
		secretAnswer = sa;
	}
	
	public void setFullName(String fn) {
		fullName = fn;
	}
	
	//getters for achievements
	public int getKindergardenCounting() {
		return kindergardenCounting;
	}
	
	public int getKindergardenOperations() {
		return kindergardenOperations;
	}
	
	public int getKindergardenNumbers() {
		return kindergardenNumbers;
	}
	
	public int getKindergardenMeasurements() {
		return kindergardenMeasurements;
	}
	
	public int getGrade1Operations() {
		return grade1Operations;
	}
	
	public int getGrade1Numbers() {
		return grade1Numbers;
	}
	
	public int getGrade1Measurements() {
		return grade1Measurements;
	}
	
	public int getGrade2Operations() {
		return grade2Operations;
	}
	
	public int getGrade2Numbers() {
		return grade2Numbers;
	}
	
	public int getGrade2Measurements() {
		return grade2Measurements;
	}
	
	public int getGrade3Operations() {
		return grade3Operations;
	}
	
	public int getGrade3Numbers() {
		return grade3Numbers;
	}
	
	public int getGrade3Fractions() {
		return grade3Fractions;
	}
	
	public int getGrade3Measurements() {
		return grade3Measurements;
	}
	
	public int getGrade4Operations() {
		return grade4Operations;
	}
	
	public int getGrade4Numbers() {
		return grade4Numbers;
	}
	
	public int getGrade4Fractions() {
		return grade4Fractions;
	}
	
	public int getGrade4Measurements() {
		return grade4Measurements;
	}
	
	//setters for achievements
	public void setKindergardenCounting(int a) {
		kindergardenCounting = a;
	}
	
	public void setKindergardenOperations(int a) {
		kindergardenOperations = a;
	}
	
	public void setKindergardenNumbers(int a) {
		kindergardenNumbers = a;
	}
	
	public void setKindergardenMeasurements(int a) {
		kindergardenMeasurements = a;
	}
	
	public void setGrade1Operations(int a) {
		grade1Operations = a;
	}
	
	public void setGrade1Numbers(int a) {
		grade1Numbers = a;
	}
	
	public void setGrade1Measurements(int a) {
		grade1Measurements = a;
	}
	
	public void setGrade2Operations(int a) {
		grade2Operations = a;
	}
	
	public void setGrade2Numbers(int a) {
		grade2Numbers = a;
	}
	
	public void setGrade2Measurements(int a) {
		grade2Measurements = a;
	}
	
	public void setGrade3Operations(int a) {
		grade3Operations = a;
	}
	
	public void setGrade3Numbers(int a) {
		grade3Numbers = a;
	}
	
	public void setGrade3Fractions(int a) {
		grade3Fractions = a;
	}
	
	public void setGrade3Measurements(int a) {
		grade3Measurements = a;
	}
	
	public void setGrade4Operations(int a) {
		grade4Operations = a;
	}
	
	public void setGrade4Numbers(int a) {
		grade4Numbers = a;
	}
	
	public void setGrade4Fractions(int a) {
		grade4Fractions = a;
	}
	
	public void setGrade4Measurements(int a) {
		grade4Measurements = a;
	}
	
	public String[] getLatestAchievements() {
		return latestAchievements;
	}
	
	public void setLatestAchievements(String[] la) {
		latestAchievements = la;
	}
}
