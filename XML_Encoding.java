package misc;

import java.util.HashMap;

public class XML_Encoding {
	
	public static class Element{
		
		public String text;
		Element(String text){
			
			StringBuilder cleaned = new StringBuilder();
			for(int i = 0;i < text.length();i++) {
				String s = text.substring(i, i+1);
				if(s.equals("\"")){
					continue;
				}
				cleaned.append(s);
			}
			
			this.text = cleaned.toString();
		}
	}
	
	public static class Atribute{
		
		public HashMap<String,Integer> map;
		
		Atribute(HashMap<String,Integer> map){
			this.map = map;
		}
	}
	
	public String encode_unit(Element e,Atribute a,int strt,int end){
		
		//end is : ">" and strt is : ">"
		//A unit is something like: <atribute atribute ... atribute=thing atribute atribute ... atribute =thing>
		
		StringBuilder encoded = new StringBuilder();
	
		StringBuilder current = new StringBuilder();	
		boolean is_thing = false;
		for(int i = strt+1;i < end;i++){
			String s = e.text.substring(i,i+1);
			
			if(is_thing && i+1 == end){
				encoded.append(current);
			}
			
			if(s.equals(" ")){
				String str_curr = current.toString();
				if(is_thing){
					encoded.append(str_curr);
					encoded.append(" ");
				}
				if(!is_thing){
					if(a.map.containsKey(str_curr)){
						encoded.append(a.map.get(str_curr));
						encoded.append(" ");
					}
					else {
						// * here means no id
						encoded.append("*");
						encoded.append(" ");
					}
				}
				is_thing = false;
				current = new StringBuilder();
				continue;
			}
			if(s.equals("=")){
				String str_curr = current.toString();
				//System.out.println(str_curr);
				if(a.map.containsKey(str_curr)){
					encoded.append(a.map.get(str_curr));
					encoded.append(" ");
				}
				else {
					// * here means no id
					encoded.append("*");
					encoded.append(" ");
				}
				current = new StringBuilder();
				is_thing = true;
				continue;
			}
			current.append(s);
			
			
		}
		
		return encoded.toString();	
	}
	
	public String encode(Element e,Atribute a) {
		
		StringBuilder encoded = new StringBuilder();
		
		int strt = 0;
		boolean is_end = false;
		boolean is_message = false;
		for(int i = 0;i < e.text.length();i++) {
			
			String s = e.text.substring(i, i+1);
			
			
			if(s.equals("<")){
				if(is_message) {
					is_message = false;
					encoded.append(" ");
				}
				strt = i;
				//System.out.println("strt: "+strt);
			}
			
			if(is_message){
				encoded.append(s);
			}
			
			if(s.equals(">")){
				if(i+2 <= e.text.length() && !e.text.substring(i+1,i+2).equals("<")){
					is_message = true;
				}
				if(is_end) {
					encoded.append(" 0 ");
					is_end = false;
				}
				else {
					encoded.append(this.encode_unit(e, a, strt, i+1));
					encoded.append(" 0 ");
					strt = 0;
				}
			}
			
			if(s.equals("/")) {
				//System.out.println(end);
				is_end = true;
			}
			
			
			
		}
		return encoded.toString();
	}
	
	public Atribute create_atribute(HashMap<String,Integer> map){
		return new Atribute(map);
	}
	
	public Element create_element(String text){
		return new Element(text);
	}
	

}
