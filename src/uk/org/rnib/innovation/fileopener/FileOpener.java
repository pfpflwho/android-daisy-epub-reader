package uk.org.rnib.innovation.fileopener;

import java.io.InputStream;

public interface FileOpener {
	public InputStream open(String path);
}
