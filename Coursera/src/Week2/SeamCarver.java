package Week2;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Picture;

import java.awt.Color;
import java.util.Arrays;

/**
 * created: 12.04.2020
 *
 * @author David Zashkolny
 * 3 course, comp math
 * Taras Shevchenko National University of Kyiv
 * email: davendiy@gmail.com
 *
 * In this assignment, you will create a data type that resizes
 * a W-by-H image using the seam-carving technique.
 *
 * Finding and removing a seam involves three parts and a tiny bit of notation:
 *      0. Notation. In image processing, pixel (x, y) refers to the pixel in column x and row y,
 *         with pixel (0, 0) at the upper left corner and pixel (W − 1, H − 1) at the bottom right
 *         corner. This is consistent with the Picture data type in algs4.jar.
 *         Warning: this is the opposite of the standard mathematical notation used in linear
 *         algebra where (i, j) refers to row i and column j and with Cartesian coordinates
 *         where (0, 0) is at the lower left corner. We also assume that the color of a pixel is represented
 *         in RGB space, using three integers between 0 and 255.
 *         This is consistent with the java.awt.Color data type.
 *
 *      1. Energy calculation. The first step is to calculate the energy of each pixel,
 *         which is a measure of the importance of each pixel—the higher the energy, the less
 *         likely that the pixel will be included as part of a seam (as we'll see in the next step).
 *         In this assignment, you will implement the dual-gradient energy function, which is described below.
 *
 *      2. Seam identification. The next step is to find a vertical seam of minimum total energy.
 *         This is similar to the classic shortest path problem in an edge-weighted digraph
 *         except for the following:
 *                  - The weights are on the vertices instead of the edges.
 *                  - We want to find the shortest path from any of the W pixels in the top row to
 *                    any of the W pixels in the bottom row.
 *                  - The digraph is acyclic, where there is a downward edge from pixel (x, y)
 *                    to pixels (x − 1, y + 1), (x, y + 1), and (x + 1, y + 1), assuming that the
 *                    coordinates are in the prescribed range.
 *
 *      3. Seam removal. The final step is to remove from the image all of the pixels along the seam.
 *
 *
 */
public class SeamCarver {

    private int[][] cur_r;
    private int[][] cur_g;
    private int[][] cur_b;
    private double[][] cur_energies;

    /** Create a seam carver object based on the given picture.
     *
     *  The data type may not mutate the Picture argument to the constructor.
     *
     * @param picture - given picture.
     * @throws IllegalArgumentException if picture is null.
     */
    public SeamCarver(Picture picture){
        if (picture == null) throw new IllegalArgumentException("Null parameter exception.");
        int height = picture.height();
        int width = picture.width();
        cur_r = new int[width][height];
        cur_g = new int[width][height];
        cur_b = new int[width][height];
        cur_energies = new double[width][height];

        for (int x = 0; x < width; x++){
            Arrays.fill(cur_energies[x], -1);
            for (int y = 0; y < height; y++){
                Color color = picture.get(x, y);
                cur_r[x][y] = color.getRed();
                cur_g[x][y] = color.getGreen();
                cur_b[x][y] = color.getBlue();
            }
        }
    }

    // current picture
    public Picture picture(){
        Picture res_pic = new Picture(width(), height());
        for (int x = 0; x < width(); x++){
            for (int y = 0; y < height(); y++){
                Color color = new Color(cur_r[x][y], cur_g[x][y], cur_b[x][y]);
                res_pic.set(x, y, color);
            }
        }
        return res_pic;
    }

    // width of current picture
    // should take O(1) time in the worst case
    public int width(){
        return cur_r.length;
    }

    // height of current picture
    // should take O(1) time in the worst case
    public int height(){
        if (width() > 0)  return cur_r[0].length;
        else              return 0;
    }

