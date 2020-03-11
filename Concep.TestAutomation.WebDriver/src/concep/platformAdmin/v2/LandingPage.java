package concep.platformAdmin.v2;

import org.testng.annotations.Test;

public class LandingPage extends BasePlatformAdmin {

    @Test(groups = { "all-tests", "landing-page-tests" })
    public void successfullyDisplayLandingPageMainNavigationTabs() throws Exception {

        log.startTest( "Verify that all the navigation tabs are successfully displayed from the Platform Admin landing page" );
        try {

            accessPlatformAdminHomePage().verifyDisplayedElements( new String[]{ elements.getPageTab( "Systems" ),
                                                                                 elements.getPageTab( "Alert" ),
                                                                                 elements.getPageTab( "Schedules" ),
                                                                                 elements.getPageTab( "Logs" ) },
                                                                   new String[]{ "Systems navigation tab",
                                                                                 "Alert navigation tab",
                                                                                 "Schedules navigation tab",
                                                                                 "Logs navigation tab" },
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "landing-page-tests" })
    public void successfullyDisplayLandingPageNavigationLinksContainers() throws Exception {

        log.startTest( "Verify that all the page navigation links containers are successfully displayed from the Platform Admin landing page" );
        try {

            accessPlatformAdminHomePage().verifyDisplayedElements( new String[]{ elements.getLandingPageNavigationListContainers( "Connections" ),
                                                                                 elements.getLandingPageNavigationListContainers( "Alert" ),
                                                                                 elements.getLandingPageNavigationListContainers( "Schedules" ),
                                                                                 elements.getLandingPageNavigationListContainers( "Logs" ) },
                                                                   new String[]{ "Connections navigation links container",
                                                                                 "Alert navigation links container",
                                                                                 "Schedules navigation links container",
                                                                                 "Logs navigation links container" },
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "landing-page-tests" })
    public void successfullyNavigateToMainPagesFromMainNavigationTabs() throws Exception {

        log.startTest( "Verify that user is able to access each of the pages from the main navigation" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" )
                                         .verifyDisplayedElements( new String[]{ elements.getPageTitle( elements.extSystemPageTitle ) },
                                                                   new String[]{ "Systems page title" },
                                                                   true )
                                         .browserCommand( "Back" )
                                         .navigateToPage( "Alert" )
                                         .verifyDisplayedElements( new String[]{ elements.getPageTitle( elements.contactPageTitle ) },
                                                                   new String[]{ "Alert page title" },
                                                                   true )
                                         .browserCommand( "Back" )
                                         .navigateToPage( "Schedules" )
                                         .verifyDisplayedElements( new String[]{ elements.getPageTitle( elements.schedulesPageTitle ) },
                                                                   new String[]{ "Schedules page title" },
                                                                   true )
                                         .browserCommand( "Back" )
                                         .navigateToPage( "Logs" )
                                         .verifyDisplayedElements( new String[]{ elements.logsReloadBtn },
                                                                   new String[]{ "Logs page view heading" },
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "landing-page-tests" })
    public void successfullyDisplayLandingPageNavigationLinks() throws Exception {

        log.startTest( "Verify that all the page navigation links are successfully displayed under their relevant container from the Platform Admin landing page" );
        try {

            accessPlatformAdminHomePage().verifyDisplayedElements( new String[]{ elements.getLandingPageNavigationPageLink( "Connections",
                                                                                                                            "Systems" ),
                                                                                 elements.getLandingPageNavigationPageLink( "Connections",
                                                                                                                            "System users" ),
                                                                                 elements.getLandingPageNavigationPageLink( "Connections",
                                                                                                                            "Connections" ),
                                                                                 elements.getLandingPageNavigationPageLink( "Alert",
                                                                                                                            "Content" ),
                                                                                 elements.getLandingPageNavigationPageLink( "Alert",
                                                                                                                            "Contacts" ),
                                                                                 elements.getLandingPageNavigationPageLink( "Alert",
                                                                                                                            "Meta Model" ),
                                                                                 elements.getLandingPageNavigationPageLink( "Alert",
                                                                                                                            "Channel/Aoi Editor" ),
                                                                                 elements.getLandingPageNavigationPageLink( "Alert",
                                                                                                                            "Subscribers" ),
                                                                                 elements.getLandingPageNavigationPageLink( "Alert",
                                                                                                                            "Templates" ),
                                                                                 elements.getLandingPageNavigationPageLink( "Schedules",
                                                                                                                            "Review, add, edit and run scheduled jobs" ),
                                                                                 elements.getLandingPageNavigationPageLink( "Logs",
                                                                                                                            "Monitor system logs" ) },
                                                                   new String[]{ "Systems link",
                                                                                 "System users link",
                                                                                 "Connections link",
                                                                                 "Content link",
                                                                                 "Contacts link",
                                                                                 "Meta Model link",
                                                                                 "Channel/Aoi Editor link",
                                                                                 "Subscribers link",
                                                                                 "Templates link",
                                                                                 "Review, add, edit and run scheduled jobs link",
                                                                                 "Monitor system logs link", },
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "landing-page-tests" })
    public void successfullyNavigateToAllSubpagesFromAdditionalNavigationLinks() throws Exception {

        log.startTest( "Verify that all the subpages from the additional navigation menu ca be successfully accessed by following the links" );
        try {

            accessPlatformAdminHomePage().clikTextHyperlink( elements.getLandingPageNavigationPageLink( "Connections",
                                                                                                        "Systems" ),
                                                             "Systems" )
                                         .verifyDisplayedElements( new String[]{ elements.getPageTitle( elements.extSystemPageTitle ) },
                                                                   new String[]{ "Systems page" },
                                                                   true )
                                         .browserCommand( "Back" )
                                         .clikTextHyperlink( elements.getLandingPageNavigationPageLink( "Connections",
                                                                                                        "System users" ),
                                                             "Systems users" )
                                         .verifyDisplayedElements( new String[]{ elements.getPageTitle( elements.systemUsersPageTitle ) },
                                                                   new String[]{ "Systems users page" },
                                                                   true )
                                         .browserCommand( "Back" )
                                         .clikTextHyperlink( elements.getLandingPageNavigationPageLink( "Connections",
                                                                                                        "Connections" ),
                                                             "Connections" )
                                         .verifyDisplayedElements( new String[]{ elements.getPageTitle( elements.connectionPageTitle ) },
                                                                   new String[]{ "Connections page" },
                                                                   true )
                                         .browserCommand( "Back" )
                                         .clikTextHyperlink( elements.getLandingPageNavigationPageLink( "Alert",
                                                                                                        "Content" ),
                                                             "Content" )
                                         .verifyDisplayedElements( new String[]{ elements.getPageTitle( elements.alertContentPageTitle ) },
                                                                   new String[]{ "Content page" },
                                                                   true )
                                         .browserCommand( "Back" )
                                         .clikTextHyperlink( elements.getLandingPageNavigationPageLink( "Alert",
                                                                                                        "Contacts" ),
                                                             "Contacts" )
                                         .verifyDisplayedElements( new String[]{ elements.getPageTitle( elements.contactPageTitle ) },
                                                                   new String[]{ "Contacts page" },
                                                                   true )
                                         .browserCommand( "Back" )
                                         .clikTextHyperlink( elements.getLandingPageNavigationPageLink( "Alert",
                                                                                                        "Meta Model" ),
                                                             "Meta Model" )
                                         .verifyDisplayedElements( new String[]{ elements.metaModelAddCategory },
                                                                   new String[]{ "Meta Model page" },
                                                                   true )
                                         .browserCommand( "Back" )
                                         .clikTextHyperlink( elements.getLandingPageNavigationPageLink( "Alert",
                                                                                                        "Channel/Aoi Editor" ),
                                                             "Channel/Aoi Editor" )
                                         .verifyDisplayedElements( new String[]{ elements.channelEditorAddChannel },
                                                                   new String[]{ "Channel/Aoi Editor page" },
                                                                   true )
                                         .browserCommand( "Back" )
                                         .clikTextHyperlink( elements.getLandingPageNavigationPageLink( "Alert",
                                                                                                        "Subscribers" ),
                                                             "Subscribers" )
                                         .verifyDisplayedElements( new String[]{ elements.getPageTitle( elements.subscriberPageTitle ) },
                                                                   new String[]{ "Subscribers page" },
                                                                   true )
                                         .browserCommand( "Back" )
                                         .clikTextHyperlink( elements.getLandingPageNavigationPageLink( "Alert",
                                                                                                        "Templates" ),
                                                             "Templates" )
                                         .verifyDisplayedElements( new String[]{ elements.getPageTitle( elements.templatePageTitle ) },
                                                                   new String[]{ "Templates page" },
                                                                   true )
                                         .browserCommand( "Back" )
                                         .clikTextHyperlink( elements.getLandingPageNavigationPageLink( "Schedules",
                                                                                                        "Review, add, edit and run scheduled jobs" ),
                                                             "Review, add, edit and run scheduled jobs" )
                                         .verifyDisplayedElements( new String[]{ elements.getPageTitle( elements.schedulesPageTitle ) },
                                                                   new String[]{ "Schedules page" },
                                                                   true )
                                         .browserCommand( "Back" )
                                         .clikTextHyperlink( elements.getLandingPageNavigationPageLink( "Logs",
                                                                                                        " Monitor system logs" ),
                                                             " Monitor system logs" )
                                         .verifyDisplayedElements( new String[]{ elements.logsAddViewButton },
                                                                   new String[]{ "Logs page" },
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
}
