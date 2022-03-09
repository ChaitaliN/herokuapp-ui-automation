package dataset;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;

public class BaseDataSet {

    @CsvBindByName(column = "TESTCASE_ID", required = true)
    private @Getter String testCaseID;

    @CsvBindByName(column = "TESTCASE_NAME", required = true)
    private @Getter String testCaseName;

    @CsvBindByName(column = "IS_POSITIVE_TEST", required = true)
    private Boolean isPositiveTest;

    public Boolean getPositiveTest() {
        return isPositiveTest;
    }

    @Override
    public String toString() {

        String type;
        if (getPositiveTest()) {
            type = "PositiveTest";
        } else {
            type = "NegativeTest";
        }
        return " TestCaseID: [" + testCaseID + "] [" + type + "] " + testCaseName;
    }
}