    /** Energy of pixel.
     *
     * You will use the dual-gradient energy function:
     *  The energy of pixel (x,y) is sqrt(Δ^2_x(x,y)+Δ^2_y(x,y)), where
     *  the square of the x-gradient Δ^2_x(x,y) = R_x(x,y)^2 + G_x(x,y)^2 + B_x(x,y)^2,
     *  and where the central differences R_x(x,y), G_x(x,y), and B_x(x,y) are the differences
     *  in the red, green, and blue components between pixel (x + 1, y) and pixel (x − 1, y), respectively.
     *  The square of the y-gradient Δ^2_y(x,y) is defined in an analogous manner.
     *
     *  We define the energy of a pixel at the border of the image to be 1000, so that it is strictly larger
     *  than the energy of any interior pixel.
     *
     *  This method should take O(1) time in the worst case.
     *
     * @param x - number of the required column.
     * @param y - number of the required row.
     * @return - positive real number, energy of the (x, y) pixel.
     *           It is equal to 1000, if the (x, y) is the pixel from one of the borders.
     *
     * @throws IllegalArgumentException if any of (x, y) is out of its prescribed range.
     */
    public double energy(int x, int y) {
        if (x < 0 || x >= width() || y < 0 || y >= height())
            throw new IllegalArgumentException("Bad position of the required pixel. It's out of range.");

        if (cur_energies[x][y] != -1) {
            return cur_energies[x][y];
        }

        if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1)
            cur_energies[x][y] = 1000;
        else {
            double delta_x;
            double delta_y;
            delta_x = Math.pow(cur_r[x + 1][y] - cur_r[x - 1][y], 2)
                    + Math.pow(cur_g[x + 1][y] - cur_g[x - 1][y], 2)
                    + Math.pow(cur_b[x + 1][y] - cur_b[x - 1][y], 2);

            delta_y = Math.pow(cur_r[x][y + 1] - cur_r[x][y - 1], 2)
                    + Math.pow(cur_g[x][y + 1] - cur_g[x][y - 1], 2)
                    + Math.pow(cur_b[x][y + 1] - cur_b[x][y - 1], 2);
            cur_energies[x][y] = Math.sqrt(delta_x + delta_y);
        }
        return cur_energies[x][y];
    }

    // sequence of indices for horizontal seam
    // Should run in time O(width × height) in the worst case.
    public int[] findHorizontalSeam(){
        double[][] dist_to = new double[width()][height()];
        int[][] vert_from  = new int[width()][height()];

        for (int y = 0; y < height(); y++){
            if (y < width()) Arrays.fill(dist_to[y], Double.POSITIVE_INFINITY);
            dist_to[0][y] = energy(0, y);
        }
        for (int x = height(); x < width(); x++)
            Arrays.fill(dist_to[x], Double.POSITIVE_INFINITY);

        for (int x = 1; x < width(); x++){
            for (int y = 0; y < height(); y++){
                horizontalRelax(x, y, dist_to, vert_from);
            }
        }
        double best_val = Double.POSITIVE_INFINITY;
        int best_index = -1;
        for (int y = 0; y < height(); y++){
            if (dist_to[width()-1][y] < best_val){
                best_val = dist_to[width()-1][y];
                best_index = y;
            }
        }
        return horizontalRebuild(best_index, vert_from);
    }

    private void horizontalRelax(int x, int y, double[][] dist_to, int[][] vert_from){
        int left_bias  = 1;
        int right_bias = 1;
        if (y == 0)                left_bias = 0;
        else if (y == height()-1)  right_bias = 0;

        for (int prev_y = y - left_bias; prev_y <= y + right_bias; prev_y++){
            if (dist_to[x][y] > dist_to[x-1][prev_y] + energy(x, y)) {
                dist_to[x][y] = dist_to[x-1][prev_y] + energy(x, y);
                vert_from[x][y] = prev_y;
            }
        }

    }

    private int[] horizontalRebuild(int last_index, int[][] vert_from){
        int[] res = new int[width()];
        int cur_index = last_index;
        for (int x = width()-1; x >= 0; x--){
            res[x] = cur_index;
            cur_index = vert_from[x][cur_index];
        }
        return res;
    }

    // sequence of indices for vertical seam
    // Should run in time O(width × height) in the worst case.
    public int[] findVerticalSeam(){
        double[][] dist_to = new double[width()][height()];
        int[][] vert_from  = new int[width()][height()];

        for (int x = 0; x < width(); x++){
            Arrays.fill(dist_to[x], Double.POSITIVE_INFINITY);
            dist_to[x][0] = energy(x, 0);
            vert_from[x][0] = -1;
        }

        for (int y = 1; y < height(); y++){
            for (int x = 0; x < width(); x++){
                verticalRelax(x, y, dist_to, vert_from);
            }
        }
        double best_val = Double.POSITIVE_INFINITY;
        int best_index = -1;
        for (int x = 0; x < width(); x++){
            if (dist_to[x][height()-1] < best_val){
                best_val = dist_to[x][height()-1];
                best_index = x;
            }
        }
        return verticalRebuild(best_index, vert_from);
    }

    private void verticalRelax(int x, int y, double[][] dist_to, int[][] vert_from){
        int left_bias  = 1;
        int right_bias = 1;
        if (x == 0)               left_bias = 0;
        else if (x == width()-1)  right_bias = 0;

        for (int prev_x = x - left_bias; prev_x <= x + right_bias; prev_x++){
            if (dist_to[x][y] > dist_to[prev_x][y-1] + energy(x, y)) {
                dist_to[x][y] = dist_to[prev_x][y-1] + energy(x, y);
                vert_from[x][y] = prev_x;
            }
        }
    }

    private int[] verticalRebuild(int last_index, int[][] vert_from){
        int[] res = new int[height()];
        int cur_index = last_index;
        for (int y = height()-1; y >= 0; y--){
            res[y] = cur_index;
            cur_index = vert_from[cur_index][y];
        }
        return res;
    }

    /** Remove horizontal seam from current picture.
     *
     * Should run in time O(width × height) in the worst case.
     *
     * @param seam - the result of the findHorizontalSeam method.
     * @throws IllegalArgumentException if
     *                  - seam is null
     *                  - length of seam differs from the width
     *                  - an entry from the seam is outside of its prescribed range
     *                  - two adjacent entries differ by more than 1
     *                  - the height of the picture is less than or equal to 1
     */
    public void removeHorizontalSeam(int[] seam){
        if (height() <= 1) throw new IllegalArgumentException("The picture has too small height for such operation");
        validateSeam(seam, width(), height());
        int[][] next_r = new int[width()][height()-1];
        int[][] next_g = new int[width()][height()-1];
        int[][] next_b = new int[width()][height()-1];
        double[][] next_energies = new double[width()][height()-1];
        for (int x = 0; x < width(); x++){
            System.arraycopy(cur_r[x], 0, next_r[x], 0, seam[x]);
            System.arraycopy(cur_r[x], seam[x]+1, next_r[x], seam[x], height() - 1 - seam[x]);

            System.arraycopy(cur_g[x], 0, next_g[x], 0, seam[x]);
            System.arraycopy(cur_g[x], seam[x]+1, next_g[x], seam[x], height() - 1 - seam[x]);

            System.arraycopy(cur_b[x], 0, next_b[x], 0, seam[x]);
            System.arraycopy(cur_b[x], seam[x]+1, next_b[x], seam[x], height() - 1 - seam[x]);
            Arrays.fill(next_energies[x], -1);
        }
        cur_r = next_r;
        cur_g = next_g;
        cur_b = next_b;
        cur_energies = next_energies;

    }

    /** Remove vertical seam from current picture.
     *
     * Should run in time O(width × height) in the worst case.
     *
     * @param seam - the result of the findVerticalSeam method.
     * @throws IllegalArgumentException if
     *                  - seam is null
     *                  - length of seam differs from the height
     *                  - an entry from the seam is outside of its prescribed range
     *                  - two adjacent entries differs by more than 1
     *                  - the width of the picture is less than or equal to 1
     */
    public void removeVerticalSeam(int[] seam){
        if (width() <= 1) throw new IllegalArgumentException("The picture has too small width for such operation.");
        validateSeam(seam, height(), width());
        int[][] next_r = new int[width()-1][height()];
        int[][] next_g = new int[width()-1][height()];
        int[][] next_b = new int[width()-1][height()];
        double[][] next_energies = new double[width()-1][height()];

        for (int y = 0; y < height(); y++){
            for (int x = 0; x < seam[y]; x++){
                next_r[x][y] = cur_r[x][y];
                next_g[x][y] = cur_g[x][y];
                next_b[x][y] = cur_b[x][y];
                next_energies[x][y] = -1;
            }
            for (int x = seam[y]+1; x < width(); x++){
                next_r[x-1][y] = cur_r[x][y];
                next_g[x-1][y] = cur_g[x][y];
                next_b[x-1][y] = cur_b[x][y];
                next_energies[x-1][y] = -1;
            }
        }
        cur_r = next_r;
        cur_g = next_g;
        cur_b = next_b;
        cur_energies = next_energies;
    }

    /** Validation of the seam of the current picture.
     *
     * @param seam - the result of the findHorizontalSeam or findVerticalSeam method.
     * @param required_len   - required length of the seam
     * @param required_range - required range of each entry
     * @throws IllegalArgumentException if
     *                  - seam is null
     *                  - length of seam differs from the required
     *                  - an entry from the seam is outside of its required range
     *                  - two adjacent entries differ by more than 1
     */
    private void validateSeam(int[] seam, int required_len, int required_range){
        if (seam == null) throw new IllegalArgumentException("Null parameter exception.");
        if (seam.length != required_len) throw new IllegalArgumentException("Bad length of the seam.");
        for (int i = 0; i < seam.length; i++){
            if (seam[i] < 0 || seam[i] >= required_range)
                throw new IllegalArgumentException(i + "-th entry of the seam is out of its prescribed range.");
            if (i < seam.length-1)
                if (Math.abs(seam[i] - seam[i+1]) > 1)
                    throw new IllegalArgumentException(i + "-th and " + (i+1) + "-th entries differ by more that 1.");
        }
    }

    //  unit testing (optional)
    public static void main(String[] args){
        Picture test_pic = new Picture("files/week2/test.jpg");
        SeamCarver test  = new SeamCarver(test_pic);
        test_pic.show();
        In in = new In();
        in.readChar();

        for (int i = 0; i < 100; i++){
            int[] horizontal_seam = test.findHorizontalSeam();
            test.removeHorizontalSeam(horizontal_seam);
        }

        for (int i = 0; i < 200; i++){
            int[] vertical_seam = test.findVerticalSeam();
            test.removeVerticalSeam(vertical_seam);
        }
        test.picture().show();

    }

}
