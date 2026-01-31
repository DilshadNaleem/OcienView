package org.Ocean_View.Factory.Controller;

import org.Ocean_View.Admin.Services.Interfaces.AdminRegistrationService;
import org.Ocean_View.Customer.Services.Interfaces.RegistrationService;
import org.Ocean_View.Factory.ServiceFactory.RegistrationServiceFactory;

public class BaseRegistrationController
{
    protected RegistrationService registrationService;
    protected AdminRegistrationService adminRegistrationService;

    // Default constructor - uses singleton instances
    public BaseRegistrationController() {
        this.registrationService = RegistrationServiceFactory.getRegistrationService();
        this.adminRegistrationService = RegistrationServiceFactory.getAdminRegistrationService();
    }

    // Parameterized constructor - creates new instances based on type
    public BaseRegistrationController(String serviceType)
    {
        // For ADMIN type, we need to cast to AdminRegistrationService
        if ("ADMIN".equalsIgnoreCase(serviceType)) {
            this.registrationService = RegistrationServiceFactory.getRegistrationService(serviceType);
            this.adminRegistrationService = RegistrationServiceFactory.getAdminService(serviceType);
        } else {
            this.registrationService = RegistrationServiceFactory.getRegistrationService(serviceType);
            this.adminRegistrationService = null; // Not needed for customer
        }
    }
}