package de.tub.duplicateDetection;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
          return Files.lines(Paths.get(path))
                  .skip(1)
                  .map(line->{
                      return line.split(",");
                  })
                  .map(items->{
                        switch (items.length){
                            case 11:  return new Row(items[0],items[1],items[2],items[3],items[4],items[5],items[6],items[7],items[8],items[9],items[10],"");
                            case 12:  return new Row(items[0],items[1],items[2],items[3],items[4],items[5],items[6],items[7],items[8],items[9],items[10],items[11]);
                            case 13:  return new Row(items[0],items[1],items[2],items[3],items[4],items[5],items[6],items[7],items[8],items[9],items[10],items[11]);
                        }
                        System.out.println(items.length + "   " + Arrays.toString(items));
                        return null;
                     })
                  .filter(x->x!=null)
                  .collect(Collectors.toList());

    }
}
