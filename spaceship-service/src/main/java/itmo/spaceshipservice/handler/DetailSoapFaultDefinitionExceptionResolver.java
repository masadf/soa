package itmo.spaceshipservice.handler;


import itmo.spaceshipservice.domain.exceptions.MarineNotFoundException;
import itmo.spaceshipservice.domain.exceptions.ServiceUnavailableException;
import itmo.spaceshipservice.domain.exceptions.StarshipNotFoundException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;

import javax.xml.namespace.QName;

public class DetailSoapFaultDefinitionExceptionResolver extends SoapFaultMappingExceptionResolver {
    private static final QName CODE = new QName("code");
    private static final QName MESSAGE = new QName("message");


    @Override
    protected void customizeFault(Object endpoint, Exception ex, SoapFault fault) {
        logger.info("Exception processed ", ex);
        if (ex instanceof StarshipNotFoundException || ex instanceof MarineNotFoundException) {
            SoapFaultDetail detail = fault.addFaultDetail();
            detail.addFaultDetailElement(CODE).addText("404");
            detail.addFaultDetailElement(MESSAGE).addText(ex.getMessage());
            return;
        }
        if (ex instanceof ServiceUnavailableException) {
            SoapFaultDetail detail = fault.addFaultDetail();
            detail.addFaultDetailElement(CODE).addText("503");
            detail.addFaultDetailElement(MESSAGE).addText(ex.getMessage());
            return;
        }
        if (ex instanceof HttpClientErrorException ||
                ex instanceof MethodArgumentNotValidException ||
                ex instanceof HttpMessageNotReadableException ||
                ex instanceof MethodArgumentTypeMismatchException) {
            SoapFaultDetail detail = fault.addFaultDetail();
            detail.addFaultDetailElement(CODE).addText("400");
            detail.addFaultDetailElement(MESSAGE).addText("Некорректные входящие параметры");
            return;
        }

        if (ex != null) {
            SoapFaultDetail detail = fault.addFaultDetail();
            detail.addFaultDetailElement(CODE).addText("500");
            detail.addFaultDetailElement(MESSAGE).addText("Внутренняя ошибка");
        }
    }
}