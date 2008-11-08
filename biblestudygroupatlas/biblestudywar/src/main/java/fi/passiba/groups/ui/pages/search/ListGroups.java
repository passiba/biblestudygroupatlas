package fi.passiba.groups.ui.pages.search;

import fi.passiba.biblestudy.BibleStudyApplication;
import fi.passiba.biblestudy.BibleStudyFaceBookSession;
import fi.passiba.groups.ui.model.DomainModelIteratorAdaptor;
import fi.passiba.groups.ui.model.HashcodeEnabledCompoundPropertyModel;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.queryParser.ParseException;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import fi.passiba.groups.ui.pages.BasePage;
import fi.passiba.groups.ui.pages.group.EditGroupInfo;
import fi.passiba.groups.ui.pages.group.ViewGroupInfo;
import fi.passiba.hibernate.PaginationInfo;
import fi.passiba.services.address.IAddressService;
import fi.passiba.services.authenticate.IAuthenticator;
import fi.passiba.services.group.IGroupServices;
import fi.passiba.services.group.persistance.Groups;
import fi.passiba.services.persistance.Adress;
import fi.passiba.services.persistance.Person;
import fi.passiba.services.search.ISearchService;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.GMapHeaderContributor;
import wicket.contrib.gmap.api.GControl;
import wicket.contrib.gmap.api.GIcon;
import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.api.GMapType;
import wicket.contrib.gmap.api.GMarker;
import wicket.contrib.gmap.api.GMarkerOptions;
import wicket.contrib.gmap.api.GOverlay;
import wicket.contrib.gmap.api.GPoint;
import wicket.contrib.gmap.api.GSize;

public class ListGroups extends BasePage {

    @SpringBean
    private IGroupServices groupService;
    @SpringBean
    private IAddressService addressservice;
    @SpringBean
    private ISearchService searchService;
    @SpringBean
    private IAuthenticator authenticate;
    private PaginationInfo pageInfo;
    private List<Groups> results = new ArrayList<Groups>(0);

    public ListGroups(PageParameters params) {

        init(params);

    }

    private void init(PageParameters params) {

        final String searchCriteria = params.getString("searchcriteria");
        final String searchString = params.getString("searchString");
        final Person loggedInPerson = getLoggInPerson();
        String country = "", city = "";
        if (loggedInPerson != null) {
            city = loggedInPerson.getAdress().getCity();
            country = loggedInPerson.getAdress().getCountry();
        }

        if (searchCriteria != null && searchString != null) {
            try {
                if (searchCriteria.equals("Ryhmätyyppi")) {

                    results = searchService.findGroupsByType(country, searchString);


                } else if (searchCriteria.equals("Kaupunki")) {


                    // results = groupService.findGroupsByLocation(country, city, searchCriteria);
                    results = searchService.findGroupsByLocation(country, searchString);

                } else {
                    results = searchService.findGroupsByName(searchString, 0, 20);
                // pageInfo = groupService.findPagingInfoForGroups(10);
                //pageInfo.setFirstRow(0);
                // results = groupService.findGroupsWithPaging(pageInfo);
                }
            } catch (ParseException ex) {
                throw new WicketRuntimeException(ex);
            }
        }
        add(populateSearchResult(results));

        IModel linkModel = new CompoundPropertyModel(new LoadableDetachableModel() {

            protected Object load() {
                pageInfo = groupService.findPagingInfoForGroups(10);
                return pageInfo;
            }
        });
        Link previous = new Link("previous", new Model(linkModel)) {

            public void onClick() {
                pageInfo = (PaginationInfo) getModelObject();
                pageInfo.setFirstRow(pageInfo.getFirstRow() - 1);
                results = groupService.findGroupsWithPaging(pageInfo);
                setResponsePage(ListGroups.class);
            }
        };
        Link next = new Link("next", new Model(linkModel)) {

            public void onClick() {
                pageInfo = (PaginationInfo) getModelObject();
                pageInfo.setFirstRow(pageInfo.getFirstRow() + 1);
                results = groupService.findGroupsWithPaging(pageInfo);
                setResponsePage(ListGroups.class);
            }
        };
        if (pageInfo == null) {
            previous.setVisible(false);
            next.setVisible(false);
        } else if (pageInfo != null) {
            previous.setVisible(pageInfo.isPreviousPage());
            next.setVisible(pageInfo.isPreviousPage());
        }
        add(previous);
        add(next);

        /*@AdminOnly
        private class UserLink extends Link {

        UserLink(String id) {
        super(id);
        }

        @Override
        public void onClick() {
        inEditMode = !inEditMode;
        setContentPanel();
        }
        }
        private boolean inEditMode = false;
        void setContentPanel() {
        if (inEditMode) {
        addOrReplace(new DiscountsEditList("content"));
        } else {
        addOrReplace(new DiscountsList("content"));
        }
        }*/


        add(createSearchResultMap(results));


    }

