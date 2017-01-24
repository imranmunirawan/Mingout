package com.mingout.models;

import java.io.Serializable;
import java.util.ArrayList;

public class ReviewSocialDataModel implements Serializable {

	String name, myPhrase, gender, status, age, height, lookingFor, aboutMe,
			wantToMeet, dob, imageUrl, type;

	ArrayList<String> imagesUrls;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getLookingFor() {
		return lookingFor;
	}

	public void setLookingFor(String lookingFor) {
		this.lookingFor = lookingFor;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public String getWantToMeet() {
		return wantToMeet;
	}

	public void setWantToMeet(String wantToMeet) {
		this.wantToMeet = wantToMeet;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArrayList<String> getImagesUrls() {
		return imagesUrls;
	}

	public void setImagesUrls(ArrayList<String> imagesUrls) {
		this.imagesUrls = imagesUrls;
	}

}
