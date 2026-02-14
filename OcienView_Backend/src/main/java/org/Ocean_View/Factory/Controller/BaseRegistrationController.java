package org.Ocean_View.Factory.Controller;

import org.Ocean_View.Admin.Services.Interfaces.AdminRegistrationService;
import org.Ocean_View.Customer.Services.Interfaces.RegistrationService;
import org.Ocean_View.Factory.ServiceFactory.RegistrationServiceFactory;

public class BaseRegistrationController
{
    protected RegistrationService registrationService;
    protected AdminRegistrationService adminRegistrationService;


    public BaseRegistrationController() {
        this.registrationService = RegistrationServiceFactory.getRegistrationService();
        this.adminRegistrationService = RegistrationServiceFactory.getAdminRegistrationService();
    }


    public BaseRegistrationController(String serviceType)
    {

        if ("ADMIN".equalsIgnoreCase(serviceType)) {
            this.registrationService = RegistrationServiceFactory.getRegistrationService(serviceType);
            this.adminRegistrationService = RegistrationServiceFactory.getAdminService(serviceType);
        } else {
            this.registrationService = RegistrationServiceFactory.getRegistrationService(serviceType);
            this.adminRegistrationService = null;
        }
    }
}