package controller;

import java.util.List;

import controller.interfaces.IProductBatchController;
import dataAccessObjects.interfaces.IProductBatchDAO;
import dataAccessObjects.interfaces.IRecipeDAO;
import dataTransferObjects.ProductBatchDTO;
import exceptions.CollisionException;
import exceptions.DALException;
import exceptions.InputException;

public class ProductBatchController implements IProductBatchController {

	IProductBatchDAO dao;
	IRecipeDAO rdao;

	public ProductBatchController(IProductBatchDAO dao, IRecipeDAO rdao){
		this.dao = dao;
		this.rdao = rdao;
	}


	public IProductBatchDAO getDao() {
		return dao;
	}


	@Override
	public ProductBatchDTO getProductBatch(int pbId) throws InputException, DALException {
		return dao.getProductBatch(pbId);
	}

	@Override
	public List<ProductBatchDTO> getProductBatchList() throws DALException {
		return dao.getProductBatchList();
	}

	@Override
	public void createProductBatch(ProductBatchDTO productBatch) //TODO check -1<status<3
			throws CollisionException, InputException, DALException {

		try{
			rdao.getRecipe(productBatch.getReceptId());
		}catch(DALException e){
			throw new InputException(e.getMessage());
		}

		if(productBatch.getStatus() <= 0 && productBatch.getStatus() <= 2)
			dao.createProductBatch(productBatch);
		else
			throw new InputException("Status must be between 0 and 2");
	}

	@Override
	public void updateProductBatch(ProductBatchDTO productBatch) throws InputException, DALException {
		if(productBatch.getStatus() <= 0 && productBatch.getStatus() <= 2)
			dao.updateProductBatch(productBatch);
		else
			throw new InputException("Status must be between 0 and 2");
	}

}
