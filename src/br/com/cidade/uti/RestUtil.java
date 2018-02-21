package br.com.cidade.uti;

import javax.ws.rs.core.Response;

public class RestUtil {
	
	public static Response buildResponseError(String msg) {
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(msg).build();
	}

}
