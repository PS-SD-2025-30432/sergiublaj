package en.sd.chefmgmt.resttemplate;

public interface ExchangeOperation<Request, Response> {

    Response postForEntity(String url, Request request);
}
