package us.elite.views.profile;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.BoxSizing;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import us.elite.scholar.ScholarArticle;
import us.elite.scholar.ScholarInterest;
import us.elite.scholar.ScholarProfileResult;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class ProfileView {

    private HorizontalLayout createText(String name, String text)  {
        final HorizontalLayout horizontalLayout = new HorizontalLayout();
        final Span span = new Span(name);
        span.getStyle().set("margin", "0px");
        span.getStyle().set("padding", "0px");
        span.getStyle().set("font-weight", "bold");
        final Paragraph paragraph = new Paragraph(text == null || text.isBlank() ? "None" : text);
        paragraph.getStyle().set("margin", "0px");
        paragraph.getStyle().set("padding", "0px");
        horizontalLayout.add(span, paragraph);
        return horizontalLayout;
    }

    private HorizontalLayout createLayout(String name)  {
        final HorizontalLayout horizontalLayout = new HorizontalLayout();
        final Span span = new Span(name);
        span.getStyle().set("margin", "0px");
        span.getStyle().set("padding", "0px");
        span.getStyle().set("font-weight", "bold");
        horizontalLayout.add(span);
        return horizontalLayout;
    }

    private Grid<ScholarArticle> getArticlesGrid(Collection<ScholarArticle> articles) {
        final Grid<ScholarArticle> articleGrid = new Grid<>(ScholarArticle.class);
        articleGrid.addColumn(new ComponentRenderer<>(element -> {
            final Anchor anchor = new Anchor();
            anchor.setHref(element.link());
            anchor.setText(element.title());
            return anchor;
        })).setHeader("Title");
        articleGrid.addColumn(ScholarArticle::year).setHeader("Year");
        articleGrid.addColumn(ScholarArticle::publication).setHeader("Publication");
        articleGrid.addColumn(ScholarArticle::authors).setHeader("Authors");
        articleGrid.setSizeFull();
        articleGrid.getColumns().forEach(column -> column.setResizable(true));
        articleGrid.setItems(articles);
        return articleGrid;
    }

    public VerticalLayout getComponents(ScholarProfileResult scholarProfileResult) {

        final VerticalLayout layout = new VerticalLayout();
        layout.getStyle().set("height", "100vh");
        layout.setAlignItems(FlexComponent.Alignment.START);
        layout.add(createText("ID: ", scholarProfileResult.id()),
                createText("Full Name: ", scholarProfileResult.fullName()),
                createText("Affiliations: ", scholarProfileResult.affiliations()),
                createText("Email: ", scholarProfileResult.email()),
                createText("Cited By: ", String.valueOf(scholarProfileResult.citedBy())));

        final List<ScholarInterest> interests = scholarProfileResult.getScholarInterests();

        final List<Anchor> interestList = interests.stream()
                .map(interest -> new Anchor(interest.link(), interest.title()))
                .toList();

        if (interestList.isEmpty())
            layout.add(createText("Interests: ", "None"));
        else {
            final HorizontalLayout horizontalLayout = createLayout("Interests: ");
            interestList.forEach(horizontalLayout::add);
            layout.add(horizontalLayout);
        }

        layout.setWidth("85vw");
        layout.getStyle().set("background-color", "#F8F8FF");
        layout.addClassName(scholarProfileResult.id());
        layout.setBoxSizing(BoxSizing.BORDER_BOX);

        try {
            layout.add(getArticlesGrid(scholarProfileResult.getScholarArticles()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return layout;
    }
}
