package fi.passiba.biblestudy;

import fi.passiba.biblestudy.authorization.BibleStudyAuthorizationStrategy;
import fi.passiba.biblestudy.authorization.BibleStudyFaceBookAuthorizationStrategy;


import fi.passiba.groups.ui.pages.Main;
import org.apache.wicket.Page;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequestCycleProcessor;
import org.apache.wicket.protocol.http.request.CryptedUrlWebRequestCodingStrategy;
import org.apache.wicket.protocol.http.request.WebRequestCodingStrategy;
import org.apache.wicket.request.IRequestCodingStrategy;
import org.apache.wicket.request.IRequestCycleProcessor;
import org.apache.wicket.settings.ISecuritySettings;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Application object for your web application.
 * <p/>
 * If you want to run this application without deploying, run the Start class.
 */
public class BibleStudyApplication extends WebApplication implements ApplicationContextAware{
    
    
    private static final String GOOGLE_MAPS_API_KEY_PARAM = "GoogleMapsAPIkey";
    private static final String FACEBOOK_API_KEY_PARAM = "facebookAPIkey";
    private static final String FACEBOOK_SECRET_KEY_PARAM = "facebookSecretkey";
    private ApplicationContext ctx;


    
   @Override
    public Class getHomePage()
    {
        return Main.class;
    }
    private ApplicationContext getContext() {
        return WebApplicationContextUtils
				.getRequiredWebApplicationContext(getServletContext());
    }
   
       
    @Override
    public Session newSession(Request request, Response response) {
        return new BibleStudySession(request);
     //   return new  BibleStudyFaceBookSession(request);
    }        
    public static BibleStudyApplication get() {
        return (BibleStudyApplication) WebApplication.get();
    }
    @Override
    protected void init() {
      // super.init();
        addComponentInstantiationListener(new SpringComponentInjector(
                this));
        
        //authorization settings
        BibleStudyAuthorizationStrategy authStrat = new BibleStudyAuthorizationStrategy();
       // BibleStudyFaceBookAuthorizationStrategy  authStrat= new BibleStudyFaceBookAuthorizationStrategy ();
        ISecuritySettings securitySettings = getSecuritySettings();
        securitySettings.setAuthorizationStrategy(authStrat);
        securitySettings.setUnauthorizedComponentInstantiationListener(authStrat);
        getMarkupSettings().setStripWicketTags(true);

    }
    
       /**
	 * Gets the init parameter 'GoogleMapsAPIkey' of the filter, or throws a
	 * WicketRuntimeException, if it is not set.
	 * 
	 * Pay attention at webapp deploy context, we need a different key for each
	 * deploy context check <a
	 * href="http://www.google.com/apis/maps/signup.html">Google Maps API - Sign
	 * Up</a> for more info.
	 * 
	 * Also the GClientGeocoder is pickier on this than the GMap2. Running on
	 * 'localhost' GMap2 will ignore the key and the maps will show up, but
	 * GClientGeocoder will not. So if the key doesn't match the url down to the
	 * directory GClientGeocoder will not work.
	 * 
	 * <pre>
	 * [...]
	 * &lt;init-param&gt;
	 *     &lt;param-name&gt;GoogleMapsAPIkey&lt;/param-name&gt;
	 *     &lt;param-value&gt;ABQIAAAAzaZpf6nHOd9w1PfLaM9u2xQRS2YPSd8S9D1NKPBvdB1fr18_CxR-svEYj6URCf5QDFq3i03mqrDlbA&lt;/param-value&gt;
	 * &lt;/init-param&gt;
	 * [...]
	 * </pre>
	 * 
	 * @return Google Maps API key
	 */
	public String getGoogleMapsAPIkey()
	{
		String googleMapsAPIkey = getInitParameter(GOOGLE_MAPS_API_KEY_PARAM);
		if (googleMapsAPIkey == null)
		{
			throw new WicketRuntimeException("There is no Google Maps API key configured in the "
					+ "deployment descriptor of this application.");
		}
		return googleMapsAPIkey;
	}
          /**
	 * Gets the init parameter 'facebookAPIkey' of the filter, or throws a
	 * WicketRuntimeException, if it is not set.
	 *
	 *
	 * @return String - facebook Api key
	 */
	public String getFaceBookAPIkey()
	{
		String facebookAPIkey = getInitParameter(FACEBOOK_API_KEY_PARAM);
		if (facebookAPIkey == null)
		{
			throw new WicketRuntimeException("There is no Facebook API key configured in the "
					+ "deployment descriptor of this application.");
		}
		return facebookAPIkey;
	}

    
     /**
	 * Gets the init parameter 'facebookSecretkey' of the filter, or throws a
	 * WicketRuntimeException, if it is not set.
	 *
	 *
	 * @return String - facebook secret key
	 */
	public String getFaceBookSecretkey()
	{
		String facebookSecretkey = getInitParameter(FACEBOOK_SECRET_KEY_PARAM );
		if (facebookSecretkey == null)
		{
			throw new WicketRuntimeException("There is no Facebook Secret key configured in the "
					+ "deployment descriptor of this application.");
		}
		return facebookSecretkey;
	}
    public void setApplicationContext(ApplicationContext arg) throws BeansException {
        this.ctx=arg;
    }
    @Override
    protected IRequestCycleProcessor newRequestCycleProcessor() {
        return new WebRequestCycleProcessor() {

            @Override
            protected IRequestCodingStrategy newRequestCodingStrategy() {
                return new CryptedUrlWebRequestCodingStrategy(
                        new WebRequestCodingStrategy());
            }
        };
    }

   

    
}
