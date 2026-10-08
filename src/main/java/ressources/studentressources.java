package ressources;

import entities.Etudiant;
import entities.EtudiantList;
import entities.Option;
import utilities.Services;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/etudiants")
public class studentressources {

    // GET /etudiants
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiants() {
        return Response.ok(Services.ETUDIANTS.getAllEtudiants()).build();
    }

    // GET /etudiants/I003
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiant(@PathParam("id") String id) {
        Etudiant e = Services.ETUDIANTS.getEtudiantByIdentifiant(id);
        if (e == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(e).build();
    }

    // POST /etudiants  (404 if the option inside doesn't exist)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEtudiant(Etudiant etudiant) {
        if (etudiant.getOption() == null || !Services.ETUDIANTS.addEtudiant(etudiant)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().build();
    }

    // PUT /etudiants/I001
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEtudiant(@PathParam("id") String id, Etudiant etudiant) {
        if (Services.ETUDIANTS.updateEtudiant(id, etudiant)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // DELETE /etudiants/I003
    @DELETE
    @Path("/{id}")
    public Response deleteEtudiant(@PathParam("id") String id) {
        if (Services.ETUDIANTS.deleteEtudiant(id)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // GET /etudiants/option?codeOption=1   -> XML
    @GET
    @Path("/option")
    @Produces(MediaType.APPLICATION_XML)
    public Response getEtudiantsByOption(@QueryParam("codeOption") int codeOption) {
        Option option = Services.OPTIONS.getOptionByCode(codeOption);
        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        EtudiantList list = new EtudiantList(Services.ETUDIANTS.getEtudiantsByOption(option));
        return Response.ok(list).build();
    }
}