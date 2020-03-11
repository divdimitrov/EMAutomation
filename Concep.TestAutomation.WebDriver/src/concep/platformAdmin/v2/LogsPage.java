package concep.platformAdmin.v2;

import org.testng.annotations.Test;

public class LogsPage extends BasePlatformAdmin {

	public enum LogViewOption {

        APPLICATION("application", "Application Name"),
        DATE("date", "Date"),
        ERRORMETHOD	("errorMethod", "Error Method"),
        ERRORSOURCE("errorSource", "Error Source"),
        EXCEPTION("exception", "Exception"),
        LEVEL("level", "Level"),
        MESSAGE("message", "Message"),
        STACKTRACE("stackTrace", "Stack Trace");
        
        public String optionName;
        public String columnName;

        private LogViewOption( String optionName, String columnName) {

            this.optionName = optionName;
            this.columnName = columnName;
        }
    }
	
	private String[] allViewOptions = new String[] {
    		
			LogViewOption.APPLICATION.optionName,
			LogViewOption.DATE.optionName,
			LogViewOption.ERRORMETHOD.optionName,
			LogViewOption.ERRORSOURCE.optionName,
			LogViewOption.EXCEPTION.optionName,
			LogViewOption.LEVEL.optionName,
			LogViewOption.MESSAGE.optionName,
			LogViewOption.STACKTRACE.optionName
	};
	
    private LogsPage createView(
                                 String viewName ) throws Exception {

        if( viewName != "" ) {

            log.startStep( "Type in the view name '" + viewName + "'" );
            driver.type( elements.logsViewNameInput, viewName, driver.timeOut );
            log.endStep();
        }

        log.startStep( "Click on the 'Add view' button" );
        driver.click( elements.logsAddViewButton, driver.timeOut );
        log.endStep();
        
        log.startStep("View added success message is displayed");
        driver.waitUntilElementIsLocated(elements.logsViewSaveMsg, driver.timeOut);
        log.endStep();
        
        driver.waitForAjaxToComplete(driver.timeOut);        

        return this;
    }

    private LogsPage deleteView(
                                 String viewName,
                                 boolean isConfirmed ) throws Exception {

        log.startStep( "Click on the delete button for the view with name '" + viewName + "'" );
        driver.click( elements.getDeleteViewButton( viewName ), driver.timeOut );
        log.endStep();

        if( isConfirmed ) {

            log.startStep( "Confirm the deletion of the category" );
            driver.click( elements.confirmDeletion, driver.timeOut );
            log.endStep();

        } else {

            log.startStep( "Cancel the deletion of the category" );
            driver.click( elements.cancelDeletion, driver.timeOut );
            log.endStep();
        }

        return this;
    }
    
    private LogsPage switchToView(String viewName) throws Exception {
    	
    	log.startStep("Switch to view with name: " + viewName);
    	driver.click(elements.getLogViewLabel(viewName), driver.timeOut);
    	log.endStep();
    	
    	return this;
    }
    
    private LogsPage toggleOptionsVisibility(boolean isCollapse) throws Exception {
    	
    	if (!isCollapse) {
			
    		if (!driver.isElementPresent(elements.logsViewNameInput, driver.timeOut)) {
				
    			log.startStep("Show view options");
            	driver.click(elements.logsShowHideBtn, driver.timeOut);
            	log.endStep();
			}    		
        	
		} else {
			
			if (driver.isElementPresent(elements.logsViewNameInput, driver.timeOut)) {
				
    			log.startStep("Hide view options");
            	driver.click(elements.logsShowHideBtn, driver.timeOut);
            	log.endStep();
			}    		
		}    	
    	
    	return this;
    }
    
    private LogsPage toggleTableColumnOption(String[] optionNames, boolean areVisible ) throws Exception {
    	
    	if (areVisible) {			
    		
    		for (int i = 0; i < optionNames.length; i++) {
				
    			log.startStep("Wait for '" + optionNames[i] + "' checkbox to be located and clickable");
    			driver.waitUntilElementIsLocated(elements.getLogOptionCheckbox(optionNames[i]), driver.timeOut);
    			driver.waitUntilElementIsClickable(elements.getLogOptionCheckbox(optionNames[i]));
    			log.endStep();
    			
    			if (!driver.isChecked(elements.getLogOptionCheckbox(optionNames[i]))) {
    				
        			log.startStep("Enable column with name " + optionNames[i]);        			
    				driver.click(elements.getLogOptionCheckbox(optionNames[i]), driver.timeOut);
    				log.endStep();
				}
			}    		
    		
		} else {
			
			for (int i = 0; i < optionNames.length; i++) {				
				
				log.startStep("Wait for '" + optionNames[i] + "' checkbox to be located and clickable");
    			driver.waitUntilElementIsLocated(elements.getLogOptionCheckbox(optionNames[i]), driver.timeOut);
    			driver.waitUntilElementIsClickable(elements.getLogOptionCheckbox(optionNames[i]));
    			log.endStep();
    			
				if (driver.isChecked(elements.getLogOptionCheckbox(optionNames[i]))) {
    				
					log.startStep("Disable column with name " + optionNames[i]);        			
					driver.click(elements.getLogOptionCheckbox(optionNames[i]), driver.timeOut);
					log.endStep();
				}
			}
		}  	
    	
    	return this;
    }
   
