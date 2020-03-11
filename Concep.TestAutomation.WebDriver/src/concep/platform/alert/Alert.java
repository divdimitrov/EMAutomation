package concep.platform.alert;

import concep.platform.core.PlatformBase;
import concep.seleniumWD.core.SeleniumDriverCore;

public class Alert extends PlatformBase {

    protected AlertElement   element;
    protected AlertAssertion assertion;

    public Alert( SeleniumDriverCore driver ) {
        super( driver );
        this.element = new AlertElement( super.driver );
        this.assertion = new AlertAssertion( super.driver );
    }

}
