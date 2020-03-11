package concep.selenium.send;

import concep.selenium.send.SendEnum.FTcontactField;
import concep.selenium.send.SendEnum.MCQuestionAnswerType;
import concep.selenium.send.SendEnum.MCcontactFiled;
import concep.selenium.send.SendEnum.QuestionStatus;

public class IAQuestions extends Questions<IAQuestions> {

    @Override
    public IAQuestions addOrUpdateFreeTextQuestion(
                                                    QuestionStatus questionStatus,
                                                    FTcontactField questionInfo,
                                                    boolean isMandatory ) throws Exception {

        addOrUpdateFTQuestionBy( questionInfo.question, questionStatus ).mapFtQuestionToContactField( questionInfo.conntactFiled );
        if( isMandatory ) {
            showQuestionSettings( "qTypetext" ).addManadatoryOption().hideQuestionSettings( "qTypetext" );
        }
        updateQuestion();
        return this;
    }

    @Override
    public IAQuestions addOrUpdateMCQuestion(
                                              QuestionStatus questionStatus,
                                              MCcontactFiled contactField ) throws Exception {

        addOrUpdateMCQuestion( questionStatus, contactField, MCQuestionAnswerType.DEFAULT );
        return this;
    }

    public IAQuestions addOrUpdateMCQuestion(
                                              QuestionStatus questionStatus,
                                              MCcontactFiled contactField,
                                              SendEnum.MCQuestionAnswerType answerType ) throws Exception {

        addOrUpdateMCQuestionBy( contactField.question, questionStatus ).mapMcQuestionToContactField( contactField )
                                                                        .fillinMCQAnswers( contactField.option_1,
                                                                                        contactField.option_2 )
                                                                        .addOptionsToMultipleCQuestion( answerType )
                                                                        .updateQuestion();

        return this;
    }

    public IAQuestions mapFtQuestionToContactField(
                                                    String contactField ) throws Exception {

        log.startStep( "Select the '" + contactField + "' interaction field " );
        driver.select2DropDown( element.interactionFieldFT, element.crmDropDownSearch, contactField );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();

        return this;
    }

    public IAQuestions mapMcQuestionToContactField(
                                                    String contactField,
                                                    String contactOption1,
                                                    String contactOption2 ) throws Exception {

        log.startStep( "Select the '" + contactField + "' interaction field " );
        driver.select2DropDown( element.interactionFieldMC, element.crmDropDownSearch, contactField );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();

        log.startStep( "Map the 1st answer to the contact option '" + contactOption1 + "'" );
        driver.select( "//div[@id='qAnswersList']//div[contains(@class, 'qAnswerRow')][1]//select[@id[starts-with(.,'answerDropDown')]]" )
              .selectByVisibleText( contactOption1 );
        log.endStep();

        log.startStep( "Map the 2nd answer to the contact option '" + contactOption2 + "'" );
        driver.select( "//div[@id='qAnswersList']//div[contains(@class, 'qAnswerRow')][2]//select[@id[starts-with(.,'answerDropDown')]]" )
              .selectByVisibleText( contactOption2 );
        log.endStep();

        return this;
    }

    public IAQuestions mapMcQuestionToContactField(
                                                    MCcontactFiled mcContactFiel ) throws Exception {

        mapMcQuestionToContactField( mcContactFiel.name, mcContactFiel.option_1, mcContactFiel.option_2 );

        return this;
    }

    public IAQuestions editFTQAndMapItToEmail(
                                               boolean isManadatory ) throws Exception {

        addOrUpdateFreeTextQuestion( QuestionStatus.EDIT, FTcontactField.EMAIL, isManadatory );

        return this;
    }

    public IAQuestions addFTQAndMapItToEmail(
                                              boolean isManadatory ) throws Exception {

        addOrUpdateFreeTextQuestion( QuestionStatus.NEW, FTcontactField.EMAIL, isManadatory );

        return this;
    }

