package fi.passiba.groups.ui.pages.biblesession;

import fi.passiba.biblestudy.BibleStudyFaceBookSession;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fi.passiba.biblestudy.BibleStudySession;
import fi.passiba.groups.ui.model.DomainModelIteratorAdaptor;
import fi.passiba.groups.ui.model.HashcodeEnabledCompoundPropertyModel;
import fi.passiba.services.biblestudy.persistance.Chapter;
import fi.passiba.services.biblestudy.persistance.ChapterVoting;
import fi.passiba.services.biblestudy.persistance.Verse;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.rating.RatingPanel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class ChapterPanel extends AbstractDataPanel {



	private static final ResourceReference RATINGIMAGE= new ResourceReference(ChapterPanel.class,
			"groups.png");
    private static final ResourceReference RATINGIMAGE2= new ResourceReference(ChapterPanel.class,
			"groups2.png");

    public static RatingModel rating = new RatingModel();

    /**
	 * keeps track whether the user has already voted on this page, comes typically from the
	 * database, or is stored in a cookie on the client side.
	 */
	private Boolean hasVoted = Boolean.FALSE;

    public ChapterPanel(String id, final long chapterid) {


        super(id);

        IModel model = new CompoundPropertyModel(new LoadableDetachableModel() {

            public Object load() {
                return bibleTranslationDataRetrievalService.findChapterById(chapterid);
            }
        });

        setModel(model);
        init(chapterid);
      

    }
    @Override
    public boolean isVisible()
    {
       //return BibleStudySession.get().isAuthenticated();
       return BibleStudyFaceBookSession.get().isAuthenticated();
    }
    private void init(long chapterid) {

        add(new VersesForm("form", getModel(), chapterid));
    }

    private final class VersesForm extends Form {

        public VersesForm(String id, IModel m, long chapterid) {
            super(id, m);

            RefreshingView verses = populateBibleVerses(chapterid);
            verses.setItemReuseStrategy(ReuseIfModelsEqualStrategy.getInstance());
            add(verses);


            add(new Label("chapterTitle", new PropertyModel(getModel(), "chapterTitle")));
            add(new Label("chapterNum", new PropertyModel(getModel(), "chapterNum")));
            add(new ChapterRating("rating", new PropertyModel(rating, "rating"), 5, new PropertyModel(
			rating, "nrOfVotes"), true));


            /*add(new ChapterRating("rating", new PropertyModel(rating, "rating"),
							new Model(5), new PropertyModel(rating, "nrOfVotes"),
							new PropertyModel(this, "hasVoted"), true));*/

            add(new ResetRatingLink("reset", new Model(rating)));
            
            add(new NewButton("newButton"));
            add(new SaveButton("saveButton"));
            
            
        }

       

        private final class SaveButton extends Button {

            private SaveButton(String id) {
                super(id);
            }

            @Override
            public void onSubmit() {
                Chapter chapter = (Chapter) getForm().getModelObject();
                ChapterVoting rating=bibleTranslationDataRetrievalService.findRatingByChapterid(chapter.getId());
                if(rating==null)
                {
                    rating=new ChapterVoting ();
                }
                rating.setNumberofvotes(ChapterPanel.rating.getNrOfVotes());
                rating.setTotalscore(ChapterPanel.rating.getSumOfRatings());
                rating.setChapter(chapter);
                bibleTranslationDataRetrievalService.updateChapterVoting(rating);
               // bibleTranslationDataRetrievalService.updateChapter(chapter);
            // setResponsePage(ListContacts.class);
            //setResponsePage(EditPersonContact.this.backPage);
            }
        }

        private final class NewButton extends Button {

            private NewButton(String id) {
                super(id);
            }

            @Override
            public void onSubmit() {
                Chapter chapter = (Chapter) getForm().getModelObject();
                ChapterPanel.this.replaceWith(new NewVerseForm("bibleSessionpanel", chapter).setOutputMarkupId(true));
            }
        }

        private final class ChapterRating extends RatingPanel
        {

            
            private ChapterRating(java.lang.String id, IModel rating, int nrOfStars, IModel nrOfVotes, boolean addDefaultCssStyle)
            {
                super(id,rating, nrOfStars,nrOfVotes,addDefaultCssStyle);
            }
                  
            /*private ChapterRating(java.lang.String id, IModel rating, IModel nrOfStars, IModel nrOfVotes, IModel hasVoted, boolean addDefaultCssStyle)
            {
                  super(id, rating,nrOfStars,nrOfVotes,hasVoted,addDefaultCssStyle) ;
            }*/
            @Override
            protected String getActiveStarUrl(int iteration)
			{
				return getRequestCycle().urlFor(RATINGIMAGE2).toString();
			}
            @Override
			protected String getInactiveStarUrl(int iteration)
			{
				return getRequestCycle().urlFor(RATINGIMAGE).toString();
			}
            @Override
            protected boolean onIsStarActive(int star) {
                 return ChapterPanel.rating.isActive(star);
            }

            @Override
            protected void onRated(int rating, AjaxRequestTarget arg1) {
                 if(ChapterPanel.this.hasVoted != Boolean.TRUE);
                 {
                    ChapterPanel.rating.addRating(rating);
                 }
                 ChapterPanel.this.hasVoted = Boolean.TRUE;
            }

        }
        private final class ResetRatingLink extends Link {

            /** For serialization. */
            private static final long serialVersionUID = 1L;

            /**
             * Constructor.
             * 
             * @param id.
             *            component id
             * @param object
             *            the model to reset.
             */
            public ResetRatingLink(String id, IModel object) {
                super(id, object);
            }

            /**
             * @see Link#onClick()
             */
            public void onClick() {

               ChapterPanel.this.hasVoted = Boolean.FALSE;
               ChapterPanel.rating.setRating(0);
               ChapterPanel.rating.setNrOfVotes(0);
               ChapterPanel.rating.setSumOfRatings(0);
            }
        }
        private RefreshingView populateBibleVerses(final long chapterid) {
            RefreshingView bibleVerses = new RefreshingView("bibleVerses") {

                List<Verse> result = new ArrayList<Verse>(0);

                @Override
                protected Iterator getItemModels() {

                    result = bibleTranslationDataRetrievalService.findVersesByChapterId(chapterid);


                    return new DomainModelIteratorAdaptor<Verse>(result.iterator()) {

                        @Override
                        protected IModel model(final Object object) {

                            return new HashcodeEnabledCompoundPropertyModel((Verse) object);
                        }
                    };
                }

                @Override
                protected void populateItem(Item item) {

                Link editview = new Link("editView", item.getModel()) {
                    public void onClick() {
                        Verse verse = (Verse) getModelObject();
                        ChapterPanel.this.replaceWith(new NewVerseForm("bibleSessionpanel", verse).setOutputMarkupId(true));

                    }
                };
                editview.add(new Label("verseNum",
                            new PropertyModel(item.getModel(), "verseNum")));
                item.add(editview);

                item.add(new MultiLineLabel("verseText",
                            new PropertyModel(item.getModel(), "verseText")));
                }
            };
            return bibleVerses;
        }
    }
    public Boolean getHasVoted()
	{
		return ChapterPanel.this.hasVoted;
	}
}


