
public class VersionUtil {
	public static String getV(){
		return SystemProperty.getValue("version");
		//return System.currentTimeMillis()+"";
	}
}
