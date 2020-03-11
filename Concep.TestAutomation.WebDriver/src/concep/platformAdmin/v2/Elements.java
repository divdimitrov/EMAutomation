package concep.platformAdmin.v2;

public class Elements {

    protected final String deleteConfirmationTxt1    = "Are you really sure you want to delete this connection?";
    protected final String deleteConfirmationTxt2    = "Are you sure you want to remove this item?";
    protected final String deleteConfirmationTxtNote = "NOTE: If you leave a customer without a connection it will lead to misoperation of some of the running products.";
    protected final String sendIntegratedUsersNoIntegrationPromptTxt = "You need to setup first Dynamics Connector with client admin account,create connection and then to hit this button to import any regular users previously being integrated";
    protected final String recordDeletedMsg          = "//div[contains(text(), 'Record deleted')]";

    // Landing page
    protected final String getLandingPageNavigationListContainers(
                                                                   String pageName ) {

        return "//h3[text()[contains(.,'" + pageName + "')]]";
    }

    protected final String getLandingPageNavigationPageLink(
                                                             String pageName,
                                                             String subPage ) {

        return getLandingPageNavigationListContainers( pageName ) + "/../..//li/a[text()[contains(.,'"
               + subPage + "')]]";
    }

    // Systems page elements xPaths
    protected final String extSystemPageTitle         = "Review, add and edit client system";
    protected final String extSystemName              = "//td//input[@name='Name']";
    protected final String extSystemConnecitonMethod  = "//td//select[@name='ConnectionMethodId']";
    protected final String extSystemType              = "//td//select[@name='ProviderType']";
    protected final String extSystemForType           = "//td//select[@name='ProviderTypeAsString']";
    protected final String extSystemAuthentication    = "//td//select[@name='AuthenticationTypeId']";
    protected final String extSystemAddress           = "//tr/td//input[@name='Address']";
    protected final String extSystemSuperUser         = "//td//select[@name='SuperUserExtenalSystemIdentityid']";

    // System user page elements xPaths
    protected final String systemUsersPageTitle       = "external system identities (integrations). Make sure to enter the username in EXACTLY the same way, as some systems are case sensitive";
    protected final String extSystemIdentityExtSystem = "//td//select[@name='ExternalSystemId']";
    protected final String extSystemIdentityUsername  = "//td//input[@name='Username']";
    protected final String extSystemIdentityPassword  = "//td//input[@name='Password']";
    protected final String extSystemIdentityJsonDoc   = "//td//textarea[contains(concat(' ',@class,' '),'jsonDocument')]";
    
    protected String getExtSystemUserPermission(String permissionName) {
    	
    	return "//td[contains(text(), '" + permissionName + "')]/..//input[@type='checkbox' and not (@disabled='disabled')]";
    }

    protected String getSystemUserObfuscatedPassword(
                                                      String sysUserRecord ) {

        return "//tr/td/span[contains(text(), '" + sysUserRecord
               + "')]/../../td[3]//i[contains(text(), '(hidden)')]";
    }
    
    // Send Integrated Users page elements xPaths
    protected final String sendIntegratedUersPageTitle = "Send Integrated Users.";
    protected final String sendIntegratedUsersNoIntegrationPromptMsg = "//span[contains(text(), '" + sendIntegratedUsersNoIntegrationPromptTxt + "')]";
    protected final String sendIntegratedUsersUpdateAllTokensBtn = "//a[contains(text(), 'Update all tokens')]";
    
    // Connections page elements xPaths
    protected final String connectionPageTitle   = "Connections";
    protected final String connectionSource      = "//td//select[@name='SourceId']";
    protected final String connectionDestination = "//td//select[@name='DestinationId']";
    protected final String connectionJsonDoc     = "//td//textarea[contains(concat(' ',@class,' '),'jsonDocument')]";

    // Schedules page elements xPaths
    protected final String schedulesPageTitle    = "Review, add, edit and run scheduled jobs.";
    protected final String schedulesServices     = "//td//select[@name='ServiceName']";
    protected final String schedulesJobContext   = "//td//select[@name='ContextType']";
    protected final String schedulesContext      = "//td//textarea[contains(concat(' ',@class,' '),'context')]";
    protected final String schedulesEnabled      = "//td//input[@type='checkbox'][not(@disabled)]";
    protected final String schedulesPeriod       = "//tr[last()]//select[@name='SchedulePeriod']";

