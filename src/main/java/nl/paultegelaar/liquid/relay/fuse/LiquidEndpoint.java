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
    @UriParam(defaultValue = "10")
    private int option = 10;
    
	private long count = 0;
	
    private Transport transport;
	private Converter<Exchange> converter;
	private Marshaller marshaller;
    private String destination = "com.pte.liquid.relay.json.in";
    private String hostname = "localhost";
    private int port = 33555;
    private boolean enabled = true;

    public LiquidEndpoint() {
    }

    public LiquidEndpoint(String uri, LiquidComponent component) {
        super(uri, component);
    }

    public LiquidEndpoint(String endpointUri) {
        super(endpointUri);
    }

    public Producer createProducer() throws Exception {
this.enabled = enabled;
    	
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
    	
    	transport = new StompTransport();
    	transport.setProperties(properties);
    	transport.setMarshaller(marshaller);
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

    /**
     * Some description of this option, and what it does
     */
    public void setOption(int option) {
        this.option = option;
    }

    public int getOption() {
        return option;
    }

	public Transport getTransport() {
		return transport;
	}

	public void setTransport(Transport transport) {
		this.transport = transport;
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
    
    
    
}
