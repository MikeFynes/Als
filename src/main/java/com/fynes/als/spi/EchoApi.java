package com.fynes.als.spi;

import com.fynes.als.Constants;
import com.fynes.als.model.Echo;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.Named;

@Api(name = "echo", version = "v1", scopes = { Constants.EMAIL_SCOPE }, clientIds = {
        Constants.WEB_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID }, description = "API for the Person API")
public class EchoApi {

    @ApiMethod(name="echoWithName", path = "echo", httpMethod = HttpMethod.GET)
    public Echo echo(@Named("name") String name){
        return new Echo(name);
    }
}
