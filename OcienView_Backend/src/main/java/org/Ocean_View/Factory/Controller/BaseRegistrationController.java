package org.Ocean_View.Factory.Controller;

import org.Ocean_View.Customer.Services.Interfaces.RegistrationService;
import org.Ocean_View.Factory.ServiceFactory.RegistrationServiceFactory;

public class BaseRegistrationController
{
    protected RegistrationService registrationService;

    public BaseRegistrationController() {
        this.registrationService = RegistrationServiceFactory.getRegistrationService();

    }
        public BaseRegistrationController(String serviceType)
        {
           this.registrationService = RegistrationServiceFactory.getRegistrationService(serviceType);
        }
    }

