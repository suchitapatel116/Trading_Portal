import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.*;

@WebServlet("/DataVisualization")

public class DataVisualization extends HttpServlet
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		HttpSession session = request.getSession();

		StringBuffer sb = new StringBuffer();
		sb.append("<section id='content'><div class='row'>");
		sb.append("<h3 class='title'>Goods on Sale</h3>");

		sb.append("<script src=\"https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/markerclusterer.js\"/>");
    	sb.append("<script src=\"C:/tomcat-7.0.34-preconfigured/apache-tomcat-7.0.34/webapps/project2_merged/markerclusterer.js\"/>");
    	sb.append("<meta name=\"viewport\" content=\"initial-scale=1.0, user-scalable=no\">");
    	sb.append("<meta charset=\"utf-8\">");
    	sb.append("<script src=\"http://maps.google.com/maps/api/js?sensor=false\"></script>");
		sb.append("<script async defer src=\"https://maps.googleapis.com/maps/api/js?key=AIzaSyBC8vL-dc5qoUhzlKbYFfwOjE9EgVIyEAQ&callback=initialize\"></script>");
    	sb.append("<meta http-equiv='content-type' content='text/html; charset=utf-8' />");
		sb.append("<script type='text/javascript' src='https://maps.googleapis.com/maps/api/js?sensor=false'></script>");
    	sb.append("<script type='text/javascript' src='../src/data.json'></script>");
    	sb.append("<script type='text/javascript' src='../src/markerclusterer.js'></script>");

    	sb.append("<div id='map-canvas' style='width: 100%; height: 400px;' ></div>");

    	sb.append("<script>");

    	List<String> list = MySqlDataStoreUtilities.getZipcodes();
    	if(list != null){
    		sb.append("var postalCodes = [ ");
    		for(String val : list)
    			sb.append("'"+val+"',");
    		sb.append("];");
		}

		sb.append("function initialize() {"
    	+"var map = new google.maps.Map(document.getElementById('map-canvas'), {"
        +"center: new google.maps.LatLng(41.881832, -87.623177),"
        +"zoom:   10"
    	+"});"
    
    	+"var geocoder = new google.maps.Geocoder();"
    
    	+"google.maps.event.addListenerOnce(map, 'tilesloaded', function() {"

        +"for (var i = 0; i < postalCodes.length; ++i) {"
            +"geocoder.geocode({"
                +"address: postalCodes[i],"
                +"region: 'DE'"
            +"}, function(result, status) {"
                +"if (status == 'OK' && result.length > 0) {"

                    +"new google.maps.Marker({"
                        +"position: result[0].geometry.location,"
                        +"map: map"
                    +"});"
                +"}"
            +"});"
	        +"}"
	    +"});"
		+"}"

		+"google.maps.event.addDomListener(window, 'load', initialize);"
    	+"</script>");

    	sb.append("<style> #map { height: 100%; } </style>");
    	sb.append("<div id='map'></div><script>");


    	sb.append("function initMap() {"

        +"var map = new google.maps.Map(document.getElementById('map'), {"
          +"zoom: 3,"
          +"center: {lat: -28.024, lng: 140.887}"
        +"});"

        // Create an array of alphabetical characters used to label the markers.
        +"var labels = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';"

        // Add some markers to the map.
        // Note: The code uses the JavaScript Array.prototype.map() method to
        // create an array of markers based on a given "locations" array.
        // The map() method here has nothing to do with the Google Maps API.
        +"var markers = locations.map(function(location, i) {"
          +"return new google.maps.Marker({"
            +"position: location,"
            +"label: labels[i % labels.length]"
          +"});"
        +"});"

        // Add a marker clusterer to manage the markers.
        +"var markerCluster = new MarkerClusterer(map, markers,"
            +"{imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});"
      +"}"
      +"var locations = [ {lat: -31.563910, lng: 147.154312}, {lat: -33.718234, lng: 150.363181}, {lat: -33.727111, lng: 150.371124},"
        +"{lat: -33.848588, lng: 151.209834}, {lat: -33.851702, lng: 151.216968}, {lat: -34.671264, lng: 150.863657}, {lat: -35.304724, lng: 148.662905},"
        +"{lat: -36.817685, lng: 175.699196}, {lat: -36.828611, lng: 175.790222}, {lat: -37.750000, lng: 145.116667}, {lat: -37.759859, lng: 145.128708},"
        +"{lat: -37.765015, lng: 145.133858}, {lat: -37.770104, lng: 145.143299}, {lat: -37.773700, lng: 145.145187}, {lat: -37.774785, lng: 145.137978},"
        +"{lat: -37.819616, lng: 144.968119}, {lat: -38.330766, lng: 144.695692}, {lat: -39.927193, lng: 175.053218}, {lat: -41.330162, lng: 174.865694},"
	        +"{lat: -42.734358, lng: 147.439506}, {lat: -42.734358, lng: 147.501315}, {lat: -42.735258, lng: 147.438000}, {lat: -43.999792, lng: 170.463352} ]");
	    sb.append("</script>");
	    sb.append("<script src='https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/markerclusterer.js'></script>");
	    sb.append("<script async defer src='https://maps.googleapis.com/maps/api/js?key=AIzaSyBC8vL-dc5qoUhzlKbYFfwOjE9EgVIyEAQ&callback=initMap'></script>");


		sb.append("</div></section>");
		String contentData = sb.toString();
		pw.println(contentData);
		utility.printHtml("Footer.html");		
	}
}