package controller;

import java.util.Hashtable;

import controller.interfaces.ILoginController;
import dataAccessObjects.MyOperatoerDAO;
import dataAccessObjects.interfaces.OperatoerDAO;
import dataTransferObjects.LoginPOJO;
import dataTransferObjects.OperatoerDTO;
import exceptions.DALException;
import exceptions.InputException;
import staticClasses.Validator;

public class LoginController implements ILoginController {

	static Hashtable<Integer, Integer> adminKeyTable = new Hashtable<Integer, Integer>();
	OperatoerDAO dao = new MyOperatoerDAO();


	@Override
	public LoginState checkLogin(LoginPOJO user) {
		try{
			if(user.isSuperAdmin())
				return LoginState.SUPER;
			
		    else if (adminKeyTable.remove(user.getId()) == Integer.parseInt(user.getPassword()))
				return LoginState.NEW;
			
			else
				return LoginState.FALSE;

		}catch(Exception e){
			try{
				if(dao.getOperatoer(Integer.parseInt(user.getId())).getPassword().equals(user.getPassword()))
					return LoginState.TRUE;
				else
					return LoginState.FALSE;

			}catch(Exception e2){
				System.out.println(e2);
				return LoginState.FALSE;
			}
		}
	}
	
	@Override
	public void setNewPassword(int id, String password) throws InputException, DALException{
		OperatoerDTO user = dao.getOperatoer(id).copy();

		user.setPassword(password);

		dao.updateOperatoer(user);
	}



	@Override
	public int generateAdminKey(int id) {
		Integer key = new Integer((int) Math.floor(Math.random()*10000));
		adminKeyTable.put(id, key);
		return key;
	}

	@Override
	public int resetPassword(int id) throws InputException, DALException{
		try{
			Validator.validateUserID(id);

			OperatoerDTO user = dao.getOperatoer(id).copy();

			user.setPassword(generatePassword());

			dao.updateOperatoer(user);

			return generateAdminKey(id);

		}catch(InputException e){
			throw new InputException(e.getMessage());
		}catch(DALException e){
			e.printStackTrace();
			throw new DALException(e.getMessage());
		}
	}


	/**
	 * Generates a password for the userDTO accepting the rules of DTU
	 * passwords.
	 * 
	 * @return The generated password.
	 */
	public String generatePassword() {
		String password = "XX";
		int rand = (int) (Math.random() * 1000000000);
		return password+rand;
	}

}
