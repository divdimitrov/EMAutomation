package concep.IA.Api;

import java.util.ArrayList;
import java.util.List;

public class Contact {

    public List<String> answers;
    public List<String> questions;
    public String       folderName;
    public String       folderId;
    public String       lastName;
    public String       contactId;
    public String       contactActivity;
    public String       password;
    public List<String> phoneNumber;

    public Contact() {

        answers = new ArrayList<String>();
        questions = new ArrayList<String>();
        phoneNumber = new ArrayList<String>();
    }
}
