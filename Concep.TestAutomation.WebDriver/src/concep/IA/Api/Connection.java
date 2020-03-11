package concep.IA.Api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
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
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Connection {

    private String _cookies;
    private String _userName;
    private String _password;
    private String _url;
    public Boolean Connected;

    Connection( String userName,
                String password,
                String url ) {

        // System.setProperty( "http.proxyHost", "localhost" );
        // System.setProperty( "http.proxyPort", "8888" );
        _userName = userName;
        _password = password;

        if( !url.endsWith( "/" ) ) {
            url += "/";
        }
        _url = url;
    }

    public String ExecuteCommand(
                                  String commandName,
                                  Map<String, String> parameters,
                                  Boolean isPost ) throws MalformedURLException, IOException {

        //connecting to the requested method within the api and encode it with the proper encoding
        String url = _url + commandName;
        String charset = "UTF-8";
        String query = "";

        //taking the values within the hash map as a parameters 
        for( String key : parameters.keySet() ) {
            query += String.format( "%s=%s&",
                                    URLEncoder.encode( key, charset ),
                                    URLEncoder.encode( parameters.get( key ), charset ) );
        }

        HttpURLConnection connection = ( HttpURLConnection ) new URL( url ).openConnection();
        connection.setInstanceFollowRedirects( false );
        //checking whether the cookie has a value if not set a value to it
        if( _cookies != null && !_cookies.isEmpty() ) {
            connection.setRequestProperty( "Cookie", _cookies );
        }
        connection.setDoOutput( isPost ); // Triggers POST.
        connection.setRequestProperty( "Accept-Charset", charset );
        connection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded;charset=" + charset );
        connection.setRequestProperty( "Content-Length", Integer.toString( query.getBytes( charset ).length ) );

        //checking whether it's a post or a get request
        if( isPost ) {
            try (OutputStream output = connection.getOutputStream()) {
                output.write( query.getBytes( charset ) ); //Thats the stream thats sending data to the server
            }
        }

        //Here the server is already sending info back to us
        String cookies = connection.getHeaderField( "Set-Cookie" );
        if( cookies != null && !cookies.isEmpty() ) {
            _cookies = cookies.split( ";" )[0];
        }

        //reading the entire xml request
        return readXmlStringFromResponse( connection.getInputStream() );
    }

    private String readXmlStringFromResponse(
                                              InputStream response ) throws IOException,
                                                                    UnsupportedEncodingException {

        int len;
        int size = 1024;
        byte[] buf;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        buf = new byte[size];
        while( ( len = response.read( buf, 0, size ) ) != -1 )
            bos.write( buf, 0, len );

        String outputString = new String( bos.toByteArray(), "UTF-8" );
        return outputString;
    }

    public void login() throws MalformedURLException, IOException {

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put( "accountName", _userName );
        parameters.put( "userPassword", _password );

        ExecuteCommand( "establishSession", parameters, true );

        parameters = new HashMap<String, String>();
        ExecuteCommand( "startPage", parameters, false );
        ExecuteCommand( "home", parameters, false );

        Connected = true;
    }

    public ArrayList<Contact> getFolderListByName(
                                                   String folderName ) throws ParserConfigurationException,
                                                                      SAXException, IOException,
                                                                      XPathExpressionException {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Map<String, String> params = ( new HashMap<String, String>() );
        params.put( "folderName", folderName );
        Document doc = dBuilder.parse( new InputSource( new StringReader( ExecuteCommand( "findFolders",
                                                                                          params,
                                                                                          true ) ) ) );
        XPath xPath = XPathFactory.newInstance().newXPath();

        ArrayList<Contact> contactList = new ArrayList<Contact>();
        String xpath = "/methodResponse/results/result";
        NodeList nodes = ( NodeList ) xPath.evaluate( xpath, doc.getDocumentElement(), XPathConstants.NODESET );
        for( int j = 0; j < nodes.getLength(); j++ ) {
            Contact tempFolder = new Contact();
            xpath = String.format( "/methodResponse/results/result[%s]/folderId/text()", ( j + 1 ) );

            NodeList tempNodes = ( NodeList ) xPath.evaluate( xpath,
                                                              doc.getDocumentElement(),
                                                              XPathConstants.NODESET );
            tempFolder.folderId = tempNodes.item( 0 ).getTextContent();

            xpath = String.format( "/methodResponse/results/result[%s]/folderName/text()", j + 1 );
            tempNodes = ( NodeList ) xPath.evaluate( xpath, doc.getDocumentElement(), XPathConstants.NODESET );
            tempFolder.folderName = tempNodes.item( 0 ).getTextContent();

            contactList.add( tempFolder );
        }

        return contactList;
    }
}
