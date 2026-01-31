package org.Ocean_View.Factory.ServiceFactory;

import org.Ocean_View.Admin.Services.Implementation.AdminRegistrationServiceImpl;
import org.Ocean_View.Admin.Services.Interfaces.AdminRegistrationService;
import org.Ocean_View.Customer.Services.Implementations.CustomerRegistrationServiceImpl;
import org.Ocean_View.Customer.Services.Interfaces.RegistrationService;

public class RegistrationServiceFactory
{
    private static RegistrationService instance;
    private static AdminRegistrationService instanceAdmin;
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

    public static AdminRegistrationService getAdminRegistrationService() {
        if (instanceAdmin == null) {
            synchronized (RegistrationServiceFactory.class) {
                if (instanceAdmin == null) {
                    instanceAdmin = new AdminRegistrationServiceImpl();
                }
            }
        }
        return instanceAdmin;
    }


    public static RegistrationService getRegistrationService(String type)
    {
        switch (type.toUpperCase())
        {
            case "CUSTOMER":
                return new CustomerRegistrationServiceImpl();
            case "ADMIN":
                // Cast AdminRegistrationService to RegistrationService
                // Since AdminRegistrationService extends RegistrationService, this is safe
                return (RegistrationService) new AdminRegistrationServiceImpl();
            default:
                throw new IllegalArgumentException("Unknown service type: " + type);
        }
    }

    public static AdminRegistrationService getAdminService(String type)
    {
        switch (type.toUpperCase())
        {
            case "ADMIN":
                return new AdminRegistrationServiceImpl();
            default:
                throw new IllegalArgumentException("Unknown service type: " + type);
        }
    }
}