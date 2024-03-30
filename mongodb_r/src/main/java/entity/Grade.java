package entity;

import java.util.Date;

public class Grade {
	private String date;
	private String grade;
	private int score;
	@Override
	public String toString() {
		return "Grade [date=" + date + ", grade=" + grade + ", score=" + score + "]";
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public Grade(String date, String grade, int score) {
		super();
		this.date = date;
		this.grade = grade;
		this.score = score;
	}
	public Grade() {
		super();
		// TODO Auto-generated constructor stub
	}
}
