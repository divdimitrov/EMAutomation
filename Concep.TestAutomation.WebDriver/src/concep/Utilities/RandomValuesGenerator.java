package concep.Utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class RandomValuesGenerator {

    private final String dateTimeFormat = "ddMMyy-HHmmss";

    private String getDateTime(
                                String dateTimeFormat ) {

        Date dateTime = new Date();
        SimpleDateFormat simpleFormat = new SimpleDateFormat( dateTimeFormat );

        return simpleFormat.format( dateTime );
    }

    private String getRandomNumber(
                                    int size ) {

        String number = "0";
        Random random = new Random();
        for( int i = 0; i < size; i++ ) {
            number = number + Integer.toString( random.nextInt( 9 ) );
        }

        return number;
    }

    public boolean getRandomBoolean() {

        Random random = new Random();
        return random.nextBoolean();
    }

    public int getRandomArrayIndexValue(
                                         int arraySize ) {

        Random random = new Random();
        int number = random.nextInt( arraySize );
        return number;
    }

    public String getTodaysDate() {

        Date dateTime = new Date();
        SimpleDateFormat simpleFormat = new SimpleDateFormat( "M/d/yyyy" );

        return simpleFormat.format( dateTime );
    }

    public String getTSEmail() {

        return this.getDateTime( dateTimeFormat ) + "QA@mailinator.com";
    }

    public String getTSFirstname() {

        return this.getDateTime( dateTimeFormat ) + "firstname";
    }

    public String getTSLastname() {

        return this.getDateTime( dateTimeFormat ) + "lastname";
    }

    public String getTSUsername() {

        return this.getDateTime( dateTimeFormat ) + "username";
    }

    public String getPassword() {

        return this.getRandomNumber( 7 ) + "C0ncep!";
    }

    public String getTSCustomName(
                                   String name ) {

        return this.getDateTime( dateTimeFormat ) + name;
    }
    
    public String getTSAddress() {
    	
    	return "http://" + this.getDateTime( dateTimeFormat ) + "address/TestAPI/";
    }

    public String getPhoneNumber() {

        return this.getRandomNumber( 10 );
    }   
}
