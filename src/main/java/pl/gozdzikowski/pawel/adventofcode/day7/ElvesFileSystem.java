package pl.gozdzikowski.pawel.adventofcode.day7;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class ElvesFileSystem {
    sealed static class ElvesFileSystemObject permits ElvesDeviceDirectory, ElvesDeviceFile {
        private String name;

        public ElvesFileSystemObject(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    static final class ElvesDeviceFile extends ElvesFileSystemObject {
        private Long size;

        public ElvesDeviceFile(String name, Long size) {
            super(name);
            this.size = size;
        }

        public Long getSize() {
            return size;
        }

        @Override
        public String toString() {
            return "ElvesDeviceFile{" +
                    "size=" + size +
                    ", name='" + getName() + '\'' +
                    '}';
        }
    }

    static final class ElvesDeviceDirectory extends ElvesFileSystemObject {

        private ElvesDeviceDirectory parent;
        private String absolutePath;

        private List<ElvesFileSystemObject> directories;

        public ElvesDeviceDirectory(String name) {
            super(name);
            this.directories = new ArrayList<>();
            this.absolutePath = name;
        }

        public ElvesDeviceDirectory(String name, ElvesDeviceDirectory parent) {
            super(name);
            Objects.requireNonNull(parent);
            this.parent = parent;
            this.directories = new ArrayList<>();
            this.absolutePath = parent.absolutePath + "/" + name;
        }

        public ElvesDeviceDirectory getParent() {
            return parent;
        }

        public List<ElvesFileSystemObject> getDirectories() {
            return directories;
        }

        public String getAbsolutePath() {
            return absolutePath;
        }

        public void addFileSystemObject(ElvesFileSystemObject elvesFileSystemObject) {
            getDirectory(elvesFileSystemObject.getName()).ifPresentOrElse(
                    Function.identity()::apply,
                    () -> this.directories.add(elvesFileSystemObject)
            );
        }

        public Optional<ElvesFileSystemObject> getDirectory(String directory) {
            return this.directories.stream().filter((file) -> file.name.equals(directory))
                    .findFirst();
        }

        @Override
        public String toString() {
            return "ElvesDeviceDirectory{" +
                    "absolutePath='" + absolutePath + '\'' +
                    ", name='" + getName() + '\'' +
                    '}';
        }
    }
}
