package de.htwBerlin.ai.kbe.config;

import org.glassfish.jersey.server.ResourceConfig;

import de.htwBerlin.ai.kbe.api.filter.AuthenticationFilter;

public class MyApplication extends ResourceConfig
{
    public MyApplication()
    {
    	System.out.println("Hi, MyApplication is drin");
    	register(new DependencyBinder());
    	
        packages(true,"de.htwBerlin.ai.kbe.services");
 
        //Register Auth Filter here
        register(AuthenticationFilter.class);
        
    }
}
