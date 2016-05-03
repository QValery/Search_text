package com.kuklyonkov.search_text.files;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by Валерий on 30.04.2016.
 */
public final class ScanerFileSystem {

    private ScanerFileSystemInterface _receiver;
    private FilenameFilter _filterDirs;

    public ScanerFileSystem(ScanerFileSystemInterface receiver) {
        _receiver = receiver;

        _filterDirs = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return new File(dir, name).isDirectory();
            }
        };
    }

    public synchronized void startScanerFiles(String rooDir) {
        final File file = new File(rooDir);
        if (!file.exists()) {
            return;
        }

        final FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                final File file1 = new File(dir, name);
                return file1.isFile() && file1.canRead();
            }
        };

        scanerFiles(file, filter);
        if (_receiver != null) {
            _receiver.finishitedScanerFiles();
        }
    }


    // Рекурсивная функция, для которой надо создавать свой поток
    private void scanerFiles(File dir, FilenameFilter filenameFilter) {
        final File[] files = dir.listFiles(filenameFilter);
        if (_receiver != null && files != null) {
            _receiver.foundNewFileObjects(files);
        }

        final File[] dirs = dir.listFiles(_filterDirs);

        if (dirs != null) {
            for (File item : dirs) {
                scanerFiles(item, filenameFilter);
            }
        }
    }


    public interface ScanerFileSystemInterface {
        void foundNewFileObjects(File files[]);

        void finishitedScanerFiles();
    }
}
