package concep.selenium.iaConnector;

import concep.selenium.iaConnector.IAattributes.ContactActivities;

public class IAActivities {

    public String defualtRegarding = "Contacts";

    public ContactActivities addActivity(
                                              Activities activity,
                                              String regarding ) {

        ContactActivities contactActivity = new ContactActivities();
        contactActivity.activityTypeName = activity.type;
        contactActivity.summary = activity.summary;
        contactActivity.notes = activity.notes;
        contactActivity.regarding = regarding;

        return contactActivity;

    }

    public enum Activities {

        EMAILSENT("EmailSent", "", ""), EMAILCLICK("EmailSent", "", "");

        public String type;
        public String summary;
        public String notes;

        private Activities( String type,
                            String summary,
                            String notes ) {
            this.type = type;
            this.summary = summary;
            this.notes = notes;
        }

    }

}