    protected String getScheduleRecordManuallyJobRunBtn(
                                                         String parameter ) {

        return "//tr/td/span[contains(text(), '" + parameter + "')]/../..//td//span[@name='run']";
    }

    // Alert page elements xPaths
    protected final String alertContentPageTitle       = "Review, add and edit customer";
    protected final String alertContentExtID           = "//td//input[@name='ExternalId']";
    protected final String alertContentTitle           = "//td//input[@name='Title']";
    protected final String alertContentDescription     = "//td//input[@name='Description']";
    protected final String alertContentAuthor          = "//td//input[@name='Author']";
    protected final String alertContentUrl             = "//td//input[@name='Url']";
    protected final String alertContentMedia           = "//td//input[@name='MediaUrl']";
    protected final String alertContentType            = "//td//input[@name='ContentType']";
    protected final String alertContentPublicationDate = "//tr[1]/td//input[@name='publicationDate']";
    protected final String alertContentPublicationTime = "//tr[1]/td//input[@name='publicationTime']";
    protected final String alertContentExpiryDate      = "//tr[1]/td//input[@name='expiryDate']";
    protected final String alertContentExpiryTime      = "//tr[1]/td//input[@name='expiryTime']";

    // Alert --> Content page subgrid xPaths
    protected final String addSubgridRecord            = "//div[@name='modal-body']//th/i[@title='Add new record']";
    protected final String saveSubgridRecord           = "//div[@name='modal-body']//button[@name='save']";
    protected final String subgridIcon                 = "//td/i[contains(concat(' ',@class,' '),'glyphicon-folder-open')]";
    protected final String subgridKey                  = "//div/input[@name='Key']";
    protected final String subgridDisplayName          = "//div/input[@name='DisplayName']";
    protected final String subgridDataType             = "//div/select[@name='Type']";
    protected final String subgridSpecialType          = "//div/select[@name='FieldType']";
    protected final String subgridValue                = "//div/input[@name='Value']";
    protected final String subgridClose                = "//button[text()[contains(.,'Close')]]";

    protected String getEditSubgridRecord(
                                           String parameter ) {

        return "//tr/td/span[contains(text(), '" + parameter + "')]/../..//td//span[@name='edit']";
    }

    protected String getDeleteRecordBtn(
                                         String parameter ) {

        return "//tr/td/span[contains(text(), '" + parameter + "')]/../..//td//span[@name='delete']";
    }

    protected String getCancelSubgridRecord(
                                             String parameter ) {

        return "//tr/td/span[contains(text(), '" + parameter + "')]/../..//td//span[@name='close']";
    }

    protected String getSaveSubgridRecord(
                                           String parameter ) {

        return " //div[@name='modal-body']//tr/td/span[contains(text(), '" + parameter
               + "')]/../..//button[@name='save']";
    }

    // Alert --> Contacts page xPaths
    protected final String contactPageTitle                = "Review, add and edit customer";
    protected final String contactExtReference             = "//td//input[@name='ExternalReference']";
    protected final String contactFirstName                = "//td//input[@name='FirstName']";
    protected final String contactLastName                 = "//td//input[@name='LastName']";
    protected final String contactEmail                    = "//td//input[@name='Email']";
    protected final String contactPhoneNumber              = "//td//input[@name='PhoneNumber']";
    protected final String contactCompany                  = "//tr[1]/td//select[@name='company']";
    protected final String contactJobTitle                 = "//td//input[@name='JobTitle']";

