/*
 * class containing constants used in the biblestudy application
 */
package fi.passiba.groups.ui.model;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author haverinen
 */
public class Constants {

    public static enum BibleSessionDataType {

        BIBLETRANSLATION("BIBLETRANSLATION"), BOOK("BOOK"), CHAPTER("CHAPTER"),
        VERSE("VERSE");
        private String type;

        private BibleSessionDataType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

     public static enum BibleSessionType {

        GROUP("Ryhmä"), PERSON("Henkilö");
        private String type;

        private BibleSessionType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public static List<String> getBibleSessionTypes() {

            return Arrays.asList(new String[]{GROUP.getType(), PERSON.getType()});
        }
    }

    public static enum GroupType {

        MENSGROUP("Miestenpiiri"), WOMENSGROUP("Naistenpiiri"), SUNDAYSCHOOL("Pyhäkoulu"), MOTHERBABYGROUP("Äiti/lapsi-piiri"), PRAYERGROUP("Rukouspiiri"),
        YOUNGADULTS("Nuoret aikuiset");
        private String type;

        private GroupType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public static List<String> getGroupTypes() {

            return Arrays.asList(new String[]{MENSGROUP.getType(), WOMENSGROUP.getType(), SUNDAYSCHOOL.getType(), MOTHERBABYGROUP.getType(), PRAYERGROUP.getType(), YOUNGADULTS.getType()});
        }
    }

    public static enum RoleType {

        ADMIN("Admin"), USER("User");
        private String type;

        private RoleType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public static List<String> getRoleTypes() {

            return Arrays.asList(new String[]{ADMIN.getType(), USER.getType()});
        }
    }
     public static enum StatusType {

        ACTIVE("Aktiivinen"), NOTACTIVE("Ei aktiivinen");
        private String type;

        private StatusType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public static List<String> getStatusTypes() {

            return Arrays.asList(new String[]{ACTIVE.getType(), NOTACTIVE.getType()});
        }
    }
     
    public static enum GroupSearchOption {

        TYPE("Ryhmätyyppi"), CITY("Kaupunki"),GROUPNAME("Nimi");
        private String option;

        private GroupSearchOption (String option) {
            this.option = option;
        }

        public String getOption() {
            return option;
        }

        public static List<String> getGroupSearchOptions() {

            return Arrays.asList(new String[]{TYPE.getOption(),CITY.getOption(),GROUPNAME.getOption()});
        }
    }

     public static enum PersonSearchOption {

        USERROLE("Rooli"), USERNAME("Käyttäjänimi"),CITY("Kaupunki");
        private String option;

        private PersonSearchOption (String option) {
            this.option = option;
        }

        public String getOption() {
            return option;
        }

        public static List<String> getPersonSearchOptions() {

            return Arrays.asList(new String[]{USERROLE.getOption(),USERNAME.getOption(),CITY.getOption()});
        }
    }


}
