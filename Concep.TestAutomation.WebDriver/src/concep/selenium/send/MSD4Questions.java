package concep.selenium.send;

import concep.selenium.send.SendEnum.FTcontactField;
import concep.selenium.send.SendEnum.MCQuestionAnswerType;
import concep.selenium.send.SendEnum.MCcontactFiled;
import concep.selenium.send.SendEnum.QuestionStatus;

public class MSD4Questions extends Questions<MSD4Questions> {

    public MSD4Questions addMCResponseCodeOptions() throws Exception {

        log.startStep( "Map the 1st answer of the Response Codes 'Interested'" );
        driver.select( "//ul[@id='qAnswerList']/li[@class='s_clear answerItem'][1]/ul/li[@class='responseCodeDropDown']//select[@id[starts-with(.,'responseCodeDropDown')]]" )
              .selectByVisibleText( "Interested" );
        log.endStep();

        log.startStep( "Map the 2nd answer of the Response Cdoes 'Not Interested'" );
        driver.select( "//ul[@id='qAnswerList']/li[@class='s_clear answerItem'][2]/ul/li[@class='responseCodeDropDown']//select[@id[starts-with(.,'responseCodeDropDown')]]" )
              .selectByVisibleText( "Not Interested" );
        log.endStep();
        return this;
    }

    public MSD4Questions mapMCQuestionToContactField(
                                                      MCcontactFiled contactField ) throws Exception {

        mapMCQuestionToContactField( contactField.name, contactField.option_1, contactField.option_2 );

        return this;

    }

    public MSD4Questions mapMCQuestionToContactField(
                                                      String contactFieldMC,
                                                      String contactOption1,
                                                      String contactOption2 ) throws Exception {

        mapQuestionToContactField( contactFieldMC, element.msdContactFieldMCid );

        log.startStep( "Map the 1st answer to the contact option '" + contactOption1 + "'" );
        driver.select( "//ul[@id='qAnswerList']/li[1]//select[@id[starts-with(.,'answerDropDown')]]" )
              .selectByVisibleText( contactOption1 );
        log.endStep();

        log.startStep( "Map the 2nd answer to the contact option '" + contactOption2 + "'" );
        driver.select( "//ul[@id='qAnswerList']/li[2]//select[@id[starts-with(.,'answerDropDown')]]" )
              .selectByVisibleText( contactOption2 );
        log.endStep();
        return this;
    }

    @Override
    public MSD4Questions addOrUpdateFreeTextQuestion(
                                                      QuestionStatus questionStatus,
                                                      FTcontactField questionInfo,
                                                      boolean isMandatory ) throws Exception {

        addOrUpdateFTQuestionBy( questionInfo.question, questionStatus ).mapFTQuestionToContactField( questionInfo.conntactFiled );
        if( isMandatory ) {
            showQuestionSettings( "qTypetext" ).addManadatoryOption().hideQuestionSettings( "qTypetext" );
        }
        updateQuestion();
        return this;
    }

    @Override
    public MSD4Questions addOrUpdateMCQuestion(
                                                QuestionStatus questionStatus,
                                                MCcontactFiled contactField ) throws Exception {

        addOrUpdateMCQuestion( questionStatus, contactField, MCQuestionAnswerType.DEFAULT );

        return this;
    }

    public MSD4Questions mapFTQuestionToContactField(
                                                    String contactField ) throws Exception {

        mapQuestionToContactField( contactField, element.msdContactFieldFTid );
        return this;
    }

    public MSD4Questions addOrUpdateMCQuestion(
                                                QuestionStatus questionStatus,
                                                MCcontactFiled contactField,
                                                SendEnum.MCQuestionAnswerType answerType ) throws Exception {

        addOrUpdateMCQuestionBy( contactField.question, questionStatus ).mapMCQuestionToContactField( contactField )
                                                                        .addMCResponseCodeOptions()
                                                                        .fillinMCQAnswers( contactField.option_1,
                                                                                           contactField.option_2 )
                                                                        .addOptionsToMultipleCQuestion( answerType )
                                                                        .updateQuestion();

        return this;
    }

    public MSD4Questions editFTQAndMapItToEmail(
                                                 boolean isManadatory ) throws Exception {

        addOrUpdateFreeTextQuestion( QuestionStatus.EDIT, FTcontactField.EMAIL, isManadatory );

        return this;
    }

