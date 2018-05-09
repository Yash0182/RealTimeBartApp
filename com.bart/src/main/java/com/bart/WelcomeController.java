package com.bart;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WelcomeController {
	
	
	private List<StationDetail> stationList = new ArrayList();
    private List stationDetail = new ArrayList();
    
    public JSONObject getJSONResponse(String address) throws Exception
    {
        
        String titleArguement = address;
         URL obj = new URL(titleArguement);
         HttpURLConnection con = (HttpURLConnection) obj.openConnection();
         con.setRequestMethod("GET");
         con.setRequestProperty("User-Agent", "Mozilla/5.0");
         int responseCode = con.getResponseCode();
         System.out.println("\nSending 'GET' request to URL : " + titleArguement);
         BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
         String inputLine;
         StringBuffer response = new StringBuffer();
         while ((inputLine = in.readLine()) != null) 
         {
             
            response.append(inputLine);
            System.out.println("Response "+response);
         }
         in.close();     
         JSONObject myResponse = new JSONObject(response.toString());
         return (myResponse);
    }
	
	@RequestMapping("/welcome")
	public ModelAndView welcomePage()
	{
		ModelAndView mv = new ModelAndView("index");
		//mv.addObject("msg","Hello Yash");
		mv.addObject("msg","Hello, Welcome to the Bart APP ");
		return mv;
	
	}
	
	
	public void setStations() throws Exception
    {
		 stationList = new ArrayList();
         JSONObject myResponse = this.getJSONResponse("http://api.bart.gov/api/stn.aspx?cmd=stns&key=MW9S-E7SL-26DU-VV8V&json=y");
         JSONObject tempStation = myResponse.getJSONObject("root");
         JSONObject tempStations = tempStation.getJSONObject("stations");
         JSONArray stationDetails = tempStations.getJSONArray("station");
         for(int i=0;i<stationDetails.length();i++)
         {
             stationList.add(new StationDetail(((stationDetails.getJSONObject(i)).getString("city")+" "+(stationDetails.getJSONObject(i)).getString("address")),((stationDetails.getJSONObject(i)).getString("abbr"))));
         }
    }
    
    public List getStations()
    {   
        try
        {
        this.setStations();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        return stationList;
    }
    
	
	@RequestMapping("/stations")
	public ModelAndView stations()
	{
		ModelAndView mv = new ModelAndView("stationlist");
		getStations();
		mv.addObject("stationList",stationList);
		mv.addObject("msg","Stations");
		return mv;
	}
	
	
	@RequestMapping("/tripsview")
	public ModelAndView tripsView()
	{
		ModelAndView mv = new ModelAndView("tripsview");
		getStations();
		mv.addObject("stationList",stationList);
		return mv;
		
	}
	
	public List getRealTimeDeparture(String source)
    {
        List bartTime = new ArrayList();
        try
        {
            
        String arguement = " http://api.bart.gov/api/etd.aspx?cmd=etd&orig="+source+"&key=MW9S-E7SL-26DU-VV8V&json=y";
        JSONObject myResponse  = this.getJSONResponse(arguement);
        JSONArray stationDateTimeDetails = myResponse.getJSONObject("root").getJSONArray("station");
        JSONObject etddateTimeDetails = stationDateTimeDetails.getJSONObject(0);
        JSONArray dateTimeDetails = etddateTimeDetails.getJSONArray("etd");
        JSONArray estimateArrival = dateTimeDetails.getJSONObject(0).getJSONArray("estimate");
        for(int i=0;i<estimateArrival.length();i++)
        {
            bartTime.add(estimateArrival.getJSONObject(i).getString("minutes"));
        }
        }
        catch(Exception e)
        {
            e.getMessage();
        }
        return bartTime;
    }
	
	
	
	
	@RequestMapping(value="/trips",method=RequestMethod.GET)
	public ModelAndView trips(@RequestParam("source")String sourceFull, @RequestParam("destination")String destinationFull )
	{
		String sourceArray[] = sourceFull.split(" ");
		String source = sourceArray[sourceArray.length-1];
		String destinationArray[] = destinationFull.split(" ");
		String destination = destinationArray[destinationArray.length-1];
		System.out.println(source);
		ModelAndView mv = new ModelAndView("trip");
		try
        {
        String aruguement = "https://api.bart.gov/api/sched.aspx?cmd=fare&orig="+source+"&dest="+destination+"&key=MW9S-E7SL-26DU-VV8V&json=y";
        List timings = this.getRealTimeDeparture(source);
        JSONObject myResponse  = this.getJSONResponse(aruguement);
        System.out.print(source + "->" + destination+ "  " );
        System.out.println("Fare: "+ myResponse.getJSONObject("root").getJSONObject("trip").getString("fare"));
        String fare = myResponse.getJSONObject("root").getJSONObject("trip").getString("fare");
        System.out.println(timings);
        mv.addObject("timings", timings);
        mv.addObject("fare",fare);
        mv.addObject("trip", sourceFull+"  TO  "+destinationFull);
        }catch(Exception e)
        {
         System.out.println(e.getMessage());
        }
		
		return mv;
	}
	
	
	@RequestMapping("/stationInfo")
	public ModelAndView stationInfo()
	{
		ModelAndView mv = new ModelAndView("stationInfo");
		getStations();
		mv.addObject("stationList",stationList);
		return mv;
		
	}

	public void setStationDetails(String station) throws Exception
    {
        String arguement = "http://api.bart.gov/api/stn.aspx?cmd=stninfo&orig="+station+"&key=MW9S-E7SL-26DU-VV8V&json=y";
        
         JSONObject myResponse = this.getJSONResponse(arguement);
         JSONObject tempStation = myResponse.getJSONObject("root");
         this.stationDetail.add(tempStation);
         System.out.println("Yash"+" "+station+" "+stationDetail);
    }
    
	@RequestMapping(value="/station",method=RequestMethod.GET)
    public ModelAndView getStationDetails(@RequestParam("source")String station)
    {   
		ModelAndView mv = new ModelAndView("station");
        try
        {
        this.setStationDetails(station);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        mv.addObject("stationDetail",stationDetail);
        return mv;
    }
	
}
