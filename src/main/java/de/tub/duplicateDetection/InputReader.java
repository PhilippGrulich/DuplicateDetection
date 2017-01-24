package de.tub.duplicateDetection;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class reads the given input file and outputs a list of rows.
 */
public class InputReader {

    private String path;

    InputReader(String path){
        this.path = path;
    }

    List<Row> readFile() throws IOException {
        File csvData = new File(path);
        CSVFormat csvFormat = CSVFormat.newFormat(',')
                .withQuote('"')
                .withHeader();
        CSVParser parser = CSVParser.parse(csvData, Charset.defaultCharset(), csvFormat);

        List<Row> list = new ArrayList<>();
        parser.forEach(item -> {

            Row row = new Row(item.get("RecID(String)"), item.get("FirstName(String)"), item.get("MiddleName(String)"), item.get("LastName(String)"),
                    item.get("Address(String)"),
                    item.get("City(String)"),
                    item.get("State"),
                    item.get("ZIP"),
                    item.get("POBox(String)"),
                    item.get("POCityStateZip(String)"),
                    item.get("SSN(String)"),
                    item.get("DOB(String)"));

            list.add(row);

        });

        return list;
    }
}
