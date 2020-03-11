package concep.platform.Systems.Enums;

public enum SystemForType {

	NOT_SET("NotSet"),
	INTERACTION("Interaction"),
	DYNAMICS("Dynamics"),
	SALESFORCE("Salesforce"),
	SEND("Send"),
	RSS("Rss");
	
	public String value;
	
	private SystemForType(String value) {
		this.value = value;
	}
}