    // Alert --> Meta Model page xPaths
    protected final String metaModelChannelsListContainer  = "//ul[@data-type='channel']";
    protected final String metaModelCategoryName           = "//div/input[@id='categoryName']";
    protected final String metaModelCategoryKey            = "//div/input[@id='categoryKey']";
    protected final String metaModelCategoryNameEdit       = "//div[contains(@class, 'categoryeditmode')]//input[@name='categoryName']";
    protected final String metaModelCategoryKeyEdit        = "//div[contains(@class, 'categoryeditmode')]//input[@name='categoryKey']";
    protected final String metaModelAddCategory            = "//button[contains(text(), 'Add Category')]";
    protected final String metaModelAddCategoryBtnDisabled = "//button[contains(text(), 'Add Category')][@disabled='disabled']";
    protected final String metaModelAoiNameEdit            = "//div[contains(@class, 'aoieditmode')]//input[@name='aoiName']";
    protected final String metaModelAoiKeyEdit             = "//div[contains(@class, 'aoieditmode')]//input[@name='aoiKey']";
    protected final String metaModelTagNameEdit            = "//div[contains(@class, 'tageditmode')]//input[@placeholder='Tag name']";
    protected final String metaModelExpandAllBtn           = "//div[@id='tree-root']/span[contains(text(), 'Expand all')]";
    protected final String metaModelCollapseAllBtn         = "//div[@id='tree-root']/span[contains(text(), 'Collapse all')]";

    protected String getMetaModelChannelContainerRecord(
                                                         String channelName ) {

        return metaModelChannelsListContainer + "//li//div[contains(text(), '" + channelName + "')]";
    }

    protected String getMetaModelCategoryRecord(
                                                 String categoryName ) {

        return "//div[contains(@class, 'category-title')]/div[contains(text(), '" + categoryName + "')]";
    }

    protected String getMetaModelAoiRecord(
                                            String parentCategory,
                                            String aoiName ) {

        return getMetaModelCategoryRecord( parentCategory )
               + "/../..//div[contains(@class, 'aoi-title')]/div[contains(text(), '" + aoiName + "')]";
    }

    protected String getMetaModelTagRecord(
                                            String parentCategory,
                                            String parentAoi,
                                            String tagName ) {

        return getMetaModelAoiRecord( parentCategory, parentAoi )
               + "/../..//div[contains(@class, 'aoi-title')]/div[contains(text(), '" + tagName + "')]";
    }

    protected String getMetaModelAOIName(
                                          String categoryName ) {

        return "//li//div[contains(text(), '" + categoryName + "')]/../../ol//input[@placeholder='AOI name']";
    }

    protected String getMetaModelAOIKey(
                                         String categoryName ) {

        return "//li//div[contains(text(), '" + categoryName + "')]/../../ol//input[@placeholder='AOI Key']";
    }

    protected String getMetaModelAddAOI(
                                         String categoryName ) {

        return "//li//div[contains(text(), '" + categoryName
               + "')]/../../ol//button[contains(text(), 'Add AOI')]";
    }

    protected String getAOIdragAndDropArea(
                                            String aoiName ) {

        return "//li//div[contains(text(), '" + aoiName
               + "')]/../../div[contains(concat(' ',@class,' '),'pull-left')]";
    }

    protected String getMetaModelTagName(
                                          String categoryName,
                                          String aoiName ) {

        return getMetaModelAoiRecord( categoryName, aoiName ) + "/../..//input[@placeholder='Tag name']";
    }

    protected String getMetaModelAddTag(
                                         String categoryName,
                                         String aoiName ) {

        return getMetaModelAoiRecord( categoryName, aoiName ) + "/../..//button[contains(text(), 'Add tag')]";
    }

    protected String getMetaModelInteraction(
                                              String interactionType ) {

        return "//div[@id='tree-root']/span[text()[contains(., '" + interactionType + " all" + "')]]";
    }

    protected String getMetaModelEditRecord(
                                             String recordName ) {

        return "//li//div[contains(text(), '" + recordName
               + "')]/../a/i[@class='glyphicon glyphicon-pencil']";
    }

    protected String getMetaModelDeleteRecord(
                                               String recordName ) {

        return "//li//div[contains(text(), '" + recordName
               + "')]/..//i[contains(concat(' ',@class,' '),'glyphicon-trash')]";
    }

    protected String getMetaModelCategoryCancelRecord() {

        return "//div[contains(@class, 'categoryeditmode')]/..//a/i[contains(@class ,'glyphicon-remove')]";
    }

