public class EchoClassLoader
{
	public static void main (String args[])
		{
		ClassLoader current = new EchoClassLoader().getClass().getClassLoader();
			while (current != null)
			{
				System.out.println(current.getClass());
				current = current.getParent();
			}
		}
}