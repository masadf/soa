package itmo.service.consul;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Data
@Getter
@Setter
public class ConsulServiceRegistration {
    private String ID;
    private String Name;
    private String Address;
    private int Port;
    private List<String> Tags;
    private Map<String, String> Meta;
    private ServiceCheck Check;

    // Getters and setters

    @Getter
    @Setter
    public static class ServiceCheck {
        private String HTTP;
        private String Interval;
        private String Timeout;
        private String DeregisterCriticalServiceAfter;
        private Boolean TLSSkipVerify = true;

        // Getters and setters
    }
}