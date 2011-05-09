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
package fi.passiba.groups.ui.pages.wizards.userCreation;

import fi.passiba.groups.ui.pages.wizards.BaseAddressdModel;
import java.util.HashSet;
import java.util.Set;

/**
 * Domain class for the new user wizard example.
 * 
 * @author Eelco Hillenius
 */
public final class User extends BaseAddressdModel {

    private String group = "";
    private String email;
    private String firstName;
    private String lastName;
    private String roleName;
    private Set roles = new HashSet();
    private String userName;
    private String confirmpassword;
    private String password;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getGroup() {
        return group;
    }

    /**
     * Gets email.
     * 
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets firstName.
     * 
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets lastName.
     * 
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets roles.
     * 
     * @return roles
     */
    public Set getRoles() {
        return roles;
    }

    /**
     * Gets userName.
     * 
     * @return userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets group.
     * 
     * @param group
     *            group
     */
    public void setGroup(String group) {
        if (group == null) {
            group = "";
        }
        this.group = group;
    }

    /**
     * Sets email.
     * 
     * @param email
     *            email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets firstName.
     * 
     * @param firstName
     *            firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets lastName.
     * 
     * @param lastName
     *            lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets roles.
     * 
     * @param roles
     *            roles
     */
    public void setRoles(Set roles) {
        this.roles = roles;
    }

    /**
     * Sets userName.
     * 
     * @param userName
     *            userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
