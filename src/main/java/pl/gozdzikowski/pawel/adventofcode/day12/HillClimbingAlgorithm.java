package pl.gozdzikowski.pawel.adventofcode.day12;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HillClimbingAlgorithm {
    private final Set<Position> allAPositions;
    private Position startingPoint;
    private Position endingPoint;
    private DefaultDirectedWeightedGraph<Position, DefaultWeightedEdge> graph;

    public HillClimbingAlgorithm(char[][] climbingMap) {
        this.startingPoint = findPositionWith(climbingMap, 'S').stream().findFirst().get();
        this.endingPoint = findPositionWith(climbingMap, 'E').stream().findFirst().get();
        this.allAPositions = findPositionWith(climbingMap, 'a');
        this.graph = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);

        for (int i = 0; i < climbingMap.length; ++i) {
            for (int j = 0; j < climbingMap[i].length; ++j) {
                Position currentPosition = new Position(j, i);
                relativePositions(climbingMap, currentPosition)
                        .stream()
                        .filter((pos) -> allowedToMove(climbingMap, pos, currentPosition))
                        .forEach((pos) -> {
                            if (!graph.containsVertex(currentPosition))
                                graph.addVertex(currentPosition);

                            if (!graph.containsVertex(pos))
                                graph.addVertex(pos);

                            DefaultWeightedEdge edgeWeight = new DefaultWeightedEdge();
                            graph.addEdge(currentPosition, pos, edgeWeight);
                            graph.setEdgeWeight(edgeWeight, 1);
                        });
            }
        }

    }

    public long howManyStepsToExitFromStartingPoint() {
        DijkstraShortestPath<Position, DefaultWeightedEdge> dijkstraAlg =
                new DijkstraShortestPath<>(graph);


        return dijkstraAlg.getPath(this.startingPoint, this.endingPoint).getLength();
    }

    public long minimalStepsFromLowestElevation() {
        DijkstraShortestPath<Position, DefaultWeightedEdge> dijkstraAlg =
                new DijkstraShortestPath<>(graph);

        return allAPositions.stream().map(
                (startingPos) -> dijkstraAlg.getPath(startingPos, this.endingPoint)
        ).filter(Objects::nonNull)
                .map(GraphPath::getLength)
                .min(Integer::compareTo).get();
    }

    private Set<Position> findPositionWith(char[][] climbingMap, char symbolToFind) {
        Set<Position> positions = new HashSet<>();
        for (int y = 0; y < climbingMap.length; ++y) {
            for (int x = 0; x < climbingMap[y].length; ++x) {
                if (climbingMap[y][x] == symbolToFind) {
                    positions.add(new Position(x, y));
                }
            }
        }

        return positions;
    }


    private boolean allowedToMove(char[][] climbingMap, Position pos, Position currentPosition) {
        char currentHigh = climbingMap[currentPosition.y][currentPosition.x];
        char nextHigh = climbingMap[pos.y][pos.x];
        return (nextHigh == 'E' && (currentHigh == 'z' || currentHigh == 'y'))
                || (nextHigh >= 'a' && nextHigh <= 'z' && (nextHigh - currentHigh <= 1)) ||
                (currentHigh == 'S' && (nextHigh == 'a' || nextHigh == 'b')
                );
    }

    Set<Position> relativePositions(char[][] climbingMap, Position currentPosition) {
        return Stream.of(new Position(currentPosition.x - 1, currentPosition.y),
                        new Position(currentPosition.x, currentPosition.y + 1),
                        new Position(currentPosition.x, currentPosition.y - 1),
                        new Position(currentPosition.x + 1, currentPosition.y)
                ).filter((pos) -> (pos.x >= 0 && pos.x < climbingMap[0].length) &&
                        (pos.y >= 0 && pos.y < climbingMap.length))
                .collect(Collectors.toSet());

    }

    record Position(
            int x,
            int y
    ) {
    }
}