    public IAQuestions dragAndDropFTQAndMapItToEmail(
                                                      boolean isManadatory ) throws Exception {

        addOrUpdateFreeTextQuestion( QuestionStatus.DRAGANDDROP, FTcontactField.EMAIL, isManadatory );

        return this;
    }

    public IAQuestions editFTQAndMapItToLastName(
                                                  boolean isManadatory ) throws Exception {

        addOrUpdateFreeTextQuestion( QuestionStatus.EDIT, FTcontactField.LASTNAME, isManadatory );

        return this;
    }

    public IAQuestions addFTQAndMapItToLastName(
                                                 boolean isManadatory ) throws Exception {

        addOrUpdateFreeTextQuestion( QuestionStatus.NEW, FTcontactField.LASTNAME, isManadatory );

        return this;
    }
    
    public IAQuestions dragAndDropFTQAndMapItToLastName(
                                                         boolean isManadatory ) throws Exception {

        addOrUpdateFreeTextQuestion( QuestionStatus.DRAGANDDROP, FTcontactField.LASTNAME, isManadatory );

        return this;
    }

    public IAQuestions editFTQAndMapItToFirstName(
                                                   boolean isManadatory ) throws Exception {

        addOrUpdateFreeTextQuestion( QuestionStatus.EDIT, FTcontactField.FIRSTNAME, isManadatory );

        return this;
    }

    public IAQuestions addFTQAndMapItToFirstName(
                                                  boolean isManadatory ) throws Exception {

        addOrUpdateFreeTextQuestion( QuestionStatus.NEW, FTcontactField.FIRSTNAME, isManadatory );

        return this;
    }

    public IAQuestions dragAndDropFTQAndMapItToFirstName(
                                                          boolean isManadatory ) throws Exception {

        addOrUpdateFreeTextQuestion( QuestionStatus.DRAGANDDROP, FTcontactField.FIRSTNAME, isManadatory );

        return this;
    }

    public IAQuestions editFTQAndMapItToCompany(
                                                 boolean isManadatory ) throws Exception {

        addOrUpdateFreeTextQuestion( QuestionStatus.EDIT, FTcontactField.COMPANY, isManadatory );

        return this;
    }

    public IAQuestions addFTQAndMapItToCompany(
                                                boolean isManadatory ) throws Exception {

        addOrUpdateFreeTextQuestion( QuestionStatus.NEW, FTcontactField.COMPANY, isManadatory );

        return this;
    }

    public IAQuestions dragAndDropFTQAndMapItToCompany(
                                                        boolean isManadatory ) throws Exception {

        addOrUpdateFreeTextQuestion( QuestionStatus.DRAGANDDROP, FTcontactField.COMPANY, isManadatory );

        return this;
    }

    public IAQuestions editMCQAndMapItToGenderG() throws Exception {

        addOrUpdateMCQuestion( QuestionStatus.EDIT, MCcontactFiled.GENDERGLOBAL );
        return this;
    }

    public IAQuestions addMCQAndMapItToGenderG() throws Exception {

        addOrUpdateMCQuestion( QuestionStatus.NEW, MCcontactFiled.GENDERGLOBAL );
        return this;
    }

    public IAQuestions dragAndDropMCQAndMapItToGenderG() throws Exception {

        addOrUpdateMCQuestion( QuestionStatus.DRAGANDDROP, MCcontactFiled.GENDERGLOBAL );
        return this;
    }

    public IAQuestions editMCQAndMapItToColorG() throws Exception {

        addOrUpdateMCQuestion( QuestionStatus.EDIT, MCcontactFiled.COLORGLOBAL );
        return this;
    }

    public IAQuestions addMCQAndMapItToColorG() throws Exception {

        addOrUpdateMCQuestion( QuestionStatus.NEW, MCcontactFiled.COLORGLOBAL );
        return this;
    }

    public IAQuestions dragAndDropMCQAndMapItToColorG() throws Exception {

        addOrUpdateMCQuestion( QuestionStatus.DRAGANDDROP, MCcontactFiled.COLORGLOBAL );
        return this;
    }
}
