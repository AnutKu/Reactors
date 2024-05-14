package Lab3;

import java.io.File;
import java.io.IOException;

public abstract class FileImporter {
    protected FileImporter next;

    public void setNext(FileImporter next) {
        this.next = next;
    }

    public abstract void importFile(File file, ReactorTypeHolder reactorMap) throws IOException;
}