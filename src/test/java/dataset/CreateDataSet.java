package dataset;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;

public class CreateDataSet extends BaseDataSet {

    @CsvBindByName(column = "TITLE")
    private @Getter String productTitle;

    @CsvBindByName(column = "SKU")
    private @Getter String productSku;

    @CsvBindByName(column = "DESCRIPTION")
    private @Getter String productDescription;

    @CsvBindByName(column = "EXPECTED_RESULT")
    private @Getter String expectedResult;


}
