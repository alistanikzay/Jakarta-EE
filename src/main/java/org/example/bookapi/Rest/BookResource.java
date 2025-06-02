package org.example.bookapi.Rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.bookapi.Service.BookService;
import org.example.bookapi.Service.BookServiceImpl;
import org.example.bookapi.Repository.InMemoryBookRepository;
import org.example.bookapi.dto.BookDTO;
import org.example.bookapi.dto.CreateBookDTO;
import org.example.bookapi.dto.UpdateBookDTO;

import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

    private BookService bookService;

    public BookResource() {

        this.bookService = new BookServiceImpl(new InMemoryBookRepository());
    }

    @GET
    public List<BookDTO> getAllBooks() {
        return bookService.getAll();
    }

    @GET
    @Path("/{id}")
    public BookDTO getBookById(@PathParam("id") Long id) {
        return bookService.getById(id);
    }

    @POST
    public Response createBook(CreateBookDTO createBookDTO) {
        BookDTO createdBook = bookService.create(createBookDTO);
        return Response.status(Response.Status.CREATED).entity(createdBook).build();
    }

    @PUT
    @Path("/{id}")
    public BookDTO updateBook(@PathParam("id") Long id, UpdateBookDTO updateBookDTO) {
        return bookService.update(id, updateBookDTO);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") Long id) {
        bookService.delete(id);
        return Response.noContent().build();
    }
}
