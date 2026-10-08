package ressources;

import entities.Option;
import utilities.Services;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/options")
public class optionressource {

    // GET /options  or  GET /options?domaine=Mathématiques
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("domaine") String domaine) {
        List<Option> result;
        if (domaine == null) {
            result = Services.OPTIONS.getListeOptions();
        } else {
            result = Services.OPTIONS.getOptionsByDomaine(domaine);
        }
        return Response.ok(result).build();
    }

    // GET /options/1
    @GET
    @Path("/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOptionByCode(@PathParam("code") int code) {
        Option o = Services.OPTIONS.getOptionByCode(code);
        if (o == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(o).build();
    }

    // POST /options
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addOption(Option option) {
        Services.OPTIONS.addOption(option);
        return Response.ok().build();
    }

    // PUT /options/1
    @PUT
    @Path("/{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOption(@PathParam("code") int code, Option updatedOption) {
        if (Services.OPTIONS.updateOption(code, updatedOption)) {
            return Response.ok(updatedOption).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // DELETE /options/2
    @DELETE
    @Path("/{code}")
    public Response deleteOption(@PathParam("code") int code) {
        if (Services.OPTIONS.deleteOption(code)) {
            return Response.noContent().build();   // 204
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}