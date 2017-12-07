package io.hawt.web.auth;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class BrandingService {

    private static final transient Logger LOG = LoggerFactory.getLogger(LoginServlet.class);
    JSONObject config;

    public BrandingService(ServletContext servletContext) {
        LOG.info("Loading src/main/webapp/hawtconfig.json...");
        InputStream in = servletContext.getResourceAsStream("/hawtconfig.json");
        if (in == null) {
            LOG.warn("hawtconfig.json not found");
        } else {
            try (Reader reader = new InputStreamReader(in)) {
                config = (JSONObject) new JSONParser().parse(reader);
                LOG.info("hawtconfig.json loaded");
            } catch (Exception e) {
                LOG.error("Failed to load hawtconfig.json", e);
            }
        }
    }

    public String getLogoUrl() {
        return getProperty("logoUrl");
    }

    public String getBrandUrl() {
        return getProperty("brandUrl");
    }

    public String getBrandName() {
        return getProperty("brandName");
    }

    private String getProperty(String name) {
        if (config != null) {
            if (config.get("branding") != null) {
                JSONObject branding = (JSONObject) config.get("branding");
                if (branding.get(name) != null) {
                    return (String) branding.get(name);
                }
            }
        }
        LOG.warn("Branding property '" + name + "' not found in hawtconfig.json");
        return "";
    }


}
