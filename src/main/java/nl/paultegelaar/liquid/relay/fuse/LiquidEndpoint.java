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

import java.util.Properties;

import org.apache.camel.Consumer;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriPath;

import com.pte.liquid.async.LiquidLegacyTransport;
import com.pte.liquid.relay.Converter;
import com.pte.liquid.relay.Marshaller;
import com.pte.liquid.relay.Transport;
import com.pte.liquid.relay.camel.converter.LiquidRelayExchangeConverterImpl;
import com.pte.liquid.relay.client.stomp.StompTransport;
import com.pte.liquid.relay.marshaller.json.JsonMarshaller;

/**
 * Represents a Liquid endpoint.
 */
@UriEndpoint(scheme = "liquid", title = "Liquid", syntax="liquid:name", consumerClass = LiquidConsumer.class, label = "Liquid")
public class LiquidEndpoint extends DefaultEndpoint {
    @UriPath @Metadata(required = "true")
    private String name;    

    	
    private Transport stompTransport;
    private Transport asyncTransport;
	private Converter<Exchange> converter;
	private Marshaller marshaller;
	
	/**
	 * Destination queue where logmessage is sent. Default is ActiveMQ messagebroker.
	 */
	@UriParam
    private String destination = "com.pte.liquid.relay.json.in";
	
	/**
	 * Hostname of ther Liquid relay where logmessage is sent.
	 */
	@UriParam
    private String hostname = "localhost";
	
	/**
	 * Portnumber of ther Liquid relay where logmessage is sent.
	 */
	@UriParam
    private int port = 33555;
	
	
	/**
	 * Swich to enable or disable Liquid interceptor.
	 */
	@UriParam
    private boolean enabled;

	
	/**
	 * Maximum message to internally queue.
	 */
	@UriParam
    private int queueSize = 1000;

	
	/**
	 * If the remaining queue size drops below this threshold, messages will be dropped.
	 */
	@UriParam
    private int queueThreshold = 100;
	
    public LiquidEndpoint() {
    }

    public LiquidEndpoint(String uri, LiquidComponent component) {
        super(uri, component);
    }

    public LiquidEndpoint(String endpointUri) {
        super(endpointUri);
    }

    public Producer createProducer() throws Exception {    	
    	    	 
    	if(queueThreshold>=queueSize){
    		throw new IllegalArgumentException("Queuesize must always be larger then queueThreshold"); 
    	}
    	
    	marshaller = new JsonMarshaller();
    	
    	 Properties properties = new Properties();
    	
        if(destination!=null){
        	properties.put("relay_destination", destination);
        }
        if(hostname!=null){
        	properties.put("relay_stomp_hostname", hostname);
        }
        if(port>0){
        	properties.put("relay_stomp_port", port);
        }
    	
    	stompTransport = new StompTransport();
    	stompTransport.setProperties(properties);
    	stompTransport.setMarshaller(marshaller);
    	
    	asyncTransport = new LiquidLegacyTransport(stompTransport);
    	
    	converter = new LiquidRelayExchangeConverterImpl();
    	
    	
        return new LiquidProducer(this);
    }

    public Consumer createConsumer(Processor processor) throws Exception {
        return new LiquidConsumer(this, processor);
    }

    public boolean isSingleton() {
        return true;
    }

    /**
     * Some description of this option, and what it does
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }	

	public Transport getStompTransport() {
		return stompTransport;
	}

	public void setStompTransport(Transport stompTransport) {
		this.stompTransport = stompTransport;
	}

	public Converter<Exchange> getConverter() {
		return converter;
	}

	public void setConverter(Converter<Exchange> converter) {
		this.converter = converter;
	}

	public Marshaller getMarshaller() {
		return marshaller;
	}

	public void setMarshaller(Marshaller marshaller) {
		this.marshaller = marshaller;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Transport getAsyncTransport() {
		return asyncTransport;
	}

	public void setAsyncTransport(Transport asyncTransport) {
		this.asyncTransport = asyncTransport;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	
	
	public int getQueueSize() {
		return queueSize;
	}

	public void setQueueSize(int queueSize) {
		this.queueSize = queueSize;
	}

	public int getQueueThreshold() {
		return queueThreshold;
	}

	public void setQueueThreshold(int queueThreshold) {
		this.queueThreshold = queueThreshold;
	}

	@Override
	public void shutdown() throws Exception {
		try{
			asyncTransport.destroy();
		}catch(Exception e){
			//Ignore all errors
		}
		
		super.shutdown();
	}
    
    
    
}
