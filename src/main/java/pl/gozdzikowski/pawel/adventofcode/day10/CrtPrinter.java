package pl.gozdzikowski.pawel.adventofcode.day10;

public class CrtPrinter {
    public static void print(char[][] crtScreen) {
        for (int y = 0; y < crtScreen.length; ++y) {
            for (int x = 0; x < crtScreen[y].length; ++x) {
                System.out.print(crtScreen[y][x]);
            }
            System.out.print("\n");
        }
    }
}
