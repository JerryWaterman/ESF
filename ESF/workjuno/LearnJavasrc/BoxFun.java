import java.util.*;

public class BoxFun {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ColoredRectangle r1 = new ColoredRectangle();
		ColoredRectangle r2 = new ColoredRectangle();

		System.out.println("Enter when ready");
		Scanner stdin = new Scanner(System.in);
		stdin.nextLine();
		
		r1.paint();
		r2.paint();
	}
}
