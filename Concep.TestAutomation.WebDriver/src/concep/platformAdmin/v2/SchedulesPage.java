package concep.platformAdmin.v2;

import java.security.InvalidParameterException;

import org.jboss.netty.channel.Channel;
import org.testng.annotations.Test;

import concep.platformAdmin.v2.AlertPage.ChannelAvailableScheduleType;

public class SchedulesPage extends BasePlatformAdmin {

	public enum SchedulePeriod {

        MANUAL("Manual"),
        EVERY_MINUTE("Every Minute"),
        EVERY_3_MINUTES("Every 3 Minutes"),
        EVERY_5_MINUTES	("Every 5 Minutes"),
        EVERY_10_MINUTES("Every 10 Minutes"),
        EVERY_20_MINUTES("Every 20 Minutes"),
        HALF_HOURLY("Half-Hourly"),
        HOURLY("Hourly"),
        DAILY("Daily"),
        WEEKLY("Weekly"),
        MONTHLY("Monthly"),
        CUSTOM("Fortnightly");
        
        public String value;

        private SchedulePeriod( String value) {

            this.value = value;
        }
    }
	
    private SchedulesPage fillScheduledJobRecord(
    											  String services,
                                                  String jobContext,
                                                  String context,
                                                  boolean isEnabled,
                                                  String schedulePeriod ) throws Exception {

    	if( services != "" ) {

            log.startStep( "Select a '" + services + "' from the Services dropdown" );
            driver.waitUntilElementIsClickable(elements.getDropdownOption(elements.schedulesServices, services));
            driver.select( elements.schedulesServices ).selectByVisibleText( services );
            log.endStep();            
        }
    	 
        if( jobContext != "" ) {

            log.startStep( "Select a '" + jobContext + "' from the Job Context dropdown" );
            driver.waitUntilElementIsClickable(elements.getDropdownOption(elements.schedulesJobContext, jobContext));
            driver.select( elements.schedulesJobContext ).selectByVisibleText( jobContext );
            log.endStep();
        }

        if( context != "" ) {

            log.startStep( "Type in '" + context + "' for the Context textarea" );
            driver.clear(elements.schedulesContext);
            driver.type( elements.schedulesContext, context, driver.timeOut );
            log.endStep();
        }

        if( isEnabled && !driver.isChecked(elements.schedulesEnabled)) {

            log.startStep( "Click on the 'Enabled' checkbox" );
            driver.click( elements.schedulesEnabled, driver.timeOut );
            log.endStep();
            
        } else if (!isEnabled && driver.isChecked(elements.schedulesEnabled)) {
        	
        	log.startStep( "Click on the 'Enabled' checkbox" );
            driver.click( elements.schedulesEnabled, driver.timeOut );
            log.endStep();
        }

        if( schedulePeriod != "" ) {

            log.startStep( "Select a '" + schedulePeriod + "' from the Job Context dropdown" );
            driver.waitUntilElementIsClickable(elements.getDropdownOption(elements.schedulesPeriod, schedulePeriod));
            driver.select( elements.schedulesPeriod ).selectByVisibleText( schedulePeriod );
            log.endStep();
        }

        return this;
    }

    @Test(groups = { "all-tests", "schedules-tests" })
    public void successfullyDisplaySchedulesTabMainNavigationFromOtherPages() throws Exception {

        log.startTest( "Verify that 'Schedules' tab from the main navigation is successfully displayed from the other pages" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" ).
            verifyDisplayedElements(new String[] {elements.getPageTab("Schedules")}, 
            						new String[] {"Schedules tab"}, true).
            navigateToPage("Alert").
            verifyDisplayedElements(new String[] {elements.getPageTab("Schedules")}, 
									new String[] {"Schedules tab"}, true).
	        navigateToPage("Schedules").
	        verifyDisplayedElements(new String[] {elements.getPageTab("Schedules")}, 
					new String[] {"Schedules tab"}, true).
			navigateToPage("Logs").
			verifyDisplayedElements(new String[] {elements.getPageTab("Schedules")}, 
					new String[] {"Schedules tab"}, true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "schedules-tests" })
    public void successfullyAccessSchedulesPage() throws Exception {

        log.startTest( "Verify that the Schedules Page can be successfully accessed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Schedules" ).verifyPageTitle( elements.schedulesPageTitle );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "schedules-tests" })
    public void successfullyDisplaySchedulesTableGrid() throws Exception {

        log.startTest( "Verify that the Schedules table grid is successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Schedules" )
                                         .verifyDisplayedElements( new String[]{ elements.recordsTableGrid },
                                        		 				   new String[] {"Schedules table grid"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "schedules-tests" })
    public void successfullyDisplaySchedulesTableColumns() throws Exception {

    	String[] schedulesTableHeaders = {
    			
    			"Services",
    			"Job Context",
    			"Additional information",
    			"Enabled",
    			"Date Created",
    			"Last Run Time",
    			"Schedule Period",
    			"Status"
    	};
    	
        log.startTest( "Verify that all the Schedules table grid columns are successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Schedules" );
            
            for (int i = 0; i < schedulesTableHeaders.length; i++) {
				
            	verifyDisplayedElements(new String[] {elements.getTableHeader( schedulesTableHeaders[i] )},
            							new String[] {schedulesTableHeaders[i] + " column"},
            							true);
			}

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "schedules-tests" })
    public void successfullyDisplayScheduleRecordEditButton() throws Exception {

        String randNumber = driver.getTimestamp();
        
        String key = randNumber + "key";    	
    	String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
        String manualScheduleMarketingList = randNumber + "manual";
        
        log.startTest( "Verify that a button for editing Schedule record exist in the grid" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
            .navigateToSubPage( "Channel/Aoi Editor" ).getAlertPage(new AlertPage())
            .startCreatingChannel()
            .fillChannelRecord(key,
           		 			channelName,
           		 			maxContentAge,
           		 			isSuppressed,
           		 			deliveryTemplateKey,
           		 			emailProviderProfile,
           		 			"",
           		 			domainId,
           		 			crmProviderProfile,
           		 			"",
           		 			true)
           	.startEditingChannel(channelName)
           	.setAvailableSchedule(new String[] {ChannelAvailableScheduleType.MANUAL.schedule},
           						  new String[] {manualScheduleMarketingList},
           						  true)
           	.saveChannelRecord().navigateToPage( "Schedules" )
                                .verifyDisplayedElements( new String[]{ elements.getRecordRow( channelName )
                                                                        + elements.editRecord },
                                                          new String[] {"Schedule record Edit button"},
                                                          true );
        	
        	System.out.println(elements.getRecordRow( channelName )
                    + elements.editRecord);
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "schedules-tests" })
    public void successfullyDisplayScheduleRecordDeleteButton() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String key = randNumber + "key";    	
    	String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
        String manualScheduleMarketingList = randNumber + "manual";

