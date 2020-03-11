package concep.platform.alert;

import concep.platform.core.PlatformAssertion;
import concep.seleniumWD.core.SeleniumDriverCore;

public class AlertAssertion extends PlatformAssertion {

    private AlertElement element;

    public AlertAssertion( SeleniumDriverCore driver ) {
        super( driver );
        this.element = new AlertElement( super.driver );

    }

}
