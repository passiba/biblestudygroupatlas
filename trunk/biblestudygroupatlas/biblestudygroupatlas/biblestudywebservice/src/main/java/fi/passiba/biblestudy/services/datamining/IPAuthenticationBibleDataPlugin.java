/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.biblestudy.services.datamining;

import java.util.List;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerPlugin;

/**
 *
 * @author haverinen
 */
public class IPAuthenticationBibleDataPlugin implements BrokerPlugin{

    List<String> allowedIPAddresses;
    
    public Broker installPlugin(Broker br) throws Exception {
        return new IPBibleDataAuthenticationBroker(br,allowedIPAddresses);
    }

    public List<String> getAllowedIPAddresses() {
		return allowedIPAddresses;
	}

	public void setAllowedIPAddresses(List<String> allowedIPAddresses) {
		this.allowedIPAddresses = allowedIPAddresses;
	}

}
