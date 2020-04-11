package model;

public enum MAP {
	MAP1(Sprite.MAP1,Sprite.LIFE,"map1/"),
	MAP2(Sprite.MAP2,Sprite.LIFE,"map2/"),
	MAP3(Sprite.MAP3,Sprite.LIFE,"map3/");
	
	private String showMAP;
	private String life;
	private String map;
	private MAP(String showMAP,String life,String map) {
		this.showMAP=showMAP;
		this.life = life;
		this.map = map;
	
	}
	
	public String getUrlMap() {
		return this.showMAP;
	}
	public String getMap() {
		return this.map;
	}

	public String getAvatar1() {
		return "char1/20.png";
	}
	public String getAvatar2() {
		return "char2/20.png";
	}
	public String getLife() {
		return this.life;
	}
}
