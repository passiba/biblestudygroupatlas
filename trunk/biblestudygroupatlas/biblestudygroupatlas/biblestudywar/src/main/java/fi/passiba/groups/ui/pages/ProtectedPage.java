package fi.passiba.groups.ui.pages;

import org.apache.wicket.IPageMap;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;

public abstract class ProtectedPage extends WebPage {

  public ProtectedPage() {
  }

  public ProtectedPage(IModel model) {
    super(model);
  }

  public ProtectedPage(IPageMap pageMap) {
    super(pageMap);
  }

  public ProtectedPage(IPageMap pageMap, IModel model) {
    super(pageMap, model);
  }

  public ProtectedPage(PageParameters parameters) {
    super(parameters);
  }

  public ProtectedPage(IPageMap pageMap, PageParameters parameters) {
    super(pageMap, parameters);
  }
}
