package org.Ocean_View;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.catalina.webresources.DirResourceSet;

import java.io.File;

public class Main {
    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();

        String webappDir = new File("src/main/webapp").getAbsolutePath();
        System.out.println("📁 Webapp directory: " + webappDir);

        String classesDir = new File("target/classes").getAbsolutePath();
        System.out.println("📁 Classes directory: " + classesDir);

        Context context = tomcat.addWebapp("", webappDir);

        // **CRITICAL: Add classes directory to resources**
        WebResourceRoot resources = new StandardRoot(context);
        resources.addPreResources(new DirResourceSet(
                resources,
                "/WEB-INF/classes",
                classesDir,
                "/"
        ));
        context.setResources(resources);

        // **CRITICAL: Enable annotation scanning**
        StandardJarScanner scanner = new StandardJarScanner();
        scanner.setScanAllDirectories(true);
        scanner.setScanClassPath(true);
        scanner.setScanManifest(false);
        context.setJarScanner(scanner);

        // Force metadata-complete="false"
        context.setXmlValidation(false);
        context.setXmlNamespaceAware(false);

        // Add welcome file
        context.addWelcomeFile("index.jsp");

        tomcat.start();

        System.out.println("\n========================================");
        System.out.println("✅ Server started successfully!");
        System.out.println("📌 Access URLs:");
        System.out.println("   JSP:     http://localhost:8080/");
        System.out.println("   Servlet: http://localhost:8080/test");
        System.out.println("========================================\n");

        tomcat.getServer().await();
    }
}