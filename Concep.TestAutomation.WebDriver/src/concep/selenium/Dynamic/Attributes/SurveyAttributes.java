package concep.selenium.Dynamic.Attributes;

import java.util.ArrayList;
import java.util.List;

public class SurveyAttributes {

    public SurveyAttributes() {

        SurveyResponse = new ArrayList<SurveyResponseAttributes>();
        CampaignResponseAttribute = new ArrayList<CampaignResponseAttribute>();
        answerses = new ArrayList<AnswersAttributes>();
        questions = new ArrayList<QuestionsAttributes>();
    }

    public String                          surveyType;
    public String                          surveyID;
    public String                          campaignType;
    public String                          surveyStatus;
    public int                             SurveyResponseLength;
    public List<SurveyResponseAttributes>  SurveyResponse;
    public List<CampaignResponseAttribute> CampaignResponseAttribute;
    public List<QuestionsAttributes>       questions;
    public List<AnswersAttributes>         answerses;
    public String                          surveyName;

}
