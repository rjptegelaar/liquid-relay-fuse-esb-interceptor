package nl.paultegelaar.liquid.relay.fuse;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pte.liquid.relay.Converter;
import com.pte.liquid.relay.Transport;
import com.pte.liquid.relay.camel.util.LiquidRelayCamelUtil;
import com.pte.liquid.relay.model.Message;

/**
 * The Liquid producer.
 */
public class LiquidProducer extends DefaultProducer {
    private static final Logger LOG = LoggerFactory.getLogger(LiquidProducer.class);
    private LiquidEndpoint endpoint;
    private Converter<Exchange> converter;
    private Transport transport;
    private boolean enabled;

    public LiquidProducer(LiquidEndpoint endpoint) {
        super(endpoint);
        this.converter = endpoint.getConverter();
        this.transport = endpoint.getTransport();
        this.enabled = endpoint.isEnabled();
        
        
        this.endpoint = endpoint;
    }

    public void process(Exchange exchange) throws Exception {
    	try{
    		if(endpoint.isEnabled()){
    			    			    			    
	        	Message preMsg = converter.convert(exchange);  
	        	String correlationID = LiquidRelayCamelUtil.determineCorrelation(exchange);
	        	String parentId = LiquidRelayCamelUtil.determineParent(exchange);
	        	int order = LiquidRelayCamelUtil.determineOrder(exchange);
	        	String messageID = preMsg.getId();
	        	
	        	LiquidRelayCamelUtil.setCorrelationID(correlationID, exchange);
	        	preMsg.setCorrelationID(correlationID);
	        	
	        	LiquidRelayCamelUtil.setParentID(messageID, exchange);
	        	preMsg.setParentID(parentId);
	        	
	        	LiquidRelayCamelUtil.setOrder(order, exchange);
	        	preMsg.setOrder(order);	
	        	
	        	   	    	    	    	  	   
	        	transport.send(preMsg);
    		}
    	} catch (Exception e) {
    		transport.destroy();
		}    
    }
    
    

}
