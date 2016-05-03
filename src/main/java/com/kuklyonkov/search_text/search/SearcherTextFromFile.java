package com.kuklyonkov.search_text.search;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Created by Валерий on 02.05.2016.
 */
public final class SearcherTextFromFile {

    public static boolean contains(File file, String text) {
        try {
            Reader fileReader = new FileReader(file);
            final int bufSize = 1024 * 1024;
            char buf[] = new char[bufSize];
            String oldBufStr = "";

            while (fileReader.read(buf) != -1) {
                final String item = oldBufStr + new String(buf);
                oldBufStr = item.substring(bufSize);

                try {
                    if (BoyerMoore.indexOf(item, text) > 0) {
                        return true;
                    }
                } catch (Exception ignored) {
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
