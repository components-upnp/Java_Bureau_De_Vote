package upnp;

import com.irit.display.Fenetre;
import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.binding.LocalServiceBindingException;
import org.fourthline.cling.binding.annotations.AnnotationLocalServiceBinder;
import org.fourthline.cling.model.DefaultServiceManager;
import org.fourthline.cling.model.meta.*;
import org.fourthline.cling.model.types.DeviceType;
import org.fourthline.cling.model.types.UDADeviceType;
import org.fourthline.cling.model.types.UDN;

import java.io.IOException;

/**
 * Created by mkostiuk on 02/05/2017.
 */
public class BureauDeVoteServer implements Runnable {
    public void run() {
        try {

            final UpnpService upnpService = new UpnpServiceImpl();

            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    upnpService.shutdown();
                }
            });

            // Add the bound local device to the registry
            upnpService.getRegistry().addDevice(
                    createDevice()
            );

        } catch (Exception ex) {
            System.err.println("Exception occured: " + ex);
            ex.printStackTrace(System.err);
            System.exit(1);
        }
    }

    public LocalDevice createDevice()
            throws LocalServiceBindingException, IOException, org.fourthline.cling.model.ValidationException {

        DeviceIdentity identity =
                new DeviceIdentity(
                        UDN.uniqueSystemIdentifier("Bureau de vote")
                );

        DeviceType type =
                new UDADeviceType("Bureau", 1);

        DeviceDetails details =
                new DeviceDetails(
                        "Bureau de vote (test)",					// Friendly Name
                        new ManufacturerDetails(
                                "UPS-IRIT",								// Manufacturer
                                ""),								// Manufacturer URL
                        new ModelDetails(
                                "Ampoule",						// Model Name
                                "simulation d'une Ampoule",	// Model Description
                                "v1" 								// Model Number
                        )
                );
        LocalService<VoteController> voteService =
                new AnnotationLocalServiceBinder().read(VoteController.class);
        voteService.setManager(
                new DefaultServiceManager(voteService, VoteController.class)
        );
        LocalService<CommandeProfesseurController> commandeProfesseurService =
                new AnnotationLocalServiceBinder().read(CommandeProfesseurController.class);
        commandeProfesseurService.setManager(
                new DefaultServiceManager(commandeProfesseurService,CommandeProfesseurController.class)
        );

        new Fenetre(voteService,commandeProfesseurService).setVisible(true);

        return new LocalDevice(
                identity, type, details,
                new LocalService[] {voteService,commandeProfesseurService}
        );
    }
}
