package us.elite.views.profile;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import us.elite.scholar.ScholarOrganicResult;

import java.util.ArrayList;
import java.util.List;

public class ProfileView {

    private Paragraph createParagraph(String text)  {
        final Paragraph paragraph = new Paragraph(text);
        paragraph.getStyle().set("margin", "0px");
        paragraph.getStyle().set("padding", "0px");
        return paragraph;
    }

    public VerticalLayout getComponents(ScholarOrganicResult scholarOrganicResult) {
        final Image profileImage;
        final Paragraph id, fullName, affiliations, email, citedBy;
        final List<Paragraph> interests = new ArrayList<>();

        profileImage = new Image(scholarOrganicResult.thumbnail(), "profile picture");
        
        profileImage.getStyle().set("width", "250px");
        profileImage.getStyle().set("height", "250px");
        profileImage.getStyle().set("border-radius", "25px");

        id = createParagraph("ID: " + scholarOrganicResult.id());
        fullName = createParagraph("Full Name: " + scholarOrganicResult.fullName());
        affiliations = createParagraph("Affiliations: " + scholarOrganicResult.affiliations());
        email = createParagraph("Email: " + (scholarOrganicResult.email() == null || scholarOrganicResult.email().isBlank() ? "None" : scholarOrganicResult.email()));
        citedBy = createParagraph("Cited By: " + scholarOrganicResult.citedBy());

        final VerticalLayout layout = new VerticalLayout();
        layout.getStyle().set("height", "100vh");
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        layout.add(profileImage, id, fullName, affiliations, email, citedBy);
        scholarOrganicResult.getScholarInterests().forEach(element -> {
            layout.add(createParagraph("Title: " + element.title()));
            layout.add(createParagraph("Link: "), new Anchor(element.link()));
            layout.add(createParagraph("Serp API Link: "), new Anchor(element.serpApiLink()));
        });
        layout.setWidth("50vw");
        layout.getStyle().set("background-color", "#CBD3D8");
        layout.getStyle().set("border-radius", "25px");
        layout.addClassName(scholarOrganicResult.id());
        return layout;
    }
}
