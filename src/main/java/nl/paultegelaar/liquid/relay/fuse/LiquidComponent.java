package nl.paultegelaar.liquid.relay.fuse;

import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.impl.UriEndpointComponent;

/**
 * Represents the component that manages {@link LiquidEndpoint}.
 */
public class LiquidComponent extends UriEndpointComponent {
    
    public LiquidComponent() {
        super(LiquidEndpoint.class);
    }

    public LiquidComponent(CamelContext context) {
        super(context, LiquidEndpoint.class);
    }

    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        Endpoint endpoint = new LiquidEndpoint(uri, this);
        setProperties(endpoint, parameters);
        return endpoint;
    }
    
    
}
