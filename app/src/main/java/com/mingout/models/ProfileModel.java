package com.mingout.models;

public class ProfileModel {
	ReviewSocialDataModel socialModel;
	ReviewBusinessDataModel businessModel;

	public ReviewSocialDataModel getSocialModel() {
		return socialModel;
	}

	public void setSocialModel(ReviewSocialDataModel socialModel) {
		this.socialModel = socialModel;
	}

	public ReviewBusinessDataModel getBusinessModel() {
		return businessModel;
	}

	public void setBusinessModel(ReviewBusinessDataModel businessModel) {
		this.businessModel = businessModel;
	}

}