    public MSD4Questions addFTQAndMapItToEmail(
                                                boolean isManadatory ) throws Exception {

        addOrUpdateFreeTextQuestion( QuestionStatus.NEW, FTcontactField.EMAIL, isManadatory );

        return this;
    }

    public MSD4Questions dragAndDropFTQAndMapItToEmail(
                                                        boolean isManadatory ) throws Exception {

        addOrUpdateFreeTextQuestion( QuestionStatus.DRAGANDDROP, FTcontactField.EMAIL, isManadatory );

        return this;
    }

    public MSD4Questions editFTQAndMapItToLastName(
                                                    boolean isManadatory ) throws Exception {

        addOrUpdateFreeTextQuestion( QuestionStatus.EDIT, FTcontactField.LASTNAME, isManadatory );

        return this;
    }

    public MSD4Questions addFTQAndMapItToLastName(
                                                   boolean isManadatory ) throws Exception {

        addOrUpdateFreeTextQuestion( QuestionStatus.NEW, FTcontactField.LASTNAME, isManadatory );

        return this;
    }

    public MSD4Questions dragAndDropFTQAndMapItToLastName(
                                                           boolean isManadatory ) throws Exception {

        addOrUpdateFreeTextQuestion( QuestionStatus.DRAGANDDROP, FTcontactField.LASTNAME, isManadatory );

        return this;
    }

    public MSD4Questions editFTQAndMapItToFirstName(
                                                     boolean isManadatory ) throws Exception {

        addOrUpdateFreeTextQuestion( QuestionStatus.EDIT, FTcontactField.FIRSTNAME, isManadatory );

        return this;
    }

    public MSD4Questions addFTQAndMapItToFirstName(
                                                    boolean isManadatory ) throws Exception {

        addOrUpdateFreeTextQuestion( QuestionStatus.NEW, FTcontactField.FIRSTNAME, isManadatory );

        return this;
    }

    public MSD4Questions dragAndDropFTQAndMapItToFirstName(
                                                            boolean isManadatory ) throws Exception {

        addOrUpdateFreeTextQuestion( QuestionStatus.DRAGANDDROP, FTcontactField.FIRSTNAME, isManadatory );

        return this;
    }

    public MSD4Questions editMCQAndMapItToGender() throws Exception {

        addOrUpdateMCQuestion( QuestionStatus.EDIT, MCcontactFiled.GENDER );
        return this;
    }

    public MSD4Questions addMCQAndMapItToGender() throws Exception {

        addOrUpdateMCQuestion( QuestionStatus.NEW, MCcontactFiled.GENDER );
        return this;
    }

    public MSD4Questions dragAndDropMCQAndMapItToGender() throws Exception {

        addOrUpdateMCQuestion( QuestionStatus.DRAGANDDROP, MCcontactFiled.GENDER );
        return this;
    }

    public MSD4Questions editMCQAndMapItToROLE() throws Exception {

        addOrUpdateMCQuestion( QuestionStatus.EDIT, MCcontactFiled.ROLE );
        return this;
    }

    public MSD4Questions addMCQAndMapItToRole() throws Exception {

        addOrUpdateMCQuestion( QuestionStatus.NEW, MCcontactFiled.ROLE );
        return this;
    }

    public MSD4Questions dragAndDropMCQAndMapItToRole() throws Exception {

        addOrUpdateMCQuestion( QuestionStatus.DRAGANDDROP, MCcontactFiled.ROLE );
        return this;
    }

    public MSD4Questions editMCQAndMapItToMartialStatus() throws Exception {

        addOrUpdateMCQuestion( QuestionStatus.EDIT, MCcontactFiled.MARITALSTATUS );
        return this;
    }

    public MSD4Questions addMCQAndMapItToMartialStatus() throws Exception {

        addOrUpdateMCQuestion( QuestionStatus.NEW, MCcontactFiled.MARITALSTATUS );
        return this;
    }

    public MSD4Questions dragAndDropMCQAndMapItToMartialStatus() throws Exception {

        addOrUpdateMCQuestion( QuestionStatus.DRAGANDDROP, MCcontactFiled.MARITALSTATUS );
        return this;
    }

}
