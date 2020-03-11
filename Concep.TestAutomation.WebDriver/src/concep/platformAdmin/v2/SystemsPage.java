package concep.platformAdmin.v2;

import org.testng.annotations.Test;

public class SystemsPage extends BasePlatformAdmin {

	public enum ExternalSystemPermission {

        ALERT("Alert"),
        DYNAMICS_CONNECTOR("Dynamics Connector"),
        INTERACTION_CONNECTOR("InterAction Connector");
        
        public String permissionName;

        private ExternalSystemPermission( String permissionName) {

            this.permissionName = permissionName;
        }
    }
	
    public SystemsPage fillExternalSystemRecord(
                                                  String name,
                                                  String systemType,
                                                  String systemForType,
                                                  String authentication,
                                                  String address,
                                                  String superUser ) throws Exception {

        if( name != "" ) {

            log.startStep( "Type in '" + name + "' for the External System record" );
            driver.clear( elements.extSystemName );
            driver.type( elements.extSystemName, name, driver.timeOut );
            log.endStep();
        }

        if( systemType != "" ) {

            log.startStep( "Select a '" + systemType + "' from the System Type dropdown" );
            driver.waitUntilElementIsClickable(elements.getDropdownOption(elements.extSystemType, systemType));
            driver.select( elements.extSystemType ).selectByVisibleText( systemType );
            log.endStep();
        }

        if( systemForType != "" ) {

            log.startStep( "Select a '" + systemForType + "' from the System for Type dropdown" );
            driver.waitUntilElementIsClickable(elements.getDropdownOption(elements.extSystemForType, systemForType));
            driver.select( elements.extSystemForType ).selectByVisibleText( systemForType );
            log.endStep();
        }

        if( authentication != "" ) {

            log.startStep( "Select a '" + authentication + "' from the System Type dropdown" );
            driver.waitUntilElementIsClickable(elements.getDropdownOption(elements.extSystemAuthentication, authentication));
            driver.select( elements.extSystemAuthentication ).selectByVisibleText( authentication );
            log.endStep();
        }

        if( address != "" ) {

            log.startStep( "Type in '" + address + "' for the External System record" );
            driver.clear( elements.extSystemAddress );
            driver.type( elements.extSystemAddress, address, driver.timeOut );
            log.endStep();
        }

        if( superUser != "" ) {
            log.startStep( "Select a '" + superUser + "' from the System Type dropdown" );
            driver.waitUntilElementIsClickable(elements.getDropdownOption(elements.extSystemSuperUser, superUser));
            driver.select( elements.extSystemSuperUser ).selectByVisibleText( superUser );
            log.endStep();
        }

        return this;
    }

    public SystemsPage fillExternalSystemIdentityRecord(
                                                          String externalSystem,
                                                          String username,
                                                          String password) throws Exception {

        if( externalSystem != "" ) {

            log.startStep( "Select a '" + externalSystem + "' from the External System dropdown" );
            driver.waitUntilElementIsClickable(elements.getDropdownOption(elements.extSystemIdentityExtSystem, externalSystem));
            driver.select( elements.extSystemIdentityExtSystem ).selectByVisibleText( externalSystem );
            log.endStep();
        }

        if( username != "" ) {

            log.startStep( "Type in '" + username + "' for the External System Identity record" );
            driver.clear( elements.extSystemIdentityUsername );
            driver.type( elements.extSystemIdentityUsername, username, driver.timeOut );
            log.endStep();
        }

        if( password != "" ) {

            log.startStep( "Type in '" + password + "' for the External System Identity record" );
            driver.clear( elements.extSystemIdentityPassword );
            driver.type( elements.extSystemIdentityPassword, password, driver.timeOut );
            log.endStep();
        }       

        return this;
    }
    
    public SystemsPage enableSystemUserPermission(String[] permissions) throws Exception {
    	
    	if (permissions != null) {
			
    		for (int i = 0; i < permissions.length; i++) {
				
    			if (!driver.isChecked(elements.getExtSystemUserPermission(permissions[i]))) {
					
    				log.startStep("Enable permission with name: " + permissions[i]);
        			driver.click(elements.getExtSystemUserPermission(permissions[i]), driver.timeOut);
        			log.endStep();
				}    			
			}
		}
    	
    	return this;
    }
    
    public SystemsPage disableSystemUserPermission(String[] permissions) throws Exception {
    	
    	if (permissions != null) {
			
    		for (int i = 0; i < permissions.length; i++) {
				
    			if (driver.isChecked(elements.getExtSystemUserPermission(permissions[i]))) {
					
    				log.startStep("Disable permission with name: " + permissions[i]);
        			driver.click(elements.getExtSystemUserPermission(permissions[i]), driver.timeOut);
        			log.endStep();
				}    			
			}
		}
    	
    	return this;
    }

