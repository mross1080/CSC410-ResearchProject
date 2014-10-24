require 'twitter'
require 'yaml'


#OpenSSL::SSL::VERIFY_PEER = OpenSSL::SSL::VERIFY_NONE

client = Twitter::REST::Client.new do |config|
  config.consumer_key        = ENV["consumer_key"]
  config.consumer_secret     = ENV["consumer_secret"]
  config.access_token        = ENV["access_token"]
  config.access_token_secret = ENV["access_token_secret"]
end

geocodes={"la" => "34.101509,-118.32691,50mi", "ny" => "40.736150,-73.991309,50mi", "london" => "51.521810,-0.04656899999997677,50mi" }

#Make api call to Twitter for each city, and create new yaml file for each results file
geocodes.each do | city, geocode|
  results = client.search("#song", :result_type => "mixed", :geocode => geocode).collect do |tweet|
     "#{tweet.user.screen_name}: #{tweet.text} "
   end

   writeFile = File.open("#{city}tweets.yaml", 'a') { |f| f.write(results)}

end



