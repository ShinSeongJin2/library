package library.infra;

import library.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class BookHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Book>> {

    @Override
    public EntityModel<Book> process(EntityModel<Book> model) {
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/registerbook")
                .withRel("registerbook")
        );
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/undefined")
                .withRel("")
        );
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/undefined")
                .withRel("")
        );

        return model;
    }
}