    @Test(groups = { "all-tests", "logs-tests" })
    public void successfullyDisplayLogsTabMainNavigationFromOtherPages() throws Exception {

        log.startTest( "Verify that 'Logs' tab from the main navigation is successfully displayed from the other pages" );
        try {

            accessPlatformAdminHomePage().
            navigateToPage( "Systems" ).
            verifyDisplayedElements(new String[] {elements.getPageTab("Logs")}, 
            						new String[] {"Logs tab"}, true).
            navigateToPage("Alert").
            verifyDisplayedElements(new String[] {elements.getPageTab("Logs")}, 
									new String[] {"Logs tab"}, true).
	        navigateToPage("Schedules").
	        verifyDisplayedElements(new String[] {elements.getPageTab("Logs")}, 
									new String[] {"Logs tab"}, true).
			navigateToPage("Logs").
			verifyDisplayedElements(new String[] {elements.getPageTab("Logs")}, 
									new String[] {"Logs tab"}, true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "logs-tests" })
    public void successfullyAccessLogsPage() throws Exception {

        log.startTest( "Verify that the Logs page can be successfully accessed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Logs" ).
            getLogsPage(this).toggleOptionsVisibility(false).
            verifyDisplayedElements(new String[] {elements.logsViewsHeading},
            						new String[] {"Logs page heading"},
            						true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "logs-tests" })
    public void successfullyDisplayLogsTableGrid() throws Exception {

        log.startTest( "Verify that the Logs table grid is successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Logs" )
                                         .verifyDisplayedElements( new String[]{ elements.logsReordsTable },
                                        		 				   new String[] {"Logs table grid"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "logs-tests" })
    public void successfullyDisplayAllLogTableColumnsWhenOptionEnabled() throws Exception {
    	
    	String[] allLogTableOptions = new String[] {
        		
    			LogViewOption.APPLICATION.optionName,
    			LogViewOption.DATE.optionName,
//    			LogViewOption.ERRORMETHOD.optionName,
//    			LogViewOption.ERRORSOURCE.optionName,
//    			LogViewOption.STACKTRACE.optionName,
    			LogViewOption.EXCEPTION.optionName,
    			LogViewOption.LEVEL.optionName,
    			LogViewOption.MESSAGE.optionName,
    			
    	};
    	
        log.startTest( "Verify that all of the Logs table grid columns are successfully displayed when options are enabled" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Logs" ).getLogsPage(this).
            toggleOptionsVisibility(false).toggleTableColumnOption(allLogTableOptions, true).
            verifyDisplayedElements(new String[] {elements.getTableHeader( LogViewOption.DATE.columnName ),
            									  elements.getTableHeader( LogViewOption.APPLICATION.columnName ),
//            									  elements.getTableHeader( LogViewOption.ERRORMETHOD.columnName ),
//            									  elements.getTableHeader( LogViewOption.ERRORSOURCE.columnName ),
//            									  elements.getTableHeader( LogViewOption.STACKTRACE.columnName),
            									  elements.getTableHeader( LogViewOption.EXCEPTION.columnName),
            									  elements.getTableHeader( LogViewOption.LEVEL.columnName ),
            									  elements.getTableHeader( LogViewOption.MESSAGE.columnName ),
            									  },
            			    		new String[] {LogViewOption.APPLICATION.columnName,
                    				     		  LogViewOption.DATE.columnName ,
//                    							  LogViewOption.ERRORMETHOD.columnName,
//                    							  LogViewOption.ERRORSOURCE.columnName,
//                    							  LogViewOption.STACKTRACE.columnName,
                    						      LogViewOption.EXCEPTION.columnName,
                    						      LogViewOption.LEVEL.columnName,
                    							  LogViewOption.MESSAGE.columnName,
                    							   },
            						true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "logs-tests" })
    public void successfullyDisplayPageElementsRelatedToCreationOfNewView() throws Exception {

        log.startTest( "Verify that the Logs table grid is successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Logs" )
            							 .getLogsPage(this).toggleOptionsVisibility(false)
                                         .verifyDisplayedElements( new String[]{ elements.logsViewNameInput,
                                        		 							     elements.logsAddViewButton},
                                        		 				   new String[] {"Add New View input field",
                                        		 								 "Add View button"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "logs-tests" })
    public void successfullyDisplayAllViewOptionCheckboxes() throws Exception {
    	    	
        log.startTest( "Verify that all the view option checkboxes are successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Logs" ).getLogsPage(this).
			                             toggleOptionsVisibility(false).verifyDisplayedElements(new String[] {elements.getLogOptionCheckbox( LogViewOption.APPLICATION.optionName ),
																	  		    elements.getLogOptionCheckbox( LogViewOption.DATE.optionName ),
																			    elements.getLogOptionCheckbox( LogViewOption.EXCEPTION.optionName),
																			    elements.getLogOptionCheckbox( LogViewOption.LEVEL.optionName ),
																			    elements.getLogOptionCheckbox( LogViewOption.MESSAGE.optionName ),
																			    },
												  				  new String[] {LogViewOption.APPLICATION.optionName + " option checkbox",
							            		 								LogViewOption.DATE.optionName + " option checkbox" ,
																			    
																		        LogViewOption.EXCEPTION.optionName + " option checkbox",
																		        LogViewOption.LEVEL.optionName + " option checkbox",
																			    LogViewOption.MESSAGE.optionName + " option checkbox",
																			     },
																  true);
                        
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "logs-tests" })
    public void successfullyCheckUncheckAllLogsViewOptions() throws Exception {
    	        
    	String[] allLogTableOptions = new String[] {
        		
    			LogViewOption.APPLICATION.optionName,
    			LogViewOption.DATE.optionName,
    			//LogViewOption.ERRORMETHOD.optionName,
    			//LogViewOption.ERRORSOURCE.optionName,
    			LogViewOption.EXCEPTION.optionName,
    			LogViewOption.LEVEL.optionName,
    			LogViewOption.MESSAGE.optionName,
    			//LogViewOption.STACKTRACE.optionName
    	};

        log.startTest( "Verify that user is able to check/uncheck all logs view options" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Logs" ).getLogsPage(this)
            .toggleOptionsVisibility(false).toggleTableColumnOption(allLogTableOptions, true)
			.verifyDisplayedElements(new String[] {elements.getTableHeader( LogViewOption.DATE.columnName ),
					elements.getTableHeader( LogViewOption.APPLICATION.columnName ),
					elements.getTableHeader( LogViewOption.LEVEL.columnName ),
					elements.getTableHeader( LogViewOption.MESSAGE.columnName ),
					elements.getTableHeader( LogViewOption.EXCEPTION.columnName),
					  //elements.getTableHeader( LogViewOption.ERRORMETHOD.columnName ),
					  //elements.getTableHeader( LogViewOption.ERRORSOURCE.columnName ),
					 // elements.getTableHeader( LogViewOption.STACKTRACE.columnName),
					  },
			new String[] {LogViewOption.APPLICATION.columnName,
					  LogViewOption.DATE.columnName ,
					 // LogViewOption.ERRORMETHOD.columnName,
					 // LogViewOption.ERRORSOURCE.columnName,
					  //LogViewOption.STACKTRACE.columnName,
				      LogViewOption.EXCEPTION.columnName,
				      LogViewOption.LEVEL.columnName,
					  LogViewOption.MESSAGE.columnName,
					   },
			true)
			.getLogsPage(this)
			.toggleTableColumnOption(allLogTableOptions, false)
			.verifyDisplayedElements(new String[] {elements.getTableHeader( LogViewOption.APPLICATION.columnName ),
					  elements.getTableHeader( LogViewOption.DATE.columnName ),
					  elements.getTableHeader( LogViewOption.EXCEPTION.columnName),
					  elements.getTableHeader( LogViewOption.LEVEL.columnName ),
					  elements.getTableHeader( LogViewOption.MESSAGE.columnName ),
//					  elements.getTableHeader( LogViewOption.ERRORMETHOD.columnName ),
//					  elements.getTableHeader( LogViewOption.ERRORSOURCE.columnName ),
//					  elements.getTableHeader( LogViewOption.STACKTRACE.columnName),
					  },
			new String[] {LogViewOption.APPLICATION.columnName,
					  LogViewOption.DATE.columnName ,
//					  LogViewOption.ERRORMETHOD.columnName,
//					  LogViewOption.ERRORSOURCE.columnName,
//					  LogViewOption.STACKTRACE.columnName,
				      LogViewOption.EXCEPTION.columnName,
				      LogViewOption.LEVEL.columnName,
					  LogViewOption.MESSAGE.columnName,
					   },
			false);
            
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
   
    
    @Test(groups = { "all-tests", "logs-tests" })
    public void successfullyDisplayAllButonsLogsPage() throws Exception {
    	        
        log.startTest( "Verify that all the buttons are successfully displayed on the Logs Page" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Logs" ).getLogsPage(this)
            .verifyDisplayedElements(new String[] {elements.logsReloadBtn,
            									   elements.logsStopAutorefresh,
            									   elements.logsClearFilters,
            									   elements.logsExportCSV,
            									   elements.logsShowHideBtn},
            						 new String[] {"Reload button",
            									   "Stop Autorefresh button",
            									   "Clear filters button",
            									   "Export to CSV button",
            									   "View show-hide button"},
            						 true);            
            
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }   
}
