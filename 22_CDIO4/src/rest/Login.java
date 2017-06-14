package rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import controller.Initializer;
import dataTransferObjects.LoginPOJO;
import exceptions.DALException;
import exceptions.InputException;

@Path("login")
public class Login {
	
	@Path("user")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String loginUser(LoginPOJO login) {
		System.out.println(login);
		switch(Initializer.getLoginController().checkLogin(login)) {
		case TRUE:
			return "true_login";
		case SUPER:
			return "super_login";
		case NEW:
			return "new_login";
		default:
			return "not_login: Bruger id eller password er forkert.";
		}
	}
	
	@Path("new_password")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public String newPassword(LoginPOJO login) {
		try {
			Initializer.getLoginController().setNewPassword(Integer.parseInt(login.getId()), login.getPassword());
			return "success: Password opdateret";
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "format-error: Det indtastede id er ugyldigt.";
		} catch (InputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "input-error: Passwordet er ugyldigt.";
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "system-error: Der skete en fejl i systemet.";
		}
	}
	
	@Path("reset_password")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String resetPassword(String userId) {
		try {
			return "success: Brugerens password blev nulstillet. Engangsnøglen er " + Initializer.getLoginController().resetPassword(Integer.parseInt(userId));
		} catch (InputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "input-error: Det indtastede er ugyldigt.";
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "system-error: Der skete en fejl i systemet.";
		}
	}
}
