package jiraPlusPlus;

import java.io.File;

public interface IImageLocation {
    public File getOldestUnprocessedImage() throws Exception;
}
