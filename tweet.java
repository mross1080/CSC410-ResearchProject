package finalproject;


import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class tweet {
	 private static Scanner keyboard = new Scanner(System.in);
	public static void main(String[] args) throws IOException {
		
		/**
		 * Reads in files, than initiates objects
		 */
		ArrayList <String> cityName= new ArrayList<String>();
		String line = null;
	
		ArrayList<city> info= new ArrayList<city>();
		city london = new city("london");
		city la = new city("la");
		city ny = new city("ny");
		
		info.add(ny);
		info.add(la);
		info.add(london);
		String answer;
		
		
		for(city place: info){
			
			String name =place.getName();
			BufferedReader cityreader = new BufferedReader(new FileReader("/Users/MatthewRoss/Desktop/CSC410/" + name + "tweets.yaml"));
			
			while ((line = cityreader.readLine()) != null) {
				if(cityreader.readLine() != null){
			       cityName.add( cityreader.readLine());
				}			     
			}
			
			System.out.println("Current city is " + place.getName());
			place.setTweets(storeTweets(cityName));
			place.setUsers(findUsers(cityName),findUniqueTweeters(cityName));
			place.setStopWords(stopWords(cityName));
			place.setUpperCaseWords(getUpperCaseWords(cityName));	
			System.out.println("Would you like to see the information for this city?");
			answer= keyboard.next();
			if(answer.equals("y")){
				topValues(place.getTweets(),0);
				topValues(place.getUsers(),0);
				topValues(place.getUpperCaseWords(),0);
			}
		}
		calculate(info);
		
	}
	
	public static void topValues(HashMap<String,Integer> values, int index){
		/**
		 * Prints out all values greater than index to console
		 */
		Iterator<String> it= values.keySet().iterator();
		while(it.hasNext()){
			String key= it.next();
			if(values.get(key) > index){
				System.out.println(key + " Frequency: "+ values.get(key));
			}
		}
	}
	
	public static ArrayList<comparison> calculate(ArrayList<city> info){
		/**
		 * Initiates Comparison objects and prints out final value
		 */
		
		//Structures to hold distribution information
		ArrayList<comparison> results= new ArrayList<comparison>();
			
		for(int i=0; i<info.size()-1; i++){
			for(int j=i+1; j< info.size(); j++){
				comparison compare= new comparison();
				results.add(compare);
				compare.setData(info.get(i), info.get(j));
				System.out.println("The similarity ratio for" + compare.getNames() + " is " + compare.getComparisonValue());
			
			}
		}
		  return results;
	}	
		
	
public static HashMap<String,Integer> findUniqueTweeters(ArrayList<String> cityName){
	/*
	 * Finds the person who submitted the tweet, and how many of their tweets appear in the data set
	 */
	String userName="";
	int nameFrequency=0;
	HashMap<String,Integer> ut= new HashMap<String,Integer>();
	
		for(int i=0; i< cityName.size()-3; i++){
			String name= cityName.get(i);
		if(name != null){
		for(int x=3; x< name.length(); x++){
			if(name.charAt(x) != ':'){
				userName += name.charAt(x);
			}else{
				if(!ut.containsKey(userName)){
				    ut.put(userName, nameFrequency+1);
			   } else {
					  ut.put(userName,(ut.get(userName)+1));
			   }
				
				userName="";
				break;
			}
		}
		}
	}
	return ut;
	
}	
	

public static ArrayList<ArrayList<String>> getIndividualWords(ArrayList<String> cityName) {
	/**
	 * Inserts each word in each tweet into a data structure
	 */
	ArrayList<String> individualWords= new ArrayList<String>();
	String sen="";
	String newWord="";
	ArrayList <ArrayList<String>> sentences= new ArrayList<ArrayList<String>>();
	
	for(int y=0; y< cityName.size(); y++){
		sen=cityName.get(y);
		if(sen != null){
	      for(int m=0; m< sen.length();m++){
		    if(sen.charAt(m) != ' '){
			  newWord += sen.charAt(m);
			  if(m< (sen.length()-1)){
				if(sen.charAt(m+1) == ' '){
					individualWords.add(newWord);
					newWord="";
				}
			  }
		    }
	      } 
	  sentences.add(individualWords);
	  individualWords= new ArrayList<String>();
	
	  }
	}
	
	return sentences;
}

   public static HashMap<String,Integer> getUpperCaseWords(ArrayList<String> cityName){
	   /**
	    * Finds the uppercase words and frequencies in the data
	    */
	   ArrayList <ArrayList<String>> sentences= getIndividualWords(cityName);
	   HashMap <String,Integer> upperWords= new HashMap<String,Integer>();
	   String currentWord="";
	   int upperWordFrequency=0;
	   for(int x=0; x<sentences.size(); x++){
			//For Every word in each Tweet
			for(int y=0; y< sentences.get(x).size(); y++){
				currentWord =sentences.get(x).get(y);
					if(Character.isUpperCase(currentWord.charAt(0))){
						if(!upperWords.containsKey(currentWord)){
						    upperWords.put(currentWord, upperWordFrequency+1);
					   } else {
							  upperWords.put(currentWord,(upperWords.get(currentWord)+1));
					   }
					}
				
			}
	   }
	   
	return upperWords;
  }
   
	public static ArrayList<ArrayList<String>> stopWords(ArrayList<String> cityName) throws IOException{
		/**
		 * Reads in stop words and checks each word in the data set against the Stop Words
		 * Replaces any stop words with STOP
		 */
		
		BufferedReader reader = new BufferedReader(new FileReader("/Users/MatthewRoss/Desktop/CSC410/english.txt"));
		ArrayList<String>stopWords = new ArrayList<String>();
		String line= "";
		
		//Take Stop words from file and add them to stopWords array list
		while ((line = reader.readLine()) != null) {
			   //  System.out.print(reader.readLine() + "\n");
			     stopWords.add( reader.readLine());
			}
		
			ArrayList <ArrayList<String>> sentences= getIndividualWords(cityName);
			String s;
			String currentWord;
		
			//For Every Tweet
			for(int x=0; x<sentences.size(); x++){
				//For Every word in each Tweet
				for(int y=0; y< sentences.get(x).size(); y++){
					//For Every StopWord
					for(int n=0; n<stopWords.size(); n++){
					s= stopWords.get(n);
					currentWord= sentences.get(x).get(y);
			          if(currentWord.equals(s)){
				        sentences.get(x).remove(y);
			            sentences.get(x).add(y, "STOP");	
			          }
					}
				}
			}
	
		return sentences;
		
	}
	
	
	public static HashMap<String,Integer> processRegExp(ArrayList<String> cityName,String patternString){
	/**
	 * Takes in a regular expression and data and returns all the matches of those regular expressions
	 */
		HashMap<String,Integer> data=new HashMap<String,Integer>();
		int userFrequency=0;
		Pattern pattern = Pattern.compile(patternString);
		
		for(String tweet: cityName ){
			if( tweet != null){
		    Matcher matcher = pattern.matcher(tweet);
		    String match= "";
		      while(matcher.find()) {
		        int start= matcher.start();
		        int end= matcher.end();
		        //Building the string hashtag from elements of matches
		          while(start < end){
		    	  match += tweet.charAt(start);
		    	  start++;
		    	  } 
		          if(!data.containsKey(match)){
			        data.put(match, userFrequency+1);
		          } else {
				    data.put(match,(data.get(match)+1));
		          }
		        match="";
		        start++;
		      }
		    }
	    }
		return data; 
	}
	
	public static HashMap<String,Integer> findUsers(ArrayList<String> cityName) throws IOException{
		/**
		 * Defines the regular expression to find users and calls the regular expression method
		 */
		String patternString = "@[a-zA-z0-9]*";
		return processRegExp(cityName,patternString);
		
	}
	public static HashMap<String,Integer> storeTweets(ArrayList<String> cityName) throws IOException{
		/**
		 * Defines the regular expression to find hashtags and calls the regular expression method
		 */
		String patternString = "#[a-zA-z0-9]*";
		return processRegExp(cityName,patternString);

	}
	
}

