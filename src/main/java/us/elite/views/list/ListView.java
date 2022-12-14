package us.elite.views.list;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import us.elite.scholar.ScholarInterest;
import us.elite.scholar.ScholarProfileResult;
import us.elite.scholar.ScholarSearchResultService;
import us.elite.views.profile.ProfileView;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

@PageTitle("Author Search")
@Route(value = "")
public class ListView extends HorizontalLayout {

    private final ScholarSearchResultService scholarSearchResultService;
    private final ProfileView profileView;
    private VerticalLayout details = null;

    public ListView(ScholarSearchResultService scholarSearchResultService) {
        this.profileView = new ProfileView();
        this.scholarSearchResultService = scholarSearchResultService;

        setSpacing(false);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);

        final H1 title = new H1("Author Search");
        final Grid<ScholarProfileResult> grid = getGrid();
        title.setWidthFull();
        title.getStyle().set("margin", "7px 0 7px 0");

        getStyle().set("text-align", "center");



        add(new VerticalLayout(
                title,
                getSearchBar(grid),
                grid
        ));

    }

    private Component getSearchBar(Grid<ScholarProfileResult> grid) {
        final TextField searchField = new TextField("Author name:");
        final TextField count = new TextField("Count:");

        searchField.setPlaceholder("Enter author name");
        searchField.setValueChangeMode(ValueChangeMode.LAZY);
        searchField.setClearButtonVisible(true);

        count.setPlaceholder("Enter result count");
        count.setValueChangeMode(ValueChangeMode.LAZY);
        count.setClearButtonVisible(true);

        final Button submit = new Button("Search");

        submit.addClickListener(event -> {
            grid.setItems(new ArrayList<>());
            scholarSearchResultService.requestBuilder()
                    .setAuthor(searchField.getValue().replace(" ", "+"));

            if (!count.getValue().isBlank())
                scholarSearchResultService.requestBuilder()
                        .setMaxResults(Integer.parseInt(count.getValue()));
            else
                scholarSearchResultService.requestBuilder().setMaxResults(0);

            try {
                grid.setItems(scholarSearchResultService.result());
            } catch (URISyntaxException | InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });

        final HorizontalLayout layout = new HorizontalLayout(searchField, count, submit);
        layout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        return layout;
    }

    private Grid<ScholarProfileResult> getGrid() {
        final Grid<ScholarProfileResult> grid = new Grid<>(ScholarProfileResult.class);
        grid.removeAllColumns();
        grid.addComponentColumn(result ->{
            final Image image = new Image(result.thumbnail(), "profile picture");
            image.getStyle().set("border-radius", "25px");
            return image;
        }).setHeader("Profile picture");
       // grid.addColumn(ScholarProfileResult::id).setHeader("Id");
        grid.addColumn(ScholarProfileResult::fullName).setHeader("Full Name");
      //  grid.addColumn(result -> (result.email() == null || result.email().isBlank()) ? "None" : result.email()).setHeader("Email");
        grid.addColumn(result -> {
            if (!result.getScholarInterests().isEmpty())
                return result.getScholarInterests().stream()
                        .map(ScholarInterest::title)
                        .reduce("", (subtotal, element) -> subtotal + " " + element);
            else
                return "None";
        }).setHeader("Interests");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));

        grid.addItemClickListener(event -> {
            if (details == null) {
                details = profileView.getComponents(event.getItem());
                add(details);
            }

            else if (details.hasClassName(event.getItem().id())) {
                remove(details);
                details = null;
            }
            else {
                remove(details);
                details = profileView.getComponents(event.getItem());
            }
        });
        return grid;
    }

}
