package concep.selenium.send;

import concep.selenium.send.SendEnum.FTcontactField;
import concep.selenium.send.SendEnum.MCcontactFiled;
import concep.selenium.send.SendEnum.QuestionStatus;

public class SfdcQuestions extends Questions<SfdcQuestions> {

    public SfdcQuestions editFTQAndMapItToEmail(
                                                 boolean isManadatory ) throws Exception {

        addOrUpdateFreeTextQuestion( QuestionStatus.EDIT, FTcontactField.EMAIL, false, isManadatory );

        return this;
    }

    public SfdcQuestions addFTQAndMapItToEmail(
                                                boolean isManadatory ) throws Exception {

        addOrUpdateFreeTextQuestion( QuestionStatus.NEW, FTcontactField.EMAIL, false, isManadatory );

        return this;
    }

    public SfdcQuestions dragAndDropFTQAndMapItToEmail(
                                                        boolean isManadatory ) throws Exception {

        addOrUpdateFreeTextQuestion( QuestionStatus.DRAGANDDROP, FTcontactField.EMAIL, false, isManadatory );

        return this;
    }

    public SfdcQuestions editFTQAndMapItToLastName(
                                                    boolean isManadatory ) throws Exception {

        addOrUpdateFreeTextQuestion( QuestionStatus.EDIT, FTcontactField.LASTNAME, false, isManadatory );

        return this;
    }

    public SfdcQuestions addFTQAndMapItToLastName(
                                                   boolean isManadatory ) throws Exception {

        addOrUpdateFreeTextQuestion( QuestionStatus.NEW, FTcontactField.LASTNAME, false, isManadatory );

        return this;
    }

    public SfdcQuestions dragAndDropFTQAndMapItToLastName(
                                                           boolean isManadatory ) throws Exception {

        addOrUpdateFreeTextQuestion( QuestionStatus.DRAGANDDROP, FTcontactField.LASTNAME, false, isManadatory );

        return this;
    }

    public SfdcQuestions editFTQAndMapItToFirstName(
                                                     boolean isManadatory ) throws Exception {

        addOrUpdateFreeTextQuestion( QuestionStatus.EDIT, FTcontactField.FIRSTNAME, false, isManadatory );

        return this;
    }

    public SfdcQuestions addFTQAndMapItToFirstName(
                                                    boolean isManadatory ) throws Exception {

        addOrUpdateFreeTextQuestion( QuestionStatus.NEW, FTcontactField.FIRSTNAME, false, isManadatory );

        return this;
    }

    public SfdcQuestions dragAndDropFTQAndMapItToFirstName(
                                                            boolean isManadatory ) throws Exception {

        addOrUpdateFreeTextQuestion( QuestionStatus.DRAGANDDROP,
                                     FTcontactField.FIRSTNAME,
                                     false,
                                     isManadatory );

        return this;
    }

    public SfdcQuestions addMCQStatusOptions() throws Exception {

        log.startStep( "Map the 1st answer to the status option 'Sent'" );
        driver.select( "//ul[@id='qAnswerList']/li[1]//select[@id[starts-with(.,'answerStatus')]]" )
              .selectByVisibleText( "Sent" );
        log.endStep();

        log.startStep( "Map the 2nd answer to the status option 'Responded'" );
        driver.select( "//ul[@id='qAnswerList']/li[2]//select[@id[starts-with(.,'answerStatus')]]" )
              .selectByVisibleText( "Responded" );
        log.endStep();

        return this;
    }

    public SfdcQuestions mapMCQuestionToContactField(
                                                      String contactFieldMC,
                                                      String contactOption1,
                                                      String contactOption2 ) throws Exception {

        mapQuestionToContactField( contactFieldMC, element.sfdcContactFieldMCid );

        log.startStep( "Map the 1st answer to the contact option '" + contactOption1 + "'" );
        driver.select( "//ul[@id='qAnswerList']/li[1]//select[@id[starts-with(.,'answerContact')]]" )
              .selectByVisibleText( contactOption1 );
        log.endStep();

        log.startStep( "Map the 2nd answer to the contact option '" + contactOption2 + "'" );
        driver.select( "//ul[@id='qAnswerList']/li[2]//select[@id[starts-with(.,'answerContact')]]" )
              .selectByVisibleText( contactOption2 );
        log.endStep();
        return this;
    }

    public SfdcQuestions mapMcQuestionToLeadField(
                                                   String leadFieldMC,
                                                   String leadOption1,
                                                   String leadOption2 ) throws Exception {

        mapQuestionToContactField( leadFieldMC, element.sfdcLeadFieldMCid );

        log.startStep( "Map the 1st answer to the lead option '" + leadOption1 + "'" );
        driver.select( "//ul[@id='qAnswerList']/li[1]//select[@id[starts-with(.,'answerLead')]]" )
              .selectByVisibleText( leadOption1 );
        log.endStep();

        log.startStep( "Map the 2nd answer to the lead option '" + leadOption2 + "'" );
        driver.select( "//ul[@id='qAnswerList']/li[2]//select[@id[starts-with(.,'answerLead')]]" )
              .selectByVisibleText( leadOption2 );
        log.endStep();
        return this;
    }

