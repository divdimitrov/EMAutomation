package concep.selenium.SalesForce.Attibutes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ali.hamadi on 3/10/2016.
 */
public class SurveyAttributes {
    public SurveyAttributes(){
         campaign=new Campaign();
    }
    public Campaign campaign;
    public String lastName;
    public String surveyName;
    public String surveyType;
    public String personType;
    public int sResponseLength;
    public List<String> email = new ArrayList<>();
    public List<String> questions=new ArrayList<>();
    public List<String> answers=new ArrayList<>();


}
