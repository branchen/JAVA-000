package io.github.kimmking.gateway.router;

import java.util.List;
import java.util.Random;

/**
 * @author bran.chen
 * @description
 */
public class RandomHttpEndpointRouter implements HttpEndpointRouter{

    @Override
    public String route(List<String> endpoints) {
        return endpoints.get(new Random().nextInt(endpoints.size()));
    }
}
