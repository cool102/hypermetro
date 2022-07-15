import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

public class Tests extends StageTest<String> {
    @DynamicTest(order = 1)
    CheckResult simpleRouteTest() {
        
        return CheckResult.correct();
    }

    @DynamicTest(order = 2)
    CheckResult severalLinesRoute() {
        

        return CheckResult.correct();
    }

    @DynamicTest(order = 3)
    CheckResult severalLines() {
        

        return CheckResult.correct();
    }

    @DynamicTest(order = 4)
    CheckResult forkTest() {
        
        return CheckResult.correct();
    }

    @DynamicTest(order = 5)
    CheckResult forkTest2() {
       

        return CheckResult.correct();
    }

    @DynamicTest(order = 6)
    CheckResult simpleTimeTest() {
        

        return CheckResult.correct();
    }

    @DynamicTest(order = 7)
    CheckResult advancedTimeTest() {
        

        return CheckResult.correct();
    }

    
}