    protected String getMetaModelAoiCancelRecord() {

        return "//div[contains(@class, 'aoieditmode')]/..//a/i[contains(@class ,'glyphicon-remove')]";
    }

    protected String getMetaModelTagCancelRecord() {

        return "//div[contains(@class, 'tageditmode')]/..//a/i[contains(@class ,'glyphicon-remove')]";
    }

    protected String getMetaModelCategorySaveRecord() {

        return "//div[contains(@class, 'categoryeditmode')]/..//button/i[contains(@class ,'glyphicon-floppy-disk')]";
    }

    protected String getMetaModelAoiSaveRecord() {

        return "//div[contains(@class, 'aoieditmode')]/..//button/i[contains(@class ,'glyphicon-floppy-disk')]";
    }

    protected String getMetaModelTagSaveRecord() {

        return "//div[contains(@class, 'tageditmode')]/..//button/i[contains(@class ,'glyphicon-floppy-disk')]";
    }

    protected String getMetaModelTargetChannel(
                                                String channelName ) {

        return "//div[contains(text(), '" + channelName + "')]/../../ul[@data-type='category']";
    }

    protected String getMetaModelChannelAOISubRecord(
                                                      String channelName,
                                                      String aoiName ) {

        return getMetaModelTargetChannel( channelName )
               + "/../..//ul[@data-type='aoi']//div[contains(text(), '" + aoiName + "')]";
    }

    protected String getMetaModelChannelAOIDeleteButton(
                                                         String channelName,
                                                         String aoiName ) {

        return getMetaModelTargetChannel( channelName ) + "/../..//ul//div[contains(text(),'" + aoiName
               + "')]/../a/i[@class='glyphicon glyphicon-remove']";
    }

    // Alert --> Channel/Aoi Editor page xPaths
    protected final String channelPageTitle                  = "Edit the channels and areas of interest";
    protected final String channelRecordsContainer           = "//div[@class='angular-ui-tree-handle']";
    protected final String channelEditorAddChannel           = "//div[@id='tree-root']//button[contains(text(), 'Add Channel')]";
    protected final String channelEditorKey                  = "//input[contains(@id,'inputKey')]";
    protected final String channelEditorDisplayName          = "//input[contains(@id,'inputDisplayName')]";
    protected final String channelEditorMaxContentAge        = "//input[contains(@id,'inputMaxContentAge')]";
    protected final String channelEditorSuppressDuplContent  = "//input[contains(@id,'chbSuppressDuplicateContent')]";

    protected final String channelEditorDeliveryTemplateKey  = "//input[contains(@id,'inputTemplateName')]";
    protected final String channelEditorEmailProviderProfile = "//select[contains(@id,'inputemailprofile')]";
    protected final String channelEditorSenderProfileIdLabel = "//label[contains(text(), 'Sender profile id:')]";
    protected final String channelEditorSenderProfileIdInput = "//input[contains(@id,'senderProfile')]";
    protected final String channelEditorDomainIdLabel        = "//label[contains(text(), 'Domain id:')]";
    protected final String channelEditorDomainIdInput        = "//input[contains(@id,'domain')]";

    protected final String channelEditorCrmProviderProfile   = "//select[contains(@id,'inputCrmProfile')]";
    protected final String channelEditorDefaultSchedule      = "//select[contains(@id,'inputDefaultSchedule')]";

    protected final String channelEditorSaveChanges          = "//button[contains(text(), 'Save Changes')]";
    
    protected String getChannelAvailableSchedulesLabel(String schedule) {
    	
    	return "//label[contains(text(), 'Available schedules')]/..//label[contains(text(), '" + schedule + "')]";
    }
    
    protected String getChannelAvailableSchedulesCheckbox(String schedule) {
    	
    	return getChannelAvailableSchedulesLabel(schedule) + "/../input[@type='checkbox']";
    }

	protected String getChannelAvailableSchedulesInput(String schedule) {
		
		return getChannelAvailableSchedulesLabel(schedule) + "/../input[@type='text']";
	}

    protected String getChannelRecord(
                                       String channelName ) {

        return "//div[contains(@class, 'category-title')]//span[contains(text(), '" + channelName + "')]";
    }