    private Person getLoggInPerson() {
        List<Person> persons = authenticate.findPerson(BibleStudyFaceBookSession.get().getFaceBookUserName());
        Person currentLogInPerson = null;
        if (persons != null && !persons.isEmpty()) {
            currentLogInPerson = persons.get(0);
        }
        return currentLogInPerson;
    }

    private GOverlay createOverlay(String title, GLatLng latLng, String image,
            String shadow) {
        GIcon icon = new GIcon(urlFor(
                new ResourceReference(ListGroups.class, image)).toString(), urlFor(
                new ResourceReference(ListGroups.class, shadow)).toString()).iconSize(new GSize(64, 64)).shadowSize(new GSize(64, 64)).iconAnchor(new GPoint(19, 40)).infoWindowAnchor(new GPoint(9, 2)).infoShadowAnchor(new GPoint(18, 25));
        return new GMarker(latLng, new GMarkerOptions(title, icon));
    }

    private RefreshingView populateSearchResult(final List<Groups> results) {

        RefreshingView groups = new RefreshingView("groups") {

            @Override
            protected Iterator getItemModels() {

                return new DomainModelIteratorAdaptor<Groups>(results.iterator()) {

                    @Override
                    protected IModel model(final Object object) {

                        return new HashcodeEnabledCompoundPropertyModel((Groups) object);
                    }
                };
            }

            @Override
            protected void populateItem(Item item) {

                Link linkview = new Link("view", item.getModel()) {

                    public void onClick() {
                        Groups g = (Groups) getModelObject();
                        setResponsePage(new ViewGroupInfo(getPage(), g.getId()));
                    }
                };
                linkview.add(new Label("name",
                        new PropertyModel(item.getModel(), "name")));

                item.add(linkview);
                item.add(new Label("congregationname",
                        new PropertyModel(item.getModel(), "congregationname")));
                item.add(new Label("type",
                        new PropertyModel(item.getModel(), "grouptypename")));

                item.add(new ExternalLink("link", new PropertyModel(item.getModel(), "congregationwebsiteurl")).add(new Label("congregationwebsiteurl",
                        new PropertyModel(item.getModel(), "congregationwebsiteurl"))));


                item.add(new Link("edit", item.getModel()) {

                    public void onClick() {
                        Groups g = (Groups) getModelObject();
                        setResponsePage(new EditGroupInfo(getPage(), g.getId()));
                    }
                });
                item.add(new Link("delete", item.getModel()) {

                    public void onClick() {
                        Groups g = (Groups) getModelObject();
                        groupService.deleteGroup(g);
                        setResponsePage(ListGroups.class);
                    }
                });

            }
        };
        groups.setItemReuseStrategy(ReuseIfModelsEqualStrategy.getInstance());
        return groups;

    }

    private GMap2 createSearchResultMap(List<Groups> results) {
        GMap2 bottomMap = new GMap2("bottomPanel",
                new GMapHeaderContributor(BibleStudyApplication.get().getGoogleMapsAPIkey()));

        bottomMap.setOutputMarkupId(true);
        bottomMap.setMapType(GMapType.G_NORMAL_MAP);
        bottomMap.addControl(GControl.GLargeMapControl);
        bottomMap.setScrollWheelZoomEnabled(true);
        bottomMap.setZoom(9);
        bottomMap.setCenter(new GLatLng(60.226280212402344, 24.820398330688477));

        for (Groups group : results) {

            long address_id = group.getAdress().getId();
            Adress address = addressservice.findAddressByAddressId(address_id);
            bottomMap.addOverlay(createOverlay(group.getName(), new GLatLng(address.getLocation_lat(),
                    address.getLocation_lng()), "groups.png", "shadow.png"));

        }

        ///bottomMap.setOverlays(overlays);

        return bottomMap;

    }
}
