package en.sd.chefmgmt.rest;

public interface ExchangeOperation<Request, Response> {

    Response postForEntity(String url, Request request);
}
