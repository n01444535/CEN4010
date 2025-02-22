package test;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.runner.RunWith;
import org.junit.platform.runner.JUnitPlatform;

@SuppressWarnings("deprecation")
@RunWith(JUnitPlatform.class) 
@Suite
@SelectClasses({
    ExpenseControllerTest.class,
    DatabaseManagerTest.class,
    UserControllerTest.class,
    LoginFormTest.class
})
public class MainTestSuite {
}