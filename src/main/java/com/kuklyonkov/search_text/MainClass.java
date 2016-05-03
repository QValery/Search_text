package com.kuklyonkov.search_text;


/**
 * Created by Валерий on 29.04.2016.
 */
public class MainClass {

    public static void main(String arg[]) {
        System.out.print("\nПрограмма для поиска текста в файлах\n");
        System.out.print("Первый аргумент - искомая строка, второй параметр (необязательный) - папка для поиска (по умолчанию папка пользователя)\n\n");
        Searcher searcher = new Searcher();
        switch (arg.length) {
            case 2: searcher.setRootDir(arg[1]);
            case 1: searcher.setText(arg[0]); break;
            default: System.out.print("Аргументы не обнаружены\n"); return;
        }

        System.out.print("Поиск \"" + searcher.getText() + "\" в директории \"" + searcher.getRootDir() + "\" ...\n\n\n");
        searcher.start();
    }

}
