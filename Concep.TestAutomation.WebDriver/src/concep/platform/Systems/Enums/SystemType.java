package concep.platform.Systems.Enums;

public enum SystemType {

	NOT_SET("NotSet"),
	CMS_PROVIDER("CmsProvider"),
	CRM_PROVIDER("CrmProvider"),
	EMAIL_PROVIDER("EmailProvider"),
	OTHER_PROVIDER("OtherProvider");
	
	public String value;
	
	private SystemType(String value) {
		this.value = value;
	}
}
