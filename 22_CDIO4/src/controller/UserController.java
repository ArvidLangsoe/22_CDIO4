package controller;

import java.util.List;

import dataAccessObjects.MyOperatoerDAO;
import dataAccessObjects.interfaces.OperatoerDAO;
import dataTransferObjects.OperatoerDTO;
import exceptions.*;
import staticClasses.Validator;

public class UserController {

	OperatoerDAO dao = new MyOperatoerDAO();

	public void validation(OperatoerDTO user) throws InputException{
		Validator.validateUserID(user.getOprId());
		Validator.validateUsername(user.getOprNavn());
		Validator.validateInitials(user.getIni());
		Validator.validateCPR(user.getCpr());
		Validator.validatePassword(user.getPassword());
		Validator.validateRole(user.getRolle());
	}

	public String createUser(OperatoerDTO user){
		try{

			validation(user);

			dao.createOperatoer(user);

			return "true";

		}catch(InputException e){
			return ("INPUT FAIL: "+e.toString());

		}catch(DALException e){
			return ("DAL FAIL: "+e.toString());
		}
	}

	public String updateUser(OperatoerDTO user){
		try{

			validation(user);

			dao.updateOperatoer(user);

			return "true";

		}catch(InputException e){
			return ("INPUT FAIL: "+e.toString());

		}catch(DALException e){
			return ("DAL FAIL: "+e.toString());

		}
	}

	public OperatoerDTO getUser(int userID){
		try{
			Validator.validateUserID(userID);

			return dao.getOperatoer(userID);

		}catch(InputException e){
			return null;


		}catch(DALException e){
			return null;

		}
	}

	public List<OperatoerDTO> getUserList(){
		try{

			return dao.getOperatoerList();

		}catch(DALException e){
			return null;

		}
	}
}