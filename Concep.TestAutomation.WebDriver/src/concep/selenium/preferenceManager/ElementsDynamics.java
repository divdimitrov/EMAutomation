package concep.selenium.preferenceManager;

public class ElementsDynamics {
	
	protected final String msDynamicsCRM	    = "//span[@id='navTabLogoTextId'][contains(text(), 'Microsoft Dynamics CRM')]";
	
	protected final String salesMenu          	= "//span[@id='navTabModuleButtonTextId'][contains(text(), 'Sales')]";
	protected final String contactsMenu       	= "//a[@id='nav_conts']";
			//a[@id='nav_conts']/span[contains(@class, 'navActionButtonLabel')][contains(text(), 'Contacts')]";
	protected final String commonMenuContact	= "//span[@class='navTabButtonArrowDown']";
	
	protected final String searchContactField	= "//input[@id='crmGrid_findCriteria']";//'crmGrid_quickFindContainer'
	
	protected final String searchContactButton  = "//a[@id='crmGrid_findCriteriaButton']";////a[@id='crmGrid_findCriteriaButton']
	
	protected final String contactData			= "//div[@class='stdTable']/nobr[@class, 'gridcellpadding'])]";
			//"//a[@id='gridBodyTable_primaryField_{FE5CC70E-3FFD-E811-8104-028E9DB599A6}_0']";
	
	
	//contact Details:
	protected final String fullNameDynamics 	= "//div[@class='ms-crm-FormSection-Container']/div/span[contains(@class, 'ms-crm-InlineEditLabelText')][contains(text(), 'Full Name')]";
	protected final String companyNameDynamics  = "//div[@class='ms-crm-FormSection-Container']/div/span[contains(@class, 'ms-crm-InlineEditLabelText')][contains(text(), 'Company Name')]";
	protected final String openContactWithEnter = "//tr[@class='ms-crm-List-Row']";//img[@class='ms-crm-grid-checkbox-image ms-crm-ImageStrip-checkbox_light']";
	protected final String emailStatus			= "";
	protected final String emailFormat			= "";
	
		
	/*public String foundContactEmail(
            						String contactEmail ) {

		   				return "//td[contains(text(),'" + contactEmail + "')]";
	   	}*/
	
	public String foundContactEmail(
									String contactEmail ) {

						return "//td[@class='ms-crm-List-DataCell inner-grid-cellPadding']/nobr[contains(text(),'" + contactEmail + "')]";
	}
	
	public String contactFullName(
            						String fullName ) {

		   				return "//div[@id='fullname']/div/span[contains(text(), '\" + fullName + \"')]";
	   	}
	
	
	//AOI in Dynamics:
	protected final String capitalMarketsNo		= "//span[@class='ms-crm-InlineEditLabelText'][contains(.,'AOI_Capital Markets')]";
	protected final String realEstateNo			= "//span[contains(@span, 'title')][contains(text(), 'No')]";
	protected final String taxNo				= "//span[contains(@span, 'title')][contains(text(), 'No')]";
	protected final String privateEquityNo		= "//span[contains(@span, 'title')][contains(text(), 'No')]";
	
	protected final String capitalMarketsYes	= "//div[@id='concep_aoi_capitalmarkets']/div/span[contains(@span, 'title')][contains(text(), 'Yes')]";
	protected final String realEstateYes		= "//div[@id='concep_aoi_realestate']/div/span[contains(@span, 'title')][contains(text(), 'Yes')]";
	protected final String taxYes				= "//div[@id='concep_aoi_tax']/div/span[contains(@span, 'title')][contains(text(), 'Yes')]";
	protected final String privateEquityYes		= "//div[@id='concep_aoi_privateequity']/div/span[contains(@span, 'title')][contains(text(), 'Yes')]";
	
	//ML in Dynamics for Contact:
	/*protected final String capitalMarketsNo		= "//div[@id='concep_aoi_capitalmarkets']/div/span[contains(@span, 'title')][contains(text(), 'No')]";
	protected final String capitalMarketsNo		= "//div[@id='concep_aoi_capitalmarkets']/div/span[contains(@span, 'title')][contains(text(), 'No')]";
	protected final String capitalMarketsNo		= "//div[@id='concep_aoi_capitalmarkets']/div/span[contains(@span, 'title')][contains(text(), 'No')]";
	protected final String capitalMarketsNo		= "//div[@id='concep_aoi_capitalmarkets']/div/span[contains(@span, 'title')][contains(text(), 'No')]";*/
	
	
	
	
	
	
	protected final String dynamicsConnectionURL = "https://Demo_BDP:Concep2014!@msd13.concep.com/";
    //protected final String emailField        = "//input[@id='inboxfield']";//div[@id='publicm8rguy']
    //protected final String loginButton       = "//button[contains(text(), 'Go')]";
    //protected String       loginInsideButton = "//div[@id='pubinboxfield']/btn";
     
    protected final String closeButton = "//a[@id='buttonClose']";
    
    //iFrames in Dynamics:
    public String          inlineDialogIframe  = "//iframe[@id='InlineDialog_Iframe']";
    public String		   contactsIframe	   = "//iframe[@id='contentIFrame0']";
    public String		   mainContactIframe   = "//iframe[@id='contentIFrame1']";
  
     
    
}

