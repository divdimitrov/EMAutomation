package concep.selenium.iaConnector.IAattributes;

import java.util.ArrayList;
import java.util.List;

public class ContactDetails {

    public ContactAddress          contactAddress;
    public AdditionalFields        additionalFields;
    public List<ContactActivities> contactActivities;

    public ContactDetails() {
        contactAddress = new ContactAddress();
        additionalFields = new AdditionalFields();
        contactActivities = new ArrayList<ContactActivities>();
        lastName = new ArrayList<String>();
    }

    public List<String> lastName;
    public String       folderName;

}
