package saa.pakkaus.maini;
import java.io.InputStream;
import java.net.URL;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXParseException; 

/**Luokka joka parseaa XML dokumentin**/
public class XMLConnect{
	
	/**Metodi joka ottaa kaupungin nimen ja palauttaa l�mp�tilat 3 arvon arrayn�.
	 * @param kaupunki Kaupungin nimi**/
	public int[] getWeather(String kaupunki){
		//laitetaan perusarvot 100:n asteeseen, joita sitten testaillaan toisessa luokassa.
		int[] temperature = {100,100,100};
    try {
    		//tehd��n tarvittavat objektit XML parseamiseen
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            
            //otetaan urli kaupungin mukaan
            URL xmlurli = new URL("http://free.worldweatheronline.com/feed/weather.ashx?q="+kaupunki+",Suomi&format=xml&num_of_days=3&key=6105050e53061200121806");
            InputStream in = xmlurli.openStream();
            
            //ja sitten parsetaan XML tiedosto
            Document doc = docBuilder.parse(in);
            
            //normalisoidaan teksti
           // doc.getDocumentElement ().normalize (); //Koska apissa voi joskus olla suomalaisia kaupungin nimi� jotka sis�lt�v�t � � � kirjaimia, en voi normalisoida teksti�.
            
            //tehd��n nodelisti XML dokumentin root elementist�.
            NodeList mainDataXML = doc.getElementsByTagName("data");
            
            //loopataan dokumentin l�pi
            for(int s=0; s<mainDataXML.getLength() ; s++){

            		//otetaan node kerrallaan ja jos stringi t�sm�� varastoidaan l�mp�tilat talteen.
                Node cityNode = mainDataXML.item(s);
                if(cityNode.getNodeType() == Node.ELEMENT_NODE){


                    Element firstPersonElement = (Element)cityNode;

                    NodeList ekaLampotila = firstPersonElement.getElementsByTagName("temp_C");
                    NodeList tokaLampotila = firstPersonElement.getElementsByTagName("tempMaxC");
                    Element lampotilaElement = (Element)ekaLampotila.item(0);
                    Element lampotila2Element = (Element)tokaLampotila.item(0);
                    Element lampotila3Element = (Element)tokaLampotila.item(1);

                    NodeList textLNList = lampotilaElement.getChildNodes();
                    NodeList textLNList2 = lampotila2Element.getChildNodes();
                    NodeList textLNList3 = lampotila3Element.getChildNodes();
                    
                    temperature[0] = Integer.parseInt(((Node)textLNList.item(0)).getNodeValue().trim());
                    temperature[1] = Integer.parseInt(((Node)textLNList2.item(0)).getNodeValue().trim());
                    temperature[2] = Integer.parseInt(((Node)textLNList3.item(0)).getNodeValue().trim());
                    //-------------l�mp�tilat varastoitu------------

                }


            }

            //feilataan ylpeydell� ja palautetaan xxx astetta joka p�iv�lle. My�hemmin tarkistetaan ett� jos l�mp�tila on xxx niin tiedet��n miss� meni pieleen.
        }catch (SAXParseException err) {
        	//koodi jos parseaminen ei onninstunut.
        return new int[]{200,200,200};
        }
        catch (Throwable t) {
        //koodi jos joku muu ei onninstunut (Esim nettiyhteys ei ole toiminnassa).
        return new int[]{300,300,300};
        }
    
    
    //jos kaikki menee hyvin niin palautetaan temperature array kolmen l�mp�tilan kanssa.
    return temperature;
	}


}