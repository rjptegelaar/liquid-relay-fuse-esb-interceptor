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
