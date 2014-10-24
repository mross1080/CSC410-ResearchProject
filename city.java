package finalproject;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

public class city {
	private String cityName;
	private HashMap<String,Integer> cityTweets;
	private ArrayList<String> userMentions;
	private HashMap<String,Integer> cityUsers;
	private ArrayList<ArrayList<String>> noStopWords;
	private HashMap<String,Integer> upperCaseWords;
	private ArrayList<String> upperWords;
	private HashMap<String,Integer> uniqueUsers;
	private int hashtagFrequency;
	private double userMentionRatio;
	private HashMap<String,Integer> wordFrequencies;
	private ArrayList<String> hashtags;
	
//----------------------------------
	public city(){
		cityName="";
		cityTweets=null;
		cityUsers=null;
		noStopWords= null;
		upperCaseWords=null;
		uniqueUsers=null;
		hashtagFrequency= 0;
		
	}
//-----------------------------------------------------------------------------
	public city(String name){
		cityName= name;
	}
//------------------------------------------------------------------------
	public String getName(){
		return cityName;
	}
//-------------------------------------------------------------------------------------------
	public void setTweets(HashMap<String,Integer> cityInfo) throws IOException{
		
		cityTweets =cityInfo;
		hashtagFrequency= cityTweets.size();
	    hashtags= new ArrayList<String>();
		Iterator<String> it= cityTweets.keySet().iterator();
		while(it.hasNext()){
			String key= it.next();
			hashtags.add(key);
		}
		
	}
//-----------------------------------------------------------------------------------------
	public ArrayList<String> getHashtags(){
		return hashtags;
	}
//-------------------------------------------------------------------------------------------
	public void setUsers(HashMap<String,Integer> cityInfo,HashMap<String,Integer> users) throws IOException{
		
	cityUsers= cityInfo;
	uniqueUsers= users;
	userMentionRatio= cityUsers.size()/uniqueUsers.size();
	userMentions= new ArrayList<String>();
	Iterator<String> it= cityUsers.keySet().iterator();
	while(it.hasNext()){
		String key= it.next();
		userMentions.add(key);
	}
		
	}
	//-------------------------------------------------------------------------------------------
    public double getUserMentionRatio(){
	  return userMentionRatio;
    }
//---------------------------------------------------------------------------------------------
    public ArrayList<String> getUserMentions(){
	  return userMentions;
    }
	//-------------------------------------------------------------------------------------------
	public HashMap<String,Integer> getUniqueUsers(){		
	  return uniqueUsers;	
    }
//-------------------------------------------------------------------------------------------
	public HashMap<String,Integer> getUsers(){
		return cityUsers;	
	}
//-----------------------------------------------------------------------------------------
    public HashMap<String,Integer> getTweets(){
		return cityTweets;	
	}
//------------------------------------------------------------------------------------------
public void setStopWords( ArrayList<ArrayList<String>> info){
	
	noStopWords= info;
	String currentWord;
	wordFrequencies = new HashMap<String,Integer>();
	for(int y=0; y <getStopWords().size(); y++){
		
		for(int z=0; z< getStopWords().get(y).size(); z++){
			currentWord = getStopWords().get(y).get(z);
			
		if(!wordFrequencies.containsKey(currentWord)){
		    wordFrequencies.put(currentWord, 1);
	   } else {
			  wordFrequencies.put(currentWord,(wordFrequencies.get(currentWord)+1));
	   }
		}
}
	//return noStopWords;
}
//----------------------------------------------------------------------------
public HashMap<String,Double> getProportions(ArrayList<String> data){
	
	//Iterator <String> user= cityUsers.keySet().iterator();
	String word= "";
	double count=0;
	HashMap<String,Double> userProportions= new HashMap<String,Double>();
	
	if(data != null){
	for(int i=0; i< data.size(); i++){
		word= data.get(i);
	for(int x=0; x< noStopWords.size(); x++){
		for(int y=0; y<noStopWords.get(x).size(); y++){
			if(noStopWords.get(x).get(y).contains(word)){
				count++;
			}
		}
		}
		userProportions.put(word, (count/(double)noStopWords.size()));
		count=0;
	}
	}
	return userProportions;
	}
	


//------------------------------------------------------------------------------
public HashMap<String,Double> getUserProportions(){
	
	//Iterator <String> user= cityUsers.keySet().iterator();
	String word= "";
	double count=0;
	HashMap<String,Double> userProportions= new HashMap<String,Double>();
	
	if(userMentions != null){
	for(int i=0; i< userMentions.size(); i++){
		word= userMentions.get(i);
	for(int x=0; x< noStopWords.size(); x++){
		for(int y=0; y<noStopWords.get(x).size(); y++){
			if(noStopWords.get(x).get(y).contains(word)){
				count++;
			}
		}
		}
		userProportions.put(word, (count/(double)noStopWords.size()));
		count=0;
	}
	}
	return userProportions;
	}
	
//------------------------------------------------------------------------------
  public HashMap<String,Integer> getWordFrequencies(){
	return wordFrequencies;
  }
//-----------------------------------------------------------------------------------
  public ArrayList<ArrayList<String>> getStopWords(){
	return noStopWords;
  }
//---------------------------------------------------------------------------------
  public void setUpperCaseWords(HashMap<String,Integer> info){
    upperCaseWords=info;
    upperWords= new ArrayList<String>();
	Iterator<String> it= upperCaseWords.keySet().iterator();
	while(it.hasNext()){
		String key= it.next();
		upperWords.add(key);
	}
  }
//------------------------------------------------------------------------------
  public ArrayList<String> getUpperWords(){
	return upperWords;
  }
//------------------------------------------------------------------------------
  public HashMap<String,Integer> getUpperCaseWords(){
	return upperCaseWords;
  }

}


