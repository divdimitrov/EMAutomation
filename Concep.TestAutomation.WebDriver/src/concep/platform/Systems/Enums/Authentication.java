package concep.platform.Systems.Enums;

public enum Authentication {

	NONE("None"),
	FORMS("Forms"),
	BASIC("Basic"),
	DIGEST("Digest"),
	NEGOTIATE("Negotiate"),
	NTLM("NTLM"),
	ADFS("ADFS");
	
	public String value;
	
	private Authentication(String value) {
		this.value = value;
	}
}
