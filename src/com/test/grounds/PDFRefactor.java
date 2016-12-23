package com.test.grounds;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

class PDFRefactor {
    public static void main(String args[]) {
        try {
            Path source = FileSystems.getDefault().getPath("tests/source");
            Path destination = FileSystems.getDefault().getPath("tests/dest");
            Stream<Path> files = Files.walk(source)
                    .filter(path -> path.toFile().isFile())
                    .filter(path -> path.toString().endsWith(".pdf"));
            files.forEach(file -> {
                try {
                    Path parent = file.getParent();
                    Path renamedFile = parent.resolve(parent.getFileName() + ".pdf");
                    Files.move(file, renamedFile);
                    Files.copy(renamedFile, destination.resolve(renamedFile.getFileName()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }
}
