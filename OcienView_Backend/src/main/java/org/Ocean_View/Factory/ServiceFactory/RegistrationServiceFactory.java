package org.Ocean_View.Factory.ServiceFactory;

import org.Ocean_View.Customer.Services.Implementations.CustomerRegistrationServiceImpl;
import org.Ocean_View.Customer.Services.Interfaces.RegistrationService;

public class RegistrationServiceFactory
{
    private static RegistrationService instance;
    private RegistrationServiceFactory() {}

    public static RegistrationService getRegistrationService()
    {
        if (instance == null)
        {
            synchronized (RegistrationServiceFactory.class)
            {
                if (instance == null)
                {
                    instance = new CustomerRegistrationServiceImpl();
                }
            }
        }
        return instance;
    }


    public static RegistrationService getRegistrationService(String type)
    {
        switch (type.toUpperCase())
        {
            case "CUSTOMER":
                return new CustomerRegistrationServiceImpl();

            default:
                throw new IllegalArgumentException("Unknown service type: " + type);
        }
    }
}