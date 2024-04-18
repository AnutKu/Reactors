import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public abstract class FileImporter {
    protected FileImporter successor;

    public void setSuccessor(FileImporter successor) {
        this.successor = successor;
    }

    public abstract void importFile(File file, Map<String, Reactor> reactorMap) throws IOException;
}