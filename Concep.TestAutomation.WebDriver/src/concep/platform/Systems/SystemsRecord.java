package concep.platform.Systems;

public class SystemsRecord {

	private String name;
	private String systemType;
	private String systemForType;
	private String authentication;
	private String address;
	
	public SystemsRecord() {
	}

	public SystemsRecord(String name, String systemType, String systemForType,
			String authentication, String address) {
		
		this.setName(name);
		this.setSystemType(systemType);
		this.setSystemForType(systemForType);
		this.setAuthentication(authentication);
		this.setAddress(address);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getSystemType() {
		return systemType;
	}

	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

	public String getSystemForType() {
		return systemForType;
	}

	public void setSystemForType(String systemForType) {
		this.systemForType = systemForType;
	}

	public String getAuthentication() {
		return authentication;
	}

	public void setAuthentication(String authentication) {
		this.authentication = authentication;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}	
}
