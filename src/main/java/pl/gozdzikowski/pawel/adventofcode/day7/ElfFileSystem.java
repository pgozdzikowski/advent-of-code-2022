package pl.gozdzikowski.pawel.adventofcode.day7;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class ElfFileSystem {
    sealed static class ElfFileSystemObject permits ElfDeviceDirectory, ElfDeviceFile {
        private String name;

        public ElfFileSystemObject(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }

    static final class ElfDeviceFile extends ElfFileSystemObject {
        private Long size;

        public ElfDeviceFile(String name, Long size) {
            super(name);
            this.size = size;
        }

        public Long getSize() {
            return size;
        }
    }

    static final class ElfDeviceDirectory extends ElfFileSystemObject {

        private ElfDeviceDirectory parent;
        private String absolutePath;

        private List<ElfFileSystemObject> directories;

        public ElfDeviceDirectory(String name) {
            super(name);
            this.directories = new ArrayList<>();
            this.absolutePath = name;
        }

        public ElfDeviceDirectory(String name, ElfDeviceDirectory parent) {
            super(name);
            Objects.requireNonNull(parent);
            this.parent = parent;
            this.directories = new ArrayList<>();
            this.absolutePath = parent.absolutePath + "/" + name;
        }

        public ElfDeviceDirectory getParent() {
            return parent;
        }

        public List<ElfFileSystemObject> getDirectories() {
            return directories;
        }

        public String getAbsolutePath() {
            return absolutePath;
        }

        public void addFileSystemObject(ElfFileSystemObject elfFileSystemObject) {
            getDirectory(elfFileSystemObject.getName()).ifPresentOrElse(
                    Function.identity()::apply,
                    () -> this.directories.add(elfFileSystemObject)
            );
        }

        public Optional<ElfFileSystemObject> getDirectory(String directory) {
            return this.directories.stream().filter((file) -> file.name.equals(directory))
                    .findFirst();
        }
    }
}
