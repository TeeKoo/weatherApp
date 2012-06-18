package saa.pakkaus.maini;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * S‰‰ appi by Taneli K‰rkk‰inen
 * **/
public class SaaActivity extends Activity {
	/**Tehd‰‰n heti alussa saaTiedotus objekti johon sitten l‰hetet‰‰n kaupunki ja saadaan l‰mpˆtilat takaisin**/
	XMLConnect saaTiedotus;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //layoutti on perus Relative, k‰tevin t‰ll‰iseen appiin.
        RelativeLayout leiaut = new RelativeLayout(this);
        
        //laitetaan suomen kartta taustakuvaksi ja sitten laitetaan kunnan ikonit kaupunkien tunnistamiseksi.
        leiaut.setBackgroundResource(R.drawable.suomi);
        setCities(leiaut,R.drawable.helsinki,183,654, "Helsinki");
        setCities(leiaut,R.drawable.oulu,190,366, "Oulu");
        setCities(leiaut,R.drawable.tampere,157,572, "Tampere");
        setCities(leiaut,R.drawable.rovaniemi,225,278, "Rovaniemi");
        setCities(leiaut,R.drawable.turku,92,632, "Turku");
        
        //ja sitten laitetaan kaikki n‰kyviin
        setContentView(leiaut);	
    }
    
    
    /**Metodi joka ottaa vastaan kaupunkien tiedot ja sis‰lt‰‰ actionlistenerin kun painaa ikonia
     * @param layout Mink‰ layoutin l‰het‰t Re
     * @param cityID kaupungin ID numerona
     * @param locationX X koordinaatti 
     * @param locationY Y koordinaatti 
     * @param kaupunginNimi kaupungin nimi 
     * **/
    private void setCities(RelativeLayout layout, int cityID, int locationX, int locationY, final String kaupunginNimi){
        
    	//tehd‰‰n uusi ikoni peruslayouttiin cityID:n numeron mukaan.
        ImageView i = new ImageView(this);
        i.setImageResource(cityID);
        
        //laitetaan parametrit kuntoon
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        param.setMargins(locationX, locationY, 0, 0);
        i.setLayoutParams(param);
        
        //laitetaan actionlistener kun ikonia painaa. 
        i.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				//tehd‰‰n uusi objecti jokaisella napin painauksella koska s‰‰ muuttuu jatkuvasti
				saaTiedotus = new XMLConnect();
				int[] lampotilat = saaTiedotus.getWeather(kaupunginNimi);
				
				//jos lampotilat array l‰p‰isee testin niin printataan ruudulle l‰mpˆtilat.
				if(testingStuff(lampotilat)){
				//yksinkertainen Toast ruutu tulee ruudulle joka n‰ytt‰‰ sitten l‰mpˆtilat. LENTGH_SHORT pysyy noin 3 sekuntia ruudulla. Pit‰isi olla tarpeeksi aikaa n‰hd‰ s‰‰.
				Toast.makeText(getApplicationContext(), kaupunginNimi+" s‰‰\n\nT‰n‰‰n: " +
						""+lampotilat[0]+"∞C\nHuomenna: "+lampotilat[1]+
						"∞C\nYlihuomenna: "+lampotilat[2]+"∞C",
						Toast.LENGTH_SHORT).show();
				}
				return false;
			}
		});
        
        //lopulta laitetaan ikoni peruslayouttiin.
        layout.addView(i);
    }
    
    
    /**Metodi testaamiseen. Palauttaa 'true' jos array sai oikeat luvut XMLConnect objektista. false jos ei.
     * @param testiArray int array jota testataan**/
    private boolean testingStuff(int[] testiArray){
    	//jos arrayn ensimm‰inen luku on 200 niin annetaan virheilmoitus. Luku 200 siksi ett‰ alempi luku saattaa olla itse l‰mpˆtil‰. 
    	if(testiArray[0]==200){
    		Toast.makeText(getApplicationContext(), "ERROR 200\nEi voitu parsettaa XML dokumenttia! :(",
					Toast.LENGTH_SHORT).show();
    		return false;
    	}
       	//jos arrayn ensimm‰inen luku on 300 niin annetaan virheilmoitus. Luku 300 siksi ett‰ alempi luku saattaa olla itse l‰mpˆtil‰. 
    	if(testiArray[0]==300){
    		Toast.makeText(getApplicationContext(), "ERROR 300\nJotain meni pieleen. Onko nettiyhteys kunnossa?",
					Toast.LENGTH_SHORT).show();
    		return false;
    	}
    	else 
    		return true;
    }
    
}