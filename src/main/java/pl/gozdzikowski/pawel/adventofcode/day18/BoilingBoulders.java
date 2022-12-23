package pl.gozdzikowski.pawel.adventofcode.day18;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class BoilingBoulders {

    public int countVisibleSides(List<String> cubes) {
        List<VisibleEdgesInCube> visibleSidesInCubes = convertToCubes(cubes);
        for (int i = 0; i < visibleSidesInCubes.size(); i++) {
            for (int j = i + 1; j < visibleSidesInCubes.size(); j++) {
                visibleSidesInCubes.get(i).markNotVisibleSides(visibleSidesInCubes.get(j));
            }
        }
        return visibleSidesInCubes.
                stream()
                .mapToInt(VisibleEdgesInCube::visibleSides)
                .sum();
    }

    List<VisibleEdgesInCube> convertToCubes(List<String> cubes) {
        return cubes.stream()
                .map((line) -> {
                    String[] split = line.split(",");
                    return new VisibleEdgesInCube(new Cube(parseInt(split[0]), parseInt(split[1]), parseInt(split[2])));
                })
                .collect(Collectors.toList());
    }

    record Cube(
            int x,
            int y,
            int z
    ) {
        Cube plus(Cube cube) {
            return new Cube(this.x + cube.x, this.y + cube.y, this.z + cube.z);
        }

        Cube invert() {
            return new Cube(-x, -y, -z);
        }
    }

    class VisibleEdgesInCube {
        private Cube cube;
        private Set<Cube> coveredSides = new HashSet<>();

        public VisibleEdgesInCube(Cube cube) {
            this.cube = cube;
        }

        public Cube getCube() {
            return cube;
        }

        public void setCube(Cube cube) {
            this.cube = cube;
        }

        public int visibleSides() {
            return 6 - coveredSides.size();
        }

        public Set<Cube> notCoveredSides() {
            Set<Cube> notCoveredSides = new HashSet<>(posibleEdges);
            notCoveredSides.removeAll(coveredSides);
            return notCoveredSides;
        }

        private static final List<Cube> posibleEdges = List.of(
                new Cube(1, 0,0), new Cube(-1, 0,0),
                new Cube(0, 1,0), new Cube(0, -1,0),
                new Cube(0, 0,1), new Cube(0, 0,-1)
        );

        public void markNotVisibleSides(VisibleEdgesInCube visibleEdgesInCube) {

            Set<Cube> connectedSides = posibleEdges.stream()
                    .filter((side) -> cube.plus(side).equals(visibleEdgesInCube.cube))
                    .collect(Collectors.toSet());

            if (connectedSides.size() == 1) {
                this.coveredSides.addAll(connectedSides);
                visibleEdgesInCube.coveredSides.addAll(connectedSides.stream().map(Cube::invert).toList());
            }
        }

    }
}
