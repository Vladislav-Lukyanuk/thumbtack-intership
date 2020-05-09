package net.thumbtack.school.matrix;

import java.util.*;
import java.util.stream.Collectors;

public class MatrixNonSimilarRows {
    private int[][] matrix;

    //1
    public MatrixNonSimilarRows(int[][] matrix) {
        this.matrix = matrix;
    }

    //2
    public Set<int[]> getNonSimilarRows() {
        Set<int[]> matrixSet = new LinkedHashSet<>();
        for (int[] row : matrix) {
            Set<Integer> rowHashSet = arrayToHashSet(row);
            if (matrixSet.stream().noneMatch(s -> (arrayToHashSet(s).equals(rowHashSet)))) {
                matrixSet.add(row);
            }
        }
        return matrixSet;
    }

    public Set<Integer> arrayToHashSet(int[] row) {
        return new HashSet<>(Arrays.stream(row).boxed().collect(Collectors.toList()));
    }

}
