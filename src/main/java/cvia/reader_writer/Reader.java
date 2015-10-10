package cvia.reader_writer;

import java.io.IOException;

/**
 * Created by Michael Limantara on 9/23/2015.
 */
public interface Reader {

    public enum InputFileFormat {
        PDF, TXT
    }

    public class FileFormatNotSupportedException extends Exception {};

    public String read(String path) throws FileFormatNotSupportedException, IOException;
}
