package fi.passiba.groups.ui.pages.googlemap;

import fi.passiba.biblestudy.BibleStudyApplication;

import fi.passiba.groups.ui.pages.wizards.BaseAddressdModel;
import fi.passiba.services.persistance.Adress;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;

import org.apache.wicket.model.PropertyModel;
import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.GMapHeaderContributor;
import wicket.contrib.gmap.api.GClientGeocoder;
import wicket.contrib.gmap.api.GControl;
import wicket.contrib.gmap.api.GIcon;
import wicket.contrib.gmap.api.GInfoWindowTab;
import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.api.GMapType;
import wicket.contrib.gmap.api.GMarker;
import wicket.contrib.gmap.api.GMarkerOptions;
import wicket.contrib.gmap.api.GOverlay;
import wicket.contrib.gmap.api.GPoint;
import wicket.contrib.gmap.api.GSize;
import wicket.contrib.gmap.util.GeocoderException;

/**
 * Example HomePage for the wicket-contrib-gmap2 project
 */
public class GoogleMapsPanel extends Panel {

    private static final long serialVersionUID = 1L;
    private final FeedbackPanel feedback;
    private BaseAddressdModel addr = new BaseAddressdModel();

   
    private class  GeoForm extends Form {

        public GeoForm(String id, IModel m) {
            super(id, m);




        }
    }
    public GoogleMapsPanel(String id, boolean required) {
        super(id);
        setModel(new CompoundPropertyModel(new LoadableDetachableModel() {

            public Object load() {
                 return addr;
            }
        }));
 
            final GMap2 bottomMap = new GMap2("bottomPanel",
                    new GMapHeaderContributor(BibleStudyApplication.get().getGoogleMapsAPIkey()));
            bottomMap.setOutputMarkupId(true);
            bottomMap.setMapType(GMapType.G_NORMAL_MAP);
            bottomMap.addControl(GControl.GSmallMapControl);
            bottomMap.setScrollWheelZoomEnabled(true);
            bottomMap.setZoom(9);
            add(bottomMap);

            GeoForm geocodeForm = new GeoForm("geocoder", getModel());
            final String addressInfo = getLocalizer().getString("addressInfo", this);

            feedback = new FeedbackPanel("feedback");
            feedback.setOutputMarkupId(true);
            //add(feedback);

            geocodeForm.add(feedback);
            final TextField addressTextField = new TextField("address", new Model(""));
            addressTextField.setRequired(required);
            geocodeForm.add(addressTextField);

            Button button = new Button("client");
            //Using GClientGeocoder the geocoding request
            //is performed on the client using JavaScript 


           
            geocodeForm.add(button);
            add(geocodeForm);
            button.add(new GClientGeocoder("onclick", addressTextField,
                    BibleStudyApplication.get().getGoogleMapsAPIkey()) {

                @Override
                public void onGeoCode(AjaxRequestTarget target, int status,
                        String address, GLatLng latLng) {
                    if (status == GeocoderException.G_GEO_SUCCESS) {


                        addr.setLat(latLng.getLat());
                        addr.setLng(latLng.getLng());
                        
                        bottomMap.setCenter(new GLatLng(latLng.getLat(), latLng.getLng()));
                        GIcon icon = new GIcon(urlFor(
                                new ResourceReference(GoogleMapsPanel.class, "image.gif")).toString(), urlFor(
                                new ResourceReference(GoogleMapsPanel.class, "shadow.png")).toString()).iconSize(new GSize(64, 64)).shadowSize(new GSize(64, 64)).iconAnchor(new GPoint(19, 40)).infoWindowAnchor(new GPoint(9, 2)).infoShadowAnchor(new GPoint(18, 25));

                        GOverlay marker = new GMarker(new GLatLng(latLng.getLat(), latLng.getLng()),
                                new GMarkerOptions(addressInfo, icon));

                        bottomMap.addOverlay(marker);
                        bottomMap.getInfoWindow().open(
                        latLng,
                        new GInfoWindowTab(address, new Label(address,
                        address)));
                        
                        target.addComponent(bottomMap);
                        

                    } else {
                        error("Unable to geocode (" + status + ")");
                        target.addComponent(feedback);
                    }
                }
                ;
            });
    }
     public BaseAddressdModel getAddr() {
        return addr;
    }

    public void setAddr(BaseAddressdModel addr) {
        this.addr = addr;
    }
    
}
