package en.sd.chefmgmt.jms;

import en.sd.chefmgmt.model.dto.mail.SendingStatusDTO;

public interface MessageSender<Request> {

    SendingStatusDTO sendMessage(Request request);
}