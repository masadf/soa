package itmo.service.consul;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ConsulRegistrationService {
    private final RestTemplate restTemplate;
    private final String consulHost;
    private final int consulPort;

    public ConsulRegistrationService(
            @Value("${spring.cloud.consul.host}") String consulHost,
            @Value("${spring.cloud.consul.port}") int consulPort) {
        this.restTemplate = new RestTemplate();
        this.consulHost = consulHost;
        this.consulPort = consulPort;
    }

    public void registerService(String serviceName, String serviceAddress, int servicePort) {
        ConsulServiceRegistration registration = new ConsulServiceRegistration();
        registration.setID(serviceName + "-" + servicePort);
        registration.setName(serviceName);
        registration.setAddress(serviceAddress);
        registration.setPort(servicePort);

        ConsulServiceRegistration.ServiceCheck check = new ConsulServiceRegistration.ServiceCheck();
        check.setHTTP("https://" + serviceAddress + ":" + servicePort + "/marines");
        check.setInterval("10s");
        check.setTimeout("5s");
        check.setDeregisterCriticalServiceAfter("30s");
        check.setTLSSkipVerify(true);

        registration.setCheck(check);

        String url = String.format("http://%s:%d/v1/agent/service/register", consulHost, consulPort);

        try {
            restTemplate.put(url, registration, Void.class);
            System.out.println("Service " + serviceName + " registered with Consul");
        } catch (Exception e) {
            System.err.println("Error registering service: " + e.getMessage());
        }
    }
}