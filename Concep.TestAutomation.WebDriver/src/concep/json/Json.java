package concep.json;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import concep.IA.Api.Contact;
import concep.selenium.Dynamic.Attributes.SurveyAttributes;
import concep.selenium.iaConnector.IAattributes.ContactDetails;
/**
 * @author Crunchify.com
 */

public class Json {
    private Gson    gson;
    private Contact par;

    public Json() {

        par = new Contact();
        gson = new Gson();
    }

    public void saveIt(
                        String fileName,
                        Contact par,
                        boolean isJson ) throws IOException {

        if( isJson ) {

            try (Writer writer = new FileWriter( "iaResults/" + fileName )) {
                Gson gsonCreate = new GsonBuilder().create();
                gsonCreate.toJson( par, writer );
            } catch( IOException e ) {
                e.printStackTrace();
            }
        } else {
            System.out.println( "File not saved." );
        }
    }

    public void saveItToDynamic(
                                 String fileName,
                                 SurveyAttributes sur,
                                 boolean isJson ) throws IOException {

        if( isJson ) {
            try (Writer writer = new FileWriter( "dynamicResults/" + fileName )) {
                Gson gsonCreate = new GsonBuilder().create();
                gsonCreate.toJson( sur, writer );
            } catch( IOException e ) {
                e.printStackTrace();
            }
        } else {
            System.out.println( "File not saved." );
        }
    }

    public void saveItToSFDC(
                              String fileName,
                              concep.selenium.SalesForce.Attibutes.SurveyAttributes sur,
                              boolean isJson ) throws IOException {

        if( isJson ) {
            try (Writer writer = new FileWriter( "sfdcResults/" + fileName )) {
                Gson gsonCreate = new GsonBuilder().create();
                gsonCreate.toJson( sur, writer );
            } catch( IOException e ) {
                e.printStackTrace();
            }
        } else {
            System.out.println( "File not saved." );
        }
    }

    public void saveItToIA(
                            String fileName,
                            ContactDetails iaAttributes,
                            boolean isJson ) {

        if( isJson ) {
            try (Writer writer = new FileWriter( "iaConnectorResults/" + fileName )) {
                Gson gsonCreate = new GsonBuilder().create();
                gsonCreate.toJson( iaAttributes, writer );
            } catch( IOException e ) {
                e.printStackTrace();
            }
        } else {
            System.out.println( "File not saved." );
        }

    }

    public Contact readIt(
                           String iaResultsFileLocation,
                           String fileName ) throws IOException, ClassNotFoundException {

        try {

            for( String line : Files.readAllLines( Paths.get( iaResultsFileLocation + fileName ) ) ) {
                par = gson.fromJson( line, Contact.class );

            }
        } catch( Exception e ) {
            System.out.println( e.getMessage() );
        }
        return par;
    }
}