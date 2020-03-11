package concep.IA.Api;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class InterAction {

    public Connection connection;
    public Contact    contact;

    public InterAction( String userName,
                        String password,
                        String url ) {

        this.connection = new Connection( userName, password, url );
        contact = new Contact();

    }

    public String getFolderId(
                               String folderName ) throws ParserConfigurationException,
                                                  XPathExpressionException, MalformedURLException,
                                                  IOException, SAXException {

        ArrayList<Contact> folderList = connection.getFolderListByName( folderName );
        for( int i = 0; i < folderList.size(); i++ ) {
            if( ( folderList.get( i ).folderName ).equals( folderName ) ) {
                return folderList.get( i ).folderId;
            }
        }

        return "";
    }

    public void addPerson(
                           String lastName,
                           String primaryEmail,
                           String folderID,
                           boolean isCompany,
                           String companyName ) throws MalformedURLException, IOException {

        Map<String, String> parameters = new HashMap<String, String>();

        parameters = new HashMap<String, String>();
        parameters.put( "folderId", folderID );
        if( isCompany ) {
            parameters.put( "companyName", companyName );
        } else {
            parameters.put( "lastName", lastName );
        }
        parameters.put( "contactVisibilityInd", "3" );
        parameters.put( "businessElectronicAddress", primaryEmail );

        for( String key : parameters.keySet() ) {
            parameters.get( key );
        }
        connection.ExecuteCommand( "addContact", parameters, true );

    }

    public void getPersonAtrributs(
                                    String methodName,
                                    String parm,
                                    String parmValue ) throws ParserConfigurationException,
                                                      XPathExpressionException, MalformedURLException,
                                                      IOException, SAXException {

        Map<String, String> folderList = getResultsNods( methodName, parm, parmValue );
        for( String key : folderList.keySet() ) {
            switch( key ){
            /* case "lastName":
                 contact.questions.add( key );
                 contact.answers.add( folderList.get( key ) );
                 break;
             case "firstName":
                 contact.questions.add( key );
                 contact.answers.add( folderList.get( key ) );
                 break;
             case "formattedElectronicAddress":
                 contact.questions.add( key );
                 contact.answers.add( folderList.get( key ) );
                 break;
             case "companyName":
                 contact.questions.add( key );
                 contact.answers.add( folderList.get( key ) );
                 break;
             case "city":
                 contact.questions.add( key );
                 contact.answers.add( folderList.get( key ) );
                 break;
             case "JobTitle":
                 contact.questions.add( key );
                 contact.answers.add( folderList.get( key ) );
                 break;
             case "country":
                 contact.questions.add( key );
                 contact.answers.add( folderList.get( key ) );
                 break;
             case "state":
                 contact.questions.add( key );
                 contact.answers.add( folderList.get( key ) );
                 break;
             case "additional":
                 contact.questions.add( key );
                 contact.answers.add( folderList.get( key ) );
                 break;*/
                case "contactId":
                    contact.contactId = folderList.get( key );
                    break;
                case "sourceFolderId":
                    contact.folderId = folderList.get( key );
                    break;
                case "folderName":
                    contact.folderName = folderList.get( key );
                    break;
                /* case "summary":
                     contact.contactActivity = folderList.get( key );
                     break;*/
                default:
                    break;
            };

        }
    }

    public Map<String, String> getResultsNods(
                                               String methodName,
                                               String parm,
                                               String parmValue ) throws MalformedURLException, SAXException,
                                                                 IOException, XPathExpressionException,
                                                                 ParserConfigurationException {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Map<String, String> params = new HashMap<String, String>();
        params.put( parm, parmValue );
        Document doc = dBuilder.parse( new InputSource( new StringReader( connection.ExecuteCommand( methodName,
                                                                                                     params,
                                                                                                     true ) ) ) );
        XPath xPath = XPathFactory.newInstance().newXPath();

        Map<String, String> resultList = new HashMap<String, String>();
        String xpath = "/methodResponse/results/result";
        NodeList nodes = ( NodeList ) xPath.evaluate( xpath, doc.getDocumentElement(), XPathConstants.NODESET );
        for( int j = 0; j < nodes.getLength(); j++ ) {
            xpath = String.format( "/methodResponse/results/result[%s]/child::*", j + 1 );

            NodeList tempNodes = ( NodeList ) xPath.evaluate( xpath,
                                                              doc.getDocumentElement(),
                                                              XPathConstants.NODESET );
            for( int i = 0; i < tempNodes.getLength(); i++ ) {
                int nodeLength = tempNodes.item( i ).getChildNodes().getLength();
                int nodeLengthChild = tempNodes.item( i )
                                               .getChildNodes()
                                               .item( 0 )
                                               .getChildNodes()
                                               .getLength();
                // if the results has child nodes to loop thro them and add them to the list.
                if( nodeLength > 1 || nodeLengthChild > 1 ) {
                    for( int k = 0; k < nodeLength; k++ ) {
                        int nodeLength_3 = 0;
                        try {
                            nodeLength_3 = tempNodes.item( i )
                                                    .getChildNodes()
                                                    .item( k )
                                                    .getChildNodes()
                                                    .getLength();
                        } catch( NullPointerException e ) {};
                        if( nodeLength_3 > 1 ) {
                            for( int l = 0; l < nodeLength_3; l++ ) {
                                Node node_l = tempNodes.item( i )
                                                       .getChildNodes()
                                                       .item( k )
                                                       .getChildNodes()
                                                       .item( l );
                                resultList.put( node_l.getNodeName(), node_l.getTextContent() );
                                contact.questions.add( node_l.getNodeName() );
                                contact.answers.add( node_l.getTextContent() );
                                // System.out.println( "this is k node child: " + node_l.getTextContent() );
                                if( node_l.getNodeName() == "phoneNumber" ) {
                                    contact.phoneNumber.add( node_l.getTextContent() );
                                }
                            }
                        } else {
                            Node node_k = tempNodes.item( i ).getChildNodes().item( k );
                            resultList.put( node_k.getNodeName(), node_k.getTextContent() );
                        }
                    }

                } else {
                    resultList.put( tempNodes.item( i ).getNodeName(), tempNodes.item( i ).getTextContent() );
                    contact.questions.add( tempNodes.item( i ).getNodeName() );
                    contact.answers.add( tempNodes.item( i ).getTextContent() );
                    // System.out.println( "this is I child node: " + tempNodes.item( i ).getTextContent() );
                }
            }

        }
        return resultList;

    }

    public void getContactAdditionalFields(
                                            String parmValue ) throws MalformedURLException, SAXException,
                                                              IOException, XPathExpressionException,
                                                              ParserConfigurationException {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Map<String, String> params = new HashMap<String, String>();
        params.put( "contactId", parmValue );
        Document doc = dBuilder.parse( new InputSource( new StringReader( connection.ExecuteCommand( "findAdditionalFields",
                                                                                                     params,
                                                                                                     true ) ) ) );
        XPath xPath = XPathFactory.newInstance().newXPath();

        String xpath = "/methodResponse/results/result";
        NodeList nodes = ( NodeList ) xPath.evaluate( xpath, doc.getDocumentElement(), XPathConstants.NODESET );
        for( int j = 0; j < nodes.getLength(); j++ ) {
            xpath = String.format( "/methodResponse/results/result[%s]/child::*", j + 1 );

            NodeList tempNodes = ( NodeList ) xPath.evaluate( xpath,
                                                              doc.getDocumentElement(),
                                                              XPathConstants.NODESET );
            for( int i = 0; i < tempNodes.getLength(); i++ ) {
                if( tempNodes.item( i ).getNodeName() == "field" ) {
                    contact.questions.add( tempNodes.item( i )
                                                    .getAttributes()
                                                    .getNamedItem( "name" )
                                                    .getNodeValue() );
                    contact.answers.add( tempNodes.item( i ).getTextContent() );
                }
            }
        }
    }

}
