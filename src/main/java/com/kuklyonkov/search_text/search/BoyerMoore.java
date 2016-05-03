package com.kuklyonkov.search_text.search;

import java.util.Arrays;

/**
 * Created by Валерий on 02.05.2016.
 */
public final class BoyerMoore {

    /**
     * Возвращает индекс первого вхождения строки template в строку source, или
     * -1, в случае если вхождение не найдено.
     *
     * @param source   исходная строка, в которой ищется вхождение шаблона.
     * @param template шаблон строки, которая ищется в строке source.
     * @return индекс первого вхождения строки template в строку source, или -1,
     * в случае если вхождение не найдено.
     */
    public static int indexOf(String source, String template) throws Exception {
        final int sourceLen = source.length();
        final int templateLen = template.length();
        if (templateLen > sourceLen) {
            return -1;
        }

        int[] offsetTable = new int[255];
        Arrays.fill(offsetTable, templateLen);
        for (int i = 0; i < templateLen - 1; i++) {
            offsetTable[template.charAt(i)] = templateLen - i - 1;
        }

        int i = templateLen - 1;
        int j = i;
        int k = i;
        while (j >= 0 && i <= sourceLen - 1) {
            j = templateLen - 1;
            k = i;
            while (j >= 0 && source.charAt(k) == template.charAt(j)) {
                k--;
                j--;
            }
            i += offsetTable[source.charAt(i)];
        }
        if (k >= sourceLen - templateLen) {
            return -1;
        } else {
            return k + 1;
        }
    }
}
