
public class OSUtil {
	public static boolean isWindows(){
		return System.getProperties().getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1;
	}
}
