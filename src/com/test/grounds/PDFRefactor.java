package com.test.grounds;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

class PDFRefactor {
    public static void main(String args[]) {
        try {
            Path source = FileSystems.getDefault().getPath("tests/source");
            final Path destination = FileSystems.getDefault().getPath("tests/dest");
            Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file,
                                                 BasicFileAttributes attrs) throws IOException {
                    if (file.toString().endsWith(".pdf")) {
                        Path parent = file.getParent();
                        Path renamedFile = parent.resolve(parent.getFileName() + ".pdf");
                        Files.move(file, renamedFile);
                        Files.copy(renamedFile, destination.resolve(renamedFile.getFileName()));
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }
}
