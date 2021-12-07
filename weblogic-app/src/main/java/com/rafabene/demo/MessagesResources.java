package com.rafabene.demo;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rafabene.demo.domain.Message;
import com.rafabene.demo.domain.MessageRepository;

/**
 * MessagesResources
 */
@Path("/messages")
public class MessagesResources {

    @Inject
	private MessageRepository messageRepository;

    @POST
	public Response addMessage(@FormParam("username") String username, @FormParam("message") String text) {
		Message message = new Message(username, text);
		try {
			messageRepository.save(message);
		} catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
		return Response.ok().build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
    public List<Message> getAllMessages(){
        return messageRepository.findAllOrdered();
    }

    
}