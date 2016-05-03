package com.kuklyonkov.search_text;

import com.kuklyonkov.search_text.files.ScanerFileSystem;
import com.kuklyonkov.search_text.search.SearcherTextFromFiles;

import java.io.File;

/**
 * Created by Валерий on 02.05.2016.
 */
public final class Searcher implements ScanerFileSystem.ScanerFileSystemInterface,
        SearcherTextFromFiles.SearcherTextFromFilesInterface {

    private ScanerFileSystem _scanerFileSystem;
    private SearcherTextFromFiles _searcherTextFromFiles;
    private String _rootDir;
    private long _startTime = 0;
    private int _resultCount = 0;
    private int _allFiles = 0;
    private int _allDirs = 0;
    private boolean _isRunScanerFiles = false;


    public Searcher() {
        _scanerFileSystem = new ScanerFileSystem(this);
        _searcherTextFromFiles = new SearcherTextFromFiles(this);
        _rootDir = System.getProperty("user.home");
    }


    public void setText(String text) {
        _searcherTextFromFiles.setSearchText(text);
    }

    public String getText() {
        return _searcherTextFromFiles.getSearchText();
    }


    public void setRootDir(String rootDir) {
        _rootDir = rootDir;
    }

    public String getRootDir() {
        return _rootDir;
    }


    public void start() {
        _isRunScanerFiles = true;
        _scanerFileSystem.startScanerFiles(_rootDir);
        _startTime = System.currentTimeMillis();
    }


    @Override
    public void foundNewFileObjects(File[] files) {
        _searcherTextFromFiles.addFiles(files);
        _searcherTextFromFiles.run();
        _allFiles += files.length;
        _allDirs ++;
    }

    @Override
    public void finishitedScanerFiles() {
        _isRunScanerFiles = false;
    }

    @Override
    public void doneTextSearch() {
        if (_isRunScanerFiles) return;
        System.out.print("\n\nПоиск завершён. Найденно " + String.valueOf(_resultCount) + " совпадений\n");
        System.out.printf("Затрачено %d мс, просканировано %d файлов в %d папках\n",System.currentTimeMillis() - _startTime, _allFiles, _allDirs);
    }

    @Override
    public void foundNewFile(File file) {
        System.out.print("Найденно совпадение в " + file.getAbsoluteFile() + "\n");
        _resultCount++;
    }
}
