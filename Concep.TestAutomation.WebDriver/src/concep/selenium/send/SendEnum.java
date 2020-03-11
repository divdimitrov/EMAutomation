package concep.selenium.send;

public class SendEnum {

    public enum QuestionStatus {
        NEW, DRAGANDDROP, EDIT
    }

    public enum MCQuestionAnswerType {
        DEFAULT, MANADATORY, DROPDOWN, OTHERANSWER, MULTIPLEANSWERS
    }

    public enum Theme {
        DEFAULT("Default"), TABLE("Table"), WATER("Water"), BEACH("Beach"), RED("Red"), DISCO("Disco"), SPORT(
                "Sport"), AQUA("Aqua"), FUNKY("Funky");
        public String type;

        private Theme( String type ) {

            this.type = type;
        }
    }

    //SalesForce Enums
    public enum MCcontactFiled {

        STATUS("Status", "Active", "Inactive", "Whats your Status?"), LEADSOURCE("Lead Source", "Other",
                "Partner", "Whats your Lead Source?"), SALUTATION("Salutation", "Mr.", "Ms.",
                "Whats your Salutation?"), ROLE("Role", "Employee", "Influencer", "Whats your Role?"), MARITALSTATUS(
                "Marital Stat", "Single", "Married", "Whats your Martial Status?"), GENDER("Gender", "Male",
                "Female", "Whats your Gender?"), COLORGLOBAL("QA Color GFL", "Orange", "Yellow",
                "Whats your G color?"), COLORLOCAL("QA Color LFL", "Orange", "Yellow", "Whats your L color?"), GENDERGLOBAL(
                "QA Gender GFL", "Male", "Female", "Whats your G Gender?"), GENDERLOCAL("QA Gender LFL",
                "Male", "Female", "Whats your L Gender?");
        public String name;
        public String option_1;
        public String option_2;
        public String question;

        private MCcontactFiled( String name,
                                String option_1,
                                String option_2,
                                String question ) {

            this.name = name;
            this.option_1 = option_1;
            this.option_2 = option_2;
            this.question = question;
        }
    }

    public enum FTcontactField {
        EMAIL("Email", "Whats your Email?"), PRIMARYEMAIL("Primary Email", "Whats your Email?"), LASTNAME("Last Name", "Whats your Last Name?"), FIRSTNAME(
                "First Name", "Whats your First Name?"), FULLNAME("Full Name", "Whats your Full Name?"), COMPANY("Company", "Whats your company?"), ALTEMAIL(
                "alt email", "Whats your Alt Email?"), JOBTITLE("Job Title", "Whats your Job Title"), EMPLOYEEGLOBAL(
                "QA Employee GFT", "Best G Employee?"), EMPLOYEELOCAL("QA Employee LFT", "Best L Employee?"), HOBBYGLOBAL(
                "QA Hobby GFT", "Whats your G Hobby?"), HOBBYLOCAL("QA Hobby LFT", "Whats your L Hobby"), BOUNDARYCHARTESTLF(
                "BoundaryCharTest LF", "Local Field Boundary Char Test"), BOUNDARYCHARTESTGF("BoundaryCharTest GF", "Global Field Boundary Char Test");
        public String conntactFiled;
        public String question;

        private FTcontactField( String contactField,
                                String question ) {

            this.conntactFiled = contactField;
            this.question = question;

        }
    }
}
