package concep.selenium.send;

import concep.selenium.core.DriverAccessor;
import concep.selenium.core.GenericSeleniumWebDriver;
import concep.selenium.core.LogResults;
import concep.selenium.send.SendEnum.MCQuestionAnswerType;
import concep.selenium.send.SendEnum.QuestionStatus;

import java.util.*;

public abstract class Questions<T> {
    protected Elements                        element = new Elements();
    protected static GenericSeleniumWebDriver driver  = DriverAccessor.getDriverInstance();
    protected LogResults                      log     = new LogResults();
    public int questionID;

    public abstract T addOrUpdateFreeTextQuestion(
                                                   SendEnum.QuestionStatus questionStatus,
                                                   SendEnum.FTcontactField questionInfo,
                                                   boolean isMandatory ) throws Exception;

    public abstract T addOrUpdateMCQuestion(
                                             SendEnum.QuestionStatus questionStatus,
                                             SendEnum.MCcontactFiled contactField ) throws Exception;

    @SuppressWarnings("unchecked")
    public T addNewQuestionType(
                                 String qType,
                                 String questionId ) throws Exception {

        log.startStep( "Click the 'Add Question' button" );
        driver.click( element.addQuestion, driver.timeOut );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();

        log.startStep( "Click the '" + qType + "' radio button" );
        driver.click( element.questionRadioButton( questionId ), driver.timeOut );
        log.endStep();

        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T addContentQuestion(
                                 boolean isDragAndDrop,
                                 String content ) throws Exception {

        if( isDragAndDrop ) {
            addDragAndDropQuestionType( "Content" );
        } else {
            addNewQuestionType( "Content", element.contentQId );
        }
        enterQuestion( content );
        updateQuestion();
        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T addOrUpdateFTQuestionBy(
                                      String question,
                                      SendEnum.QuestionStatus questionStatus ) throws Exception {

        if( questionStatus == QuestionStatus.NEW ) {
            addNewQuestionType( "free text question", element.ftQId );
        } else if( questionStatus == QuestionStatus.DRAGANDDROP ) {
            addDragAndDropQuestionType( "Free Text" );
        } else if( questionStatus == QuestionStatus.EDIT ) {
            editQuestionType( "A free text question" );
        }
        enterQuestion( question );
        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T addOrUpdateMCQuestionBy(
                                      String question,
                                      SendEnum.QuestionStatus questionStatus ) throws Exception {

        if( questionStatus == QuestionStatus.DRAGANDDROP ) {

            addDragAndDropQuestionType( "Multiple Choice" );
            enterQuestion( question );
            try {
                Thread.sleep( 1000 );
            } catch( InterruptedException e ) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            deleteMcQuestionAnswers( 1 );

        } else if( questionStatus == QuestionStatus.NEW ) {

            addNewQuestionType( "multiple choice Question", element.mcQId );
            enterQuestion( question );
            try {
                Thread.sleep( 1000 );
            } catch( InterruptedException e ) {}
            // add additional answers
            log.startStep( "Add Additonal Answer field" );
            driver.click( "//a[@id='qAddAnswer']", driver.timeOut );
            log.endStep();

        } else if( questionStatus == QuestionStatus.EDIT ) {
            editQuestionType( "A multiple choice question" );
            enterQuestion( question );
            try {
                Thread.sleep( 1000 );
            } catch( InterruptedException e ) {}
            //delete unnecessary answers
            deleteMcQuestionAnswers( 2 );

        }

        return ( T ) this;
    }

    //Type in 'Free Text', 'Multiple Choice', 'Matrix' or 'Content'
    @SuppressWarnings("unchecked")
    public T addDragAndDropQuestionType(
                                         String qType ) throws Exception {

        log.startStep( "Drag and drop a '" + qType + "' question" );
        driver.dragAndDrop( "//div/p[contains(text(),'" + qType + "')]", "//div[@id='surveyPageContainer']" );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();
        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T editQuestionType(
                               String qtype ) throws Exception {

        log.startStep( "Edit the '" + qtype + "' question" );
        driver.click( "//div[@class='sQT']/label[contains(text(), '" + qtype + "')] | //div[@class='sQT']/div[contains(text(), '" + qtype + "')] | //div[@class='sQMin'][contains(text(), '" + qtype + "')]",
                      driver.timeOut );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();
        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T fillinMCQAnswers(
                               String answer1,
                               String answer2 ) throws Exception {

        log.startStep( "Clear  the Textfield and Enter '" + answer1 + "' in the 1st answer text field" );
        driver.clear( element.mCQfirstAnswerTxt );
        driver.type( element.mCQfirstAnswerTxt, answer1, driver.timeOut );
        log.endStep();

        log.startStep( "Clear  the Textfield and Enter '" + answer2 + "' in the 2nd answer text field" );
        driver.clear( element.mCQSecondAnswerTxt );
        driver.type( element.mCQSecondAnswerTxt, answer2, driver.timeOut );
        log.endStep();

        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T deleteFtQuestionType(
                                   String question ) throws Exception {

        log.startStep( "Delete the multiple choice question" );
        driver.click( element.ftQuestionOptionsButton( question ), driver.timeOut );
        driver.click( element.deleteQuestion, driver.timeOut );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        driver.click( element.deleteQuestionCompletion, driver.timeOut );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();

        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T deleteMcQuestionType(
                                   String question ) throws Exception {

        log.startStep( "Delete the multiple choice question" );
        driver.click( element.mcQuestionOptionsButton( question ), driver.timeOut );
        driver.click( element.deleteQuestion, driver.timeOut );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        driver.click( element.deleteQuestionCompletion, driver.timeOut );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();

        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T deleteMcQuestionAnswers(
                                      int answerCount ) throws Exception {

        for( int i = 0; i < answerCount; i++ ) {
            log.startStep( "Delete unnecessary answer options number " + answerCount );
            driver.click( "//div[@id='qAnswersList']//div[contains(@class, 'qAnswerRow')][3]//a[contains(text(), 'Delete')]", driver.timeOut );
            log.endStep();
        }

        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T deleteContentQuestionType(
                                        String question ) throws Exception {

        log.startStep( "Delete the Content question" );
        driver.click( element.contentQuestionOptionsButton( question ), driver.timeOut );
        driver.click( element.deleteQuestion, driver.timeOut );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        driver.click( element.deleteQuestionCompletion, driver.timeOut );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();

        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T changeQuestionType(
                                 String questionId ) throws Exception {

        log.startStep( "click on the " + questionId + " radio button" );
        driver.click( "//input[@id='" + questionId + "' and @type='radio']", driver.timeOut );
        log.endStep();

        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T enterQuestion(
                            String question ) throws Exception {

    	
        log.startStep( "Enter a question '" + question + "' in the question editable area" );
        driver.switchToIframe( element.questionIframe );
        driver.click("//body", driver.ajaxTimeOut);
        driver.clear( "//body" );
        driver.click("//body", driver.ajaxTimeOut);
        driver.type( "//body", question, driver.timeOut );
        driver.switchToTopWindow();        
        log.endStep();
        
        questionID = Integer.parseInt(driver.getInnerText(element.questionIdContainer));

        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T mapQuestionToContactField(
                                        String contactField,
                                        String contactFiledId ) throws Exception {

        log.startStep( "Select the '" + contactField + "' field " );
        driver.select2DropDown( "//label[@id='" + contactFiledId + "']/span",
                                element.searchInputQuestion,
                                contactField );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();

        return ( T ) this;
    }

    //Ids are: qTypecontent,qTyperesult,qTypetext,qTypemc
    @SuppressWarnings("unchecked")
    public T showQuestionSettings(
                                   String questionId ) throws Exception {

        log.startStep( "Click on Settings 'Show' hyper link" );
        driver.click( element.showQuestionSettings( questionId ), driver.timeOut );
        log.endStep();

        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T hideQuestionSettings(
                                   String questionId ) throws Exception {

        log.startStep( "Click on Settings 'hide' hyper link" );
        driver.click( element.hideQuestionSettings( questionId ), driver.timeOut );
        log.endStep();

        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T updateQuestion() throws Exception {

        log.startStep( "Click the update button" );
        driver.click( element.updateQuestion, driver.timeOut );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();
        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T cancelQuestion() throws Exception {

        log.startStep( "Click the Cancel button" );
        driver.click( element.cancelQuestion, driver.timeOut );
        log.endStep();
        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T addManadatoryOption() throws Exception {

        log.startStep( "Check the 'Mandatory' check-box" );
        if( !driver.isChecked( element.mandatoryOpetion ) ) {
            driver.click( element.mandatoryOpetion, driver.timeOut );
        }
        log.endStep();
        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T addOptionsToMultipleCQuestion(
                                            SendEnum.MCQuestionAnswerType answerType ) throws Exception {

        if( answerType != MCQuestionAnswerType.DEFAULT ) {
            showQuestionSettings( "qTypemc" );

            switch( answerType ){
                case MANADATORY:
                    addManadatoryOption();
                    break;
                case DROPDOWN:
                    log.startStep( "Selct 'Drop Down Menu' from the drop down" );
                    driver.select( element.questionDisplyDropDown ).selectByVisibleText( "Drop Down Menu" );
                    ;
                    log.endStep();
                    break;
                case OTHERANSWER:
                    log.startStep( "Check the 'Allow 'Other' Answer' check-box" );
                    if( !driver.isChecked( element.allowOtherAnswerCheckBox ) ) {
                        driver.click( element.allowOtherAnswerCheckBox, driver.timeOut );
                    }
                    log.endStep();
                    break;
                case MULTIPLEANSWERS:
                    log.startStep( "Check the 'Allow Multiple Answers' check-box" );
                    if( !driver.isChecked( element.allowMultipleAnswers ) ) {
                        driver.click( element.allowMultipleAnswers, driver.timeOut );
                    }
                    log.endStep();
                    break;
                default:
                    break;
            }
            hideQuestionSettings( "qTypemc" );
        }
        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T answerMcQuestion(
                               String[] answers ) throws Exception {

        for( int i = 0; i < answers.length; i++ ) {
            log.resultStep( "select " + answers[i] + " option" );
            driver.click( "//div[@id='surveyPageContainer']//label[contains(text(),'" + answers[i] + "')]",
                          driver.timeOut );
            log.endStep();
        }
        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T answerMcQuestion(
                               String question,
                               String[] answers ) throws Exception {

        for( int i = 0; i < answers.length; i++ ) {
            log.resultStep( "select " + answers[i] + " option" );
            driver.click( element.multipleChoiceSurveyAnswers( question, answers[i] ), driver.timeOut );
            log.endStep();
        }
        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T answerDropDownQuestion(
                                     String question,
                                     String answer ) throws Exception {

        log.resultStep( "Select  '" + answer + "' option for the" + question + " question " );
        driver.select( element.dropDownMenuSurveyAnswer( question ) ).selectByVisibleText( answer );
        log.endStep();

        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T answerFtQuestion(
                               String question,
                               String answer ) throws Exception {

        log.resultStep( "type in " + answer + " in the " + question + " field" );
        driver.click(element.freeTextSurveyAnswers( question ), driver.timeOut);
        driver.type( element.freeTextSurveyAnswers( question ), answer, driver.timeOut );
        log.endStep();

        return ( T ) this;
    }

    public int getHighesQuestionId(Map<Integer, String> map) {
        
        int highestId = 0;
       
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
           
            if (entry.getKey() > highestId) {
               
                highestId = entry.getKey();
            }
        }
       
        return highestId;
    }
}
