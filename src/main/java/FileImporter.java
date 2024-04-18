import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public abstract class FileImporter {
    protected FileImporter next;

    public void setNext(FileImporter next) {
        this.next = next;
    }

    public abstract void importFile(File file, ReactorHolder reactorMap) throws IOException;
}