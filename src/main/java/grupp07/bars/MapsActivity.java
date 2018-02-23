package grupp07.bars;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public class FlickrImage {
        String Id;
        String Owner;
        String Secret;
        String Server;
        String Farm;
        String Title;

        Bitmap FlickrBitmap;

        FlickrImage(String _Id, String _Owner, String _Secret, String _Server,
                    String _Farm, String _Title) {
            Id = _Id;
            Owner = _Owner;
            Secret = _Secret;
            Server = _Server;
            Farm = _Farm;
            Title = _Title;

            FlickrBitmap = preloadBitmap();
        }

        private Bitmap preloadBitmap() {
            Bitmap bm = null;

            String FlickrPhotoPath = "https://farm" + Farm
                    + ".static.flickr.com/" + Server + "/" + Id + "_" + Secret
                    + "_m.jpg";

            URL FlickrPhotoUrl = null;

            try {
                FlickrPhotoUrl = new URL(FlickrPhotoPath);

                HttpURLConnection httpConnection = (HttpURLConnection) FlickrPhotoUrl
                        .openConnection();
                httpConnection.setDoInput(true);
                httpConnection.connect();
                InputStream inputStream = httpConnection.getInputStream();
                bm = BitmapFactory.decodeStream(inputStream);

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return bm;
        }

        public Bitmap getBitmap() {
            return FlickrBitmap;
        }

    }

    FlickrImage[] myFlickrImage;

	/*
	 * FlickrQuery = FlickrQuery_url + FlickrQuery_per_page +
	 * FlickrQuery_nojsoncallback + FlickrQuery_format + FlickrQuery_tag + q +
	 * FlickrQuery_key + FlickrApiKey
	 */

    String FlickrQuery_url = "https://api.flickr.com/services/rest/?method=flickr.photos.search";
    String FlickrQuery_per_page = "&per_page=5";
    String FlickrQuery_nojsoncallback = "&nojsoncallback=1";
    String FlickrQuery_format = "&format=json";
    String FlickrQuery_tag = "&tags=";
    String FlickrQuery_key = "&api_key=";

    // Apply your Flickr API:
    // www.flickr.com/services/apps/create/apply/?
    String FlickrApiKey = "871805936c074bf3b299789e95bb38ce";

    // final String DEFAULT_SEARCH = "Bill_Gate";
    final String DEFAULT_SEARCH = "heineken";



    Bitmap bmFlickr;

    private GoogleMap mMap;
    private HashMap<Marker, MyMarker> mMarkersHashMap;
    private ArrayList<MyMarker> mMyMarkersArray = new ArrayList<MyMarker>();
    private int FROM_FRAGMENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        Bundle extras = getIntent().getExtras();
        FROM_FRAGMENT = extras.getInt("fromfragment") - 1;
        // Initialize the HashMap for Markers and MyMarker object
        mMarkersHashMap = new HashMap<Marker, MyMarker>();

        mMyMarkersArray.add(new MyMarker("Lion Bar", "Öl: 23 kr", "icon1", Double.parseDouble("59.31479"), Double.parseDouble("18.03398"),"beer"));
        mMyMarkersArray.add(new MyMarker("Victoria", "Öl: 48 kr", "icon2", Double.parseDouble("59.33172"), Double.parseDouble("18.06997"),"dancefloor"));
        mMyMarkersArray.add(new MyMarker("Kelly's", "Öl: 29", "icon3", Double.parseDouble("59.31446"), Double.parseDouble("18.07483"),"budweisser"));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }



    private void plotMarkers(ArrayList<MyMarker> markers)
    {
        if(markers.size() > 0)
        {
            for (MyMarker myMarker : markers)
            {

                // Create user marker with custom icon and other options
                MarkerOptions markerOption = new MarkerOptions().position(new LatLng(myMarker.getmLatitude(), myMarker.getmLongitude()));

                //markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.locationbeer));

                Marker currentMarker = mMap.addMarker(markerOption);



                //currentMarker.showInfoWindow();

                mMarkersHashMap.put(currentMarker, myMarker);

                mMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter());


            }
        }
    }

    public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter
    {
        public MarkerInfoWindowAdapter()
        {
        }

        @Override
        public View getInfoWindow(Marker marker)
        {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker)
        {
            View v  = getLayoutInflater().inflate(R.layout.infowindow_layout, null);

            MyMarker myMarker = mMarkersHashMap.get(marker);

            ImageView markerIcon = (ImageView) v.findViewById(R.id.marker_icon);

            TextView markerLabel = (TextView)v.findViewById(R.id.marker_label);

            TextView markerSnippet = (TextView)v.findViewById(R.id.marker_snippet);

            ImageView imageFlickrPhoto0 = (ImageView) v.findViewById(R.id.flickrPhoto0);
            ImageView imageFlickrPhoto1 = (ImageView) v.findViewById(R.id.flickrPhoto1);

            String searchQ = myMarker.getmFlickrTag();
            String searchResult = QueryFlickr(searchQ);

            myFlickrImage = ParseJSON(searchResult);

            Bitmap myFlickrImageBM;

            myFlickrImageBM = myFlickrImage[0].getBitmap();
            if (myFlickrImageBM != null) {
                imageFlickrPhoto0.setImageBitmap(myFlickrImageBM);
            }

            myFlickrImageBM = myFlickrImage[1].getBitmap();
            if (myFlickrImageBM != null) {
                imageFlickrPhoto1.setImageBitmap(myFlickrImageBM);
            }

            markerIcon.setImageResource(manageMarkerIcon(myMarker.getmIcon()));

            markerLabel.setText(myMarker.getmLabel());

            markerSnippet.setText(myMarker.getmSnippet());



            return v;
        }
    }

    private String QueryFlickr(String q) {

        String qResult = null;

        String qString = FlickrQuery_url + FlickrQuery_per_page
                + FlickrQuery_nojsoncallback + FlickrQuery_format
                + FlickrQuery_tag + q + FlickrQuery_key + FlickrApiKey;

        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(qString);

        try {
            HttpEntity httpEntity = httpClient.execute(httpGet).getEntity();

            if (httpEntity != null) {
                InputStream inputStream = httpEntity.getContent();
                Reader in = new InputStreamReader(inputStream);
                BufferedReader bufferedreader = new BufferedReader(in);
                StringBuilder stringBuilder = new StringBuilder();

                String stringReadLine = null;

                while ((stringReadLine = bufferedreader.readLine()) != null) {
                    stringBuilder.append(stringReadLine + "\n");
                }

                qResult = stringBuilder.toString();
                inputStream.close();
            }

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return qResult;
    }

    private FlickrImage[] ParseJSON(String json) {

        FlickrImage[] flickrImage = null;

        bmFlickr = null;
        String flickrId;
        String flickrOwner;
        String flickrSecret;
        String flickrServer;
        String flickrFarm;
        String flickrTitle;

        try {
            JSONObject JsonObject = new JSONObject(json);
            JSONObject Json_photos = JsonObject.getJSONObject("photos");
            JSONArray JsonArray_photo = Json_photos.getJSONArray("photo");

            flickrImage = new FlickrImage[JsonArray_photo.length()];
            for (int i = 0; i < JsonArray_photo.length(); i++) {
                JSONObject FlickrPhoto = JsonArray_photo.getJSONObject(i);
                flickrId = FlickrPhoto.getString("id");
                flickrOwner = FlickrPhoto.getString("owner");
                flickrSecret = FlickrPhoto.getString("secret");
                flickrServer = FlickrPhoto.getString("server");
                flickrFarm = FlickrPhoto.getString("farm");
                flickrTitle = FlickrPhoto.getString("title");
                flickrImage[i] = new FlickrImage(flickrId, flickrOwner,
                        flickrSecret, flickrServer, flickrFarm, flickrTitle);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return flickrImage;
    }

    private int manageMarkerIcon(String markerIcon)
    {
        if (markerIcon.equals("icon1"))
            return R.drawable.lionb1;
        else if(markerIcon.equals("icon2"))
            return R.drawable.victoria;
        else if(markerIcon.equals("icon3"))
            return R.drawable.kellys;
        else
            return R.drawable.icondefault;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        plotMarkers(mMyMarkersArray);



        // Add a marker in Sydney and move the camera
        LatLng position = new LatLng(mMyMarkersArray.get(FROM_FRAGMENT).getmLatitude(), mMyMarkersArray.get(FROM_FRAGMENT).getmLongitude());

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15.0f));

    }


}