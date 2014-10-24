package finalproject;
import java.util.*;


public class comparison {
  private city city1;
  private city city2;
//------------------------------------------------------------------
public comparison(){
	city1=null;
	city2=null;
}
//------------------------------------------------------------------
public void setData(city info1, city info2){
	city1=info1;
	city2=info2;
}
//------------------------------------------------------------------
public String getNames(){
	return " " + city1.getName() + "  and " + city2.getName();
}
//------------------------------------------------------------------
public Double prop(HashMap<String, Double> hashMap, HashMap<String, Double> hashMap2){
	HashMap<String,Double> absDifference= new HashMap<String,Double>();
	Iterator<String> ct1= hashMap.keySet().iterator();
	Iterator<String> ct2;
	
	//Iterate through both lists, then take the absolute difference of the proportions that exist
	while(ct1.hasNext()){
		String key1= ct1.next();
		ct2= hashMap2.keySet().iterator();
		while(ct2.hasNext()){
			String key2= ct2.next();
			if(key2.equals(key1)){
			absDifference.put(key1,(double) Math.abs(hashMap.get(key1)-hashMap2.get(key2)));	
			}
		}
	}
	
	return getAverage(absDifference);
	
}


//-----------------------------------------------------------------
public double getAverage(HashMap<String,Double> data){
	
	double totalValues=0;
	Iterator<Double> it= data.values().iterator();
	while(it.hasNext()){
		Double value= it.next();
		totalValues += value;
	}
	return(totalValues/data.size());
	
}
//------------------------------------------------------------------
public Double getComparisonValue(){
	
	Double userData= prop(city1.getProportions(city1.getUserMentions()),city2.getProportions(city2.getUserMentions()));
	Double hashTagData= prop(city1.getProportions(city1.getHashtags()),city2.getProportions(city2.getHashtags()));
	Double upperCaseData= prop(city1.getProportions(city1.getUpperWords()),city2.getProportions(city2.getUpperWords()));
	
	return ((userData+ hashTagData + upperCaseData)/3);
}

}
