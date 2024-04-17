package ru.job4j.io;

import java.io.*;
import java.nio.file.Files;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent(Predicate<Character> filter) throws IOException {
        try {
            return Files.readString(file.toPath())
                    .chars()
                    .filter((IntPredicate) filter)
                    .mapToObj(c -> (char) c)
                    .map(String::valueOf)
                    .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                    .toString();
        } catch (IOException e) {
            throw new IOException();
        }
    }

    public String getAllContent() throws IOException {
        return getContent(data -> true);
    }

    public String getFilteredContent() throws IOException {
        return getContent(data -> data < 0x80);
    }
}