    protected String getChannelEditorDeleteButton(
                                                   String channelName ) {

        return getChannelRecord( channelName ) + "/../../button/i[contains(@class, 'glyphicon-trash')]";
    }

    protected String getChannelEditorEditButton(
                                                 String channelName ) {

        return getChannelRecord( channelName ) + "/../../a/i[@class='glyphicon glyphicon-pencil']";
    }

    // Alert --> Subscribers page xPaths
    protected final String subscriberPageTitle             = "View channel subscribers";
    protected final String subscriberChannelsListContainer = "//h5[contains(text(), 'Channels')]/..";
    protected final String subscriberAreaOfInterest        = "//div/select[@name='AreaOfInterestId']";

    protected String getSubscribersChannel(
                                            String channelName ) {

        return subscriberChannelsListContainer + "//li/a[contains(text(), '" + channelName + "')]";
    }

    // Alert --> Templates page xPaths
    protected final String templatePageTitle           = "Review, add and edit templates";
    protected final String templateName                = "//td//input[@name='Name']";
    protected final String templateChannel             = "//td//select[@name='ChannelId']";
    protected final String templateDeliveryChannelType = "//td//select[@name='DeliveryChannelTypeId']";
    protected final String templateType                = "//td//select[@name='TemplateTypeId']";
    protected final String templateContent             = "//td//textarea[contains(concat(' ',@class,' '),'content')]";
    protected final String templateReference           = "//td//input[@name='Reference']";

    // Logs
    protected final String logsViewNameInput           = "//div[@class='sidebar-widget']";
    protected final String logsAddViewButton           = "//div[@class='sidebar-widget']//button[contains(text(), 'Add view')]";
    protected final String logsViewsHeading            = "//div[@class='sidebar']//h4[contains(text(), 'Saved views')]";
    protected final String logsReordsTable             = "//section/table[@id='logs-table']";
    protected final String logsViewSaveMsg             = "//div[contains(text(), 'View added')]";
    protected final String logsReloadBtn               = "//button[contains(text(), 'Reload')]";
    protected final String logsStopAutorefresh         = "//button[contains(text(), 'Stop autorefresh')]";
    protected final String logsClearFilters            = "//button[contains(text(), 'Clear filters')]";
    protected final String logsExportCSV               = "//a[contains(text(), 'Export to CSV')]";
    protected final String logsShowHideBtn             = "//section[@id='dashboard-view']//a[@class='show-hide-settings']";
    protected final String logsViewOptionsContainer    = "//div[@class='sidebar-widget']//h4[contains(text(), 'Options')]/../table";

    protected String getLogViewLabel(
                                      String viewName ) {

        return "//div[@class='sidebar-inner']//li/a[contains(text(), '" + viewName + "')]";
    }

    protected String getDeleteViewButton(
                                          String viewName ) {

        return "//div[@class='sidebar-inner']//li/a[contains(text(), '" + viewName
               + "')]/span[@class='close']";
    }

    protected String getLogOptionCheckbox(
                                           String option ) {

        return "//label[contains(text(), '" + option + "')]/following-sibling::input[@type='checkbox']";
    }