    private SystemsPage fillConnectionRecord(
                                              String source,
                                              String destination,
                                              String jsonDocument ) throws Exception {

        if( source != "" ) {

            log.startStep( "Select a '" + source + "' from the Source dropdown" );
            driver.waitUntilElementIsClickable(elements.getDropdownOption(elements.connectionSource, source));
            driver.select( elements.connectionSource ).selectByVisibleText( source );
            log.endStep();
        }

        if( destination != "" ) {

            log.startStep( "Select a '" + destination + "' from the Destination dropdown" );
            driver.waitUntilElementIsClickable(elements.getDropdownOption(elements.connectionDestination, destination));
            driver.select( elements.connectionDestination ).selectByVisibleText( destination );
            log.endStep();
        }

        if( jsonDocument != "" ) {

            log.startStep( "Fill in the value for a Json Document textarea" );
            driver.type( elements.connectionJsonDoc, jsonDocument, driver.timeOut );
            log.endStep();
        }

        return this;
    }
    
    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyDisplaySystemsTabMainNavigationFromOtherPages() throws Exception {

        log.startTest( "Verify that 'Systems' tab from the main navigation is successfully displayed from the other pages" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" ).
            verifyDisplayedElements(new String[] {elements.getPageTab("Systems")}, 
            						new String[] {"System tab"}, true).
            navigateToPage("Alert").
            verifyDisplayedElements(new String[] {elements.getPageTab("Systems")}, 
									new String[] {"System tab"}, true).
	        navigateToPage("Schedules").
	        verifyDisplayedElements(new String[] {elements.getPageTab("Systems")}, 
					new String[] {"System tab"}, true).
			navigateToPage("Logs").
			verifyDisplayedElements(new String[] {elements.getPageTab("Systems")}, 
					new String[] {"System tab"}, true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyAccessSystemsPage() throws Exception {

        log.startTest( "Verify that the Systems Page can be successfully accessed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" ).verifyPageTitle( elements.extSystemPageTitle );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyDisplaySystemsPageSubmenuTabs() throws Exception {

        log.startTest( "Verify that the Systems Page submenu elements are successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .verifyPageSubmenuTabs( new String[]{ "Systems",
                                                 "System users",
                                                 "Send integrated users",
                                                 "Connections" } );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyDisplayExternalSystemsTableGrid() throws Exception {

        log.startTest( "Verify that the External Systems table grid is successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .verifyDisplayedElements( new String[]{ elements.recordsTableGrid },
                                        		 				   new String[] {"External Systems table grid"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyDisplayExternalSystemTableColumns() throws Exception {

        log.startTest( "Verify that the External Systems table grid columns are successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .verifyDisplayedElements( new String[]{ elements.getTableHeader( "Name" ),
		                                                                         elements.getTableHeader( "System Type" ),
		                                                                         elements.getTableHeader( "System for type" ),
		                                                                         elements.getTableHeader( "Authentication" ),
		                                                                         elements.getTableHeader( "Address" ),
		                                                                         elements.clearAllFilters },
                                                                    new String[] {"Name column",
                                        		 								  "System Type column",
                                        		 								  "System for type column",
                                        		 								  "Authentication column",
                                        		 								  "Address column",
                                        		 								  "Clear filters button"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyDisplayExternalSystemsTableHeadersFilterButtons() throws Exception {

        log.startTest( "Verify that a filter button exist for each of the following External Systems table grid headers" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .verifyDisplayedElements( new String[]{ elements.getFilterIcon( "Name" ),
                                                                           elements.getFilterIcon( "System Type" ),
                                                                           elements.getFilterIcon( "System for type" ),
                                                                           elements.getFilterIcon( "Authentication" ),
                                                                           elements.getFilterIcon( "Address" ) },
                                                                   new String[] {"Filter by Name",
                                        		 								 "Filter by System Type",
                                        		 								 "Filter by System for type",
                                        		 								 "Filter by Authentication",
                                        		 								 "Filter by Address"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyDisplayExternalSystemsRecordEditButton() throws Exception {

        String randNumber = driver.getTimestamp();
        
        String name = randNumber + "name";
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";

        log.startTest( "Verify that a button for editing External Systems record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( name,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .filterRecordsBy( "Name", name, "Text" )
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( name )
                                                                                 + elements.editRecord },
                                                                   new String[] {"External System record Edit button"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyDisplayExternalSystemsRecordDeleteButton() throws Exception {

        String randNumber = driver.getTimestamp();
        
        String name = randNumber + "name";
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";

        log.startTest( "Verify that a button for deleting External Systems record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( name,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .filterRecordsBy( "Name", name, "Text" )
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( name )
                                                 								 + elements.deleteRecord },
		                                   new String[] {"External System record Delete button"},
		                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyDisplayExternalSystemsRecordCancelButton() throws Exception {

        String randNumber = driver.getTimestamp();

        String name = randNumber + "name";
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";
        
        log.startTest( "Verify that a button for canceling External Systems record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( name,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .filterRecordsBy( "Name", name, "Text" )
                                         .startEditingRecord( elements.getRecordRow( name ), false, "" )
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( name )
                                                                                 + elements.cancelRecord },
                                                                   new String[] {"External System record Cancel button"},
                                                                   true );
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyDisplayExternalSystemsRecordSaveButton() throws Exception {

        String randNumber = driver.getTimestamp();

        String name = randNumber + "name";
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";
        
        log.startTest( "Verify that a button for saving External Systems record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( name,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .filterRecordsBy( "Name", name, "Text" )
                                         .startEditingRecord( elements.getRecordRow( name ), false, "" )
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( name )
                                                                                 + elements.saveRecord },
                                                                   new String[] {"External System record Save button"},
                                                                   true );
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyDisplayExternalSystemsAddNewRecordButton() throws Exception {

        log.startTest( "Verify that a button for adding External Systems record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .verifyDisplayedElements( new String[]{ elements.addNewRecord },
                                        		 new String[] {"Add new External System record button"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyAddExternalSystemRecordToGrid() throws Exception {

        String randNumber = driver.getTimestamp();
        
        String name = randNumber + "name";
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";

        log.startTest( "Verify that a new External Systems record can be added to the grid" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( name,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .filterRecordsBy( "Name", name, "Text" )
                                         .verifyDisplayedElements( new String[] { elements.getRecordRow( name ) },
                                        		 				   new String[] { name + " External System record"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyDisplayValidationMessageExternalSystemRecordWithoutName() throws Exception {

        String randNumber = driver.getTimestamp();

        String name = "";
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";
        
        log.startTest( "Verify that a validation message is displayed when External System record is saved without Name" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( name,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .verifyDisplayedElements( new String[]{ elements.getValidationMessageRecordField( elements.extSystemName,
                                                                                                                           super.requiredFieldTextMsg ) },
                                                                   new String[] {super.requiredFieldTextMsg},
                                                                                                                           true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    /*@Test
    public void successfullyDisplayExternalSystemRecordFieldValidationMessageMisisngConnectionMethod() throws Exception {
    			
    	String randNumber = driver.getTimestamp();
    	
    	log.startTest("Verify that a validation message is displayed when External System record is saved without Conneciton Method");
    	try {		
    			
    		accessPlatformAdminHomePage().navigateToPage("Systems").startCreatingRecord(false).getSystemPage(this).
    		fillExternalSystemRecord("name" + randNumber, "", "CrmProvider", "", "Forms", "address" + randNumber, "").saveRecord(false).
    		verifyDisplayedElements(new String[] {elements.getValidationMessageRecordField(elements.extSystemConnecitonMethod, super.requiredFieldTextMsg)}, true);	
    		
    	} catch (Exception e) {
    		 
    		 log.endStep( false );
    	     throw e;
    	}
    	
    	log.endTest();
     }*/

    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyDisplayValidationMessageExternalSystemRecordWithoutSystemType()
                                                                                                throws Exception {

        String randNumber = driver.getTimestamp();    
        
        String name = randNumber + "name";
        String systemType = "";
        String systemForType = "";
        String authentication = "ADFS";
        String address = randNumber + "address";

        log.startTest( "Verify that a validation message is displayed when External System record is saved without System Type" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( name,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .verifyDisplayedElements( new String[]{ elements.getValidationMessageRecordField( elements.extSystemType,
                                                                                                                           super.requiredFieldTextMsg ) },
                                                                   new String[] {super.requiredFieldTextMsg},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyDisplayValidationMessageExternalSystemRecordWithoutAddress()
                                                                                             throws Exception {

        String randNumber = driver.getTimestamp();

        String name = randNumber + "name";
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = "";
        
        log.startTest( "Verify that a validation message is displayed when External System record is saved without Address" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( name,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .verifyDisplayedElements( new String[]{ elements.getValidationMessageRecordField( elements.extSystemAddress,
                                                                                                                           super.requiredFieldTextMsg ) },
                                                                   new String[] {super.requiredFieldTextMsg},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "systems-tests" })
    public void unsuccessfullyAddExternalSystemRecordWhenCancelButtonClicked() throws Exception {

        String randNumber = driver.getTimestamp();
        
        String name = randNumber + "name";
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";

        log.startTest( "Verify that a new External Systems record is not added to the grid when Cancel button is clicked" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( name,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .cancelRecord(false)
                                         .filterRecordsBy( "Name", name, "Text" )
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( name ) },
                                        		 				   new String[] { name + " External System record"},
                                                                   false );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyEditExternalSystemRecordWhenSaveButtonClicked() throws Exception {

        String randNumber = driver.getTimestamp();

        String name = randNumber + "name";
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";
        
        // Note that the "SystemType" and "System for type" values can't be edited
        log.startTest( "Verify that already existing External System record can be successfully edited when click on the Save button" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( name,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .filterRecordsBy( "Name", name, "Text" )
                                         .startEditingRecord(elements.getRecordRow( name ), false, "" )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( name + "Changed",
                                                                    "",
                                                                    "",
                                                                    "Basic",
                                                                    address + "Changed",
                                                                    "" )
                                         .saveRecord( false )
                                         .clearFilters( "", true )
                                         .filterRecordsBy( "Name", name + "Changed", "Text" )
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( name + "Changed" ) },
                                                                   new String[] {"Successfully edited record"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "systems-tests" })
    public void unsuccessfullyEditExternalSystemRecordWhenCancelButtonClicked() throws Exception {

        String randNumber = driver.getTimestamp();
        
        String name = randNumber + "name";
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";

        log.startTest( "Verify that already existing External System record is not updated when click on the Cancel button" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( name,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .filterRecordsBy( "Name", name, "Text" )
                                         .startEditingRecord(elements.getRecordRow( name ), false, "" )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( name + "Changed",
                                                                    "",
                                                                    "",
                                                                    "Basic",
                                                                    address + "Changed",
                                                                    "" )
                                         .cancelRecord(false)
                                         .clearFilters( "", true )
                                         .filterRecordsBy( "Name", name + "Changed", "Text" )
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( name + "Changed" ) },
                                        		 				   new String[] {"Successfully edited record"},
                                                                   false );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyDisplayWarningMessageDeleteExternalSystemRecord() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String name = randNumber + "name";
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";

        log.startTest( "Verify that a warning deletion message for External System record is successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( name,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .filterRecordsBy( "Name", name, "Text" )
                                         .deleteRecord( name, false );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "systems-tests" })
    public void unsuccessfullyDeleteExternalSystemRecordWhenDeletionIsCanceled() throws Exception {

        String randNumber = driver.getTimestamp();
        
        String name = randNumber + "name";
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";

        log.startTest( "Verify that the External System record is not deleted when deletion is canceled" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( name,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .filterRecordsBy( "Name", name, "Text" )
                                         .deleteRecord( name, false )
                                         .clearFilters( "", true )
                                         .filterRecordsBy( "Name", name, "Text" )
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( name ) },
                                        		 				   new String[] {name + " External System record"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyDeleteExternalSystemRecordWhenDeletionIsConfirmed() throws Exception {

        String randNumber = driver.getTimestamp();
        
        String name = randNumber + "name";
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";

        log.startTest( "Verify that the External System record is successfully deleted when deletion is confirmed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( name,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .filterRecordsBy( "Name", name, "Text" )
                                         .deleteRecord( name, true )
                                         .clearFilters( "", true )
                                         .filterRecordsBy( "Name", name, "Text" )
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( name) },
                                        		 				   new String[] {name + " External System record"},
                                                                   false );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyDisplayExternalSystemRecordFields() throws Exception {

        log.startTest( "Verify that all the External System record fields are successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .verifyDisplayedElements( new String[]{ elements.extSystemName,
                                                                           elements.extSystemType,
                                                                           elements.extSystemForType,
                                                                           elements.extSystemAuthentication,
                                                                           elements.extSystemAddress},
                                                                   new String[] {"Name input field",
                                        		 								 "System Type dropdown",
                                        		 								 "System for type dropdown",
                                        		 								 "Authentication dropdown",
                                        		 								 "Address input field"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }   
    
    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyDisplayExternalSystemForTypeDropdownOptions() throws Exception {

        log.startTest( "Verify that the 'System for Type' dropdown menu has options depending on selected System Type" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage(this)
                                         .fillExternalSystemRecord("", "EmailProvider", "", "", "", "")
            .verifyDisplayedElements(new String[] {elements.getDropdownOption(elements.extSystemForType, "Send")},
            					 	 new String[] {"Send option"},
            					 	 true).getSystemPage(this)
            .fillExternalSystemRecord("", "CrmProvider", "", "", "", "")
            .verifyDisplayedElements(new String[] {elements.getDropdownOption(elements.extSystemForType, "NotSet"),
            									   elements.getDropdownOption(elements.extSystemForType, "InterAction"),
            									   elements.getDropdownOption(elements.extSystemForType, "Dynamics"),
            									   elements.getDropdownOption(elements.extSystemForType, "Salesforce"),
            									   elements.getDropdownOption(elements.extSystemForType, "Send"),
            									   elements.getDropdownOption(elements.extSystemForType, "Rss")},
				 	 new String[] {"NotSet option",
            					   "InterAction option",
            					   "Dynamics option",
            					   "Salesforce option",
            					   "Send option",
            					   "Rss option"},
				 	 true);
            
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }   

    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyDisplayExternalSystemTypeDropdownValues() throws Exception {

        log.startTest( "Verify that all the values for System Type are successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .verifyDisplayedElements( new String[]{ elements.getDropdownOption( elements.extSystemType,
                                                                                                             "NotSet" ),
                                                                           elements.getDropdownOption( elements.extSystemType,
                                                                                                       "CmsProvider" ),
                                                                           elements.getDropdownOption( elements.extSystemType,
                                                                                                       "CrmProvider" ),
                                                                           elements.getDropdownOption( elements.extSystemType,
                                                                                                       "EmailProvider" ),
                                                                           elements.getDropdownOption( elements.extSystemType,
                                                                                                       "OtherProvider" ) },
                                                                   new String[]{"NotSet dropdown option",
                                        		 								"CmsProvider dropdown option",
								                                        		 "CrmProvider dropdown option",
								                                        		 "EmailProvider dropdown option",
								                                        		 "CmsProvider OtherProvider option"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyDisplayExternalSystemAuthenticationDropdownValues() throws Exception {

        log.startTest( "Verify that all the values for Authentication is successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .verifyDisplayedElements( new String[]{ elements.getDropdownOption( elements.extSystemAuthentication,
                                                                                                             "None" ),
                                                                           elements.getDropdownOption( elements.extSystemAuthentication,
                                                                                                       "Forms" ),
                                                                           elements.getDropdownOption( elements.extSystemAuthentication,
                                                                                                       "Basic" ),
                                                                           elements.getDropdownOption( elements.extSystemAuthentication,
                                                                                                       "Digest" ),
                                                                           elements.getDropdownOption( elements.extSystemAuthentication,
                                                                                                       "Negotiate" ),
                                                                           elements.getDropdownOption( elements.extSystemAuthentication,
                                                                                                       "NTLM" ) },
                                                                   new String[] {"None dropdown option",
                                        		 								 "Forms dropdown option",
                                        		 								 "Basic dropdown option",
                                        		 								 "Digest dropdown option",
                                        		 								 "Negotiate dropdown option",
                                        		 								 "NTLM dropdown option",},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    // System Users page

    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyAccessSystemUsersPage() throws Exception {

        log.startTest( "Verify that the System Users page can be successfully accessed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" ).verifyPageTitle( elements.extSystemPageTitle );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyDisplaySystemUsersTableGrid() throws Exception {

        log.startTest( "Verify that the System users table grid is successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .navigateToSubPage( "System users" )
                                         .verifyDisplayedElements( new String[]{ elements.recordsTableGrid },
                                        		                   new String[] {"System Users table grid"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyDisplaySystemUsersTableColumns() throws Exception {

        log.startTest( "Verify that the System Users table grid columns are successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .navigateToSubPage( "System users" )
                                         .verifyDisplayedElements( new String[]{ elements.getTableHeader( "External System" ),
                                                                           elements.getTableHeader( "Username" ),
                                                                           elements.getTableHeader( "Password" ),
                                                                           elements.getTableHeader( "Permissions" )},
                                                                   new String[] {"External System column",
                                        		 								 "Username column",
                                        		 								 "Password column",
                                        		 								 "Permissions column"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyDisplaySystemUsersRecordEditButton() throws Exception {

        String randNumber = driver.getTimestamp();
        
        String extSysName = randNumber + "name";
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";
        
        String username = randNumber + "username";
        String password = "Aa111111!";

        log.startTest( "Verify that a button for editing System Users record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( extSysName,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .navigateToSubPage( "System users" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( extSysName,
                                        		 							username,
                                        		 							password)
                                         .saveRecord( false )
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( extSysName )
                                                                                 + elements.editRecord },
                                                                   new String[] {"System Users record Edit button"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyDisplaySystemUsersRecordDeleteButton() throws Exception {

        String randNumber = driver.getTimestamp();
        
        String extSysName = randNumber + "name";
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";
        
        String username = randNumber + "username";
        String password = "Aa111111!";

        log.startTest( "Verify that a button for deleting System Users record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( extSysName,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .navigateToSubPage( "System users" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( extSysName,
                                        		 							username,
                                        		 							password)
                                         .saveRecord( false )
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( extSysName )
                                                                                 + elements.deleteRecord },
                                                                   new String[] {"System Users record Delete button"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyDisplaySystemUsersRecordCancelButton() throws Exception {

        String randNumber = driver.getTimestamp();
        
        String extSysName = randNumber + "name";
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";
        
        String username = randNumber + "username";
        String password = "Aa111111!";

        log.startTest( "Verify that a button for canceling System Users record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( extSysName,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .navigateToSubPage( "System users" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( extSysName,
                                        		 							username,
                                        		 							password)
                                         .saveRecord( false )
                                         .startEditingRecord(elements.getRecordRow(extSysName), false, "" )
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( extSysName )
                                                                                 + elements.cancelRecord },
                                                                   new String[] {"System Users record Cancel button"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyDisplaySystemUsersRecordSaveButton() throws Exception {

        String randNumber = driver.getTimestamp();
        
        String extSysName = randNumber + "name";
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";
        
        String username = randNumber + "username";
        String password = "Aa111111!";

        log.startTest( "Verify that a button for saving System Users record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( extSysName,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .navigateToSubPage( "System users" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( extSysName,
                                        		 							username,
                                        		 							password)
                                         .saveRecord( false )
                                         .startEditingRecord(elements.getRecordRow(extSysName), false, "" )
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( extSysName )
                                                                                 + elements.saveRecord },
                                                                   new String[] {"System Users record Save button"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyDisplayExternalSystemUsersAddNewRecordButton() throws Exception {

        log.startTest( "Verify that a button for adding External System User record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .navigateToSubPage( "System users" )
                                         .verifyDisplayedElements( new String[]{ elements.addNewRecord },
                                        		 new String[] {"Add new System users record button"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyDisplaySystemUserRecordFields() throws Exception {

        log.startTest( "Verify that all the External System User record fields are successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .navigateToSubPage( "System users" )
                                         .startCreatingRecord( false )
                                         .verifyDisplayedElements( new String[]{ elements.extSystemIdentityExtSystem,
                                                                           elements.extSystemIdentityUsername,
                                                                           elements.extSystemIdentityPassword},
                                                                   new String[] {"External System dropdown",
                                        		 								 "Username input field",
                                        		 								 "Password input field"},
                                                                   true );            

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyDisplaySystemUserRecordPermissionsWhenCreatingIt() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String extSysName = randNumber + "name";
        String systemType = "EmailProvider";
        String systemForType = "Send";
        String authentication = "ADFS";
        String address = randNumber + "address";
        
        String username = randNumber + "username";
        String password = "Aa111111!";
    	
        log.startTest( "Verify that permissions are successfully displayed for External System Identity during its creation" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Systems" )
            .startCreatingRecord( false )
            .getSystemPage( this )
            .fillExternalSystemRecord( extSysName,
           		 					systemType,
           		 					systemForType,
           		 					authentication,
           		 					address,
                                       "" )
            .saveRecord( false )
            .navigateToSubPage( "System users" )
            .startCreatingRecord( false )
            .getSystemPage( this )
            .fillExternalSystemIdentityRecord( extSysName,
           		 							username,
           		 							password)
            .verifyDisplayedElements(new String[] {elements.getExtSystemUserPermission(ExternalSystemPermission.ALERT.permissionName),
								            	   elements.getExtSystemUserPermission(ExternalSystemPermission.DYNAMICS_CONNECTOR.permissionName),
								            	   elements.getExtSystemUserPermission(ExternalSystemPermission.INTERACTION_CONNECTOR.permissionName)},
            						 new String[] {ExternalSystemPermission.ALERT.permissionName + " permission",
							            		   ExternalSystemPermission.DYNAMICS_CONNECTOR.permissionName + " permission",
							            		   ExternalSystemPermission.INTERACTION_CONNECTOR.permissionName + " permission"},
            						 true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyDisplayValidationMessageSystemUserRecordWithoutExternalSystem()
                                                                                                   throws Exception {

        String randNumber = driver.getTimestamp();
        
        String extSysName = "";
        String username = randNumber + "username";
        String password = "Aa111111!";

        log.startTest( "Verify that a validation message is displayed when External System User record is saved without External System" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .navigateToSubPage( "System users" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( extSysName,
                                        		 							username,
                                        		 							password)
                                         .saveRecord( false )
                                         .verifyDisplayedElements( new String[]{ elements.getValidationMessageRecordField( elements.extSystemIdentityExtSystem,
                                                                                                                           super.requiredFieldTextMsg ) },
                                                                   new String[] {super.requiredFieldTextMsg},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyDisplayValidationMessageSystemUserRecordWithoutUsername()
                                                                                                  throws Exception {

        String randNumber = driver.getTimestamp();
        
        String extSysName = randNumber + "name";
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";
        
        String username = "";
        String password = "Aa111111!";

        log.startTest( "Verify that a validation message is displayed when External System User record is saved without Username" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( extSysName,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .navigateToSubPage( "System users" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( extSysName,
                                        		 							username,
                                        		 							password)
                                         .saveRecord( false )
                                         .verifyDisplayedElements( new String[]{ elements.getValidationMessageRecordField( elements.extSystemIdentityUsername,
                                                                                                                           super.requiredFieldTextMsg ) },
                                                                   new String[] {super.requiredFieldTextMsg},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyDisplayValidationMessageSystemUserRecordWithoutPassword()
                                                                                                  throws Exception {

        String randNumber = driver.getTimestamp();
        
        String extSysName = randNumber + "name";
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";
        
        String username = randNumber + "username";
        String password = "";

        log.startTest( "Verify that a validation message is displayed when External System User record is saved without Password" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( extSysName,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .navigateToSubPage( "System users" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( extSysName,
                                        		 							username,
                                        		 							password)
                                         .saveRecord( false )
                                         .verifyDisplayedElements( new String[]{ elements.getValidationMessageRecordField( elements.extSystemIdentityPassword,
                                                                                                                           super.requiredFieldTextMsg ) },
                                                                   new String[] {super.requiredFieldTextMsg},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyAddSystemUserRecordToGrid() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String extSysName = randNumber + "name";
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";
        
        String username = randNumber + "username";
        String password = "Aa111111!";

        log.startTest( "Verify that a new Systems Users record can be successfully added to the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( extSysName,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .navigateToSubPage( "System users" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( extSysName,
                                        		 							username,
                                        		 							password)
                                         .saveRecord( false )
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( extSysName )
                                                                                 + elements.deleteRecord },
                                                                   new String[] {extSysName + " System Users record"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyDisplayExternalSystemUsersRecordExternalSystemDropdownValue() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String extSysName = randNumber + "name";
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";

        log.startTest( "Verify that External System dropdown is populated with values of created External System record" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( extSysName,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .navigateToSubPage( "System users" )
                                         .startCreatingRecord( false )
            .verifyDisplayedElements(new String[] {elements.getDropdownOption(elements.extSystemIdentityExtSystem, extSysName)},
            						 new String[] {extSysName + " option"},
            						 true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyHideSystemUserRecordPassword() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String extSysName = randNumber + "name";
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";
        
        String username = randNumber + "username";
        String password = "Aa111111!";

        log.startTest( "Verify that after System User record is saved the password is displayed with value of (hidden)" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( extSysName,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .navigateToSubPage( "System users" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( extSysName,
                                        		 							username,
                                        		 							password)
                                         .saveRecord( false );
            
            // TODO: Verification step have to be added after method for getting the property values of the record is implemented

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "systems-tests" })
    public void unsuccessfullyAddSystemUserRecordWhenCancelButtonClicked() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String extSysName = randNumber + "name";
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";
        
        String username = randNumber + "username";
        String password = "Aa111111!";

        log.startTest( "Verify that a new System User record is not added to the grid when Cancel button is clicked" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( extSysName,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .navigateToSubPage( "System users" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( extSysName,
                                        		 							username,
                                        		 							password)
                                         .cancelRecord(false)
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( extSysName ) },
                                        		 				   new String[] {extSysName + " External System record"},
                                                                   false );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyEditSystemUserRecordWhenSaveButtonClicked() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String firstExternalSystem = "firstExtSysName" + randNumber;
        String secondExternalSystem = "secondExtSysName" + randNumber;
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";
              
        String firstExtSystemIdentityUsername = "firstUsername" + randNumber;
        String secondExtSystemIdentityUsername = "secondUsername" + randNumber;
        String password = "Aa111111!";
        
        log.startTest( "Verify that already existing System User record can be successfully edited when click on the Save button" );
        try {
        	
	        	accessPlatformAdminHomePage().navigateToPage( "Systems" )
	            .startCreatingRecord( false )
	            .getSystemPage( this )
	            .fillExternalSystemRecord( firstExternalSystem,
	           		 					systemType,
	           		 					systemForType,
	           		 					authentication,
	           		 					address,
	                                       "" )
	            .saveRecord( false )
	            .startCreatingRecord( false )
	            .getSystemPage( this )
	            .fillExternalSystemRecord( secondExternalSystem,
	           		 					systemType,
	           		 					systemForType,
	           		 					authentication,
	           		 					address,
	                                       "" )
	            .saveRecord( false )
	            .navigateToSubPage( "System users" )
	            .startCreatingRecord( false )
	            .getSystemPage( this )
	            .fillExternalSystemIdentityRecord( firstExternalSystem,
	           		 							firstExtSystemIdentityUsername,
	           		 							password)
	            .saveRecord( false )
	            .startEditingRecord(elements.getRecordRow(firstExternalSystem), false, "")
	            .getSystemPage( this )
	            .fillExternalSystemIdentityRecord( secondExternalSystem,
	           		 							secondExtSystemIdentityUsername,
	           		 							"")
	            .saveRecord( false )
	            .verifyDisplayedElements( new String[]{ elements.getRecordRow( secondExternalSystem ) },
	           		 				   new String[] {secondExternalSystem + " A new Connection record"},
	                                      true )
	            .verifyDisplayedElements( new String[]{ elements.getRecordRow( firstExternalSystem ) },
	       	         				   new String[] {firstExternalSystem + " A new Connection record"},
	       	                              false );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "systems-tests" })
    public void unsuccessfullyEditSystemUserRecordWhenCancelButtonClicked() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String firstExternalSystem = "firstExtSysName" + randNumber;
        String secondExternalSystem = "secondExtSysName" + randNumber;
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";
              
        String firstExtSystemIdentityUsername = "firstUsername" + randNumber;
        String secondExtSystemIdentityUsername = "secondUsername" + randNumber;
        String password = "Aa111111!";
        
        log.startTest( "Verify that already existing System USer record is not updated when click on the Cancel button" );
        try {
        	
	        	accessPlatformAdminHomePage().navigateToPage( "Systems" )
	            .startCreatingRecord( false )
	            .getSystemPage( this )
	            .fillExternalSystemRecord( firstExternalSystem,
	           		 					systemType,
	           		 					systemForType,
	           		 					authentication,
	           		 					address,
	                                       "" )
	            .saveRecord( false )
	            .startCreatingRecord( false )
	            .getSystemPage( this )
	            .fillExternalSystemRecord( secondExternalSystem,
	           		 					systemType,
	           		 					systemForType,
	           		 					authentication,
	           		 					address,
	                                       "" )
	            .saveRecord( false )
	            .navigateToSubPage( "System users" )
	            .startCreatingRecord( false )
	            .getSystemPage( this )
	            .fillExternalSystemIdentityRecord( firstExternalSystem,
	           		 							firstExtSystemIdentityUsername,
	           		 							password)
	            .saveRecord( false )
	            .startEditingRecord(elements.getRecordRow(firstExternalSystem), false, "")
	            .getSystemPage( this )
	            .fillExternalSystemIdentityRecord( secondExternalSystem,
	           		 							secondExtSystemIdentityUsername,
	           		 							"")
	            .cancelRecord(false)
	            .verifyDisplayedElements( new String[]{ elements.getRecordRow( secondExternalSystem ) },
	           		 				   new String[] {secondExternalSystem + " A new Connection record"},
	                                      false )
	            .verifyDisplayedElements( new String[]{ elements.getRecordRow( firstExternalSystem ) },
	       	         				   new String[] {firstExternalSystem + " A new Connection record"},
	       	                              true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "systems-tests" })
    public void unsuccessfullyDeleteSystemUserRecordWhenDeletionIsCanceled() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String extSysName = randNumber + "name";
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";
        
        String username = randNumber + "username";
        String password = "Aa111111!";

        log.startTest( "Verify that a System User record is not deleted when deletion is canceled" );
        try {

	        	accessPlatformAdminHomePage().navigateToPage( "Systems" )
	            .startCreatingRecord( false )
	            .getSystemPage( this )
	            .fillExternalSystemRecord( extSysName,
	           		 					systemType,
	           		 					systemForType,
	           		 					authentication,
	           		 					address,
	                                       "" )
	            .saveRecord( false )
	            .navigateToSubPage( "System users" )
	            .startCreatingRecord( false )
	            .getSystemPage( this )
	            .fillExternalSystemIdentityRecord( extSysName,
	           		 							username,
	           		 							password)
	            .saveRecord( false )
	            .verifyDisplayedElements( new String[]{ elements.getRecordRow( extSysName )
	                                                    + elements.deleteRecord },
	                                      new String[] {extSysName + " System Users record"},
	                                      true )
	             .deleteRecord(extSysName, false)
	             .verifyDisplayedElements( new String[]{ elements.getRecordRow( extSysName ) },
		 				   new String[] {extSysName + " System User record"},
                         true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyDeleteSystemUserRecordWhenDeletionIsConfirmed() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String extSysName = randNumber + "name";
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";
        
        String username = randNumber + "username";
        String password = "Aa111111!";

        log.startTest( "Verify that the System User record is successfully deleted when deletion is confirmed" );
        try {

	        	accessPlatformAdminHomePage().navigateToPage( "Systems" )
	            .startCreatingRecord( false )
	            .getSystemPage( this )
	            .fillExternalSystemRecord( extSysName,
	           		 					systemType,
	           		 					systemForType,
	           		 					authentication,
	           		 					address,
	                                       "" )
	            .saveRecord( false )
	            .navigateToSubPage( "System users" )
	            .startCreatingRecord( false )
	            .getSystemPage( this )
	            .fillExternalSystemIdentityRecord( extSysName,
	           		 							username,
	           		 							password)
	            .saveRecord( false )
	            .verifyDisplayedElements( new String[]{ elements.getRecordRow( extSysName )
	                                                    + elements.deleteRecord },
	                                      new String[] {extSysName + " System Users record"},
	                                      true )
	             .deleteRecord(extSysName, true)
	             .verifyDisplayedElements( new String[]{ elements.getRecordRow( extSysName ) },
		 				   new String[] {extSysName + " System User record"},
                         false );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyDisplayWarningMessageDeleteSystemUserRecord() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String extSysName = randNumber + "name";
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";
        
        String username = randNumber + "username";
        String password = "Aa111111!";

        log.startTest( "Verify that a warning deletion message for System User record is successfully displayed" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Systems" )
								         .startCreatingRecord( false )
								         .getSystemPage( this )
								         .fillExternalSystemRecord( extSysName,
								           		 					systemType,
								           		 					systemForType,
								           		 					authentication,
								           		 					address,
								                                       "" )
								         .saveRecord( false )
								         .navigateToSubPage( "System users" )
								         .startCreatingRecord( false )
								         .getSystemPage( this )
								         .fillExternalSystemIdentityRecord( extSysName,
								           		 							username,
								           		 							password)
								         .saveRecord( false )
                                         .deleteRecord( extSysName, false )
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( extSysName )
								                                                 + elements.editRecord },
								                                   new String[] {"System Users record Edit button"},
								                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyAccessSendIntegratedUsers() throws Exception {

        log.startTest( "Verify that the Send Integrated Users page can be successfully accessed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" ).navigateToSubPage( "Send integrated users" )
            .verifyPageTitle( elements.sendIntegratedUersPageTitle )
            .verifyDisplayedElements( new String[]{ elements.sendIntegratedUsersNoIntegrationPromptMsg,
            										elements.sendIntegratedUsersUpdateAllTokensBtn,
            										elements.getTableHeader( "Username" )},
								      new String[] {elements.sendIntegratedUsersNoIntegrationPromptTxt,
            										"Update all tokens button",
            										"Username"},
								      true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyDisplayConnectionsTableGrid() throws Exception {

        log.startTest( "Verify that the Connections table grid is successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .navigateToSubPage( "Connections" )
                                         .verifyDisplayedElements( new String[]{ elements.recordsTableGrid },
                                        		 				   new String[] {"Connections table grid"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "systems-tests" })
    public void successfullyDisplayConnectionsTableColumns() throws Exception {

        log.startTest( "Verify that the Connections table grid columns are successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .navigateToSubPage( "Connections" )
                                         .verifyDisplayedElements( new String[]{ elements.getTableHeader( "Source" ),
                                                                           elements.getTableHeader( "Destination" ),
                                                                           elements.getTableHeader( "Json Document" ), },
                                                                   new String[] {"Source column",
                                         										 "Destination column",
                                         										 "Json Document column"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    /*@Test(groups = { "all-tests", "systems-tests" }) // Changes were made for Connection page - records can't be edited anymore
    public void successfullyDisplayConnectionRecordEditButton() throws Exception {

        String randNumber = driver.getTimestamp();       

        String firstExternalSystem = "firstExtSysName" + randNumber;
        String secondExternalSystem = "secondExtSysName" + randNumber;
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";
              
        String firstExtSystemIdentityUsername = "firstUsername" + randNumber;
        String secondExtSystemIdentityUsername = "secondUsername" + randNumber;
        String password = "Aa111111!";
        
        String connecitonSource = firstExtSystemIdentityUsername + " (" + firstExternalSystem + ")";
        String connectionDestination = secondExtSystemIdentityUsername + " (" + secondExternalSystem + ")";

        log.startTest( "Verify that a button for editing Connection record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( firstExternalSystem,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( secondExternalSystem,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .navigateToSubPage( "System users" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( firstExternalSystem,
                                        		 							firstExtSystemIdentityUsername,
                                        		 							password)
                                         .saveRecord( false )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( secondExternalSystem,
                                        		 							secondExtSystemIdentityUsername,
                                        		 							password)
                                         .saveRecord( false )
                                         .navigateToSubPage( "Connections" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillConnectionRecord( connecitonSource, connectionDestination, "" )
                                         .saveRecord( false )
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( connecitonSource )
                                                                                 + elements.editRecord },
                                                                   new String[] {"Connection record Edit button"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }*/
    
    //@Test(groups = { "all-tests", "systems-tests" }) // TODO: The steps for connection creation have to be changed because now it can be created only automatically
    public void successfullyDisplayConnectionRecordDeleteButton() throws Exception {

        String randNumber = driver.getTimestamp();
        
        String firstExternalSystem = "firstExtSysName" + randNumber;
        String secondExternalSystem = "secondExtSysName" + randNumber;
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";
              
        String firstExtSystemIdentityUsername = "firstUsername" + randNumber;
        String secondExtSystemIdentityUsername = "secondUsername" + randNumber;
        String password = "Aa111111!";
        
        String connecitonSource = firstExtSystemIdentityUsername + " | " + firstExternalSystem;
        String connectionDestination = secondExtSystemIdentityUsername + " | " + secondExternalSystem;

        log.startTest( "Verify that a button for deleting Connection record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( firstExternalSystem,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( secondExternalSystem,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .navigateToSubPage( "System users" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( firstExternalSystem,
                                        		 							firstExtSystemIdentityUsername,
                                        		 							password)
                                         .saveRecord( false )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( secondExternalSystem,
                                        		 							secondExtSystemIdentityUsername,
                                        		 							password)
                                         .saveRecord( false )
                                         .navigateToSubPage( "Connections" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillConnectionRecord( connecitonSource, connectionDestination, "" )
                                         .saveRecord( false )
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( connecitonSource )
                                                                                 + elements.deleteRecord },
                                                                   new String[] {"Connection record Delete button"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    /*@Test(groups = { "all-tests", "systems-tests" }) // Changes were made for Connection page - records can't be edited anymore
    public void successfullyDisplayConnectionRecordCancelButton() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String firstExternalSystem = "firstExtSysName" + randNumber;
        String secondExternalSystem = "secondExtSysName" + randNumber;
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";
              
        String firstExtSystemIdentityUsername = "firstUsername" + randNumber;
        String secondExtSystemIdentityUsername = "secondUsername" + randNumber;
        String password = "Aa111111!";
        String json = "{json}";
        
        String connecitonSource = firstExtSystemIdentityUsername + " (" + firstExternalSystem + ")";
        String connectionDestination = secondExtSystemIdentityUsername + " (" + secondExternalSystem + ")";

        log.startTest( "Verify that a button for canceling Connection record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( firstExternalSystem,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( secondExternalSystem,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .navigateToSubPage( "System users" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( firstExternalSystem,
                                        		 							firstExtSystemIdentityUsername,
                                        		 							password,
                                        		 							json)
                                         .saveRecord( false )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( secondExternalSystem,
                                        		 							secondExtSystemIdentityUsername,
                                        		 							password,
                                        		 							json)
                                         .saveRecord( false )
                                         .navigateToSubPage( "Connections" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillConnectionRecord( connecitonSource, connectionDestination, "" )
                                         .saveRecord( false )
                                         .startEditingRecord( elements.getRecordRow( connecitonSource ), true, connecitonSource )
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( connecitonSource )
                                                                                 + elements.cancelRecord },
                                                                   new String[] {"Connection record Cancel button"},          
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }*/

   /* @Test(groups = { "all-tests", "systems-tests" }) // Changes were made for Connection page - records can't be edited anymore
    public void successfullyDisplayConnectionRecordSaveButton() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String firstExternalSystem = "firstExtSysName" + randNumber;
        String secondExternalSystem = "secondExtSysName" + randNumber;
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";
              
        String firstExtSystemIdentityUsername = "firstUsername" + randNumber;
        String secondExtSystemIdentityUsername = "secondUsername" + randNumber;
        String password = "Aa111111!";
        String json = "{json}";
        
        String connecitonSource = firstExtSystemIdentityUsername + " (" + firstExternalSystem + ")";
        String connectionDestination = secondExtSystemIdentityUsername + " (" + secondExternalSystem + ")";

        log.startTest( "Verify that a button for saving Connection record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( firstExternalSystem,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( secondExternalSystem,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .navigateToSubPage( "System users" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( firstExternalSystem,
                                        		 							firstExtSystemIdentityUsername,
                                        		 							password,
                                        		 							json)
                                         .saveRecord( false )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( secondExternalSystem,
                                        		 							secondExtSystemIdentityUsername,
                                        		 							password,
                                        		 							json)
                                         .saveRecord( false )
                                         .navigateToSubPage( "Connections" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillConnectionRecord( connecitonSource, connectionDestination, "" )
                                         .saveRecord( false )
                                         .startEditingRecord( elements.getRecordRow( connecitonSource ), true, connecitonSource )
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( connecitonSource )
                                                                                 + elements.saveRecord },
                                                                   new String[] {"Connection record Save button"},  
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }*/

    @Test(groups = { "all-tests", "systems-tests" })
    public void unsuccessfullyDisplayConnectionAddNewRecordButton() throws Exception {

        log.startTest( "Verify that no button for adding Connection record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .navigateToSubPage( "Connections" )
                                         .verifyDisplayedElements( new String[]{ elements.addNewRecord },
                                        		 new String[] {"Add new Connection record button"},
                                                                   false );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    //@Test(groups = { "all-tests", "systems-tests" }) // TODO: The steps for connection creation have to be changed because now it can be created only automatically
    public void successfullyAddConnectionRecordToGrid() throws Exception {

        String randNumber = driver.getTimestamp();
        
        String firstExternalSystem = "firstExtSysName" + randNumber;
        String secondExternalSystem = "secondExtSysName" + randNumber;
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";
              
        String firstExtSystemIdentityUsername = "firstUsername" + randNumber;
        String secondExtSystemIdentityUsername = "secondUsername" + randNumber;
        String password = "Aa111111!";
        
        String connecitonSource = firstExtSystemIdentityUsername + " | " + firstExternalSystem;
        String connectionDestination = secondExtSystemIdentityUsername + " | " + secondExternalSystem;

        log.startTest( "Verify that a new Connection record can be added to the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( firstExternalSystem,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( secondExternalSystem,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .navigateToSubPage( "System users" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( firstExternalSystem,
                                        		 							firstExtSystemIdentityUsername,
                                        		 							password)
                                         .saveRecord( false )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( secondExternalSystem,
                                        		 							secondExtSystemIdentityUsername,
                                        		 							password)
                                         .saveRecord( false )
                                         .navigateToSubPage( "Connections" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillConnectionRecord( connecitonSource, connectionDestination, "" )
                                         .saveRecord( false )
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( connecitonSource ) },
                                        		 				   new String[] {connecitonSource + " Connection record"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    /*@Test(groups = { "all-tests", "systems-tests" }) // Connection records can't be added manually anymore
    public void successfullyDisplayConnectionRecordFields() throws Exception {

        log.startTest( "Verify that all the Connection record fields are successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .navigateToSubPage( "Connections" )
                                         .startCreatingRecord( false )
                                         .verifyDisplayedElements( new String[]{ elements.connectionSource,
                                                                           elements.connectionDestination,
                                                                           elements.connectionJsonDoc },
                                                                   new String[] {"Source dropdown",
                                        		 								 "Destination dropdown",
                                        		 								 "Json Document textfield"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }*/
    
    /*@Test(groups = { "all-tests", "systems-tests" }) // Connection records can't be added manually anymore
    public void successfullyDisplayValidationMessageConnectionRecordWithoutSource() throws Exception {

        String randNumber = driver.getTimestamp();
        
        String firstExternalSystem = "firstExtSysName" + randNumber;
        String secondExternalSystem = "secondExtSysName" + randNumber;
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";
              
        String firstExtSystemIdentityUsername = "firstUsername" + randNumber;
        String secondExtSystemIdentityUsername = "secondUsername" + randNumber;
        String password = "Aa111111!";
        
        String connecitonSource = "";
        String connectionDestination = secondExtSystemIdentityUsername + " | " + secondExternalSystem;

        log.startTest( "Verify that a validation message is displayed when Connection record is saved without Source" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( firstExternalSystem,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( secondExternalSystem,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .navigateToSubPage( "System users" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( firstExternalSystem,
                                        		 							firstExtSystemIdentityUsername,
                                        		 							password)
                                         .saveRecord( false )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( secondExternalSystem,
                                        		 							secondExtSystemIdentityUsername,
                                        		 							password)
                                         .saveRecord( false )
                                         .navigateToSubPage( "Connections" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillConnectionRecord( connecitonSource, connectionDestination, "" )
                                         .saveRecord( false )
                                         .verifyDisplayedElements( new String[]{ elements.getValidationMessageRecordField( elements.connectionSource,
                                                 super.requiredFieldTextMsg ) },
															new String[] {super.requiredFieldTextMsg},
															true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }*/
    
    /*@Test(groups = { "all-tests", "systems-tests" }) // Connection records can't be added manually anymore
    public void successfullyDisplayValidationMessageConnectionRecordWithoutDestination() throws Exception {

        String randNumber = driver.getTimestamp();
        
        String firstExternalSystem = "firstExtSysName" + randNumber;
        String secondExternalSystem = "secondExtSysName" + randNumber;
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";
              
        String firstExtSystemIdentityUsername = "firstUsername" + randNumber;
        String secondExtSystemIdentityUsername = "secondUsername" + randNumber;
        String password = "Aa111111!";
        
        String connecitonSource = firstExtSystemIdentityUsername + " | " + firstExternalSystem ;
        String connectionDestination = "";

        log.startTest( "Verify that a validation message is displayed when Connection record is saved without Destination" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( firstExternalSystem,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( secondExternalSystem,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .navigateToSubPage( "System users" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( firstExternalSystem,
                                        		 							firstExtSystemIdentityUsername,
                                        		 							password)
                                         .saveRecord( false )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( secondExternalSystem,
                                        		 							secondExtSystemIdentityUsername,
                                        		 							password)
                                         .saveRecord( false )
                                         .navigateToSubPage( "Connections" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillConnectionRecord( connecitonSource, connectionDestination, "" )
                                         .saveRecord( false )
                                         .verifyDisplayedElements( new String[]{ elements.getValidationMessageRecordField( elements.connectionDestination,
                                                 super.requiredFieldTextMsg ) },
															new String[] {super.requiredFieldTextMsg},
															true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }*/
    
    /*@Test(groups = { "all-tests", "systems-tests" }) // Connection records can't be added manually anymore
    public void unsuccessfullyAddConnectionRecordWhenCancelButtonClicked() throws Exception {

        String randNumber = driver.getTimestamp();
        
        String firstExternalSystem = "firstExtSysName" + randNumber;
        String secondExternalSystem = "secondExtSysName" + randNumber;
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";
              
        String firstExtSystemIdentityUsername = "firstUsername" + randNumber;
        String secondExtSystemIdentityUsername = "secondUsername" + randNumber;
        String password = "Aa111111!";
        
        String connecitonSource = firstExtSystemIdentityUsername + " | " + firstExternalSystem;
        String connectionDestination = secondExtSystemIdentityUsername + " | " + secondExternalSystem;

        log.startTest( "Verify that a new Connection record is not added to the grid when Cancel button is clicked" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( firstExternalSystem,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( secondExternalSystem,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .navigateToSubPage( "System users" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( firstExternalSystem,
                                        		 							firstExtSystemIdentityUsername,
                                        		 							password)
                                         .saveRecord( false )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( secondExternalSystem,
                                        		 							secondExtSystemIdentityUsername,
                                        		 							password)
                                         .saveRecord( false )
                                         .navigateToSubPage( "Connections" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillConnectionRecord( connecitonSource, connectionDestination, "" )
                                         .cancelRecord(false)
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( connecitonSource ) },
                                        		 				   new String[] {connecitonSource + " Connection record"},
                                                                   false );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }*/
    
    /*@Test(groups = { "all-tests", "systems-tests" }) // Changes were made for Connection page - records can't be edited anymore
    public void successfullyEditConnectionRecordWhenSaveButtonClicked() throws Exception {

        String randNumber = driver.getTimestamp();
        
        String firstExternalSystem = "firstExtSysName" + randNumber;
        String secondExternalSystem = "secondExtSysName" + randNumber;
        String thirdExternalSystem = "thirdExtSysName" + randNumber;
        String fourthExternalSystem = "fourthExtSysName" + randNumber;
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";
              
        String firstExtSystemIdentityUsername = "firstUsername" + randNumber;
        String secondExtSystemIdentityUsername = "secondUsername" + randNumber;
        String thirdExtSystemIdentityUsername = "thirdUsername" + randNumber;
        String fourthExtSystemIdentityUsername = "fourthUsername" + randNumber;
        String password = "Aa111111!";
        String json = "{json}";
        
        String firstConnecitonSource = firstExtSystemIdentityUsername + " (" + firstExternalSystem + ")";
        String firstConnectionDestination = secondExtSystemIdentityUsername + " (" + secondExternalSystem + ")";
        
        String secondConnecitonSource = thirdExtSystemIdentityUsername + " (" + thirdExternalSystem + ")";
        String secondConnectionDestination = fourthExtSystemIdentityUsername + " (" + fourthExternalSystem + ")";

        log.startTest( "Verify that a new Connection record is not added to the grid when Cancel button is clicked" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( firstExternalSystem,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( secondExternalSystem,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( thirdExternalSystem,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( fourthExternalSystem,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .navigateToSubPage( "System users" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( firstExternalSystem,
                                        		 							firstExtSystemIdentityUsername,
                                        		 							password,
                                        		 							json)
                                         .saveRecord( false )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( secondExternalSystem,
                                        		 							secondExtSystemIdentityUsername,
                                        		 							password,
                                        		 							json)
                                         .saveRecord( false )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( thirdExternalSystem,
                                        		 							thirdExtSystemIdentityUsername,
                                        		 							password,
                                        		 							json)
                                         .saveRecord( false )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( fourthExternalSystem,
                                        		 							fourthExtSystemIdentityUsername,
                                        		 							password,
                                        		 							json)
                                         .saveRecord( false )
                                         .navigateToSubPage( "Connections" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillConnectionRecord( firstConnecitonSource, firstConnectionDestination, "" )
                                         .saveRecord(false)
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( firstConnecitonSource ) },
                                        		 				   new String[] {firstConnecitonSource + " Connection record"},
                                                                   true )
                                          .startEditingRecord(elements.getRecordRow( firstConnecitonSource ),
                                        		  false, "").getSystemPage(this)
                                          .fillConnectionRecord( secondConnecitonSource, secondConnectionDestination, "" )
                                          .saveRecord(false)
                                          .verifyDisplayedElements( new String[]{ elements.getRecordRow( secondConnecitonSource ) },
                       		 				   new String[] {secondConnecitonSource + " Connection record"},
                                                  true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }*/
    
   /* @Test(groups = { "all-tests", "systems-tests" }) // Changes were made for Connection page - records can't be edited anymore
    public void unsuccessfullyEditConnectionRecordWhenCancelButtonClicked() throws Exception {

        String randNumber = driver.getTimestamp();
        
        String firstExternalSystem = "firstExtSysName" + randNumber;
        String secondExternalSystem = "secondExtSysName" + randNumber;
        String thirdExternalSystem = "thirdExtSysName" + randNumber;
        String fourthExternalSystem = "fourthExtSysName" + randNumber;
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";
              
        String firstExtSystemIdentityUsername = "firstUsername" + randNumber;
        String secondExtSystemIdentityUsername = "secondUsername" + randNumber;
        String thirdExtSystemIdentityUsername = "thirdUsername" + randNumber;
        String fourthExtSystemIdentityUsername = "fourthUsername" + randNumber;
        String password = "Aa111111!";
        String json = "{json}";
        
        String firstConnecitonSource = firstExtSystemIdentityUsername + " (" + firstExternalSystem + ")";
        String firstConnectionDestination = secondExtSystemIdentityUsername + " (" + secondExternalSystem + ")";
        
        String secondConnecitonSource = thirdExtSystemIdentityUsername + " (" + thirdExternalSystem + ")";
        String secondConnectionDestination = fourthExtSystemIdentityUsername + " (" + fourthExternalSystem + ")";

        log.startTest( "Verify that a new Connection record is not added to the grid when Cancel button is clicked" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( firstExternalSystem,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( secondExternalSystem,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( thirdExternalSystem,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( fourthExternalSystem,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .navigateToSubPage( "System users" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( firstExternalSystem,
                                        		 							firstExtSystemIdentityUsername,
                                        		 							password,
                                        		 							json)
                                         .saveRecord( false )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( secondExternalSystem,
                                        		 							secondExtSystemIdentityUsername,
                                        		 							password,
                                        		 							json)
                                         .saveRecord( false )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( thirdExternalSystem,
                                        		 							thirdExtSystemIdentityUsername,
                                        		 							password,
                                        		 							json)
                                         .saveRecord( false )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( fourthExternalSystem,
                                        		 							fourthExtSystemIdentityUsername,
                                        		 							password,
                                        		 							json)
                                         .saveRecord( false )
                                         .navigateToSubPage( "Connections" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillConnectionRecord( firstConnecitonSource, firstConnectionDestination, "" )
                                         .saveRecord(false)
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( firstConnecitonSource ) },
                                        		 				   new String[] {firstConnecitonSource + " Connection record"},
                                                                   true )
                                          .startEditingRecord(elements.getRecordRow( firstConnecitonSource ),
                                        		  false, "").getSystemPage(this)
                                          .fillConnectionRecord( secondConnecitonSource, secondConnectionDestination, "" )
                                          .cancelRecord()
                                          .verifyDisplayedElements( new String[]{ elements.getRecordRow( secondConnecitonSource ) },
                       		 				   new String[] {secondConnecitonSource + " Connection record"},
                                                  false );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }*/
    
    //@Test(groups = { "all-tests", "systems-tests" }) // TODO: The steps for connection creation have to be changed because now it can be created only automatically
    public void unsuccessfullyDeleteConnectionRecordWhenDeletionIsCanceled() throws Exception {

        String randNumber = driver.getTimestamp();
        
        String firstExternalSystem = "firstExtSysName" + randNumber;
        String secondExternalSystem = "secondExtSysName" + randNumber;
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";
              
        String firstExtSystemIdentityUsername = "firstUsername" + randNumber;
        String secondExtSystemIdentityUsername = "secondUsername" + randNumber;
        String password = "Aa111111!";
        
        String connecitonSource = firstExtSystemIdentityUsername + " | " + firstExternalSystem;
        String connectionDestination = secondExtSystemIdentityUsername + " | " + secondExternalSystem;

        log.startTest( "Verify that a Connection record is not deleted when deletion is canceled" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( firstExternalSystem,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( secondExternalSystem,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .navigateToSubPage( "System users" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( firstExternalSystem,
                                        		 							firstExtSystemIdentityUsername,
                                        		 							password)
                                         .saveRecord( false )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( secondExternalSystem,
                                        		 							secondExtSystemIdentityUsername,
                                        		 							password)
                                         .saveRecord( false )
                                         .navigateToSubPage( "Connections" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillConnectionRecord( connecitonSource, connectionDestination, "" )
                                         .saveRecord( false )     
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( connecitonSource ) },
                                        		 				   new String[] {connecitonSource + " Connection record"},
                                                                   true )
                                         .deleteConnectionRecord(connecitonSource, false)
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( connecitonSource ) },
                      		 				   new String[] {connecitonSource + " Connection record"},
                                                 true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    //@Test(groups = { "all-tests", "systems-tests" }) // TODO: The steps for connection creation have to be changed because now it can be created only automatically
    public void successfullyDeleteConnectionRecordWhenDeletionIsConfirmed() throws Exception {

        String randNumber = driver.getTimestamp();
        
        String firstExternalSystem = "firstExtSysName" + randNumber;
        String secondExternalSystem = "secondExtSysName" + randNumber;
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";
              
        String firstExtSystemIdentityUsername = "firstUsername" + randNumber;
        String secondExtSystemIdentityUsername = "secondUsername" + randNumber;
        String password = "Aa111111!";
        
        String connecitonSource = firstExtSystemIdentityUsername + " | " + firstExternalSystem;
        String connectionDestination = secondExtSystemIdentityUsername + " | " + secondExternalSystem;

        log.startTest( "Verify that a Connection record is successfully deleted when deletion is confirmed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( firstExternalSystem,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemRecord( secondExternalSystem,
                                        		 					systemType,
                                        		 					systemForType,
                                        		 					authentication,
                                        		 					address,
                                                                    "" )
                                         .saveRecord( false )
                                         .navigateToSubPage( "System users" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( firstExternalSystem,
                                        		 							firstExtSystemIdentityUsername,
                                        		 							password)
                                         .saveRecord( false )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillExternalSystemIdentityRecord( secondExternalSystem,
                                        		 							secondExtSystemIdentityUsername,
                                        		 							password)
                                         .saveRecord( false )
                                         .navigateToSubPage( "Connections" )
                                         .startCreatingRecord( false )
                                         .getSystemPage( this )
                                         .fillConnectionRecord( connecitonSource, connectionDestination, "" )
                                         .saveRecord( false )     
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( connecitonSource ) },
                                        		 				   new String[] {connecitonSource + " Connection record"},
                                                                   true )
                                         .deleteConnectionRecord(connecitonSource, true)
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( connecitonSource ) },
                      		 				   new String[] {connecitonSource + " Connection record"},
                                                 false );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }  
    
    //@Test(groups = { "all-tests", "systems-tests" }) // TODO: The steps for connection creation have to be changed because now it can be created only automatically
    public void successfullyDisplayWarningMessageDeleteConnectionRecord() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String firstExternalSystem = "firstExtSysName" + randNumber;
        String secondExternalSystem = "secondExtSysName" + randNumber;
        String systemType = "CrmProvider";
        String systemForType = "Dynamics";
        String authentication = "ADFS";
        String address = randNumber + "address";
              
        String firstExtSystemIdentityUsername = "firstUsername" + randNumber;
        String secondExtSystemIdentityUsername = "secondUsername" + randNumber;
        String password = "Aa111111!";
        
        String connecitonSource = firstExtSystemIdentityUsername + " | " + firstExternalSystem;
        String connectionDestination = secondExtSystemIdentityUsername + " | " + secondExternalSystem;

        log.startTest( "Verify that a warning deletion message for Connection record is successfully displayed" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Systems" )
								         .startCreatingRecord( false )
								         .getSystemPage( this )
								         .fillExternalSystemRecord( firstExternalSystem,
								           		 					systemType,
								           		 					systemForType,
								           		 					authentication,
								           		 					address,
								                                       "" )
								         .saveRecord( false )
								         .startCreatingRecord( false )
								         .getSystemPage( this )
								         .fillExternalSystemRecord( secondExternalSystem,
								           		 					systemType,
								           		 					systemForType,
								           		 					authentication,
								           		 					address,
								                                       "" )
								         .saveRecord( false )
								         .navigateToSubPage( "System users" )
								         .startCreatingRecord( false )
								         .getSystemPage( this )
								         .fillExternalSystemIdentityRecord( firstExternalSystem,
								           		 							firstExtSystemIdentityUsername,
								           		 							password)
								         .saveRecord( false )
								         .startCreatingRecord( false )
								         .getSystemPage( this )
								         .fillExternalSystemIdentityRecord( secondExternalSystem,
								          		 							secondExtSystemIdentityUsername,
								           		 							password)
								         .saveRecord( false )
								         .navigateToSubPage( "Connections" )
								         .startCreatingRecord( false )
								         .getSystemPage( this )
								         .fillConnectionRecord( connecitonSource, connectionDestination, "" )
								         .saveRecord( false )       	
                                         .deleteConnectionRecord( connecitonSource, false );
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
}
