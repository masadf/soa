package itmo.gateway.configuration;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class HomePageUrlFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.ROUTE_TYPE; // Фильтр маршрутизации
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SIMPLE_HOST_ROUTING_FILTER_ORDER - 1; // Выполняется перед стандартной маршрутизацией
    }

    @Override
    public boolean shouldFilter() {
        return true; // Всегда активен
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        // Получаем homePageUrl из метаданных Eureka
//        String homePageUrl = context.getRequest("homePageUrl");
//        if (homePageUrl != null) {
//            context.setRouteHost(new URL(homePageUrl)); // Устанавливаем целевой URL
//        }
        return null;
    }
}