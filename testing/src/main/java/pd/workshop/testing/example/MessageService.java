package pd.workshop.testing.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pd.workshop.testing.external.InfoService;

@Service
public class MessageService {
    private static final String MESSAGE_FORMAT = "Info: %s";

    @Autowired(required = false)
    private InfoService infoService;

    public boolean sendInfo(String phoneNumber, String infoMessage) {
        boolean messageHasBeenSent = false;
        if(infoService.hasSubscription( phoneNumber )) {
            infoService.sendMessage( phoneNumber, String.format(MESSAGE_FORMAT, infoMessage ));
            messageHasBeenSent = true;
        }
        return messageHasBeenSent;
    }

}
