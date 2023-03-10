package hexlet.code.controllers;

import hexlet.code.model.Label;
import hexlet.code.services.LabelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/labels")
@SecurityRequirement(name = "javainuseapi")
public class LabelController {

    @Autowired
    private LabelService labelService;

    public static final String ID = "/{id}";

    @Operation(summary = "Get all Labels")
    @ApiResponses(
            @ApiResponse(responseCode = "200", content =
                @Content(array = @ArraySchema(schema = @Schema(implementation = Label.class)))
            )
    )
    @GetMapping
    public List<Label> getAll() {
        return labelService.findAll();
    }

    @Operation(summary = "Create new Label")
    @ApiResponse(responseCode = "201", description = "Label created")
    @PostMapping
    @ResponseStatus(CREATED)
    public Label create(
            @Parameter(schema = @Schema(implementation = Label.class))
            @RequestBody @Valid final Label label) {
        return labelService.createLabel(label);
    }

    @Operation(summary = "Get label by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Label found"),
            @ApiResponse(responseCode = "404", description = "Label with that id not found")
    })
    @GetMapping(ID)
    public Label findById(@PathVariable final long id) {
        return labelService.findLabelById(id);
    }

    @Operation(summary = "Update Label")
    @ApiResponse(responseCode = "200", description = "Label updated")
    @PutMapping(ID)
    public Label update(@PathVariable final long id,
                        @Parameter(schema = @Schema(implementation = Label.class))
                        @RequestBody @Valid final Label label) {
        return labelService.updateLabel(id, label);
    }

    @Operation(summary = "Delete Label")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Label deleted"),
            @ApiResponse(responseCode = "404", description = "Label with that id not found")
    })
    @DeleteMapping(ID)
    public void delete(@PathVariable final long id) {
        labelService.deleteLabel(id);
    }
}

