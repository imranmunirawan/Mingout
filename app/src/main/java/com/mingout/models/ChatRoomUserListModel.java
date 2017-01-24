package com.mingout.models;

import java.io.Serializable;

public class ChatRoomUserListModel implements Serializable {

	String name, email, profileId, userId, type, gender, age, myPhrase,
			original_picture, thumb_picture, lookingFor, userName, newMessages,
			unreadMessageCounter;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getMyPhrase() {
		return myPhrase;
	}

	public void setMyPhrase(String myPhrase) {
		this.myPhrase = myPhrase;
	}

	public String getOriginal_picture() {
		return original_picture;
	}

	public void setOriginal_picture(String original_picture) {
		this.original_picture = original_picture;
	}

	public String getThumb_picture() {
		return thumb_picture;
	}

	public void setThumb_picture(String thumb_picture) {
		this.thumb_picture = thumb_picture;
	}

	public String getLookingFor() {
		return lookingFor;
	}

	public void setLookingFor(String lookingFor) {
		this.lookingFor = lookingFor;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNewMessages() {
		return newMessages;
	}

	public void setNewMessages(String newMessages) {
		this.newMessages = newMessages;
	}

	public String getUnreadMessageCounter() {
		return unreadMessageCounter;
	}

	public void setUnreadMessageCounter(String unreadMessageCounter) {
		this.unreadMessageCounter = unreadMessageCounter;
	}

}
