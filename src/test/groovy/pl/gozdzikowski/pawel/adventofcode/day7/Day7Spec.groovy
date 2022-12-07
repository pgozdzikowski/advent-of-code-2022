package pl.gozdzikowski.pawel.adventofcode.day7

import pl.gozdzikowski.pawel.adventofcode.shared.input.FileInput
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input
import spock.lang.Specification

class Day7Spec extends Specification {
    NoSpaceLeftOnDevice noSpaceLeftOnDevice = new NoSpaceLeftOnDevice()

    def 'should calculate size of directories with most '() {
        List<String> input = [
                '$ cd /',
                '$ ls',
                'dir a',
                '14848514 b.txt',
                '8504156 c.dat',
                'dir d',
                '$ cd a',
                '$ ls',
                'dir e',
                '29116 f',
                '2557 g',
                '62596 h.lst',
                '$ cd e',
                '$ ls',
                '584 i',
                '$ cd ..',
                '$ cd ..',
                '$ cd d',
                '$ ls',
                '4060174 j',
                '8033020 d.log',
                '5626152 d.ext',
                '7214296 k'
        ]
        when:
        def size = noSpaceLeftOnDevice.calculateSumDirectoriesLowerThan(input, 100000)
        then:
        size == 95437
    }

    def 'file inout'() {
        Input input = new FileInput('day7.txt')
        when:
        def size = noSpaceLeftOnDevice.calculateSumDirectoriesLowerThan(input.get(), 100000)
        then:
        size == 1477771
    }

    def 'find directory clossest to '() {
        List<String> input = [
                '$ cd /',
                '$ ls',
                'dir a',
                '14848514 b.txt',
                '8504156 c.dat',
                'dir d',
                '$ cd a',
                '$ ls',
                'dir e',
                '29116 f',
                '2557 g',
                '62596 h.lst',
                '$ cd e',
                '$ ls',
                '584 i',
                '$ cd ..',
                '$ cd ..',
                '$ cd d',
                '$ ls',
                '4060174 j',
                '8033020 d.log',
                '5626152 d.ext',
                '7214296 k'
        ]
        when:
        def size = noSpaceLeftOnDevice.findDirectorySizeClosestToNeededSpace(input, 30000000L, 70000000L)
        then:
        size == 24933642
    }

    def 'file inout 2'() {
        Input input = new FileInput('day7.txt')
        when:
        def size = noSpaceLeftOnDevice.findDirectorySizeClosestToNeededSpace(input.get(), 30000000L, 70000000L)
        then:
        size == 3579501
    }
}
