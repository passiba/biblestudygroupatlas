/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.biblestudy.crosswaybible.webservice.client.domain;

import fi.passiba.biblestudy.crosswaybible.webservice.client.jaxb.request.Options;

/**
 *
 * @author uniikkiihminen
 */
public class RequestDetails {
    
    private String key;
    private String passage;
    private Options options;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public String getPassage() {
        return passage;
    }

    public void setPassage(String passage) {
        this.passage = passage;
    }

}
