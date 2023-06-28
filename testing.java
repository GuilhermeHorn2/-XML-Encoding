package misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class main {

	public static void main(String[] args) {
		
		//String text1 = "<family lastName=\"McDowell\" state=\"CA\">";
		String text1 = "<familly lastName=McDowell state=CA>";
		String text2 ="<person firstName=Gayle>Some Message</person></familly>";
		HashMap<String,Integer> map = new HashMap<>();
		map.put("familly", 1);
		map.put("person", 2);
		map.put("firstName", 3);
		map.put("lastName", 4);
		map.put("state", 5);
		XML_Encoding enc = new XML_Encoding();
		String encoded = enc.encode(enc.create_element(text1+text2),enc.create_atribute(map));
		System.out.println(encoded);
		
		
}
	


}
