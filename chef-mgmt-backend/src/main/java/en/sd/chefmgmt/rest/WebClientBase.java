package en.sd.chefmgmt.rest;

import en.sd.chefmgmt.exception.model.ExceptionCode;
import en.sd.chefmgmt.exception.model.RestTemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public abstract class WebClientBase<Request, Response> implements ExchangeOperation<Request, Response> {

    private final WebClient webClient;

    @Override
    public Response postForEntity(String url, Request request) {
        try {
            return webClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(
                            status -> !status.is2xxSuccessful(),
                            _ -> Mono.error(new RestTemplateException(
                                    ExceptionCode.REST_TEMPLATE_ERROR,
                                    getExceptionMessage(request)
                            ))
                    )
                    .bodyToMono(getResponseType())
                    .block();
        } catch (Exception e) {
            throw new RestTemplateException(ExceptionCode.REST_TEMPLATE_ERROR, getExceptionMessage(request));
        }
    }

    protected abstract Class<Response> getResponseType();

    protected abstract String getExceptionMessage(Request request);
}