/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.biblestudy.services.datamining;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerFilter;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.command.ConnectionInfo;

/**
 *
 * @author haverinen
 */
public class IPBibleDataAuthenticationBroker extends BrokerFilter {

    List<String> allowedIPAddresses;
	Pattern pattern = Pattern.compile("^/([0-9\\.]*):(.*)");

	public IPBibleDataAuthenticationBroker(Broker next, List<String> allowedIPAddresses) {
		super(next);
		this.allowedIPAddresses = allowedIPAddresses;
	}

	public void addConnection(ConnectionContext context,
			ConnectionInfo info) throws Exception {

		String remoteAddress = context.getConnection().getRemoteAddress();

		Matcher matcher = pattern.matcher(remoteAddress);
		if (matcher.matches()) {
			String ip = matcher.group(1);
			if (!allowedIPAddresses.contains(ip)) {
				throw new SecurityException("Connecting from IP address " + ip + " is not allowed");
			}
		} else {
			throw new SecurityException("Invalid remote address " + remoteAddress);
		}

		super.addConnection(context, info);
	}

}