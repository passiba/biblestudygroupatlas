package fi.passiba.groups;

import fi.passiba.services.group.IGroupServices;
import fi.passiba.services.persistance.Adress;
import fi.passiba.services.group.dao.IGroupsDAO;
import fi.passiba.services.group.persistance.Groups;
import java.util.List;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public final class GroupServiceImpTest extends AbstractDependencyInjectionSpringContextTests {

    private String groupType = "Miestenpiiri",  city = "Espoo",  country = "Finland";

    @Override
    protected String[] getConfigLocations() {
        return new String[]{"classpath:applicationContext.xml"};
    }

    private Groups addGroup(Groups group) throws Exception {
        IGroupServices groupServices = (IGroupServices) applicationContext.getBean("IGroupServices");
        if (group == null) {
            Adress ad = new Adress();
            ad.setAddr1("Testikuja 2 b");
            ad.setAddr2("PL 39");
            ad.setCity("Helsinki");
            ad.setCountry(country);
            ad.setPhone("0505555555");
            ad.setState("Uusimaa");
            ad.setZip("01000");


            group = new Groups();
            group.setAdress(ad);

            group.setCongregatiolistemailaddress("hellarit@svk.fi");
            group.setCongregationname("Espoon L�hiseurakunta");
            group.setCongregationwebsiteurl("www.lahisrk.fi");
            group.setGrouptypename(groupType);
            group.setDescription("Kristillisten miesten kasvuryhm�");
            group.setStatus("Aktiivinen");
            group.setName("Miehet muutoksessa");
            group.setCreatedBy("Admin");
            group.setCreatedBy("Admin");

        }

        groupServices.addGroup(group);
        return group;
    }

    public void testAddingNewGroup() throws Exception {

        
      /*  IGroupsDAO groupDAO = (IGroupsDAO) applicationContext.getBean("GroupsDAO");
        Groups group = addGroup(null);
        List<Groups> groups = groupDAO.getByExample(group);
        Groups fetchGroup = null;
        for (Groups grp : groups) {
            fetchGroup = grp;
        }
        assert (group.getName().equals(fetchGroup.getName()));*/
    }

    public void testUpdatingGroup() throws Exception {
       IGroupServices groupServices = (IGroupServices) applicationContext.getBean("IGroupServices");

        Adress ad = new Adress();
        ad.setAddr1("Testikuja 1 b");
        ad.setAddr2("PL 39");
        ad.setCity(city);
        ad.setCountry("Finland");
        ad.setPhone("0505555555");
        ad.setState("Uusimaa");
        ad.setZip("01000");
        List<Groups> groups = groupServices.findGroupsByLocation(country, city, groupType);

        Groups group = null;
        for (Groups grp : groups) {
            group = grp;
        }
        if (groups == null || groups.isEmpty()) {
            group = addGroup(null);
        }
        group.setAdress(ad);

        groupServices.updateGroup(group);
        groups =  groupServices.findGroupsByLocation(country, city, groupType);
        Groups fetchGroup = null;
        for (Groups grp : groups) {
            fetchGroup = grp;
        }
        assert (group.getAdress().getCity().equals(fetchGroup.getAdress().getCity()));
    }

    public void testDeletingGroup() throws Exception {
        IGroupServices groupServices = (IGroupServices) applicationContext.getBean("IGroupServices");

        List<Groups> groups = groupServices.findGroupsByLocation(country, city, groupType);

        Groups group = null;
        for (Groups grp : groups) {
            group = grp;
        }
        if (groups == null || groups.isEmpty()) {
            group = addGroup(null);
        }
        groupServices.deleteGroup(group);
        groups = groupServices.findGroupsByLocation(country, city, groupType);
        assertEquals(0, groups.size());
    }
}