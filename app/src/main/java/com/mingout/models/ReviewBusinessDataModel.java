package com.mingout.models;

import java.io.Serializable;
import java.util.ArrayList;

public class ReviewBusinessDataModel implements Serializable {

	String name, myPhrase, gender, age, jobTitle, company, biography, dob,
			imageUrl;
	ArrayList<String> imagesUrl;

	public String getMyPhrase() {
		return myPhrase;
	}

	public void setMyPhrase(String myPhrase) {
		this.myPhrase = myPhrase;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public ArrayList<String> getImagesUrl() {
		return imagesUrl;
	}

	public void setImagesUrl(ArrayList<String> imagesUrl) {
		this.imagesUrl = imagesUrl;
	}

}
