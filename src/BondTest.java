public class BondTest {
    public static void main(String[] args) {
        Bond b1 = new Bond(1, 2);
        Bond b2 = new Bond(3, 4);
        int[] ret = b1.get();
        System.out.println(ret[0] + ", " + ret[1]);
    }
}