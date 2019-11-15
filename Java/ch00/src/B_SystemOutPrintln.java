import java.util.ArrayList;
import java.util.HashMap;

public class B_SystemOutPrintln{

	public static void main(String[] args){
		HashMap<String, ArrayList<String>> temp = new HashMap<String, ArrayList<String>>();
		ArrayList<String> bloockUsers = new ArrayList<String>();

		bloockUsers.add("1");
		bloockUsers.add("2");
		bloockUsers.add("3");

		temp.put("first", bloockUsers);
		bloockUsers = new ArrayList<String>();

		bloockUsers.add("4");
		bloockUsers.add("5");
		bloockUsers.add("6");
		bloockUsers.add("7");

		temp.put("second", bloockUsers);



		for(int i = 0; i < temp.get("first").size() ; i++){
			System.out.println(temp.get("first").get(i));
		}
		System.out.println();
		System.out.println();
		for(int i = 0; i < temp.get("second").size() ; i++){
			System.out.println(temp.get("first").get(0));
			System.out.println(temp.get("second").get(i));
		}



	}
}