    public SfdcQuestions addOrUpdateMCQuestion(
                                                boolean isLead,
                                                SendEnum.QuestionStatus questionStatus,
                                                SendEnum.MCcontactFiled contactField ) throws Exception {

        addOrUpdateMCQuestionBy( contactField.question, questionStatus );
        mapMCQuestionToContactField( contactField.name, contactField.option_1, contactField.option_2 );
        fillinMCQAnswers( contactField.option_1, contactField.option_2 );
        addMCQStatusOptions();
        if( isLead ) {
            mapMcQuestionToLeadField( contactField.name, contactField.option_1, contactField.option_2 );
        }

        updateQuestion();
        return this;
    }

    public SfdcQuestions editMCquestionAndMapItToStatus(
                                                         boolean isLead ) throws Exception {

        addOrUpdateMCQuestion( isLead, QuestionStatus.EDIT, MCcontactFiled.STATUS );
        return this;
    }

    public SfdcQuestions addMCquestionAndMapItToStatus(
                                                        boolean isLead ) throws Exception {

        addOrUpdateMCQuestion( isLead, QuestionStatus.NEW, MCcontactFiled.STATUS );
        return this;
    }

    public SfdcQuestions dragAndDropMCquestionAndMapItToStatus(
                                                                boolean isLead ) throws Exception {

        addOrUpdateMCQuestion( isLead, QuestionStatus.DRAGANDDROP, MCcontactFiled.STATUS );
        return this;
    }

    public SfdcQuestions editMCquestionAndMapItToSalutation(
                                                             boolean isLead ) throws Exception {

        addOrUpdateMCQuestion( isLead, QuestionStatus.EDIT, MCcontactFiled.SALUTATION );
        return this;
    }

    public SfdcQuestions addMCquestionAndMapItToSalutation(
                                                            boolean isLead ) throws Exception {

        addOrUpdateMCQuestion( isLead, QuestionStatus.NEW, MCcontactFiled.SALUTATION );
        return this;
    }

    public SfdcQuestions dragAndDropMCquestionAndMapItToSalutation(
                                                                    boolean isLead ) throws Exception {

        addOrUpdateMCQuestion( isLead, QuestionStatus.DRAGANDDROP, MCcontactFiled.SALUTATION );
        return this;
    }

    public SfdcQuestions editMCquestionAndMapItToLeadSource(
                                                             boolean isLead ) throws Exception {

        addOrUpdateMCQuestion( isLead, QuestionStatus.EDIT, MCcontactFiled.LEADSOURCE );
        return this;
    }

    public SfdcQuestions addMCquestionAndMapItToLeadSource(
                                                            boolean isLead ) throws Exception {

        addOrUpdateMCQuestion( isLead, QuestionStatus.NEW, MCcontactFiled.LEADSOURCE );
        return this;
    }

    public SfdcQuestions dragAndDropMCquestionAndMapItToLeadSource(
                                                                    boolean isLead ) throws Exception {

        addOrUpdateMCQuestion( isLead, QuestionStatus.DRAGANDDROP, MCcontactFiled.LEADSOURCE );
        return this;
    }

    @Override
    public SfdcQuestions addOrUpdateFreeTextQuestion(
                                                      SendEnum.QuestionStatus questionStatus,
                                                      SendEnum.FTcontactField questionInfo,
                                                      boolean isMandatory ) throws Exception {

        addOrUpdateFreeTextQuestion( questionStatus, questionInfo, false, isMandatory );
        return this;

    }

    public SfdcQuestions addOrUpdateFreeTextQuestion(
                                                      SendEnum.QuestionStatus questionStatus,
                                                      SendEnum.FTcontactField questionInfo,
                                                      boolean isLead,
                                                      boolean isMandatory ) throws Exception {

        addOrUpdateFTQuestionBy( questionInfo.question, questionStatus );

        mapFTQuestionToContactField( questionInfo.conntactFiled );

        if( isLead ) {
            mapQuestionToContactField( questionInfo.conntactFiled, element.sfdcLeadFieldFTid );
        }

        if( isMandatory ) {
            showQuestionSettings( "qTypetext" ).addManadatoryOption().hideQuestionSettings( "qTypetext" );
        }
        updateQuestion();
        return this;
    }

    @Override
    public SfdcQuestions addOrUpdateMCQuestion(
                                                QuestionStatus questionStatus,
                                                MCcontactFiled contactField ) throws Exception {

        addOrUpdateMCQuestion( false, questionStatus, contactField );
        return null;
    }

    public SfdcQuestions mapFTQuestionToContactField(
                                                    String contactFiled ) throws Exception {

        mapQuestionToContactField( contactFiled, element.sfdcContactFieldFTid );
        return this;
    }
    
    public SfdcQuestions mapFTQuestionToLeadField(
    												 String leadFiled ) throws Exception {

		mapQuestionToContactField( leadFiled, element.sfdcLeadFieldFTid );
		return this;
	}
}
