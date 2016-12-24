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

            SimpleFileVisitor<Path> fileVisitor = new SimpleFileVisitor<Path>(){
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (file.toString().endsWith(".pdf")) {
                        Path parent = file.getParent();
                        if (parent != destination) {
                            Path renamedFile = parent.resolve(parent.getFileName() + ".pdf");
                            Path destinationFile = destination.resolve(renamedFile.getFileName());
                            Files.move(file, renamedFile);
                            Files.copy(renamedFile, destinationFile);
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }
            };

            Files.walkFileTree(source, fileVisitor);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }
}
