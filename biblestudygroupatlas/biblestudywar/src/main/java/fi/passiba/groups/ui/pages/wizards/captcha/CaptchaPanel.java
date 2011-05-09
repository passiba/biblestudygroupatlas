package fi.passiba.groups.ui.pages.wizards.captcha;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.extensions.markup.html.captcha.CaptchaImageResource;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.ValueMap;

public class CaptchaPanel extends Panel {

    /** Random captcha password to match against. */
    private String imagePass = randomString(6, 8);
    private final ValueMap properties = new ValueMap();
 
    public CaptchaPanel(String id) {
        
        
        super(id);
        final String ok= getLocalizer().getString("ok", this);
        final CaptchaForm captchaform=new CaptchaForm("captchaForm");
        captchaform.setOutputMarkupId(true);
        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        feedback.setOutputMarkupId(true);
        final CaptchaImageResource captchaImageResource= new CaptchaImageResource(imagePass);
        captchaform.add(new Image("captchaImage", captchaImageResource));
        captchaform.add(feedback);
      
      
        RequiredTextField<String> password = new RequiredTextField("captchapassword", new PropertyModel<String>(properties, "captchapassword")) {

            protected final void onComponentTag(final ComponentTag tag) {
                super.onComponentTag(tag);
                // clear the field after each render
                tag.put("value", "");
            }
        };
        captchaform.add(password);
        add(captchaform);
        password.add(new AjaxFormComponentUpdatingBehavior("onchange") {

            protected void onUpdate(AjaxRequestTarget target) {
                    if (!imagePass.equals(getPassword())) {
                        error("Captcha salasana '" + getPassword() + "' on väärä.\n" +
                                "Oikea salasana on: " + imagePass);
                    } else {
                       
                        info(ok);
                       captchaform.setVisible(false);
                    }
                    target.addComponent(feedback);
                    target.addComponent(captchaform);
                    captchaImageResource.invalidate();
                    

                }
        });
    }

    private final class CaptchaForm extends Form {

        /*private static final long serialVersionUID = 1L;
        private final CaptchaImageResource captchaImageResource;*/

        /**
         * Construct.
         * 
         * @param id
         */
        public CaptchaForm(String id) {
            super(id);
            
            
           /* final FeedbackPanel feedback = new FeedbackPanel("feedback");
            add(feedback);
            captchaImageResource = new CaptchaImageResource(imagePass);
            add(new Image("captchaImage", captchaImageResource));


            add(new RequiredTextField("captchapassword", new PropertyModel(properties, "captchapassword")) {

                protected final void onComponentTag(final ComponentTag tag) {
                    super.onComponentTag(tag);
                    // clear the field after each render
                    tag.put("value", "");
                }
            }).add(new AjaxFormComponentUpdatingBehavior("onchange") {

                protected void onUpdate(AjaxRequestTarget target) {
                    if (!imagePass.equals(getPassword())) {
                        error("Captcha salasana '" + getPassword() + "' on väärä.\n" +
                                "Oikea salasana on: " + imagePass);
                    } else {
                        info("Onnistui!");
                    }
                    target.addComponent(feedback);
                    captchaImageResource.invalidate();
                    

                }
            });*/

        }

        /**
         * @see org.apache.wicket.markup.html.form.Form#onSubmit()
         
        public void onSubmit() {
            if (!imagePass.equals(getPassword())) {
                error("Captcha salasana '" + getPassword() + "' on väärä.\n" +
                        "Oikea salasana on: " + imagePass);
            } else {
                info("Onnistui!");
                this.setVisible(false);
            }

            // force redrawing
            captchaImageResource.invalidate();
        }*/
    }
    private static final long serialVersionUID = 1L;

    private static int randomInt(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    private static String randomString(int min, int max) {
        int num = randomInt(min, max);
        byte b[] = new byte[num];
        for (int i = 0; i < num; i++) {
            b[i] = (byte) randomInt('a', 'z');
        }
        return new String(b);
    }

    private String getPassword() {
        return properties.getString("captchapassword");
    }
}
