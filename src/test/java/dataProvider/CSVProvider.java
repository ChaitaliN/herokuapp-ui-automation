package dataProvider;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import org.testng.ITestContext;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class CSVProvider {

    static private String scenariosDirectory = "scenarios";

    public Iterator<Object> toObject(ITestContext context, String filePath, Class pClass) throws IOException {

        // Get CSV file path for provided environment
        String csvFileName = scenariosDirectory + filePath;
        String csvFilePath = getClass().getClassLoader().getResource(csvFileName).getFile();

        // Load CSV data into object
        return  new CsvToBeanBuilder<>(new FileReader(csvFilePath)).withType(pClass).withOrderedResults(false)
                .withFieldAsNull(CSVReaderNullFieldIndicator.EMPTY_QUOTES).withIgnoreEmptyLine(false)
                .withIgnoreLeadingWhiteSpace(true).build().iterator();
    }
}
