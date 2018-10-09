package com.example.luhongcheng.SQ;

public class SSS {

	private String iconUrl;//头像链接
	private String imageUrl;//图像链接
	private String title;//内容
	private String qm;//个性签名
	private String nickname;//昵称
	private String personID;//说说ID
	private String ssID;//人ID
	private String label;//标签

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	private String time;




	private String zan;
	private String pinglun;



	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}


	public String getPersonID() {
		return personID;
	}

	public void setPersonID(String personID) {
		this.personID = personID;
	}


	public String getSsID() {
		return ssID;
	}

	public void setSsID(String ssID) {
		this.ssID = ssID;
	}



	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}


	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	public String getQm() {
		return qm;
	}

	public void setQm(String qm) {
		this.qm = qm;
	}


	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public SSS(String imageUrl, String title, String iconUrl , String qm, String nickname,String ssID,String personID,String label,String time) {
		this.imageUrl = imageUrl;
		this.title = title;
		this.iconUrl = iconUrl;
		this.nickname = nickname;
		this.qm = qm;
		this.ssID = ssID;
		this.personID = personID;
		this.label =label;
		this.time = time;
	}

	public SSS(String imageUrl, String title) {
		this.imageUrl = imageUrl;
		this.title = title;
	}
}
