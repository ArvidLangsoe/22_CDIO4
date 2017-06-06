package rest.crud;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import dataTransferObjects.RaavareDTO;
import exceptions.CollisionException;
import exceptions.DALException;
import exceptions.InputException;

@Path("raw_material")
public class RawMaterialCrud {
	
	IRawMaterialController controller = new rawMaterialController();
	
	@Path("create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String createRawMaterial(RaavareDTO rawMaterial)
	{
		try
		{
		controller.create(RaavareDTO);
		return "success";
		}
		catch (CollisionException e)
		{
			return "failure";
		}
		catch (InputException e)
		{
			
		}
		catch (DALException e)
		{
			
		}
	}
}