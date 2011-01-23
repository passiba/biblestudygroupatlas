/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fi.passiba.groups.ui.pages.wizards.groupCreation;

import fi.passiba.groups.ui.pages.wizards.BaseAddressdModel;
import fi.passiba.groups.ui.pages.wizards.userCreation.User;

/**
 * Domain class for the new user wizard example.
 * 
 * @author Eelco Hillenius
 */
public final class Group extends BaseAddressdModel {

    private String grouptype;
    // private Status status;
    private String name;
    private String congregationname;
    private String congregationwebsiteurl;
    private String congregatiolistemailaddress;
    private String congregationemail;
    User contactPerson = new User();

    public String getCongregationemail() {
        return congregationemail;
    }

    public void setCongregationemail(String congregationemail) {
        this.congregationemail = congregationemail;
    }

    public String getCongregatiolistemailaddress() {
        return congregatiolistemailaddress;
    }

    public void setCongregatiolistemailaddress(String congregatiolistemailaddress) {
        this.congregatiolistemailaddress = congregatiolistemailaddress;
    }

    public String getCongregationname() {
        return congregationname;
    }

    public void setCongregationname(String congregationname) {
        this.congregationname = congregationname;
    }

    public String getCongregationwebsiteurl() {
        return congregationwebsiteurl;
    }

    public void setCongregationwebsiteurl(String congregationwebsiteurl) {
        this.congregationwebsiteurl = congregationwebsiteurl;
    }

    public User getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(User contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getGrouptype() {
        return grouptype;
    }

    public void setGrouptype(String grouptype) {
        this.grouptype = grouptype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