    // Common elements xPaths
    protected final String recordsTableGrid                     = "//section[@class='loading-container']//table";
    protected final String addNewRecord                         = "//th/i[@title='Add new record' and @data-is-disabled='false']";
    protected final String addNewRecordHiddenButton             = "//th/i[contains(@class, 'ng-hide') AND @title='Add new record']";
    protected final String saveRecord                           = "//td//div[@class='editable-controls form-group']//td//button[@name='save'] | //td//form[contains(@class, 'form-buttons') and not(contains(@class, 'ng-hide'))]//button[@name='save']";
    protected final String editRecord                           = "//td//span[@name='edit']";
    protected final String cancelRecord                         = "//td//div[@class='editable-controls form-group']//td//span[@name='close'] | //td//form[contains(@class, 'form-buttons') and not(contains(@class, 'ng-hide'))]//span[@name='close']";
    protected final String cancelSubgridRecord                  = "//div[contains(@class, 'modal-body')]//td//span[@name='close']";
    protected final String deleteRecord                         = "//td//span[@name='delete']";
    protected final String deleteConfirmation1                  = "//div[contains(text(), '"
                                                                  + deleteConfirmationTxt1 + "')]";
    protected final String deleteConfirmation2                  = "//div/p[contains(text(), '"
                                                                  + deleteConfirmationTxt2 + "')]";
    protected final String deleteConfirmationNote               = "//p[contains(text(), '"
                                                                  + deleteConfirmationTxtNote + "')]";
    protected final String deleteConfirmationUnderstandCheckbox = "//span[contains(text(), 'I understand')]/../input[@type='checkbox']";;
    protected final String confirmDeletion                      = "//button[text()[contains(.,'OK')]]";
    protected final String cancelDeletion                       = "//button[text()[contains(.,'Cancel')]]";
    protected final String filterInput                          = "//div[@class='popover-content']//input";
    protected final String filterDropdown                       = "//div[@class='popover-content']//select";
    protected final String filterButton                         = "//div[@class='popover-content']//a[text()[contains(.,'Filter')]]";
    protected final String clearSingleFilter                    = "//div[@class='popover-content']//a[text()[contains(.,'Clear filter')]]";
    protected final String clearAllFilters                      = "//th[contains(text(), 'Clear filters')]";
    protected final String recordsTableSubgrid                  = "//div[@name='modal-body']/table";
    protected final String recordSavedSuccess                   = "//div[contains(@class, 'toast-success')]/div[contains(text(), 'Record saved')]";

    protected String getScheduleRecord(String service,
    								   String jobContext,
    								   String additionalInformation,
    								   String schedulePeriod) {
    	
    	String fullXpath = "//tr";
    	
    	if (service != null) {
			
    		fullXpath += "[td//span[contains(text(), '" + service + "')]/../..]";
		}
    	
    	if (jobContext != null) {
			
    		fullXpath += "[td//span[contains(text(), '" + jobContext + "')]/../..]";
		}
    	
    	if (additionalInformation != null) {
			
    		fullXpath += "[td//span[contains(text(), '" + additionalInformation + "')]/../..]";
		}
    	
    	if (schedulePeriod != null) {
			
    		fullXpath += "[td//div[text()[contains(.,'" + schedulePeriod + "')]]]";
		}
    	
    	return fullXpath;
    }
    
    protected String getRecordRow(
                                   String recordName ) {

        return "//tr/td/span[contains(text(), '" + recordName + "')]/../..";
    }

    protected String getSubgridRecordRow(
                                          String recordName ) {

        return recordsTableSubgrid + "//tr/td/span[contains(text(), '" + recordName + "')]/../..";
    }

    protected String getFilterIcon(
                                    String parameter ) {

        return "//th/span[contains(text(), '" + parameter + "')]/span";
    }

    protected String getPageTab(
                                 String pageName ) {

        return "//ul[@class='nav navbar-nav']/li/a[text()[contains(., '" + pageName + "')]]";
    }

    protected String getSubPageTab(
                                    String subPageName ) {

        return "//div[@class='sidebar-inner']//li/a[contains(text(), '" + subPageName + "')]";
    }

    protected String getPageNumber(
                                    String pageNumber ) {

        return "//ul/li/a[@class='ng-binding' and contains(text(),'" + pageNumber + "')]";
    }

    protected String getPageTitle(
                                   String pageTitle ) {

        return "//p[contains(text(), '" + pageTitle + "')]";
    }

    protected String getTableHeader(
                                     String tableHeader ) {

        return "//th//span[normalize-space(text())='" + tableHeader + "']";
    }

    protected String getSubgridTableHeader(
                                            String tableHeader ) {

        return recordsTableSubgrid + "//th/span[contains(text(), '" + tableHeader + "')]";
    }

    protected String getValidationMessageRecordField(
                                                      String field,
                                                      String validationMsg ) {

        return field + "/../div[contains(text(), '" + validationMsg + "')]";
    }

    protected String getDropdownOption(
                                        String xPathDropdown,
                                        String optionText ) {

        return xPathDropdown + "/option[contains(text(), '" + optionText + "')]";
    }
}
