package nl.paultegelaar.liquid.relay.fuse;

import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;

/**
 * The Liquid consumer.
 */
public class LiquidConsumer extends DefaultConsumer {
    private final LiquidEndpoint endpoint;

    public LiquidConsumer(LiquidEndpoint endpoint, Processor processor) {
        super(endpoint, processor);
        this.endpoint = endpoint;
    }

   
}
