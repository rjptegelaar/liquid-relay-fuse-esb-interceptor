//Copyright 2017 Paul Tegelaar
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.
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
        
        LOG.info("Created Liquid interceptor queue size: " + endpoint.getQueueSize() + ", threshold: " + endpoint.getQueueThreshold());
        
        this.converter = endpoint.getConverter();
        this.transport = endpoint.getAsyncTransport();
        this.enabled = endpoint.isEnabled();
        
        
        this.endpoint = endpoint;
    }
    
    
    public void process(Exchange exchange) throws Exception {
    	try{
    		if(enabled){
    			    			    			    
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
