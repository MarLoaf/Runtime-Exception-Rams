package com.mygdx.game;

public class Account {
	
	private String username;
	private String password;
	private String secretQuestion;
	private String secretAnswer;
	private String fullName;
	
	//achievements, 0 - nothing, 1 - bronze, 2 - silver, 3 - gold
	private int kindergartenCounting;
	private int kindergartenOperations;
	private int kindergartenNumbers;
	private int kindergartenMeasurements;

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
	
	private String[] latestAchievements; // none - nothing, 1 - bronze, 2 - silver, 3 - gold, 6 fields: grade1, topic1, grade2, topic2, grade3, topic3
	
	public Account(String u, String p, String sq, String sa, String fn, int kc, int ko, int kn, int km, int o1, int n1, int m1, int o2, int n2, int m2, int o3, int n3, int f3, int m3, int o4, int n4, int f4, int m4, String[] la) {
		username = u;
		password = p;
		secretQuestion = sq;
		secretAnswer = sa;
		fullName = fn;
		
		kindergartenCounting = kc;
		kindergartenOperations = ko;
		kindergartenNumbers = kn;
		kindergartenMeasurements = km;

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
		
		kindergartenCounting = 0;
		kindergartenOperations = 0;
		kindergartenNumbers = 0;
		kindergartenMeasurements = 0;

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
		
		kindergartenCounting = 0;
		kindergartenOperations = 0;
		kindergartenNumbers = 0;
		kindergartenMeasurements = 0;

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
	
	public int getAchievement(String grade, String topic) {
		switch(grade) {
		case "Kindergarten":
			switch(topic) {
			case "Counting":
				return kindergartenCounting;
			case "Operations":
				return kindergartenOperations;
			case "Numbers":
				return kindergartenNumbers;
			case "Measurements":
				return kindergartenMeasurements;
			}
			break;
		case "1st Grade":
			switch(topic) {
			case "Operations":
				return grade1Operations;
			case "Numbers":
				return grade1Numbers;
			case "Measurements":
				return grade1Measurements;
			}
			break;
		case "2nd Grade":
			switch(topic) {
			case "Operations":
				return grade2Operations;
			case "Numbers":
				return grade2Numbers;
			case "Measurements":
				return grade2Measurements;
			}
			break;
		case "3rd Grade":
			switch(topic) {
			case "Operations":
				return grade3Operations;
			case "Numbers":
				return grade3Numbers;
			case "Fractions":
				return grade3Fractions;
			case "Measurements":
				return grade3Measurements;
			}
			break;
		case "4th Grade":
			switch(topic) {
			case "Operations":
				return grade4Operations;
			case "Numbers":
				return grade4Numbers;
			case "Fractions":
				return grade4Fractions;
			case "Measurements":
				return grade4Measurements;
			}
			break;
		}
		return -1;
	}
	
	public void gainAchievement(String grade, String topic, int a) {
		switch(grade) {
		case "Kindergarten":
			switch(topic) {
			case "Counting":
				if (kindergartenCounting < a) kindergartenCounting = a;
				break;
			case "Operations":
				if (kindergartenOperations < a) kindergartenOperations = a;
				break;
			case "Numbers":
				if (kindergartenNumbers < a) kindergartenNumbers = a;
				break;
			case "Measurements":
				if (kindergartenMeasurements < a) kindergartenMeasurements = a;
				break;
			}
			break;
		case "1st Grade":
			switch(topic) {
			case "Operations":
				if (grade1Operations < a) grade1Operations = a;
				break;
			case "Numbers":
				if (grade1Numbers < a) grade1Numbers = a;
				break;
			case "Measurements":
				if (grade1Measurements < a) grade1Measurements = a;
				break;
			}
			break;
		case "2nd Grade":
			switch(topic) {
			case "Operations":
				if (grade2Operations < a) grade2Operations = a;
				break;
			case "Numbers":
				if (grade2Numbers < a) grade2Numbers = a;
				break;
			case "Measurements":
				if (grade2Measurements < a) grade2Measurements = a;
				break;
			}
			break;
		case "3rd Grade":
			switch(topic) {
			case "Operations":
				if (grade3Operations < a) grade3Operations = a;
				break;
			case "Numbers":
				if (grade3Numbers < a) grade3Numbers = a;
				break;
			case "Fractions":
				if (grade3Fractions < a) grade3Fractions = a;
				break;
			case "Measurements":
				if (grade3Measurements < a) grade3Measurements = a;
				break;
			}
			break;
		case "4th Grade":
			switch(topic) {
			case "Operations":
				if (grade4Operations < a) grade4Operations = a;
				break;
			case "Numbers":
				if (grade4Numbers < a) grade4Numbers = a;
				break;
			case "Fractions":
				if (grade4Fractions < a) grade4Fractions = a;
				break;
			case "Measurements":
				if (grade4Measurements < a) grade4Measurements = a;
				break;
			}
			break;
		}
		updateLatestAchievements(grade, topic);
	}
	
	private void updateLatestAchievements(String grade, String topic) {
		// none - nothing, 1 - bronze, 2 - silver, 3 - gold, 6 fields: grade1, topic1, grade2, topic2, grade3, topic3
		if (latestAchievements[0].equals("none")) {
			latestAchievements[0] = grade;
			latestAchievements[1] = topic;
		}
		else {
			latestAchievements[5] = latestAchievements[3];
			latestAchievements[4] = latestAchievements[2];
			latestAchievements[3] = latestAchievements[1];
			latestAchievements[2] = latestAchievements[0];
			latestAchievements[1] = topic;
			latestAchievements[0] = grade;
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
	public int getkindergartenCounting() {
		return kindergartenCounting;
	}
	
	public int getkindergartenOperations() {
		return kindergartenOperations;
	}
	
	public int getkindergartenNumbers() {
		return kindergartenNumbers;
	}
	
	public int getkindergartenMeasurements() {
		return kindergartenMeasurements;
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
	public void setkindergartenCounting(int a) {
		kindergartenCounting = a;
	}
	
	public void setkindergartenOperations(int a) {
		kindergartenOperations = a;
	}
	
	public void setkindergartenNumbers(int a) {
		kindergartenNumbers = a;
	}
	
	public void setkindergartenMeasurements(int a) {
		kindergartenMeasurements = a;
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
