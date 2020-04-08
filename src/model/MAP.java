package model;

public enum MAP {
	MAP1("map1/map.png","char1/life.png"),
	MAP2("map2/map.png","char1/life.png"),
	MAP3("map3/map.png","char1/life.png");
	private String showMAP;
	private String avatar;
	private String life;
	private MAP(String showMAP,String life) {
		this.showMAP=showMAP;
		this.life = life;
		
	}
	
	public String getUrlMap() {
		return this.showMAP;
	}

	public String getAvatar1() {
		return "char1/2.png";
	}
	public String getAvatar2() {
		return "char2/2.png";
	}
	public String getLife() {
		return this.life;
	}
}
