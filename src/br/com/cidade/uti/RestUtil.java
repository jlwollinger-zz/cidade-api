package br.com.cidade.uti;

import javax.ws.rs.core.Response;

/***
 * 
 * @author Jose Wollinger
 * 21 Feb, 2018
 *
 */
public class RestUtil {
	
	public static Response buildResponseError(String msg) {
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(msg).build();
	}
	
	public static Response buildResponseBadRequest(String msg) {
		return Response.status(Response.Status.BAD_REQUEST).entity(msg).build();
	}

}
