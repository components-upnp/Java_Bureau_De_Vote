package test.com.irit;

import com.irit.reponses.LecteurXml;
import com.irit.reponses.StockReponses;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created by mkostiuk on 12/05/2017.
 */
public class TestLecteurXml extends TestCase {

    private StockReponses sr;
    private LecteurXml l;
    private final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><TelecommandeEleve xmlns=\"/\"><UDN>uuid:460edede-7fde-40f2-8012-58245008f038</UDN><Commande>2</Commande></TelecommandeEleve>";

    @Before
    public void setUp() throws IOException, SAXException, ParserConfigurationException {
        l = new LecteurXml(xml);
    }

    @Test
    public void testUdnOk() {
        assertEquals("uuid:460edede-7fde-40f2-8012-58245008f038",
        l.getUdn());
    }

    @Test
    public void testCommandeOk() {
        assertEquals("2",l.getCommande());
    }
}