        log.startTest( "Verify that a button for deleting Schedule record exist in the grid" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
            .navigateToSubPage( "Channel/Aoi Editor" ).getAlertPage(new AlertPage())
            .startCreatingChannel()
            .fillChannelRecord(key,
           		 			channelName,
           		 			maxContentAge,
           		 			isSuppressed,
           		 			deliveryTemplateKey,
           		 			emailProviderProfile,
           		 			"",
           		 			domainId,
           		 			crmProviderProfile,
           		 			"",
           		 			true)
           	.startEditingChannel(channelName)
           	.setAvailableSchedule(new String[] {ChannelAvailableScheduleType.MANUAL.schedule},
           						  new String[] {manualScheduleMarketingList},
           						  true)
           	.saveChannelRecord().navigateToPage( "Schedules" )
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( channelName )
                                                                                 + elements.deleteRecord },
                                                                   new String[] {"Schedule record Delete button"},
                                                                   true );            

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "schedules-tests" })
    public void successfullyDisplayScheduleRecordCancelButton() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String key = randNumber + "key";    	
    	String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
        String manualScheduleMarketingList = randNumber + "manual";

        log.startTest( "Verify that a button for cancel Schedule record exist in the grid" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
            .navigateToSubPage( "Channel/Aoi Editor" ).getAlertPage(new AlertPage())
            .startCreatingChannel()
            .fillChannelRecord(key,
           		 			channelName,
           		 			maxContentAge,
           		 			isSuppressed,
           		 			deliveryTemplateKey,
           		 			emailProviderProfile,
           		 			"",
           		 			domainId,
           		 			crmProviderProfile,
           		 			"",
           		 			true)
           	.startEditingChannel(channelName)
           	.setAvailableSchedule(new String[] {ChannelAvailableScheduleType.MANUAL.schedule},
           						  new String[] {manualScheduleMarketingList},
           						  true)
           	.saveChannelRecord().navigateToPage( "Schedules" )
                                         .startEditingRecord(elements.getRecordRow( channelName ), false, "")
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( channelName )
                                                                                 + elements.cancelRecord },
                                                                   new String[] {"Schedule record Cancel button"},
                                                                   true );           

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "schedules-tests" })
    public void successfullyDisplayScheduleRecordSaveButton() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String key = randNumber + "key";    	
    	String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
        String manualScheduleMarketingList = randNumber + "manual";

        log.startTest( "Verify that a button for saving Schedule record exist in the grid" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
            .navigateToSubPage( "Channel/Aoi Editor" ).getAlertPage(new AlertPage())
            .startCreatingChannel()
            .fillChannelRecord(key,
           		 			channelName,
           		 			maxContentAge,
           		 			isSuppressed,
           		 			deliveryTemplateKey,
           		 			emailProviderProfile,
           		 			"",
           		 			domainId,
           		 			crmProviderProfile,
           		 			"",
           		 			true)
           	.startEditingChannel(channelName)
           	.setAvailableSchedule(new String[] {ChannelAvailableScheduleType.MANUAL.schedule},
           						  new String[] {manualScheduleMarketingList},
           						  true)
           	.saveChannelRecord().navigateToPage( "Schedules" )
            .startEditingRecord(elements.getRecordRow( channelName ), false, "")
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( channelName )
                                                                                 + elements.saveRecord },
                                                                   new String[] {"Schedule record Cancel button"},
                                                                   true );           
            
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "schedules-tests" })
    public void unsuccessfullyDisplaySchedulesAddNewRecordButton() throws Exception {

        log.startTest( "Verify that a button for adding Schedule record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Schedules" )
                                         .verifyDisplayedElements( new String[]{ elements.addNewRecord },
                                        		 new String[] {"Add new Schedules record button"},
                                                                   false );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "schedules-tests" })
    public void successfullyAddScheduleRecordToGrid() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String key = randNumber + "key";    	
    	String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
        String manualScheduleMarketingList = randNumber + "manual";

        log.startTest( "Verify that a new Schedule record can be added to the grid" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
            .navigateToSubPage( "Channel/Aoi Editor" ).getAlertPage(new AlertPage())
            .startCreatingChannel()
            .fillChannelRecord(key,
           		 			channelName,
           		 			maxContentAge,
           		 			isSuppressed,
           		 			deliveryTemplateKey,
           		 			emailProviderProfile,
           		 			"",
           		 			domainId,
           		 			crmProviderProfile,
           		 			"",
           		 			true)
           	.startEditingChannel(channelName)
           	.setAvailableSchedule(new String[] {ChannelAvailableScheduleType.MANUAL.schedule},
           						  new String[] {manualScheduleMarketingList},
           						  true)
           	.saveChannelRecord().navigateToPage( "Schedules" )
                                .verifyDisplayedElements( new String[]{ elements.getRecordRow( channelName )},
                                                          new String[] {"Schedule record for channel with name: " + channelName },
                                                          true );    

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "schedules-tests" })
    public void successfullyAddAllAvailableSchedulesForChannel() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String key = randNumber + "key";    	
    	String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
        String manualScheduleMarketingList = randNumber + "manual";
        String dailyScheduleMarketingList = randNumber + "daily";
        String weeklyScheduleMarketingList = randNumber + "weekly";
        String monthlyScheduleMarketingList = randNumber + "monthly";
        String fortnightlyScheduleMarketingList = randNumber + "fortnightly";
        
        String scheduleService = "Concep.Alert";
        String scheduleJobContext = "AlertContext";

        log.startTest( "Verify that a new Schedule record can be added to the grid" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
            .navigateToSubPage( "Channel/Aoi Editor" ).getAlertPage(new AlertPage())
            .startCreatingChannel()
            .fillChannelRecord(key,
           		 			channelName,
           		 			maxContentAge,
           		 			isSuppressed,
           		 			deliveryTemplateKey,
           		 			emailProviderProfile,
           		 			"",
           		 			domainId,
           		 			crmProviderProfile,
           		 			"",
           		 			true)
           	.startEditingChannel(channelName)
           	.setAvailableSchedule(new String[] {ChannelAvailableScheduleType.MANUAL.schedule,
           										ChannelAvailableScheduleType.DAILY.schedule,
           										ChannelAvailableScheduleType.WEEKLY.schedule,
           										ChannelAvailableScheduleType.MONTHLY.schedule,
           										ChannelAvailableScheduleType.FORTNIGHTLY.schedule},
           						  new String[] {manualScheduleMarketingList,
           									    dailyScheduleMarketingList,
           									    weeklyScheduleMarketingList,
           									    monthlyScheduleMarketingList,
           									    fortnightlyScheduleMarketingList},
           						  true)
           	.saveChannelRecord().navigateToPage( "Schedules" )
                                .verifyDisplayedElements( new String[]{ elements.getScheduleRecord(scheduleService, scheduleJobContext, channelName, ChannelAvailableScheduleType.MANUAL.schedule),
                                										elements.getScheduleRecord(scheduleService, scheduleJobContext, channelName, ChannelAvailableScheduleType.DAILY.schedule),
                                										elements.getScheduleRecord(scheduleService, scheduleJobContext, channelName, ChannelAvailableScheduleType.WEEKLY.schedule),
                                										elements.getScheduleRecord(scheduleService, scheduleJobContext, channelName, ChannelAvailableScheduleType.MONTHLY.schedule),
                                										elements.getScheduleRecord(scheduleService, scheduleJobContext, channelName, ChannelAvailableScheduleType.FORTNIGHTLY.schedule)},
                                                          new String[] {"Schedule record for channel with name: " + channelName + " and period: " + ChannelAvailableScheduleType.MANUAL.schedule,
                                										"Schedule record for channel with name: " + channelName + " and period: " + ChannelAvailableScheduleType.DAILY.schedule,
                                										"Schedule record for channel with name: " + channelName + " and period: " + ChannelAvailableScheduleType.WEEKLY.schedule,
                                										"Schedule record for channel with name: " + channelName + " and period: " + ChannelAvailableScheduleType.MONTHLY.schedule,
                                										"Schedule record for channel with name: " + channelName + " and period: " + ChannelAvailableScheduleType.FORTNIGHTLY.schedule},
                                                          true );    

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "schedules-tests" })
    public void successfullyDeleteAllAvailableSchedulesForChannelWhenDeleted() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String key = randNumber + "key";    	
    	String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
        String manualScheduleMarketingList = randNumber + "manual";
        String dailyScheduleMarketingList = randNumber + "daily";
        String weeklyScheduleMarketingList = randNumber + "weekly";
        String monthlyScheduleMarketingList = randNumber + "monthly";
        String fortnightlyScheduleMarketingList = randNumber + "fortnightly";
        
        String scheduleService = "Concep.Alert";
        String scheduleJobContext = "AlertContext";

        log.startTest( "Verify that a new Schedule record can be added to the grid" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
            .navigateToSubPage( "Channel/Aoi Editor" ).getAlertPage(new AlertPage())
            .startCreatingChannel()
            .fillChannelRecord(key,
           		 			channelName,
           		 			maxContentAge,
           		 			isSuppressed,
           		 			deliveryTemplateKey,
           		 			emailProviderProfile,
           		 			"",
           		 			domainId,
           		 			crmProviderProfile,
           		 			"",
           		 			true)
           	.startEditingChannel(channelName)
           	.setAvailableSchedule(new String[] {ChannelAvailableScheduleType.MANUAL.schedule,
           										ChannelAvailableScheduleType.DAILY.schedule,
           										ChannelAvailableScheduleType.WEEKLY.schedule,
           										ChannelAvailableScheduleType.MONTHLY.schedule,
           										ChannelAvailableScheduleType.FORTNIGHTLY.schedule},
           						  new String[] {manualScheduleMarketingList,
           									    dailyScheduleMarketingList,
           									    weeklyScheduleMarketingList,
           									    monthlyScheduleMarketingList,
           									    fortnightlyScheduleMarketingList},
           						  true)
           	.saveChannelRecord().navigateToPage( "Schedules" )
                                .verifyDisplayedElements( new String[]{ elements.getScheduleRecord(scheduleService, scheduleJobContext, channelName, ChannelAvailableScheduleType.MANUAL.schedule),
                                										elements.getScheduleRecord(scheduleService, scheduleJobContext, channelName, ChannelAvailableScheduleType.DAILY.schedule),
                                										elements.getScheduleRecord(scheduleService, scheduleJobContext, channelName, ChannelAvailableScheduleType.WEEKLY.schedule),
                                										elements.getScheduleRecord(scheduleService, scheduleJobContext, channelName, ChannelAvailableScheduleType.MONTHLY.schedule),
                                										elements.getScheduleRecord(scheduleService, scheduleJobContext, channelName, ChannelAvailableScheduleType.FORTNIGHTLY.schedule)},
                                                          new String[] {"Schedule record for channel with name: " + channelName + " and period: " + ChannelAvailableScheduleType.MANUAL.schedule,
                                										"Schedule record for channel with name: " + channelName + " and period: " + ChannelAvailableScheduleType.DAILY.schedule,
                                										"Schedule record for channel with name: " + channelName + " and period: " + ChannelAvailableScheduleType.WEEKLY.schedule,
                                										"Schedule record for channel with name: " + channelName + " and period: " + ChannelAvailableScheduleType.MONTHLY.schedule,
                                										"Schedule record for channel with name: " + channelName + " and period: " + ChannelAvailableScheduleType.FORTNIGHTLY.schedule},
                                                          true )
            .navigateToPage("Alert")
            .navigateToSubPage("Channel/Aoi Editor")
            .getAlertPage(new AlertPage())
            .deleteChannel(channelName, true)
            .verifyDisplayedElements( new String[]{ elements.getRecordRow( channelName )},
                    new String[] {"Schedule record for channel with name: " + channelName},
                    false )
            .navigateToPage("Schedules")
            .verifyDisplayedElements( new String[]{ elements.getScheduleRecord(scheduleService, scheduleJobContext, channelName, ChannelAvailableScheduleType.MANUAL.schedule),
                                										elements.getScheduleRecord(scheduleService, scheduleJobContext, channelName, ChannelAvailableScheduleType.DAILY.schedule),
                                										elements.getScheduleRecord(scheduleService, scheduleJobContext, channelName, ChannelAvailableScheduleType.WEEKLY.schedule),
                                										elements.getScheduleRecord(scheduleService, scheduleJobContext, channelName, ChannelAvailableScheduleType.MONTHLY.schedule),
                                										elements.getScheduleRecord(scheduleService, scheduleJobContext, channelName, ChannelAvailableScheduleType.FORTNIGHTLY.schedule)},
                                                          new String[] {"Schedule record for channel with name: " + channelName + " and period: " + ChannelAvailableScheduleType.MANUAL.schedule,
                                										"Schedule record for channel with name: " + channelName + " and period: " + ChannelAvailableScheduleType.DAILY.schedule,
                                										"Schedule record for channel with name: " + channelName + " and period: " + ChannelAvailableScheduleType.WEEKLY.schedule,
                                										"Schedule record for channel with name: " + channelName + " and period: " + ChannelAvailableScheduleType.MONTHLY.schedule,
                                										"Schedule record for channel with name: " + channelName + " and period: " + ChannelAvailableScheduleType.FORTNIGHTLY.schedule},
                                                          false );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    //@Test(groups = { "all-tests", "schedules-tests" }) // Schedule records can't be created manually. They are generated automatically and the Services field is auto-populated
    public void successfullyDisplayScheduleRecordFields() throws Exception {
    	
        log.startTest( "Verify that all the Schedules record fields are successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Schedules" )
                                         .startCreatingRecord( false )
                                         .verifyDisplayedElements( new String[]{ elements.schedulesServices,
                                                                           elements.schedulesJobContext,
                                                                           elements.schedulesContext,
                                                                           elements.schedulesEnabled,
                                                                           elements.schedulesPeriod },
                                                                   new String[] {"Services dropdown",
                                        		 								 "Job Context dropdown",
                                        		 								 "Context text area",
                                        		 								 "Enabled checkbox",
                                        		 								 "Schedule Period dropdown"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }   
    
    //@Test(groups = { "all-tests", "schedules-tests" }) // Schedule records can't be created manually. They are generated automatically and the Services field is auto-populated
    public void successfullyDisplayScheduleRecordServiceTypeDropdownValues() throws Exception {

    	String[] servicesDropdownValues = {
    			
    			"Concep.Alert",
    			"Concep.Surveys",
    			"Concep.InteractionConnector"
    	};
    	
        log.startTest( "Verify that all the Service values for Schedule record are successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Schedules" )
                                         .startCreatingRecord( false );
            
            for (int i = 0; i < servicesDropdownValues.length; i++) {
				
            	verifyDisplayedElements(new String[] {elements.getDropdownOption(elements.schedulesServices, servicesDropdownValues[i])},
            							new String[] {servicesDropdownValues[i] + " dropdown option"},
            							true);
            }

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    //@Test(groups = { "all-tests", "schedules-tests" }) // Schedule records can't be created manually. They are generated automatically and the Services field is auto-populated
    public void successfullyDisplayScheduleRecordJobContextDropdownValues() throws Exception {

    	String[] servicesDropdownValues = {
    			
    			"Concep.Alert",
    			"Concep.InteractionConnector",
    			"Concep.DynamicsConnector"
    	};
    	
        log.startTest( "Verify that all the Service values for Schedule record are successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Schedules" );
                                                     
            for (int i = 0; i < servicesDropdownValues.length; i++) {
            	
            	startCreatingRecord( false )
                .getSchedulesPage(this);
            	fillScheduledJobRecord(servicesDropdownValues[i],
            				           "",
					 		           "",
					 		           false,
					 		           "");
            	
            	if (servicesDropdownValues[i].equals("Concep.Alert")) {
					
            		verifyDisplayedElements(new String[] {elements.getDropdownOption( elements.schedulesJobContext, "AlertContext" ),
            											  elements.getDropdownOption( elements.schedulesJobContext, "ContentMatcherContext" )},
            								new String[] {"AlertContext option",
            											  "ContentMatcherContext option"},
            								true);
            		
				} else if (servicesDropdownValues[i].equals("Concep.InteractionConnector")) {
					
					verifyDisplayedElements(new String[] {elements.getDropdownOption( elements.schedulesJobContext, "InteractionConnectorHelperJobContext" )},
											new String[] {"InteractionConnectorHelperJobContext option"},
											true);
				} else if (servicesDropdownValues[i].equals("Concep.DynamicsConnector")) {
					
					verifyDisplayedElements(new String[] {elements.getDropdownOption( elements.schedulesJobContext, "DynamicsConnectorHelperJobContext" )},
											new String[] {"DynamicsConnectorHelperJobContext option"},
											true);
				} else {
					
					throw new InvalidParameterException("Invalid parametter is passed through. Parameter name: " + servicesDropdownValues[i]);
				}
            	
            	cancelRecord(false);
			}
                                         
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "schedules-tests" }) 
    public void successfullyDisplayScheduleRecordAllSchedulePeriodDropdownValues() throws Exception {

    	String[] schedulePeriods = {
    			
    			SchedulePeriod.MANUAL.value,
    			SchedulePeriod.EVERY_MINUTE.value,
    			SchedulePeriod.EVERY_3_MINUTES.value,
    			SchedulePeriod.EVERY_5_MINUTES.value,
    			SchedulePeriod.EVERY_10_MINUTES.value,
    			SchedulePeriod.EVERY_20_MINUTES.value,
    			SchedulePeriod.HALF_HOURLY.value,
    			SchedulePeriod.HOURLY.value,
    			SchedulePeriod.DAILY.value,
    			SchedulePeriod.WEEKLY.value,
    			SchedulePeriod.MONTHLY.value,
    	};
    	
    	String randNumber = driver.getTimestamp();
        
        String key = randNumber + "key";    	
    	String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
        String manualScheduleMarketingList = randNumber + "manual";
    	
        log.startTest( "Verify that all the Schedule Period values for Schedule record are successfully displayed" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
            .navigateToSubPage( "Channel/Aoi Editor" ).getAlertPage(new AlertPage())
            .startCreatingChannel()
            .fillChannelRecord(key,
           		 			channelName,
           		 			maxContentAge,
           		 			isSuppressed,
           		 			deliveryTemplateKey,
           		 			emailProviderProfile,
           		 			"",
           		 			domainId,
           		 			crmProviderProfile,
           		 			"",
           		 			true)
           	.startEditingChannel(channelName)
           	.setAvailableSchedule(new String[] {ChannelAvailableScheduleType.MANUAL.schedule},
           						  new String[] {manualScheduleMarketingList},
           						  true)
           	.saveChannelRecord().navigateToPage( "Schedules" )
           	.startEditingRecord(elements.getRecordRow( channelName ), false, "");
            
            for (int i = 0; i < schedulePeriods.length; i++) {
				
            	System.out.println(elements.getDropdownOption(elements.schedulesPeriod, schedulePeriods[i]));
            	verifyDisplayedElements(new String[] {elements.getDropdownOption(elements.schedulesPeriod, schedulePeriods[i])},
            							new String[] {schedulePeriods[i] + " dropdown option"},
            							true);
            }

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }   
    
    //@Test(groups = { "all-tests", "schedules-tests" }) // Schedule records can't be created manually. They are generated automatically and the Services field is auto-populated
    public void unsuccessfullyAddScheduleRecordToGridWhenCancelButtonClicked() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String services = "Concep.Alert";
        String jobContext = "AlertContext";
        String context = "{" + randNumber + "context}";
        boolean isEnabled = true;
        String schedulePeriod = SchedulePeriod.HOURLY.value;

        log.startTest( "Verify that a new Schedule record is not added to the grid when Cancel button is clicked" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Schedules" )
                                         .startCreatingRecord( false )
                                         .getSchedulesPage( this )
                                         .fillScheduledJobRecord(
                                        		 				 services,	
                                        		 				 jobContext,
				                                        		 context,
				                                        		 isEnabled,
				                                        		 schedulePeriod)
                                         .cancelRecord(false)
            .verifyDisplayedElements( new String[]{ elements.getRecordRow( context )},
                                      new String[] {"Schedule record"},
                                      false );             	

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "schedules-tests" })
    public void successfullyDeleteScheduleRecordWhenDeletionIsConfirmed() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String key = randNumber + "key";    	
    	String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
        String manualScheduleMarketingList = randNumber + "manual";

        log.startTest( "Verify that a Schedule record is successfully deleted when deletion is confirmed" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
            .navigateToSubPage( "Channel/Aoi Editor" ).getAlertPage(new AlertPage())
            .startCreatingChannel()
            .fillChannelRecord(key,
           		 			channelName,
           		 			maxContentAge,
           		 			isSuppressed,
           		 			deliveryTemplateKey,
           		 			emailProviderProfile,
           		 			"",
           		 			domainId,
           		 			crmProviderProfile,
           		 			"",
           		 			true)
           	.startEditingChannel(channelName)
           	.setAvailableSchedule(new String[] {ChannelAvailableScheduleType.MANUAL.schedule},
           						  new String[] {manualScheduleMarketingList},
           						  true)
           	.saveChannelRecord().navigateToPage( "Schedules" )
                                         .deleteRecord(channelName, true)
            .verifyDisplayedElements( new String[]{ elements.getRecordRow( channelName )},
                                      new String[] {"Schedule record for channel with name: " + channelName},
                                      false );                	

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "schedules-tests" })
    public void unsuccessfullyDeleteScheduleRecordWhenDeletionIsCanceled() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String key = randNumber + "key";    	
    	String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
        String manualScheduleMarketingList = randNumber + "manual";

        log.startTest( "Verify that a Schedule record is not deleted when deletion is canceled" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
            .navigateToSubPage( "Channel/Aoi Editor" ).getAlertPage(new AlertPage())
            .startCreatingChannel()
            .fillChannelRecord(key,
           		 			channelName,
           		 			maxContentAge,
           		 			isSuppressed,
           		 			deliveryTemplateKey,
           		 			emailProviderProfile,
           		 			"",
           		 			domainId,
           		 			crmProviderProfile,
           		 			"",
           		 			true)
           	.startEditingChannel(channelName)
           	.setAvailableSchedule(new String[] {ChannelAvailableScheduleType.MANUAL.schedule},
           						  new String[] {manualScheduleMarketingList},
           						  true)
           	.saveChannelRecord().navigateToPage( "Schedules" )
                                         .deleteRecord(channelName, false)
            .verifyDisplayedElements( new String[]{ elements.getRecordRow( channelName )},
                                      new String[] {"Schedule record"},
                                      true );                	

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "schedules-tests" })
    public void successfullyEditScheduleRecordWhenSaveButtonClicked() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String key = randNumber + "key";    	
    	String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
        String manualScheduleMarketingList = randNumber + "manual";

        log.startTest( "Verify that a Schedule record can be successfully edited when Save button is clicked" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
            .navigateToSubPage( "Channel/Aoi Editor" ).getAlertPage(new AlertPage())
            .startCreatingChannel()
            .fillChannelRecord(key,
           		 			channelName,
           		 			maxContentAge,
           		 			isSuppressed,
           		 			deliveryTemplateKey,
           		 			emailProviderProfile,
           		 			"",
           		 			domainId,
           		 			crmProviderProfile,
           		 			"",
           		 			true)
           	.startEditingChannel(channelName)
           	.setAvailableSchedule(new String[] {ChannelAvailableScheduleType.MANUAL.schedule},
           						  new String[] {manualScheduleMarketingList},
           						  true)
           	.saveChannelRecord().navigateToPage( "Schedules" )
           	.verifyDisplayedElements( new String[]{ elements.getRecordRow( channelName )
                                                                        + elements.editRecord },
                                                          new String[] {"Schedule record Edit button"},
                                                          true )
                                         .startEditingRecord(elements.getRecordRow(channelName), false, "")
                                         .getSchedulesPage( this )
                                         .fillScheduledJobRecord(
                                        		 				 "",	
                                        		 				 "",
				                                        		 "",
				                                        		 true,
				                                        		 ChannelAvailableScheduleType.FORTNIGHTLY.schedule)
                                         .saveRecord( false )
            .startEditingRecord(elements.getRecordRow(channelName), false, "");
        	
        	log.startStep("Verify that the scheduled job for channel with name: " + channelName + " is successfully enabled");
        	log.endStep(driver.isChecked(elements.schedulesEnabled));
        	
        	log.startStep("Verify that the scheduled job for channel with name: " + channelName + " has updated its schedule period to " + ChannelAvailableScheduleType.FORTNIGHTLY.schedule);
        	log.endStep(driver.isSelected(elements.schedulesPeriod, ChannelAvailableScheduleType.FORTNIGHTLY.schedule));

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "schedules-tests" }) 
    public void unsuccessfullyEditScheduleRecordWhenCancelButtonClicked() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String key = randNumber + "key";    	
    	String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
        String manualScheduleMarketingList = randNumber + "manual";

        log.startTest( "Verify that a Schedule record is not edited when Cancel button is clicked" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
            .navigateToSubPage( "Channel/Aoi Editor" ).getAlertPage(new AlertPage())
            .startCreatingChannel()
            .fillChannelRecord(key,
           		 			channelName,
           		 			maxContentAge,
           		 			isSuppressed,
           		 			deliveryTemplateKey,
           		 			emailProviderProfile,
           		 			"",
           		 			domainId,
           		 			crmProviderProfile,
           		 			"",
           		 			true)
           	.startEditingChannel(channelName)
           	.setAvailableSchedule(new String[] {ChannelAvailableScheduleType.MANUAL.schedule},
           						  new String[] {manualScheduleMarketingList},
           						  true)
           	.saveChannelRecord().navigateToPage( "Schedules" )
           	.verifyDisplayedElements( new String[]{ elements.getRecordRow( channelName )
                                                                        + elements.editRecord },
                                                          new String[] {"Schedule record Edit button"},
                                                          true )
                                         .startEditingRecord(elements.getRecordRow(channelName), false, "")
                                         .getSchedulesPage( this )
                                         .fillScheduledJobRecord(
                                        		 				 "",	
                                        		 				 "",
				                                        		 "",
				                                        		 true,
				                                        		 ChannelAvailableScheduleType.FORTNIGHTLY.schedule)
                                         .cancelRecord( false )
            .startEditingRecord(elements.getRecordRow(channelName), false, "");
        	
        	log.startStep("Verify that the scheduled job for channel with name: " + channelName + " is not enabled");
        	log.endStep(!driver.isChecked(elements.schedulesEnabled));
        	
        	log.startStep("Verify that the scheduled job for channel with name: " + channelName + " hasn't updated its schedule period to " + ChannelAvailableScheduleType.FORTNIGHTLY.schedule);
        	log.endStep(!driver.isSelected(elements.schedulesPeriod, ChannelAvailableScheduleType.FORTNIGHTLY.schedule));    

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "schedules-tests" })
    public void successfullyDisplayScheduleRecordManualJobRunButton() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String key = randNumber + "key";    	
    	String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
        String manualScheduleMarketingList = randNumber + "manual";

        log.startTest( "Verify that a button for manually run job Schedule record exist in the grid" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
            .navigateToSubPage( "Channel/Aoi Editor" ).getAlertPage(new AlertPage())
            .startCreatingChannel()
            .fillChannelRecord(key,
           		 			channelName,
           		 			maxContentAge,
           		 			isSuppressed,
           		 			deliveryTemplateKey,
           		 			emailProviderProfile,
           		 			"",
           		 			domainId,
           		 			crmProviderProfile,
           		 			"",
           		 			true)
           	.startEditingChannel(channelName)
           	.setAvailableSchedule(new String[] {ChannelAvailableScheduleType.MANUAL.schedule},
           						  new String[] {manualScheduleMarketingList},
           						  true)
           	.saveChannelRecord().navigateToPage( "Schedules" )
           	.verifyDisplayedElements( new String[]{ elements.getRecordRow( channelName )
                                                                        + elements.editRecord },
                                                          new String[] {"Schedule record Edit button"},
                                                          true )
                                         .startEditingRecord(elements.getRecordRow(channelName), false, "")
                                         .getSchedulesPage( this )
                                         .fillScheduledJobRecord(
                                        		 				 "",	
                                        		 				 "",
				                                        		 "",
				                                        		 true,
				                                        		 ChannelAvailableScheduleType.FORTNIGHTLY.schedule)
                                         .saveRecord( false )
            .verifyDisplayedElements( new String[]{ elements.getScheduleRecordManuallyJobRunBtn(channelName)},
                                      new String[] {"Schedule record Job Run button"},
                                      true );     

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "schedules-tests" })
    public void successfullyDeleteScheduleRecordWhenDisableAvailableSchedule() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String key = randNumber + "key";    	
    	String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
        String manualScheduleMarketingList = randNumber + "manual";

        log.startTest( "Verify that a Schedule record is successfully deleted when the available schedule for the channel is disabled" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
            .navigateToSubPage( "Channel/Aoi Editor" ).getAlertPage(new AlertPage())
            .startCreatingChannel()
            .fillChannelRecord(key,
           		 			channelName,
           		 			maxContentAge,
           		 			isSuppressed,
           		 			deliveryTemplateKey,
           		 			emailProviderProfile,
           		 			"",
           		 			domainId,
           		 			crmProviderProfile,
           		 			"",
           		 			true)
           	.startEditingChannel(channelName)
           	.setAvailableSchedule(new String[] {ChannelAvailableScheduleType.MANUAL.schedule},
           						  new String[] {manualScheduleMarketingList},
           						  true)
           	.saveChannelRecord().navigateToPage( "Schedules" )
            .verifyDisplayedElements( new String[]{ elements.getRecordRow( channelName )},
                                      new String[] {"Schedule record for channel with name: " + channelName},
                                      true )
            .navigateToPage( "Alert" )
            .navigateToSubPage( "Channel/Aoi Editor" )
            .getAlertPage(new AlertPage())
            .startEditingChannel(channelName)
            .setAvailableSchedule(new String[] {ChannelAvailableScheduleType.MANUAL.schedule},
           						  null,
           						  false)
           	.saveChannelRecord().navigateToPage( "Schedules" )
           	.verifyDisplayedElements( new String[]{ elements.getRecordRow( channelName )},
                                      new String[] {"Schedule record for channel with name: " + channelName},
                                      false );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "schedules-tests" })
    public void successfullyCreateContnetSynchronizerContextScheduleRecordWhenCmsProviderCreated() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String name = randNumber + "name";
        String systemType = "CmsProvider";
        String address = randNumber + "address";
        
        String scheduleService = "Concep.Alert";
        String scheduleJobContext = "ContentSynchronizerContext";
        
        log.startTest( "Verify that a Schedule record of type 'ContentSynchronizerContext' is successfully created when Cms external system type is created" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Systems" )
            .startCreatingRecord( false )
            .getSystemPage( new SystemsPage() )
            .fillExternalSystemRecord( name,
           		 					systemType,
           		 					"",
           		 					"",
           		 					address,
                                       "" )
            .saveRecord( false )
            .filterRecordsBy( "Name", name, "Text" )
            .verifyDisplayedElements( new String[] { elements.getRecordRow( name ) },
           		 				   new String[] { name + " External System record"},
                                      true )
            .navigateToPage( "Schedules" )
            .verifyDisplayedElements( new String[]{ elements.getScheduleRecord(scheduleService, scheduleJobContext, name, null)},
                                      new String[] {"Schedule record for CmsProvider with name: " + name},
                                      true );       	

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "schedules-tests" })
    public void successfullyDeleteContnetSynchronizerContextScheduleRecordWhenCmsProviderDelete() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String name = randNumber + "name";
        String systemType = "CmsProvider";
        String address = randNumber + "address";
        
        String scheduleService = "Concep.Alert";
        String scheduleJobContext = "ContentSynchronizerContext";
        
        log.startTest( "Verify that a Schedule record of type 'ContentSynchronizerContext' is successfully deleted when Cms external system type is deleted" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Systems" )
            .startCreatingRecord( false )
            .getSystemPage( new SystemsPage() )
            .fillExternalSystemRecord( name,
           		 					systemType,
           		 					"",
           		 					"",
           		 					address,
                                       "" )
            .saveRecord( false )
            .filterRecordsBy( "Name", name, "Text" )
            .verifyDisplayedElements( new String[] { elements.getRecordRow( name ) },
           		 				   new String[] { name + " External System record"},
                                      true )
            .navigateToPage( "Schedules" )
            .verifyDisplayedElements( new String[]{ elements.getScheduleRecord(scheduleService, scheduleJobContext, name, null)},
                                      new String[] {"Schedule record for CmsProvider with name: " + name},
                                      true )
            .navigateToPage( "Systems" )
            .filterRecordsBy( "Name", name, "Text" )
            .deleteRecord( name, true )
            .navigateToPage( "Schedules" )
            .verifyDisplayedElements( new String[]{ elements.getScheduleRecord(scheduleService, scheduleJobContext, name, null)},
                                      new String[] {"Schedule record for CmsProvider with name: " + name},
                                      false );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "schedules-tests" })
    public void successfullyDeleteCmsProviderWhenContnetSynchronizerContextScheduleRecordDelete() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String name = randNumber + "name";
        String systemType = "CmsProvider";
        String address = randNumber + "address";
        
        String scheduleService = "Concep.Alert";
        String scheduleJobContext = "ContentSynchronizerContext";
        
        log.startTest( "Verify that Cms external system type is successfully deleted when Schedule record of type 'ContentSynchronizerContext' is deleted" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Systems" )
            .startCreatingRecord( false )
            .getSystemPage( new SystemsPage() )
            .fillExternalSystemRecord( name,
           		 					systemType,
           		 					"",
           		 					"",
           		 					address,
                                       "" )
            .saveRecord( false )
            .filterRecordsBy( "Name", name, "Text" )
            .verifyDisplayedElements( new String[] { elements.getRecordRow( name ) },
           		 				   new String[] { name + " External System record"},
                                      true )
            .navigateToPage( "Schedules" )
            .verifyDisplayedElements( new String[]{ elements.getScheduleRecord(scheduleService, scheduleJobContext, name, null)},
                                      new String[] {"Schedule record for CmsProvider with name: " + name},
                                      true )
            .deleteRecord(name, true)
            .navigateToPage( "Systems" )
            .filterRecordsBy( "Name", name, "Text" )
            .verifyDisplayedElements( new String[] { elements.getRecordRow( name ) },
           		 				   	  new String[] { name + " External System record"},
                                      false );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
}
