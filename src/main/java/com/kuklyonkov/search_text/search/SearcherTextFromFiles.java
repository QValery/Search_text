package com.kuklyonkov.search_text.search;

import java.io.File;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by Валерий on 02.05.2016.
 */
public final class SearcherTextFromFiles {
    private Queue<File> _queueFiles;
    private boolean _isRun = false;
    private int _threadCount;
    private SearcherTextFromFilesInterface _receiver;
    private String _text = "";

    public SearcherTextFromFiles(SearcherTextFromFilesInterface receiver) {
        _receiver = receiver;
        _queueFiles = new PriorityQueue<>();
        _threadCount = Runtime.getRuntime().availableProcessors();
    }


    public synchronized void addFiles(File[] files) {
        Collections.addAll(_queueFiles, files);
    }


    // Устанавливает текст для поиска
    // Если поис уже запущен, то вернёт false
    public boolean setSearchText(String text) {
        if (_isRun) return false;
        _text = text;
        return true;
    }

    public String getSearchText() {
        return _text;
    }


    private boolean isRun() {
        return _isRun;
    }

    public void run() {
        if (_isRun || _text.isEmpty()) return;
        _isRun = true;

        for (int i = 0; i < _threadCount; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        final File file = dequeue();
                        if (file == null) break;
//                        System.out.print(file.getAbsoluteFile() + "\n");

                        if (SearcherTextFromFile.contains(file, _text) && _receiver != null) {
                            _receiver.foundNewFile(file);
                        }
                    }
                }
            }).start();
        }
    }


    private synchronized File dequeue() {
        if (!_isRun) return null;
        if (_queueFiles.isEmpty()) {
            if (_receiver != null) {
                _receiver.doneTextSearch();
            }
            _isRun = false;
            return null;
        }
        return _queueFiles.poll();
    }


    public interface SearcherTextFromFilesInterface {
        void doneTextSearch();

        void foundNewFile(File file);
    }
}
