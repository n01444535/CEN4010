package test;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages({"database", "controller", "model", "view"})
public class MainTestSuite {
    @Test
    void testSuiteRunner() {
        System.out.println("✅ Running all tests...");
    }
}